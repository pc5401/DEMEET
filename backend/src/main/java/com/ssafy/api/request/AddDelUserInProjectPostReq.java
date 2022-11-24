package com.ssafy.api.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Positive;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddDelUserInProjectPostReq {
    @Positive
    Long uid;

    @Positive
    Long pid;
}
