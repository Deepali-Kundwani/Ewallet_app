package com.example.Ewallet.kafka.dto;

import com.example.Ewallet.collections.PendingUser;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailVerificationMessage {
    private PendingUser pendingUser;
    private String siteURL1;

}
