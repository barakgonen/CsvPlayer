package org.bg.test.sensors.generated;

import com.opencsv.bean.CsvBindByPosition;
import org.bg.test.sensors.AbstractSensorInputPojo;

public class AbTargetReport extends AbstractSensorInputPojo {

    @CsvBindByPosition(position = 1)
    private String id;
    @CsvBindByPosition(position = 2)
    private String uuid;
    @CsvBindByPosition(position = 3)
    private String size;
    @CsvBindByPosition(position = 4)
    private String bearing;
    @CsvBindByPosition(position = 5)
    private String lat;
    @CsvBindByPosition(position = 6)
    private String lon;

    public static String getSensorName() {
        return "AB";
    }

    @Override
    public String toString() {
        return "abTarget{" +
                super.toString() +
                ", id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", size='" + size + '\'' +
                ", bearing='" + bearing + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }
}
