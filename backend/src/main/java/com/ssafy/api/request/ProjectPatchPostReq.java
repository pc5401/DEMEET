package com.ssafy.api.request;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Optional;

/**
 * 프로젝트 정보를 업데이트할때 필요한 리퀘스트 바디 정의
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectPatchPostReq {
    @Positive
    @NotBlank
    Long pid;

    String name;

    String desc;

    Optional<Boolean> deactivate;

}
