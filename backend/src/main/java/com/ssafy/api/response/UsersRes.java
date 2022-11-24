package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Users;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 회원 본인 정보 조회 API ([GET] /users/{uid});
 */
@Getter
@Setter
@ApiModel("UsersResponse")
public class UsersRes extends BaseResponseBody {


    @ApiModelProperty(name="user's uid")
    Long uid;
    @ApiModelProperty(name="user's email")
    String email;
    @ApiModelProperty(name="user's nickname")
    String nickname;
    @ApiModelProperty(name="user's regdate")
    LocalDateTime regdate;


    public static UsersRes of(Users newUser){
        UsersRes res = new UsersRes();
        res.setUid(newUser.getUid());
        res.setEmail(newUser.getEmail());
        res.setNickname(newUser.getNickname());
        res.setRegdate(newUser.getRegDate());
        return res;
    }
}
