package com.accenture.transactionapplication.service.transaction;

import com.accenture.model.ActionType;
import com.accenture.model.Product;
import com.accenture.model.Transaction;
import com.accenture.transactionapplication.service.product.ProductRepository;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ProductRepository productRepository;
    @Spy
    @InjectMocks
    private TransactionService transactionService;

    @Test
    void findTransactionById() {
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.when(transactionRepository.findTransactionById(integerArgumentCaptor.capture()))
                .thenReturn(new Transaction());

        transactionService.findTransactionById(2);
        Mockito.verify(transactionRepository, Mockito.times(1)).findTransactionById(integerArgumentCaptor.capture());
        Assumptions.assumeTrue(2 == integerArgumentCaptor.getValue());
    }

    @Test
    void findAllByAmountBeforeAndAmountAfterOrderByAmountDesc() {

        Mockito.doReturn(new ArrayList<>()).when(transactionService).findAllByAmountBeforeAndAmountAfterOrderByAmountDesc(Mockito.anyDouble(), Mockito.anyDouble());
        transactionService.findAllByAmountBeforeAndAmountAfterOrderByAmountDesc(200.0, 600.0);
        ArgumentCaptor<Double> minAmount = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> maxAmount = ArgumentCaptor.forClass(Double.class);
        Mockito.verify(transactionService, Mockito.times(1)).findAllByAmountBeforeAndAmountAfterOrderByAmountDesc(minAmount.capture(), maxAmount.capture());
        Assumptions.assumeTrue(200.0 == minAmount.getValue());
        Assumptions.assumeTrue(600.0 == maxAmount.getValue());
    }

    @Test
    void updateTransactionById() {
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        Mockito.doReturn(new Transaction()).when(transactionService).updateTransactionById(transactionArgumentCaptor.capture(), integerArgumentCaptor.capture());
        Transaction transaction = new Transaction(LocalDateTime.of(2022, 5, 12, 9, 0, 0), true,
                2, ActionType.BUY, 300.0, new Product("S20", "Smartphone",
                LocalDateTime.of(2022, 5, 14, 9, 0, 0), null));

        transactionService.updateTransactionById(transaction, 2);
        Mockito.verify(transactionService, Mockito.times(1)).updateTransactionById(transactionArgumentCaptor.capture(), integerArgumentCaptor.capture());
        Assumptions.assumeTrue(2 == integerArgumentCaptor.getValue());
        Assumptions.assumeTrue(transaction.equals(transactionArgumentCaptor.getValue()));
    }
}