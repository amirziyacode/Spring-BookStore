package org.example.bookstoreapp.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Integer> {
   Optional<List<Contact>> findByUserEmail(String email);
}
