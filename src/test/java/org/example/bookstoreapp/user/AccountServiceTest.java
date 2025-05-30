package org.example.bookstoreapp.user;

import org.example.bookstoreapp.modelDTO.UserDTO;
import org.example.bookstoreapp.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    private UserDTO userDTO;
    private User existingUser;
    private final String email = "test@example.com";
    private User mappedUser;



    @BeforeEach
    void setUp() {

        userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setPhone(1234567890L);
        userDTO.setAddress("123 Main St");
        userDTO.setCity("Springfield");
        userDTO.setState("IL");
        userDTO.setCountry("USA");
        userDTO.setZipCode(62701);

        existingUser = new User();
        existingUser.setEmail(email);

        mappedUser = new User();
        mappedUser.setFullName("John Doe");
        mappedUser.setPhoneNumber(1234567890L);
        mappedUser.setAddress("123 Main St");
        mappedUser.setCity("Springfield");
        mappedUser.setState("IL");
        mappedUser.setCountry("USA");
        mappedUser.setZipCode(62701);

    }

    @Test
    void setAccountDetails_should_update_account() {

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(existingUser));
        when(userMapper.UserDtoToUser(userDTO)).thenReturn(mappedUser);
        when(userRepo.save(any(User.class))).thenReturn(existingUser);


        String result = accountService.setAccountDetails(email, userDTO);


        assertEquals("Account " + email + " details updated successfully", result);
        verify(userRepo,times(1)).findByEmail(email);
        verify(userMapper,times(1)).UserDtoToUser(userDTO);
        verify(userRepo,times(1)).save(existingUser);
        assertEquals(mappedUser.getFullName(), existingUser.getFullName());
        assertEquals(mappedUser.getPhoneNumber(), existingUser.getPhoneNumber());
        assertEquals(mappedUser.getAddress(), existingUser.getAddress());
        assertEquals(mappedUser.getCity(), existingUser.getCity());
        assertEquals(mappedUser.getState(), existingUser.getState());
        assertEquals(mappedUser.getCountry(), existingUser.getCountry());
        assertEquals(mappedUser.getZipCode(), existingUser.getZipCode());
    }


    @Test
    void getAccountDetails_should_return_user() {


        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(existingUser));
        when(userMapper.UserToUserDTO(existingUser)).thenReturn(userDTO);

        UserDTO accountDetails = accountService.getAccountDetails(existingUser.getEmail());

        assertNotNull(accountDetails);
        assertEquals(userDTO, accountDetails);
    }

    @Test
    void getAccountDetails_should_throw_exception() {
        assertThatThrownBy(
                () -> accountService.getAccountDetails(""))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Account not found");
    }
}