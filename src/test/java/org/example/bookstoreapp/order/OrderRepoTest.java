package org.example.bookstoreapp.order;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class OrderRepoTest {

    @Mock
    private OrderRepo orderRepo;


    @Test
    void findByEmail_should_return_List_of_Order() {
        OrderItem orderItem = OrderItem.builder()
                .price(100.0)
                .quantity(20)
                .author("author")
                .coverImage("coverImage")
                .title("title")
                .build();
        Order order = Order.builder()
                .id(1)
                .items(of(orderItem))
                .tax(10)
                .tax(10)
                .total(120)
                .subTotal(100.0)
                .status(OrderStatus.PROCESSING)
                .email("email")
                .build();
        when(orderRepo.findByEmail(anyString())).thenReturn(List.of(order));
        List<Order> orders = orderRepo.findByEmail("email");

        assertEquals(order, orders.get(0));

    }
}