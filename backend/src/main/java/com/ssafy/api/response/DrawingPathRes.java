package com.ssafy.api.response;

import com.ssafy.DTO.project.DrawingPathDTO;
import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class DrawingPathRes extends BaseResponseBody {

    List<DrawingPathDTO> drawingPathList;

    public DrawingPathRes(){super();}

    public static DrawingPathRes of(Integer statusCode, String message, List<DrawingPathDTO> drawingPathList){

        DrawingPathRes drawingPathRes = new DrawingPathRes();
        drawingPathRes.setStatusCode(statusCode);
        drawingPathRes.setMessage(message);
        drawingPathRes.setDrawingPathList(drawingPathList);

        return drawingPathRes;
    }
}
