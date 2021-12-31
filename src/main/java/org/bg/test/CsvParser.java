package org.bg.test;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvParser<S> {
    private List<S> csvList;
    private Class<S> parsingType;
    private String inputFilePath;

    public CsvParser(Class<S> inputSensorType, String inputFilePath) {
        this.parsingType = inputSensorType;
        this.inputFilePath = inputFilePath;
        this.csvList = new ArrayList<>();
    }

    public List<S> getRawData() {
        initializeReader();
        if (csvList != null)
            return csvList;
        return new ArrayList<>();
    }

    private void initializeReader() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(inputFilePath));

            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(parsingType)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            csvToBean.parse().forEach(o -> csvList.add(parsingType.cast(o)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
