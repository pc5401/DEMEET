package com.ssafy.DTO.project;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectJoinedActivateSimpleInfoDTO extends ProjectSimpleInfoDTO{
    boolean sessionActivate = false;
}
