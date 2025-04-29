package org.example.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bookstoreapp.order.OrderItem;
import org.example.bookstoreapp.order.OrderStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Integer id;
    private List<OrderItem> items ;
    private String email;
    private double subtotal;
    private double tax;
    private double total;
    private OrderStatus status;
}
