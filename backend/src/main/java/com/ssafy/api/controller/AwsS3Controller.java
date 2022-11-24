package com.ssafy.api.controller;

import com.ssafy.api.request.ImageUploadReq;
import com.ssafy.api.service.AwsS3Service;
import com.ssafy.api.service.ProjectsService;
import com.ssafy.api.service.UsersService;
import com.ssafy.common.auth.SsafyUsersDetails;
import com.ssafy.common.customException.NotImageException;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.ProfileImagePath;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.Users;
import com.ssafy.db.repository.ProfileImagePathRepository;
import com.ssafy.db.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RestController
@RequestMapping("/S3")
@RequiredArgsConstructor
public class AwsS3Controller {
    // 필요할거 같은 기능? 이미지 업로드, 가져오기, 삭제

    @Autowired
    private final AwsS3Service awsS3Service;

    @Autowired
    private final UsersService usersService;

    @Autowired
    private final ProjectsService projectsService;

    @Autowired
    private final ProfileImagePathRepository profileImagePathRepository;

    @PostMapping("/drawing")
    public ResponseEntity<BaseResponseBody> uploadDrawingImage(@ApiIgnore Authentication authentication, @ModelAttribute ImageUploadReq imageUploadReq){
        return null;
    }
}
