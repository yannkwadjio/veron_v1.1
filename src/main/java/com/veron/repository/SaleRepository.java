package com.veron.repository;

import com.veron.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {
    List<Sale> findBySaleDateBetween(LocalDate startDate,LocalDate endDate);
    List<Sale> findAllByCustomerAndSaleDateBetween(String customer,LocalDate startDate,LocalDate endDate);
}
