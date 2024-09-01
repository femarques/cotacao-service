package com.example.cotacao.external_services.catalog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Product {
    private String id;
    private String name;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    private Boolean active;
    private List<String> offers = new ArrayList<String>();   
}
