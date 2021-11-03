package com.daewooenc.pips.admin.core.service.authorization;

import com.daewooenc.pips.admin.core.domain.authorization.AuthPassword;
import com.daewooenc.pips.admin.core.util.crypto.Sha256Cipher;
import com.daewooenc.pips.admin.core.dao.authorization.PasswordMapper;
import com.daewooenc.pips.admin.core.domain.authorization.ChangePassword;
import com.daewooenc.pips.admin.core.domain.authorization.User;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 비밀번호 변경 Service.
 *
 */
@Service
public class PasswordService {

	/** the logger. */
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private @Value("${pips.encrypt.key}") String pipsEncryptKey;

	/** ChangePasswordMapper Autowired. */
	@Autowired
	private PasswordMapper passwordMapper;

	@Autowired
	private XSSUtil xssUtil;

	/**
	 * 이전 비밀번호 확인.
	 *
	 * @param changePassword 비밀번호
	 * @return boolean
	 */
	public boolean checkPreviousPassword(ChangePassword changePassword) {
		User u = passwordMapper.checkPassword(changePassword);

		return u.checkPreviousPassword(changePassword);
	}

	/**
	 * 인증정보 확인
	 *
	 * @param userId 아이디
	 * @return boolean
	 */
	public AuthPassword selectUserAuth(String userId) {
		AuthPassword authPassword = passwordMapper.selectAuth(userId);

		return authPassword;
	}

	/**
	 * 비밀번호 변경.
	 *
	 * @param changePassword 비밀번호
	 * @return boolean
	 */
	public boolean updatePassword(ChangePassword changePassword) {
		changePassword.setCurrentPassword(new Sha256Cipher(changePassword.getCurrentPassword()).encrypt());
		changePassword.setNewPassword(new Sha256Cipher(changePassword.getNewPassword()).encrypt());
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

		return (passwordMapper.updatePassword(changePassword, pipsEncryptKey) > 0);
	}

    /**
     * 비밀번호 설정을 위한 인증정보 변경
     * @param authPassword
     * @return
     */
	public boolean updateAuthForReset(AuthPassword authPassword) {
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

	    return passwordMapper.updateAuthForReset(authPassword, pipsEncryptKey) > 0;
    }

	/**
	 * 비밀번호 설정을 위한 인증확인 정보 확인 후 인증여부 수정
	 * @param authPassword
	 * @return
	 */
    public boolean updateAuthCompleteForReset(AuthPassword authPassword) {
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

		return passwordMapper.updateAuthCompleteForReset(authPassword, pipsEncryptKey) > 0;
	}

	/**
	 * 비밀번호 변경. (인증 후 재설정)
	 *
	 * @param changePassword 비밀번호
	 * @return boolean
	 */
	public boolean updatePasswordForReset(ChangePassword changePassword) {
		changePassword.setNewPassword(new Sha256Cipher(changePassword.getNewPassword()).encrypt());

		return (passwordMapper.updatePasswordForReset(changePassword) > 0);
	}
}
