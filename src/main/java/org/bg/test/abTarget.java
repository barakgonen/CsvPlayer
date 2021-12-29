package org.bg.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public class abTarget {
    private String timeStamp;
    private String id;
    private String uuid;
    private String size;
    private String bearing;
    private String lat;
    private String lon;

    public abTarget(HashMap<String, String> keyValue) {
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

    public void setid(String id) {
        this.id = id;
    }

    public void setuuid(String uuid) {
        this.uuid = uuid;
    }

    public void setsize(String size) {
        this.size = size;
    }

    public void setbearing(String bearing) {
        this.bearing = bearing;
    }

    public void setlat(String lat) {
        this.lat = lat;
    }

    public void setlon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "abTarget{" +
                "timeStamp='" + timeStamp + '\'' +
                ", id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", size='" + size + '\'' +
                ", bearing='" + bearing + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }
}
