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
@Table(name = "client")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCustomer;
    private String refCustomer;
    private String fullName;
    private String adresse;
    private String phoneNumber;
    private String email;
    private double depot;
    private double retrait;
    private double balance;
    private double balanceCredit;
    private String userCreated;
    private int idDay;
    private LocalDate dateCreation;
    private String enterprise;
    private String country;
    private String type;
}
