package com.example.cotacao.services.quote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyCreatedDTO {
    private Long policyId;
    private Long quoteId;
}
