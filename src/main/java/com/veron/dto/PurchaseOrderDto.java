package com.veron.dto;

import com.veron.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDto {
    private String enterprise;
    private String agency;
    private List<String> products;
    private double priceHT;
    private double tva;
    private double priceTTC;
    private double remise;
    private PaymentMethod paymentMethod;
}
