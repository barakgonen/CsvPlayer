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
    private HashMap<String, ArrayList<String>> keyToValues;
    private ArrayList<HashMap<String, String>> updatesList;


    public RawSensorData(File file) {
        this.inputFile = file;
        initializeData();
    }

    private void initializeData() {
        this.sensorName = inputFile.getName().substring(0, inputFile.getName().indexOf('.'));
        this.keyToValues = new HashMap<>();
        this.updatesList = new ArrayList<>();
        BufferedReader bufferedReader;
        ArrayList<String> lines;
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFile));
            lines = bufferedReader.lines().collect(Collectors.toCollection(ArrayList::new));
            Arrays.stream(lines.get(0).split(",")).forEach(s -> keyToValues.put(s, new ArrayList<>()));
            for (int i = 1; i < lines.size(); i++) {
                List<String> splitedRow = List.of(lines.get(i).split(",").clone());
                for (int j = 0; j < keyToValues.keySet().size(); j++) {
                    keyToValues.get(keyToValues.keySet().toArray()[j]).add(splitedRow.get(j));
                }
            }
            for (int i = 0; i < keyToValues.get(keyToValues.keySet().stream().findFirst().get()).size(); i++) {
                updatesList.add(specificKeyValueExtractor(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> specificKeyValueExtractor(int specificUpdate) {
        HashMap<String, String> specificKeyValue = new HashMap<>();
        keyToValues.forEach((key, value) -> specificKeyValue.put(key, value.get(specificUpdate)));
        return specificKeyValue;
    }

    public String getSensorName() {
        return sensorName;
    }

    public ArrayList<HashMap<String, String>> getSensorUpdates() {
        return updatesList;
    }
}
