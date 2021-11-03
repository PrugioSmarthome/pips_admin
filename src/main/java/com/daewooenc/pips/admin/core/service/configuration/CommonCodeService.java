package com.daewooenc.pips.admin.core.service.configuration;

import com.daewooenc.pips.admin.core.dao.configuration.CommonCodeMapper;
import com.daewooenc.pips.admin.core.domain.configuration.CommonCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 공통코드 Service.
 */
@Service
public class CommonCodeService {

	/** CommonCodeMapper Autowired. */
	@Autowired
	private CommonCodeMapper commonCodeMapper;

	/**
	 * 목록.
	 *
	 * @param groupCode 그룹코드
	 * @return List<CommonCode>
	 */
	public List<CommonCode> list(String groupCode) {
		return commonCodeMapper.list(groupCode);
	}

	/**
	 * 코드정보.
	 *
	 * @param groupCode 그룹코드
	 * @param detailCode 상세코드
	 * @return CommonCode
	 */
	public CommonCode getCommonCode(String groupCode, String detailCode) {
		return commonCodeMapper.getCommonCode(groupCode, detailCode);
	}

	/**
	 * 기 존재 여부.
	 *
	 * @param commonCode 코드정보
	 * @return boolean
	 */
	public boolean isExist(CommonCode commonCode) {
		return (commonCodeMapper.isExist(commonCode) > 0);
	}


	/**
	 * 등록.
	 *
	 * @param commonCode 코드정보
	 * @return boolean
	 */
	public boolean insert(CommonCode commonCode) {
		return (commonCodeMapper.insert(commonCode) > 0);
	}

	/**
	 * 수정.
	 *
	 * @param commonCode 코드정보
	 * @return boolean
	 */
	public boolean update(CommonCode commonCode) {
		return (commonCodeMapper.update(commonCode) > 0);
	}

	/**
	 * 삭제.
	 *
	 * @param commonCode 코드정보
	 * @return boolean
	 */
	public boolean delete(CommonCode commonCode) {
		return (commonCodeMapper.delete(commonCode) > 0);
	}

}