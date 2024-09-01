package com.example.cotacao.errors;

public class InvalidMonthlyTotalPremiumError extends BusinessError {
    public InvalidMonthlyTotalPremiumError() {
        super("O valor mensal informado está fora da faixa aceitável da Oferta.");
    }
}
