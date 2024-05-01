package com.example.Ewallet.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransferRequest {
    private String email;
    private Double amount;
}
