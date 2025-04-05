package org.example.bookstoreapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book")
@CrossOrigin("http://localhost:3000")
public class BookController {

    private final BookService bookService;

    @GetMapping("allBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @Transactional // => for big text is come from database !!
    @GetMapping("getByCategory")
    public ResponseEntity<List<Book>> getByCategory(@RequestParam String category) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findByCategory(category));
    }

    @GetMapping("getById")
        public ResponseEntity<Book> getById(@RequestParam int id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(id));
    }
}
