package org.example.bookstoreapp.springData;

import org.example.bookstoreapp.book.BookRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MockBookTest {

    @Autowired
    private  BookRepo bookRepo;

    @Test
    void check_we_have_17_books(){
        assertThat(bookRepo.count()).isEqualTo(21);
    }
}