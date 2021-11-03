package com.daewooenc.pips.admin.core.service.configuration;

import com.daewooenc.pips.admin.core.dao.configuration.LoginHistoryMapper;
import com.daewooenc.pips.admin.core.domain.configuration.LoginHistory;
import com.daewooenc.pips.admin.core.domain.configuration.LoginHistoryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 로그인 이력 Service.
 */
@Service
public class LoginHistoryService {

	/** LoginHistoryMapper Autowired. */
	@Autowired
	private LoginHistoryMapper historyMapper;

	/**
	 * 목록.
	 *
	 * @param condition 조회조건
	 * @return List<LoginHistory>
	 */
	public List<LoginHistory> list(LoginHistoryCondition condition) {
		condition.setStartDate(condition.getStartDate().replace("-", ""));
		condition.setEndDate(condition.getEndDate().replace("-", ""));

		return historyMapper.list(condition);
	}

	/**
	 * 총 히스토리 수.
	 *
	 * @param condition 조회조건
	 * @return int
	 */
	public int count(LoginHistoryCondition condition) {
		condition.setStartDate(condition.getStartDate().replace("-", ""));
		condition.setEndDate(condition.getEndDate().replace("-", ""));

		return historyMapper.count(condition);
	}
}