package com.veron.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MvtPurchaseDto {
    private String supplier;
    private String product;
    private String purchaseOder;
    private double quantity;
    private double purchasePrice;
    private double remise;
    private String category;
    private double stockFinal;
    private String  store;
    private double totalPrice;
}
