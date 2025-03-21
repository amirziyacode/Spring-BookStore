package org.example.bookstoreapp.book;


import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

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
    private  String cover;

    @Enumerated(EnumType.STRING)
    private Category category;
    private double discount;
    private double price;
    private String isbn;
    private double rating;
    private int paperback;
    private int year;
    private int edition;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
}
