package com.veron.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellingService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idService;
    private String name;
    private String description;
    private double price;
    private double balanceCredit;
    private String userCreated;
    private String enterprise;
    private String category;

}
