package com.accenture.transactionapplication.controller.transaction;

import com.accenture.model.ActionType;
import com.accenture.model.Product;
import com.accenture.model.Transaction;
import com.accenture.transactionapplication.service.transaction.TransactionDetails;
import com.accenture.transactionapplication.service.transaction.TransactionService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@JsonFormat
@JsonSerialize
@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final JmsTemplate jmsTemplate;

    @GetMapping
    @Secured("ROLE_USER")
    public List<Transaction> findAllByProductAndTypeAndAmountBeforeAndAmountAfterOrderByCreatedAtAsc
            (@RequestParam(required = false) @Validated Product productId,
             @RequestParam(required = false) ActionType actionType,
             @RequestParam(required = false) @Validated Double minAmount,
             @RequestParam(required = false) @Validated Double maxAmount,
             @RequestParam(required = false) Integer page,
             @RequestParam(required = false) Integer size) {
        return transactionService.findAllByProductAndTypeAndAmountBeforeAndAmountAfterOrderByCreatedAtAsc
                (productId, actionType, minAmount, maxAmount, page, size);
    }

    @GetMapping("/byAmount")
    @Secured("ROLE_USER")
    public List<Transaction> findAllByAmountBeforeAndAmountAfterOrderByAmountDesc
            (@RequestParam(required = false) Double minAmount,
             @RequestParam(required = false) Double maxAmount) {
        return transactionService.findAllByAmountBeforeAndAmountAfterOrderByAmountDesc(minAmount, maxAmount);
    }

    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public Transaction findTransactionById(@PathVariable Integer id) {
        return transactionService.findTransactionById(id);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public Integer deleteTransactionById(@PathVariable Integer id) {
        return transactionService.deleteTransactionById(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public Transaction insertNewTransaction(@RequestBody Transaction transaction) {
        return transactionService.insertNewTransaction(transaction);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public Transaction updateTransactionById(@PathVariable @Validated Integer id, @RequestBody Transaction transaction) {
        return transactionService.updateTransactionById(transaction, id);
    }

    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @GetMapping("/product-transactions_amount")
    public Map<String, List<TransactionDetails>> reportProductTransactions() {
        return transactionService.reportProductTransactions();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/actionType-transaction_amount")
    public Map<String, List<TransactionDetails>> reportActionTypeTransactions() {
        return transactionService.reportActionTypeTransactions();
    }

    @Secured("ROLE_USER")
    @PostMapping("/send")
    public void sedMessage(@RequestBody Transaction transaction) {
        System.out.println("Sending a transaction");
        jmsTemplate.convertAndSend("TransactionQueue", transaction);
        System.out.println("Sent message is: " + transaction);
    }
}
