package com.accenture.transactionapplication.service.transaction;

import com.accenture.model.ActionType;
import com.accenture.model.Product;
import com.accenture.model.Transaction;
import com.accenture.transactionapplication.service.BaseService;
import com.accenture.transactionapplication.service.product.ProductRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@JsonSerialize
@AllArgsConstructor
public class TransactionService extends BaseService<Transaction, Integer> {
    @Override
    public boolean validateEntity(Transaction baseEntity) {
        return false;
    }
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final JobDao jobDao;

    //    Filter by min and max amount
    public List<Transaction> findAllByAmountBeforeAndAmountAfterOrderByAmountDesc(Double minAmount, Double maxAmount) {
        return transactionRepository.findAllByAmountBeforeAndAmountAfterOrderByAmountDesc(minAmount, maxAmount);
    }

    //  Find a transaction by id
    public Transaction findTransactionById(Integer id) {
        return transactionRepository.findTransactionById(id);
    }

    // Find all transactions by productId, actionType, minAmount, maxAmount
    public List<Transaction> findAllByProductAndTypeAndAmountBeforeAndAmountAfterOrderByCreatedAtAsc
    (Product productId, ActionType actionType, Double minAmount, Double maxAmount, Integer page, Integer size) {
        return transactionRepository.findAllByProductAndTypeAndAmountBeforeAndAmountAfterOrderByCreatedAtAsc
                (productId, actionType, minAmount, maxAmount, PageRequest.of(page, size, Sort.by("amount").ascending()));
    }

    // Delete a transaction by id
    public Integer deleteTransactionById(Integer id) {
        return transactionRepository.deleteTransactionsById(id);
    }

    // Update a transaction by id
    public Transaction updateTransactionById(Transaction transaction, Integer id) {
        if (transactionRepository.findById(id).isPresent()) {
            Transaction existingTransaction = transactionRepository.getById(id);
            LocalDateTime modificationDate = LocalDateTime.now();
            existingTransaction.setConfirmed(transaction.isConfirmed());
            existingTransaction.setAmount(transaction.getAmount());
            existingTransaction.setCreatedAt(modificationDate);
            existingTransaction.setProduct(transaction.getProduct());
            existingTransaction.setType(transaction.getType());
            existingTransaction.setUserId(transaction.getUserId());
            return transactionRepository.save(existingTransaction);
        } else {
            System.out.println("There is no transaction available with the required id number");
            return null;
        }
    }

    // POST /transactions - adds a new transaction
    public Transaction insertNewTransaction(Transaction transaction) {
        LocalDateTime modificationDate = LocalDateTime.now();
        Transaction newTransaction = new Transaction(modificationDate, transaction.getConfirmed(),
                transaction.getUserId(), transaction.getType(), transaction.getAmount(), transaction.getProduct());
        return transactionRepository.save(newTransaction);
    }

    // Return all confirmed transaction for past month:
    public List<Transaction> returnAllConfirmedTransactionsForPastMonth() {
        int beginDay = 1;
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        int endDay = lastMonth
                .with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
        Month transactionMonth = lastMonth.getMonth();
        int transactionYear = lastMonth.getYear();
        LocalDateTime beginDate = LocalDateTime.of
                (transactionYear, transactionMonth, beginDay, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of
                (transactionYear, transactionMonth, endDay, 23, 59, 59);
        return transactionRepository.findAllByConfirmedAAndCreatedAtAfterAndCreatedAtBefore(true, beginDate, endDate);
    }

    // Confirm all unconfirmed transaction in DB:
    public void confirmingAllUnconfirmedTransactions() {
        List<Transaction> unConfirmedTransactions = transactionRepository.findAllByConfirmed(false);
        if (unConfirmedTransactions.size() == 0) {
            System.out.println("------------------");
        } else {
            for (Transaction t : unConfirmedTransactions) {
                t.setConfirmed(true);
                transactionRepository.save(t);
            }
        }
    }

    // Filter transactions by date
    public List<Transaction> getTransactionsByCreatedAtAndOrderByCreatedAtAmountAsc() {
        return transactionRepository.getTransactionsByCreatedAtAndOrderByCreatedAtAmountAsc();
    }

    //    GET /transactions/reports/product
    public Map<String, List<TransactionDetails>> reportProductTransactions() {
        Map<String, List<TransactionDetails>> resultMap = new HashMap<>();
        transactionRepository.findAll()
                .forEach(t -> {
                    TransactionDetails transactionDetails = TransactionDetails.builder()
                            .type(t.getProduct().getName())
                            .amount(t.getAmount())
                            .build();
                    if (resultMap.containsKey(t.getProduct().getName())) {
                        resultMap.get(t.getProduct().getName()).add(transactionDetails);
                    } else {
                        resultMap.put(t.getProduct().getName(), new ArrayList<>(List.of(transactionDetails)));
                    }
                });
        return resultMap;
    }

    //    GET /transactions/reports/actionType
    public Map<String, List<TransactionDetails>> reportActionTypeTransactions() {
        Map<String, List<TransactionDetails>> resultMap = new HashMap<>();
        transactionRepository.findAll()
                .forEach(t -> {
                    TransactionDetails transactionDetails = TransactionDetails.builder()
                            .type(t.getType().toString())
                            .amount(t.getAmount())
                            .build();
                    if (resultMap.containsKey(t.getType().toString())) {
                        resultMap.get(t.getType().toString()).add(transactionDetails);
                    } else {
                        resultMap.put(t.getType().toString(), new ArrayList<>(List.of(transactionDetails)));
                    }
                });
        return resultMap;
    }
}
