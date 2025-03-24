package org.example.bookstoreapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.book.Category;
import org.example.bookstoreapp.repository.BookRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BookService {

    private final BookRepo bookRepo;

    public List<Book> getAllBooks() {
            return bookRepo.findAll();
    }
    public List<Book> findByCategory(String category) {
        for(Category cat:Category.values()){
            if(String.valueOf(cat).equals(category.toUpperCase())){
                return bookRepo.findByCategory(cat).orElse(null);
            }
        }
        throw new IllegalArgumentException("No book found for category " + category);
    }
}
