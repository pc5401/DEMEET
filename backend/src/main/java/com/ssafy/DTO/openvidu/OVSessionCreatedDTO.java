package com.ssafy.DTO.openvidu;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OVSessionCreatedDTO extends OVBaseDTO {
    //    {sessionId=ses_KEY10EWVwb,
    //    uniqueSessionId=ses_KEY10EWVwb_1659920022264,
    //    timestamp=1659920022264,
    //    event=sessionCreated}
}
