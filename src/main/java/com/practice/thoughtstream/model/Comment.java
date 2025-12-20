package com.practice.thoughtstream.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Comment implements Serializable {

    private String commentId;
    private String userId;
    private String name;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;

}
