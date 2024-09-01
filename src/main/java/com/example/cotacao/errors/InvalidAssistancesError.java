package com.example.cotacao.errors;

public class InvalidAssistancesError extends BusinessError {
    public InvalidAssistancesError() {
        super("Todas as assistências da cotação precisam estar presentes na Oferta");
    }
}
