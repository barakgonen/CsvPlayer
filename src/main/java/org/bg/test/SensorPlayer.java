package org.bg.test;

import org.bg.test.sensors.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SensorPlayer {
    private RawSensorData rawSensorData;
    private ArrayList<AbstractTarget> timeRangeScenario;

    public SensorPlayer(RawSensorData rawSensorData) {
        this.rawSensorData = rawSensorData;
        this.timeRangeScenario = new ArrayList<>();
    }

    public void play() {
        System.out.println("Starting to play sensor: " + rawSensorData.getSensorName());
        for (HashMap<String, String> update : rawSensorData.getSensorUpdates()) {
            switch (rawSensorData.getSensorName()) {
                case "A":
                    timeRangeScenario.add(new aTarget(update));
                    break;
                case "AB":
                    timeRangeScenario.add(new abTarget(update));
                    break;
                case "AIS":
                    timeRangeScenario.add(new aisTarget(update));
                    break;
                case "GPS":
                    timeRangeScenario.add(new gpsTarget(update));
                    break;
                default:
                    break;
            }
        }

        long lut = timeRangeScenario.get(0).getMillis();
        for (AbstractTarget target : timeRangeScenario) {
            long diff = Math.abs(lut - target.getMillis());
            try {
                Thread.sleep(diff);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(target);
        }

    }
}
