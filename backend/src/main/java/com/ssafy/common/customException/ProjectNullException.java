package com.ssafy.common.customException;

import java.util.function.Supplier;

public class ProjectNullException extends Exception{

    public ProjectNullException(String msg) {
        super(msg);
    }
}
