package com.ssafy.common.customException;

public class EmailDuplicatedException extends Exception{
    public EmailDuplicatedException(String msg) {
        super(msg);
    }
}
