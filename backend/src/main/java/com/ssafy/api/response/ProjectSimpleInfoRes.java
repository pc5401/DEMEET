package com.ssafy.api.response;

import com.ssafy.DTO.project.ProjectSimpleInfoDTO;
import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectSimpleInfoRes extends BaseResponseBody {

    List<ProjectSimpleInfoDTO> activateProjects = new ArrayList<ProjectSimpleInfoDTO>();


    public ProjectSimpleInfoRes() {super();}

    public static ProjectSimpleInfoRes of(Integer statusCode, String message, List<ProjectSimpleInfoDTO> activateProjects){
        ProjectSimpleInfoRes res = new ProjectSimpleInfoRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setActivateProjects(activateProjects);
        return res;
    }
}
