package com.practice.thoughtstream.dto.response;

import com.practice.thoughtstream.dto.PostDto;
import com.practice.thoughtstream.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class PostResponseDto extends PostDto  implements Serializable {

    List<Comment> comments;
}
