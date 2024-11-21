package com.veron.repository;

import com.veron.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise,Long> {

    Optional<Enterprise> findByName(String name);
}
