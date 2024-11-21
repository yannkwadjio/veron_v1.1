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
public class MvtCash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMvtCash;
    private LocalDate dateMvtCash;
    private String refOperationCash;
    private String type;
    private String reference;
    private String motif;
    private String sens;
    private double balanceBefore;
    private double amount;
    private double fee;
    private double balanceAfter;
    private String cash;
   private String agency;
   private boolean isValidated;
   private int idDay;
    private String userCreated;

}
