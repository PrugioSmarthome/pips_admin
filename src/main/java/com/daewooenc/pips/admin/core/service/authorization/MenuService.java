package com.daewooenc.pips.admin.core.service.authorization;

import com.daewooenc.pips.admin.core.dao.authorization.MenuMapper;
import com.daewooenc.pips.admin.core.dao.authorization.UserGroupAuthMapper;
import com.daewooenc.pips.admin.core.dao.authorization.UserGroupMapper;
import com.daewooenc.pips.admin.core.domain.authorization.Menu;
import com.daewooenc.pips.admin.core.domain.authorization.UserGroupAuth;
import com.daewooenc.pips.admin.web.common.UserType;
import org.apache.commons.lang3.BooleanUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 메뉴관리 관련 Service.
 *
 */
@Service
public class MenuService {
	/**
	 * logger.
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/** the mapper. */
	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private UserGroupAuthMapper userGroupAuthMapper;

	@Autowired
	private PlatformTransactionManager transactionManager;

	/**
	 * 시스템 관리자용 메뉴 개수 조회
	 * @return
	 */
	public int getMenuLastNo() {
		return menuMapper.selectMenuLastNo();
	}

	/**
	 * 시스템 관리자용 메뉴정보 등록 (권한없을 시)
	 * @param menu
	 * @return
	 */
	public boolean insert(Menu menu) {
		return menuMapper.insertMenu(menu) > 0;
	}

	/**
	 * 시스템 관리자용 메뉴등록시 관리자 선택시 권한도 등록처리
	 * @param menu
	 * @param menuTargetType
	 */
	public void insertMenuInfo(Menu menu, String menuTargetType) {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

		try {
			boolean insertResult = insert(menu);

			if (BooleanUtils.isTrue(insertResult)) {
				List<UserGroupAuth> userGroupAuthList = new ArrayList<>();
				Map<String, Object> userGroupAuthMap = new HashMap<String, Object>();

				int menuNo = menu.getMenuNo();
				int upMenuNo = 0;
				int stepNo = 1;
				int displayOrder = menu.getDisplayOrder();

				if (menuTargetType.equals("ALL")) {
					UserType[] userTypes = UserType.values();

					for (int i=0; i<userTypes.length; i++) {
//						String authType = userTypes[i].name().equals(UserType.SYSTEM_ADMIN.name()) ? "A" : "R";

						UserGroupAuth userGroupAuth = new UserGroupAuth();
//						userGroupAuth.setUserGroupId(userTypes[i].getGroupId());
						userGroupAuth.setMenuNo(menuNo);
//						userGroupAuth.setAuthType(authType);
						userGroupAuth.setUpMenuNo(upMenuNo);
						userGroupAuth.setStepNo(stepNo);
						userGroupAuth.setDisplayOrder(displayOrder);

						userGroupAuthList.add(userGroupAuth);
					}
				} else if (menuTargetType.equals("SYSTEM")) {
					String authType = "A";

					UserGroupAuth userGroupAuth = new UserGroupAuth();
//					userGroupAuth.setUserGroupId(UserType.SYSTEM_ADMIN.getGroupId());
					userGroupAuth.setMenuNo(menuNo);
					userGroupAuth.setAuthType(authType);
					userGroupAuth.setUpMenuNo(upMenuNo);
					userGroupAuth.setStepNo(stepNo);
					userGroupAuth.setDisplayOrder(displayOrder);

					userGroupAuthList.add(userGroupAuth);
				} else if (menuTargetType.equals("COMPLEX")) {
					String authType = "R";

					UserGroupAuth userGroupAuth = new UserGroupAuth();
//					userGroupAuth.setUserGroupId(UserType.COMPLEX_ADMIN.getGroupId());
					userGroupAuth.setMenuNo(menuNo);
					userGroupAuth.setAuthType(authType);
					userGroupAuth.setUpMenuNo(upMenuNo);
					userGroupAuth.setStepNo(stepNo);
					userGroupAuth.setDisplayOrder(displayOrder);

					userGroupAuthList.add(userGroupAuth);
				}

				userGroupAuthMap.put("list", userGroupAuthList);

				userGroupAuthMapper.insertUserGroupAuth(userGroupAuthMap);
			}
		} catch (Exception e) {
			log.debug("insertMenuInfo Exception: " + e.getCause());
			transactionManager.rollback(transactionStatus);
		}

		transactionManager.commit(transactionStatus);
	}

