package com.daewooenc.pips.admin.core.dao.configuration;

import com.daewooenc.pips.admin.core.domain.configuration.CommonCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 공통 코드 Mapper.
 *
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface CommonCodeMapper {

	/**
	 * 목록.
	 *
	 * @param groupCode 그룹코드
	 * @return List<CommonCode>
	 */
	List<CommonCode> list(@Param("groupCode") String groupCode);

	/**
	 * 상세내용.
	 *
	 * @param groupCode 그룹코드
	 * @param detailCode 상세코드
	 * @return CommonCode
	 */
	CommonCode getCommonCode(@Param("groupCode") String groupCode, @Param("detailCode") String detailCode);

	/**
	 * 기 존재 여부.
	 *
	 * @param commonCode 코드
	 * @return int
	 */
	int isExist(@Param("commonCode") CommonCode commonCode);

	/**
	 * 등록.
	 *
	 * @param commonCode 코드
	 * @return int
	 */
	int insert(@Param("commonCode") CommonCode commonCode);

	/**
	 * 수정.
	 *
	 * @param commonCode 코드
	 * @return int
	 */
	int update(@Param("commonCode") CommonCode commonCode);

	/**
	 * 삭제.
	 *
	 * @param commonCode 코드
	 * @return int
	 */
	int delete(@Param("commonCode") CommonCode commonCode);

	/**
	 * 출력 순서 수정.
	 *
	 * @param displayOrder 출력순서
	 * @param groupCode 그룹코드
	 * @param detailCode 상세코드
	 * @return int
	 */
	int updateDisplayOrder(
            @Param("displayOrder") Integer displayOrder,
            @Param("groupCode") String groupCode,
            @Param("detailCode") String detailCode);

	/**
	 * 엑셀 출력 목록.
	 *
	 * @param groupCode 그룹코드
	 * @return List<Map<String,String>>
	 */
	List<Map<String, String>> listExcel(@Param("groupCode") String groupCode);

}