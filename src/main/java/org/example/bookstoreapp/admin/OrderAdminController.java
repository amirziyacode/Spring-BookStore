package org.example.bookstoreapp.admin;


import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.order.OrderDetails;
import org.example.bookstoreapp.order.OrderStatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("updateStatus/{orderId}")
    public ResponseEntity<String> updateStatus(@RequestBody OrderStatusResponse orderStatus, @PathVariable Integer orderId) {
        orderAdminService.changeStatus(orderStatus.getOrderStatus(),orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Status updated successfully");
    }
}
