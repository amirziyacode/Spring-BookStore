package org.example.bookstoreapp.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookstoreapp.order.OrderDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = OrderAdminController.class,excludeAutoConfiguration =  SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {OrderAdminController.class, OrderAdminService.class})
class OrderAdminControllerTest {

    @MockBean
    private OrderAdminService orderAdminService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderDetails orderDetails;


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
    }

    @Test
    void should_returns_getAllOrder_status_ok() throws Exception {

        when(orderAdminService.getAllOrders()).thenReturn(List.of(orderDetails));

        mockMvc.perform(get("http://localhost:8080/api/admin/order/getAllOrders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].items").value(orderDetails.getItems()))
                .andExpect(jsonPath("$[0].customerEmail").value("email"))
                .andExpect(jsonPath("$[0].totalAmount").value(121.0))
                .andExpect(jsonPath("$[0].status").value("PROCESSING"))
                .andExpect(jsonPath("$[0].orderNumber").value("123"));
    }

    @Test
    void should_updateStatus_with_id_ok() throws Exception {
        orderDetails.setStatus("DELIVERED");
        when(orderAdminService.getAllOrders()).thenReturn(List.of(orderDetails));

        mockMvc.perform(put("http://localhost:8080/api/admin/order/updateStatus/{orderId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDetails)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$").value("Status updated successfully"));
    }
}