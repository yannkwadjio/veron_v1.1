package com.veron.repository;

import com.veron.entity.SellingServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellingServiceCategoryRepository extends JpaRepository<SellingServiceCategory,Long> {
    Optional<SellingServiceCategory> findByName(String name);
}
