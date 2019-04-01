package com.nlp.text.summarizer.com.nlp.text.summarizer.exception;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvalidDataResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

}
