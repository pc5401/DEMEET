package com.ssafy.api.service;

import com.ssafy.DTO.project.ProjectDeactivateSimpleInfoDTO;
import com.ssafy.DTO.project.ProjectJoinedActivateSimpleInfoDTO;
import com.ssafy.DTO.project.ProjectSimpleInfoDTO;
import com.ssafy.api.request.ProjectPatchPostReq;
import com.ssafy.api.request.ProjectsCreatePostReq;
import com.ssafy.common.customException.NoAuthorizedException;
import com.ssafy.common.customException.ProjectNullException;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.db.entity.Conferences;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.UserProject;
import com.ssafy.db.entity.Users;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.kurento.jsonrpc.client.JsonRpcClient.log;

@Service("projectsService")
public class ProjectsServiceImpl implements ProjectsService {
    @Autowired
    ProjectsRepository projectRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserProjectRepository userProjectRepository;
    @Autowired
    UserProjectRepositorySupport userProjectRepositorySupport;
    @Autowired
    UsersRepositorySupport usersRepositorySupport;

    @Autowired
    ProjectsRepositorySupport projectRepositorySupport;

    @Autowired
    UserProjectService userProjectService;


    public ProjectJoinedActivateSimpleInfoDTO makeProjectJoinedActivateSimpleInfoDTO(Projects project) {
        ProjectJoinedActivateSimpleInfoDTO simpleInfoDTO = new ProjectJoinedActivateSimpleInfoDTO();
        simpleInfoDTO.setPid(project.getPid());
        simpleInfoDTO.setProjectOwner(project.getOwnerId());
        simpleInfoDTO.setPjtName(project.getPjtName());
        simpleInfoDTO.setPjtDesc(project.getPjtDesc());
        simpleInfoDTO.setActivation(project.isActivation());
        // 세션 활성화 여부 체크
        simpleInfoDTO.setSessionActivate(sessionActivateCheck(project));
        return simpleInfoDTO;
    }
    public boolean sessionActivateCheck(Projects project){
        boolean activationCheck = false;
        List<Conferences> conferenceList = project.getConferencesList();
        for(Conferences conf : conferenceList){
            if(conf.isActivation()){
                activationCheck = true;
                break;
            }
        }
        return activationCheck;

    }

