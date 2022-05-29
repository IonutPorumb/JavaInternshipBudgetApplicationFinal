package com.accenture.payment.processor.payment;

import com.accenture.model.PaymentEntity;
import com.accenture.model.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {
    //  Find a payment by id
    private final PaymentService paymentService;
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public PaymentEntity findPaymentById(@PathVariable Integer id){
        return paymentService.findPaymentById(id);
    }

    @GetMapping("/total")
    @Secured("ROLE_USER")
    //  Find the total payment made by a person
    public Double findTotalAmountByCustomerName(@RequestParam String customerName){
        return paymentService.findTotalAmountByCustomerName(customerName);
    }
    // Delete a payment by id
    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public Integer deletePaymentById(@PathVariable Integer id){
        return paymentService.deletePaymentById(id);
    }


}
