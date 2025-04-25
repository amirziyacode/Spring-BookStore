package org.example.bookstoreapp.mapper;

import org.example.bookstoreapp.dto.OrderDTO;
import org.example.bookstoreapp.order.Order;
import org.example.bookstoreapp.order.OrderStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapperImpl implements OrderMapper {
    @Override
    public Order OrderDTOToOrder(OrderDTO orderDTO) {
        return Order.builder()
                .items(orderDTO.getItems())
                .subTotal(orderDTO.getSubTotal())
                .data(LocalDateTime.now())
                .status(OrderStatus.PROCESSING)
                .tax(orderDTO.getTax())
                .total(orderDTO.getTotal())
                .build();
    }
}
