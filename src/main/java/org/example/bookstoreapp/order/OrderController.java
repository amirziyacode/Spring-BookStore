package org.example.bookstoreapp.order;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.dto.OrderDTO;
import org.example.bookstoreapp.dto.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("addOrder/{email}")
    public ResponseEntity<OrderMassage> addOrder(@PathVariable String email, @RequestBody OrderDTO order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(email,order));
    }

    @GetMapping("getAllOrders/{email}")
    public ResponseEntity<List<OrderResponse>> getAllOrders(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders(email));
    }
}
