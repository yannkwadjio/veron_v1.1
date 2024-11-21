package com.veron.repository;

import com.veron.entity.MvtSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MvtSalesRepository extends JpaRepository<MvtSales,Long> {
    List<MvtSales> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);
}
