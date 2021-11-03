package com.daewooenc.pips.admin.core.dao.configuration;

import com.daewooenc.pips.admin.core.domain.configuration.LoginHistory;
import com.daewooenc.pips.admin.core.domain.configuration.LoginHistoryCondition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 로그인 이력 mapper.
 *
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface LoginHistoryMapper {

	/**
	 * 목록.
	 *
	 * @param condition 조회 조건
	 * @return List<LoginHistory>
	 */
	List<LoginHistory> list(LoginHistoryCondition condition);

	/**
	 * 총 목록 수.
	 *
	 * @param condition 조회조건
	 * @return int
	 */
	int count(LoginHistoryCondition condition);

	/**
	 * 등록.
	 *
	 * @param loginHistory 로그인이력정보
	 * @return int
	 */
   	int insert(LoginHistory loginHistory);


   	/**
   	 * 엑셀 목록.
   	 *
   	 * @param condition 조회 조건
   	 * @return List<Map<String,String>>
   	 */
	List<Map<String, String>> listExcel(LoginHistoryCondition condition);

}