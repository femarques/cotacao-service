package com.example.cotacao.services.policy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendPolicyIdDTO {
    private Long quoteId;
    private Long policyId;
}
