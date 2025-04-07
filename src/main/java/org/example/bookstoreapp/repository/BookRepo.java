package org.example.bookstoreapp.repository;


import lombok.NonNull;
import org.example.bookstoreapp.Models.book.Book;
import org.example.bookstoreapp.Models.book.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    @NonNull
     Page<Book> findAll(@NonNull Pageable booksPageable);
    Optional<List<Book>> findByCategory(Category category);
}

