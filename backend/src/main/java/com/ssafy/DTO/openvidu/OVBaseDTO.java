package com.ssafy.DTO.openvidu;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OVBaseDTO {
    String event;
    String sessionId;
    String uniqueSessionId;
    Long timestamp;

}
