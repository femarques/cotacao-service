package com.example.cotacao.services.quote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QuoteDTOReq {
    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("offer_id")
    private String offerId;

    private String category;

    @JsonProperty("total_monthly_premium_amount")
    private BigDecimal totalMonthlyPremiumAmount;
    
    @JsonProperty("total_coverage_amount")
    private BigDecimal totalCoverageAmount;

    private Map<String, BigDecimal> coverages = new HashMap<>();
    private List<String> assistances = new ArrayList<>();
    private CustomerDTOReq customer;
}
