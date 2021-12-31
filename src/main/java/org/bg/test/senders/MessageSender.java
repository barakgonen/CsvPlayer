package org.bg.test.senders;

import org.bg.test.sensors.AbstractSensorInputPojo;

public interface MessageSender<T extends AbstractSensorInputPojo> {
    void sendMessage(T sensorUpdate);
}
