package com.veron.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpendingFamily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFamily;
    private String refFamily;
    private String name;
    private String userCreated;
    private int numeroSerie;
}
