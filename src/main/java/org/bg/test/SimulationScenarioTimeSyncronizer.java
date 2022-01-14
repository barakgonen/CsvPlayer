package org.bg.test;

import org.bg.test.sensors.AbstractSensorInputPojo;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

public class SimulationScenarioTimeSyncronizer implements Runnable {
    private long currentTimeStamp;
    private ConcurrentSkipListSet<AbstractSensorInputPojo> inputStream;
    private TransferQueue<AbstractSensorInputPojo> outputStream;

    public SimulationScenarioTimeSyncronizer(ConcurrentSkipListSet<AbstractSensorInputPojo> inputStream,
                                             TransferQueue<AbstractSensorInputPojo> outputStream) {
        this.currentTimeStamp = 0;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        while (!inputStream.isEmpty()) {
            AbstractSensorInputPojo first = inputStream.pollFirst();
            if (currentTimeStamp != 0) {
                try {
                    long sleepDuration = Math.abs(first.getMillis() - currentTimeStamp);
                    TimeUnit.of(ChronoUnit.MILLIS).sleep(sleepDuration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            outputStream.add(first);
            currentTimeStamp = first.getMillis();
        }
    }
}
