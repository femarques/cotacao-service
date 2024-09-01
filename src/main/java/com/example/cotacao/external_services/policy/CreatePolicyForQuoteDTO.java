package com.example.cotacao.external_services.policy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePolicyForQuoteDTO {
    private Long quoteId;
}
