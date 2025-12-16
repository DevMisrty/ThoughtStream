package com.practice.thoughtstream.security;

import com.practice.thoughtstream.model.Users;
import com.practice.thoughtstream.repository.UsersRepository;
import com.practice.thoughtstream.utility.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = repository.findUsersByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(MessageConstants.USER_NOT_FOUND));
        return new User(
                users.getEmail(),
                users.getPassword(),
                List.of(new SimpleGrantedAuthority("ADMIN"))
        );
    }
}
