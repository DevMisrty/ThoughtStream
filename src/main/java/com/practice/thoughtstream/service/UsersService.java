package com.practice.thoughtstream.service;

import com.practice.thoughtstream.dto.request.RegisterUserRequestDto;
import com.practice.thoughtstream.dto.response.UserResponseDto;
import com.practice.thoughtstream.model.Users;

public interface UsersService {
    UserResponseDto saveUsers(RegisterUserRequestDto requestDto);

    Users findUserEmail(String email);
}
