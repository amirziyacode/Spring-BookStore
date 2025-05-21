package org.example.bookstoreapp.admin;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.book.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.allBooks());
    }

    @GetMapping("getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUser() {
        return  ResponseEntity.status(HttpStatus.OK).body(adminService.getAllUser());
    }

    @GetMapping("getAllOrders")
    public ResponseEntity<List<OrderDetails>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllOrders());
    }
}
