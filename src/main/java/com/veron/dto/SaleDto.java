package com.veron.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleDto {
    private String refCashValue;
    private String paymentMethod;
    private String customerValue;
    private String totalPrice;
    private String remise;
    private String serviceValue;
    private double tvaValues;
    private List<String> products;
    private String store;
}
