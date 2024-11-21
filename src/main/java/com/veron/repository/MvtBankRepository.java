package com.veron.repository;

import com.veron.entity.MvtBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MvtBankRepository extends JpaRepository<MvtBank,Long> {
}
