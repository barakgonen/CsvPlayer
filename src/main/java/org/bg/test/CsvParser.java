package org.bg.test;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class CsvParser<S> {
    private ConcurrentLinkedDeque<S> rawDataQueue;
    private Class<S> parsingType;
    private String inputFilePath;
    private AtomicBoolean finishIndicator;
    private String sensorName;
    private String promptColor;

    public CsvParser(Class<S> inputSensorType,
                     String inputFilePath, ConcurrentLinkedDeque<S> rawDataQueue,
                     AtomicBoolean finishIndicator) {
        this.parsingType = inputSensorType;
        this.inputFilePath = inputFilePath;
        this.rawDataQueue = rawDataQueue;
        this.finishIndicator = finishIndicator;
        this.sensorName = Utils.fileNameToSensorName(inputFilePath.substring(inputFilePath.lastIndexOf('/') + 1));
        this.promptColor = Constants.PROMPT_COLORS.get(this.sensorName);
    }

    public void startReadCsv() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(inputFilePath));
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(parsingType)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<Object> iterator = csvToBean.iterator();

            int counter = 0;
            while (iterator.hasNext()) {
                rawDataQueue.add(parsingType.cast(iterator.next()));
                counter++;
                if (counter % 1000000 == 0)
                    System.out.println(promptColor + "<CsvParser::startReadCsv()> parsing csv for sensor: "
                            + sensorName + " so far, read: " + counter + " msgs");
                while (rawDataQueue.size() > 200000) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(promptColor + "<CsvParser::startReadCsv()> Finished reading input csv file for sensor: "
                    + sensorName + " totaly read: " + counter + " msgs");
            reader.close();
            this.finishIndicator.set(true);
            System.out.println(promptColor + "<CsvParser::startReadCsv()> Set finish indicator to true for sensor: "
                    + sensorName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
