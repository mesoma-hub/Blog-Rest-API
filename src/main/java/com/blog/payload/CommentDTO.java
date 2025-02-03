package com.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    @NotEmpty(message = "Name should not be null or empty")
    @Size()
    private String name;
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;
    @NotEmpty(message = "Body should not be null or empty")
    @Size(min = 10, message = "Body should be at least 10 characters")
    private String body;
}
