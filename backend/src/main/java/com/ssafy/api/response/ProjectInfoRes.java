package com.ssafy.api.response;


import com.ssafy.DTO.project.ProjectInfoDTO;
import com.ssafy.DTO.user.UserSimpleInfoDTO;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.TypeConverter;
import com.ssafy.db.entity.Projects;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectInfoRes extends BaseResponseBody {



    ProjectInfoDTO project = new ProjectInfoDTO();

    public ProjectInfoRes(){super();}
    public static ProjectInfoRes of(Integer statusCode, String message, Projects project, Long projectOwner,  List<UserSimpleInfoDTO> userList){
        ProjectInfoRes res = new ProjectInfoRes();
//         project = new ProjectInfoDTO();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.project.setPid(project.getPid());
        res.project.setProjectOwner(projectOwner);
        res.project.setMember(userList);
        res.project.setSessionId(project.getCustomSessionName());
        res.project.setPjtStartDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(project.getPjtStartDate()));
        if(project.getPjtEndDate() != null){
            res.project.setPjtEndDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(project.getPjtEndDate()));
        }
        else{
            res.project.setPjtEndDate(null);
        }
        res.project.setPjtName(project.getPjtName());
        res.project.setPjtDesc(project.getPjtDesc());
        TypeConverter typeConverter;
        res.project.setTotalMeetTime(TypeConverter.LongSecondsToStringTime(project.getTotalMeetTime()));
        res.project.setActivation(project.isActivation());
        return res;
    }

}
