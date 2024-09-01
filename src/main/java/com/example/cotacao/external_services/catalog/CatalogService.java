package com.example.cotacao.external_services.catalog;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class CatalogService {
    private final RestClient client = RestClient.builder()
            .requestFactory(new SimpleClientHttpRequestFactory())
            .baseUrl("http://localhost:81")
            .build();

    public Offer getOffer(String offerId) {
        try {
            return client.get()
            .uri("/offers/{offerId}", offerId)
            .retrieve()
            .body(Offer.class);
            
        } catch (HttpClientErrorException e){
            return null;
        }
    }

    public Product getProduct(String productId) {
        try {
            return client.get()
                .uri("/products/{productId}", productId)
                .retrieve()
                .body(Product.class);
            
        } catch (HttpClientErrorException e){
            return null;
        }
    }
}
