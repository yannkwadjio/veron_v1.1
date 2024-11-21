package com.veron.repository;

import com.veron.entity.Reset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetRepository extends JpaRepository<Reset,Long> {
    Optional<Reset> findByCount(int count);
}
