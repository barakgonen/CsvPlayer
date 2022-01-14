package org.bg.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TransferQueue;

public class MessagesPlayer implements Runnable {
    int numberOfHandledMsgs;
    private TransferQueue<Object> convertedMessages;
    private ExecutorService executorService;

    public MessagesPlayer(TransferQueue<Object> convertedMessages, ExecutorService executorService) {
        this.convertedMessages = convertedMessages;
        this.numberOfHandledMsgs = 0;
        this.executorService = executorService;
    }

    @Override
    public void run() {
        while (!convertedMessages.isEmpty()) {
            Object message = convertedMessages.poll();
            executorService.submit(() -> sendToInterface(message));
            if (convertedMessages.size() > 5) {
                System.out.println("BG ERROR!!! were too late");
            }
            if (numberOfHandledMsgs % 1000 == 0) {
                System.out.println("Handled: " + numberOfHandledMsgs);
            }
        }
    }

    private void sendToInterface(Object obj) {
        try {
            Thread.sleep(50);
            numberOfHandledMsgs++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
