package com.daewooenc.pips.admin.web.service.maintenanceFee;

import com.daewooenc.pips.admin.web.dao.maintenanceFee.MaintenanceFeeMapper;
import com.daewooenc.pips.admin.web.domain.dto.maintenanceFee.MaintenanceFee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-12-10      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-12-10
 **/
@Service
public class MaintenanceFeeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaintenanceFeeMapper maintenanceFeemapper;

    /**
     * 시스템관리자, 단지관리자: 단지 관리비 목록 조회
     * @return
     */
    public List<MaintenanceFee> getMaintenanceFeeList(MaintenanceFee maintenanceFee) {
        List<MaintenanceFee> maintenanceFeeList = maintenanceFeemapper.selectMaintenanceFeeList(maintenanceFee);

        return maintenanceFeeList;
    }

    /**
     * 멀티단지관리자: 단지 관리비 목록 조회
     * @return
     */
    public List<MaintenanceFee> getMaintenanceFeeListMulti(MaintenanceFee maintenanceFee) {
        List<MaintenanceFee> maintenanceFeeList = maintenanceFeemapper.selectMaintenanceFeeListMulti(maintenanceFee);

        return maintenanceFeeList;
    }

    /**
     * 단지 관리비 목록 상세
     * @return
     */
    public MaintenanceFee getMaintenanceFeeDetail(MaintenanceFee maintenanceFee) {
        MaintenanceFee maintenanceFeeDetail = maintenanceFeemapper.selectMaintenanceFeeDetail(maintenanceFee);

        return maintenanceFeeDetail;
    }

    /**
     * 단지 관리비 등록시 기존 관리비 삭제
     * @return
     */
    public boolean deleteMaintenanceFee(Map<String, Object> paramMap) {
        return maintenanceFeemapper.deleteMaintenanceFee(paramMap) > 0;
    }

    /**
     * 단지 관리비 관리비 등록
     * @return
     */
    public boolean insertMaintenanceFee(Map<String, Object> paramMap) {
        return maintenanceFeemapper.insertMaintenanceFee(paramMap) > 0;
    }

}