package org.bg.test.sensors.generated;

import com.opencsv.bean.CsvBindByName;
import org.bg.test.sensors.AbstractSensorInputPojo;

public class AisTargetReport implements AbstractSensorInputPojo {

    @CsvBindByName
    private long timeStamp;
    @CsvBindByName
    private String mmsi;

    public static String getSensorName() {
        return "AIS";
    }

    @Override
    public String toString() {
        return "aisTarget{" +
                "timeStamp=" + timeStamp +
                ", mmsi='" + mmsi + '\'' +
                '}';
    }

    @Override
    public long getMillis() {
        return timeStamp;
    }
}
