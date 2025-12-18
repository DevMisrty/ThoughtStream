package com.practice.thoughtstream.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Comment {

    private String id;

    private String name;
    private String message;

    @CreatedDate
    private String createdAt;

}
