package org.bg.test;

import org.bg.test.senders.DummyMessageSender;
import org.bg.test.sensors.AbstractSensorInputPojo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bg.test.Constants.CSV_RECORDS_PATH;

public class Main {

    public static void main(String[] args) {
        //--generate GPS,A,AB,AIS
        if (args.length > 0 && args[0].equals("--generate")) {
            // length of file represents csv in size of about 300MB, I saw records are about 280MB
            Utils.generateCsvToPathInLength(CSV_RECORDS_PATH, args[1], 11214632);
        } else {
            Set<File> recordFiles = Stream.of(new File(CSV_RECORDS_PATH).listFiles()).collect(Collectors.toSet());
            HashMap<String, Class<?>> sensorNameToClass = Utils.mapSensorNameToClass();

            if (recordFiles.isEmpty()) {
                System.out.println(Constants.PROMPT_COLORS.get("ERROR") +
                        "ERROR! couldn't find files in path: " + CSV_RECORDS_PATH);
            } else {
                ArrayList<SimulationManager<? extends AbstractSensorInputPojo>> simulationManagers = new ArrayList<>();
                ExecutorService executorService = Executors.newFixedThreadPool(recordFiles.size() * 2);
                recordFiles.forEach(file -> {
                    String sensorName = Utils.fileNameToSensorName(file.getName());
                    AtomicBoolean finishIndicator = new AtomicBoolean(false);
                    ConcurrentLinkedDeque<? extends AbstractSensorInputPojo> dataQueue = new ConcurrentLinkedDeque<>();
                    simulationManagers.add(new SimulationManager<>(new CsvParser(sensorNameToClass.get(sensorName),
                            file.getPath(),
                            dataQueue, finishIndicator),
                            new SensorPlayer(sensorName,
                                    dataQueue,
                                    new DummyMessageSender<>(sensorName),
                                    finishIndicator),
                            executorService));
                });
                simulationManagers.forEach(SimulationManager::run);
                executorService.shutdown();
            }
        }
    }
}
