//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.accenture.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "person_transactions")
@NoArgsConstructor
@AllArgsConstructor
@ValidTransaction
@Getter
@Setter
public class Transaction extends BaseEntity {
    @Column(nullable = false, name = "creation_date")
    private LocalDateTime createdAt;
    @Column(nullable = false, name = "transaction_confirmation")
    private Boolean confirmed;
    @Column(nullable = false, name = "user_id")
    private Integer userId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "action_type")
    private ActionType type;
    @Column(nullable = false)
    private Double amount;
    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;
    public Product getProduct() {
        return this.product;
    }
    public ActionType getType() {
        return this.type;
    }
    public double getAmount() {
        return this.amount;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public boolean isConfirmed() {
        return this.confirmed;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String toString() {
        return "Transaction{" +
                "id=" + getId() + ", " +
                "product='" + this.product +
                "', createdAt=" + this.createdAt +
                ", confirmed=" + this.confirmed +
                ", userId=" + this.userId +
                ", type=" + this.type +
                ", amount=" + this.amount + "}";
    }
}
