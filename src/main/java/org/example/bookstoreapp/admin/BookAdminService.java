package org.example.bookstoreapp.admin;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.book.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookAdminService {
    private final BookRepo bookRepo;

    public List<Book> allBooks() {
        return bookRepo.findAll();
    }
}
