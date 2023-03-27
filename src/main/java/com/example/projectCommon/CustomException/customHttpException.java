package com.example.projectCommon.CustomException;

public class customHttpException extends RuntimeException{

    public customHttpException() {
        super();
    }

    public customHttpException(String message) {
        super(message);
    }

    public customHttpException(Throwable e) {
        super(e);
    }

    public customHttpException(String message, Throwable e) {
        super(message, e);
    }
}
