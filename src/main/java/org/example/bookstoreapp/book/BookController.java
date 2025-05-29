package org.example.bookstoreapp.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("Books")
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(defaultValue = "0")int pageNumber,
            @RequestParam(defaultValue = "12") int perPage
            ) {
        Page<Book> books = bookService.getAllBooks(pageNumber, perPage);
        return ResponseEntity.status(HttpStatus.OK).body(books);
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

    @GetMapping("findBestSeller")
    public ResponseEntity<List<Book>> getBestSeller(@RequestParam int books) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findBestSeller(books));
    }

    @GetMapping("getAllBooksByQuery")
    public ResponseEntity<List<Book>> getAllBooksByQuery(@RequestParam String query) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooksByQuery(query));
    }
}
