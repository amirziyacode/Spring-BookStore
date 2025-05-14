package org.example.bookstoreapp.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookstoreapp.dto.OrderDTO;
import org.example.bookstoreapp.dto.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static java.lang.String.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;


@WebMvcTest(value = OrderController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = {OrderController.class, OrderService.class})
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderResponse orderResponse;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        orderItem = OrderItem.builder()
                .price(100.0)
                .quantity(20)
                .author("author")
                .coverImage("coverImage")
                .title("title")
                .build();
        orderResponse = OrderResponse.builder()
                .id(1)
                .items(List.of(orderItem))
                .tax(10)
                .total(120)
                .subtotal(100.0)
                .status(OrderStatus.PROCESSING)
                .email("email")
                .build();

    }

    @Test
    void addOrder_returnsOrderResponse() throws Exception {
        OrderMassage order = OrderMassage.builder()
                .massage("You add an order")
                .build();

        when(orderService.addOrder(anyString(),any(OrderDTO.class))).thenReturn(order);

        mockMvc.perform(post("http://localhost:8080/api/order/addOrder/{email}","email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.Massage_Order").value(order.getMassage()));



    }

    @Test
    void getAllOrders_return_OrderResponse() throws Exception {
        when(orderService.getAllOrders("email")).thenReturn(List.of(orderResponse));

        mockMvc.perform(get("http://localhost:8080/api/order/getAllOrders/{email}","email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value(orderResponse.getId()))
                .andExpect(jsonPath("[0].email").value(orderResponse.getEmail()))
                .andExpect(jsonPath("[0].total").value(orderResponse.getTotal()))
                .andExpect(jsonPath("[0].subtotal").value(orderResponse.getSubtotal()))
                .andExpect(jsonPath("[0].status").value(valueOf(orderResponse.getStatus())))
                .andExpect(jsonPath("[0].items[0]").value(orderItem))
                .andExpect(jsonPath("[0].tax").value(orderResponse.getTax()));
    }
}