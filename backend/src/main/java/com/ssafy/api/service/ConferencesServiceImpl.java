package com.ssafy.api.service;

import com.ssafy.common.customException.ConferenceNullException;
import com.ssafy.db.entity.Conferences;
import com.ssafy.db.repository.ConferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.kurento.jsonrpc.client.JsonRpcClient.log;

@Service("ConferencesService")
public class ConferencesServiceImpl implements ConferencesService {

    @Autowired
    ConferencesRepository conferencesRepository;

    @Override
    public Conferences findConferencesBySessionNameAndActivation(String openviduSessionId) throws ConferenceNullException {
        log.info("enter getConferencesByOpenviduSessionId");
        Conferences conference = conferencesRepository.findConferencesBySessionNameAndActivation(openviduSessionId, true)
                .orElseThrow(() -> new ConferenceNullException("can not find conference by session name " + openviduSessionId));
        return conference;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void resetConferenceStatus() {
        log.info("CONFERENCES STATUS RESET");
        // 서버가 배포되고나면 세션들의 웹훅을 못받는 문제때문에 NULL 또는 꺼진 세션도 활성화된것처럼 보일때가 있다.
        // 서버를 처음 동작시켰을때 전체 컨퍼런스들의 데이터들을 초기화해준다.
        List<Conferences> conferencesList = conferencesRepository.findAll();
        // 현재 디비에 저장되어있는 모든 컨퍼런스 목록 확인
        for (int i = 0; i < conferencesList.size(); i++) {
            // 컨퍼런스 설정
            Conferences conference = conferencesList.get(i);
            // 컨퍼런스 생성시간은 있는데 종료시간이 null이라면?
            if (conference.getConfStartTime() != null && conference.getConfEndTime() == null) {
                log.info("Conferences {} reset", conference.getSessionName());
                conference.setActivation(false);
                conference.setConfEndTime(conference.getConfStartTime());
                conferencesRepository.save(conference);
            }

        }


        log.info("CONFERENCES STATUS RESET CLEAR");
    }

}
