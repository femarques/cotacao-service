package com.example.cotacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cotacao.models.Assistance;

@Repository
public interface AssistanceRepository extends JpaRepository<Assistance, Long>{
    
}
