package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class PostException extends RuntimeException{

    private final HttpStatus httpStatus;

    public PostException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public PostException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}

