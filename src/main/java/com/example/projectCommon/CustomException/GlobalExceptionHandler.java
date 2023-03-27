package com.example.projectCommon.CustomException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BasicException.class})
    public ResponseEntity<String> handleBasicException(Exception e) {
        logger.error("");
        return new ResponseEntity<>("Basic error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    Do not use this == > will catch all exceptions !!
//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<String> handleBasicException(Exception e) {
//        logger.error("");
//        return new ResponseEntity<>("Basic error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
