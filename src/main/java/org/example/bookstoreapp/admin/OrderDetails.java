package org.example.bookstoreapp.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private Integer id;
    private String orderNumber;
    private String customerEmail;
    private double totalAmount;
    private String status;
    private String createdAt;
    private List<OrderItemDetails> items;
}
