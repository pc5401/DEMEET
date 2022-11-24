package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersEmailDuplicateRes extends BaseResponseBody {

    Boolean check;

    public static UsersEmailDuplicateRes of(Integer statusCode, String Message, Boolean check){
        UsersEmailDuplicateRes res = new UsersEmailDuplicateRes();
        res.setStatusCode(statusCode);
        res.setMessage(Message);
        res.setCheck(check);
        return res;
    }
}
