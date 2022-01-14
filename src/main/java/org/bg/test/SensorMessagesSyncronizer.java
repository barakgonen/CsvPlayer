package org.bg.test;

import org.bg.test.sensors.AbstractSensorInputPojo;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Syncronizer for specific sensor
 */
public class SensorMessagesSyncronizer {
    TreeSet<AbstractSensorInputPojo> syncronizedMessages;

    public SensorMessagesSyncronizer() {
        this.syncronizedMessages = new TreeSet<>();
    }

    public void addMessage(AbstractSensorInputPojo message) {
        this.syncronizedMessages.add(message);
    }

    public Set<AbstractSensorInputPojo> getSyncronizedSensorMessages() {
        return new HashSet<>(syncronizedMessages);
    }
}
