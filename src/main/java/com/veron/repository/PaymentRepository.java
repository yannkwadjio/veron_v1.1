package com.veron.repository;

import com.veron.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
 List<Payment> findByDateCreationBetween(LocalDate startDate, LocalDate endDate);
 List<Payment> findAllByCustomerAndDateCreationBetween(String customer,LocalDate startDate, LocalDate endDate);
}
