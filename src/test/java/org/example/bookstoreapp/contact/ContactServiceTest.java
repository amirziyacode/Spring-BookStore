package org.example.bookstoreapp.contact;

import org.example.bookstoreapp.dto.ContactDTO;
import org.example.bookstoreapp.mapper.ContactMapper;
import org.example.bookstoreapp.mapper.ContactMapperImpl;
import org.example.bookstoreapp.notification.Notification;
import org.example.bookstoreapp.notification.NotificationService;
import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepo contactRepo;


    @Mock
    private UserRepo userRepo;


    @InjectMocks
    private ContactService contactService;

    @Mock
    private NotificationService notificationService;

    private ContactDTO mockContactDTO;
    private Contact mockContact;
    private ContactResponse mockContactResponse;
    private User user;

    private ContactMapper contactMapper;

    @BeforeEach
    void setUp() {
        userRepo.deleteAll();
        contactMapper = new ContactMapperImpl();

        mockContactDTO = ContactDTO.builder()
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

        mockContactResponse = ContactResponse.builder()
                .massages("your massage has been added successfully")
                .createdAt(LocalDateTime.now())
                .build();

        user = User.builder()
                .email("email")
                .build();

        userRepo.save(user);
    }

    @Test
    @DisplayName("save_contact")
    void add_Contact_should_be_return_contactResponse_have_notifications_and_added() {
        when(userRepo.findByEmail(mockContact.getEmail())).thenReturn(Optional.of(user));
        ContactResponse contactResponse = contactService.addMassage(mockContactDTO);


        assertThat(contactResponse.getMassages()).isEqualTo(mockContactResponse.getMassages());

        verify(notificationService,times(1)).createNotification(any(Notification.class));
        verify(contactRepo,times(1)).save(any(Contact.class));
        verify(userRepo, times(1)).findByEmail(mockContact.getEmail());
    }


    @Test
    void add_contact_should_be_throw_exception_when_user_not_found() {
        when(userRepo.findByEmail("email")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contactService.addMassage(mockContactDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
    }

    @Test
    void should_get_All_massages_by_email() {
        String email = "email";

        List<Contact> contacts = List.of(mockContact);
        // mapper for contactDTO
        List<ContactDTO> contactDTOS = contactMapper.contactToContactDTOList(contacts);

        when(contactRepo.findByUserEmail(email)).thenReturn(Optional.of(contacts));

        Optional<List<Contact>> result = contactRepo.findByUserEmail(email);

        assertThat(result).isNotNull();
        assertThat(result.orElseThrow()).hasSize(1);
        assertThat(contactDTOS.get(0).getMassage()).isEqualTo(contacts.get(0).getMessage());
        assertThat(result.get()).containsExactly(mockContact);
    }

    @Test
    void get_all_massages_by_email_should_throw_exception() {

        when(contactRepo.findByUserEmail("email")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contactService.getAllMassages("email"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Not found contact for this email");
    }
}