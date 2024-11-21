package com.veron.repository;

import com.veron.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Optional<Store> findByNameAndAgencies(String name,String agencies);
    Optional<Store> findByRefStore(String refStore);
}
