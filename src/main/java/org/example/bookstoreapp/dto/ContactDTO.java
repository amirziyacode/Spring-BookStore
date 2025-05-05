package org.example.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bookstoreapp.contact.ContactStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    private String fullName;
    private String email;
    private String subject;
    private String massage;
    private ContactStatus status;
    private LocalDateTime createdAt;
}
