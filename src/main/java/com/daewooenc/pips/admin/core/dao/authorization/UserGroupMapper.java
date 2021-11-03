package com.daewooenc.pips.admin.core.dao.authorization;

import com.daewooenc.pips.admin.core.domain.authorization.UserGroup;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 사용자 그룹 mapper.
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface UserGroupMapper {

	/**
	 * 사용자그룹 정보.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @return UserGroup
	 */
	UserGroup getUserGroup(String userGroupId);

	/**
	 * 신규 사용자그룹 ID.
	 *
	 * @return String
	 */
	String getUserGroupID();

	/**
	 * 등록.
	 *
	 * @param usergroup 그룹정보
	 * @return int
	 */
	int insert(UserGroup usergroup);

	/**
	 * 수정.
	 *
	 * @param usergroup 그룹정보
	 * @return int
	 */
	int update(UserGroup usergroup);

	/**
	 * 삭제.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @return int
	 */
	int delete(String userGroupId);

	/**
	 * 기 존재 여부.
	 *
	 * @param usergroup 그룹정보
	 * @return int
	 */
	int isExist(UserGroup usergroup);

	/**
	 * 사용자그룹 목록.
	 *
	 * @param userGroupLevel 사용자그룹레벨
	 * @return List<Map<String,String>>
	 */
	List<UserGroup> listUserGroup(String userGroupLevel);

	/**
	 * 사용자 그룹 엑셀 목록.
	 *
	 * @param userGroupLevel 사용자그룹레벨
	 * @return List<Map<String,String>>
	 */
	List<Map<String, String>> listUserGroupExcel(String userGroupLevel);

}
