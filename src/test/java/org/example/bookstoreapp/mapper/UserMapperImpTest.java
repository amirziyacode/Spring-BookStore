package org.example.bookstoreapp.mapper;



import org.assertj.core.api.Assertions;
import org.example.bookstoreapp.dto.UserDTO;
import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserMapperImpTest {


    @InjectMocks
    private UserMapperImp userMapper;


    @Mock
    private UserRepo userRepo;

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

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(mockUser));

        UserDTO result = userMapper.UserToUserDTO(email);

        assertNotNull(result);
        assertEquals(mockUser.getFullName(), result.getName());
        assertEquals(mockUser.getEmail(), result.getEmail());
        assertEquals(mockUser.getCity(), result.getCity());
        assertEquals(mockUser.getCountry(), result.getCountry());
        assertEquals(mockUser.getAddress(), result.getAddress());
        assertEquals(mockUser.getPhoneNumber(), result.getPhone());
        assertEquals(mockUser.getState(), result.getState());
        assertEquals(mockUser.getZipCode(), result.getZipCode());

        verify(userRepo,times(1)).findByEmail(email);
    }

    @Test
    void user_not_found() {
        // replace lambda  () - > { userMapper.UserToUserDTO(email) }
        RuntimeException fakeEmail = assertThrows(RuntimeException.class, this::execute);
        Assertions.assertThat(fakeEmail.getMessage()).isEqualTo("User not found");
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

    private void execute() {
        userMapper.UserToUserDTO("fakeEmail");
    }
}