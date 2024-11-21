package com.veron.entity;

import com.veron.enums.StatusOfOperation;
import com.veron.enums.TypeBank;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MvtBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMvtBank;
    private LocalDate MvtBankDate;
    private String refOperationBank;
    private TypeBank typeBank;
    private String motif;
    private double balanceBefore;
    private double amount;
    private double balanceAfter;
    private double fee;
    private StatusOfOperation statusOfOperation;
    private String bank;
    private String agency;
    private String users;
}
