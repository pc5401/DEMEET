package com.ssafy.api.request.openvidu;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OVAllInOneReq {
    String sessionId = null;
    String uniqueSessionId = null;
    Long timestamp = null;
    Long startTime = null;
    Long duration = null;
    String reason = null;
    String participantId = null;
    String connectionId = null;
    String location = null;
    String ip = null;
    String platform = null;
    String clientData = null;
    String serverData = null;
    String event = null;
}
