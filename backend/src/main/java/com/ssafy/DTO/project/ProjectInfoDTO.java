package com.ssafy.DTO.project;

import com.ssafy.DTO.user.UserSimpleInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectInfoDTO {
    Long pid;
    Long projectOwner;
    List<UserSimpleInfoDTO> member;
    String pjtStartDate;
    String pjtEndDate;
    String pjtName;
    String pjtDesc;
    String sessionId;
    String totalMeetTime;
    boolean activation;
}
