package com.example.cotacao.external_services.catalog;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class Offer {
    private String id;

    @JsonProperty("product_id")
    private String productId;
    
    private String name;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    private Boolean active;
    private Map<String, BigDecimal> coverages = new Hashtable<String, BigDecimal>();
    private List<String> assistances = new ArrayList<String>();
    
    @JsonProperty("monthly_premium_amount")
    private MonthlyPremiumAmount monthlyPremiumAmount;
}
