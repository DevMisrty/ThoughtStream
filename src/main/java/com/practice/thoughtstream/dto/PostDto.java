package com.practice.thoughtstream.dto;

import com.practice.thoughtstream.model.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PostDto {

    private String authorName;
    private String title;
    private String content;
    private PostStatus status;

}
