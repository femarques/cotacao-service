package com.example.cotacao.services.quote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cotacao.errors.InactiveOfferError;
import com.example.cotacao.errors.InactiveProductError;
import com.example.cotacao.errors.InvalidAssistancesError;
import com.example.cotacao.errors.InvalidCoverageTotalAmountError;
import com.example.cotacao.errors.InvalidCoveragesError;
import com.example.cotacao.errors.InvalidMonthlyTotalPremiumError;
import com.example.cotacao.errors.OfferNotFoundError;
import com.example.cotacao.errors.ProductNotFoundError;
import com.example.cotacao.errors.QuoteNotFoundError;
import com.example.cotacao.external_services.catalog.CatalogService;
import com.example.cotacao.external_services.catalog.Offer;
import com.example.cotacao.external_services.catalog.Product;
import com.example.cotacao.external_services.policy.PolicyService;
import com.example.cotacao.models.Assistance;
import com.example.cotacao.models.Coverage;
import com.example.cotacao.models.Customer;
import com.example.cotacao.models.Quote;
import com.example.cotacao.repositories.AssistanceRepository;
import com.example.cotacao.repositories.CoverageRepository;
import com.example.cotacao.repositories.CustomerRepository;
import com.example.cotacao.repositories.QuoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuoteService {
    @Autowired
    private final QuoteRepository quoteRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final AssistanceRepository assistanceRepository;

    @Autowired
    private final CoverageRepository coverageRepository;

    private final CatalogService catalogService;
    private final PolicyService policyService;

    public static Boolean validateAssistances(List<String> quoteAssistances, List<String> offerAssistances) {
        return offerAssistances.containsAll(quoteAssistances);
    }

    public static Boolean validateCoverageTotalAmount(Map<String, BigDecimal> quoteCoverages,
            BigDecimal quoteTotalAmount) {

        var sum = new BigDecimal(0);
        for (BigDecimal value : quoteCoverages.values()) {
            sum = sum.add(value);
        }
        return sum.equals(quoteTotalAmount);
    }

    public static Boolean validateCoveragesAreValid(Map<String, BigDecimal> quoteCoverages,
            Map<String, BigDecimal> offerCoverages) {
        for (Map.Entry<String, BigDecimal> entry : quoteCoverages.entrySet()) {
            var quoteKey = entry.getKey();
            var quoteValue = entry.getValue();
            if (!offerCoverages.keySet().contains(quoteKey)) {
                return false;
            }
            if (quoteValue.compareTo(offerCoverages.get(quoteKey)) == 1) {
                return false;
            }
        }

        return true;
    }

    public static Boolean validateTotalMonthlyPremium(BigDecimal quotePremium, BigDecimal minOfferPremium,
            BigDecimal maxOfferPremium) {
        var lessThanMinimum = quotePremium.compareTo(minOfferPremium) == -1;
        var greaterThanMaximum = quotePremium.compareTo(maxOfferPremium) == 1;

        return (!lessThanMinimum & !greaterThanMaximum);
    }

    public static Boolean validateOfferIsActive(Offer offer) {
        return offer.getActive();
    }

    public static Boolean validateProductIsActive(Product product) {
        return product.getActive();
    }

    public static Customer createCustomer(CustomerDTOReq data) {
        return new Customer(null, data.getDocumentNumber(), data.getName(), data.getType(), data.getGender(),
                data.getDateOfBirth(), data.getEmail(), data.getPhoneNumber());
    }

    public static List<Coverage> createCoverages(Map<String, BigDecimal> coverages) {
        var coveragesList = new ArrayList<Coverage>();
        for (Map.Entry<String, BigDecimal> entry : coverages.entrySet()) {
            coveragesList.add(new Coverage(null, entry.getKey(), entry.getValue()));
        }
        return coveragesList;
    }

    public static List<Assistance> createAssistances(List<String> assistances) {
        var assistancesList = new ArrayList<Assistance>();
        for (String assistance : assistances) {
            assistancesList.add(new Assistance(null, assistance));
        }
        return assistancesList;
    }

    public Quote createQuote(QuoteDTOReq quoteDTO) {
        var product = this.catalogService.getProduct(quoteDTO.getProductId());
        if (product == null){
            throw new ProductNotFoundError();
        }
        
        if (!QuoteService.validateProductIsActive(product)) {
            throw new InactiveProductError();
        }

        var offer = this.catalogService.getOffer(quoteDTO.getOfferId());
        if (offer == null) {
            throw new OfferNotFoundError();
        }

        if (!QuoteService.validateOfferIsActive(offer)) {
            throw new InactiveOfferError();
        }

        if (!QuoteService.validateAssistances(quoteDTO.getAssistances(), offer.getAssistances())) {
            throw new InvalidAssistancesError();
        }

        if (!QuoteService.validateCoveragesAreValid(quoteDTO.getCoverages(), offer.getCoverages())) {
            throw new InvalidCoveragesError();
        }

        if (!QuoteService.validateCoverageTotalAmount(quoteDTO.getCoverages(), quoteDTO.getTotalCoverageAmount())) {
            throw new InvalidCoverageTotalAmountError();
        }

        if (!QuoteService.validateTotalMonthlyPremium(quoteDTO.getTotalMonthlyPremiumAmount(),
                offer.getMonthlyPremiumAmount().getMinAmount(), offer.getMonthlyPremiumAmount().getMaxAmount())) {
            throw new InvalidMonthlyTotalPremiumError();
        }

        var customer = createCustomer(quoteDTO.getCustomer());
        customerRepository.save(customer);

        var coverages = createCoverages(quoteDTO.getCoverages());
        coverageRepository.saveAll(coverages);

        var assistances = createAssistances(quoteDTO.getAssistances());
        assistanceRepository.saveAll(assistances);

        Quote newQuote = new Quote(
                null, null, quoteDTO.getProductId(),
                quoteDTO.getOfferId(), quoteDTO.getCategory(),
                quoteDTO.getTotalMonthlyPremiumAmount(), quoteDTO.getTotalCoverageAmount(),
                coverages, assistances, customer, null, null);

        this.quoteRepository.save(newQuote);
        this.policyService.createPolicyForQuote(newQuote.getId());
        return newQuote;
    }

    public Boolean addPolicyToQuote(Long quoteId, Long policyId) {
        var quote = this.quoteRepository.findById(quoteId).orElseThrow(() -> new QuoteNotFoundError());
        quote.setInsurancePolicyId(policyId);
        this.quoteRepository.save(quote);
        return true;
    }

    public Quote getQuoteById(Long id) {
        return this.quoteRepository.findById(id).orElseThrow(() -> new QuoteNotFoundError());
    }
}
