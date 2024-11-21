package com.veron.entity;

import com.veron.enums.InvoiceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idInvoice;
    private LocalDate dateCreation;
    private String refInvoice;
    private String customer;
    private String motif;
    @Column(length = 50000)
    private List<String> products;
    private double amount;
    private double advance;
    private double rest;
    private double balanceCredit;
    private String agency;
    private String userCreated;
    private double profit;
    private InvoiceType invoiceType;
}
