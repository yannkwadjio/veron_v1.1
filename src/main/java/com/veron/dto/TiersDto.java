package com.veron.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TiersDto {
    private String type;
    private String country;
    private String enterprise;
    private String fullName;
    private String adresse;
    private String phoneNumber;
    private String email;
    private String pointFocal;
}
