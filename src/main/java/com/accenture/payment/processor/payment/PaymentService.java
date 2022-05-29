package com.accenture.payment.processor.payment;

import com.accenture.model.PaymentEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    // Update a payment by id
    public PaymentEntity updatePaymentById(Integer id, PaymentEntity paymentEntity){
        if (paymentRepository.findById(id).isPresent()) {
            PaymentEntity existingPaymentEntity = paymentRepository.getById(id);
            LocalDateTime modificationDate = LocalDateTime.now();
            existingPaymentEntity.setPaymentAmount(paymentEntity.getPaymentAmount());
            existingPaymentEntity.setCustomerName(paymentEntity.getCustomerName());
            existingPaymentEntity.setPaymentDate(modificationDate);
            existingPaymentEntity.setPaymentDescription(paymentEntity.getPaymentDescription());
            return paymentRepository.save(existingPaymentEntity);
        } else {
            System.out.println("There is no payment available with the required id number");
            return null;
        }
    }

    // Adds a new payment
    public PaymentEntity insertNewPayment(PaymentEntity paymentEntity){
        LocalDateTime modificationDate = LocalDateTime.now();
        PaymentEntity newPayment = new PaymentEntity(paymentEntity.getPaymentId(), modificationDate,
                paymentEntity.getCustomerName(), paymentEntity.getPaymentAmount(),paymentEntity.getPaymentDescription());
        return paymentRepository.save(newPayment);
    }
}
