package org.example.bookstoreapp.mockBooks;

import org.example.bookstoreapp.repository.BookRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MockBookTest {

    @Autowired
    private  BookRepo bookRepo;

    @Test
    void check_we_have_12_books(){
        assertThat(bookRepo.count()).isEqualTo(12);
    }
}