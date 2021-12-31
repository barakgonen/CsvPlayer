package org.bg.test;

import org.bg.test.sensors.AbstractSensorInputPojo;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import static org.bg.test.Constants.GENERATED_SENSORS_PACKAGE;

public class Utils {
    public static HashMap<String, Class<?>> mapSensorNameToClass() {
        HashMap<String, Class<?>> nameToClass = new HashMap<>();

        Reflections reflections = new Reflections(GENERATED_SENSORS_PACKAGE);
        reflections.getSubTypesOf(AbstractSensorInputPojo.class).forEach(aClass -> {
            try {
                nameToClass.put((String) aClass.getDeclaredMethods()[1].invoke(aClass), aClass);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        return nameToClass;
    }

    public static String fileNameToSensorName(File file) {
        return file.getName().substring(0, file.getName().lastIndexOf('.'));
    }
}
