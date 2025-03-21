package org.example.bookstoreapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.repository.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepo bookRepo;

    List<Book> findAll() {
            return bookRepo.findAll();
    }
}
