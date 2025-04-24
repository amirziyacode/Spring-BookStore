package org.example.bookstoreapp.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM  Order o WHERE LOWER(o.email) = LOWER(:email) ")
    List<Order> findByEmail(@Param("email") String email);
}
