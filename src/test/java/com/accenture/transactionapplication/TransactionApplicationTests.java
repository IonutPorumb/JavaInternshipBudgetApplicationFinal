package com.accenture.transactionapplication;

import com.accenture.model.ActionType;
import com.accenture.model.Transaction;
import com.accenture.transactionapplication.service.transaction.TransactionService;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
class TransactionApplicationTests {

   @Autowired
   private TransactionService transactionService;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void contextLoads() {
        Transaction transactionById = transactionService.findTransactionById(2);
        Assumptions.assumeTrue(transactionById.getAmount() == 2141);
        Assumptions.assumeTrue(transactionById.getUserId() == 2);
        Assumptions.assumeTrue(transactionById.getType() == ActionType.SELL);

    }

}
