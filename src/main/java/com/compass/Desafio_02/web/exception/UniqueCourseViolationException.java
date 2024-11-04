package com.compass.Desafio_02.web.exception;

public class UniqueCourseViolationException extends RuntimeException{
    public UniqueCourseViolationException(String msg) {
        super(msg);
    }
}
