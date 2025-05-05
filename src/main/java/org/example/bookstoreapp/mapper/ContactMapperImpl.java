package org.example.bookstoreapp.mapper;

import org.example.bookstoreapp.contact.Contact;
import org.example.bookstoreapp.contact.ContactStatus;
import org.example.bookstoreapp.dto.ContactDTO;
import org.example.bookstoreapp.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ContactMapperImpl implements ContactMapper {
    @Override
    public Contact contactDtoToContact(ContactDTO contactDto, User user) {
        return Contact.builder()
                .fullName(contactDto.getFullName())
                .email(contactDto.getEmail())
                .subject(contactDto.getSubject())
                .status(ContactStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .user(user)
                .status(ContactStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .message(contactDto.getMassage())
                .build();
    }

    @Override
    public List<ContactDTO> contactToContactDTOList(List<Contact> contactList) {
        return contactList.stream().map(this::contactToContactDTO).collect(Collectors.toList());
    }

    private ContactDTO contactToContactDTO(Contact contact) {
      return ContactDTO.builder()
              .fullName(contact.getFullName())
              .email(contact.getEmail())
              .subject(contact.getSubject())
              .massage(contact.getMessage())
              .status(contact.getStatus())
              .createdAt(contact.getCreatedAt())
              .build();
    }
}
