package com.compass.Desafio_02.web.controller.exception;

public class EntityUniqueViolationException extends RuntimeException{
    public EntityUniqueViolationException(String msg){
        super(msg);
    }
}
