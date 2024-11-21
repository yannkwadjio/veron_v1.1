package com.veron.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Spent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSpent;
    private String refSpent;
    private String name;
    private double balance;
    private double balanceCredit;
    private String spendingFamily;
    private String userCreated;
    private int numeroSerie;

}
