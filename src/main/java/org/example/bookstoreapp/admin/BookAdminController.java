package org.example.bookstoreapp.admin;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.book.BookRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/book")
@RequiredArgsConstructor
public class BookAdminController {

    private final BookAdminService bookAdminService;

    @GetMapping("getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookAdminService.allBooks());
    }

    @PostMapping("addBook")
    public ResponseEntity<String> addBook(@RequestBody BookRequest book) {
        return ResponseEntity.status(HttpStatus.OK).body(bookAdminService.addBook(book));
    }

    @PutMapping("updateBook/{bookId}")
    public ResponseEntity<String> updateBook(@RequestBody BookRequest book,@PathVariable int bookId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookAdminService.updateBook(book,bookId));
    }

    @DeleteMapping("deleteBook/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable int bookId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(bookAdminService.deleteBook(bookId));
    }

    @DeleteMapping("deleteAll")
    public ResponseEntity<String> deleteAllBooks() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(bookAdminService.deleteAllBooks());
    }

}
