package com.veron.entity;

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
public class CrCash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCrCash;
    private LocalDate dateCrCash;
    private int nb10000;
    private int nb5000;
    private int nb2000;
    private int nb1000;
    private int nb500;
    private int nb100;
    private int nb50;
    private int nb25;
    private int nb10;
    private int nb5;
    private int nb2;
    private int nb1;
    private int totalCash;
    private int soldeInitial;
    private int encaissement;
    private int decaissement;
    private int soldeFinal;
    private int difference;
    private String cash;
    private String agency;
}
