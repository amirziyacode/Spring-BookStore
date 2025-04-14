package org.example.bookstoreapp.repository;

import org.example.bookstoreapp.notification.Massage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Massage, Integer> { }
