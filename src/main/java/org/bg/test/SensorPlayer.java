package org.bg.test;

import org.bg.test.senders.MessageSender;
import org.bg.test.sensors.AbstractSensorInputPojo;

import java.util.List;

public class SensorPlayer<S extends AbstractSensorInputPojo> {
    private List<S> rawData;
    private MessageSender<S> sender;
    private String sensorName;

    public SensorPlayer(String sensorName, List<S> rawData, MessageSender<S> messageSender) {
        this.rawData = rawData;
        this.sender = messageSender;
        this.sensorName = sensorName;
    }

    public void play() {
        if (!rawData.isEmpty()) {
            System.out.println("Starting to play sensor: " + sensorName);
            long lut = rawData.get(0).getMillis();
            for (S target : rawData) {
                long diff = Math.abs(lut - target.getMillis());
                try {
                    Thread.sleep(diff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sender.sendMessage(target);
            }
        } else {
            System.out.println("Raw data for sensor: " + sensorName + ", is empty!");
        }
    }
}
