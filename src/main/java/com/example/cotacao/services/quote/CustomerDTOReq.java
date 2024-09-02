package com.example.cotacao.services.quote;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTOReq {
    @JsonProperty("document_number")
    private String documentNumber;

    private String name;
    private String type;
    private String gender;
    
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;
    private String email;

    @JsonProperty("phone_number")
    private Long phoneNumber;
}
