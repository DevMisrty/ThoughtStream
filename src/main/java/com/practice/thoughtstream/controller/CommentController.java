package com.practice.thoughtstream.controller;

import com.practice.thoughtstream.dto.ApiResponse;
import com.practice.thoughtstream.dto.CommentDto;
import com.practice.thoughtstream.dto.PostDto;
import com.practice.thoughtstream.dto.response.PostResponseDto;
import com.practice.thoughtstream.exceptionHandler.exception.InvalidTokenException;
import com.practice.thoughtstream.model.Post;
import com.practice.thoughtstream.service.CommentService;
import com.practice.thoughtstream.utility.JwtUtility;
import com.practice.thoughtstream.utility.MessageConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtUtility jwtUtility;
    private final ModelMapper modelMapper;

    @PostMapping("/add/{postid}")
    public ResponseEntity<ApiResponse<PostDto>> addCommentToPost(
            @PathVariable String postid,
            @RequestBody CommentDto commentDto,
            HttpServletRequest request) throws InvalidTokenException {

        System.out.println("Add comment to post controller started");

        String email = validateTokenAndReturnEmail(request);
        System.out.println("Email is : " + email);

        var response = commentService.addComment(commentDto, postid, email);

        System.out.println("Add comment to post controller ended");

        return ApiResponse.response(HttpStatus.CREATED, MessageConstants.COMMENT_ADDED, response);
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<ApiResponse<PostResponseDto>> updateComment(
            @RequestBody CommentDto commentDto,
            @PathVariable String postId,
            @PathVariable String commentId,
            HttpServletRequest request
    ) throws InvalidTokenException, BadRequestException {

        String email = validateTokenAndReturnEmail(request);

        var response = commentService.updateComment(commentDto, postId, commentId, email);

        return ApiResponse.response(HttpStatus.ACCEPTED, MessageConstants.COMMENT_UPDATED, response);
    }

    @DeleteMapping("/{postid}/{commentid}")
    public ResponseEntity<ApiResponse<Void>> deletePostComment(
            @PathVariable String postid,
            @PathVariable String commentid,
            HttpServletRequest request
    ) throws InvalidTokenException {
        String email = validateTokenAndReturnEmail(request);

        commentService.deleteComment(postid, commentid, email);

        return ApiResponse.response(HttpStatus.NO_CONTENT, MessageConstants.COMMENT_DELETED, null);

    }

    private String validateTokenAndReturnEmail(HttpServletRequest request) throws InvalidTokenException {
        String token = request.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")){
            throw new InvalidTokenException(MessageConstants.INVALID_REQUEST);
        }

        token = token.substring(7);

        return jwtUtility.isValidToken(token)?jwtUtility.getEmailFromToken(token) : null;
    }
}
