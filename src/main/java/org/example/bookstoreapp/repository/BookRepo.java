package org.example.bookstoreapp.repository;

import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.book.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    Optional<Book> findByCategory(Category category);
}

