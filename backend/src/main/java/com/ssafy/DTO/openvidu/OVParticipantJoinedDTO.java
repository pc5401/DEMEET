package com.ssafy.DTO.openvidu;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OVParticipantJoinedDTO extends OVBaseDTO {
//    {sessionId=ses_KEY10EWVwb,
//    uniqueSessionId=ses_KEY10EWVwb_1659920022264,
//    timestamp=1659920022360,
//    participantId=con_AvB0afXvS2,
//    connectionId=con_AvB0afXvS2,
//    location=unknown,
//    ip=172.17.0.1,
//    platform=Microsoft Edge 104.0.1293.47 on Windows 10 64-bit,
//    clientData=,
//    serverData=,
//    event=participantJoined}
    String participantId;
    String connectionId;
    String location;
    String ip;
    String platform;
    String clientData;
    String serverData;
}
