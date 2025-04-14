package org.example.bookstoreapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.book.Category;
import org.example.bookstoreapp.repository.BookRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

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
        for(Category cat:Category.values()){
            if(String.valueOf(cat).equals(category.toUpperCase())){
                return bookRepo.findByCategory(cat).orElse(null);
            }
        }
        throw new IllegalArgumentException("No book found for category " + category);
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
