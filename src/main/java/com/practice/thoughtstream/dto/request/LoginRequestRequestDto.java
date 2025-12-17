package com.practice.thoughtstream.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginRequestRequestDto {

    private String email;
    private String password;
}