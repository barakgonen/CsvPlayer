package org.bg.test.sensors;

import java.util.HashMap;

public class gpsTarget extends AbstractTarget {
    private String timeStamp;
    private String id;
    private String uuid;
    private String size;
    private String lat;
    private String lon;

    public gpsTarget(HashMap<String, String> keyValue) {
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

    @Override
    public String toString() {
        return "gpsTarget{" + "timeStamp='" + timeStamp + '\'' + ", id='" + id + '\'' + ", uuid='" + uuid + '\'' + ", size='" + size + '\'' + ", lat='" + lat + '\'' + ", lon='" + lon + '\'' + '}';
    }
}
