package com.accenture.payment.processor.payment;

import com.accenture.model.PaymentEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {
    //  Find a payment by id
    private final PaymentService paymentService;
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public PaymentEntity findPaymentById(@PathVariable @Validated Integer id){
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
    public Integer deletePaymentById(@PathVariable @Validated Integer id){
        return paymentService.deletePaymentById(id);
    }

    // Update a payment by id
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public PaymentEntity updatePaymentById(@PathVariable @Validated Integer id,
                                           @RequestBody PaymentEntity paymentEntity){
        return paymentService.updatePaymentById(id, paymentEntity);
    }

    // Adds a new payment
    @PostMapping
    @Secured("ROLE_ADMIN")
    public PaymentEntity insertNewPayment(@RequestBody PaymentEntity paymentEntity) {
        return paymentService.insertNewPayment(paymentEntity);
    }


}
