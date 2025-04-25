package com.example.BookingService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class Exceptions {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> RaiseIllegalArgumnetException( IllegalArgumentException ex){
        return new ResponseEntity<>("expection"+ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(CustomIdNotFound.class)
    public ResponseEntity<CustomIdNotFound> CustomException(CustomIdNotFound ex, WebRequest webRequest){
        List<String> error=new ArrayList<>();
        error.add(webRequest.getContextPath());
        error.add(ex.getMessage());
        CustomIdNotFound customIdNotFound=new CustomIdNotFound("errors",error);
        return new ResponseEntity<>(customIdNotFound,HttpStatus.NOT_FOUND);

    }

//    @ExceptionHandler(EmailException.class)
//    public ResponseEntity<EmailException> emailExceptionResponseEntity(EmailException e){
//        String s="Email Exception";
//        EmailException emailException=new EmailException(s);
//        return new ResponseEntity<>(emailException,HttpStatus.NOT_FOUND);
//    }

}
