package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 유저 로그인 API ([POST] /users/login) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UsersLoginPostReq")
public class UsersLoginPostReq {
    @ApiModelProperty(name = "유저 email", example = "ssafy@ssafy.com")
    @NotBlank
    @Email
    String email;
    @ApiModelProperty(name = "유저 password", example = "your_password")
    @NotBlank
    String password;
}
