package org.bg.test;

import org.bg.test.senders.MessageSender;
import org.bg.test.sensors.AbstractSensorInputPojo;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class SensorPlayer<S extends AbstractSensorInputPojo> {
    private final ConcurrentLinkedDeque<S> rawDataPipe;
    private final MessageSender<S> sender;
    private final String sensorName;
    private final AtomicBoolean readerCompletedFlag;
    private final String promptColor;

    public SensorPlayer(String sensorName,
                        ConcurrentLinkedDeque<S> rawDataPipe,
                        MessageSender<S> messageSender,
                        AtomicBoolean readerCompletedFlag) {
        this.rawDataPipe = rawDataPipe;
        this.sender = messageSender;
        this.sensorName = sensorName;
        this.readerCompletedFlag = readerCompletedFlag;
        this.promptColor = Constants.PROMPT_COLORS.get(this.sensorName);
    }

    public void play() {
        int msgsCounter = 0;
        long lastTimeStamp = 0;
        while (!readerCompletedFlag.get() || !rawDataPipe.isEmpty()) {
            while (!rawDataPipe.isEmpty()) {
                S value = rawDataPipe.poll();
                if (lastTimeStamp != 0) {
                    sleepBetweenSends(lastTimeStamp, value);
                }
                sender.sendMessage(value);
                handleStatistics(msgsCounter);
                msgsCounter++;
                lastTimeStamp = value.getMillis();
            }
        }

        System.out.println(promptColor + "<SensorPlayer::play()> Finished playing for sensor: "
                + sensorName + " total num of msgs: " + msgsCounter);
    }

    private void sleepBetweenSends(long lastTimeStamp, S value) {
        long diff = Math.abs(value.getMillis() - lastTimeStamp);
        try {
            Thread.sleep(diff);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleStatistics(int msgsCounter) {
        if (msgsCounter % 10000 == 0) {
            System.out.println(promptColor + "<SensorPlayer::handleStatistics()> Sensor name: " + sensorName + ", handled: "
                    + msgsCounter + " msgs so far, total size of raw data pipe: " + rawDataPipe.size());
            if (this.readerCompletedFlag.get()) {
                System.out.println(promptColor + "<SensorPlayer::handleStatistics()> Sensor name: "
                        + sensorName + ", reading has completed");
            }
            if (this.rawDataPipe.isEmpty()) {
                System.out.println(promptColor + "<SensorPlayer::handleStatistics()> Sensor name: " + sensorName
                        + ", writing has completed --> raw data pipe is empty");
            }
        }
    }
}
