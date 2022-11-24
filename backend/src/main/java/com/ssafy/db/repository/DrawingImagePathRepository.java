package com.ssafy.db.repository;

import com.ssafy.db.entity.DrawingImgPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrawingImagePathRepository extends JpaRepository<DrawingImgPath, Long> {
    Optional<DrawingImgPath> findDrawingImgPathByDipid(long dipid);
}
