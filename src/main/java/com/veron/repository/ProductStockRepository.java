package com.veron.repository;

import com.veron.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStock,Long> {
    Optional<ProductStock> findByStoreAndLotAndAgency(String store,String lot,String agency);
}
