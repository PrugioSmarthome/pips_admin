package com.daewooenc.pips.admin.core.dao.authorization;

import com.daewooenc.pips.admin.core.domain.authorization.AuthPassword;
import com.daewooenc.pips.admin.core.domain.authorization.ChangePassword;
import com.daewooenc.pips.admin.core.domain.authorization.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 비밀번호 변경 mapper.
 *
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface PasswordMapper {

	/**
	 * 비밀번호 확인.
	 *
	 * @param changePassword 비밀번호
	 * @return User
	 */
	User checkPassword(@Param("changePassword") ChangePassword changePassword);

	/**
	 * 인증정보 확인.
	 *
	 * @param userId 아이디
	 * @return User
	 */
	AuthPassword selectAuth(@Param("userId") String userId);

	/**
	 * 비밀번호 변경.
	 *
	 * @param changePassword 비밀번호
	 * @return int
	 */
	int updatePassword(@Param("changePassword") ChangePassword changePassword, @Param("encKey") String encKey);

	/**
	 * 비밀번호 변경. (재설정)
	 *
	 * @param changePassword 비밀번호
	 * @return int
	 */
	int updatePasswordForReset(@Param("changePassword") ChangePassword changePassword);

	/**
	 * 비밀번호 설정을 위한 인증확인 정보 등록
	 * @param password
	 * @return
	 */
	int updateAuthForReset(@Param("authPassword") AuthPassword password, @Param("encKey") String encKey);

	/**
	 * 비밀번호 설정을 위한 인증확인 정보 확인 후 인증여부 수정
	 * @param password
	 * @return
	 */
	int updateAuthCompleteForReset(@Param("authPassword") AuthPassword password, @Param("encKey") String encKey);
}
