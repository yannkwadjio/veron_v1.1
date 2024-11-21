package com.veron.repository;

import com.veron.entity.MvtStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MvtStockRepository extends JpaRepository<MvtStock,Long> {
    List<MvtStock> findByDateCreationBetween(LocalDate startDateCredit, LocalDate endDateCredit);
}
