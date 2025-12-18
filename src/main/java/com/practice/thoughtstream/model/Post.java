package com.practice.thoughtstream.model;

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

    @DBRef
    private Users users;

    @CreatedDate
    private LocalDateTime createdAt;


}
