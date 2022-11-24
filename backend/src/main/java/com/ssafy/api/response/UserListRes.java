package com.ssafy.api.response;

import com.ssafy.DTO.user.UserSimpleInfoDTO;
import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListRes extends BaseResponseBody {

    List<UserSimpleInfoDTO> userList;

    public static UserListRes of(Integer statusCode, String message, List<UserSimpleInfoDTO> userList) {
        UserListRes res = new UserListRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setUserList(userList);
        return res;
    }
}
