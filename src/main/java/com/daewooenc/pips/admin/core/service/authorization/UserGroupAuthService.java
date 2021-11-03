package com.daewooenc.pips.admin.core.service.authorization;

import com.daewooenc.pips.admin.core.dao.authorization.MenuMapper;
import com.daewooenc.pips.admin.core.dao.authorization.UserGroupAuthMapper;
import com.daewooenc.pips.admin.core.domain.authorization.Menu;
import com.daewooenc.pips.admin.core.domain.authorization.UserGroup;
import com.daewooenc.pips.admin.core.domain.authorization.UserGroupAuth;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 사용자 그룹 권한 Service.
 *
 */
@Service
public class UserGroupAuthService {

	/** the logger. */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MenuMapper menuMapper;

	/** UserGroupAuthMapper Autowired. */
	@Autowired
	private UserGroupAuthMapper userGroupAuthMapper;

	@Autowired
	private PlatformTransactionManager transactionManager;

	/** Ehcache Autowired. */
	@Autowired
	private Ehcache ehcache;

	public boolean checkUserGroup(String userGroupId) {
		return userGroupAuthMapper.checkUserGroup(userGroupId) > 0;
	}

	public int getLastUserGroupId() {
		String lastUserGroupId = userGroupAuthMapper.getLastUserGroupId();

		return Integer.parseInt(lastUserGroupId);
	}

	public String getUserGroupName(String groupId) {
		String groupName = userGroupAuthMapper.getUserGroupName(groupId);

		return groupName;
	}

	public List<UserGroupAuth> checkUserAuth(String userId) {
		List<UserGroupAuth> authList = userGroupAuthMapper.checkUserAuth(userId);

		return authList;
	}

	/**
	 * 사용자 그룹 메뉴 조회.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @return List<UserGroupAuth>
	 */
	@CacheEvict(value = "authMenuListCache", key = "#userGroupId")
	public List<UserGroupAuth> getUserGroupMenu(String userGroupId) {
		List<UserGroupAuth> auth = userGroupAuthMapper.getUserGroupMenu(userGroupId);

		return auth;
	}

	/**
	 * 사용자 그룹 권한목록 조회.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @return List<UserGroupAuth>
	 */
	public List<UserGroupAuth> getUserGroupAuth(String userGroupId) {
		List<UserGroupAuth> auth = userGroupAuthMapper.getUserGroupAuth(userGroupId);

		return auth;
	}

	/**
	 * 시스템 관리자용 권한그룹 등록
	 * @param userGroupName
	 * @param  description
	 * @param userAuthType
	 */
	public void insertUserGroup(String userGroupName, String description, String userAuthType) {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

		try {
			int userGroupId = getLastUserGroupId() + 1;
			String parsedUserGroupId = "00" + userGroupId;
			String userGroupLevel = "0" + userGroupId;

			UserGroup userGroup = new UserGroup();
			userGroup.setUserGroupId(parsedUserGroupId);
			userGroup.setUserGroupName(userGroupName);
			userGroup.setDescription(description);
			userGroup.setUserGroupLevel(userGroupLevel);

			userGroupAuthMapper.insertUserGroup(userGroup);

			List<Menu> menuList = menuMapper.list();
			Map<String, Object> userGroupAuthMap = new HashMap<>();

			List<UserGroupAuth> insertUserGroupAuthList = new ArrayList<>();

			for (int i=0; i<menuList.size(); i++) {
				Menu currentMenuInfo = menuList.get(i);

				int upMenuNo = 0;

				if (currentMenuInfo.getIsfolder().equals("false")) {
					upMenuNo = 1;
				}

				UserGroupAuth userGroupAuth = new UserGroupAuth();
				userGroupAuth.setUserGroupId(parsedUserGroupId);

				if (userAuthType.equals("N")) {
					if (currentMenuInfo.getMenuNo() == 1 || currentMenuInfo.getMenuNo() == 8) {
						userGroupAuth.setAuthType("R");
					} else if (currentMenuInfo.getMenuNo() != 1 && currentMenuInfo.getMenuNo() != 8) {
						userGroupAuth.setAuthType(userAuthType);
					}
				} else if (!userAuthType.equals("N")) {
					userGroupAuth.setAuthType(userAuthType);
				}

				userGroupAuth.setMenuNo(currentMenuInfo.getMenuNo());
				userGroupAuth.setUpMenuNo(upMenuNo);
				userGroupAuth.setStepNo(currentMenuInfo.getStepNo());
				userGroupAuth.setDisplayOrder(currentMenuInfo.getDisplayOrder());

				insertUserGroupAuthList.add(userGroupAuth);
			}

			userGroupAuthMap.put("list", insertUserGroupAuthList);

			if (userGroupAuthMap.size() > 0) {
				userGroupAuthMapper.insertUserGroupAuth(userGroupAuthMap);
			}

		} catch (Exception e) {
			logger.debug("insertUserGroup Exception: " + e.getCause());
			transactionManager.rollback(transactionStatus);
		}

		transactionManager.commit(transactionStatus);
	}

	/**
	 * 그룹별 권한 정보 수정
	 * @param authMap
	 * @return
	 */
	public boolean updateUserGroupAuth(Map<String, Object> authMap) {
		return userGroupAuthMapper.updateUserGroupAuth(authMap) > 0;
	}

	/**
	 * 시스템 관리자용 권한그룹 삭제
	 * @param userGroupId
	 */
	public void deleteUserGroup(String userGroupId) {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

		try {
			userGroupAuthMapper.deleteUserGroup(userGroupId);
			userGroupAuthMapper.deleteUserGroupAuth(userGroupId);
		} catch (Exception e) {
			logger.debug("deleteUserGroup Exception: " + e.getCause());
			transactionManager.rollback(transactionStatus);
		}

		transactionManager.commit(transactionStatus);
	}

	/**
	 * 등록.
	 *
	 * @param usergroup 그룹권한정보
	 * @return boolean
	 */
	public boolean insert(UserGroupAuth usergroup) {
		return (userGroupAuthMapper.insert(usergroup) > 0);
	}

	/**
	 * 삭제.
	 *
	 * @param userGroupId 그룹ID
	 * @return boolean
	 */
	public boolean delete(String userGroupId) {
		return (userGroupAuthMapper.delete(userGroupId) > 0);
	}
}
