package com.practice.thoughtstream.controller;

import com.practice.thoughtstream.dto.ApiResponse;
import com.practice.thoughtstream.dto.PostDto;
import com.practice.thoughtstream.exceptionHandler.exception.InvalidTokenException;
import com.practice.thoughtstream.model.Category;
import com.practice.thoughtstream.service.PostService;
import com.practice.thoughtstream.utility.JwtUtility;
import com.practice.thoughtstream.utility.MessageConstants;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtUtility jwtUtility;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<PostDto>> addNewPost(@RequestBody PostDto postDto, HttpServletRequest request) throws InvalidTokenException {

        String email = validateRequestAndGiveEmail(request);

        PostDto responseDto = postService.addNewPost(postDto, email);

        return ApiResponse.response(HttpStatus.CREATED, MessageConstants.BLOG_CREATED, responseDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable String id, HttpServletRequest request)
            throws InvalidTokenException, BadRequestException {

        String email = validateRequestAndGiveEmail(request);

        postService.deletePost(id, email);

        return ApiResponse.response(HttpStatus.NO_CONTENT, MessageConstants.BLOG_DELETED, null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDto>> updatePost(@RequestBody PostDto postDto , @PathVariable String id, HttpServletRequest request)
            throws InvalidTokenException, BadRequestException {

        String email = validateRequestAndGiveEmail(request);

        PostDto responseDto  = postService.updatePostContent(id, postDto, email );
        return ApiResponse.response(HttpStatus.ACCEPTED, MessageConstants.BLOG_UPDATED, responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDto>> getPostByPostId(@PathVariable String id, HttpServletRequest request) {

        PostDto response = null;
        try{
            String email = validateRequestAndGiveEmail(request);
            response = postService.getPost(id, email);

        }catch(InvalidTokenException e){
            response = postService.getPost(id, null);
        }

        return ApiResponse.response(HttpStatus.OK, MessageConstants.BLOG_FETCHED, response);
    }

    @GetMapping("/{id}/{page}")
    public ResponseEntity<ApiResponse<List<PostDto>>> getAllMyPost(@PathVariable String id ,@PathVariable Integer page, HttpServletRequest request)
            throws InvalidTokenException {

        if(page<0)page=0;

        List<PostDto> response = null;

        try{
            String email = validateRequestAndGiveEmail(request);
            response = postService.getAllMyPost(id, email, page);

        }catch(InvalidTokenException e){
            response = postService.getAllMyPost(id,null, page);
        }

        return ApiResponse.response(HttpStatus.OK, MessageConstants.BLOG_FETCHED, response);
    }

    @GetMapping("/category/{category}/{page}")
    public ResponseEntity<ApiResponse<List<PostDto>>> getPostByCategory(@PathVariable String category, @PathVariable Integer page) throws InvalidTokenException {

        if(page< 0)page = 0;

        if(category == null || isValidCategory(category)){
           throw new InvalidTokenException(MessageConstants.INVALID_REQUEST);
        }

        List<PostDto> response = postService.getPostByCategory(category, page);
        return ApiResponse.response(HttpStatus.OK,MessageConstants.BLOG_FETCHED, response);
    }

    @GetMapping("/tags/{page}")
    public ResponseEntity<ApiResponse<List<PostDto>>> getPostByTag(@RequestParam List<String> tags, @PathVariable Integer page){

        if(tags.isEmpty()){
            throw new InvalidRequestStateException(MessageConstants.INVALID_REQUEST);
        }

        if(page < 0)page =0;

        List<PostDto> response = postService.getPostByTags(tags, page);
        return ApiResponse.response(HttpStatus.OK, MessageConstants.BLOG_FETCHED, response);
    }

    private String validateRequestAndGiveEmail(HttpServletRequest request) throws InvalidTokenException {

        String token = request.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")){
            throw new InvalidTokenException(MessageConstants.INVALID_REQUEST);
        }

        token = token.substring(7);
        return jwtUtility.getEmailFromToken(token);
    }

    private boolean isValidCategory(String value) {
        try {
            Category category = Category.valueOf(value.toUpperCase()); // enum constants are usually uppercase
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
