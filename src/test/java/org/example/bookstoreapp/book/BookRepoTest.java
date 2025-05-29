package org.example.bookstoreapp.book;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class BookRepoTest {

    @Autowired
    private BookRepo bookRepo;
    private Book mockBook;

    @BeforeEach
    void setUp() {
        bookRepo.deleteAll();
        mockBook = Book.builder()
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
    }

    @Test
    void find_books_Pagination(){
        List<Book> books = Collections.singletonList(mockBook);
        bookRepo.saveAll(books);
        Page<Book> pageBooks = bookRepo.findAll(PageRequest.of(0, 12));

        Book book = pageBooks.getContent().get(0);
        assertThat(book.getTitle()).isEqualTo(mockBook.getTitle());
        assertThat(book.getAuthor()).isEqualTo(mockBook.getAuthor());
        assertThat(book.getCoverImage()).isEqualTo(mockBook.getCoverImage());
        assertThat(book.getPrice()).isEqualTo(mockBook.getPrice());
        assertThat(book.getDiscount()).isEqualTo(mockBook.getDiscount());
        assertThat(book.getRating()).isEqualTo(mockBook.getRating());
        assertThat(book.getPublisher()).isEqualTo(mockBook.getPublisher());
        assertThat(book.getCategory()).isEqualTo(mockBook.getCategory());
    }

    @Test
    void find_books_by_category(){
        List<Book> books = Collections.singletonList(mockBook);
        bookRepo.saveAll(books);
        Optional<List<Book>> byCategory = bookRepo.findByCategory(Category.COMPUTER_SCIENCE);

        assertThat(byCategory.isPresent()).isTrue();
        assertThat(byCategory.get().size()).isEqualTo(1);
        assertThat(byCategory.get().get(0).getTitle()).isEqualTo(mockBook.getTitle());
        assertThat(byCategory.get().get(0).getAuthor()).isEqualTo(mockBook.getAuthor());
        assertThat(byCategory.get().get(0).getCoverImage()).isEqualTo(mockBook.getCoverImage());
        assertThat(byCategory.get().get(0).getPrice()).isEqualTo(mockBook.getPrice());
        assertThat(byCategory.get().get(0).getDiscount()).isEqualTo(mockBook.getDiscount());
        assertThat(byCategory.get().get(0).getRating()).isEqualTo(mockBook.getRating());
        assertThat(byCategory.get().get(0).getPublisher()).isEqualTo(mockBook.getPublisher());
        assertThat(byCategory.get().get(0).getCategory()).isEqualTo(mockBook.getCategory());
    }

    @Test
    void should_findAllBooksByParameter_and_returned(){
        bookRepo.save(mockBook);
        List<Book> bySearch = bookRepo.findAllBooksByParameter("gr");


        assertThat(bySearch.size()).isEqualTo(1);
        assertThat(bySearch.get(0).getTitle()).isEqualTo(mockBook.getTitle());
        assertThat(bySearch.get(0).getAuthor()).isEqualTo(mockBook.getAuthor());
        assertThat(bySearch.get(0).getCoverImage()).isEqualTo(mockBook.getCoverImage());
        assertThat(bySearch.get(0).getPrice()).isEqualTo(mockBook.getPrice());
        assertThat(bySearch.get(0).getDiscount()).isEqualTo(mockBook.getDiscount());
        assertThat(bySearch.get(0).getRating()).isEqualTo(mockBook.getRating());
        assertThat(bySearch.get(0).getPublisher()).isEqualTo(mockBook.getPublisher());
        assertThat(bySearch.get(0).getCategory()).isEqualTo(mockBook.getCategory());
    }
}