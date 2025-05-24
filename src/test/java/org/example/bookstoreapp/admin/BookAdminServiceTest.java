package org.example.bookstoreapp.admin;

import org.example.bookstoreapp.book.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BookAdminServiceTest {

    @InjectMocks
    private BookAdminService bookAdminService;

    @Mock
    private BookRepo bookRepo;

    private Book mockBook;

    private BookRequest bookRequest;

    @BeforeEach
    void setUp() {
        mockBook = Book.builder()
                .id(1)
                .title("Grokking Algorithms")
                .author("Aditya Y. Bhargava")
                .coverImage("coverImage")
                .price(28.99)
                .discount(10)
                .isNew(true)
                .rating(5)
                .isBestseller(true)
                .publisher("Manning")
                .language("English")
                .category(Category.COMPUTER_SCIENCE)
                .isbn("9781633438538")
                .year(2024)
                .paperback(322)
                .description("description")
                .build();

        bookRequest = BookRequest.builder()
                .title("Grokking Algorithms")
                .author("Aditya Y. Bhargava")
                .coverImage("coverImage")
                .price(28.99)
                .discount(10)
                .category(BookCategoryRequest.builder().categoryName("COMPUTER_SCIENCE").build())
                .publisher("Manning")
                .language("English")
                .isbn("9781633438538")
                .year(2024)
                .paperback(322)
                .description("description")
                .rating(5)
                .build();
    }


    @Test
    void should_returns_allBooks() {
        when(bookRepo.findAll()).thenReturn(List.of(mockBook));
        List<Book> books = bookAdminService.allBooks();

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(mockBook, books.get(0));
    }

    @Test
    void should_addBook() {

        String message = bookAdminService.addBook(bookRequest);

        assertNotNull(message);
        assertEquals("Book added successfully", message);
        verify(bookRepo, times(1)).save(any(Book.class));
    }

    @Test
    void should_addBook_throw_error_with_rating() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setRating(-1); // less than 5.0 but > 0
        bookRequest.setCategory(BookCategoryRequest.builder().categoryName("FICTION").build());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> bookAdminService.addBook(bookRequest));
        assertEquals("Rating should be greater than 5.0", thrown.getMessage());
        verify(bookRepo, never()).save(any(Book.class));
    }

    @Test
    void should_addBook_throw_error_with_invalidCategory() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setCategory(BookCategoryRequest.builder().categoryName("INVALID_CATEGORY").build());

        String result = bookAdminService.addBook(bookRequest);

        assertEquals("error with name of category : INVALID_CATEGORY", result);
        verify(bookRepo, never()).save(any(Book.class));

    }

    @Test
    void should_updateBook_successfully() {

        // change rating for check is really updated !!
        bookRequest.setRating(3.6);

        when(bookRepo.findById(1)).thenReturn(Optional.of(mockBook));
        String result = bookAdminService.updateBook(bookRequest, 1);

        assertEquals("Book updated successfully", result);
        assertEquals(3.6,mockBook.getRating());
        verify(bookRepo, times(1)).save(any(Book.class));
    }

    @Test
    void should_updateBook_throw_error_with_not_found_book() {
        when(bookRepo.findById(1)).thenReturn(Optional.empty());

        String message = assertThrows(RuntimeException.class, () -> bookAdminService.updateBook(bookRequest, 1)).getMessage();
        assertEquals("Book not found", message);
    }

    @Test
    void should_deleteBook_work_successfully() {
        int bookId = 1;
        when(bookRepo.findById(bookId)).thenReturn(Optional.of(mockBook));
        String result = bookAdminService.deleteBook(bookId);

        assertEquals("Book deleted successfully", result);
        verify(bookRepo, times(bookId)).findById(bookId);
        verify(bookRepo,times(bookId)).deleteById(bookId);
    }

    @Test
    void should_deleteBook_throw_error_with_not_found_book() {
        when(bookRepo.findById(1)).thenReturn(Optional.empty());
        String message = assertThrows(RuntimeException.class, () -> bookAdminService.deleteBook(1)).getMessage();
        assertEquals("Book not found", message);
    }

    @Test
    void deleteAllBooks() {
        String result = bookAdminService.deleteAllBooks();

        verify(bookRepo).deleteAll();
        assertEquals("All Books deleted successfully", result);
    }
}