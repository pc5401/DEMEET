package com.ssafy.db.repository;

import com.ssafy.db.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Users 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users>  findByEmail(String email);
    Optional<Users> findUsersByEmail(String email);

    Optional<Users> findByUid(long uid);
}