package org.example.bookstoreapp.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AccountResponse {
    private String name;
    private Long  phone;
    private String email;
    private String address;
    private String city;
    private String state;
    private Integer zipCode;
    private String country;
}
