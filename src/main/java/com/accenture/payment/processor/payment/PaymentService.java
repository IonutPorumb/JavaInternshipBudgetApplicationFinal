package com.accenture.payment.processor.payment;

import com.accenture.model.PaymentEntity;
import com.accenture.model.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    //  Find a payment by id
    public PaymentEntity findPaymentById(Integer id ){
        return paymentRepository.findPaymentById(id);
    }

    //  Find the total payment made by a person
    public Double findTotalAmountByCustomerName(String customerName){
        return paymentRepository.findTotalAmountByCustomerName(customerName);
    }
    // Delete a payment by id
    public Integer deletePaymentById(Integer id){
        return paymentRepository.deletePaymentById(id);
    }

//    // Update a payment by id
//    public PaymentEntity updatePaymentById(Integer id, Double paymentAmount, String customerName,
//                                  LocalDateTime paymentDate, String paymentDescription){
//        if (paymentRepository.findPaymentById(id) != null) {
//            PaymentEntity paymentEntity = paymentRepository.findPaymentById(id);
//            LocalDateTime modificationDate = LocalDateTime.now();
//            paymentEntity.setPaymentAmount(paymentAmount);
//            paymentEntity.setCustomerName(customerName);
//            paymentEntity.setPaymentDate(modificationDate);
//            paymentEntity.setPaymentDescription(paymentDescription);
//            paymentRepository.save(paymentEntity);
//        } else {
//            System.out.println("There is no payment available with the required id number");
//            return null;
//        }
//    }

}
