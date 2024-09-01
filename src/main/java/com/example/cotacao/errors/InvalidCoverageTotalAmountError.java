package com.example.cotacao.errors;

public class InvalidCoverageTotalAmountError extends BusinessError {
    public InvalidCoverageTotalAmountError() {
        super("O valor total de cobertura informado não corresponde à soma das coberturas da cotação.");
    }
}
