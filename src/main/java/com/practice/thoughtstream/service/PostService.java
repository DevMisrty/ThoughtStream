package com.practice.thoughtstream.service;

import com.practice.thoughtstream.dto.PostDto;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface PostService {
    PostDto addNewPost(PostDto postDto, String email);

    void deletePost(String id, String email) throws BadRequestException;

    PostDto updatePostContent(String id, PostDto postDto, String email) throws BadRequestException;

    PostDto getPost(String id);

    List<PostDto> getAllMyPost(String id, Integer page);
}
