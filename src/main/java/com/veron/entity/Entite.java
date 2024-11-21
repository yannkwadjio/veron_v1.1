package com.veron.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Entite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Set<String> entite;
}
