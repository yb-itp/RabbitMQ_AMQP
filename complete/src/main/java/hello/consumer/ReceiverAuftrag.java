package hello.consumer;

import hello.model.Aktion;
import hello.model.Auftrag;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ybarvenko on 20.10.2015.
 */
public class ReceiverAuftrag {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(Auftrag message) {
        System.out.println("Received <" + message.getName() + ">");
        for (Aktion aktion : message.getActions()) {
            aktion.execute(message);
        }


        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
