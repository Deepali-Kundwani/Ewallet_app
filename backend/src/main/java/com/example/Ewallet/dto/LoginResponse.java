package com.example.Ewallet.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LoginResponse {
    private String jwtToken;
    private String username;
}
