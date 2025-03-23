package org.example.bookstoreapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.book.Category;
import org.example.bookstoreapp.repository.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepo bookRepo;

    public List<Book> getAllBooks() {
            return bookRepo.findAll();
    }
    public List<Book> findByCategory(Category category) {
        if(!(bookRepo.findByCategory(category).isEmpty())) {
            return bookRepo.findByCategory(category);
        }
        throw new IllegalArgumentException("No book found for category " + category);
    }
}
