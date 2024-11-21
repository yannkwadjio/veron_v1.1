package com.veron.repository;

import com.veron.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    Optional<Supplier> findByNumberPhone(String numberPhone);
    Optional<Supplier> findByEmail(String email);
    Optional<Supplier> findByEnterprise(String enterprise);
}
