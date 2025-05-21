package org.example.bookstoreapp.admin;


import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.order.OrderDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/order")
public class OrderAdminController {
    private final OrderAdminService orderAdminService;

    @GetMapping("getAllOrders")
    public ResponseEntity<List<OrderDetails>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderAdminService.getAllOrders());
    }
}
