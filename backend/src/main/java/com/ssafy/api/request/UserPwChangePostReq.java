package com.ssafy.api.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * changeUserPassword의 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@Setter
public class UserPwChangePostReq {
    @NotBlank
    String currPassword;
    @NotBlank
    String newPassword;
}
