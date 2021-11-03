package com.daewooenc.pips.admin.web.dao.manufacturerByDevice;

import com.daewooenc.pips.admin.web.domain.dto.manufacturerByDevice.ManufacturerByDevice;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 대우건설 스마트홈 푸르지오 장치별 제조사 관련 ManufacturerByDeviceMapper.xml 맵핑 Interface
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2021-03-23    :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2021-03-23
 * **/
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface ManufacturerByDeviceMapper {
    /**
     * 시스템 관리자용 장치별 제조사 관리 목록 조회
     * @return
     */
    List<ManufacturerByDevice> selectManufacturerByDeviceList(ManufacturerByDevice manufacturerByDevice);
    /**
     * 시스템 관리자용 장치별 제조사 관리 목록 삭제
     * @return
     */
    int deleteManufacturerByDevice(ManufacturerByDevice manufacturerByDevice);
    /**
     * 시스템 관리자용 장치별 제조사 관리 장치제조사 목록 조회
     * @return
     */
    List<ManufacturerByDevice> selectDeviceMfList(ManufacturerByDevice manufacturerByDevice);
    /**
     * 시스템 관리자용 장치별 제조사 등록
     * @return
     */
    int insertManufacturerByDevice(ManufacturerByDevice manufacturerByDevice);
    /**
     * 시스템 관리자용 장치별 제조사 등록 중복 체크
     * @return
     */
    int checkDeviceMf(ManufacturerByDevice manufacturerByDevice);
    /**
     * 시스템 관리자용 장치별 제조사 수정 조회
     * @return
     */
    ManufacturerByDevice selectManufacturerByDeviceEdit(ManufacturerByDevice manufacturerByDevice);
    /**
     * 시스템 관리자용 장치별 제조사 수정
     * @return
     */
    int updateManufacturerByDevice(ManufacturerByDevice manufacturerByDevice);
}
