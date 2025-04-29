package org.example.bookstoreapp.mapper;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.dto.OrderDTO;
import org.example.bookstoreapp.dto.OrderResponse;
import org.example.bookstoreapp.order.Order;
import org.example.bookstoreapp.order.OrderStatus;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
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
                .build();
    }

}
