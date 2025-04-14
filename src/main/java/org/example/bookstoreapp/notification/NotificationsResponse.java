package org.example.bookstoreapp.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NotificationsResponse {

    @JsonProperty("Massage")
    private String massages;

    @JsonProperty("Creat_At")
    private LocalDateTime createdAt;
}
