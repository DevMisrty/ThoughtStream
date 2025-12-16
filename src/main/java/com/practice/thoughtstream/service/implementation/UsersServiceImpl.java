package com.practice.thoughtstream.service.implementation;

import com.practice.thoughtstream.dto.request.RegisterUserRequestDto;
import com.practice.thoughtstream.dto.response.UserResponseDto;
import com.practice.thoughtstream.model.Users;
import com.practice.thoughtstream.model.UsersRole;
import com.practice.thoughtstream.repository.UsersRepository;
import com.practice.thoughtstream.service.UsersService;
import com.practice.thoughtstream.utility.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;

@Component
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

    @Override
    public UserResponseDto saveUsers(RegisterUserRequestDto requestDto) {
        Users user = modelMapper.map(requestDto, Users.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(UsersRole.USERS);
        Users savedUser = repo.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @Override
    public Users findUserEmail(String email) {
        return repo.findUsersByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(MessageConstants.USER_NOT_FOUND));
    }
}
