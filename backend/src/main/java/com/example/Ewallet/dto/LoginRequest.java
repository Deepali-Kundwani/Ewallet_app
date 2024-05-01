package com.example.Ewallet.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequest {

    @NotEmpty(message = "Email cannot be empty")
    @Pattern(regexp = "^([a-zA-Z0-9\\._-]+)@([a-zA-Z0-9-])+\\.([a-z]+)(.[a-z]+)?$", message = "This is not valid email format")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters in length")
    private String password;
}
