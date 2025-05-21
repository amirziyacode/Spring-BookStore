package org.example.bookstoreapp.admin;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.mapper.OrderMapper;
import org.example.bookstoreapp.order.OrderDetails;
import org.example.bookstoreapp.order.OrderRepo;
import org.example.bookstoreapp.order.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderAdminService {

    private final OrderMapper orderMapper;
    private final OrderRepo orderRepo;

    public List<OrderDetails> getAllOrders() {return  orderMapper.OrderToOrderDetailsList(orderRepo.findAll());}

    public void changeStatus(String orderStatus, Integer orderId) {
        orderRepo.findById(orderId).ifPresent(order -> {
            try {
                OrderStatus status = OrderStatus.valueOf(orderStatus);
                order.setStatus(status);
                orderRepo.save(order);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid order status: " + orderStatus);
            }
        });
    }

}
