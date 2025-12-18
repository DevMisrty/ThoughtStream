package com.practice.thoughtstream.dto;

import com.practice.thoughtstream.model.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PostDto {

    private String id;
    private String title;
    private String content;
    private String status;

    private String category;
    private List<String> tags;

}
