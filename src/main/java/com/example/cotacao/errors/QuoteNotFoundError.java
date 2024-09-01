package com.example.cotacao.errors;

public class QuoteNotFoundError extends BusinessError {

    public QuoteNotFoundError() {
        super("Cotação não encontrada.");
    }
    
}
