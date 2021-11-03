package com.daewooenc.pips.admin.core.service.authorization;

import com.daewooenc.pips.admin.core.dao.authorization.UserGroupAuthMapper;
import com.daewooenc.pips.admin.core.dao.authorization.UserGroupMapper;
import com.daewooenc.pips.admin.core.domain.authorization.UserGroup;
import com.daewooenc.pips.admin.core.domain.authorization.UserGroupAuth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 사용자 그룹 Service.
 *
 */
@Service
public class UserGroupService {

	/** 로그 출력. */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** 사용자 그룹 Mapper. */
	@Autowired
	private UserGroupMapper userGroupMapper;

	/** 그룹별 메뉴 권한 Mapper. */
	@Autowired
	private UserGroupAuthMapper userGroupAuthMapper;


	/**
	 * List.
	 *
	 * @param userGroupLevel the userGroupLevel
	 * @return the list
	 *
	 * 사용자 그룹 콤보박스를 위해...
	 */
	public List<UserGroup> list(String userGroupLevel) {
		return userGroupMapper.listUserGroup(userGroupLevel);
	}

	/**
	 * userGroupId의 사용자 그룹 정보 get.
	 *
	 * @param userGroupId the userGroupId
	 * @return the user group
	 */
	public UserGroup getUserGroup(String userGroupId) {
		return userGroupMapper.getUserGroup(userGroupId);
	}

	/**
	 * 사용자 그룹 추가.
	 *
	 * @param userGroup the user group
	 * @return the int
	 */
	public boolean insert(UserGroup userGroup) {
		userGroup.setUserGroupId(userGroupMapper.getUserGroupID());

		if (userGroupMapper.insert(userGroup) > 0) {
			logger.debug("==>> user group insert success!!! : {}", userGroup.getUserGroupId());
			insertUserGroupAuth(userGroup.getUserGroupAuth(), userGroup.getUserGroupId());
			return true;
		} else {
			logger.debug("==>> user group insert fail!!! : {}", userGroup.getUserGroupId());
			return false;
		}
	}

	/**
	 * 사용자 그룹 수정.
	 *
	 * @param userGroup the user group
	 * @return the int
	 */
	public boolean update(UserGroup userGroup) {
		if ((userGroupMapper.update(userGroup) > 0)) {
			insertUserGroupAuth(userGroup.getUserGroupAuth(), userGroup.getUserGroupId());
			return true;
		} else
			return false;
	}

	/**
	 * 사용자 그룹 삭제.
	 *
	 * @param userGroupId the userGroupId
	 * @return the int
	 */
	public boolean delete(String userGroupId) {
		userGroupAuthMapper.delete(userGroupId);
		return (userGroupMapper.delete(userGroupId) > 0);
	}

	/**
	 * 사용자 그룹명이 중복되는지 검사.
	 *
	 * @param userGroup the user group
	 * @return true, if is exist
	 */
	public boolean isExist(UserGroup userGroup) {
		return (userGroupMapper.isExist(userGroup) > 0);
	}

	/**
	 * jsp에서 만든 groupAuthData 문자열을 파싱하여 UserGroupAuth에 넣어주고 DB에 저장.
	 *
	 * @param groupAuthData the group auth data
	 * @param userGroupId the userGroupId
	 * @return true, if successful
	 */
	private boolean insertUserGroupAuth(String groupAuthData, String userGroupId) {

		// 문자열이 Null이면 DB처리 못하게...
		if ("".equals(StringUtils.defaultString(groupAuthData))) {
			return false;
		}

		// 먼저 userGroupId에  관계된 데이터를 삭제
		userGroupAuthMapper.delete(userGroupId);

		UserGroupAuth userGroupAuth = new UserGroupAuth();

		String[] st = groupAuthData.split("\\|");
		String[] menuData = new String[6];

		for(int i = 0; i < st.length; i++) {
			menuData = st[i].split(",");

			userGroupAuth.setUserGroupId( ("".equals(menuData[0]) ? userGroupId : menuData[0]));
			userGroupAuth.setMenuNo(Integer.parseInt(menuData[1]));
			userGroupAuth.setAuthType(menuData[2]);
			userGroupAuth.setUpMenuNo(Integer.parseInt(menuData[3]));
			userGroupAuth.setStepNo(Integer.parseInt(menuData[4]));
			userGroupAuth.setDisplayOrder(Integer.parseInt(menuData[5]));

			// 메뉴 권한 디비에 저장
			userGroupAuthMapper.insert(userGroupAuth);
		}

		return true;
	}

	/**
	 * 엑셀 목록.
	 *
	 * @param userGroupLevel 사용자그룹레벨
	 * @return List<Map<String,String>>
	 */
	public List<Map<String, String>> listExcel(String userGroupLevel) {
		return userGroupMapper.listUserGroupExcel(userGroupLevel);
	}

}