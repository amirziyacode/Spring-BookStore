package org.example.bookstoreapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.order.Order;
import org.example.bookstoreapp.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("addOrder")
    public ResponseEntity<Order> addOrder(@RequestParam String email, @RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(email,order));
    }

    @GetMapping("getAllOrders")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders(email));
    }
}
