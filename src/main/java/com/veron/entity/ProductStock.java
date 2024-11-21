package com.veron.entity;

import com.veron.enums.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProductStock;
    private String agency;
    private String refProduct;
    private Category category;
    private String name;
    private double purchasePrice;
    private double finalStock;
    private String store;
    private String lot;
    private LocalDate dateExpiration;
}
