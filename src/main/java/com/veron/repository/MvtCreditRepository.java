package com.veron.repository;

import com.veron.entity.MvtCredit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MvtCreditRepository extends JpaRepository<MvtCredit,Long> {
    List<MvtCredit> findByDateCreationBetween(LocalDate startDateCredit, LocalDate endDateCredit);
}
