package com.ssafy.common.util;

import javax.mail.MessagingException;

public interface EmailUtil {
    boolean sendEmail(String toAddress, String email, String password) throws MessagingException;
}
