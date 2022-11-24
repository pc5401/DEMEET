package com.ssafy.api.response;

import com.ssafy.DTO.project.ProjectJoinedActivateSimpleInfoDTO;
import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectJoinedActivateSimpleInfoRes extends BaseResponseBody {
    List<ProjectJoinedActivateSimpleInfoDTO> activateProjects = new ArrayList<ProjectJoinedActivateSimpleInfoDTO>();


    public ProjectJoinedActivateSimpleInfoRes() {super();}

    public static ProjectJoinedActivateSimpleInfoRes of(Integer statusCode, String message, List<ProjectJoinedActivateSimpleInfoDTO> activateProjects){
        ProjectJoinedActivateSimpleInfoRes res = new ProjectJoinedActivateSimpleInfoRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setActivateProjects(activateProjects);
        return res;
    }

}
