package com.example.cotacao.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorPayload {
    private String message;
}
