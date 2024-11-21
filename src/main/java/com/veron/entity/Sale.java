package com.veron.entity;

import com.veron.enums.PaymentMethod;
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
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSale;
    private String refSale;
    private LocalDate saleDate;
    private String customer;
    private String service;
    @Column(length = 50000)
    private List<String> products;
    private PaymentMethod paymentMethod;
    private double profit;
    private double priceHT;
    private double remise;
    private double tva;
    private double priceTTC;
    private String lotSale;
    private String userCreated;
    private String agency;
    private int idDay;
}
