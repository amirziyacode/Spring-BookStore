package org.example.bookstoreapp.mapper;



import org.assertj.core.api.Assertions;
import org.example.bookstoreapp.dto.UserDTO;
import org.example.bookstoreapp.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
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
}