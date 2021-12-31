package org.bg.test.sensors;

import com.opencsv.bean.CsvBindByPosition;

public class abTarget extends CsvBean {
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
