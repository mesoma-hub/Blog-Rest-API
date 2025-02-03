package com.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    /*Title should not be empty or null.
    * Title should have at least 2 characters*/
    @NotEmpty
    @Size(min = 2,  message = "Post title should have at least 2 characters")
    private String title;
    /*Post content should not be null or empty*/
    @NotEmpty
    private String content;
    /*Description should not be null or empty
    * Description should have at least 10 characters*/
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;
    private Set<CommentDTO> comments;
}
