package org.bg.test.sensors;

import com.opencsv.bean.CsvBindByPosition;

// This class represents A target report, it's fields must be the same as CSV's header
public class aTarget extends CsvBean {
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
    @CsvBindByPosition(position = 6)
    private String vel;

    @Override
    public String toString() {
        return "aTarget{" +
                super.toString() +
                ", id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", size='" + size + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", vel='" + vel + '\'' +
                '}';
    }
}
