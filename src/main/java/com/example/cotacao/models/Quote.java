package com.example.cotacao.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quote {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long insurancePolicyId;
    private String productId;
    private String offerId;
    private String category;
    private BigDecimal totalMonthlyPremiumAmount;
    private BigDecimal totalCoverageAmount;
    
    @OneToMany
    private List<Coverage> coverages = new ArrayList<Coverage>();

    @OneToMany
    private List<Assistance> assistances = new ArrayList<Assistance>();

    @OneToOne
    private Customer customer;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
