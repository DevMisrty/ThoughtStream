package com.practice.thoughtstream.service.implementation;

import com.practice.thoughtstream.dto.CommentDto;
import com.practice.thoughtstream.dto.response.PostResponseDto;
import com.practice.thoughtstream.model.Comment;
import com.practice.thoughtstream.model.Post;
import com.practice.thoughtstream.model.Users;
import com.practice.thoughtstream.repository.PostRepository;
import com.practice.thoughtstream.repository.UsersRepository;
import com.practice.thoughtstream.service.CommentService;
import com.practice.thoughtstream.utility.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository repo;
    private final UsersRepository userRepo;
    private final ModelMapper modelMapper;

    @Override
    public PostResponseDto addComment(CommentDto commentDto, String postid, String email) {

        Users user = userRepo.findUsersByEmail(email)
                .orElseThrow(() -> new NoSuchElementException(MessageConstants.USER_NOT_FOUND));

        Comment comment = Comment.builder()
                .commentId(UUID.randomUUID().toString())
                .userId(user.getId())
                .name(user.getFirstName())
                .createdAt(LocalDateTime.now())
                .lastModified(LocalDateTime.now())
                .message(commentDto.getMessage())
                .name(user.getFirstName() + " " + user.getLastName())
                .build();

        Post post = repo.findPostById(postid)
                .orElseThrow(() -> new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND));

        if (post.getComments() == null) {
            post.setComments(new ArrayList<>());
        }

        post.getComments().add(comment);

        Post savedPost = repo.save(post);

        return modelMapper.map(savedPost, PostResponseDto.class);

    }

    @Override
    public PostResponseDto updateComment(CommentDto commentDto, String postId, String commentId, String email) throws BadRequestException {

        Post post = repo.findById(postId)
                .orElseThrow(() -> new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND));

        Comment comment = post.getComments().stream()
                .filter(com -> com.getCommentId().equals(commentId))
                .findAny()
                .orElseThrow(()-> new NoSuchElementException(MessageConstants.COMMENT_NOT_FOUND));

        comment.setMessage(commentDto.getMessage());
        comment.setLastModified(LocalDateTime.now());

        Users users = userRepo.findUsersByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(MessageConstants.USER_NOT_FOUND));

        if(!users.getId().equals(comment.getUserId())){
            throw new BadRequestException(MessageConstants.INVALID_REQUEST);
        }

        Post savedPost = repo.save(post);
        return modelMapper.map(savedPost, PostResponseDto.class);
    }

    @Override
    public void deleteComment(String postid, String commentid, String email) {

        Post post = repo.findPostById(postid)
                .orElseThrow(()-> new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND));

        Users users = userRepo.findUsersByEmail(email)
                .orElseThrow(()-> new NoSuchElementException(MessageConstants.USER_NOT_FOUND));

        post.getComments().removeIf(comment ->
                comment.getCommentId().equals(commentid) && !comment.getUserId().equals(users.getId()) );

        Post savedPost = repo.save(post);
    }

}