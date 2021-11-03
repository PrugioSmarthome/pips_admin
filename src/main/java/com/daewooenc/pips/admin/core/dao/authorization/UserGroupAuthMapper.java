package com.daewooenc.pips.admin.core.dao.authorization;

import com.daewooenc.pips.admin.core.domain.authorization.UserGroup;
import com.daewooenc.pips.admin.core.domain.authorization.UserGroupAuth;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 사용자 그룹 권한 mapper.
 *
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface UserGroupAuthMapper {

	/**
	 * 사용자 그룹 정보 체크
	 * @param userGroupId
	 * @return
	 */
	int checkUserGroup(@Param("userGroupId") String userGroupId);

	/**
	 * 가장 최근 등록된 user group id 정보 조회
	 * @return
	 */
	String getLastUserGroupId();

	/**
	 * 그룹ID를 통한 사용자 그룹 타입 조회
	 * @return
	 */
	String getUserGroupName(@Param("userGroupId") String userGroupId);

	/**
	 * 관리자 메뉴 권한 확인
	 * @param userId
	 * @return
	 */
	List<UserGroupAuth> checkUserAuth(@Param("userId") String userId);

	/**
	 * 시스템 관리자용 메뉴 추가 시 선택한 관리자에 권한정보 맵핑 처리
	 * @param userGroup
	 * @return
	 */
	int insertUserGroup(UserGroup userGroup);

	/**
	 * 시스템 관리자용 메뉴 추가 시 선택한 관리자에 권한정보 맵핑 처리
	 * @param authMap
	 * @return
	 */
	int insertUserGroupAuth(Map<String, Object> authMap);

	/**
	 * 시스템 관리자용 상위메뉴 삭제 전 권한정보 삭제 처리
	 * @param menuNo
	 * @return
	 */
	int deleteUserGroupAuthForUpMenu(@Param("menuNo") int menuNo);

	/**
	 * 시스템 관리자용 하위메뉴 삭제 전 권한정보 삭제 처리
	 * @param menuNo
	 * @return
	 */
	int deleteUserGroupAuthForDownMenu(@Param("menuNo") int menuNo);

	/**
	 * 사용자 그룹 권한 정보.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @return List<UserGroupAuth>
	 */
	List<UserGroupAuth> getUserGroupAuth(@Param(value = "userGroupId") String userGroupId);

	/**
	 * 사용자 그룹 메뉴 정보.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @return List<UserGroupAuth>
	 */
	List<UserGroupAuth> getUserGroupMenu(@Param(value = "userGroupId") String userGroupId);

	/**
	 * 변경된 권한 UI에 대한 데이터 변경처리
	 * @param authMap
	 * @return
	 */
	int updateUserGroupAuth(Map<String, Object> authMap);

	/**
	 * 사용자 그룹 정보 삭제
	 * @param userGroupId
	 * @return
	 */
	int deleteUserGroup(@Param(value = "userGroupId") String userGroupId);

	/**
	 * 사용자 그룹 권한 맵핑 정보 삭제
	 * @param userGroupId
	 * @return
	 */
	int deleteUserGroupAuth(@Param(value = "userGroupId") String userGroupId);

	/**
	 * 등록.
	 *
	 * @param userGroupAuth 사용자그룹권한
	 * @return int
	 */
	int insert(UserGroupAuth userGroupAuth);

	/**
	 * 삭제.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @return int
	 */
	int delete(String userGroupId);

	/**
	 * 최상단 메뉴.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @param sessionLanguage 세션언어
	 * @return List<UserGroupAuth>
	 */
	List<UserGroupAuth> getTopMenu(@Param(value = "userGroupId") String userGroupId, @Param(value = "sessionLanguage") String sessionLanguage);

	/**
	 * 하위 메뉴.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @param upMenuNo 상위메뉴
	 * @param stepNo depth
	 * @param sessionLanguage 세션언어 
	 * @return List<UserGroupAuth>
	 */
	List<UserGroupAuth> getChildMenu(
            @Param(value = "userGroupId") String userGroupId,
            @Param(value = "upMenuNo") int upMenuNo,
            @Param(value = "stepNo") int stepNo,
            @Param(value = "sessionLanguage") String sessionLanguage);

}
