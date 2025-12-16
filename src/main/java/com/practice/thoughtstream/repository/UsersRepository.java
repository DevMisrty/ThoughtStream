package com.practice.thoughtstream.repository;

import com.practice.thoughtstream.model.Users;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends ListCrudRepository<Users, String> {
    Optional<Users> findUsersByEmail(String email);
}
