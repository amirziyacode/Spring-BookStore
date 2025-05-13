package org.example.bookstoreapp.contact;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class ContactRepoTest {

    @Mock
    private ContactRepo contactRepo;

    @Test
    void find_all_contacts_by_email() {
       Contact mockContact = Contact.builder()
                .subject("Order")
                .message("Hello World")
                .email("email")
                .fullName("John Doe")
                .build();
        when(contactRepo.findByUserEmail("email")).thenReturn(Optional.of(List.of(mockContact)));

        Optional<List<Contact>> email = contactRepo.findByUserEmail("email");

        assertNotNull(email.orElse(null));
        Assertions.assertThat(email.orElse(null).get(0)).isEqualTo(mockContact);
    }
}