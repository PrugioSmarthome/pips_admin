package com.daewooenc.pips.admin.web.service.externalsvcinfo;

import com.daewooenc.pips.admin.web.dao.externalsvcinfo.ExternalSvcInfoMapper;
import com.daewooenc.pips.admin.web.domain.dto.externalsvcinfo.ExternalServiceInfo;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 외부연계 관리 관련 Service
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-09      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-09
 **/
@Service
public class ExternalSvcInfoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExternalSvcInfoMapper externalSvcInfoMapper;

    /**
     * 시스템 관리자용 외부연계 관리 목록을 조회
     * @param externalServiceInfo
     * @return
     */
    public List<ExternalServiceInfo> getExternalServiceList(ExternalServiceInfo externalServiceInfo) {
        List<ExternalServiceInfo> externalSvcInfoServiceList = externalSvcInfoMapper.getExternalServiceList(externalServiceInfo);

        return externalSvcInfoServiceList;
    }

    /**
     * 시스템 관리자용 외부연계 관리 서비스명 중복체크 조회
     * @param externalServiceInfo
     * @return
     */
    public int getServiceListCount(ExternalServiceInfo externalServiceInfo) {
        int serviceListCount = externalSvcInfoMapper.getExternalServiceListCount(externalServiceInfo);

        return serviceListCount;
    }

    /**
     * 시스템 관리자용 외부연계 상세내용을 조회
     * @param externalServiceInfo
     * @return
     */
    public ExternalServiceInfo getExternalServiceDetail(ExternalServiceInfo externalServiceInfo) {
        ExternalServiceInfo externalServiceInfoDetail = externalSvcInfoMapper.getExternalServiceDetail(externalServiceInfo);

        return externalServiceInfoDetail;
    }

    /**
     * 시스템 관리자용 외부연계 정보를 등록
     * @param externalServiceInfo
     * @return
     */
    public boolean insertExternalServiceInfo(ExternalServiceInfo externalServiceInfo) {

        return externalSvcInfoMapper.insertExternalServiceInfo(externalServiceInfo) > 0;
    }

    /**
     * 시스템 관리자용 외부연계 정보를 수정
     * @param externalServiceInfo
     * @return
     */
    public boolean updateExternalServiceInfo(ExternalServiceInfo externalServiceInfo) {

        return externalSvcInfoMapper.updateExternalServiceInfo(externalServiceInfo) > 0;
    }

    /**
     * 시스템 관리자용 외부연계 정보를 삭제
     * @param externalServiceInfo
     * @return
     */
    public boolean deleteExternalServiceInfo(ExternalServiceInfo externalServiceInfo) {

        return externalSvcInfoMapper.deleteExternalServiceInfo(externalServiceInfo) > 0;
    }
}