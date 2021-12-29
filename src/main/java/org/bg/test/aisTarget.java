package org.bg.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public class aisTarget {
    private String timeStamp;
    private String mmsi;

    public aisTarget(HashMap<String, String> keyValue) {
        HashMap<String, Method> nameToMethod = new HashMap<>();
        Arrays.stream(this.getClass().getDeclaredMethods()).forEach(method -> {
            nameToMethod.put(method.getName(), method);
        });
        // do your magic!
        keyValue.entrySet().stream().forEach(stringStringEntry -> {
            try {
                nameToMethod.get("set"+stringStringEntry.getKey().toLowerCase(Locale.ROOT)).invoke(this, stringStringEntry.getValue());
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public void settimestamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setmmsi(String mmsi) {
        this.mmsi = mmsi;
    }

    @Override
    public String toString() {
        return "aisTarget{" +
                "timeStamp='" + timeStamp + '\'' +
                ", mmsi='" + mmsi + '\'' +
                '}';
    }
}
