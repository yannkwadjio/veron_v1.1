package com.veron.entity;

import com.veron.enums.Category;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProduct;
    private String refProduct;
    private String name;
    private Category category;
    private double purchasePrice;
    private double sellingPrice;
    private double unitCost;
    private double finalStock;
    private double finalValue;
    private int numeroSerie;
    private String enterprise;
    private String store;
    private String userCreated;
}
