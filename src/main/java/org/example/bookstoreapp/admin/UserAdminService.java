package org.example.bookstoreapp.admin;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.mapper.UserMapper;
import org.example.bookstoreapp.user.Role;
import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserRepo;
import org.example.bookstoreapp.user.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAdminService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUser(){
        return userMapper.UserToUserResponseList(userRepo.findAll());
    }

    public String changeStatus(int userId, boolean status){
        userRepo.findById(userId).ifPresent(user -> {
            user.setActive(status);
            userRepo.save(user);
        });

        return "User Status Changed";
    }

    public String changeRole(int userId, String role){

        Optional<User> byId = userRepo.findById(userId);

        if(byId.isPresent() && byId.get().getRole().equals(Role.ADMIN)){
            throw new IllegalArgumentException("You are not allowed to change the this user's role");
        }

        byId.ifPresent(user -> {
             try{
                 Role roleEnum = Role.valueOf(role);
                 user.setRole(roleEnum);
                 userRepo.save(user);
             }catch (IllegalArgumentException e){
                 throw new RuntimeException("Invalid order status: " + role);
             }
        });

        return "User Role Changed";
    }

}
