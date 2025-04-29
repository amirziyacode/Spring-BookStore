package org.example.bookstoreapp.mapper;

import org.example.bookstoreapp.dto.OrderDTO;
import org.example.bookstoreapp.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderMapperImplTest {
    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapperImpl();
    }
    @Test
    void givenOrderDTO_whenMapping_thenReturnCorrectOrder() {
        // Given
        OrderDTO orderDTO = OrderDTO.builder()
                .email("amzdidi@gmail.com")
                .items(new ArrayList<>())
                .total(13.9)
                .tax(8.0)
                .subTotal(14.3)
                .build();

        // When
        Order mappedOrder = orderMapper.OrderDTOToOrder(orderDTO);

        // Then
        assertThat(mappedOrder.getItems()).isEqualTo(orderDTO.getItems());
        assertThat(mappedOrder.getTotal()).isEqualTo(orderDTO.getTotal());
        assertThat(mappedOrder.getTax()).isEqualTo(orderDTO.getTax());
        assertThat(mappedOrder.getSubTotal()).isEqualTo(orderDTO.getSubTotal());
        assertThat(mappedOrder.getStatus()).isNotNull();
        assertThat(mappedOrder.getData()).isNotNull();
    }
}