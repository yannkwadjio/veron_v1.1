package com.veron.entity;

import com.veron.enums.PaymentMethod;
import com.veron.enums.StatutPurchase;
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
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPurchaseOrder;
    private String refPurchaseOrder;
    private String enterprise;
    private String agency;
    @Column(length = 5000)
    private List<String> products;
    private double priceHT;
    private double tva;
    private double priceTTC;
    private double remise;
    private PaymentMethod paymentMethod;
    private StatutPurchase statutPurchase;
    private String userCreated;
    private LocalDate dateCreation;
    private double balanceCredit;
}
