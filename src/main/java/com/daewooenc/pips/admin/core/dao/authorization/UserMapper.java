package com.daewooenc.pips.admin.core.dao.authorization;

import com.daewooenc.pips.admin.core.domain.authorization.User;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 사용자 mapper.
 *
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface UserMapper {

	/**
	 * Gets the user.
	 *
	 * @param userId the userId
	 * @return the user
	 *
	 * 설명 : 사용자 정보
	 */
	User getUser(@Param("userId") String userId, @Param("encKey") String encKey);

	/**
	 * Gets the user for auth
	 *
	 * @param userId the userId
	 * @param houscplxCd the houscplxCd
	 * @param telNo the telNo
	 * @return the user
	 *
	 * 설명 : 사용자 정보 확인
	 */
	User getUserForAuth(@Param("userId") String userId, @Param("houscplxCd") String houscplxCd,
						@Param("telNo") String telNo, @Param("encKey") String encKey);

	/**
	 * Gets the user for auth
	 *
	 * @param userId the userId
	 * @param telNo the telNo
	 * @return the user
	 *
	 * 설명 : 사용자 정보 확인
	 */
	User getUserForSysAdminAuth(@Param("userId") String userId, @Param("telNo") String telNo, @Param("encKey") String encKey);

	/**
	 * Gets the user for auth
	 *
	 * @param userId the userId
	 * @param houscplxCd the houscplxCd
	 * @param telNo the telNo
	 * @param authCode the authCode
	 * @return the user
	 *
	 * 설명 : 사용자 정보 확인
	 */
	User getUserForAuthVerify(@Param("userId") String userId, @Param("houscplxCd") String houscplxCd,
							  @Param("telNo") String telNo, @Param("authCode") String authCode,
							  @Param("encKey") String encKey);

	/**
	 * Gets the user for auth
	 *
	 * @param userId the userId
	 * @param telNo the telNo
	 * @param authCode the authCode
	 * @return the user
	 *
	 * 설명 : 시스템 관리자 사용자 정보 확인
	 */
	User getUserForSysAdminAuthVerify(@Param("userId") String userId, @Param("telNo") String telNo,
									  @Param("authCode") String authCode, @Param("encKey") String encKey);

	/**
	 * List
	 *
	 * @param userId the userId
	 * @return the list
	 *
	 * 설명 : 멀티 단지 관리자 단지 리스트 조회
	 */
	List<User> getMultiDanjiList(@Param("userId") String userId);

	/**
	 * List.
	 *
	 * @param user User
	 * @return the list
	 *
	 * 설명 : 사용자 목록
	 */
	List<User> list(User user);

	/**
	 * Count.
	 *
	 * @param user User
	 * @return the int
	 *
	 * 설명 : 전체 목록수
	 */
	int count(User user);

	/**
	 * Insert.
	 *
	 * @param user the user
	 * @return the int
	 *
	 * 설명 : 계정 삭제
	 */
	int insert(User user);

	/**
	 * multiInsert.
	 *
	 * @param user the user
	 * @return the int
	 *
	 * 설명 : 멀티 단지 관리자 등록
	 */
	int multiInsert(User user);

	/**
	 * multiDelete.
	 *
	 * @param userId the userId
	 * @return the int
	 *
	 * 설명 : 계정 삭제
	 */
	int multiDelete(@Param("userId") String userId);

	/**
	 * Update.
	 *
	 * @param user the user
	 * @return the int
	 *
	 * 설명 : 계정 수정
	 */
	int update(User user);

	int updatePassword(User user);

	/**
	 * Delete.
	 *
	 * @param userId the userId
	 * @return the int
	 *
	 * 설명 : 계정 삭제
	 */
	int delete(@Param("userId") String userId);

	/**
	 * Checks if is exist.
	 *
	 * @param userId the userId
	 * @return the int
	 *
	 * 설명 : 계정이 이미 존재하는지 확인
	 */
	int isExist(@Param("userId") String userId);

	/**
	 * Checks if is use user group.
	 *
	 * @param userGroupId the userGroupId
	 * @return the int
	 *
	 * 설명 : 사용자 그룹의 사용자가 존재하는지 확인
	 */
	int isUseUserGroup(@Param("userGroupId") String userGroupId);


	/**
	 *	gets the user for excel file.
	 *
	 *  @return List<Map<String,String>>
	 *  @param userGroupId String
	 *  @param userGroupLevel String
	 */
	List<Map<String, String>> listExcel(@Param("userGroupId") String userGroupId, @Param("userGroupLevel") String userGroupLevel);


	/**
	 * Gets the user.
	 *
	 * @param userId the userId
	 * @return the user
	 *
	 * 설명 : 사용자 정보
	 */
	User getUserInfo(@Param("userId") String userId);

	List<User> selectUserList(User user);

	List<User> getUserGroupList(User user);

	User selectUser(User user);

	int deleteUser(User user);

	/**
	 * update.
	 *
	 * @param userId, masterYn
	 * @return the int
	 *
	 * 설명 : 마스터 계정 관리 등록
	 */
	int insertUserMaster(@Param("userId") String userId, @Param("masterYn") String masterYn);

	/**
	 * List
	 *
	 * @param userId the userId
	 * @return the list
	 *
	 * 설명 : 멀티 단지 관리자 단지 리스트 조회
	 */
	List<User> getSelectMultiDanjiList(@Param("userId") String userId);

}