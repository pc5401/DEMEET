package com.ssafy.DTO.project;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DrawingPathDTO {
    long dipid;
    String url;
}
