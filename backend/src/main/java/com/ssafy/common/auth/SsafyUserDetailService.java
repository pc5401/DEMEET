package com.ssafy.common.auth;

import com.ssafy.api.service.UsersService;
import com.ssafy.common.customException.UserNullException;
import com.ssafy.db.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@Component
public class SsafyUserDetailService implements UserDetailsService{
	@Autowired
	UsersService usersService;
	
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException  {
		Users user = null;
		try {
			user = usersService.getUsersByUserEmail(email);
		} catch (UserNullException e) {
			throw new RuntimeException(e);
		}
		if(user != null) {
    			SsafyUsersDetails userDetails = new SsafyUsersDetails(user);
    			return userDetails;
    		}
    		return null;
    }
}
