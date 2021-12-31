package org.bg.test;

import org.bg.test.sensors.AbstractSensorInputPojo;

import java.util.List;

public class RawSensorData {

    private List<AbstractSensorInputPojo> rawData;
    private String sensorName;

    public RawSensorData(String sensorName, List<AbstractSensorInputPojo> rawData) {
        this.sensorName = sensorName;
        this.rawData = rawData;
    }

    public String getSensorName() {
        return sensorName;
    }

    public List<AbstractSensorInputPojo> getSensorUpdates() {
        return rawData;
    }
}
