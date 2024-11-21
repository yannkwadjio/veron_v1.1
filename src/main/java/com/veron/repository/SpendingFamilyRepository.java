package com.veron.repository;

import com.veron.entity.SpendingFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpendingFamilyRepository extends JpaRepository<SpendingFamily,Long> {
    Optional<SpendingFamily> findByName(String name);
}
