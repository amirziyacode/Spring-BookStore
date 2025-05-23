package org.example.bookstoreapp.book;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
    @Column(name = "publication_year")
    private int year;
    private  String publisher;
    private String language;
    private boolean isNew;
    private boolean isBestseller;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;


    @Lob
    @Column(columnDefinition = "TEXT")
    @Basic(fetch = FetchType.LAZY)
    private String description;
}
