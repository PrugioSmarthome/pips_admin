package com.daewooenc.pips.admin.web.dao.device;

import com.daewooenc.pips.admin.web.domain.dto.convenience.Convenience;
import com.daewooenc.pips.admin.web.domain.dto.device.SystemDevice;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 대우건설 스마트홈 푸르지오 플랫폼용 배너 관련 DeviceMapper.xml 맵핑 Interface
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-11-23    :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-11-23
 * **/
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface DeviceMapper {
    /**
     * 시스템 관리자용 장치 설정 목록 조회 (단지별)
     * @return
     */
    List<SystemDevice> selectSystemDeviceList(SystemDevice systemDevice);

    /**
     * 시스템 관리자용 장치 설정 수정 조회 (단지별)
     * @return
     */
    SystemDevice selectSystemDeviceEdit(SystemDevice systemDevice);

    /**
     * 시스템 관리자용 장치 설정 수정 (단지별)
     * @return
     */
    int updateDevice(SystemDevice systemDevice);

    /**
     * 시스템 관리자용 장치 설정 수정 (단지별(세대별 일괄 수정))
     * @return
     */
    int updateDeviceHshold(SystemDevice systemDevice);

    /**
     * 시스템 관리자용 장치 설정 수정 (세대별)
     * @return
     */
    int updateHsholdDevice(SystemDevice systemDevice);

    /**
     * 시스템 관리자용 장치 설정 상세 수정 (세대별)
     * @return
     */
    int updateHsholdDeviceView(SystemDevice systemDevice);

    /**
     * 시스템 관리자용 장치 설정 목록 조회 (세대별)
     * @return
     */
    List<SystemDevice> selectSystemDeviceHsholdList(SystemDevice systemDevice);

    /**
     * 시스템 관리자용 장치 설정 상세 (세대별)
     * @return
     */
    List<SystemDevice> selectDeviceHsholdDetail(SystemDevice systemDevice);

    /**
     * 시스템 관리자용 장치 설정 상세 단지이름 조회(세대별)
     * @return
     */
    String selectDeviceHsholdDetailNm(String houscplxCd);
}
