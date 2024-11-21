package com.veron.entity;

import com.veron.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MvtStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMvtStock;
    private LocalDate dateCreation;
    private String idOperation;
    private String store01;
    private String store02;
    private Category category;
    private String product;
    private double initialStock;
    private double incomingQuantity;
    private double outgoingQuantity;
    private double finalStock;
    private double valueStock;
    private String userCreated;
    private int idDay;

}
