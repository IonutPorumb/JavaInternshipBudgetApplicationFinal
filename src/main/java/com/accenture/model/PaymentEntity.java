package com.accenture.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="payment_entities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer paymentId;
    @Column(nullable = false, name = "payment_date")
    @NotNull
    private LocalDateTime paymentDate;
    @Column(nullable = false, name = "customer_name")
    @NotNull
    private String CustomerName;
    @Column(nullable = false, name = "payment_amount")
    @NotNull
    private Double paymentAmount;
    @Column(nullable = false, name = "payment_description")
    @NotNull
    private String paymentDescription;
}
