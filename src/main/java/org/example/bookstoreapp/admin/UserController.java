package org.example.bookstoreapp.admin;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.user.UserResponse;
import org.example.bookstoreapp.user.UserRoleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/user")
public class UserController {
    private final UserAdminService userAdminService;
    @GetMapping("getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUser() {
        return  ResponseEntity.status(HttpStatus.OK).body(userAdminService.getAllUser());
    }

    @PutMapping("updateStatus/{userId}")
    public ResponseEntity<String> updateStatus(@PathVariable int userId,@RequestParam boolean status){
        return ResponseEntity.status(HttpStatus.CREATED).body(userAdminService.changeStatus(userId, status));
    }

    @PutMapping("updateRole/{userId}")
    public ResponseEntity<String> updateRole(@PathVariable int userId, @RequestBody UserRoleResponse userRoleResponse){
        return ResponseEntity.status(HttpStatus.CREATED).body(userAdminService.changeRole(userId, userRoleResponse.getRole()));
    }

}
