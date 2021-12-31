package org.bg.test.sensors;

import com.opencsv.bean.CsvBindByPosition;

public abstract class AbstractSensorInputPojo {

    @CsvBindByPosition(position = 0)
    protected long millis;

    public long getMillis() {
        return millis;
    }

    @Override
    public String toString() {
        return "millis=" + millis;
    }
}
