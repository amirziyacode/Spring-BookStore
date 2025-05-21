package org.example.bookstoreapp.admin;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDetails {
    private Integer bookId;
    private int quantity;
    private double price;
}
