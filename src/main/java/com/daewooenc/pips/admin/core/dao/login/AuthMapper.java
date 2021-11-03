package com.daewooenc.pips.admin.core.dao.login;

import com.daewooenc.pips.admin.core.domain.authorization.User;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 인증 Mapper.
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface AuthMapper {

	/**
	 * 세션 유저 조회.
	 *
	 * @param sessionUser
	 * @return SessionUser
	 */
	SessionUser login(@Param("sessionUser") SessionUser sessionUser);

	/**
	 * 최종 로그인 시간 저장.
	 *
	 * @param sessionUser 세션 유저
	 */
	void	updateLastLoginDateTime(@Param("sessionUser") SessionUser sessionUser);


	/**
	 * 로그인 실패 횟수 저장.
	 *
	 * @param userId 사용자ID
	 * @return int
	 */
	int		updateLoginFailCount(@Param("userId") String userId);


	/**
	 * 로그인 일자 조회.
	 *
	 * @param userId 사용자ID
	 * @return Map<String,String>
	 */
	Map<String, String>	getLoginDate(@Param("userId") String userId);


	/**
	 * 패스워드 만료 여부 확인.
	 *
	 * @param userId 사용자ID
	 * @return int
	 */
	int isOverPasswordDueDate(@Param("userId") String userId);

//	int		isOverPasswordChangePeriod(String userId);

	/**
	 * 로그인 실패 횟수 조회.
	 *
	 * @param userId 사용자ID
	 * @return Integer
	 */
	Integer	getLoginFailCount(@Param("userId") String userId);


	/**
	 * 사용자 계정 잠금 처리.
	 *
	 * @param userId 사용자ID
	 * @return int
	 */
	int		setAccountLock(@Param("userId") String userId);

	/**
	 * 사용자 유무 조회
	 *
	 * @param userId 사용자ID
	 * @return Integer
	 */
	Integer	getUserCount(@Param("userId") String userId);

	/**
	 * 사용자 계정 잠금 여부 확인.
	 *
	 * @param userId 사용자ID
	 * @return String
	 */
	String	getAccountLock(@Param("userId") String userId);

	/**
	 * 사용자 최초접속여부 확인
	 *
	 * @param userId 사용자 ID
	 * @return String
	 */
	String checkInitAccount(@Param("userId") String userId);

	int updateMyInfo(User user);
}
