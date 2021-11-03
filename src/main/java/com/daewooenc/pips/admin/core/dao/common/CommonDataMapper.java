package com.daewooenc.pips.admin.core.dao.common;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 공통데이터 mapper.
 *
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface CommonDataMapper {

	/**
	 * 사용자 그룹 목록.
	 *
	 * @param userGroupLevel 로그인사용자그룹레벨
	 * @return List<HashMap<String,String>>
	 */
	List<HashMap<String, String>> listUserGroup(@Param("userGroupLevel") String userGroupLevel);

	/**
	 * 사용자 목록.
	 *
	 * @param userGroupId 사용자그룹ID
	 * @param userGroupLevel 사용자그룹레벨
	 * @return List<HashMap<String,String>>
	 */
	List<HashMap<String, String>> listUser(
            @Param("userGroupId") String userGroupId,
            @Param("userGroupLevel") String userGroupLevel);

	/**
	 * 메뉴 목록.
	 *
	 * @return List<HashMap<String,String>>
	 */
	List<HashMap<String, String>> listMenu();

	/**
	 * 코드 목록.
	 *
	 * @param code_type 코드구분
	 * @return List<HashMap<String,String>>
	 */
	List<HashMap<String, String>> listCommonCode(@Param(value = "code_type") String code_type);

	/**
	 * 코드.
	 * 
	 * @param code_type 코드구분
	 * @param detailCode 상세코드
	 * @return HashMap<String,String>
	 */
	HashMap<String, String> getCommonCode(@Param(value = "code_type") String code_type, @Param(value = "detailCode") String detailCode);
}
