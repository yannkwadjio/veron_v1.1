package com.veron.repository;

import com.veron.entity.CountLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountLotRepository extends JpaRepository<CountLot,Long> {
Optional<CountLot> findByLot(String lot);
}
