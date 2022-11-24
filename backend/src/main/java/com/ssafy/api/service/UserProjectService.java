package com.ssafy.api.service;

import com.ssafy.DTO.user.UserSimpleInfoDTO;
import com.ssafy.api.request.AddDelUserInProjectPostReq;
import com.ssafy.common.customException.ProjectNullException;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.UserProject;
import com.ssafy.db.entity.Users;

import java.util.List;

/**
 * UsersProjects 관련 빈즈니스 로직 처리를 위한 서비스 인터페이스 정의
 */
public interface UserProjectService {


    List<UserSimpleInfoDTO> getUserSimpleInfoDTOListByPid(Long pid) throws UidNullException;

//    List<UserSimpleInfoDTO> getUserSimpleInfoWithPrifileDTOListByPid(Long pid) throws UidNullException;

    UserProject addUserInProject(AddDelUserInProjectPostReq addDelUserInProjectPostReq, Projects project, Users user);

    boolean userDuplicateCheck(Projects project, Users user);

    void deleteUserInProject(Projects project, Users user);

    List<Long> getUserUidListByPid(Long pid) throws UidNullException;

    List<Projects> getJoinedProjectList(Long uid) throws ProjectNullException;
}
