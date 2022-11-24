package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UserForgetPasswordReq {
    @Email
    @NotBlank(message = "아이디를 입력해주세요.")
    String email;

    @NotBlank
    String nickname;
}
