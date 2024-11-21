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
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSupplier;
    private String refSupplier;
    private String country;
    private String enterprise;
    private String numberPhone;
    private String email;
    private String adresse;
    private String pointFocal;
    private double balanceCredit;
    private LocalDate dateCreation;
    private String userCreated;
    private int idDay;
}
