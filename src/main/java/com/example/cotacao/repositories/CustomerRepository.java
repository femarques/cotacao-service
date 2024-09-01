package com.example.cotacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cotacao.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
