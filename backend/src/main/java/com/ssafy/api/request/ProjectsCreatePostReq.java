package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * 프로젝트 생성 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@Setter
@ToString
public class ProjectsCreatePostReq {
    /**
     * The Owner id.
     */
    @Positive
    Long owner_id;
    /**
     * The Pjt name.
     */
    @NotBlank
    String pjt_name;
    /**
     * The Member list.
     */
    List<Long> memberList;
    /**
     * The Pjt desc.
     */
    String pjt_desc;
}
