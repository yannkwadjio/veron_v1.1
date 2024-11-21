package com.veron.entity;

import com.veron.enums.PaymentMethod;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPurchase;
    private String refPurchase;
    private LocalDate purchaseDate;
    private String supplier;
    private List<String> products;
    private String purchaseOder;
    private PaymentMethod paymentMethod;
    private double priceHT;
    private double remise;
    private double totalPrice;
    private String agency;
    private String userCreated;
    private int idDay;
}
