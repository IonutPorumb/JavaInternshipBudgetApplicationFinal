package com.accenture.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PaymentValidator implements ConstraintValidator<ValidPayment, PaymentEntity> {
    @Override
    public boolean isValid(PaymentEntity paymentEntity, ConstraintValidatorContext constraintValidatorContext) {
        boolean amountValid = paymentEntity.getPaymentAmount() > 0;
        boolean userIdValid = paymentEntity.getPaymentId() > 0;
        if (!amountValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Introduced amount is negative!")
                    .addConstraintViolation();
        }
        if (!userIdValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("User id must be a positive integer number!")
                    .addConstraintViolation();
        }
        return amountValid && userIdValid;
    }
}
