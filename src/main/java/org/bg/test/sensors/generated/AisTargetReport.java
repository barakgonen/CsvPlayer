package org.bg.test.sensors.generated;

import com.opencsv.bean.CsvBindByPosition;
import org.bg.test.sensors.AbstractSensorInputPojo;

public class AisTargetReport extends AbstractSensorInputPojo {

    @CsvBindByPosition(position = 1)
    private String mmsi;

    public static String getSensorName() {
        return "AIS";
    }

    @Override
    public String toString() {
        return "aisTarget{" +
                super.toString() +
                ", mmsi='" + mmsi + '\'' +
                '}';
    }
}
