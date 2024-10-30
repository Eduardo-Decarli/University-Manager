package com.compass.Desafio_02.web.exception;

public class CoordinatorInCourseUniqueViolationException extends RuntimeException{
    public CoordinatorInCourseUniqueViolationException(String msg){
        super(msg);
    }
}
