package org.example.bookstoreapp.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ContactResponse {

    @JsonProperty("Massage")
    private String massages;

    @JsonProperty("Creat_At")
    private LocalDateTime createdAt;
}
