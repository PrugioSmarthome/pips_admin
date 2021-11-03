package com.daewooenc.pips.admin.web.dao.externalsvcinfo;

import com.daewooenc.pips.admin.web.domain.dto.externalsvcinfo.ExternalServiceInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 외부연계 관리 관련 ExternalSvcInfoMapper.xml 맵핑 Interface
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-21      :       yckim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-21
 **/
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface ExternalSvcInfoMapper {
	/**
	 * 서비스 연동 정보
	 */
	ExternalServiceInfo getExternalServiceInfo(@Param("serviceTpGrCd") String serviceTpGrCd, @Param("serviceTpCd") String serviceTpCd);

	/**
	 * 시스템 관리자: 외부연계 관리 목록조회
	 * @param externalServiceInfo
	 * @return
	 */
	List<ExternalServiceInfo> getExternalServiceList(ExternalServiceInfo externalServiceInfo);

	/**
	 * 시스템 관리자: 외부연계 관리 서비스명 중복체크 조회
	 * @param externalServiceInfo
	 * @return
	 */
	int getExternalServiceListCount(ExternalServiceInfo externalServiceInfo);

	/**
	 * 시스템 관리자: 외부연계 관리 상세조회
	 * @param externalServiceInfo
	 * @return
	 */
	ExternalServiceInfo getExternalServiceDetail(ExternalServiceInfo externalServiceInfo);

	/**
	 * 시스템 관리자: 외부연계 관리 정보 등록
	 * @param externalServiceInfo
	 * @return
	 */
	int insertExternalServiceInfo(ExternalServiceInfo externalServiceInfo);

	/**
	 * 시스템 관리자: 외부연계 관리 정보 수정
	 * @param externalServiceInfo
	 * @return
	 */
	int updateExternalServiceInfo(ExternalServiceInfo externalServiceInfo);

	/**
	 * 시스템 관리자: 외부연계 관리 정보 삭제상태로 변경
	 * @param externalServiceInfo
	 * @return
	 */
	int deleteExternalServiceInfo(ExternalServiceInfo externalServiceInfo);
}
