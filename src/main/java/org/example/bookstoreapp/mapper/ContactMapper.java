package org.example.bookstoreapp.mapper;

import org.example.bookstoreapp.contact.Contact;
import org.example.bookstoreapp.dto.ContactDTO;
import org.example.bookstoreapp.user.User;

import java.util.List;

public interface ContactMapper {
    Contact contactDtoToContact(ContactDTO contactDto, User user);
    List<ContactDTO> contactToContactDTOList(List<Contact> contactList);
}
