package com.compass.Desafio_02.web.exception;

public class EntityUniqueViolationException extends RuntimeException{
    public EntityUniqueViolationException(String msg){
        super(msg);
    }
}
