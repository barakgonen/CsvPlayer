package org.bg.test.sensors.generated.ais;

import org.bg.test.sensors.AbstractSensorInputPojo;

public class StandardAB implements AbstractSensorInputPojo, Comparable<AbstractSensorInputPojo> {
    private long timeStamp;

    public StandardAB(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "StandardAB{" +
                "timeStamp='" + timeStamp + '\'' +
                '}';
    }

    @Override
    public int compareTo(AbstractSensorInputPojo abstractSensorInputPojo) {
        if (this.timeStamp == abstractSensorInputPojo.getMillis()) {
            return 0;
        } else if (this.timeStamp > abstractSensorInputPojo.getMillis()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public long getMillis() {
        return timeStamp;
    }

}
