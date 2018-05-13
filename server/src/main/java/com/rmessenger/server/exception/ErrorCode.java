package com.rmessenger.server.exception;

public enum ErrorCode {

    DATABASE_ERROR("databaseError"),
    CONNECTION_ERROR("connectionError"),
    OTHER("other");

    public String message;

    private ErrorCode(String message){
        this.message = message;
    }
}
