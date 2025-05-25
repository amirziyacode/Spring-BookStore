package org.example.bookstoreapp.admin;

import org.example.bookstoreapp.mapper.UserMapper;
import org.example.bookstoreapp.user.Role;
import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserRepo;
import org.example.bookstoreapp.user.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserAdminServiceTest {

    @InjectMocks
    private UserAdminService userAdminService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepo userRepo;

    private UserResponse userResponse;

    private User user;

    @BeforeEach
    void setUp() {
        userResponse = UserResponse.builder()
                .id(1)
                .role("ADMIN")
                .name("admin")
                .isActive(true)
                .email("admin@gmial")
                .build();

        user = User.builder()
                .id(1)
                .role(Role.USER)
                .fullName("admin")
                .isActive(true)
                .email("admin@gmial")
                .build();
    }

    @Test
    void should_return_all_users_response() {
        when(userRepo.findAll()).thenReturn(anyList());
        when(userMapper.UserToUserResponseList(List.of(user))).thenReturn(List.of(userResponse));

        List<UserResponse> allUser = userAdminService.getAllUser();

        assertEquals(1, allUser.size());
        assertEquals(userResponse, allUser.get(0));
        verify(userRepo, times(1)).findAll();
    }

    @Test
    void should_changeStatus_with_id_successful(){

        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        String result = userAdminService.changeStatus(user.getId(), false);

        assertEquals("User Status Changed", result);
        assertFalse(user.isActive());
        verify(userRepo, times(1)).findById(1);
    }

    @Test
    void should_changeRole_with_id_successful() {
        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));

        String result = userAdminService.changeRole(1, "ADMIN");


        assertEquals("User Role Changed", result);
        assertEquals("ADMIN", String.valueOf(user.getRole()));
        verify(userRepo, times(1)).findById(1);
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void should_changeRole_with_id_invalidRole_throwException() {
        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));

        String message = assertThrows(RuntimeException.class, () -> userAdminService.changeRole(1, "INVALID")).getMessage();

        assertEquals("Invalid user status: INVALID", message);

    }

    @Test
    void should_changeRole_byAdmin_throwException() {
        user.setRole(Role.ADMIN);

        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));

        String message = assertThrows(RuntimeException.class, () -> userAdminService.changeRole(1, "USER")).getMessage();

        assertEquals("You are not allowed to change the this user's role", message);

    }

    @Test
    void should_changeRole_byId_notFoundUser_and_throwException() {
        when(userRepo.findById(anyInt())).thenReturn(Optional.empty());
        String message = assertThrows(RuntimeException.class, () -> userAdminService.changeRole(1, "USER")).getMessage();
        assertEquals("User not found", message);
    }
}