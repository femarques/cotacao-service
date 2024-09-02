package com.example.cotacao.services.quote;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.cotacao.external_services.catalog.Offer;
import com.example.cotacao.external_services.catalog.Product;
import com.example.cotacao.models.Assistance;
import com.example.cotacao.models.Coverage;
import com.example.cotacao.models.Customer;

public class QuoteServiceTest {
    @Test
    void testValidateAssistances_when_quoteAssistancesMatchOfferAssistances() {
        // Arrange
        var quoteAssistances = new ArrayList<String>(List.of("assist1", "assist2"));
        var offerAssistances = new ArrayList<String>(List.of("assist1", "assist2", "assist3"));
        // Act
        var result = QuoteService.validateAssistances(quoteAssistances, offerAssistances);
        // Assert
        assertTrue(result);
    }

    @Test
    void testValidateAssistances_when_quoteAssistancesDontMatchOfferAssistances() {
        // Arrange
        var quoteAssistances = new ArrayList<String>(List.of("assist1", "assist5"));
        var offerAssistances = new ArrayList<String>(List.of("assist1", "assist2", "assist3"));
        // Act
        var result = QuoteService.validateAssistances(quoteAssistances, offerAssistances);
        // Assert
        assertFalse(result);
    }

    @Test
    void testValidateCoverageTotalAmount_whenOk() {
        // Arrange
        var quoteCoverages = new HashMap<String, BigDecimal>();
        quoteCoverages.put("coverage1", BigDecimal.valueOf(100));
        quoteCoverages.put("coverage2", BigDecimal.valueOf(200));
        quoteCoverages.put("coverage3", BigDecimal.valueOf(300));
        var totalSum = BigDecimal.valueOf(600);

        // Act
        var result = QuoteService.validateCoverageTotalAmount(quoteCoverages, totalSum);

        // Assert
        assertTrue(result);
    }

    @Test
    void testValidateCoveragesAreValid_whenOK() {
        // Arrange
        var quoteCoverages = new HashMap<String, BigDecimal>();
        quoteCoverages.put("coverage1", BigDecimal.valueOf(100));
        quoteCoverages.put("coverage2", BigDecimal.valueOf(200));

        var offerCoverages = new HashMap<String, BigDecimal>();
        offerCoverages.put("coverage1", BigDecimal.valueOf(200));
        offerCoverages.put("coverage2", BigDecimal.valueOf(201));

        // Act
        var result = QuoteService.validateCoveragesAreValid(quoteCoverages, offerCoverages);

        // Assert
        assertTrue(result);
    }

    @Test
    void testValidateCoveragesAreValid_whenInvalidCoverage() {
        // Arrange
        var quoteCoverages = new HashMap<String, BigDecimal>();
        quoteCoverages.put("coverage3", BigDecimal.valueOf(100));
        quoteCoverages.put("coverage2", BigDecimal.valueOf(200));

        var offerCoverages = new HashMap<String, BigDecimal>();
        offerCoverages.put("coverage1", BigDecimal.valueOf(200));
        offerCoverages.put("coverage2", BigDecimal.valueOf(201));

        // Act
        var result = QuoteService.validateCoveragesAreValid(quoteCoverages, offerCoverages);

        // Assert
        assertFalse(result);
    }

    @Test
    void testValidateCoveragesAreValid_whenValuesExceedLimit() {
        // Arrange
        var quoteCoverages = new HashMap<String, BigDecimal>();
        quoteCoverages.put("coverage1", BigDecimal.valueOf(201));
        quoteCoverages.put("coverage2", BigDecimal.valueOf(200));

        var offerCoverages = new HashMap<String, BigDecimal>();
        offerCoverages.put("coverage1", BigDecimal.valueOf(200));
        offerCoverages.put("coverage2", BigDecimal.valueOf(201));

        // Act
        var result = QuoteService.validateCoveragesAreValid(quoteCoverages, offerCoverages);

        // Assert
        assertFalse(result);
    }

    @Test
    void testValidateOfferIsActive_whenTrue() {
        // Arrange
        var offer = new Offer();
        offer.setActive(true);

        // Act
        var result = QuoteService.validateOfferIsActive(offer);

        // Assert
        assertTrue(result);
    }

    @Test
    void testValidateOfferIsActive_whenFalse() {
        // Arrange
        var offer = new Offer();
        offer.setActive(false);

        // Act
        var result = QuoteService.validateOfferIsActive(offer);

        // Assert
        assertFalse(result);
    }

