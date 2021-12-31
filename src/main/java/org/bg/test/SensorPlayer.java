package org.bg.test;

import org.bg.test.sensors.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SensorPlayer<S extends CsvBean> {
    private RawSensorData rawSensorData;
    private ArrayList<CsvBean> timeRangeScenario;

    public SensorPlayer(RawSensorData rawSensorData) {
        this.rawSensorData = rawSensorData;
        this.timeRangeScenario = new ArrayList<>();
    }

    public void play() {
        System.out.println("Starting to play sensor: " + rawSensorData.getSensorName());
        for (CsvBean b : rawSensorData.getSensorUpdates()) {
            timeRangeScenario.add((S)b);
        }

        long lut = timeRangeScenario.get(0).getMillis();
        for (CsvBean target : timeRangeScenario) {
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
