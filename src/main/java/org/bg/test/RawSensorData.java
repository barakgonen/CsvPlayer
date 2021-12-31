package org.bg.test;

import org.bg.test.sensors.CsvBean;

import java.util.ArrayList;
import java.util.List;

public class RawSensorData {

    private List<CsvBean> rawData;
    private String sensorName;

    public RawSensorData(String sensorName, List<CsvBean> rawData) {
        this.sensorName = sensorName;
        this.rawData = rawData;
    }

    public String getSensorName() {
        return sensorName;
    }

    public List<CsvBean> getSensorUpdates() {
        return rawData;
    }
}
