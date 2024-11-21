package com.veron.entity;

import com.veron.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPayment;
    private String refPayment;
    private String customer;
    private String invoice;
    private double amountInvoice;
    private double amount;
    private double rest;
    private PaymentMethod paymentMethod;
    private double balanceCredit;
    private String userCreated;
    private String agency;
    private LocalDate dateCreation;
}
