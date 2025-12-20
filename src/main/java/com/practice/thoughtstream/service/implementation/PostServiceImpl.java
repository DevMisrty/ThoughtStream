package com.practice.thoughtstream.service.implementation;

import com.practice.thoughtstream.dto.PostDto;
import com.practice.thoughtstream.model.Category;
import com.practice.thoughtstream.model.Post;
import com.practice.thoughtstream.model.PostStatus;
import com.practice.thoughtstream.model.Users;
import com.practice.thoughtstream.repository.PostRepository;
import com.practice.thoughtstream.repository.UsersRepository;
import com.practice.thoughtstream.service.PostService;
import com.practice.thoughtstream.utility.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @CachePut(value = "post", key = "#result.id")
    public PostDto addNewPost(PostDto postDto, String email) {
        Post post = modelMapper.map(postDto, Post.class);

        Users users = userRepo.findUsersByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(MessageConstants.USER_NOT_FOUND));

        post.setAuthorName(users.getFirstName()+ "  " + users.getLastName());
        post.setUsers(users);
        post.setStatus(PostStatus.valueOf(postDto.getStatus().toUpperCase()));
        post.setCategory(Category.valueOf(postDto.getCategory().toUpperCase()));
        post.setTags(postDto.getTags());

        repo.save(post);
        var response = modelMapper.map(post, PostDto.class);
        return  response;
    }

    @CacheEvict(value = "post", key = "#id")
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
    @CachePut(cacheNames = "post", key = "#id")
    public PostDto updatePostContent(String id, PostDto postDto, String email) throws BadRequestException {
        isValidPost(id);

        Post post = repo.findById(id)
                .orElseThrow(()-> new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND));

        System.out.println(post);
        
        if(!post.getUsers().getEmail().equals(email)){
            throw new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND);
        }

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setStatus(PostStatus.valueOf(postDto.getStatus().toUpperCase()));

        Post savedPost = repo.save(post);

        return modelMapper.map(savedPost, PostDto.class);


    }

    @Override
    @Cacheable(value = "post", key = "#id")
    public PostDto getPost(String id, String email) {
        isValidPost(id);

        Post post = repo.findById(id)
                .orElseThrow(()-> new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND));

        PostDto mappedDto = modelMapper.map(post, PostDto.class);

        if(checkPostStatus(mappedDto) || post.getUsers().getEmail().equals(email)) return mappedDto;
        throw new NoSuchElementException(MessageConstants.BLOG_NOT_FOUND);
    }

    @Override
    @Cacheable(cacheNames="post", key = "{#id, #page}")
    public List<PostDto> getAllMyPost(String id,String email, Integer page) {

        if(!userRepo.existsById(id)){
            throw new UsernameNotFoundException(MessageConstants.USER_NOT_FOUND);
        }
        List<PostDto> response = getPageablePostDto(id, page);

        if(email == null){
            return response.stream()
                    .filter(this::checkPostStatus)
                    .toList();
        }
        return response;
    }

    @Override
    @Cacheable(cacheNames="post", key = "{#category, #page}")
    public List<PostDto> getPostByCategory(String category, Integer page) {

        Pageable pageable = PageRequest.of(page, pageSize);
        List<Post> allPost = repo.findAllByCategory(Category.valueOf(category.toUpperCase()), pageable).getContent();

        return allPost.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    @Cacheable(cacheNames="post", key = "{#tags, #page}")
    public List<PostDto> getPostByTags(List<String> tags, Integer page) {

        Pageable pageable = PageRequest.of(page, pageSize);

        List<Post> allPost = repo.findAllByTagsIn(tags, pageable).getContent();

        return allPost.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    private boolean checkPostStatus(PostDto post){
        return post.getStatus().equalsIgnoreCase("PUBLISHED");
    }

    private List<PostDto> getPageablePostDto(String id ,Integer page){
        Pageable pageable = PageRequest.of(page,pageSize);
        List<Post> allPost = repo.findAllByUsers_Id(id, pageable).getContent();

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
