package com.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*The API will throw a ResourceNotFoundException whenever a post with a given ID
is not found in the database. We Use @ResponseStatus annotation in the above exception class.
This will cause Spring Boot to respond with the specified HTTP Status code whenever this
exception is thrown from the controller class. Also note whenever we create a custom exception
we need to extend RuntimeException*/
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;
    private final String fieldName;
    private final Long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
