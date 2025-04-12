package org.example.bookstoreapp.contactUs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ContactResponse {

    @JsonProperty("Massage")
    private String massages;

    @JsonProperty("Creat_At")
    private LocalDate createdAt;
}
