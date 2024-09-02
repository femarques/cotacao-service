package com.example.cotacao.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyCreatedQueueListenerTest {
    @Test
    void testParseMessage_whenOK() {
        // Arrange
        var message = "{\"quoteId\": 10, \"policyId\": 30}";

        // Act
        var result = PolicyCreatedQueueListener.parseMessage(message);

        // Assert
        assertTrue(result.getPolicyId() == 30);
        assertTrue(result.getQuoteId() == 10);
    }

    @Test
    void testParseMessage_whenMalFormed() {
        // Arrange
        var message = "{\"quoteId\"";

        // Act
        var result = PolicyCreatedQueueListener.parseMessage(message);

        // Assert
        assertTrue(result == null);
    }
}