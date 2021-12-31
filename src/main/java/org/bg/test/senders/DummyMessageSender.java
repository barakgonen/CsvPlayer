package org.bg.test.senders;

import org.bg.test.sensors.AbstractSensorInputPojo;

public class DummyMessageSender<S extends AbstractSensorInputPojo> implements MessageSender<S> {
    @Override
    public void sendMessage(S sensorUpdate) {
        System.out.println("Dummy send for the following message: " + sensorUpdate);
    }
}
