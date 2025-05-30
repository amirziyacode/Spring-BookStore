package org.example.bookstoreapp.mapper;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.order.OrderDetails;
import org.example.bookstoreapp.order.OrderItemDetails;
import org.example.bookstoreapp.modelDTO.OrderDTO;
import org.example.bookstoreapp.modelDTO.OrderResponse;
import org.example.bookstoreapp.order.Order;
import org.example.bookstoreapp.order.OrderItem;
import org.example.bookstoreapp.order.OrderStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order OrderDTOToOrder(OrderDTO orderDTO) {
        return Order.builder()
                .items(orderDTO.getItems())
                .subTotal(orderDTO.getSubtotal())
                .data(LocalDateTime.now())
                .status(OrderStatus.PROCESSING)
                .tax(orderDTO.getTax())
                .total(orderDTO.getTotal())
                .createdAt(LocalDate.now())
                .orderNumber(
                        UUID.randomUUID()
                        .toString()
                        .substring(0, 4))
                .build();
    }

    @Override
    public List<OrderResponse> OrderToOrderResponse(List<Order> orders) {
        return orders.stream().map(this::OrderToOrderResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse OrderToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .items(order.getItems())
                .tax(order.getTax())
                .subtotal(order.getSubTotal())
                .total(order.getTotal())
                .email(order.getEmail())
                .status(order.getStatus())
                .date(LocalDate.now())
                .build();
    }

    @Override
    public OrderDetails OrderToOrderDetails(Order order) {
        return OrderDetails.builder()
                .id(order.getId())
                .customerEmail(order.getEmail())
                .totalAmount(order.getTotal())
                .createdAt(String.valueOf(order.getCreatedAt()))
                .orderNumber(order.getOrderNumber())
                .status(String.valueOf(order.getStatus()))
                .items(this.OrderItemToOrderItemDetails(order.getItems()))
                .build();
    }

    @Override
    public List<OrderDetails> OrderToOrderDetailsList(List<Order> orders) {
        return  orders.stream().map(this::OrderToOrderDetails).collect(Collectors.toList());
    }

    @Override
    public List<OrderItemDetails> OrderItemToOrderItemDetails(List<OrderItem> items) {
        return items.stream().map(this::OrderItemToOrderItemDetails).collect(Collectors.toList());
    }

    @Override
    public OrderItemDetails OrderItemToOrderItemDetails(OrderItem orderItem) {
        return OrderItemDetails.builder()
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .bookId(orderItem.getId())
                .build();
    }
}