	/**
	 * 시스템 관리자용 상위 메뉴정보 수정
	 * @param menu
	 * @param menuTargetType
	 */
	public void updateMenuItemInfoForUpMenu(Menu menu, String menuTargetType) {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

		try {
			boolean updateResult = menuMapper.updateMenu(menu) > 0;
			int menuNo = menu.getMenuNo();

			if (BooleanUtils.isTrue(updateResult)) {
				userGroupAuthMapper.deleteUserGroupAuthForUpMenu(menuNo);

				List<Menu> upMenuInfoList = menuMapper.selectMenuInfoList(menuNo);

				List<UserGroupAuth> userGroupAuthList = new ArrayList<>();
				Map<String, Object> userGroupAuthMap = new HashMap<String, Object>();

				for(int i=0; i<upMenuInfoList.size(); i++) {
					int selectedMenuNo = upMenuInfoList.get(i).getMenuNo();
					int upMenuNo = upMenuInfoList.get(i).getUpMenuNo();
					int stepNo = upMenuInfoList.get(i).getStepNo();
					int displayOrder = upMenuInfoList.get(i).getDisplayOrder();

					if (menuTargetType.equals("ALL")) {
						UserType[] userTypes = UserType.values();

						for (int j=0; j<userTypes.length; j++) {
							UserGroupAuth userGroupAuth = new UserGroupAuth();

//							String authType = userTypes[j].name().equals(UserType.SYSTEM_ADMIN.name()) ? "A" : "R";

//							userGroupAuth.setUserGroupId(userTypes[j].getGroupId());
							userGroupAuth.setMenuNo(selectedMenuNo);
//							userGroupAuth.setAuthType(authType);
							userGroupAuth.setUpMenuNo(upMenuNo);
							userGroupAuth.setStepNo(stepNo);
							userGroupAuth.setDisplayOrder(displayOrder);

							userGroupAuthList.add(userGroupAuth);
						}
					} else if (menuTargetType.equals("SYSTEM")) {
						UserGroupAuth userGroupAuth = new UserGroupAuth();

//						userGroupAuth.setUserGroupId(UserType.SYSTEM_ADMIN.getGroupId());
						userGroupAuth.setMenuNo(selectedMenuNo);
						userGroupAuth.setAuthType("A");
						userGroupAuth.setUpMenuNo(upMenuNo);
						userGroupAuth.setStepNo(stepNo);
						userGroupAuth.setDisplayOrder(displayOrder);

						userGroupAuthList.add(userGroupAuth);
					} else if (menuTargetType.equals("COMPLEX")) {
						UserGroupAuth userGroupAuth = new UserGroupAuth();

//						userGroupAuth.setUserGroupId(UserType.COMPLEX_ADMIN.getGroupId());
						userGroupAuth.setMenuNo(selectedMenuNo);
						userGroupAuth.setAuthType("R");
						userGroupAuth.setUpMenuNo(upMenuNo);
						userGroupAuth.setStepNo(stepNo);
						userGroupAuth.setDisplayOrder(displayOrder);

						userGroupAuthList.add(userGroupAuth);
					}
				}

				if (!menuTargetType.equals("NONE")) {
					userGroupAuthMap.put("list", userGroupAuthList);

					userGroupAuthMapper.insertUserGroupAuth(userGroupAuthMap);
				}
			}
		} catch (Exception e) {
			log.debug("updateMenuItemInfoForUpMenu Exception: " + e.getCause());
			transactionManager.rollback(transactionStatus);
		}

		transactionManager.commit(transactionStatus);
	}

