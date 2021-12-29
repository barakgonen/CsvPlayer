package org.bg.test.sensors;

import java.util.HashMap;

public class aisTarget extends AbstractTarget {
    private String timeStamp;
    private String mmsi;

    public aisTarget(HashMap<String, String> keyValue) {
        super(keyValue);
    }

    public void settimestamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setmmsi(String mmsi) {
        this.mmsi = mmsi;
    }

    @Override
    public void initializeKeyFields() {
        millis = Long.parseLong(timeStamp);
    }

    @Override
    public String toString() {
        return "aisTarget{" +
                "timeStamp='" + timeStamp + '\'' +
                ", mmsi='" + mmsi + '\'' +
                '}';
    }
}
