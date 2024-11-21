package com.veron.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryCustomDto {
    private LocalDate dateCreation;
    private String type;
    private Double amount;
    private String paymentMethod;
}
