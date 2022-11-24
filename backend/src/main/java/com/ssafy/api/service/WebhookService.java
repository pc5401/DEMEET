package com.ssafy.api.service;

import com.ssafy.DTO.openvidu.OVSessionCreatedDTO;
import com.ssafy.DTO.openvidu.OVSessionDestroyedDTO;
import com.ssafy.db.entity.Conferences;

public interface WebhookService {


    Conferences makeConferenceWithOvSessionCreatedReq(OVSessionCreatedDTO ovSessionCreatedDTO);

    Conferences editConferenceWithOvSessionDestroyed(OVSessionDestroyedDTO ovSessionDestroyedDTO);
}
