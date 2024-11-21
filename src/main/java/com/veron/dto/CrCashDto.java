package com.veron.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrCashDto {
    private int nb10000;
    private int nb5000;
    private int nb2000;
    private int nb1000;
    private int nb500;
    private int nb100;
    private int nb50;
    private int nb25;
    private int nb10;
    private int nb5;
    private int nb2;
    private int nb1;
    private int totalCash;
    private int soldeInitial;
    private int encaissement;
    private int decaissement;
    private int soldeFinal;
    private int soldeFinalConf;
    private int difference;
    private String refCash;
    private String responsible;
}
