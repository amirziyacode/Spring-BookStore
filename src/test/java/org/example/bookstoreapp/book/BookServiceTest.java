package org.example.bookstoreapp.book;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookService bookService;

    private Book mockBook;
    @BeforeEach
    void setUp() {
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
    void getAllBooks_pageable(){
        List<Book> bookList = IntStream.range(0, 12)
                .mapToObj(i -> Book.builder()
                        .id(i)
                        .title("title " + i)
                        .author("author" + i)
                        .price(i * 10)
                        .description("description" + i)
                        .build())
                .toList();
        Pageable pageable = PageRequest.of(0, 12);
        Page<Book> page = new PageImpl<>(bookList, pageable, bookList.size());

        Mockito.when(bookRepo.findAll(pageable)).thenReturn(page);

        Page<Book> allBooks = bookService.getAllBooks(0, 12);

        assertEquals(12, allBooks.getTotalElements());
        assertEquals("title 0", allBooks.getContent().get(0).getTitle());
    }

    @Test
    void find_by_category_throw_exception(){
        IllegalArgumentException category = assertThrows(IllegalArgumentException.class, () -> bookService.findByCategory("category"));
        assertEquals("Invalid category: category", category.getMessage());
    }
    @Test
    void find_by_category(){
        List<Book> books = Collections.singletonList(mockBook);

        Mockito.when(bookRepo.findByCategory(Category.valueOf(String.valueOf(Category.COMPUTER_SCIENCE)))).thenReturn(Optional.of(books));

        assertEquals(books, bookService.findByCategory(String.valueOf(Category.COMPUTER_SCIENCE)));
    }

    @Test
    void find_by_id_throw_exception(){
        assertThrows(IllegalArgumentException.class, () -> bookService.findBestSeller(-1));
    }

    @Test
    void find_books_by_id(){
        Mockito.when(bookRepo.findById(1)).thenReturn(Optional.of(mockBook));
        assertEquals(mockBook, bookService.findById(1));
    }

    @Test
    void find_bestSeller_should_return_books(){
        Mockito.when(bookService.findBestSeller(1)).thenReturn(Collections.singletonList(mockBook));
        assertEquals(List.of(mockBook), bookService.findBestSeller(1));
    }
}