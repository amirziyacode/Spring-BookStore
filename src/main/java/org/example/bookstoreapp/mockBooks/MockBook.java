package org.example.bookstoreapp.mockBooks;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.book.Category;
import org.example.bookstoreapp.repository.BookRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MockBook implements CommandLineRunner {

    private final BookRepo bookRepo;

    @Override
    @Transactional
    public void run(String... args) {
        loadBooks();
    }

    private void loadBooks() {
        if(bookRepo.count() == 0) {
            Book grokAlgo = Book.builder()
                    .title("Grokking Algorithms")
                    .author("Aditya Y. Bhargava")
                    .cover("https://skybooks.ir/images/productImages/Grokking-Algorithms_EB1709675048.jpg")
                    .price(24.99)
                    .discount(15)
                    .rating(5)
                    .category(Category.COMPUTER_SCIENCE)
                    .isbn("9781633438538")
                    .year(2012)
                    .edition(2)
                    .paperback(322)
                    .description("An Illustrated Guide for Programmers and Other Curious People  A friendly, " +
                            "fully-illustrated introduction to the most important computer programming algorithms." +
                            "Master the most widely used algorithms and be fully prepared when you’re asked about them at your next job interview. " +
                            "With beautifully simple explanations, over 400 fun illustrations, and dozens of relevant examples," +
                            " you’ll actually enjoy learning about algorithms with this fun and friendly guide!" +
                            " The first edition of Grokking Algorithms proved to over 100,000 readers that learning algorithms doesn't have to be complicated or boring! " +
                            "This revised second edition contains brand new coverage of trees, including binary search trees, balanced trees, B-trees and more. " +
                            "You’ll also discover fresh insights on data structure performance that takes account of modern CPUs. Plus, the book’s fully annotated code samples have been updated to Python 3." +
                            "Foreword by Daniel Zingaro")
                    .build();

            bookRepo.save(grokAlgo);
        }
    }
}
