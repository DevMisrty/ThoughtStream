package com.practice.thoughtstream.service.implementation;

import com.practice.thoughtstream.dto.PostDto;
import com.practice.thoughtstream.model.Post;
import com.practice.thoughtstream.model.Users;
import com.practice.thoughtstream.repository.PostRepository;
import com.practice.thoughtstream.repository.UsersRepository;
import com.practice.thoughtstream.service.PostService;
import com.practice.thoughtstream.utility.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repo;
    private final ModelMapper modelMapper;
    private final UsersRepository userRepo;

    @Value("${pagination.pageSize}")
    private Integer pageSize;


    @Override
    public PostDto addNewPost(PostDto postDto, String email) {
        Post post = modelMapper.map(postDto, Post.class);

        Users users = userRepo.findUsersByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(MessageConstants.USER_NOT_FOUND));

        post.setUsers(users);

        repo.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePost(String id, String email) throws BadRequestException {
        isValidPost(id);

        Post post = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND));

        if(!post.getUsers().getEmail().equals(email)){
            throw new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND);
        }

        repo.deleteById(id);
    }

    @Override
    public PostDto updatePostContent(String id, PostDto postDto, String email) throws BadRequestException {
        isValidPost(id);

        Post post = repo.findById(id)
                .orElseThrow(()-> new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND));
        
        if(!post.getUsers().getEmail().equals(email)){
            throw new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND);
        }

        post = modelMapper.map(postDto, Post.class);
        post.setId(id);

        Post savedPost = repo.save(post);

        return modelMapper.map(savedPost, PostDto.class);


    }

    @Override
    public PostDto getPost(String id) {
        isValidPost(id);

        Post post = repo.findById(id)
                .orElseThrow(()-> new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND));

         return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllMyPost(String id, Integer page) {

        if(!userRepo.existsById(id)){
            throw new UsernameNotFoundException(MessageConstants.USER_NOT_FOUND);
        }
        return getPageablePostDto(id,page);
    }

    private List<PostDto> getPageablePostDto(String id ,Integer page){
        Pageable pageable = PageRequest.of(page,pageSize);
        List<Post> allPost = repo.findAllByUsers_Id(id, pageable);

        return allPost
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }


    private void isValidPost(String id){
        if(!repo.existsById(id)){
            throw new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND);
        }
    }
}
