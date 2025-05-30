package org.example.bookstoreapp.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoreapp.modelDTO.OrderDTO;
import org.example.bookstoreapp.modelDTO.OrderResponse;
import org.example.bookstoreapp.mapper.OrderMapper;
import org.example.bookstoreapp.notification.Notification;
import org.example.bookstoreapp.notification.NotificationService;
import org.example.bookstoreapp.notification.NotificationStatus;
import org.example.bookstoreapp.user.UserRepo;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final OrderMapper orderMapper;
    private final NotificationService notificationService;

    public OrderMassage addOrder(String email, OrderDTO order) {
        return userRepo.findByEmail(email).map(user -> {
            Order toOrder = orderMapper.OrderDTOToOrder(order);
            toOrder.setEmail(email);
            orderRepo.save(toOrder);

            notificationService.createNotification(Notification.builder()
                            .title("New order")
                            .message("You have been add "+order.getItems().size()+" items to your order")
                            .type(NotificationStatus.ORDER)
                            .date(LocalDateTime.now())
                            .user(user)
                    .build());

            return OrderMassage.builder()
                    .massage(LocalDate.now() +"  Order has been processed from this email : " + email +" Please be patient.")
                    .build();
        }).orElseThrow(() -> new RuntimeException("User Not Fount !"));
    }

    public List<OrderResponse> getAllOrders(String email) {
        List<Order> byEmail = orderRepo.findByEmail(email);
        return orderMapper.OrderToOrderResponse(byEmail);
    }
}
