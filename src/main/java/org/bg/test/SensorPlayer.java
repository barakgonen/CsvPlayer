package org.bg.test;

import org.bg.test.sensors.AbstractSensorInputPojo;

import java.util.ArrayList;

public class SensorPlayer<S extends AbstractSensorInputPojo> {
    private RawSensorData rawSensorData;
    private ArrayList<AbstractSensorInputPojo> timeRangeScenario;

    public SensorPlayer(RawSensorData rawSensorData) {
        this.rawSensorData = rawSensorData;
        this.timeRangeScenario = new ArrayList<>();
    }

    public void play() {
        System.out.println("Starting to play sensor: " + rawSensorData.getSensorName());
        timeRangeScenario.addAll(rawSensorData.getSensorUpdates());

        long lut = timeRangeScenario.get(0).getMillis();
        for (AbstractSensorInputPojo target : timeRangeScenario) {
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
