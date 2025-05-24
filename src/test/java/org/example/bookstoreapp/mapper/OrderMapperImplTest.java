package org.example.bookstoreapp.mapper;

import org.example.bookstoreapp.dto.OrderDTO;
import org.example.bookstoreapp.dto.OrderResponse;
import org.example.bookstoreapp.order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderMapperImplTest {
    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapperImpl();
    }
    @Test
    void givenOrderDTO_whenMapping_thenReturnCorrectOrder() {

        OrderDTO orderDTO = OrderDTO.builder()
                .email("amzdidi@gmail.com")
                .items(new ArrayList<>())
                .total(13.9)
                .tax(8.0)
                .subtotal(14.3)
                .build();


        Order mappedOrder = orderMapper.OrderDTOToOrder(orderDTO);


        assertThat(mappedOrder.getItems()).isEqualTo(orderDTO.getItems());
        assertThat(mappedOrder.getTotal()).isEqualTo(orderDTO.getTotal());
        assertThat(mappedOrder.getTax()).isEqualTo(orderDTO.getTax());
        assertThat(mappedOrder.getSubTotal()).isEqualTo(orderDTO.getSubtotal());
        assertThat(mappedOrder.getStatus()).isNotNull();
        assertThat(mappedOrder.getData()).isNotNull();
    }
    @Test
    void give_OrderList_to_OrderResponseList() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder()
                        .id(1)
                        .items(new ArrayList<>())
                        .subTotal(13.9)
                        .total(8.0)
                        .email("email")
                        .status(OrderStatus.PROCESSING)
                .build());

        List<OrderResponse> orderResponses = orderMapper.OrderToOrderResponse(orderList);

        assertThat(orderResponses.get(0).getEmail()).isEqualTo(orderList.get(0).getEmail());
        assertThat(orderResponses.get(0).getItems().size()).isEqualTo(orderList.get(0).getItems().size());
        assertThat(orderResponses.get(0).getTotal()).isEqualTo(orderList.get(0).getTotal());
        assertThat(orderResponses.get(0).getTax()).isEqualTo(orderList.get(0).getTax());
        assertThat(orderResponses.get(0).getSubtotal()).isEqualTo(orderList.get(0).getSubTotal());
        assertThat(orderResponses.get(0).getStatus()).isEqualTo(orderList.get(0).getStatus());
        assertThat(orderResponses.get(0).getId()).isEqualTo(orderList.get(0).getId());
    }

    @Test
    void should_give_order_To_OrderDetails(){
        Order order = Order.builder()
                .id(1)
                .items(new ArrayList<>())
                .subTotal(13.9)
                .total(8.0)
                .email("email")
                .status(OrderStatus.PROCESSING)
                .build();

        OrderDetails orderDetails = orderMapper.OrderToOrderDetails(order);

        assertThat(orderDetails.getId()).isEqualTo(order.getId());
        assertThat(orderDetails.getItems().size()).isEqualTo(0);
        assertThat(orderDetails.getCustomerEmail()).isEqualTo(orderDetails.getCustomerEmail());
        assertThat(orderDetails.getStatus()).isEqualTo(orderDetails.getStatus());
        assertThat(orderDetails.getTotalAmount()).isEqualTo(orderDetails.getTotalAmount());
    }

    @Test
    void should_give_ListOrders_to_OrderDetails(){
        Order order = Order.builder()
                .id(1)
                .items(new ArrayList<>())
                .subTotal(13.9)
                .total(8.0)
                .email("email")
                .status(OrderStatus.PROCESSING)
                .build();

        List<OrderDetails> orderDetails = orderMapper.OrderToOrderDetailsList(List.of(order));

        assertThat(orderDetails.get(0).getId()).isEqualTo(order.getId());
        assertThat(orderDetails.get(0).getItems().size()).isEqualTo(0);
        assertThat(orderDetails.get(0).getCustomerEmail()).isEqualTo(orderDetails.get(0).getCustomerEmail());
        assertThat(orderDetails.get(0).getStatus()).isEqualTo(orderDetails.get(0).getStatus());
        assertThat(orderDetails.get(0).getTotalAmount()).isEqualTo(orderDetails.get(0).getTotalAmount());
    }

    @Test
    void should_give_orderItems_to_OrderItemDetails(){
        OrderItem orderItemTest = OrderItem.builder()
                .id(1)
                .title("title")
                .quantity(10)
                .price(8.0)
                .build();

        OrderItemDetails orderItemDetails = orderMapper.OrderItemToOrderItemDetails(orderItemTest);

        assertThat(orderItemDetails.getBookId()).isEqualTo(orderItemTest.getId());
        assertThat(orderItemDetails.getQuantity()).isEqualTo(orderItemTest.getQuantity());
        assertThat(orderItemDetails.getPrice()).isEqualTo(orderItemTest.getPrice());
    }



}