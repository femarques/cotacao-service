package com.example.cotacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.cotacao.services.quote.PolicyCreatedDTO;
import com.example.cotacao.services.quote.QuoteService;
import com.google.gson.Gson;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class PolicyCreatedQueueListener {
    @Autowired
    private QuoteService quoteService;

    @SqsListener("insurance-policy-created")
    public void receivePolicyId(String message) {
        var data = PolicyCreatedQueueListener.parseMessage(message);
        if (data == null) {
            return;
        }
        quoteService.addPolicyToQuote(data.getQuoteId(), data.getPolicyId());
    }

    public static PolicyCreatedDTO parseMessage(String message){
        Gson gson = new Gson();
        try {
            return gson.fromJson(message, PolicyCreatedDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
