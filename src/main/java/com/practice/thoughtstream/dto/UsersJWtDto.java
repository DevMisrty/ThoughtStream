package com.practice.thoughtstream.dto;

import com.practice.thoughtstream.model.UsersRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UsersJWtDto {

    private String email;
    private UsersRole role;

}
