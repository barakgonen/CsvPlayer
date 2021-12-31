package org.bg.test.sensors.generated;

import com.opencsv.bean.CsvBindByPosition;
import org.bg.test.sensors.AbstractSensorInputPojo;

public class gpsTarget extends AbstractSensorInputPojo {

    @CsvBindByPosition(position = 1)
    private String id;
    @CsvBindByPosition(position = 2)
    private String uuid;
    @CsvBindByPosition(position = 3)
    private String size;
    @CsvBindByPosition(position = 4)
    private String lat;
    @CsvBindByPosition(position = 5)
    private String lon;

    public static String getSensorName() {
        return "GPS";
    }

    @Override
    public String toString() {
        return "gpsTarget{" + super.toString() + '\'' + ", id='" + id + '\'' + ", uuid='" + uuid + '\'' + ", size='" + size + '\'' + ", lat='" + lat + '\'' + ", lon='" + lon + '\'' + '}';
    }
}
