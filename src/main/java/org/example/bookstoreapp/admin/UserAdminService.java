package org.example.bookstoreapp.admin;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.mapper.UserMapper;
import org.example.bookstoreapp.user.UserRepo;
import org.example.bookstoreapp.user.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAdminService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUser(){
        return userMapper.UserToUserResponseList(userRepo.findAll());
    }

}
