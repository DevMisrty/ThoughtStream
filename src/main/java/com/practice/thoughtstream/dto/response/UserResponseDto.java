package com.practice.thoughtstream.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserResponseDto {

    private String firstName;
    private String lastName;
    private String email;
}
