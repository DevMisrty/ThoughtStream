package com.practice.thoughtstream.service;

import com.practice.thoughtstream.dto.CommentDto;
import com.practice.thoughtstream.dto.response.PostResponseDto;
import org.apache.coyote.BadRequestException;

public interface CommentService {
    PostResponseDto addComment(CommentDto commentDto, String postid, String email);

    PostResponseDto updateComment(CommentDto commentDto, String postId, String commentId, String email) throws BadRequestException;

    void deleteComment(String postid, String commentid, String email);
}
