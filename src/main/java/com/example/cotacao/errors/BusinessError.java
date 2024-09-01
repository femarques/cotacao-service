package com.example.cotacao.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusinessError extends RuntimeException {
    private String message;
}
