package com.example.cotacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cotacao.models.Quote;
import com.example.cotacao.services.quote.QuoteDTOReq;
import com.example.cotacao.services.quote.QuoteService;

import lombok.AllArgsConstructor;



@RestController
@RequestMapping(path="/quotes")
@AllArgsConstructor
public class QuoteController {
    @Autowired
    private QuoteService quoteService;

    @PostMapping
    public ResponseEntity<Quote> createQuote(@RequestBody @Validated QuoteDTOReq data){
        // ResponseEntity<Quote> response = ResponseEntity.created(null)
        return ResponseEntity.created(null).body(quoteService.createQuote(data)); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> getQuote(@PathVariable Long id) {
        return ResponseEntity.ok().body(quoteService.getQuoteById(id));
    }

}
