package com.practice.thoughtstream.repository;

import com.practice.thoughtstream.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {
    Optional<Users> findUsersByEmail(String email);

    boolean existsByEmail(String email);

    List<Users> findAllByEmail(String email);
}
