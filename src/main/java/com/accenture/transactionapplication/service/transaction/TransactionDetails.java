package com.accenture.transactionapplication.service.transaction;

import lombok.*;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class TransactionDetails {
    private String type;
    private Double amount;
}
