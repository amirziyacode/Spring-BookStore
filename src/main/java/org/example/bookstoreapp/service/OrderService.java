package org.example.bookstoreapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.order.Order;
import org.example.bookstoreapp.order.OrderStatus;
import org.example.bookstoreapp.repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;

    public Order save(Order order) {
        order.setData(LocalDateTime.now());
        order.setStatus(OrderStatus.DELIVERED);
        return orderRepo.save(order);
    }
}
