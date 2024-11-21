package com.veron.repository;

import com.veron.entity.Spent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpentRepository extends JpaRepository<Spent,Long> {
    Optional<Spent> findByName(String name);
}
