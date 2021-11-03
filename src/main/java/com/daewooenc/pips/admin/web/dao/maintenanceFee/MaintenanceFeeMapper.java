package com.daewooenc.pips.admin.web.dao.maintenanceFee;

import com.daewooenc.pips.admin.web.domain.dto.maintenanceFee.MaintenanceFee;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 대우건설 스마트홈 푸르지오 플랫폼용 단지 관리비 관련 MaintenanceFeeMapper.xml 맵핑 Interface
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-12-10    :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-12-10
 **/
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface MaintenanceFeeMapper {
    /**
     * 시스템관리자, 단지관리자: 단지 관리비 목록 조회
     * @return
     */
    List<MaintenanceFee> selectMaintenanceFeeList(MaintenanceFee maintenanceFee);
    /**
     * 멀티단지관리자: 단지 관리비 목록 조회
     * @return
     */
    List<MaintenanceFee> selectMaintenanceFeeListMulti(MaintenanceFee maintenanceFee);
    /**
     * 단지 관리비 목록 상세
     * @return
     */
    MaintenanceFee selectMaintenanceFeeDetail(MaintenanceFee maintenanceFee);
    /**
     * 단지 관리비 등록시 기존 관리비 삭제
     * @return
     */
    int deleteMaintenanceFee(Map<String, Object> paramMap);

    /**
     * 단지 관리비 관리비 등록
     * @return
     */
    int insertMaintenanceFee(Map<String, Object> paramMap);
}
