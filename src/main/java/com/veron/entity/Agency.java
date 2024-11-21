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
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAgency;
    private String refAgency;
    private String name;
    private double balanceCredit;
    private double balance;
    private int numeroAgency;
    private LocalDate dateCreation;
    private String userCreated;
    private String entity;
    private String city;
    private String region;
    private String enterprise;
    private String country;

}
