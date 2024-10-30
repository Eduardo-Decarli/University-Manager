package com.compass.Desafio_02.web.controller.exception;

public class EmptyListException extends RuntimeException{
    public EmptyListException(String msg){
        super(msg);
    }
}