    public ProjectDeactivateSimpleInfoDTO makeProjectDeactivateSimpleInfoDTO(Projects project) throws UidNullException {
        ProjectDeactivateSimpleInfoDTO simpleInfoDTO = new ProjectDeactivateSimpleInfoDTO();
        simpleInfoDTO.setPid(project.getPid());
        simpleInfoDTO.setPjtName(project.getPjtName());
        simpleInfoDTO.setPjtStartDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(project.getPjtStartDate()));
        simpleInfoDTO.setPjtEndDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(project.getPjtEndDate()));
        List<Long> userList = userProjectService.getUserUidListByPid(project.getPid());
        simpleInfoDTO.setMember(userList);
        return simpleInfoDTO;
    }

    private List<ProjectSimpleInfoDTO> getProjectSimpleInfoDTOS(List<Projects> activateProjectsList) {
        log.debug("projectsList = {}" + activateProjectsList.toString());
        log.info("위 프로젝트를 ProjectSimpleInfoDTOList로 변환해준다.");
        List<ProjectSimpleInfoDTO> projectSimpleInfoList = new ArrayList<ProjectSimpleInfoDTO>();
        for (Projects project : activateProjectsList) {
            projectSimpleInfoList.add(makeProjectJoinedActivateSimpleInfoDTO(project));
        }
        log.debug("projectSimpleInfoList = " + projectSimpleInfoList.toString());
        return projectSimpleInfoList;
    }
    private List<ProjectJoinedActivateSimpleInfoDTO> getProjectJoinedActivateSimpleInfoDTOS(List<Projects> activateProjectsList) {
        log.debug("projectsList = {}" + activateProjectsList.toString());
        log.info("위 프로젝트를 ProjectJoinedActivateSimpleInfoDTOSList로 변환해준다.");
        List<ProjectJoinedActivateSimpleInfoDTO> projectSimpleInfoList = new ArrayList<ProjectJoinedActivateSimpleInfoDTO>();
        for (Projects project : activateProjectsList) {
            projectSimpleInfoList.add(makeProjectJoinedActivateSimpleInfoDTO(project));
        }
        log.debug("ProjectJoinedActivateSimpleInfoDTOSList = " + projectSimpleInfoList.toString());
        return projectSimpleInfoList;
    }

    @Override
    public List<Projects> getDeactivatedProjectsByUid(Long uid) throws ProjectNullException {
        log.info("uid를 기반으로  비활성화된 프로젝트들 조회");
        List<Projects> projectsList = userProjectRepositorySupport.getDeactivateProjectsByUid(uid)
                .orElseThrow(() -> new ProjectNullException("could not find Projects by uid " + uid));
        log.info("조회 성공");
        log.debug("projectList size = {}", projectsList.size());
        return projectsList;
    }

    @Override
    public List<ProjectDeactivateSimpleInfoDTO> changetProjectListToProjectDeactivateSimpleInfoDTOList(List<Projects> deActivateProjectsList) throws UidNullException {
        log.info("프로젝트 리스트를 projectDeactivateSimpleInfoDTO 리스트 형식으로 변경");
        List<ProjectDeactivateSimpleInfoDTO> projectDeactivateSimpleInfoDTOList = new ArrayList<ProjectDeactivateSimpleInfoDTO>();
        for (Projects project : deActivateProjectsList) {
            ProjectDeactivateSimpleInfoDTO simpleInfoDTO = makeProjectDeactivateSimpleInfoDTO(project);
            projectDeactivateSimpleInfoDTOList.add(simpleInfoDTO);
        }
        log.info("변경 완료");
        log.debug("projectDeactivateSimpleInfoDTOList size = {}", projectDeactivateSimpleInfoDTOList.size());
        return projectDeactivateSimpleInfoDTOList;
    }

    @Override
    public boolean deleteProjects(Projects projects) throws ProjectNullException {
        long pid = projects.getPid();
        projectRepository.delete(projects);
        Optional<Projects> checkProjects = projectRepository.findProjectsByPid(pid);
        if(checkProjects.isPresent()){
            log.error("Project delete fail");
            return false;
        }
        log.info("Project delete success");
        return true;
    }

    @Override
    @Transactional
    public Projects createProject(ProjectsCreatePostReq projectsCreatePostReq) throws UidNullException {
        log.info("프로젝트 생성 시작");
        Projects project = new Projects();

        log.info("프론트에서 받은 추가할 맴버 uid들을 통해 프로젝트에 해당 유저들 추가");
        List<Long> memberList = new ArrayList<Long>();
        memberList.add(projectsCreatePostReq.getOwner_id());
        memberList.addAll(projectsCreatePostReq.getMemberList());
        log.debug("받은 유저들의 정보 = {}", memberList.toString());

        log.info("위 정보들을 기반으로 실제 프로젝트에 추가하기 위한 유저객체 리스트 생성");
        List<Users> userList = new ArrayList<Users>();
        for (Long member : memberList) {
            try {
                Users user = usersRepositorySupport.findUserById(member).get();
                userList.add(user);
            } catch (NoSuchElementException e) {
                log.info("유저 추가작업중 uid를 기반으로 찾을수없는 유저 발견");
                log.debug("uid = {}", member);
                throw new UidNullException("can not find user that uid is " + member);
            }
        }

        log.info("프로젝트 이름 설정");
        project.setPjtName(projectsCreatePostReq.getPjt_name());
        log.debug("프로젝트 이름 ={}", project.getPjtName());

        log.info("프로젝트 설명 설정");
        if (projectsCreatePostReq.getPjt_desc() != null) {
            log.info("설명이 null이므로 추가하지않음.");
            project.setPjtDesc(projectsCreatePostReq.getPjt_desc());
        }
        log.debug("프로젝트 설명 = {}", project.getPjtDesc());

        log.info("프로젝트 오너 지정");
        project.setOwnerId(projectsCreatePostReq.getOwner_id());
        log.debug("프로젝트 오너 = {}", project.getOwnerId());

        log.info("프로젝트 활성화 체크");
        project.setActivation(true);
        log.debug("프로젝트 활성화여부 = {}", project.isActivation());

        log.info("프로젝트 시작날짜 설정");
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        project.setPjtStartDate(localDateTime);
        log.debug("프로젝트 시작날짜 = {}", project.getPjtStartDate().toString());

        log.info("프로젝트 토탈시간 초기화");
        project.setTotalMeetTime(0L);
        log.debug("프로젝트 토탈시간 = {}", project.getTotalMeetTime().toString());

        log.info("프로젝트의 세션ID 지정");
        String baseString = project.getPjtName() + project.getPjtStartDate().toString();
        String sessionId = Base64.getEncoder().encodeToString(baseString.getBytes());
        project.setCustomSessionName(sessionId);

        log.info("프로젝트 저장");
        Projects savedProject = projectRepository.save(project);
        log.debug("프로젝트 = {}", savedProject.toString());
        log.info("userProject에 유저와 프로젝트관계 저장");
        for (Users user : userList) {
            UserProject userProject = new UserProject();
            userProject.setUsers(user);
            userProject.setProjects(savedProject);
            log.info("userProject = {}", savedProject.toString());
            userProjectRepository.save(userProject);
        }
        log.info("프로젝트 생성 완료");
        return savedProject;
    }

    @Override
    public Projects getProject(Long pid) throws ProjectNullException {
        log.info("pid를 기반으로 프로젝트 조회");
        Projects project = projectRepositorySupport.getProject(pid).orElseThrow(() -> new ProjectNullException("Project " + pid + " does not exist"));
        log.debug("project = {}", project.toString());
        return project;
    }

    @Override
    public List<ProjectSimpleInfoDTO> getActivateProjectsList(Long uid) throws ProjectNullException {
        log.info("get activate projects with uid");
        List<Projects> activateProjectsList = projectRepository.findProjectsByOwnerIdAndActivation(uid, true).orElseThrow(() -> new ProjectNullException("not found"));
        List<ProjectSimpleInfoDTO> projectSimpleInfoList = getProjectSimpleInfoDTOS(activateProjectsList);
        return projectSimpleInfoList;
    }

    @Override
    public List<ProjectJoinedActivateSimpleInfoDTO> getJoinedProjectList(Long uid) throws ProjectNullException {
        log.info("내가 속해있는 프로젝트 조회");
        List<Projects> activateProjectsList = userProjectRepositorySupport.getJoinedProjectList(uid).orElseThrow(() -> new ProjectNullException("Projets not found"));
        List<ProjectJoinedActivateSimpleInfoDTO> projectSimpleInfoList = getProjectJoinedActivateSimpleInfoDTOS(activateProjectsList);
        return projectSimpleInfoList;
    }

    @Override
    public List<ProjectDeactivateSimpleInfoDTO> getDeActivateProjectsByUid(Long uid) throws ProjectNullException, UidNullException {
        log.info("uid를 기반으로 내가 속한 프로젝트들중 비활성화된 프로젝트들 조회");
        List<Projects> deActivateProjects = userProjectRepositorySupport.getDeactivateProjectsByUid(uid).orElseThrow(() -> new ProjectNullException("Projets not found"));
        log.debug("deActivateProjects = {}", deActivateProjects.toString());
        log.info("위 프로젝트를 ProjectSimpleInfoDTOList로 변환해준다.");
        List<ProjectDeactivateSimpleInfoDTO> projectDeactivateSimpleInfoList = new ArrayList<ProjectDeactivateSimpleInfoDTO>();
        for (Projects project : deActivateProjects) {
            projectDeactivateSimpleInfoList.add(makeProjectDeactivateSimpleInfoDTO(project));
        }
        log.debug("projectSimpleInfoList = {}", projectDeactivateSimpleInfoList.toString());
        return projectDeactivateSimpleInfoList;
    }

    @Override
    public Projects deactivateProject(long pid, Long userUid) throws ProjectNullException, NoAuthorizedException {
        log.info("프로젝트 비활성화");
        Projects project = projectRepository.findProjectsByPid(Long.valueOf(pid)).orElseThrow(() -> new ProjectNullException("ProjectNullException"));
        log.debug("프로젝트 = {}", project.toString());
        if (project.getOwnerId() != userUid) {
            log.error("uid가 {}인 사용자는 해당 프로젝트의 오너가 아니기때문에 비활성화 못함", userUid);
            throw new NoAuthorizedException("this user is not allowed to deactivate " + pid + " project");
        }
        log.info("비활성화");
        project.setActivation(false);
        log.debug("project Activation = {}", project.isActivation());
        log.info("프로젝트를 비활성화했으니 해당 프로젝트의 endDate 지정");
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        project.setPjtEndDate(localDateTime);
        log.debug("endDate = {}", project.getPjtEndDate());
        Projects savedProject = projectRepository.save(project);
        return savedProject;
    }

    @Override
    public Optional<Projects> getProjectByCustomSessionName(String sessionId) throws ProjectNullException {
        log.info("세션아이디를 기반으로 프로젝트 조회");
        Optional<Projects> project = projectRepository.findProjectsByCustomSessionName(sessionId);
        log.debug("project = {}", project.toString());
        if (project == null) return Optional.empty();
        return project;
    }

    @Override
    public Projects patchProjectInfo(ProjectPatchPostReq projectPatchPostReq, Long uid) throws ProjectNullException, NullPointerException {
        log.info("프로젝트 정보 수정");
        log.info("조건 : 해당하는 pid의 프로젝트가 있고, 내가 그 프로젝트의 오너이고, 프로젝트는 활성화되어있어야한다.");
        log.info("pid를 기반으로 프로젝트 조회");
        Projects currProject = projectRepository.findProjectsByPidAndOwnerIdAndActivationIsTrue(projectPatchPostReq.getPid(), uid).orElseThrow(() -> new ProjectNullException("Your request does not meet the criteria."));
        log.debug("project = {}", currProject.toString());
//         변경할 정보(name, desc, decativate)모두 null일경우 똑같이 오류를 띄워준다.
        if (projectPatchPostReq.getName() == null & projectPatchPostReq.getDesc() == null & !projectPatchPostReq.getDeactivate().orElse(false)) {
//            System.out.println(projectPatchPostReq.getDeactivate().orElse(true));
            log.error("변경할 정보(name, desc, decativate)가 모두 null이다.");
            throw new NullPointerException();
        }

        // 위 모든 조건들을 통과하였을경우 이제 변환작업을 시작한다.
        if (projectPatchPostReq.getName() != null) {
            log.info("프로젝트 이름 변경");
            currProject.setPjtName(projectPatchPostReq.getName());
            log.debug("ProjectName = {}", currProject.getPjtName());
        }
        if (projectPatchPostReq.getDesc() != null) {
            log.info("프로젝트 설명 변경");
            currProject.setPjtDesc(projectPatchPostReq.getDesc());
            log.debug("ProjectDesc = {}", currProject.getPjtDesc());
        }
        if (projectPatchPostReq.getDeactivate().orElse(false)) {
            log.info("프로젝트 비활성화");
            currProject.setActivation(!projectPatchPostReq.getDeactivate().orElse(false));
            LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
            currProject.setPjtEndDate(localDateTime);
            log.debug("ProjectActivation = {}", currProject.isActivation());
        }
        log.info("프로젝트에 변경사항 추가");

        Projects savedProject = projectRepository.save(currProject);
        log.debug("Projects = {}", savedProject.toString());
        return savedProject;
    }


}

