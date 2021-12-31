package org.bg.test;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.bg.test.sensors.*;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static HashMap<Path, Set<File>> listFilesUsingFileWalkAndVisitor(String dir) throws IOException {
        HashMap<Path, Set<File>> tree = new HashMap<>();
        Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                if (!tree.containsKey(file.getParent())) {
                    tree.put(file.getParent(), new HashSet<>());
                }
                if (!Files.isDirectory(file)) {
                    tree.get(file.getParent()).add(file.toFile());
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return tree;
    }

    public static Path getCorrectPath(HashMap<Path, Set<File>> tree) {
        System.out.println("Choose relevant dates: ");
        for (int i = 0; i < tree.keySet().size(); i++) {
            System.out.println(i + 1 + ") " + ((Path) tree.keySet().toArray()[i]).getFileName().toString());
        }

        Scanner in = new Scanner(System.in);
//        int input = in.nextInt();
//        input -= 1;
        int input = 0;
        if (input >= tree.keySet().size()) {
            System.out.println("ERROR, choose valid date");
            return null;
        }

        System.out.println("Date chosen is: " + ((Path) tree.keySet().toArray()[input]).getFileName().toString());
        return ((Path) tree.keySet().toArray()[input]);
    }

    public static void main(String[] args) throws IOException {
        HashMap<String, Class<?>> nameToClass = new HashMap<>();


        nameToClass.put("AB", new AbTargetReport().getClass());
        nameToClass.put("AIS", new AisTargetReport().getClass());
        nameToClass.put("A", new PaTargetReport().getClass());
        nameToClass.put("GPS", new gpsTarget().getClass());

        String filesDir = "/home/barakg/IdeaProjects/CsvPlayer/csvFiles/";
        HashMap<Path, Set<File>> tree = listFilesUsingFileWalkAndVisitor(filesDir);
        if (tree.keySet().size() < 1) {
            System.out.println("ERROR! couldn't find files in path: " + filesDir);
        } else {
            Collection<RawSensorData> rawSensorsData = new ArrayList<>();
            if (tree.keySet().size() >= 2) {
                Path path = null;
                while (path == null) {
                    path = getCorrectPath(tree);
                }

                tree.get(path).forEach(file -> {
                    String sensorName = file.getPath().substring(file.getPath().lastIndexOf("/") + 1, file.getPath().lastIndexOf('.'));
                    CsvTransfer csvTransfer = new CsvTransfer();
                    ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
                    ms.setType(nameToClass.get(sensorName));

                    try {
                        Reader reader = Files.newBufferedReader(Paths.get(file.getPath()));
                                            CsvToBean cb = new CsvToBeanBuilder(reader)
                            .withType(nameToClass.get(sensorName))
                            .withMappingStrategy(ms)
                            .withSkipLines(1)
                            .build();

                    csvTransfer.setCsvList(cb.parse());
                    reader.close();

                    RawSensorData rawSensorData = new RawSensorData(sensorName, csvTransfer.getCsvList());
                    rawSensorsData.add(rawSensorData);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                ExecutorService executorService = Executors.newFixedThreadPool(tree.size());
                System.out.println("Found: " + rawSensorsData.size() + " reporting sensors, now starting to play them");
                Collection<SensorPlayer> sensorsPlayer = new ArrayList<>();
                rawSensorsData.forEach(rawSensorData -> sensorsPlayer.add(new SensorPlayer(rawSensorData)));
                sensorsPlayer.forEach(sensorPlayer -> executorService.submit(sensorPlayer::play));

                executorService.shutdown();
            }
        }
    }
}
