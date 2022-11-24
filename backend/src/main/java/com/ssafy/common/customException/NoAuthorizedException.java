package com.ssafy.common.customException;

public class NoAuthorizedException extends Exception {

    public NoAuthorizedException(String msg) {
        super(msg);
    }
}
