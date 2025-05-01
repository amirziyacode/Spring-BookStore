package org.example.bookstoreapp.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BookService {

    private final BookRepo bookRepo;

    public Page<Book> getAllBooks(int pageNumber,int perPage) {
        Pageable pageable = PageRequest.of(pageNumber,perPage);
        return bookRepo.findAll(pageable);
    }

    public List<Book> findByCategory(String category) {
        try {
            Category cat = Category.valueOf(category.toUpperCase());
            return bookRepo.findByCategory(cat).orElse(Collections.emptyList());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

    public Book findById(int id) {
        if(bookRepo.findById(id).isPresent()){
            return bookRepo.findById(id).get();
        }else {
            throw new IllegalArgumentException("No book found for id " + id);
        }
    }

    public List<Book> findBestSeller(int books) {
       return bookRepo.findAll().stream().filter(Book::isBestseller).limit(books).collect(Collectors.toList());
    }
}
