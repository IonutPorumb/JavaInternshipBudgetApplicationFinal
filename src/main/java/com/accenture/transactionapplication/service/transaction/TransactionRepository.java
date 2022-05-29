package com.accenture.transactionapplication.service.transaction;

import com.accenture.model.ActionType;
import com.accenture.model.Product;
import com.accenture.model.Transaction;
import com.accenture.transactionapplication.service.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Integer> {
    //    Filter by min and max amount
    @Query("select t from Transaction t where (:min is null or t.amount>:min) and (:max is null or t.amount<:max)")
    List<Transaction> findAllByAmountBeforeAndAmountAfterOrderByAmountDesc(@Param("min") Double min,
                                                                           @Param("max") Double max);
    //  Find a transaction by id
    @Query("select t from Transaction t where t.id=:id")
    Transaction findTransactionById(@Param("id") Integer id);
    // Delete a transaction by id
    @Modifying
    @Transactional
    @Query(value = "delete from person_transactions where id=:id", nativeQuery = true)
    Integer deleteTransactionsById(@Param("id") Integer id);
    // Update a transaction by id
    @Modifying
    @Transactional
    @Query(value = "update person_transactions SET product_id=:product_id, " +
            "transaction_confirmation=:transaction_confirmation," +
            "user_id=:user_id," +
            "creation_date=:creation_date," +
            "action_type=:action_type," +
            "amount=:amount where id=:id", nativeQuery = true)
    void updateTransactionById(@Param("id") Integer id,
                               @Param("product_id") Product productId,
                               @Param("transaction_confirmation") boolean transactionConfirmation,
                               @Param("user_id") Integer userId,
                               @Param("creation_date") LocalDateTime creationDate,
                               @Param("action_type") ActionType actionType,
                               @Param("amount") Double amount);

    // POST /transactions - adds a new transaction
    @Query(value = "insert into person_transactions (amount, transaction_confirmation, creation_date, " +
            "action_type, user_id, product_id)" +
            "values (:amount, :transaction_confirmation, :creation_date, " +
            ":action_type, :user_id, :product_id )", nativeQuery = true)
    void insertNewTransaction(@Param("product_id") Product productId,
                              @Param("transaction_confirmation") boolean transactionConfirmation,
                              @Param("user_id") Integer userId,
                              @Param("creation_date") LocalDateTime creationDate,
                              @Param("action_type") ActionType actionType,
                              @Param("amount") Double amount);

    // Find all transactions by productId, actionType, minAmount, maxAmount
    @Query("select t from Transaction t where ((:product_id is null or t.product =:product_id) and (:action_type is null or t.type=:action_type) and " +
            "(:min_amount is null or t.amount >:min_amount) and (:max_amount is null or t.amount<:max_amount)) order by t.createdAt asc")
    List<Transaction> findAllByProductAndTypeAndAmountBeforeAndAmountAfterOrderByCreatedAtAsc(
            @Param("product_id") Product productId,
            @Param("action_type") ActionType actionType,
            @Param("min_amount") Double minAmount,
            @Param("max_amount") Double maxAmount,
            Pageable page);
    @Query("select t from Transaction t where (t.confirmed=:transaction_confirmation) and (t.createdAt>=:created_after) and " +
            "(t.createdAt<=:created_before)")
    List<Transaction> findAllByConfirmedAAndCreatedAtAfterAndCreatedAtBefore(
            @Param("transaction_confirmation") Boolean transactionConfirmation,
            @Param("created_after") LocalDateTime createdAfter,
            @Param("created_before") LocalDateTime createdBefore);
    List<Transaction> findAllByConfirmed(boolean value);

    // Filter transactions by date
    @Query(value = "select * from person_transactions order by creation_date asc", nativeQuery = true)
    List<Transaction> getTransactionsByCreatedAtAndOrderByCreatedAtAmountAsc();

    //    GET /transactions/reports/product
    @Query("SELECT t.amount from Transaction t where t.type=:action_type")
    List<Double> reportActionTypeTransactions(@Param("action_type") ActionType actionType);

    //    GET /transactions/reports/product
    @Query(value = "SELECT amount FROM person_transactions INNER JOIN products ON " +
            "person_transactions.product_id = products.id WHERE products.id=:product_id", nativeQuery = true)
    List<Double> reportProductTransactions(
            @Param("product_id") Integer productId);
}
