package org.example.bookstoreapp.repository;

import org.example.bookstoreapp.Models.book.jwtToken.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
}
