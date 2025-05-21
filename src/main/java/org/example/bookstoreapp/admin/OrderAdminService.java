package org.example.bookstoreapp.admin;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.mapper.OrderMapper;
import org.example.bookstoreapp.order.OrderDetails;
import org.example.bookstoreapp.order.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderAdminService {

    private final OrderMapper orderMapper;
    private final OrderRepo orderRepo;

    public List<OrderDetails> getAllOrders() {return  orderMapper.OrderToOrderDetailsList(orderRepo.findAll());}

}
