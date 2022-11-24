package com.ssafy.api.response;

import com.ssafy.DTO.project.ProjectDeactivateSimpleInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectDeactivateSimpleInfoRes {

    /*
    Long pid;
    String pjtName;
    List<Integer> member = new ArrayList<Integer>();
    LocalDateTime pjtStartDate;
    LocalDateTime pjtEndDate;
     */
    ProjectDeactivateSimpleInfoDTO projectDeactivateSimpleInfo;


}
