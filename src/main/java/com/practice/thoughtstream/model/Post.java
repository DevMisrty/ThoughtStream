package com.practice.thoughtstream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
@Document
public class Post {

    private String id;

    private String authorName;
    private String title;
    private String content;
    private PostStatus status;

    private List<Comment> comments;

    private Category category;
    private List<String> tags;


    @DBRef(lazy = true)
    @JsonIgnoreProperties
    private Users users;

    @CreatedDate
    private LocalDateTime createdAt;


}
