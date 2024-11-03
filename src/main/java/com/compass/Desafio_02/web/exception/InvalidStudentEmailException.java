package com.compass.Desafio_02.web.exception;

public class InvalidStudentEmailException extends RuntimeException{
    public InvalidStudentEmailException(String msg){
        super(msg);
    }
}
