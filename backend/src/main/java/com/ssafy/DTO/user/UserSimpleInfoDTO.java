package com.ssafy.DTO.user;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSimpleInfoDTO {
    Long uid;
    String email;
    String nickname;
    String profileImagePath;

    public UserSimpleInfoDTO() {
    }

    @QueryProjection
    public UserSimpleInfoDTO(Long uid, String email, String nickname, String profileImagePath) {
        this.uid = uid;
        this.email = email;
        this.nickname = nickname;
        this.profileImagePath = profileImagePath;
    }

    public UserSimpleInfoDTO(NumberPath<Long> uid, StringPath email, StringPath nickname, StringPath path) {
    }
}
