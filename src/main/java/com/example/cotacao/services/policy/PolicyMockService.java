package com.example.cotacao.services.policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;

@Component
public class PolicyMockService {

    @Autowired
    private SqsTemplate sqsTemplate;

    @SqsListener("insurance-quote-received")
    public void createPolicyToQuote(String message){
        var quoteId = getQuoteIdFromMessage(message);
        var policyId = createPolicyId();
        sendPolicyId(quoteId, policyId);
    }

    public Long getQuoteIdFromMessage(String message) {
        var gson = new Gson();
        var data = gson.fromJson(message, CreatePolicyToQuoteDTO.class);
        return data.getQuoteId();
    }

    public Long createPolicyId(){
        long generatedLong = 1L + (long) (Math.random() * (9999L - 1L));
        return generatedLong;
    }

    public Boolean sendPolicyId(Long quoteId, Long policyId){
        var data = new SendPolicyIdDTO(quoteId,policyId);
        var gson = new Gson();
        var message = gson.toJson(data);
        sqsTemplate.send("insurance-policy-created", message);
        return true; 
    }
}
