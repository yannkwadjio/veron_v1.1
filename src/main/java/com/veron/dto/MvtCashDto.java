package com.veron.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MvtCashDto {
    private String type;
    private String motif;
    private String sens;
    private String reference;
    private double amount;
    private double fee;
    private String cashRef;
    private String description;
}
