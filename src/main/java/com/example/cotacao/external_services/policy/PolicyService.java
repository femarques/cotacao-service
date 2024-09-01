package com.example.cotacao.external_services.policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.awspring.cloud.sqs.operations.SqsTemplate;

@Component
public class PolicyService {
    
    @Autowired
    private SqsTemplate sqsTemplate;

    public Boolean createPolicyForQuote(Long quoteId) {
        var message = this.createMessage(quoteId);
        sqsTemplate.send("insurance-quote-received", message);
        return true;
    }

    public String createMessage(Long quoteId) {
        var gson = new Gson();
        var message = new CreatePolicyForQuoteDTO(quoteId);
        return gson.toJson(message);
    }
}
