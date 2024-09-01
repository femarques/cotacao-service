package com.example.cotacao.errors;

public class InvalidCoveragesError extends BusinessError {
    public InvalidCoveragesError() {
        super("Todas as coberturas da cotação precisam ser ofertadas pela Oferta, e seus valores menores ou iguais aos da Oferta.");
    }
}
