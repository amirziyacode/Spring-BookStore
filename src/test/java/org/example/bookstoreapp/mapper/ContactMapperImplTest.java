package org.example.bookstoreapp.mapper;

import org.assertj.core.api.Assertions;
import org.example.bookstoreapp.contact.Contact;
import org.example.bookstoreapp.dto.ContactDTO;
import org.example.bookstoreapp.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ContactMapperImplTest {

    private ContactMapper contactMapper;
    private ContactDTO contactDTO;
    private Contact mockContact;
    @BeforeEach
    void setUp() {
        contactMapper = new ContactMapperImpl();
        contactDTO = ContactDTO.builder()
                .subject("Order")
                .massage("Hello World")
                .email("email")
                .fullName("John Doe")
                .build();
         mockContact = Contact.builder()
                .subject("Order")
                .message("Hello World")
                .email("email")
                .fullName("John Doe")
                .build();
    }

    @Test
    void contactDto_To_Contact() {
        User user =  User.builder()
                .email("email")
                .build();
        Contact contact = contactMapper.contactDtoToContact(contactDTO, user);


        assertThat(contact.getSubject()).isEqualTo(contactDTO.getSubject());
        assertThat(contact.getFullName()).isEqualTo(contactDTO.getFullName());
        assertThat(contact.getEmail()).isEqualTo(contactDTO.getEmail());
        assertThat(contact.getMessage()).isEqualTo(contactDTO.getMassage());
        assertThat(contact.getFullName()).isEqualTo(contactDTO.getFullName());
    }

    @Test
    void contact_To_ContactDTOList() {
        List<ContactDTO> contactDTOS = contactMapper.contactToContactDTOList(List.of(mockContact));

        assertThat(contactDTOS.size()).isEqualTo(1);
        assertThat(contactDTOS.get(0).getSubject()).isEqualTo(contactDTO.getSubject());
        assertThat(contactDTOS.get(0).getFullName()).isEqualTo(contactDTO.getFullName());
        assertThat(contactDTOS.get(0).getEmail()).isEqualTo(contactDTO.getEmail());
        assertThat(contactDTOS.get(0).getMassage()).isEqualTo(contactDTO.getMassage());
    }
}