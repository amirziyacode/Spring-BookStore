package org.example.bookstoreapp.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {

    @Query("SELECT n from _notifications n where Lower(n.user.email) = lower(:email) ")
    List<Notification> findByEmail(@Param("email") String email);
}