package org.example.bookstoreapp.admin;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer id;
    private String email;
    private String name;
    @JsonProperty("isActive")
    private boolean isActive;
    private String createdAt;
    private String role;
}
