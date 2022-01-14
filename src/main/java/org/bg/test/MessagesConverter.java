package org.bg.test;

import org.bg.test.sensors.AbstractSensorInputPojo;

import java.util.concurrent.TransferQueue;

public class MessagesConverter implements Runnable {
    private int numberOfLoops;
    private TransferQueue<AbstractSensorInputPojo> inputConsumer;
    private TransferQueue<Object> outputProducer;

    public MessagesConverter(TransferQueue<AbstractSensorInputPojo> inputConsumer,
                             TransferQueue<Object> outputProducer) {
        this.numberOfLoops = 0;
        this.inputConsumer = inputConsumer;
        this.outputProducer = outputProducer;
    }

    @Override
    public void run() {
        if (!inputConsumer.isEmpty()) {
            while (!inputConsumer.isEmpty()) {
                try {
                    numberOfLoops++;
                    AbstractSensorInputPojo type = inputConsumer.take();
                    // MAKE CONVERSION
                    outputProducer.put(type);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (inputConsumer.size() > 10) {
                    System.out.println("BG ERROR!!! were late!");
                }
            }
        }
    }
}
