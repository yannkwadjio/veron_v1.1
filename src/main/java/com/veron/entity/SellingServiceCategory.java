package com.veron.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellingServiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCategoryService;
    private String name;
    private String userCreated;
}
