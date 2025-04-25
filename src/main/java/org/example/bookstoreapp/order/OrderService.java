package org.example.bookstoreapp.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoreapp.dto.OrderDTO;
import org.example.bookstoreapp.mapper.OrderMapper;
import org.example.bookstoreapp.user.UserRepo;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final   OrderMapper orderMapper;

    public OrderResponse save(String email, OrderDTO order) {
        return userRepo.findByEmail(email).map(user -> {
            Order toOrder = orderMapper.OrderDTOToOrder(order);
            toOrder.setEmail(email);
            orderRepo.save(toOrder);
            return OrderResponse.builder()
                    .massage(LocalDate.now() +"  Order has been processed from this email : " + email +" Please be patient.")
                    .build();
        }).orElseThrow(() -> new RuntimeException("User Not Fount !"));
    }

    public List<Order> getAllOrders(String email) {
        return orderRepo.findByEmail(email);
    }
}
