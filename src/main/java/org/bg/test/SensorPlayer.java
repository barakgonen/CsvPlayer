package org.bg.test;

import java.util.HashMap;

public class SensorPlayer {
    private RawSensorData rawSensorData;

    public SensorPlayer(RawSensorData rawSensorData) {
        this.rawSensorData = rawSensorData;
    }

    public void play() {
        System.out.println("Starting to play sensor: " + rawSensorData.getSensorName());
        for (HashMap<String, String> update : rawSensorData.getSensorUpdates()) {
            switch (rawSensorData.getSensorName()) {
                case "A":
                    aTarget a = new aTarget(update);
                    System.out.println(a);
                    break;
                case "AB":
                    abTarget ab = new abTarget(update);
                    System.out.println(ab);
                    break;
                case "AIS":
                    aisTarget ais = new aisTarget(update);
                    System.out.println(ais);
                    break;
                case "GPS":
                    gpsTarget gps = new gpsTarget(update);
                    System.out.println(gps);
                    break;
                default:
                    break;
            }
        }
    }
}
