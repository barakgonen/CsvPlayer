package org.bg.test;

import org.bg.test.sensors.AbstractSensorInputPojo;

import java.util.*;

/**
 * Syncronizer for whole record, set of sensors
 */
public class WholeScenarioSyncronizer {
    Collection<SensorMessagesSyncronizer> sensorsSyncronizedMessages;
    TreeSet<AbstractSensorInputPojo> scenerioSyncronizer;
    PriorityQueue<AbstractSensorInputPojo> syncronizedScenerioQueue;

    public WholeScenarioSyncronizer(Collection<SensorMessagesSyncronizer> sensorsSyncronizedMessages) {
        this.sensorsSyncronizedMessages = sensorsSyncronizedMessages;
        this.scenerioSyncronizer = new TreeSet<>();
        this.syncronizedScenerioQueue = new PriorityQueue<>();
        sensorsSyncronizedMessages.forEach(sensorMessagesSyncronizer ->
                this.scenerioSyncronizer.addAll(sensorMessagesSyncronizer.getSyncronizedSensorMessages()));
        syncronizedScenerioQueue.addAll(scenerioSyncronizer);
    }

    public PriorityQueue<AbstractSensorInputPojo> getSyncronizedScenerio() {
        return syncronizedScenerioQueue;
    }
}
