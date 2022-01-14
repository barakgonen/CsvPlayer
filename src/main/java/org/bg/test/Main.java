package org.bg.test;

import org.bg.test.sensors.AbstractSensorInputPojo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static org.bg.test.Constants.CSV_RECORDS_PATH;

public class Main {

    public static HashMap<String, Set<File>> mapSensorToRecords(String path) {
        HashMap<String, Set<File>> sensorToInputFiles = new HashMap<>();
        Stream.of(new File(path).listFiles()).forEach(file -> {
            String sensorName = Utils.fileNameToSensorName(file.getName());
            if (!sensorToInputFiles.containsKey(sensorName))
                sensorToInputFiles.put(sensorName, new HashSet<>());
            sensorToInputFiles.get(sensorName).add(file);
        });

        return sensorToInputFiles;
    }

    public static void main(String[] args) {
        //--generate GPS,A,AB,AIS
        if (args.length > 0 && args[0].equals("--generate")) {
            // length of file represents csv in size of about 300MB, I saw records are about 280MB
            Utils.generateCsvToPathInLength(CSV_RECORDS_PATH, args[1], 11214632);
        } else {
            HashMap<String, Set<File>> recordFiles = mapSensorToRecords(CSV_RECORDS_PATH);
            HashMap<String, Class<?>> sensorNameToClass = Utils.mapSensorNameToClass();

            if (recordFiles.isEmpty()) {
                System.out.println(Constants.PROMPT_COLORS.get("ERROR") +
                        "ERROR! couldn't find files in path: " + CSV_RECORDS_PATH);
            } else {
                ConcurrentLinkedQueue<AbstractSensorInputPojo> rawDataQueue = new ConcurrentLinkedQueue<>();
                ExecutorService executorService = Executors.newFixedThreadPool(recordFiles.size() * 2);

                ArrayList<AtomicBoolean> hasCompletedReading = new ArrayList<>();
                recordFiles.forEach((s, files) -> {
                    files.forEach(file -> {
                        String sensorName = Utils.fileNameToSensorName(file.getName());
                        AtomicBoolean finishIndicator = new AtomicBoolean(false);
                        CsvParser parser = new CsvParser(sensorNameToClass.get(sensorName), file.getPath(), sensorName,
                                finishIndicator, rawDataQueue);
                        hasCompletedReading.add(finishIndicator);
                        executorService.execute(parser);
                    });

//            }
                });
                ConcurrentSkipListSet<AbstractSensorInputPojo> msgsOutputStream = new ConcurrentSkipListSet<>();
                TransferQueue<AbstractSensorInputPojo> waitedMessages = new LinkedTransferQueue<>();
                TransferQueue<Object> convertedOutputStream = new LinkedTransferQueue<>();

                ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(recordFiles.size() * 2);
                SensorMessagesSyncronizer messagesSyncronizer = new SensorMessagesSyncronizer(rawDataQueue, msgsOutputStream);
                scheduledThreadPoolExecutor.scheduleAtFixedRate(messagesSyncronizer::runAndMerge, 0, 500, TimeUnit.MILLISECONDS);
                SimulationScenarioTimeSyncronizer simulationScenarioTimeSyncronizer = new SimulationScenarioTimeSyncronizer(msgsOutputStream, waitedMessages);
                scheduledThreadPoolExecutor.scheduleAtFixedRate(simulationScenarioTimeSyncronizer, 0, 500, TimeUnit.MILLISECONDS);
                MessagesConverter messagesConverter = new MessagesConverter(waitedMessages, convertedOutputStream);
                scheduledThreadPoolExecutor.scheduleAtFixedRate(messagesConverter, 0, 100, TimeUnit.MICROSECONDS);
                MessagesPlayer messagesPlayer = new MessagesPlayer(convertedOutputStream, executorService);
                scheduledThreadPoolExecutor.scheduleAtFixedRate(messagesPlayer, 0, 100, TimeUnit.MICROSECONDS);

                boolean hasFinishedReading = false;
                while (!hasFinishedReading) {
                    for (AtomicBoolean state : hasCompletedReading) {
                        hasFinishedReading = state.get() && hasFinishedReading;
                    }
                }

                executorService.shutdown();
                scheduledThreadPoolExecutor.shutdown();
            }
        }
    }
}
//        HashMap<String, HashMap<String, ConcurrentLinkedQueue<AbstractSensorInputPojo>>> sensorToMsgsQueue = new HashMap();
//
//        sensorToMsgsQueue.put("NAV-SYSTEM", new HashMap<>() {
//            {
//                put("POSITION", new ConcurrentLinkedQueue());
//                put("KINEMATIC", new ConcurrentLinkedQueue());
//                put("STATUS", new ConcurrentLinkedQueue());
//            }
//        });
//        sensorToMsgsQueue.put("AIS", new HashMap<>() {
//            {
//                put("STATIC-AND-VOYAJE", new ConcurrentLinkedQueue());
//                put("STANDARD-AB", new ConcurrentLinkedQueue());
//            }
//        });
//
//        HashMap<String, Integer> msgsToCounter = new HashMap<>();
//        msgsToCounter.put("NAV-POSITION", 0);
//        msgsToCounter.put("NAV-KINEMATIC", 0);
//        msgsToCounter.put("NAV-STATUS", 0);
//        msgsToCounter.put("AIS-STATIC-AND-VOYAJE", 0);
//        msgsToCounter.put("AIS-STANDARD-AB", 0);
//
//
//        long beforeGeneration = System.currentTimeMillis();
//        // MSGS FILLER FOR NAV
//        int numberOfMsgs = 0;
//        final int NUMBER_OF_MESSAGES = 1000;
//        while (numberOfMsgs != NUMBER_OF_MESSAGES) {
//            long nowTime = System.currentTimeMillis();
//            if (nowTime % 3 == 0) {
//                sensorToMsgsQueue.get("NAV-SYSTEM").get("POSITION").add(new PositionReport(nowTime));
//                msgsToCounter.put("NAV-POSITION", msgsToCounter.get("NAV-POSITION") + 1);
//                numberOfMsgs++;
//            } else if (nowTime % 4 == 0) {
//                sensorToMsgsQueue.get("NAV-SYSTEM").get("KINEMATIC").add(new KinematicReport(nowTime));
//                msgsToCounter.put("NAV-KINEMATIC", msgsToCounter.get("NAV-KINEMATIC") + 1);
//                numberOfMsgs++;
//            } else if (nowTime % 5 == 0) {
//                sensorToMsgsQueue.get("NAV-SYSTEM").get("STATUS").add(new StatusReport(nowTime));
//                msgsToCounter.put("NAV-STATUS", msgsToCounter.get("NAV-STATUS") + 1);
//                numberOfMsgs++;
//            }
//            try {
//                TimeUnit.of(ChronoUnit.MILLIS).sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // MSGS FILLER FOR AIS
//        numberOfMsgs = 0;
//        while (numberOfMsgs != NUMBER_OF_MESSAGES * 2) {
//            long nowTime = System.currentTimeMillis();
//            if (nowTime % 3 == 0) {
//                sensorToMsgsQueue.get("AIS").get("STATIC-AND-VOYAJE").add(new StaticAndVoyage(nowTime));
//                msgsToCounter.put("AIS-STATIC-AND-VOYAJE", msgsToCounter.get("AIS-STATIC-AND-VOYAJE") + 1);
//                numberOfMsgs++;
//            } else if (nowTime % 4 == 0) {
//                sensorToMsgsQueue.get("AIS").get("STANDARD-AB").add(new StandardAB(nowTime));
//                msgsToCounter.put("AIS-STANDARD-AB", msgsToCounter.get("AIS-STANDARD-AB") + 1);
//                numberOfMsgs++;
//            }
//            try {
//                TimeUnit.of(ChronoUnit.MILLIS).sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        int numberOfMessages = sensorToMsgsQueue.values().stream().mapToInt(stringConcurrentLinkedQueueHashMap ->
//                stringConcurrentLinkedQueueHashMap.values().stream().mapToInt(ConcurrentLinkedQueue::size).sum()).sum();
//        int countedMessagesSize = msgsToCounter.values().stream().mapToInt(integer -> integer).sum();
//        if (numberOfMessages != countedMessagesSize) {
//            System.out.println("BG ERRORRRR!!!");
//            System.exit(-1);
//        }
//        long afterGeneration = System.currentTimeMillis();
//        System.out.println("==============================================");
//        System.out.println("after filling, status is: " + msgsToCounter);
//        System.out.println("Generation took: " + (afterGeneration - beforeGeneration)
//                + ", millis which is about: " + (double)((afterGeneration - beforeGeneration) / 1000) + " seconds");
//        System.out.println("==============================================");
//
//        long logicStart = System.currentTimeMillis();
//
//        HashMap<String, SensorMessagesSyncronizer> sensorToOrderedMsgs = new HashMap<>();
//        sensorToMsgsQueue.keySet().forEach(s -> sensorToOrderedMsgs.put(s, new SensorMessagesSyncronizer()));
//
//        for (Map.Entry<String, HashMap<String,
//                ConcurrentLinkedQueue<AbstractSensorInputPojo>>> sensorToMsgs : sensorToMsgsQueue.entrySet()) {
//            System.out.println("Sensor name: " + sensorToMsgs.getKey());
//            sensorToMsgs.getValue().forEach((s, abstractSensorInputPojos) -> {
//                while (!abstractSensorInputPojos.isEmpty()) {
//                    sensorToOrderedMsgs.get(sensorToMsgs.getKey()).addMessage(abstractSensorInputPojos.poll());
//                }
//            });
//        }
//
//        int actualOrderedSize = sensorToOrderedMsgs.values().stream().
//                mapToInt(sensorMessagesSyncronizer -> sensorMessagesSyncronizer.syncronizedMessages.size()).sum();
//        if (actualOrderedSize != numberOfMessages || actualOrderedSize != countedMessagesSize) {
//            System.out.println("BG ERRORR!!!");
//            System.exit(-1);
//        }
//        // Finished to handle all sensors, now syncronizing the whole scenerio
//        WholeScenarioSyncronizer scenarioSyncronizer = new WholeScenarioSyncronizer(sensorToOrderedMsgs.values());
//
//        PriorityQueue<AbstractSensorInputPojo> syncronizedScenario = scenarioSyncronizer.getSyncronizedScenerio();
//
//        if (syncronizedScenario.size() != numberOfMessages || syncronizedScenario.size() != countedMessagesSize || syncronizedScenario.size() != actualOrderedSize) {
//            System.out.println("BG ERRORR!!!");
//            System.exit(-1);
//        }
//        long logicEnd = System.currentTimeMillis();
//        System.out.println("Logic took: " + (logicEnd - logicStart) + ", millis!! which is about: " + (double)((logicEnd - logicStart) / 1000) + " seconds");
//
//        System.out.println("Start sending");
//        long startSend = System.currentTimeMillis();
//        long currentMillis = 0;
//        while (!syncronizedScenario.isEmpty()) {
//            AbstractSensorInputPojo msg = syncronizedScenario.poll();
//            if (currentMillis != 0) {
//                long sleepDuration = msg.getMillis() - currentMillis;
////                System.out.println("Sleeping for: " + sleepDuration);
//                if (currentMillis > msg.getMillis()) {
//                    System.out.println("EROR!");
//                }
//                try {
//                    TimeUnit.of(ChronoUnit.MILLIS).sleep(sleepDuration);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (msg instanceof KinematicReport) {
//                msgsToCounter.put("NAV-KINEMATIC", msgsToCounter.get("NAV-KINEMATIC") - 1);
//            } else if (msg instanceof PositionReport) {
//                msgsToCounter.put("NAV-POSITION", msgsToCounter.get("NAV-POSITION") - 1);
//            } else if (msg instanceof StatusReport) {
//                msgsToCounter.put("NAV-STATUS", msgsToCounter.get("NAV-STATUS") - 1);
//            } else if (msg instanceof StandardAB) {
//                msgsToCounter.put("AIS-STANDARD-AB", msgsToCounter.get("AIS-STANDARD-AB") - 1);
//            } else if (msg instanceof StaticAndVoyage) {
//                msgsToCounter.put("AIS-STATIC-AND-VOYAJE", msgsToCounter.get("AIS-STATIC-AND-VOYAJE") - 1);
//            } else {
//                System.out.println("BG EEERRROOORRRR!!");
//                System.exit(-1);
//            }
//            currentMillis = msg.getMillis();
//        }
//
//        long endSend = System.currentTimeMillis();
//        System.out.println("==============================================");
//        System.out.println("after sending, status is: " + msgsToCounter);
//        System.out.println("Send took total: " + (endSend - startSend) + " millis which is about: " + (double)((endSend - startSend) / 1000) + " seconds");
//        System.out.println("==============================================");
