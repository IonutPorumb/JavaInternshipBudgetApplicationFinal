package com.accenture.payment.processor.jms;

import com.accenture.model.Transaction;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionReceiver {

    //    Definition of the first listener
    @JmsListener(destination = "TransactionQueue", containerFactory = "myFactory")
    public void receiveMessageListener1(Transaction transaction) {
        System.out.println("Received Transaction by listener 1 is: " + transaction);
    }

    //    Definition of the second listener
    @JmsListener(destination = "TransactionQueue", containerFactory = "myFactory")
    public void receiveMessageListener2(Transaction transaction) {
        System.out.println("Received transaction by the listener 2 is:" + transaction);
    }
}
