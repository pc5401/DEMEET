package com.ssafy.api.controller;


/**
 * Demeet에 사용되는 회원관리 컨트롤러
 * Users테이블을 사용한다.
 */

import com.ssafy.DTO.project.ProjectDeactivateSimpleInfoDTO;
import com.ssafy.DTO.user.UserSimpleInfoDTO;
import com.ssafy.api.request.*;
import com.ssafy.api.response.UserListRes;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.response.UsersMyInfoRes;
import com.ssafy.api.service.AwsS3Service;
import com.ssafy.api.service.ProjectsService;
import com.ssafy.api.service.UserProjectService;
import com.ssafy.api.service.UsersService;
import com.ssafy.common.auth.SsafyUsersDetails;
import com.ssafy.common.customException.NotImageException;
import com.ssafy.common.customException.ProjectNullException;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.common.customException.UserNullException;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.EmailUtil;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@Validated
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    ProjectsService projectsService;

    @Autowired
    UserProjectService userProjectService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AwsS3Service awsS3Service;

    @Value("${whoisxml.key}")
    String apiKey;

    @PostMapping()
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @Validated UsersRegisterPostReq registerInfo) {
        try {
            log.info("회원가입");
            boolean check = checkEmailValidate(registerInfo.getEmail());
//            boolean check = true;
            if (!check) {
                log.error("Email validation failed");
                return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Email validation failed"));
            }
            Users newUser = usersService.createUser(registerInfo);
            log.info("user register success");
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "sign-in success"));
        } catch (Exception e) {
            log.error("Error:{}", e.getMessage());
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<? extends BaseResponseBody> login(@RequestBody @Validated UsersLoginPostReq loginInfo) {
        try {
            log.info("로그인");
            log.debug("Email = {}, Password = {}", loginInfo.getEmail(), loginInfo.getPassword());
            String userEmail = loginInfo.getEmail();
            String userPassword = loginInfo.getPassword();
            Users newUser = usersService.getUsersByUserEmail(userEmail);

            if (passwordEncoder.matches(userPassword, newUser.getPassword())) {
                // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
                log.info("로그인 성공");
                return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(userEmail)));
            }
//        유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
            log.error("로그인 실패");
            return ResponseEntity.status(406).body(BaseResponseBody.of(406, "Invalid Password"));
        } catch (UserNullException e) {
            log.error("cannot find user");
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, "email not matched"));
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, e.getMessage()));
        }

    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponseBody> getMyInfo(Authentication authentication) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        log.info("내정보 조회");
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        // 내가 속한 프로젝트들중에서 끝난 프로젝트들을 가지고온다.
        try {
            log.info("유저정보 조회");
            Users user = usersService.getUsersByUid(ssafyUsersDetails.getUserUid());

            log.info("해당 유저가 속한 프로젝트들 중 종료된 프로젝트리스트 조회");
            List<Projects> deActivateProjectsList = projectsService.getDeactivatedProjectsByUid(user.getUid());

            log.info("받은 프로젝트들들 ProjectDeactivateSimpleInfoDTO방식으로 변경");
            List<ProjectDeactivateSimpleInfoDTO> ProjectDeactivateSimpleInfoDTOList = projectsService.changetProjectListToProjectDeactivateSimpleInfoDTOList(deActivateProjectsList);

            return ResponseEntity.status(200).body(UsersMyInfoRes.of(200, "Success", user, ProjectDeactivateSimpleInfoDTOList));
        } catch (UserNullException e) {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, "fail to find user"));
        } catch (ProjectNullException e) {
            return ResponseEntity.status(423).body(BaseResponseBody.of(423, "fail to find projects"));
        } catch (UidNullException e) {
            return ResponseEntity.status(424).body(BaseResponseBody.of(424, "invalid uid"));
        }
    }

    @PatchMapping("/password/forget")
    public ResponseEntity<BaseResponseBody> changeForgotPassword(@RequestBody UserForgetPasswordReq userForgetPasswordReq) {
        log.info("Change forgot password");
        Users user = null;
        try {
            user = usersService.getUsersByUserEmail(userForgetPasswordReq.getEmail());
            if (!user.getNickname().equals(userForgetPasswordReq.getNickname())) {
                log.error("nickname is incorrect");
                return ResponseEntity.status(426).body(BaseResponseBody.of(426, "nickname is incorrect"));
            }
            log.info("email and nickname matches");
            // 임시 비밀번호 생성
            log.info("make tempPassword");
            String tempPassword = usersService.makeTempPassword();
            log.info("save tempPassword");
            boolean changePassword = usersService.changeUserPassword(user.getUid(), tempPassword);

            if (changePassword) {
                log.info("password change success");
                log.info("send tempPassword to user's email");
                emailUtil.sendEmail(user.getEmail(), user.getEmail(), tempPassword);
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "password change success"));
            } else {
                log.error("server error");
                return ResponseEntity.status(500).body(BaseResponseBody.of(500, "server error during change user password"));
            }

        } catch (UserNullException e) {
            log.error("can't find user");
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, "can't find user"));
        } catch (MessagingException e) {
            log.error("can't send Email");
            return ResponseEntity.status(503).body(BaseResponseBody.of(503, "can't send Email"));
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<BaseResponseBody> checkEmailDuplication(@PathVariable("email") @Email @NotBlank String email) {
        log.info("이메일 중복체크");
        log.debug("email = {}", email);
        boolean check = usersService.checkEmailDuplicate(email);
        // check가 false일경우는 이메일 중복이 없음
        if (!check) {
            log.info("이메일 중복 아님");
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "email checked, no duplicated email"));
        }
        // check가 true일경우는 이메일 중복이 있음
        else {
            log.info("이메일 중복");
            return ResponseEntity.status(401).body(BaseResponseBody.of(401, "email checked, duplicated email, please try another email"));
        }
    }

    @GetMapping()
    public ResponseEntity<UserListRes> getUserList(@ApiIgnore Authentication authentication) {

        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        List<UserSimpleInfoDTO> userList = usersService.getUserList();
        return ResponseEntity.status(200).body(UserListRes.of(200, "user list lookup success", userList));
    }

    @PatchMapping("/password")
    public ResponseEntity<BaseResponseBody> changeUserPassword(@ApiIgnore Authentication authentication, @RequestBody @Validated UserPwChangePostReq userPwChangePostReq) {
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        log.info("유저 비밀번호 변경");
        log.debug("currPassword = {}, newPassword = {}", userPwChangePostReq.getCurrPassword(), userPwChangePostReq.getNewPassword());
        try {
            log.info("토큰을 통해 유저 받아오기");
            Users user = usersService.getUsersByUid(ssafyUsersDetails.getUserUid());
            log.info("passwordEncoder.match를 통해 currPassword값을 user의 현재 비밀번호와 비교");
            boolean check = passwordEncoder.matches(userPwChangePostReq.getCurrPassword(), user.getPassword());
            log.debug("일치여부 = {}", check);
            // 만약 틀리다면 406 에러를 띄운다.
            if (!check) {
                log.info("받은 currPassword와 유저의 현재 비밀번호가 일치하지 않음.");
                return ResponseEntity.status(406).body(BaseResponseBody.of(406, "invalid currPassword"));
            }
            log.info("유저의 비밀번호를 새로운 비밀번호로 바꿔준다.");
            Boolean changeCheck = usersService.changeUserPassword(user.getUid(), userPwChangePostReq.getNewPassword());
            if (changeCheck) {
                log.info("비밀번호 변경 성공");
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success to change user password"));
            }
            log.error("서버 오류로 비밀번호 변경 실패");
            return ResponseEntity.status(500).body(BaseResponseBody.of(500, "server error"));
        } catch (UserNullException e) {
            throw new RuntimeException(e);
        } catch (UidNullException e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/nickname")
    public ResponseEntity<BaseResponseBody> changeUserNickname(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> nicknameMap) {
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        log.info("유저의 비밀번호 변경");
        try {
            String newNickname = nicknameMap.get("nickname");
            log.info("토큰을 통해 유저 받아오기");
            Users user = usersService.getUsersByUid(ssafyUsersDetails.getUserUid());
            log.info("현재 nickname과 newNickname이 같은지 비교");
            boolean check = user.getNickname().equals(newNickname);
            log.debug("일치여부 = {}", check);
            if (check) {
                log.error("현재 닉네임과 새로운 닉네임이 같으므로 변경하지않음.");
                return ResponseEntity.status(426).body(BaseResponseBody.of(426, "new nickname matches current nickname. Please change your new nickname."));
            }
            log.info("닉네임이 다르기 때문에 변경 작업 시작");
            Boolean changeCheck = usersService.changeUserNickname(user.getUid(), newNickname);
            if (changeCheck) {
                log.info("닉네임 변경 성공");
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success to change user's nickname"));
            }
            log.error("서버 오류로 닉네임 변경 실패");
            return ResponseEntity.status(200).body(BaseResponseBody.of(500, "server error"));
        } catch (UserNullException e) {
            throw new RuntimeException(e);
        } catch (UidNullException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping()
    public ResponseEntity<BaseResponseBody> deleteUser(Authentication authentication) {
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        log.info("유저 삭제");
        boolean deleteCheck = usersService.deleteUser(ssafyUsersDetails.getUserUid());
        log.debug("유저 삭제 성공여부 = {}", deleteCheck);
        if (deleteCheck) {
            log.info("삭제 성공");
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success to delete user"));
        } else {
            log.error("삭제 실패");
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, "fail to find user"));

        }

    }

    @PostMapping("/profile")
    public ResponseEntity<BaseResponseBody> uploadProfileImage(@ApiIgnore Authentication authentication, @ModelAttribute ImageUploadReq imageUploadReq) {
        SsafyUsersDetails usersDetails = (SsafyUsersDetails) authentication.getDetails();
        long uid = usersDetails.getUserUid();
        String path = null;
        // 이미지 업로드
        try {
            // 파일, id, 프로필/드로잉 구분
            path = awsS3Service.putImage(imageUploadReq.getMultipartFile(), uid, "profile");
            // 성공시 db에 정보 넣기
            Users users = awsS3Service.saveProfileImagePath(path, uid, "profile");
            users.toString();
        } catch (IOException e) {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        } catch (NotImageException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BaseResponseBody.of(HttpStatus.NOT_ACCEPTABLE.value(), "the File is NOT image"));
        }
        ;
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, path));
    }

    @DeleteMapping("/profile")
    public ResponseEntity<BaseResponseBody> deleteProfileImage(@ApiIgnore Authentication authentication) {
        SsafyUsersDetails usersDetails = (SsafyUsersDetails) authentication.getDetails();
        long uid = usersDetails.getUserUid();
        try {
            // 이미지 삭제
            awsS3Service.DeleteImage(uid, "profile");
            // DB에서 삭제
            // uid를 기반으로 pipid를 찾는다 -> 1:1 대응이라 가능
            awsS3Service.saveProfileImagePath(null, uid, "profile");
        } catch (NotImageException e1) {
            e1.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BaseResponseBody.of(HttpStatus.NOT_ACCEPTABLE.value(), "the File is NOT image"));
        }


        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Profile image Delete Success"));
    }

    public boolean checkEmailValidate(String email) {
        log.info("Checking email validation");
        boolean validateCheck = false;
        String url = "https://emailverification.whoisxmlapi.com/api/v2?apiKey=" + apiKey + "&emailAddress=" + email;
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, Object> result = new HashMap<String, Object>();
        ResponseEntity<?> responseEntity = restTemplate.getForEntity(url, HashMap.class);
        result = (HashMap<String, Object>) responseEntity.getBody();
        log.debug(result.toString());
        // 대부분의 포털사이트 이메일들은 smtpcheck가 true다.
        // 이메일 검증조건을 아래 모두를 충족하는것으로 하겠다
        boolean smtpCheck = Boolean.parseBoolean((String) result.get("smtpCheck"));
        boolean formatCheck = Boolean.parseBoolean((String) result.get("formatCheck"));
        boolean dnsCheck = Boolean.parseBoolean((String) result.get("dnsCheck"));
        if (smtpCheck & formatCheck & dnsCheck)
            validateCheck = true;
        log.info("validateCheck = {}", validateCheck);
        return validateCheck;
    }
}