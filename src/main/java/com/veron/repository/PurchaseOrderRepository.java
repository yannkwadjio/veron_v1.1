package com.veron.repository;

import com.veron.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {
    Optional<PurchaseOrder> findByRefPurchaseOrder(String refPurchaseOrder);
    List<PurchaseOrder> findByDateCreationBetween(LocalDate startDate,LocalDate endDate);
}
