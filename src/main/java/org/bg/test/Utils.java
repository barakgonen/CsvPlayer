package org.bg.test;

import org.bg.test.sensors.AbstractSensorInputPojo;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bg.test.Constants.*;

public class Utils {
    public static HashMap<String, Class<?>> mapSensorNameToClass() {
        HashMap<String, Class<?>> nameToClass = new HashMap<>();

        Reflections reflections = new Reflections(GENERATED_SENSORS_PACKAGE);
        reflections.getSubTypesOf(AbstractSensorInputPojo.class).forEach(aClass -> {
            try {
                nameToClass.put((String) aClass.getDeclaredMethods()[aClass.getDeclaredMethods().length - 1].invoke(aClass), aClass);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        return nameToClass;
    }

    public static String fileNameToSensorName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    public static String convertToCSV(String data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }

    public static HashMap<String, String> mapSensorToHeader() {
        HashMap<String, String> sensorToHeader = new HashMap<>();

        Reflections reflections = new Reflections(GENERATED_SENSORS_PACKAGE);
        reflections.getSubTypesOf(AbstractSensorInputPojo.class).forEach(aClass -> {
            try {
                sensorToHeader.put((String) Arrays.stream(aClass.getDeclaredMethods()).filter(method -> method.getName().contains("getSensorName")).findFirst().get().invoke(aClass),
                        (String) Arrays.stream(aClass.getDeclaredMethods()).filter(method -> method.getName().contains("getHeader")).findFirst().get().invoke(aClass));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        return sensorToHeader;
    }

    public static void generateCsvToPathInLength(String path, String sensorName, int length) {
        Set<String> sensorsToGenerate = Arrays.stream(sensorName.split(",")).collect(Collectors.toSet());
        HashMap<String, String> sensorToHeader = mapSensorToHeader();
        sensorsToGenerate.forEach(sensorToGenerate -> {
            ArrayList<String> dataLines = new ArrayList<>();
            dataLines.add(convertToCSV(sensorToHeader.get(sensorToGenerate)));

            // do some bullshit
            for (int i = 0; i < length; i++) {
                AtomicReference<String> line = new AtomicReference<>(
                        INITIAL_TIME_STAMP + (DELAY_BETWEEN_REPORTS_FOR_SENSOR.get(sensorToGenerate) * i) + ",");
                Arrays.stream(sensorToHeader.get(sensorToGenerate).split(",")).forEach(s -> {
                    if (!s.equals("timeStamp")) {
                        line.set(line.get() + s + ",");
                    }

                });
                dataLines.add(convertToCSV(line.get().substring(0, line.get().length() - 1)));
            }
            // write the file

            File csvOutputFile = new File(path + sensorToGenerate + ".csv");
            try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
                dataLines.stream()
                        .map(Utils::convertToCSV)
                        .forEach(pw::println);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

    }
}