	/**
	 * 시스템 관리자용 하위 메뉴정보 수정
	 * @param menu
	 * @param menuTargetType
	 */
	public void updateMenuItemInfoForDownMenu(Menu menu, String menuTargetType) {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

		try {
			boolean updateResult = menuMapper.updateMenu(menu) > 0;int menuNo = menu.getMenuNo();

			if (BooleanUtils.isTrue(updateResult)) {
				userGroupAuthMapper.deleteUserGroupAuthForDownMenu(menuNo);

				List<Menu> downMenuInfoList = menuMapper.selectMenuInfoList(menuNo);

				List<UserGroupAuth> userGroupAuthList = new ArrayList<>();
				Map<String, Object> userGroupAuthMap = new HashMap<String, Object>();

				for(int i=0; i<downMenuInfoList.size(); i++) {
					int selectedMenuNo = downMenuInfoList.get(i).getMenuNo();
					int upMenuNo = downMenuInfoList.get(i).getUpMenuNo();
					int stepNo = downMenuInfoList.get(i).getStepNo();
					int displayOrder = downMenuInfoList.get(i).getDisplayOrder();

					if (menuTargetType.equals("ALL")) {
						UserType[] userTypes = UserType.values();

						for (int j=0; j<userTypes.length; j++) {
							UserGroupAuth userGroupAuth = new UserGroupAuth();

//							String authType = userTypes[j].name().equals(UserType.SYSTEM_ADMIN.name()) ? "A" : "R";

//							userGroupAuth.setUserGroupId(userTypes[j].getGroupId());
							userGroupAuth.setMenuNo(selectedMenuNo);
//							userGroupAuth.setAuthType(authType);
							userGroupAuth.setUpMenuNo(upMenuNo);
							userGroupAuth.setStepNo(stepNo);
							userGroupAuth.setDisplayOrder(displayOrder);

							userGroupAuthList.add(userGroupAuth);
						}
					} else if (menuTargetType.equals("SYSTEM")) {
						UserGroupAuth userGroupAuth = new UserGroupAuth();

//						userGroupAuth.setUserGroupId(UserType.SYSTEM_ADMIN.getGroupId());
						userGroupAuth.setMenuNo(selectedMenuNo);
						userGroupAuth.setAuthType("A");
						userGroupAuth.setUpMenuNo(upMenuNo);
						userGroupAuth.setStepNo(stepNo);
						userGroupAuth.setDisplayOrder(displayOrder);

						userGroupAuthList.add(userGroupAuth);
					} else if (menuTargetType.equals("COMPLEX")) {
						UserGroupAuth userGroupAuth = new UserGroupAuth();

//						userGroupAuth.setUserGroupId(UserType.COMPLEX_ADMIN.getGroupId());
						userGroupAuth.setMenuNo(selectedMenuNo);
						userGroupAuth.setAuthType("R");
						userGroupAuth.setUpMenuNo(upMenuNo);
						userGroupAuth.setStepNo(stepNo);
						userGroupAuth.setDisplayOrder(displayOrder);

						userGroupAuthList.add(userGroupAuth);
					}
				}

				if (!menuTargetType.equals("NONE")) {
					userGroupAuthMap.put("list", userGroupAuthList);

					userGroupAuthMapper.insertUserGroupAuth(userGroupAuthMap);
				}
			}

		} catch (Exception e) {
			log.debug("updateMenuItemInfoForDownMenu Exception: " + e.getCause());
			transactionManager.rollback(transactionStatus);
		}

		transactionManager.commit(transactionStatus);
	}

