package com.compass.Desafio_02.web.exception;

public class InvalidTeacherEmailException extends RuntimeException{
    public InvalidTeacherEmailException(String msg){
        super(msg);
    }
}
