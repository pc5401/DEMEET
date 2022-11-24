package com.ssafy.DTO.openvidu;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OVParticipantLeftDTO extends OVParticipantJoinedDTO {
//    {sessionId=ses_KEY10EWVwb,
//    uniqueSessionId=ses_KEY10EWVwb_1659920022264,
//    timestamp=1659920064492,
//    startTime=1659920022360,
//    duration=42,
//    reason=disconnect,
//    participantId=con_AvB0afXvS2,
//    connectionId=con_AvB0afXvS2,
//    location=unknown,
//    ip=172.17.0.1,
//    platform=Microsoft Edge 104.0.1293.47 on Windows 10 64-bit,
//    clientData=,
//    serverData=,
//    event=participantLeft}
    String Reason;
    Long duration;
}
