package com.example.cotacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cotacao.models.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long>{
    
}
