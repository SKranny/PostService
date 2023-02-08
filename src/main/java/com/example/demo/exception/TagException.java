package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class TagException extends RuntimeException{

    private final HttpStatus httpStatus;


    public TagException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public TagException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

}
