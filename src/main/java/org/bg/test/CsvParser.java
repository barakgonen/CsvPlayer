package org.bg.test;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.bg.test.sensors.AbstractSensorInputPojo;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CsvParser<S> implements Runnable {
    private ConcurrentLinkedQueue<AbstractSensorInputPojo> rawDataQueue;
    private Class<S> parsingType;
    private String inputFilePath;
    private AtomicBoolean finishIndicator;
    private String sensorName;

    public CsvParser(Class<S> inputSensorType, String inputFilePath, String sensorName, AtomicBoolean finishIndicator,
                     ConcurrentLinkedQueue<AbstractSensorInputPojo> rawDataQueue) {
        this.parsingType = inputSensorType;
        this.inputFilePath = inputFilePath;
        this.rawDataQueue = rawDataQueue;
        this.finishIndicator = finishIndicator;
        this.sensorName = sensorName;
    }

    @Override
    public void run() {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Path.of(inputFilePath));
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(parsingType)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<Object> iterator = csvToBean.iterator();

            int counter = 0;
            while (iterator.hasNext()) {
                rawDataQueue.add((AbstractSensorInputPojo) parsingType.cast(iterator.next()));
                counter++;
                if (counter % 1000000 == 0)
                    System.out.println("<CsvParser::startReadCsv()> parsing csv for sensor: " + sensorName
                            + " so far, read: " + counter + " msgs");
                while (rawDataQueue.size() > 200000) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("<CsvParser::startReadCsv()> Finished reading input csv file for sensor: " + sensorName
                    + " totaly read: " + counter + " msgs");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.finishIndicator.set(true);
        System.out.println("<CsvParser::startReadCsv()> Set finish indicator to true for sensor: " + sensorName);
    }
}
