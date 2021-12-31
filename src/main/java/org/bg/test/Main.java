package org.bg.test;

import org.bg.test.senders.DummyMessageSender;
import org.bg.test.sensors.AbstractSensorInputPojo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bg.test.Constants.CSV_RECORDS_PATH;

public class Main {

    public static void main(String[] args) {
        Set<File> recordFiles = Stream.of(new File(CSV_RECORDS_PATH).listFiles()).collect(Collectors.toSet());
        HashMap<String, Class<?>> sensorNameToClass = Utils.mapSensorNameToClass();

        if (recordFiles.isEmpty()) {
            System.out.println("ERROR! couldn't find files in path: " + CSV_RECORDS_PATH);
        } else {
            Collection<SensorPlayer<? extends AbstractSensorInputPojo>> sensorsPlayer = new ArrayList<>();
            recordFiles.forEach(file -> {
                String sensorName = Utils.fileNameToSensorName(file);
                CsvParser csvParser = new CsvParser(sensorNameToClass.get(sensorName), file.getPath());
                sensorsPlayer.add(new SensorPlayer(sensorName, csvParser.getRawData(), new DummyMessageSender()));
            });
            ExecutorService executorService = Executors.newFixedThreadPool(sensorsPlayer.size());
            System.out.println("Found: " + sensorsPlayer.size() + " reporting sensors, now starting to play them");
            sensorsPlayer.forEach(sensorPlayer -> executorService.submit(sensorPlayer::play));

            executorService.shutdown();
        }
    }
}
