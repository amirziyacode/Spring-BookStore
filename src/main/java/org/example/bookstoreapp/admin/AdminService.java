package org.example.bookstoreapp.admin;


import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.book.BookRepo;
import org.example.bookstoreapp.mapper.UserMapper;
import org.example.bookstoreapp.order.Order;
import org.example.bookstoreapp.order.OrderRepo;
import org.example.bookstoreapp.user.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BookRepo bookRepo;
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final UserMapper userMapper;

    public List<Book> allBooks() {
        return bookRepo.findAll();
    }

    public List<UserResponse> getAllUser(){
        return userMapper.UserToUserResponseList(userRepo.findAll());
    }

    public List<Order> getAllOrders() {return orderRepo.findAll();}
}
