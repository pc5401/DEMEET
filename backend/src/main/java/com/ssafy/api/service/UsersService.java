package com.ssafy.api.service;

import com.ssafy.DTO.user.UserSimpleInfoDTO;
import com.ssafy.api.request.UsersRegisterPostReq;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.common.customException.UserNullException;
import com.ssafy.db.entity.Users;

import java.util.List;

/**
 * Users 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의
 */
public interface UsersService {

    boolean deleteUser(Long uid);

    Users createUser(UsersRegisterPostReq usersRegisterInfo);

    Users getUsersByUserEmail(String userEmail) throws UserNullException;
    Boolean checkEmailDuplicate(String email);

    List<UserSimpleInfoDTO> getUserList();

    Boolean changeUserPassword(Long uid, String newPassword);

    Boolean changeUserNickname(Long uid, String newNickname);

    Users getUsersByUid(Long ownerId) throws UidNullException, UserNullException;

    String makeTempPassword();
}