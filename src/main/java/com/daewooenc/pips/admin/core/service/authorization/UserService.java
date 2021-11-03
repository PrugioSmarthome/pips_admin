package com.daewooenc.pips.admin.core.service.authorization;

import com.daewooenc.pips.admin.core.util.crypto.Sha256Cipher;
import com.daewooenc.pips.admin.core.dao.authorization.UserMapper;
import com.daewooenc.pips.admin.core.domain.authorization.User;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 사용자 메뉴 Service.
 *
 */
@Service
public class UserService {

	/** 로그 출력. */
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private @Value("${pips.encrypt.key}") String pipsEncryptKey;

	/** UserMapper Autowired. */
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private XSSUtil xssUtil;

	/**
	 * 사용자 목록.
	 *
	 * @param user User
	 * @return List<User>
	 */
	public List<User> list(User user) {

		List<User> l = userMapper.list(user);

		return l;
	}

	/**
	 * 사용자 총 수.
	 *
	 * @param user User
	 * @return int
	 */
	public int count(User user) {
		return userMapper.count(user);
	}

	/**
	 * 사용자 정보.
	 *
	 * @param userId 사용자ID
	 * @return User
	 */
	public User getUser(String userId) {
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));
		User u = userMapper.getUser(userId, pipsEncryptKey);

		return u;
	}

	/**
	 * 사용자 정보 확인
	 *
	 * @param userId 사용자ID
	 * @return User
	 */
	public User getUserForAuth(String userId, String houscplxCd, String telNo) {
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));
		User u = userMapper.getUserForAuth(userId, houscplxCd, telNo, pipsEncryptKey);

		return u;
	}

	/**
	 * 시스템 관리자 사용자 정보 확인
	 * @param userId
	 * @param telNo
	 * @return
	 */
	public User getUserForSysAdminAuth(String userId, String telNo) {
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));
		User u = userMapper.getUserForSysAdminAuth(userId, telNo, pipsEncryptKey);

		return u;
	}

	/**
	 * 사용자 정보 인증 확인
	 *
	 * @param userId 사용자ID
	 * @return User
	 */
	public User getUserForAuthVerify(String userId, String houscplxCd, String telNo, String authCode) {
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));
		User u = userMapper.getUserForAuthVerify(userId, houscplxCd, telNo, authCode, pipsEncryptKey);

		return u;
	}

	/**
	 * 시스템 관리자 사용자 정보 인증 확인
	 *
	 * @param userId 사용자ID
	 * @return User
	 */
	public User getUserForSysAdminAuthVerify(String userId, String telNo, String authCode) {
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));
		User u = userMapper.getUserForSysAdminAuthVerify(userId, telNo, authCode, pipsEncryptKey);

		return u;
	}

	/**
	 * 멀티 단지 관리자 단지 리스트 조회
	 *
	 * @param userId
	 * @return List<User>
	 */
	public List<User> getMultiDanjiList(String userId) {
		List<User> l = userMapper.getMultiDanjiList(userId);

		return l;
	}

	/**
	 * 등록.
	 *
	 * @param user 사용자정보
	 * @return boolean
	 */
	public boolean insert(User user) {
		user.setPassword(new Sha256Cipher(user.getPassword()).encrypt());
		user.setPasswordDueDate(user.getPasswordDueDate().replaceAll("-", ""));

		return (userMapper.insert(user) > 0);
	}

	/**
	 * 수정.
	 *
	 * @param user 사용자정보
	 * @return boolean
	 */
	public boolean update(User user) {
		if (!"".equals(StringUtils.defaultString(user.getPassword())))
			user.setPassword(new Sha256Cipher(user.getPassword()).encrypt());
		else
			user.setPassword("");

		user.setPasswordDueDate(user.getPasswordDueDate().replace("-", ""));

		return (userMapper.update(user) > 0);
	}

	public boolean updatePassword(User user) {
		if (!"".equals(StringUtils.defaultString(user.getPassword()))) {
			user.setPassword(new Sha256Cipher(user.getPassword()).encrypt());
		}

		return userMapper.updatePassword(user) > 0;
	}

	public boolean updateUser(User user) {
		if (!"".equals(StringUtils.defaultString(user.getPassword()))) {
			user.setPassword(new Sha256Cipher(user.getPassword()).encrypt());
		}

		return userMapper.update(user) > 0;
	}

	/**
	 * 삭제.
	 *
	 * @param userId 사용자ID
	 * @return boolean
	 */
	public boolean delete(String userId) {

		boolean result = userMapper.delete(userId) > 0;

		return result;
	}


	/**
	 * 멀티 단지 삭제.
	 *
	 * @param userId 사용자ID
	 * @return boolean
	 */
	public boolean multiDelete(String userId) {

		boolean result = userMapper.multiDelete(userId) > 0;

		return result;
	}


	/**
	 * 기 존재 여부.
	 *
	 * @param user 사용자정보.
	 * @return boolean
	 */
	public boolean isExist(User user) {
		return (userMapper.isExist(user.getUserId()) > 0);
	}

	/**
	 * 사용자 그룹 사용 여부.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @return boolean
	 */
	public boolean isUseUserGroup(String userGroupId) {
		return (userMapper.isUseUserGroup(userGroupId) > 0);
	}

	/**
	 * 엑셀목록.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @param userGroupLevel 사용자그룹레벨
	 * @return List<Map<String,String>>
	 */
	public List<Map<String, String>> listExcel( String userGroupId,  String userGroupLevel) {

		return userMapper.listExcel(userGroupId,  userGroupLevel);
	}

	
	/**
	 * yyyyMMdd 형태의 문자열을 yyyy-MM-dd로 변환.
	 *
	 * @param yyyyMMdd 입력문자열
	 * @return String
	 */
	public String getDateFormat(final String yyyyMMdd) {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

		String ret = "";

		if (yyyyMMdd == null || yyyyMMdd.length() != 8){
			try {
				ret = df2.format(df1.parse(yyyyMMdd));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	/**
	 * HHmmss 형태의 문자열을 HH:mm:ss 형태로 변환.
	 *
	 * @param HHmmss 입력문자열
	 * @return String
	 */
	public String getTimeFormat(final String HHmmss) {
		SimpleDateFormat df1 = new SimpleDateFormat("HHmmss");
		SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");

		String ret = "";

		if (HHmmss == null || HHmmss.length() != 6);
		else{
			try {
				ret = df2.format(df1.parse(HHmmss));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	public List<User> getUserList(User user) {
		return userMapper.selectUserList(user);
	}

	public User getUserInfo(User user) {
		return userMapper.selectUser(user);
	}

	public boolean insertUser(User user) {
		user.setPassword(new Sha256Cipher(user.getPassword()).encrypt());
		return (userMapper.insert(user) > 0);
	}

	public boolean multiInsertUser(User user) {
		return (userMapper.multiInsert(user) > 0);
	}

	public boolean deleteUser(User user) {
		return userMapper.deleteUser(user) > 0;
	}

	public List<User> getGroupList(User user) {
		List<User> userGroupList = userMapper.getUserGroupList(user);

		return userGroupList;
	}

	/**
	 * 마스터 계정 관리 등록
	 *
	 * @param userId 사용자ID, masterYn 마스터계정여부
	 * @return User
	 */
	public boolean insertUserMaster(String userId, String masterYn) {
		return userMapper.insertUserMaster(userId, masterYn) >0;
	}

    /**
     * 멀티 단지 관리자 단지 리스트 조회(수정)
     *
     * @param userId 사용자ID
     * @return User
     */
    public List<User> getSelectMultiDanjiList(String userId) {
        List<User> l = userMapper.getSelectMultiDanjiList(userId);

        return l;
    }

}