    @Test
    void testValidateProductIsActive_whenTrue() {
        // Arrange
        var product = new Product();
        product.setActive(true);

        // Act
        var result = QuoteService.validateProductIsActive(product);

        // Assert
        assertTrue(result);
    }

    @Test
    void testValidateProductIsActive_whenFalse() {
        // Arrange
        var product = new Product();
        product.setActive(false);

        // Act
        var result = QuoteService.validateProductIsActive(product);

        // Assert
        assertFalse(result);
    }

    @Test
    void testValidateTotalMonthlyPremium_whenEqualsMin() {
        // Arrange
        var premium = BigDecimal.valueOf(100);
        var min_premium = BigDecimal.valueOf(100);
        var max_premium = BigDecimal.valueOf(200);

        // Act
        var result = QuoteService.validateTotalMonthlyPremium(premium, min_premium, max_premium);

        // Assert
        assertTrue(result);
    }

    @Test
    void testValidateTotalMonthlyPremium_whenEqualsMax() {
        // Arrange
        var premium = BigDecimal.valueOf(200);
        var min_premium = BigDecimal.valueOf(100);
        var max_premium = BigDecimal.valueOf(200);

        // Act
        var result = QuoteService.validateTotalMonthlyPremium(premium, min_premium, max_premium);

        // Assert
        assertTrue(result);
    }

    @Test
    void testValidateTotalMonthlyPremium_whenBetweenMinAndMax() {
        // Arrange
        var premium = BigDecimal.valueOf(150);
        var min_premium = BigDecimal.valueOf(100);
        var max_premium = BigDecimal.valueOf(200);

        // Act
        var result = QuoteService.validateTotalMonthlyPremium(premium, min_premium, max_premium);

        // Assert
        assertTrue(result);
    }

    @Test
    void testValidateTotalMonthlyPremium_whenLowerThanMin() {
        // Arrange
        var premium = BigDecimal.valueOf(99);
        var min_premium = BigDecimal.valueOf(100);
        var max_premium = BigDecimal.valueOf(200);

        // Act
        var result = QuoteService.validateTotalMonthlyPremium(premium, min_premium, max_premium);

        // Assert
        assertFalse(result);
    }

    @Test
    void testValidateTotalMonthlyPremium_whenHigherThanMax() {
        // Arrange
        var premium = BigDecimal.valueOf(201);
        var min_premium = BigDecimal.valueOf(100);
        var max_premium = BigDecimal.valueOf(200);

        // Act
        var result = QuoteService.validateTotalMonthlyPremium(premium, min_premium, max_premium);

        // Assert
        assertFalse(result);
    }

    @Test
    void testCreateAssistances() {
        // Arrange
        var assistances = new ArrayList<String>(List.of("assist1", "assis2", "assist3"));

        // Act
        var result = QuoteService.createAssistances(assistances);

        // Assert
        assertTrue(result instanceof List<Assistance>);
        assertTrue(result.size() == 3);
        assertTrue(result.get(0).getName().equals("assist1"));
        assertTrue(result.get(1).getName().equals("assis2"));
        assertTrue(result.get(2).getName().equals("assist3"));
    }

    @Test
    void testCreateCoverages() {
        // Arrange
        var coverages = new HashMap<String, BigDecimal>();
        coverages.put("coverage1", BigDecimal.valueOf(100));
        coverages.put("coverage2", BigDecimal.valueOf(200.05));

        // Act
        var result = QuoteService.createCoverages(coverages);

        // Assert
        assertTrue(result instanceof List<Coverage>);
        assertTrue(result.size() == 2);
    }

    @Test
    void testCreateCustomer() {
        // Arrange
        var customer = new CustomerDTOReq("111", "name", "type", "gender", LocalDate.of(2000, 1, 1), "111", 1111L);
        
        // Act
        var result = QuoteService.createCustomer(customer);

        // Assert
        assertTrue(result instanceof Customer);
        assertTrue(result.getDateOfBirth().equals(customer.getDateOfBirth()));
        assertTrue(result.getDocumentNumber().equals(customer.getDocumentNumber()));
        assertTrue(result.getName().equals(customer.getName()));
        assertTrue(result.getType().equals(customer.getType()));
        assertTrue(result.getGender().equals(customer.getGender()));
        assertTrue(result.getEmail().equals(customer.getEmail()));
        assertTrue(result.getPhoneNumber().equals(customer.getPhoneNumber()));
    }
}
