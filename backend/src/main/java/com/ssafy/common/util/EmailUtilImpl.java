package com.ssafy.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service("EmailUtil")
@Slf4j
public class EmailUtilImpl implements EmailUtil {

    @Autowired
    private JavaMailSender sender;

    @Override
    public boolean sendEmail(String toAddress, String email, String password) throws MessagingException {
        Map<String, Object> result = new HashMap<String, Object>();
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        boolean check = false;
        try {
            helper.setTo(toAddress);
            helper.setSubject("DEMEET 임시비밀번호 확인");
            helper.setText(makeHTML(email, password), true);
            check = true;
            sender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send email = {}", e.getMessage());
            throw new MessagingException();
        }
        return check;
    }

    public String makeHTML(String email, String password) {
        String html = "<!DOCTYPE html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><link rel=\"stylesheet\" type=\"text/css\" id=\"u0\" href=\"https://ko.rakko.tools/tools/129/lib/tinymce/skins/ui/oxide/content.min.css\"><link rel=\"stylesheet\" type=\"text/css\" id=\"u1\" href=\"https://ko.rakko.tools/tools/129/lib/tinymce/skins/content/default/content.min.css\"><script src=\"chrome-extension://mooikfkahbdckldjjndioackbalphokd/assets/prompt.js\"></script></head><body id=\"tinymce\" class=\"mce-content-body \" data-id=\"content\" contenteditable=\"true\" spellcheck=\"false\"><h1 style=\"text-align: center;\" data-mce-style=\"text-align: center;\">DEMEET 임시 비밀번호 확인</h1><p><br data-mce-bogus=\"1\"></p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\"><a data-mce-href=\"https://i7b309.p.ssafy.io/\" href=\"https://i7b309.p.ssafy.io/\" title=\"demeet\" target=\"_blank\" rel=\"noopener\"><img src=\"https://i7b309.p.ssafy.io/img/DEMEET_logo.b4a772ef.png\" alt=\"demeet logo\" width=\"266\" height=\"51\"></a></p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\"><strong><br data-mce-bogus=\"1\"></strong></p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\"><strong>\uD83D\uDC80아래 비밀번호는 임시 비밀번호입니다. 로그인 후 꼭 비밀번호를 변경해주세요.</strong></p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\">이메일 : " + email + "</p><p style=\"text-align: center;\" data-mce-style=\"text-align: center;\">임시 비밀번호 : <span style=\"background-color: rgb(194, 224, 244);\" data-mce-style=\"background-color: #c2e0f4;\">" + password + "</span></p></body></html>";
        return html;
    }

}
