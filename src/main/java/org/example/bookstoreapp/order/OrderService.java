package org.example.bookstoreapp.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoreapp.user.UserRepo;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

    public Order save(String email, Order order) {
        return userRepo.findByEmail(email).map(user -> {
            order.setEmail(email);
            order.setData(LocalDateTime.now());
            order.setStatus(OrderStatus.PROCESSING);
            return orderRepo.save(order);
        }).orElseThrow(() -> new RuntimeException("User Not Fount !"));
    }

    public List<Order> getAllOrders(String email) {
        return orderRepo.findByEmail(email);
    }
}
