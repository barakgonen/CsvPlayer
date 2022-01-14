package org.bg.test.sensors.generated;

import com.opencsv.bean.CsvBindByName;
import org.bg.test.sensors.AbstractSensorInputPojo;

public class AisTargetReport implements AbstractSensorInputPojo, Comparable<AbstractSensorInputPojo> {

    @CsvBindByName
    private long timeStamp;
    @CsvBindByName
    private String mmsi;

    public static String getSensorName() {
        return "AIS";
    }

    public static String getHeader() {
        return "timeStamp,mmsi";
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

    @Override
    public int compareTo(AbstractSensorInputPojo abstractSensorInputPojo) {
        if (timeStamp > abstractSensorInputPojo.getMillis()) {
            return 1;
        } else if (timeStamp < abstractSensorInputPojo.getMillis()) {
            return -1;
        } else {
            return 0;
        }
    }
}
