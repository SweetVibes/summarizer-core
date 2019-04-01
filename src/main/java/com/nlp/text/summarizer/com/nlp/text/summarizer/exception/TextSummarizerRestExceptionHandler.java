package com.nlp.text.summarizer.com.nlp.text.summarizer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class TextSummarizerRestExceptionHandler {

  /*  @ExceptionHandler
    public ResponseEntity<InvalidDataResponse> handleException(Exception ex){
        InvalidDataResponse response = new InvalidDataResponse();
        response.setMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response , HttpStatus.BAD_REQUEST);
    }*/

}
