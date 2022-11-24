package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

/**
    이미지 업로드를 위한 리퀘스트 바디 정의
 */
@Getter
@Setter
@ToString
public class ImageUploadReq {
    @NotBlank
    MultipartFile multipartFile;
}
