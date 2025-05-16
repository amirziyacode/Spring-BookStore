package org.example.bookstoreapp.order;

import org.example.bookstoreapp.dto.OrderDTO;
import org.example.bookstoreapp.dto.OrderResponse;
import org.example.bookstoreapp.mapper.OrderMapper;
import org.example.bookstoreapp.notification.Notification;
import org.example.bookstoreapp.notification.NotificationService;
import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.List.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock private OrderRepo orderRepo;
    @Mock private UserRepo userRepo;
    @Mock private OrderMapper orderMapper;
    @Mock private NotificationService notificationService;


    private OrderResponse orderResponse;
    private Order order;
    @BeforeEach
    void setUp() {
        OrderItem orderItem = OrderItem.builder()
                .price(100.0)
                .quantity(20)
                .author("author")
                .coverImage("coverImage")
                .title("title")
                .build();
        orderResponse = OrderResponse.builder()
                .id(1)
                .items(of(orderItem))
                .tax(10)
                .total(120)
                .subtotal(100.0)
                .status(OrderStatus.PROCESSING)
                .email("email")
                .build();
        order = Order.builder()
                .id(1)
                .items(of(orderItem))
                .tax(10)
                .tax(10)
                .total(120)
                .subTotal(100.0)
                .status(OrderStatus.PROCESSING)
                .email("email")
                .build();

    }

    @Test
    void addOrder_should_return_OrderMassage() {
        OrderMassage orderMassage = OrderMassage.builder()
                .massage(LocalDate.now() +"  Order has been processed from this email : email Please be patient.")
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .tax(order.getTax())
                .items(order.getItems())
                .total(order.getTotal())
                .email(order.getEmail())
                .subtotal(order.getSubTotal())
                .build();

        Optional<User> user = Optional.of(User.builder()
                .email("email")
                .build());

        when(userRepo.findByEmail("email")).thenReturn(user);
        when(orderMapper.OrderDTOToOrder(orderDTO)).thenReturn(order);

        OrderMassage orderBeehive = orderService.addOrder("email", orderDTO);


        assertNotNull(orderBeehive);
        assertThat(orderBeehive).isEqualTo(orderMassage);

        verify(userRepo,times(1)).findByEmail(anyString());
        verify(orderRepo, times(1)).save(any(Order.class));
        verify(orderMapper, times(1)).OrderDTOToOrder(any(OrderDTO.class));
        verify(notificationService,times(1)).createNotification(any(Notification.class));
    }

    @Test
    void addOrder_should_throw_RunTimeException() {
        when(userRepo.findByEmail("email")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderService.addOrder("email",any(OrderDTO.class)))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User Not Fount !");
    }

    @Test
    void getAllOrders_shouldReturnOrderResponses() {
        when(orderRepo.findByEmail("email")).thenReturn(of(order));
        when(orderMapper.OrderToOrderResponse(any(Order.class))).thenReturn(orderResponse);
        when(orderService.getAllOrders("email")).thenReturn(of(orderResponse));

        List<OrderResponse> orders = orderService.getAllOrders("email");

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(orderResponse, orders.get(0));

    }
}