package com.example.cotacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cotacao.models.Coverage;

@Repository
public interface CoverageRepository extends JpaRepository<Coverage, Long>{
    
}
