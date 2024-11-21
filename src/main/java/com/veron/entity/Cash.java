package com.veron.entity;

import com.veron.enums.CasherRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCash;
    private String refCash;
    private double balance;
    private double balanceCredit;
    private int numeroCash;
    private LocalDate dateCreation;
    private String userCreated;
    private CasherRole casherRole;
    private String Enterprise;
    private String entity;
    private String users;
    private String agency;
}
