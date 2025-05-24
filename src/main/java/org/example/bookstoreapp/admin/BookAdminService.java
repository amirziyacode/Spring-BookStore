package org.example.bookstoreapp.admin;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.book.BookRepo;
import org.example.bookstoreapp.book.BookRequest;
import org.example.bookstoreapp.book.Category;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookAdminService {

    private final BookRepo bookRepo;

    public List<Book> allBooks() {
        return bookRepo.findAll();
    }

    public String addBook(BookRequest book) {
        if(book.getRating() < 0 || book.getRating() > 5.0) {
            throw new IllegalArgumentException("Rating should be greater than 5.0");
        }
        try{
            Category bookCategory = Category.valueOf(book.getCategory().getCategoryName());

            Book build = Book.builder()
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .category(bookCategory)
                    .year(book.getYear())
                    .discount(book.getDiscount())
                    .price(book.getPrice())
                    .createdAt(LocalDateTime.now())
                    .paperback(book.getPaperback())
                    .coverImage(book.getCoverImage())
                    .isbn(book.getIsbn())
                    .rating(book.getRating())
                    .description(book.getDescription())
                    .language(book.getLanguage())
                    .publisher(book.getPublisher())
                    .build();

            bookRepo.save(build);

        }catch (IllegalArgumentException e){
            return  "error with name of category : " + book.getCategory().getCategoryName();
        }

        return "Book added successfully";
    }

    public String updateBook(BookRequest book, int bookId) {
        bookRepo.findById(bookId).map(oldBook -> {
           oldBook.setTitle(book.getTitle());
           oldBook.setAuthor(book.getAuthor());
           oldBook.setYear(book.getYear());
           oldBook.setPrice(book.getPrice());
           oldBook.setPaperback(book.getPaperback());
           oldBook.setCoverImage(book.getCoverImage());
           oldBook.setIsbn(book.getIsbn());
            oldBook.setPublisher(book.getPublisher());
           oldBook.setDiscount(book.getDiscount());
           oldBook.setRating(book.getRating());
           oldBook.setCategory(Category.valueOf(book.getCategory().getCategoryName()));
           oldBook.setDescription(book.getDescription());
           oldBook.setLanguage(book.getLanguage());
           bookRepo.save(oldBook);

           return "Book updated successfully";

        }).orElseThrow(() ->  new RuntimeException("Book not found"));

       return "Book updated successfully";
    }

        public String deleteBook(int bookId) {
            if(bookRepo.findById(bookId).isPresent()){
                bookRepo.deleteById(bookId);
                return "Book deleted successfully";
            }

            throw new IllegalStateException("Book not found");
        }

        public String deleteAllBooks() {
            bookRepo.deleteAll();
            return "All Books deleted successfully";
        }
}
