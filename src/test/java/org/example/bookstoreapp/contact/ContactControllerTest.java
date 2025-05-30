package org.example.bookstoreapp.contact;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookstoreapp.modelDTO.ContactDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value =ContactController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = {ContactController.class, ContactService.class})
class ContactControllerTest {

    @MockBean
    private ContactService contactService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ContactResponse contactResponse;

    private ContactDTO mockContact;

    @BeforeEach
    void setUp() {
        mockContact = ContactDTO.builder()
                .subject("Order")
                .message("Hello World")
                .email("email")
                .status(ContactStatus.PENDING)
                .fullName("John Doe")
                .build();
        contactResponse = ContactResponse.builder()
                .massages("your massage has been added successfully")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void add_contact_success() throws Exception {


        when(contactService.addMassage(mockContact)).thenReturn(contactResponse);

        mockMvc.perform(post("http://localhost:8080/api/contact/addContact")
                        .content(objectMapper.writeValueAsString(mockContact))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("Massage").value("your massage has been added successfully"));
    }

    @Test
    void get_all_massage_by_email() throws Exception {
        when(contactService.getAllMassages("email")).thenReturn(List.of(mockContact));

        mockMvc.perform(get("http://localhost:8080/api/contact/getMyMassages")
                .param("email", "email")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].fullName").value(mockContact.getFullName()))
                .andExpect(jsonPath("[0].message").value(mockContact.getMessage()))
                .andExpect(jsonPath("[0].subject").value(mockContact.getSubject()))
                .andExpect(jsonPath("[0].status").value(String.valueOf(ContactStatus.PENDING)))
                .andExpect(jsonPath("[0].email").value(mockContact.getEmail()));
    }
}