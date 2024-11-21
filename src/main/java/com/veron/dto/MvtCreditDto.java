package com.veron.dto;

import com.veron.enums.StatutCredit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MvtCreditDto {
    private String entiteSource;
    private String source;
    private String entiteDestination;
    private String destination;
    private double amount;
    private StatutCredit statutCredit;
}
