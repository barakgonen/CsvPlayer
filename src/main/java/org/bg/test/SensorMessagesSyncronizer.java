package org.bg.test;

import org.bg.test.sensors.AbstractSensorInputPojo;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class SensorMessagesSyncronizer {
    private int numberOfLoops;
    private ConcurrentLinkedQueue<AbstractSensorInputPojo> msgsInputStream;
    private ConcurrentSkipListSet<AbstractSensorInputPojo> outputStream;

    public SensorMessagesSyncronizer(ConcurrentLinkedQueue<AbstractSensorInputPojo> msgsInputStream,
                                     ConcurrentSkipListSet<AbstractSensorInputPojo> outputStream) {
        this.numberOfLoops = 0;
        this.msgsInputStream = msgsInputStream;
        this.outputStream = outputStream;
    }

    public void runAndMerge() {
        numberOfLoops++;
        while (!msgsInputStream.isEmpty()) {
            outputStream.add(msgsInputStream.poll());
        }
    }
}
