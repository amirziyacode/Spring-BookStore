package org.example.bookstoreapp.dto;
import lombok.*;
import org.example.bookstoreapp.order.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private List<OrderItem> items ;
    private String email;
    private double subtotal;
    private double tax;
    private double total;
}
