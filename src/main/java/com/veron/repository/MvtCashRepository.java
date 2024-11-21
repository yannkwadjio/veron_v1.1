package com.veron.repository;

import com.veron.entity.MvtCash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MvtCashRepository extends JpaRepository<MvtCash,Long> {
    List<MvtCash> findByDateMvtCashBetween(LocalDate startDate,LocalDate endDate);
    Optional<MvtCash> findByrefOperationCash(String refOperationCash);
}
