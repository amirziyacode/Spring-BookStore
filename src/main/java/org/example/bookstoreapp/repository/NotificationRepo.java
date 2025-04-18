package org.example.bookstoreapp.repository;

import org.example.bookstoreapp.notification.Massage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepo extends JpaRepository<Massage, Integer> { }
