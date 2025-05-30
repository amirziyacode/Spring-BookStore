package org.example.bookstoreapp.modelDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
    private String name;
    private  Long  phone;
    private  String email;
    private String address;
    private  String city;
    private  String state;
    private  Integer zipCode;
    private  String country;
}
