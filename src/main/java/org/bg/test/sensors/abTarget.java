package org.bg.test.sensors;

import java.util.HashMap;

public class abTarget extends AbstractTarget {
    private String timeStamp;
    private String id;
    private String uuid;
    private String size;
    private String bearing;
    private String lat;
    private String lon;

    public abTarget(HashMap<String, String> keyValue) {
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
