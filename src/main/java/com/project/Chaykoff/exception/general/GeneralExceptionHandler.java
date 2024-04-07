package com.project.Chaykoff.exception.general;

import com.project.Chaykoff.exception.BlogNotFoundException;
import com.project.Chaykoff.exception.ContactNotFoundException;
import com.project.Chaykoff.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFoundException(ProductNotFoundException exception) {
        ExceptionResponse response =  new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.NOT_FOUND.value()
                , HttpStatus.NOT_FOUND
                , exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BlogNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBlogNotFoundException(BlogNotFoundException exception) {
        ExceptionResponse response =  new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.NOT_FOUND.value()
                , HttpStatus.NOT_FOUND
                , exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleContactNotFoundException(ContactNotFoundException exception) {
        ExceptionResponse response =  new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.NOT_FOUND.value()
                , HttpStatus.NOT_FOUND
                , exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }


}
