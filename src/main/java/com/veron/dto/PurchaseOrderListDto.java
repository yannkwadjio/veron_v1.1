package com.veron.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderListDto {
    private String refProduct;
    private String product;
    private double quantity;
    private double price;
    private double totalPrice;
}
