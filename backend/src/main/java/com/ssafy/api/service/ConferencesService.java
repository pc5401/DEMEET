package com.ssafy.api.service;

import com.ssafy.common.customException.ConferenceNullException;
import com.ssafy.db.entity.Conferences;

public interface ConferencesService {
    Conferences findConferencesBySessionNameAndActivation(String openviduSessionId) throws ConferenceNullException;
}
