package com.veron.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private String customer;
    private String refInvoice;
    private double amount;
    private String paymentMethod;
    private String receiveAccount;
    private String refCash;
    private double payment;
    private String motif;
}
