package org.bg.test.sensors.generated;

import com.opencsv.bean.CsvBindByName;
import org.bg.test.sensors.AbstractSensorInputPojo;

public class AbTargetReport implements AbstractSensorInputPojo, Comparable<AbstractSensorInputPojo> {
    @CsvBindByName
    private Long timeStamp;
    @CsvBindByName
    private String id;
    @CsvBindByName
    private String uuid;
    @CsvBindByName
    private String size;
    @CsvBindByName
    private String bearing;
    @CsvBindByName
    private String lat;
    @CsvBindByName
    private String lon;

    public static String getSensorName() {
        return "AB";
    }

    public static String getHeader() {
        return "timeStamp,id,uuid,size,bearing,lat,lon";
    }

    @Override
    public String toString() {
        return "abTarget{" +
                "timestamp= " + timeStamp +
                ", id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", size='" + size + '\'' +
                ", bearing='" + bearing + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
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
