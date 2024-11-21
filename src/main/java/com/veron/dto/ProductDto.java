package com.veron.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String name;
    private String category;
    private double purchasePrice;
    private double sellingPrice;
    private String enterprise;
    private String supplier;

}
