package org.example.bookstoreapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.order.Order;
import org.example.bookstoreapp.order.OrderStatus;
import org.example.bookstoreapp.repository.OrderRepo;
import org.example.bookstoreapp.repository.UserRepo;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

    public Order save(String email, Order order) {
        return userRepo.findByEmail(email).map(user -> {
            order.setUser(user);
            order.setData(LocalDateTime.now());
            order.setStatus(OrderStatus.PROCESSING);
            return orderRepo.save(order);
        }).orElseThrow(() -> new RuntimeException("User Not Fount !"));
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}
