package com.accenture.payment.processor.payment;

import com.accenture.model.ActionType;
import com.accenture.model.PaymentEntity;
import com.accenture.model.Product;
import com.accenture.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.awt.geom.Arc2D;
import java.time.LocalDateTime;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

    //  Find a payment by id
    @Query("select pe from PaymentEntity pe where pe.paymentId=:id")
    PaymentEntity findPaymentById(@Param("id") Integer id);

    //  Find the total payment made by a person
    @Query("select SUM(pe.paymentAmount) AS total from PaymentEntity pe where pe.CustomerName=:customer_name")
    Double findTotalAmountByCustomerName(@Param("customer_name") String customerName);

    // Delete a payment by id
    @Modifying
    @Transactional
    @Query(value = "delete from payment_entities where payment_id=:id", nativeQuery = true)
    Integer deletePaymentById(@Param("id") Integer id);
}
