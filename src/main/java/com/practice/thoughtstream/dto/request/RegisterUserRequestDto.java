package com.practice.thoughtstream.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class RegisterUserRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
}
