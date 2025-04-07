package org.example.bookstoreapp.Models.book;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Book_model")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    private String author;
    private  String coverImage;

    @Enumerated(EnumType.STRING)
    private Category category;
    private double discount;
    private double price;
    private String isbn;
    private double rating;
    private int paperback;
    private int year;
    private  String publisher;
    private String language;
    private boolean isNew;
    private boolean isBestseller;

    @Lob
    @Column(columnDefinition = "TEXT")
    @Basic(fetch = FetchType.LAZY)
    private String description;
}
