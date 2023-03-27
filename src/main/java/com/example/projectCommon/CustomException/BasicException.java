package com.example.projectCommon.CustomException;

public class BasicException extends RuntimeException{

    public BasicException() {
        super();
    }

    public BasicException(String message) {
        super(message);
    }

    public BasicException(Throwable e) {
        super(e);
    }

    public BasicException(String message, Throwable e) {
        super(message, e);
    }
}
