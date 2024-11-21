package com.veron.repository;


import com.veron.entity.CrCash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CrCashRepository extends JpaRepository<CrCash,Long> {
    Optional<CrCash> findByDateCrCash(LocalDate dateCrCash);
    List<CrCash> findByDateCrCashBetween(LocalDate startDate,LocalDate endDate);
    Optional<CrCash> findByDateCrCashAndCashAndAgency(LocalDate dateCrCash,String cash,String agency);
}
