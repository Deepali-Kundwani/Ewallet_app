package com.example.Ewallet.kafka.dto;

import com.example.Ewallet.collections.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GeneralMessage {
    private Double value;
    private User sender;
    private User receiver;
}
