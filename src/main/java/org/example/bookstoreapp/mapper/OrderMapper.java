package org.example.bookstoreapp.mapper;

import org.example.bookstoreapp.order.OrderDetails;
import org.example.bookstoreapp.order.OrderItemDetails;
import org.example.bookstoreapp.dto.OrderDTO;
import org.example.bookstoreapp.dto.OrderResponse;
import org.example.bookstoreapp.order.Order;
import org.example.bookstoreapp.order.OrderItem;

import java.util.List;


public interface OrderMapper {
    Order OrderDTOToOrder(OrderDTO orderDTO);
    List<OrderResponse> OrderToOrderResponse(List<Order> orders);
    OrderResponse OrderToOrderResponse(Order order);
    OrderDetails OrderToOrderDetails(Order order);
    List<OrderDetails> OrderToOrderDetailsList(List<Order> orders);
    List<OrderItemDetails> OrderItemToOrderItemDetails(List<OrderItem> items);
    OrderItemDetails OrderItemToOrderItemDetails(OrderItem orderItem);
}
