package com.ssafy.DTO.project;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectDeactivateSimpleInfoDTO {

    Long pid;
    String pjtName;
    List<Long> member = new ArrayList<>();
    String pjtStartDate;
    String pjtEndDate;

}
