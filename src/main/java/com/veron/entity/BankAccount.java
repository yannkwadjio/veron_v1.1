package com.veron.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBkAccount;
    private String bankAccountNumber;
    private double balance;
    private double balanceCredit;
    private String bank;
    private String entity;
    private String userCreated;
}
