package org.bg.test.sensors.generated.navigation;

import org.bg.test.sensors.AbstractSensorInputPojo;

public class PositionReport implements AbstractSensorInputPojo, Comparable<AbstractSensorInputPojo> {
    private long millis;

    public PositionReport(long millis) {
        this.millis = millis;
    }

    @Override
    public long getMillis() {
        return millis;
    }

    @Override
    public int compareTo(AbstractSensorInputPojo abstractSensorInputPojo) {
        if (this.millis == abstractSensorInputPojo.getMillis()) {
            return 0;
        } else if (this.millis > abstractSensorInputPojo.getMillis()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "PositionReport{" +
                "millis=" + millis +
                '}';
    }
}
