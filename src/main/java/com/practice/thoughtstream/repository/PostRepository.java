package com.practice.thoughtstream.repository;

import com.practice.thoughtstream.model.Category;
import com.practice.thoughtstream.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SearchResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String> {

    Page<Post> findAllByUsers_Id(String usersId, Pageable pageable);

    Page<Post> findAllByCategory(Category category, Pageable pageable);

    Page<Post> findAllByTagsIn(List<String> tags, Pageable pageable);

    Optional<Post> findPostById(String id);
}
