package com.veron.repository;

import com.veron.entity.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CashRepository extends JpaRepository<Cash,Long> {
    Optional<Cash> findByRefCash(String refCash);
}
