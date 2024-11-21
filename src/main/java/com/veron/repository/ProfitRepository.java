package com.veron.repository;

import com.veron.entity.Profit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProfitRepository extends JpaRepository<Profit,Long> {
    List<Profit> findByProfitDateBetween(LocalDate startDate,LocalDate endDate);
}
