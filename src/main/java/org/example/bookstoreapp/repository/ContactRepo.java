package org.example.bookstoreapp.repository;

import org.example.bookstoreapp.contactUs.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepo extends JpaRepository<Contact, Integer> { }
