package org.bg.test.sensors.generated;

import com.opencsv.bean.CsvBindByName;
import org.bg.test.sensors.AbstractSensorInputPojo;

public class PaTargetReport implements AbstractSensorInputPojo {
    @CsvBindByName
    private Long timeStamp;
    @CsvBindByName
    private String id;
    @CsvBindByName
    private String uuid;
    @CsvBindByName
    private String size;
    @CsvBindByName
    private String lat;
    @CsvBindByName
    private String lon;
    @CsvBindByName
    private String vel;

    public static String getSensorName() {
        return "A";
    }
    public static String getHeader() {
        return "timeStamp,id,uuid,size,lat,lon,vel";
    }

    @Override
    public String toString() {
        return "aTarget{" +
                "timestamp= " + timeStamp +
                ", id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", size='" + size + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", vel='" + vel + '\'' +
                '}';
    }

    @Override
    public long getMillis() {
        return timeStamp;
    }
}
