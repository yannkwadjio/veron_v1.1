package com.veron.entity;

import com.veron.enums.StatutCredit;
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
public class MvtCredit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMvtCredit;
    private LocalDate dateCreation;
    private String source;
    private String destination;
    private double amount;
    private StatutCredit statutCredit;
    private String userCreated;
}
