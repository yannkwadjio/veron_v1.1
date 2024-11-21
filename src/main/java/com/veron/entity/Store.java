package com.veron.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idStore;
    private String refStore;
    private String name;
    private String agencies;
    private Set<String> userStores;
    private String userCreated;
}
