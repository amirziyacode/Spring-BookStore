package org.example.bookstoreapp.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // => for big text is come from database !!
public class BookService {

    private final BookRepo bookRepo;

    public Page<Book> getAllBooks(int pageNumber,int perPage) {
        Pageable pageable = PageRequest.of(pageNumber,perPage, Sort.by(Sort.Direction.ASC,"createdAt"));
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

    public List<Book> getAllBooksByQuery(String query) {
        return bookRepo.findAllBooksByParameter(query);
    }
}