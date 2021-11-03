package com.daewooenc.pips.admin.web.service.system.manufacturerByDevice;

import com.daewooenc.pips.admin.web.dao.manufacturerByDevice.ManufacturerByDeviceMapper;
import com.daewooenc.pips.admin.web.domain.dto.manufacturerByDevice.ManufacturerByDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2021-03-23      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2021-03-23
 **/
@Service
public class ManufacturerByDeviceService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ManufacturerByDeviceMapper ManufacturerByDeviceMapper;

    /**
     * 시스템 관리자용 장치별 제조사 목록 조회
     * @return
     */
    public List<ManufacturerByDevice> getManufacturerByDeviceList(ManufacturerByDevice manufacturerByDevice) {
        List<ManufacturerByDevice> manufacturerByDeviceList = ManufacturerByDeviceMapper.selectManufacturerByDeviceList(manufacturerByDevice);

        return manufacturerByDeviceList;
    }

    /**
     * 시스템 관리자용 장치별 제조사 목록 삭제
     * @return
     */
    public boolean deleteManufacturerByDevice(ManufacturerByDevice manufacturerByDevice) {
        return ManufacturerByDeviceMapper.deleteManufacturerByDevice(manufacturerByDevice) > 0;
    }

    /**
     * 시스템 관리자용 장치별 제조사 등록 장치제조사 목록 조회
     * @return
     */
    public List<ManufacturerByDevice> getDeviceMfList(ManufacturerByDevice manufacturerByDevice) {
        List<ManufacturerByDevice> deviceMfList = ManufacturerByDeviceMapper.selectDeviceMfList(manufacturerByDevice);

        return deviceMfList;
    }

    /**
     * 시스템 관리자용 장치별 제조사 등록
     * @return
     */
    public boolean insertManufacturerByDevice(ManufacturerByDevice manufacturerByDevice) {
        return ManufacturerByDeviceMapper.insertManufacturerByDevice(manufacturerByDevice) > 0;
    }

    /**
     * 시스템 관리자용 장치별 제조사 등록 중복 체크
     * @return
     */
    public boolean checkDeviceMf(ManufacturerByDevice manufacturerByDevice) {
        return ManufacturerByDeviceMapper.checkDeviceMf(manufacturerByDevice) > 0;
    }

    /**
     * 시스템 관리자용 장치별 제조사 수정 조회
     * @return
     */
    public ManufacturerByDevice getManufacturerByDeviceEdit(ManufacturerByDevice manufacturerByDevice){
        ManufacturerByDevice manufacturerByDeviceEdit = ManufacturerByDeviceMapper.selectManufacturerByDeviceEdit(manufacturerByDevice);

        return manufacturerByDeviceEdit;
    }

    /**
     * 시스템 관리자용 장치별 제조사 수정
     * @return
     */
    public boolean updateManufacturerByDevice(ManufacturerByDevice manufacturerByDevice) {
        return ManufacturerByDeviceMapper.updateManufacturerByDevice(manufacturerByDevice) > 0;
    }

}