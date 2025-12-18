package com.practice.thoughtstream.repository;

import com.practice.thoughtstream.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SearchResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {

    List<Post> findAllByUsers_Id(String usersId, Pageable pageable);
}
