package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QUsers;
import com.ssafy.db.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * users 모델 관련 디비 쿼리 생성을 위한 구현 정의
 */
@Repository
public class UsersRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QUsers qNewUsers = QUsers.users;


    public Optional<Users> findUserById(Long id) {
        Users User = jpaQueryFactory.select(qNewUsers)
                .from(qNewUsers)
                .where(qNewUsers.uid.eq(id)).fetchOne();
        if (User == null) return Optional.empty();
        return Optional.ofNullable(User);
    }

    public Optional<Users> findUserByEmail(String email) {
        Users newUser = jpaQueryFactory.select(qNewUsers).from(qNewUsers)
                .where(qNewUsers.email.eq(email)).fetchOne();
        if (newUser == null) return Optional.empty();
        return Optional.ofNullable(newUser);
    }

    /**
     * email값을 통해 해당 email이 Users테이블에 저장되어있는지 확인하는 메서드
     *
     * @param email 만약 같은 이메일이 있다면 return true
     *              같은 이메일이 없다면(null값포함) return false
     * @return Boolean
     */
    public Boolean checkEmailDuplicate(String email) {
        String checkEmail = jpaQueryFactory.select(qNewUsers.email).from(qNewUsers).where(qNewUsers.email.eq(email)).fetchOne();
        boolean check = email.equals(checkEmail);
//        log.info("email = " + email);
//        log.info(("check email = " , checkEmail);
//        log.info(("같니? => " + check + "");
        if (checkEmail == null) {
            return false;
        }
        return check;

    }

//    public List<UsersSimpleInfoDTO> getUserList() {
//        List<UsersSimpleInfoDTO> userlist = jpaQueryFactory.select(new QuserSimpleInfoDTO(qNewUsers.uid, qNewUsers.email, qNewUsers.nickname)).
//                from(qNewUsers).fetch();
//        return userlist;
//    }

    public Boolean changeUserPassword(Long uid, String newPassword) {
        Long res = jpaQueryFactory.update(qNewUsers).set(qNewUsers.password, newPassword).where(qNewUsers.uid.eq(uid)).execute();

        return res > 0;
    }

    public Boolean changeUserNickname(Long uid, String newNickname) {
        Long res = jpaQueryFactory.update(qNewUsers).set(qNewUsers.nickname, newNickname).where(qNewUsers.uid.eq(uid)).execute();
        return res > 0;
    }
}
