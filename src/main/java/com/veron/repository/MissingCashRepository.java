package com.veron.repository;

import com.veron.entity.MissingCash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MissingCashRepository extends JpaRepository<MissingCash,Long> {
    Optional<MissingCash> findByRefMissingCash(String refMissingCash);
    List<MissingCash> findByDateCreationBetween(LocalDate startDate,LocalDate endDate);
    List<MissingCash> findByDateCreation(LocalDate dateCreation);
}
