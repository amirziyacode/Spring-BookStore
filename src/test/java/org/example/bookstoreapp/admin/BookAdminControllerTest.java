package org.example.bookstoreapp.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.book.BookRequest;
import org.example.bookstoreapp.book.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;


@WebMvcTest(value = BookAdminController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = {BookAdminController.class,BookAdminService.class})
class BookAdminControllerTest {


    @MockBean
    private BookAdminService  bookAdminService;

    @Autowired
    private MockMvc mockMvc;

    private Book mockBook;
    private BookRequest bookRequest;

    @Autowired
    private ObjectMapper objectMapper;

   final static int BOOK_ID = 1;

    @BeforeEach
    void setUp() {
        bookRequest = BookRequest.builder()
                .title("Grokking Algorithms")
                .author("Aditya Y. Bhargava")
                .coverImage("coverImage")
                .price(28.99)
                .discount(10)
                .publisher("Manning")
                .language("English")
                .isbn("9781633438538")
                .year(2024)
                .paperback(322)
                .description("description")
                .rating(5)
                .build();
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
    void should_get_all_books_status_ok()throws Exception {
        when(bookAdminService.allBooks()).thenReturn(List.of(mockBook));

        mockMvc.perform(get("http://localhost:8080/api/admin/book/getAllBooks")
                .content(objectMapper.writeValueAsString(List.of(mockBook)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mockBook.getId()))
                .andExpect(jsonPath("$[0].title").value(mockBook.getTitle()))
                .andExpect(jsonPath("$[0].author").value(mockBook.getAuthor()))
                .andExpect(jsonPath("$[0].coverImage").value(mockBook.getCoverImage()))
                .andExpect(jsonPath("$[0].price").value(mockBook.getPrice()))
                .andExpect(jsonPath("$[0].discount").value(mockBook.getDiscount()))
                .andExpect(jsonPath("$[0].isNew").value(mockBook.isNew()))
                .andExpect(jsonPath("$[0].isBestSeller").value(mockBook.isBestseller()))
                .andExpect(jsonPath("$[0].publisher").value(mockBook.getPublisher()))
                .andExpect(jsonPath("$[0].language").value(mockBook.getLanguage()))
                .andExpect(jsonPath("$[0].category").value("COMPUTER_SCIENCE"))
                .andExpect(jsonPath("$[0].isbn").value(mockBook.getIsbn()))
                .andExpect(jsonPath("$[0].year").value(mockBook.getYear()))
                .andExpect(jsonPath("$[0].paperback").value(mockBook.getPaperback()))
                .andExpect(jsonPath("$[0].description").value(mockBook.getDescription()));
    }

    @Test
    void should_addBook_than_return_book_added_successfully() throws Exception{


        when(bookAdminService.addBook(bookRequest)).thenReturn("Book added successfully");

        mockMvc.perform(post("http://localhost:8080/api/admin/book/addBook")
                .content(objectMapper.writeValueAsString(bookRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Book added successfully"));
    }

    @Test
    void should_deleteBook_byId() throws Exception{
        when(bookAdminService.deleteBook(BOOK_ID)).thenReturn("Book deleted successfully");

        mockMvc.perform(delete("http://localhost:8080/api/admin/book/deleteBook/{bookId}", BOOK_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").value("Book deleted successfully"));
    }

    @Test
    void deleteAllBooks() throws Exception{
        when(bookAdminService.deleteAllBooks()).thenReturn("All Books deleted successfully");

        mockMvc.perform(delete("http://localhost:8080/api/admin/book/deleteAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").value("All Books deleted successfully"));
    }

    @Test
    void should_update_book_by_id_return_201()throws Exception {
        when(bookAdminService.updateBook(bookRequest, BOOK_ID)).thenReturn("Book updated successfully");

        mockMvc.perform(put("http://localhost:8080/api/admin/book/updateBook/{bookId}", BOOK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("Book updated successfully"));
    }
}