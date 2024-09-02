package com.example.cotacao.services.policy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyMockServiceTest {
    @Test
    void testCreatePolicyId() {
        // Arrange
        
        // Act
        var result = PolicyMockService.createPolicyId();
        
        // Assert
        assertTrue(result instanceof Long);
    }

    @Test
    void testGetQuoteIdFromMessage() {
        // Arrange
        var message = "{\"quoteId\": 20}";

        // Act
        var result = PolicyMockService.getQuoteIdFromMessage(message);

        // Assert
        assertTrue(result == 20L);
    }
}
