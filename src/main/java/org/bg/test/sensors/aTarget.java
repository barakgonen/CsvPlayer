package org.bg.test.sensors;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

// This class represents A target report, it's fields must be the same as CSV's header
public class aTarget extends AbstractTarget {
    private String timeStamp;
    private String id;
    private String uuid;
    private String size;
    private String lat;
    private String lon;
    private String vel;

    public aTarget(HashMap<String, String> keyValue) {
        super(keyValue);
    }

    @Override
    public void initializeKeyFields() {
        millis = Long.parseLong(timeStamp);
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
    public void setlat(String lat) {
        this.lat = lat;
    }
    public void setlon(String lon) {
        this.lon = lon;
    }
    public void setvel(String vel) {
        this.vel = vel;
    }

    @Override
    public String toString() {
        return "aTarget{" +
                "timeStamp='" + timeStamp + '\'' +
                ", id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", size='" + size + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", vel='" + vel + '\'' +
                '}';
    }
}
