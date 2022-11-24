package com.ssafy.api.response;

import com.ssafy.DTO.project.ProjectDeactivateSimpleInfoDTO;
import com.ssafy.DTO.user.UserMyInfoDTO;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Users;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.format.DateTimeFormatter;
import java.util.List;
@Data
@EqualsAndHashCode(callSuper=false)
public class UsersMyInfoRes extends BaseResponseBody {
    UserMyInfoDTO user = new UserMyInfoDTO();

    public static UsersMyInfoRes of(Integer statusCode, String message, Users user, List<ProjectDeactivateSimpleInfoDTO> deActivateProjects){
        UsersMyInfoRes res = new UsersMyInfoRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.user.setUid(user.getUid());
        res.user.setEmail(user.getEmail());
        res.user.setNickname(user.getNickname());
        res.user.setRegdate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(user.getRegDate()));
        res.user.setProfileImagePath(user.getProfileImagePath().getPath());
        res.user.setDeActivateProjects(deActivateProjects);
        return res;
    }

}
