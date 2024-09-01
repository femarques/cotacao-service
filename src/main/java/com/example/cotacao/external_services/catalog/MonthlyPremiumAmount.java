package com.example.cotacao.external_services.catalog;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MonthlyPremiumAmount {
    @JsonProperty("max_amount")
    private BigDecimal maxAmount;

    @JsonProperty("min_amount")
    private BigDecimal minAmount;

    @JsonProperty("suggested_amount")
    private BigDecimal suggestedAmount;
}