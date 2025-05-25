package org.example.bookstoreapp.admin;

import org.example.bookstoreapp.mapper.OrderMapper;
import org.example.bookstoreapp.order.Order;
import org.example.bookstoreapp.order.OrderDetails;
import org.example.bookstoreapp.order.OrderRepo;
import org.example.bookstoreapp.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class OrderAdminServiceTest {

    @InjectMocks
    private OrderAdminService orderAdminService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderRepo orderRepo;

    private OrderDetails orderDetails;

    private Order order;

    @BeforeEach
    void setUp() {
        orderDetails =   OrderDetails.builder()
                .id(1)
                .orderNumber("123")
                .items(new ArrayList<>())
                .customerEmail("email")
                .totalAmount(121.0)
                .status("PROCESSING")
                .build();

        order = Order.builder()
                .id(1)
                .orderNumber("123")
                .items(new ArrayList<>())
                .email("email")
                .total(121.0)
                .status(OrderStatus.PROCESSING)
                .build();
    }


    @Test
    void should_getAllOrders_with_mapper_to_orderDetails() {
        when(orderAdminService.getAllOrders()).thenReturn(List.of(orderDetails));
        when(orderMapper.OrderToOrderDetailsList(anyList())).thenReturn(List.of(orderDetails));

        List<OrderDetails> allOrders = orderAdminService.getAllOrders();

        assertEquals(1, allOrders.size());
        assertEquals(orderDetails, allOrders.get(0));
        verify(orderMapper, times(1)).OrderToOrderDetailsList(anyList());
        verify(orderRepo, times(2)).findAll();

    }

    @Test
    void should_changeStatus_byId_successful() {
        when(orderRepo.findById(anyInt())).thenReturn(Optional.of(order));

        orderAdminService.changeStatus("DELIVERED",1);

        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        verify(orderRepo, times(2)).findById(anyInt());
        verify(orderRepo,times(1)).save(order);
    }

    @Test
    void should_changeStatus_byId_failed() {
        when(orderRepo.findById(anyInt())).thenReturn(Optional.of(order));


        String message = assertThrows(IllegalArgumentException.class, () -> orderAdminService.changeStatus("SSS", 1)).getMessage();
        assertEquals("Invalid order status: SSS", message);
    }

    @Test
    void should_changeStatus_with_Id_not_found() {
        when(orderRepo.findById(anyInt())).thenReturn(Optional.empty());
        String message = assertThrows(RuntimeException.class, () -> orderAdminService.changeStatus("DELIVERED", -1)).getMessage();
        assertEquals("Order not found", message);
    }
}