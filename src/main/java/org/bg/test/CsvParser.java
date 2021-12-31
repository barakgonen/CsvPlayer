package org.bg.test;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.bg.test.sensors.AbstractSensorInputPojo;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvParser<S extends AbstractSensorInputPojo> {
    private List<S> csvList;
    private Class<S> parsingType;
    private String inputFilePath;

    public CsvParser(Class<S> inputSensorType, String inputFilePath) {
        this.parsingType = inputSensorType;
        this.inputFilePath = inputFilePath;
    }

    public List<S> getRawData() {
        initializeReader();
        if (csvList != null)
            return csvList;
        return new ArrayList<>();
    }

    private void initializeReader() {
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(this.parsingType);
        try {
            Reader reader = Files.newBufferedReader(Paths.get(inputFilePath));
            CsvToBean cb = new CsvToBeanBuilder(reader)
                    .withType(this.parsingType)
                    .withMappingStrategy(ms)
                    .withSkipLines(1)
                    .build();
            csvList = cb.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
