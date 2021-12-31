package org.bg.test.sensors;

import com.opencsv.bean.CsvBindByPosition;

public class gpsTarget extends CsvBean {
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

    @Override
    public String toString() {
        return "gpsTarget{" + super.toString() + '\'' + ", id='" + id + '\'' + ", uuid='" + uuid + '\'' + ", size='" + size + '\'' + ", lat='" + lat + '\'' + ", lon='" + lon + '\'' + '}';
    }
}
