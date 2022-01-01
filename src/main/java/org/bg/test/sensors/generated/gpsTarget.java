package org.bg.test.sensors.generated;

import com.opencsv.bean.CsvBindByName;
import org.bg.test.sensors.AbstractSensorInputPojo;

public class gpsTarget implements AbstractSensorInputPojo {
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

    public static String getSensorName() {
        return "GPS";
    }

    public static String getHeader() {
        return "timeStamp,id,uuid,size,lat,lon";
    }

    @Override
    public String toString() {
        return "gpsTarget{" +
                "timeStamp=" + timeStamp +
                ", id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", size='" + size + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }

    @Override
    public long getMillis() {
        return timeStamp;
    }
}
