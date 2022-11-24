package com.ssafy.DTO.project;

import com.ssafy.DTO.user.UserSimpleInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectSimpleInfoDTO {
    Long pid;
    Long projectOwner;
    List<UserSimpleInfoDTO> member;
    String pjtName;
    String pjtDesc;
    boolean activation;
}
