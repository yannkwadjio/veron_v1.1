package com.veron.repository;

import com.veron.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<Agency,Long> {
    Optional<Agency> findByName(String name);
}
