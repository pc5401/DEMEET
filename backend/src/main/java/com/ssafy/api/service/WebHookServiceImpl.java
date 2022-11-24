package com.ssafy.api.service;

import com.ssafy.DTO.openvidu.OVSessionCreatedDTO;
import com.ssafy.DTO.openvidu.OVSessionDestroyedDTO;
import com.ssafy.common.util.TypeConverter;
import com.ssafy.db.entity.Conferences;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.repository.ConferencesRepository;
import com.ssafy.db.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.kurento.jsonrpc.client.JsonRpcClient.log;

@Service("WebhookService")

public class WebHookServiceImpl implements WebhookService {

    @Autowired
    ConferencesRepository conferencesRepository;

    @Autowired
    ProjectsRepository projectsRepository;


    @Override
    public Conferences makeConferenceWithOvSessionCreatedReq(OVSessionCreatedDTO ovSessionCreatedDTO) {
        log.info("WebhookService.makeConferenceWithOvSessionCreatedReq 시작");
        // 세션 정보를 받아서 컨퍼런스 테이블에 저장한다.
        log.info("confernece 찾기");
        Conferences conference = conferencesRepository.findConferencesBySessionName(ovSessionCreatedDTO.getSessionId()).get();
        // 세션의 이름
//        conference.setSessionName(ovSessionCreatedDTO.getSessionId());
        conference.setUniqueSessionName(ovSessionCreatedDTO.getUniqueSessionId());
        // 세션 시작시간
        log.debug("timestamp : " + ovSessionCreatedDTO.getSessionId());
        LocalDateTime localDateTime = TypeConverter.LongToLocalDateTime(ovSessionCreatedDTO.getTimestamp());
        log.debug(localDateTime.toString());
        conference.setConfStartTime(localDateTime);
        // 세션 활성화여부 체크
        conference.setActivation(true);
        // 세션에 프로젝트 매칭

        log.info("conference DB에 저장");
        return conferencesRepository.save(conference);
//        return null;
    }

    @Override
    public Conferences editConferenceWithOvSessionDestroyed(OVSessionDestroyedDTO ovSessionDestroyedDTO) {
        log.info("WebhookService.editConferenceWithOvSessionDestroyed 시작");
        // 세션 정보를 받아서 컨퍼런스 테이블에 저장한다.
        log.info("confernece 가져오기");
        Conferences conference = conferencesRepository.findConferencesBySessionName(ovSessionDestroyedDTO.getSessionId())
                .orElseThrow(IllegalStateException::new);
        if (conference == null) {
            log.debug("can't find conference with unique session name " + ovSessionDestroyedDTO.getUniqueSessionId());
        }
        conference.setActivation(false);
        // 종료시간 설정
        LocalDateTime endTime = TypeConverter.LongToLocalDateTime(ovSessionDestroyedDTO.getTimestamp());
        conference.setConfEndTime(endTime);
        Projects projects = conference.getProject();
        log.debug("project = {} ", projects.toString());
        Long totalTime = projects.getTotalMeetTime();
        Long duration = ovSessionDestroyedDTO.getDuration();
        log.debug("duration = {}", duration);
        projects.setTotalMeetTime(totalTime + duration);
        projectsRepository.save(projects);
        return conferencesRepository.save(conference);
    }
}
