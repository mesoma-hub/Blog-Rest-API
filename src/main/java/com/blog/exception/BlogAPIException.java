package com.blog.exception;

import org.springframework.http.HttpStatus;

/*This exception is thrown whenever we write a business logic or validate request parameters*/
public class BlogAPIException extends RuntimeException{
    private final HttpStatus status;
    private String message;

    public BlogAPIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BlogAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.message = message1;
        this.status = status;
    }


}
