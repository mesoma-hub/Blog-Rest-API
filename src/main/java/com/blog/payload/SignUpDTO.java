package com.blog.payload;

import lombok.Data;

@Data
public class SignUpDTO {
    private String name;
    private String userName;
    private String password;
    private String email;
}
