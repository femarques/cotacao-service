package com.example.cotacao.errors;

public class ProductNotFoundError extends BusinessError{

    public ProductNotFoundError() {
        super("Produto não encontrado.");
    }
    
}
