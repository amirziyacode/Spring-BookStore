package org.example.bookstoreapp.book;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = BookController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = {BookController.class, BookService.class})
class BookControllerTest {

    @MockBean
    private  BookService bookService;

    @Autowired
    MockMvc mockMvc;
    
    private Book mockBook;
    
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
    }
    
    @Test
    void get_All_books_pageable() throws Exception {
        List<Book> books = Collections.singletonList(mockBook);
        Pageable pageable = PageRequest.of(0, 12);
        Page<Book> mockPage = new PageImpl<>(books, pageable, books.size());

        when(bookService.getAllBooks(any(Integer.class),any(Integer.class))).thenReturn(mockPage);

        mockMvc.perform(get("http://localhost:8080/api/book/Books")
                        .param("page", "0")
                        .param("size", "12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Grokking Algorithms"))
                .andExpect(jsonPath("$.content[0].author").value("Aditya Y. Bhargava"))
                .andExpect(jsonPath("$.content[0].price").value(28.99))
                .andExpect(jsonPath("$.content[0].discount").value(10))
                .andExpect(jsonPath("$.content[0].new").value(true))
                .andExpect(jsonPath("$.content[0].rating").value(5))
                .andExpect(jsonPath("$.content[0].publisher").value("Manning"))
                .andExpect(jsonPath("$.content[0].language").value("English"))
                .andExpect(jsonPath("$.content[0].category").value("COMPUTER_SCIENCE"))
                .andExpect(jsonPath("$.content[0].isbn").value("9781633438538"))
                .andExpect(jsonPath("$.content[0].year").value(2024))
                .andExpect(jsonPath("$.content[0].paperback").value(322))
                .andExpect(jsonPath("$.content[0].description").value("description"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));


    }
    @Test
    void find_book_by_category() throws Exception {

        List<Book> books = Collections.singletonList(mockBook);

        Mockito.when(bookService.findByCategory(String.valueOf(mockBook.getCategory()))).thenReturn(books);


        mockMvc.perform(get("http://localhost:8080/api/book/getByCategory")
                .param("category", String.valueOf(mockBook.getCategory()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].title").value("Grokking Algorithms"))
                .andExpect(jsonPath("[0].author").value("Aditya Y. Bhargava"))
                .andExpect(jsonPath("[0].price").value(28.99))
                .andExpect(jsonPath("[0].discount").value(10))
                .andExpect(jsonPath("[0].new").value(true))
                .andExpect(jsonPath("[0].rating").value(5))
                .andExpect(jsonPath("[0].publisher").value("Manning"))
                .andExpect(jsonPath("[0].language").value("English"))
                .andExpect(jsonPath("[0].category").value("COMPUTER_SCIENCE"))
                .andExpect(jsonPath("[0].isbn").value("9781633438538"))
                .andExpect(jsonPath("[0].year").value(2024))
                .andExpect(jsonPath("[0].paperback").value(322))
                .andExpect(jsonPath("[0]..description").value("description"));
    }

    @Test
    void find_book_by_id() throws Exception {
        Mockito.when(bookService.findById(mockBook.getId())).thenReturn(mockBook);

        mockMvc.perform(get("http://localhost:8080/api/book/getById")
                .param("id", Integer.toString(mockBook.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("Grokking Algorithms"))
                .andExpect(jsonPath("author").value("Aditya Y. Bhargava"))
                .andExpect(jsonPath("price").value(28.99))
                .andExpect(jsonPath("discount").value(10))
                .andExpect(jsonPath("new").value(true))
                .andExpect(jsonPath("rating").value(5))
                .andExpect(jsonPath("publisher").value("Manning"))
                .andExpect(jsonPath("language").value("English"))
                .andExpect(jsonPath("category").value("COMPUTER_SCIENCE"))
                .andExpect(jsonPath("isbn").value("9781633438538"))
                .andExpect(jsonPath("year").value(2024))
                .andExpect(jsonPath("paperback").value(322))
                .andExpect(jsonPath("description").value("description"));
    }
    @Test
    void get_books_by_bestSeller() throws Exception {
        Mockito.when(bookService.findBestSeller(anyInt())).thenReturn(Collections.singletonList(mockBook));
        mockMvc.perform(get("http://localhost:8080/api/book/findBestSeller")
                .param("books",Integer.toString(1))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("[0].title").value("Grokking Algorithms"))
                .andExpect(jsonPath("[0].author").value("Aditya Y. Bhargava"))
                .andExpect(jsonPath("[0].price").value(28.99))
                .andExpect(jsonPath("[0].discount").value(10))
                .andExpect(jsonPath("[0].new").value(true))
                .andExpect(jsonPath("[0].rating").value(5))
                .andExpect(jsonPath("[0].publisher").value("Manning"))
                .andExpect(jsonPath("[0].language").value("English"))
                .andExpect(jsonPath("[0].category").value("COMPUTER_SCIENCE"))
                .andExpect(jsonPath("[0].isbn").value("9781633438538"))
                .andExpect(jsonPath("[0].year").value(2024))
                .andExpect(jsonPath("[0].paperback").value(322))
                .andExpect(jsonPath("[0]..description").value("description"));
    }
}