package com.veron.repository;

import com.veron.entity.Entite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntiteRepository extends JpaRepository<Entite,Long> {

}
