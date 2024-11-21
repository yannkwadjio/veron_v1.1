package com.veron.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseDto {
    private String country;
    private String name;
    private String email;
    private String phoneNumber;
    private String slogan;
    private String registreCommerce;
    private String boitePostale;
    private String uniqueIdentificationNumber;
    private String fileName;
    private String fileType;
    private double balanceCredit;
}