	public void updateMenuItemInfo(Menu menu, String menuTargetType) {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

		try {
			int menuNo = menu.getMenuNo();

			boolean updateResult = menuMapper.update(menu) > 0;

			if (BooleanUtils.isTrue(updateResult)) {
				userGroupAuthMapper.deleteUserGroupAuthForDownMenu(menuNo);

				List<UserGroupAuth> userGroupAuthList = new ArrayList<>();
				Map<String, Object> userGroupAuthMap = new HashMap<String, Object>();

				String authType = "R";
				int upMenuNo = 0;
				int stepNo = 1;
				int displayOrder = menu.getDisplayOrder();

				if (menuTargetType.equals("ALL")) {
					UserType[] userTypes = UserType.values();

					for (int i=0; i<userTypes.length; i++) {
						UserGroupAuth userGroupAuth = new UserGroupAuth();
//						userGroupAuth.setUserGroupId(userTypes[i].getGroupId());
						userGroupAuth.setMenuNo(menuNo);
						userGroupAuth.setAuthType(authType);
						userGroupAuth.setUpMenuNo(upMenuNo);
						userGroupAuth.setStepNo(stepNo);
						userGroupAuth.setDisplayOrder(displayOrder);

						userGroupAuthList.add(userGroupAuth);
					}
				} else if (menuTargetType.equals("SYSTEM")) {
					UserGroupAuth userGroupAuth = new UserGroupAuth();
//					userGroupAuth.setUserGroupId(UserType.SYSTEM_ADMIN.getGroupId());
					userGroupAuth.setMenuNo(menuNo);
					userGroupAuth.setAuthType(authType);
					userGroupAuth.setUpMenuNo(upMenuNo);
					userGroupAuth.setStepNo(stepNo);
					userGroupAuth.setDisplayOrder(displayOrder);

					userGroupAuthList.add(userGroupAuth);
				} else if (menuTargetType.equals("COMPLEX")) {
					UserGroupAuth userGroupAuth = new UserGroupAuth();
//					userGroupAuth.setUserGroupId(UserType.COMPLEX_ADMIN.getGroupId());
					userGroupAuth.setMenuNo(menuNo);
					userGroupAuth.setAuthType(authType);
					userGroupAuth.setUpMenuNo(upMenuNo);
					userGroupAuth.setStepNo(stepNo);
					userGroupAuth.setDisplayOrder(displayOrder);

					userGroupAuthList.add(userGroupAuth);
				}

				userGroupAuthMap.put("list", userGroupAuthList);

				userGroupAuthMapper.insertUserGroupAuth(userGroupAuthMap);
			} else if (BooleanUtils.isFalse(updateResult)) {
				transactionManager.rollback(transactionStatus);
			}
		} catch (Exception e) {
			log.debug("updateMenuItemInfo Exception: " + e.getCause());
			transactionManager.rollback(transactionStatus);
		}

		transactionManager.commit(transactionStatus);
	}

	/**
	 * 시스템 관리자용 메뉴 리스트 정보 순서 수정
	 * @param menuMap
	 * @return
	 */
	public boolean updateMenuItemOrderList(Map<String, Object> menuMap) {
		return menuMapper.updateMenuItemOrderList(menuMap) > 0;
	}

	/**
	 * 시스템 관리자용 메뉴 및 권한 정보 삭제
	 * @param menuNo
	 * @param upMenuNo
	 */
	public void deleteMenuInfo(int menuNo, int upMenuNo) {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

		try {
			if (upMenuNo == 0) {
				userGroupAuthMapper.deleteUserGroupAuthForUpMenu(menuNo);
				menuMapper.deleteMenuItemForUpMenu(menuNo);
			} else if (upMenuNo != 1) {
				userGroupAuthMapper.deleteUserGroupAuthForDownMenu(menuNo);
				menuMapper.deleteMenuItemForDownMenu(menuNo);
			}
		} catch (Exception e) {
			log.debug("deleteMenuInfo Exception: " + e.getCause());
			transactionManager.rollback(transactionStatus);
		}

		transactionManager.commit(transactionStatus);
	}
	
	/**
	 * 메뉴 목록.
	 *
	 * @return List<Menu>
	 */
	public List<Menu> getListMenu() {
		return menuMapper.list();
	}

	public String selectUpMenuNo(String viewPath, String userGroupId) {
		Menu menu = new Menu();
		menu.setViewPath(viewPath);
		menu.setUserGroupId(userGroupId);

		String upMenuNo = menuMapper.selectUpMenuNo(menu);

		return upMenuNo;
	}


}
