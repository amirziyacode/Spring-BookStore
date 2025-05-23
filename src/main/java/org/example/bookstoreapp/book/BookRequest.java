package org.example.bookstoreapp.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {
    private String title;
    private String author;
    private String description;
    private double price;
    private BookCategoryRequest category;
    private String publisher;
    private int year;
    private int paperback;
    private String isbn;
    private double rating;
    private String language;
    private String coverImage;
    private double discount;
}
