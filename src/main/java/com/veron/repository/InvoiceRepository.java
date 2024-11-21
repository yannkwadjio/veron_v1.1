package com.veron.repository;

import com.veron.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    Optional<Invoice> findByRefInvoice(String refInvoice);
}
