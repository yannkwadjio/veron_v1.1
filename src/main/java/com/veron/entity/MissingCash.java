package com.veron.entity;

import com.veron.enums.StatutMissing;
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
public class MissingCash {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMissing;
  private String refMissingCash;
  private String agency;
  private LocalDate dateCreation;
  private String  responsible;
  private double amount;
  private double advance;
  private double rest;
  private double balanceCredit;
  private StatutMissing statut;
}
