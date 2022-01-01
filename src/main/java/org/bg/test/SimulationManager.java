package org.bg.test;

import org.bg.test.sensors.AbstractSensorInputPojo;

import java.util.concurrent.ExecutorService;

public class SimulationManager<S extends AbstractSensorInputPojo> {
    private CsvParser<S> parser;
    private SensorPlayer<S> sensorPlayer;
    private ExecutorService executorService;

    public SimulationManager(CsvParser<S> parser, SensorPlayer<S> sensorPlayer, ExecutorService executorService) {
        this.parser = parser;
        this.sensorPlayer = sensorPlayer;
        this.executorService = executorService;
    }

    public void run() {
        executorService.submit(() -> parser.startReadCsv());
        executorService.submit(() -> sensorPlayer.play());
    }
}
