package com.practice.thoughtstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Data
@NoArgsConstructor @AllArgsConstructor
public class Users {

    @MongoId
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private UsersRole role;
}
