package com.ssafy.api.service;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ssafy.common.customException.NotImageException;
import com.ssafy.db.entity.Conferences;
import com.ssafy.db.entity.DrawingImgPath;
import com.ssafy.db.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public interface AwsS3Service {
    // 파일을 업로드 한다.
    String putImage(MultipartFile multipartFile, Long id, String flag) throws IOException, NotImageException;

    // dipid를 받으면 이미지 주소를 리턴한다.
    String getImage(Long dipid);

    // dipid를 받으면 이미지를 삭제한다.
    void DeleteImage(Long dipid, String flag) throws NotImageException;

    String getFolderName(Long imgid, String flag);

    // pid를 기반으로 파일 이름을 생성한다.
    String getFileName(String originalFileName, Long id, String flag) throws NotImageException;

    // 파일의 형식을 판단한다.
    String getFileExtension(String fileName) throws NotImageException;

    // 프로필 이미지를 DB에 저장한다.
    Users saveProfileImagePath(String path, long uid, String flag) throws NotImageException;

    // 드로잉 이미지를 DB에 저장한다.
    DrawingImgPath saveDrawingImagePath(String path, Conferences conference, Users user, String drawing);

    // S3Object 리스트를 불러온다.
    ListIterator<S3ObjectSummary> getS3Object(String folderPath);

    // 특정 dipid 삭제
    void deleteDrawingPath(long dipid) throws NotImageException;
}
