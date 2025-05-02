package org.example.bookstoreapp.mapper;

import org.example.bookstoreapp.dto.OrderDTO;
import org.example.bookstoreapp.dto.OrderResponse;
import org.example.bookstoreapp.order.Order;

import java.util.List;


public interface OrderMapper {
    Order OrderDTOToOrder(OrderDTO orderDTO);
    List<OrderResponse> OrderToOrderResponse(List<Order> orders);

    OrderResponse OrderToOrderResponse(Order order);
}
