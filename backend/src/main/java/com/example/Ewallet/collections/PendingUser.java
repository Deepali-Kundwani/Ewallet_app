package com.example.Ewallet.collections;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "user_pending")
public class PendingUser {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String token;

    @Indexed(expireAfterSeconds = 3600)
    private Date expirationDate;
}
