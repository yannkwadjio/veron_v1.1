package com.veron.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCountry;
    private String name;
    private String userCreated;
    private double balanceCredit;
    private String entity;


}
