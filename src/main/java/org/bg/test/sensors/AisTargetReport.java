package org.bg.test.sensors;

import com.opencsv.bean.CsvBindByPosition;

public class AisTargetReport extends AbstractSensorInputPojo {
    @CsvBindByPosition(position = 1)
    private String mmsi;

    @Override
    public String toString() {
        return "aisTarget{" +
                super.toString() +
                ", mmsi='" + mmsi + '\'' +
                '}';
    }
}
