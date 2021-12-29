package org.bg.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RawSensorData {
    private File inputFile;
    private String sensorName;
    private ArrayList<SimpleField> fields;
    private int numberOfUpdates;

    private ArrayList<HashMap<String, String>> updatesList;

    public RawSensorData(File file) {
        this.inputFile = file;
        initializeData();
    }

    private void initializeData() {
        this.fields = new ArrayList<>();
        this.sensorName = inputFile.getName().substring(0, inputFile.getName().indexOf('.'));
        this.updatesList = new ArrayList<>();
        BufferedReader bufferedReader;
        ArrayList<String> lines;
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFile));
            lines = bufferedReader.lines().collect(Collectors.toCollection(ArrayList::new));
            Arrays.stream(lines.get(0).split(",")).forEach(s -> fields.add(new SimpleField(s)));
            for (int i = 1; i < lines.size(); i++) {
                List<String> splitedRow = List.of(lines.get(i).split(",").clone());
                for (int j = 0; j < fields.size(); j++) {
                    fields.get(j).addField(splitedRow.get(j));
                }
            }
            numberOfUpdates = lines.size() - 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSensorName() {
        return sensorName;
    }

    public ArrayList<HashMap<String, String>> getSensorUpdates() {
        ArrayList<HashMap<String, String>> allData = new ArrayList<>();

        for (int i = 0; i < numberOfUpdates; i++) {
            HashMap<String, String> singleUpdate = new HashMap<>();
            for (int j = 0; j < fields.size(); j++) {
                singleUpdate.put(fields.get(j).getFieldName(), fields.get(j).getNthReport(i));
            }
            allData.add(singleUpdate);
        }
        return allData;
    }
}
