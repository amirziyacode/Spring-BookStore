package org.example.bookstoreapp.mapper;



import org.assertj.core.api.Assertions;
import org.example.bookstoreapp.modelDTO.UserDTO;
import org.example.bookstoreapp.user.Role;
import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserMapperImpTest {


    @InjectMocks
    private UserMapperImp userMapper;



    @Test
    void give_user_to_userDTO() {
        String email = "test@example.com";
        User mockUser = User.builder()
                .email(email)
                .fullName("John Doe")
                .city("New York")
                .country("USA")
                .address("123 Main St")
                .phoneNumber(1234567890L)
                .state("NY")
                .zipCode(10001)
                .build();


        UserDTO result = userMapper.UserToUserDTO(mockUser);

        assertNotNull(result);
        assertEquals(mockUser.getFullName(), result.getName());
        assertEquals(mockUser.getEmail(), result.getEmail());
        assertEquals(mockUser.getCity(), result.getCity());
        assertEquals(mockUser.getCountry(), result.getCountry());
        assertEquals(mockUser.getAddress(), result.getAddress());
        assertEquals(mockUser.getPhoneNumber(), result.getPhone());
        assertEquals(mockUser.getState(), result.getState());
        assertEquals(mockUser.getZipCode(), result.getZipCode());

    }

    @Test
    void user_not_found() {
        RuntimeException fakeEmail = assertThrows(RuntimeException.class, () -> userMapper.UserToUserDTO(null));
        Assertions.assertThat(fakeEmail.getMessage()).isEqualTo("User Not Found");
    }

    @Test
    void give_userDTO_to_user() {
        UserDTO userDto = UserDTO.builder()
                .name("John Doe")
                .state("USA")
                .city("New York")
                .country("USA")
                .address("123 Main St")
                .phone(1234567890L)
                .zipCode(10001)
                .build();
        User user = userMapper.UserDtoToUser(userDto);

        assertNotNull(user);
        Assertions.assertThat(userDto.getName()).isEqualTo(user.getFullName());
        Assertions.assertThat(userDto.getState()).isEqualTo(user.getState());
        Assertions.assertThat(userDto.getCity()).isEqualTo(user.getCity());
        Assertions.assertThat(userDto.getCountry()).isEqualTo(user.getCountry());
        Assertions.assertThat(userDto.getAddress()).isEqualTo(user.getAddress());
        Assertions.assertThat(userDto.getPhone()).isEqualTo(user.getPhoneNumber());
        Assertions.assertThat(userDto.getZipCode()).isEqualTo(user.getZipCode());

    }
    @Test
    void should_give_user_to_userResponse(){
        User mockUser = User.builder()
                .id(1)
                .email("email")
                .fullName("John Doe")
                .city("New York")
                .country("USA")
                .address("123 Main St")
                .phoneNumber(1234567890L)
                .state("NY")
                .isActive(true)
                .role(Role.USER)
                .zipCode(10001)
                .build();
        UserResponse userResponse = UserResponse.builder()
                .id(1)
                .email("email")
                .name("John Doe")
                .isActive(true)
                .role("USER")
                .build();

        UserResponse userToUserResponse = userMapper.UserToUserResponse(mockUser);

        assertNotNull(userToUserResponse);
        assertEquals(userResponse.getId(), userToUserResponse.getId());
        assertEquals(userResponse.getName(), userToUserResponse.getName());
        assertEquals(userResponse.isActive(), userToUserResponse.isActive());
        assertEquals(userResponse.getRole(), userToUserResponse.getRole());
        assertEquals(userResponse.getEmail(), userToUserResponse.getEmail());
    }

    @Test
    void should_give_user_to_List_of_userResponse(){
        User mockUser = User.builder()
                .id(1)
                .email("email")
                .fullName("John Doe")
                .city("New York")
                .country("USA")
                .address("123 Main St")
                .phoneNumber(1234567890L)
                .state("NY")
                .isActive(true)
                .role(Role.USER)
                .zipCode(10001)
                .build();


        UserResponse userResponse = UserResponse.builder()
                .id(1)
                .email("email")
                .name("John Doe")
                .isActive(true)
                .role("USER")
                .build();


        List<UserResponse> useredToUserResponseList = userMapper.UserToUserResponseList(List.of(mockUser));


        assertNotNull(useredToUserResponseList);
        assertEquals(useredToUserResponseList.get(0).getId(), userResponse.getId());
        assertEquals(useredToUserResponseList.get(0).getName(), userResponse.getName());
        assertEquals(useredToUserResponseList.get(0).getEmail(), userResponse.getEmail());
        assertEquals(useredToUserResponseList.get(0).getRole(), userResponse.getRole());
        assertEquals(useredToUserResponseList.get(0).isActive(), userResponse.isActive());
    }
}