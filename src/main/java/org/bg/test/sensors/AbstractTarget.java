package org.bg.test.sensors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public abstract class AbstractTarget {
    protected long millis;

    public AbstractTarget(HashMap<String, String> keyValue) {
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
        initializeKeyFields();
    }

    public abstract void initializeKeyFields();

    public long getMillis() {
        return millis;
    }
}
