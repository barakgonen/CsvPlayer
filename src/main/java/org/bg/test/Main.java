package org.bg.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

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
        int input = 1;
        if (input >= tree.keySet().size()) {
            System.out.println("ERROR, choose valid date");
            return null;
        }

        System.out.println("Date chosen is: " + ((Path) tree.keySet().toArray()[input]).getFileName().toString());
        return ((Path) tree.keySet().toArray()[input]);
    }

    public static void main(String[] args) throws IOException {
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
                    RawSensorData rawSensorData = new RawSensorData(file);
                    rawSensorsData.add(rawSensorData);
                });

                System.out.println("Found: " + rawSensorsData.size() + " reporting sensors, now starting to play them");
                Collection<SensorPlayer> sensorsPlayer = new ArrayList<>();
                rawSensorsData.forEach(rawSensorData -> sensorsPlayer.add(new SensorPlayer(rawSensorData)));
                sensorsPlayer.forEach(SensorPlayer::play);
            }
        }

    }
}
