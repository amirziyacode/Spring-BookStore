package org.example.bookstoreapp.admin;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.user.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
