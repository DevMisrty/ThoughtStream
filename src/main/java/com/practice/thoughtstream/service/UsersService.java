package com.practice.thoughtstream.service;

import com.practice.thoughtstream.dto.request.RegisterUserRequestDto;
import com.practice.thoughtstream.dto.response.UserResponseDto;
import com.practice.thoughtstream.model.Users;
import com.practice.thoughtstream.model.UsersRole;

public interface UsersService {
    UserResponseDto saveUsers(RegisterUserRequestDto requestDto, UsersRole role);

    Users findUserEmail(String email);
}
