package org.bg.test.senders;

import org.bg.test.Constants;
import org.bg.test.sensors.AbstractSensorInputPojo;

public class DummyMessageSender<S extends AbstractSensorInputPojo> implements MessageSender<S> {
    private int numberOfSends;
    private String sensorName;
    private String promptColor;

    public DummyMessageSender(String sensorName) {
        numberOfSends = 0;
        this.sensorName = sensorName;
        this.promptColor = Constants.PROMPT_COLORS.get(this.sensorName);
    }

    @Override
    public void sendMessage(S sensorUpdate) {
        numberOfSends++;

        if (numberOfSends % 10000 == 0) {
            System.out.println(promptColor + "<DummyMessageSender::sendMessage()> sensor name: " + sensorName
                    + ", msg: " + sensorUpdate);
            System.out.println(promptColor + "Sender for sensor: " + sensorName + ", totally sent: " + numberOfSends
                    + ", messages");
        }
    }
}
