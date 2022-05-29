package com.accenture.transactionapplication.jms;

import com.accenture.model.Transaction;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    @JmsListener(destination = "TransactionQueue")
    public void receiveMessageListener1(Transaction transaction) {
        System.out.println("Message received by Listener 1 in budget-application:\n" + transaction);
    }

    @JmsListener(destination = "TransactionQueue")
    public void receiveMessageListener2(Transaction transaction) {
        System.out.println("Message received by Listener 2 in budget-application:\n" + transaction);
    }
}
