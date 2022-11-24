package com.ssafy.api.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 유저스 회원가입 api ([POST] /users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("UsersRegisterPostReq")
public class UsersRegisterPostReq {
    @ApiModelProperty(name = "유저 email", example = "ssafy@ssafy.com")
    @Email
    @NotBlank(message = "아이디를 입력해주세요.")
    String email;
    @ApiModelProperty(name = "유저 password", example = "your_password")
    @NotBlank
    String password;
    @ApiModelProperty(name = "유저 nickname", example = "ssafy")
    @NotBlank
    String nickname;
}