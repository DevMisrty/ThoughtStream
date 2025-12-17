package com.practice.thoughtstream.controller;

import com.practice.thoughtstream.dto.ApiResponse;
import com.practice.thoughtstream.dto.request.LoginRequestRequestDto;
import com.practice.thoughtstream.dto.UsersJWtDto;
import com.practice.thoughtstream.dto.request.RegisterUserRequestDto;
import com.practice.thoughtstream.dto.response.UserResponseDto;
import com.practice.thoughtstream.model.Users;
import com.practice.thoughtstream.service.UsersService;
import com.practice.thoughtstream.utility.JwtUtility;
import com.practice.thoughtstream.utility.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>>  registerUser(@RequestBody RegisterUserRequestDto requestDto){
        UserResponseDto response = usersService.saveUsers(requestDto);
        return ApiResponse.response(HttpStatus.CREATED, MessageConstants.USER_CREATED, response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String,String>>>  loginUser(@RequestBody LoginRequestRequestDto requestDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()
                )
        );

        Users users = usersService.findUserEmail(requestDto.getEmail());

        Map<String,String> tokens = new HashMap<>();
        tokens.put("accesstoken", jwtUtility.getJwtToken(new UsersJWtDto(users.getEmail(),users.getRole())));
        tokens.put("refreshToken", jwtUtility.getRefreshToken(new UsersJWtDto(users.getEmail(), users.getRole())));

        return ApiResponse.response(HttpStatus.ACCEPTED, MessageConstants.LOGIN_SUCCESS, tokens );
    }
}