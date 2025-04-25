package org.example.bookstoreapp.mapper;

import org.example.bookstoreapp.dto.OrderDTO;
import org.example.bookstoreapp.order.Order;


public interface OrderMapper {
    Order OrderDTOToOrder(OrderDTO orderDTO);
}
