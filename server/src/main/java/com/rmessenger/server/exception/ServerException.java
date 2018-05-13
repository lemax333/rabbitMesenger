package com.rmessenger.server.exception;

public class ServerException extends Exception {

    public ErrorCode errorCode;

    public ServerException(ErrorCode errorCode, Throwable cause){
        super(errorCode.message, cause);
        this.errorCode = errorCode;
    }
}
