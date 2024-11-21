package com.veron.repository;

import com.veron.entity.SellingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellingServiceRepository extends JpaRepository<SellingService,Long> {
    Optional<SellingService> findByName(String name);
    List<SellingService> findByCategory(String category);

}
