package com.daewooenc.pips.admin.web.service.system.device;

import com.daewooenc.pips.admin.web.dao.convenience.ConvenienceMapper;
import com.daewooenc.pips.admin.web.dao.device.DeviceMapper;
import com.daewooenc.pips.admin.web.dao.platform.PlatformMapper;
import com.daewooenc.pips.admin.web.domain.dto.convenience.Convenience;
import com.daewooenc.pips.admin.web.domain.dto.device.SystemDevice;
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
 *       2020-11-23      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-11-23
 **/
@Service
public class DeviceService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeviceMapper DeviceMapper;

    /**
     * 시스템 관리자용 장치 설정 목록 조회 (단지별)
     * @return
     */
    public List<SystemDevice> getSystemDeviceList(SystemDevice systemDevice) {
        List<SystemDevice> deviceList = DeviceMapper.selectSystemDeviceList(systemDevice);

        return deviceList;
    }

    /**
     * 시스템 관리자용 장치 설정 수정 조화 (단지별)
     * @return
     */
    public SystemDevice getSystemDeviceEdit(SystemDevice systemDevice) {
        SystemDevice systemDeviceEdit = DeviceMapper.selectSystemDeviceEdit(systemDevice);

        return systemDeviceEdit;
    }

    /**
     * 시스템 관리자용 장치 설정 수정 (단지별)
     * @return
     */
    public boolean updateDevice(SystemDevice systemDevice) {
        return DeviceMapper.updateDevice(systemDevice) > 0;
    }

    /**
     * 시스템 관리자용 장치 설정 수정 (단지별(세대별 일괄 수정))
     * @return
     */
    public boolean updateDeviceHshold(SystemDevice systemDevice) {
        return DeviceMapper.updateDeviceHshold(systemDevice) > 0;
    }

    /**
     * 시스템 관리자용 장치 설정 수정 (세대별)
     * @return
     */
    public boolean updateHsholdDevice(SystemDevice systemDevice) {
        return DeviceMapper.updateHsholdDevice(systemDevice) > 0;
    }

    /**
     * 시스템 관리자용 장치 설정 상세 수정 (세대별)
     * @return
     */
    public boolean updateHsholdDeviceView(SystemDevice systemDevice) {
        return DeviceMapper.updateHsholdDeviceView(systemDevice) > 0;
    }

    /**
     * 시스템 관리자용 장치 설정 목록 조회 (세대별)
     * @return
     */
    public List<SystemDevice> getSystemDeviceHsholdList(SystemDevice systemDevice) {
        List<SystemDevice> deviceList = DeviceMapper.selectSystemDeviceHsholdList(systemDevice);

        return deviceList;
    }

    /**
     * 시스템 관리자용 장치 설정 상세 (세대별)
     * @return
     */
    public List<SystemDevice> getDeviceHsholdDetail(SystemDevice systemDevice) {
        List<SystemDevice> deviceList = DeviceMapper.selectDeviceHsholdDetail(systemDevice);

        return deviceList;
    }

    /**
     * 시스템 관리자용 장치 설정 상세 (세대별)
     * @return
     */
    public String getDeviceHsholdDetailNm(String houscplxCd) {
        String houscplxNm = DeviceMapper.selectDeviceHsholdDetailNm(houscplxCd);

        return houscplxNm;
    }


}