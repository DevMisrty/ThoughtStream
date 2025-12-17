package com.practice.thoughtstream.controller;

import com.practice.thoughtstream.dto.ApiResponse;
import com.practice.thoughtstream.dto.RefreshTokenDto;
import com.practice.thoughtstream.dto.UsersJWtDto;
import com.practice.thoughtstream.dto.request.LoginRequestDto;
import com.practice.thoughtstream.dto.request.RegisterUserRequestDto;
import com.practice.thoughtstream.dto.response.UserResponseDto;
import com.practice.thoughtstream.exceptionHandler.exception.TokenExpireException;
import com.practice.thoughtstream.model.Users;
import com.practice.thoughtstream.model.UsersRole;
import com.practice.thoughtstream.service.UsersService;
import com.practice.thoughtstream.utility.JwtUtility;
import com.practice.thoughtstream.utility.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;
    private final DefaultAuthenticationEventPublisher authenticationEventPublisher;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> getAdminRegister(@RequestBody RegisterUserRequestDto requestDto){
        UserResponseDto userResponseDto = usersService.saveUsers(requestDto, UsersRole.ADMIN);
        return ApiResponse.response(HttpStatus.CREATED, MessageConstants.USER_CREATED, userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String,String>>> getAdminToken(@RequestBody LoginRequestDto requestDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()
                )
        );

        Users users = usersService.findUserEmail(requestDto.getEmail());
        Map<String,String> map = getTokens(users);

        return ApiResponse.response(HttpStatus.ACCEPTED, MessageConstants.LOGIN_SUCCESS, map);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Map<String,String>>> getAdminRefreshTokens(@RequestBody RefreshTokenDto refreshTokenDto) throws TokenExpireException {

        if( !jwtUtility.isValidToken(refreshTokenDto.getRefreshToken())){
            throw new TokenExpireException(MessageConstants.TOKEN_EXPIRE);
        }

        String email = jwtUtility.getEmailFromToken(refreshTokenDto.getRefreshToken());
        Users users = usersService.findUserEmail(email);
        var tokens = getTokens(users);

        return ApiResponse.response(HttpStatus.ACCEPTED, MessageConstants.LOGIN_SUCCESS, tokens);

    }

    private Map<String,String> getTokens(Users users){
        Map<String,String> tokens = new HashMap<>();
        tokens.put("accesstoken", jwtUtility.getJwtToken(new UsersJWtDto(users.getEmail(),users.getRole())));
        tokens.put("refreshToken", jwtUtility.getRefreshToken(new UsersJWtDto(users.getEmail(), users.getRole())));

        return tokens;
    }
}
