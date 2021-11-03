package com.daewooenc.pips.admin.core.service.common;

import com.daewooenc.pips.admin.core.dao.authorization.MenuMapper;
import com.daewooenc.pips.admin.core.dao.common.CommonDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 공통 데이터 Service.
 */
@Service
public class CommonDataService {

	/** CommonDataMapper  Autowired. */
	@Autowired
	private CommonDataMapper commonDataMapper;


	/** MenuMapper  Autowired. */
	@SuppressWarnings("unused")
	@Autowired
	private MenuMapper menuMapper;

	/**
	 * 사용자그룹 목록.
	 *
	 * @param userGroupLevel 사용자그룹레벨
	 * @return List<HashMap<String,String>>
	 */
	public List<HashMap<String, String>> listUserGroup(String userGroupLevel) {
		return commonDataMapper.listUserGroup(userGroupLevel);
	}

	/**
	 * 사용자 목록.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @param userId 사용자ID
	 * @return List<HashMap<String,String>>
	 */
	public List<HashMap<String, String>> listUser(String userGroupId, String userId) {
		return commonDataMapper.listUser(userGroupId, userId);
	}

	/**
	 * 공통코드 목록.
	 *
	 * @param code_type 코드구분
	 * @return List<HashMap<String,String>>
	 */
	public List<HashMap<String, String>> listCommonCode(String code_type) {
		return commonDataMapper.listCommonCode(code_type);
	}

	/**
	 * 공통코드 정보.
	 * 
	 * @param code_type 코드구분
	 * @param detailCode 상세코드
	 * @return HashMap<String,String>
	 */
	public HashMap<String, String> getCommonCode(String code_type, String detailCode) {
		return commonDataMapper.getCommonCode(code_type, detailCode);
	}
}
