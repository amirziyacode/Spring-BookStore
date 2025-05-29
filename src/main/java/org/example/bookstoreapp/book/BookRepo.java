package org.example.bookstoreapp.book;


import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    @NonNull
     Page<Book> findAll(@NonNull Pageable booksPageable);
    Optional<List<Book>> findByCategory(Category bookCategory);

    @Query("SELECT b FROM Book_model b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> findAllBooksByParameter(@Param("query") String query);
}

