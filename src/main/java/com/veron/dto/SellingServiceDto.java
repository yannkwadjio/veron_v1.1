package com.veron.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellingServiceDto {
    private String name;
    private String category;
    private String description;
    private double price;
    private String enterprise;
}
