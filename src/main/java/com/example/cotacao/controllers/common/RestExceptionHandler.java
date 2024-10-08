package com.example.cotacao.controllers.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cotacao.errors.BusinessError;
import com.example.cotacao.errors.QuoteNotFoundError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(QuoteNotFoundError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Void> handleNotFound(QuoteNotFoundError exception, HttpServletRequest request){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BusinessError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorPayload> handleBusinessError(BusinessError exception, HttpServletRequest request){
        return ResponseEntity.badRequest().body(new ErrorPayload(exception.getMessage()));
    }
    
}
