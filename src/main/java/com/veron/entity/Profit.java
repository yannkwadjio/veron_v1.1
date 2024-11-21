package com.veron.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProfit;
    private LocalDate profitDate;
    private double montant;
    private String agency;
    private String enterprise;
}
