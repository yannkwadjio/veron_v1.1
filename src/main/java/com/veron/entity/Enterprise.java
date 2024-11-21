package com.veron.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEnterprise;
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
    private String userCreated;
    private String entity;
    private String country;
}
