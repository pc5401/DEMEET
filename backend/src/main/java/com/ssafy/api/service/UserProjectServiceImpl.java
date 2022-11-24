package com.ssafy.api.service;

import com.ssafy.DTO.user.UserSimpleInfoDTO;
import com.ssafy.api.request.AddDelUserInProjectPostReq;
import com.ssafy.common.customException.ProjectNullException;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.UserProject;
import com.ssafy.db.entity.Users;
import com.ssafy.db.repository.UserProjectRepository;
import com.ssafy.db.repository.UserProjectRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.kurento.jsonrpc.client.JsonRpcClient.log;

@Service("UserProjectService")
public class UserProjectServiceImpl implements UserProjectService{

    @Autowired
    UserProjectRepository userProjectRepository;
    @Autowired
    UserProjectRepositorySupport userProjectRepositorySupport;

    @Override
    public List<UserSimpleInfoDTO> getUserSimpleInfoDTOListByPid(Long pid) throws UidNullException {
        log.info("pid를 이용해서 해당 프로젝트에 속한 유저들의 simpleInfoDTOList반환");
        List<UserSimpleInfoDTO> userList = userProjectRepositorySupport.getUserSimpleInfoDTOListByPid(pid).orElseThrow(() -> new UidNullException("cannot get user list by pid " + pid));
        log.debug("userList = {}", userList.toString());
        return userList;
    }

//    @Override
//    public List<UserSimpleInfoDTO> getUserSimpleInfoWithPrifileDTOListByPid(Long pid) throws UidNullException {
//        List<Users> optUserList = userProjectRepositorySupport.getUserListByPid(pid).orElseThrow(() -> new UidNullException("cannot get user list by pid"));
//        List<UserSimpleInfoDTO> usersSimpleInfoDTOList = new ArrayList<UserSimpleInfoDTO>();
//        for(Users user : optUserList) {
//            UserSimpleInfoDTO usersSimpleInfoDTO = new UserSimpleInfoDTO();
//            usersSimpleInfoDTO.setUid(user.getUid());
//            usersSimpleInfoDTO.setEmail(user.getEmail());
//            usersSimpleInfoDTO.setNickname(user.getNickname());
//            usersSimpleInfoDTOList.add(usersSimpleInfoDTO);
//        }
//        return usersSimpleInfoDTOList;
//    }

    @Override
    public List<Long> getUserUidListByPid(Long pid) throws UidNullException {
        log.info("pid를 기반으로 해당 프로젝트에 속해있는 유저들의 uidList 조회");
        List<UserSimpleInfoDTO> userList = userProjectRepositorySupport.getUserSimpleInfoDTOListByPid(pid).orElseThrow(() -> new UidNullException("cannot get user list by pid " + pid));
        log.debug("userList = {}", userList.toString());
        List<Long> uidList = new ArrayList<Long>();
        log.info("uidList로 변환");
        for(UserSimpleInfoDTO user : userList) {
            uidList.add(user.getUid());
        }
        log.debug("newUserList = {}",uidList.toString());
        return uidList;
    }

    @Override
    public List<Projects> getJoinedProjectList(Long uid) throws ProjectNullException {
        log.info("uid 기반으로 현재 유저가 속해있는 프로젝트 목록 조회");
        List<Projects> joinedProjectList = userProjectRepositorySupport.getJoinedProjectList(uid).orElseThrow(() -> new ProjectNullException("cannot get project list by uid " + uid));
        log.debug("joinedProjectList = {}", joinedProjectList.toString());
        return joinedProjectList;
    }

    @Override
    public UserProject addUserInProject(AddDelUserInProjectPostReq addDelUserInProjectPostReq, Projects project, Users user) {
        log.info("특정 프로젝트에 유저 추가");
        UserProject userProject = new UserProject();
        userProject.setUsers(user);
        userProject.setProjects(project);
        UserProject saved = userProjectRepository.save(userProject);
        log.debug("saved = {}", saved.toString());
        return saved;
    }

    @Override
    public boolean userDuplicateCheck(Projects project, Users user) {
        log.info("유저 중복 체크, true면 중복, false면 중복 아님");
        UserProject userProject =userProjectRepository.getUserProjectByProjectsAndUsers(project, user);
        boolean check = userProject != null ? true:false;
        log.debug("중복여부 = {}", check);
        return check;
    }

    @Override
    public void deleteUserInProject(Projects project, Users user) {
        log.info("프로젝트에서 특정 유저 삭제");
        UserProject userProject = userProjectRepository.getUserProjectByProjectsAndUsers(project, user);
        log.debug("UserProject = {}", userProject.toString());
        userProjectRepository.delete(userProject);
        log.info("유저 삭제 완료");
    }
}
