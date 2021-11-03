package com.daewooenc.pips.admin.web.service.housingcplx;

import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.dao.housingcplx.HousingCplxMapper;
import com.daewooenc.pips.admin.web.domain.dto.cctv.CCTV;
import com.daewooenc.pips.admin.web.domain.dto.household.Household;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.*;
import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import com.daewooenc.pips.admin.web.domain.vo.homenet.HomenetServiceFunctionSendVo;
import com.daewooenc.pips.admin.web.util.HTTPClientUtil;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 단지관리 관련 Service
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-07-31      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-07-31
 **/
@Service
public class HousingCplxService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HousingCplxMapper housingCplxMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private XSSUtil xssUtil;

    private @Value("${pips.encrypt.key}") String pipsEncryptKey;

    private @Value("${pips.serviceServer.url}") String serviceServerURL;
    private @Value("${pips.serviceServer.auth}") String serviceServerAuth;
    private String serviceFuctionPath = "/houscplx/servicefunction";

    /**
     * 시스템 및 단지 관리자용 검색영역에서 단지선택 클릭 시 단지목록을 조회
     * @param housingCplx
     * @return
     */
    public List<HousingCplx> getHouscplxMetaList(HousingCplx housingCplx) {
        List<HousingCplx> housingCplxList = housingCplxMapper.selectHousingcplxMetaList(housingCplx);

        return housingCplxList;
    }

    /**
     * 멀티 단지 관리자용 검색영역에서 단지선택 클릭 시 단지목록을 조회
     * @param housingCplx
     * @return
     */
    public List<HousingCplx> getHouscplxMetaMultiList(HousingCplx housingCplx) {
        List<HousingCplx> housingCplxList = housingCplxMapper.selectMultiHousingcplxMetaList(housingCplx);

        return housingCplxList;
    }

    /**
     * 시스템 및 단지 관리자용 검색영역에서 단지선택 클릭 시 단지목록을 조회(수정)
     * @param housingCplx
     * @return
     */
    public List<HousingCplx> getHouscplxMetaListSelectModify(HousingCplx housingCplx) {
        List<HousingCplx> housingCplxList = housingCplxMapper.selectHousingcplxMetaListSelectModify(housingCplx);

        return housingCplxList;
    }

    /**
     * 시스템 관리자용 서비스 공지사항 단지선택 버큰 클릭시 단지목록을 조회(수정)
     * @param blltNo
     * @return
     */
    public List<HousingCplx> getHouscplxMetaListSelectModifyNoti(String blltNo) {
        List<HousingCplx> housingCplxList = housingCplxMapper.selectHousingcplxMetaListSelectModifyNoti(blltNo);

        return housingCplxList;
    }

    /**
     * 시스템 관리자용 단지 등록시 코드발급을 위한 등록된 단지 개수 조회
     * @return
     */
    public int getHousingCplxCnt() {
        int housingCplxCnt = housingCplxMapper.selectHousingCplxCnt();

        return housingCplxCnt;
    }

    /**
     * 시스템 괸리자: 단지에 등록된 회원 Count조회
     * @return
     */
    public int getHousingCplxUserCnt(HousingCplx housingCplx) {
        int housingCplxUserCnt = housingCplxMapper.selectHousingCplxUserCnt(housingCplx);

        return housingCplxUserCnt;
    }

    /**
     * 시스템 및 단지 관리자용 단지정보 상세 조회
     * @param housingCplx
     * @return
     */
    public HousingCplx getHousingCplxDetail(HousingCplx housingCplx) {
        HousingCplx housingCplxDetail = housingCplxMapper.selectHousingCplxDetail(housingCplx);

        return housingCplxDetail;
    }

    /**
     * 시스템 관리자용 등록된 전체 단지 목록 조회
     * @param housingCplx
     * @return
     */
    public List<HousingCplx> getHousingCplxList(HousingCplx housingCplx) {
        List<HousingCplx> housingCplxList = housingCplxMapper.selectAllHousingCplxList(housingCplx);

        return housingCplxList;
    }

    /**
     * 멀티 단지 관리자용 등록된 전체 단지 목록 조회
     * @param housingCplx
     * @return
     */
    public List<HousingCplx> getMultiHousingCplxList(HousingCplx housingCplx) {
        List<HousingCplx> housingCplxList = housingCplxMapper.selectMultiHousingCplxList(housingCplx);

        return housingCplxList;
    }

    /**
     * 시스템 및 단지 관리자용 단지 배치도 및 타입별 평면도 목록 조회
     * @param housingCplxPtype
     * @return
     */
    public List<HousingCplxPtype> getHousingCplxPtypeDetail(HousingCplxPtype housingCplxPtype) {
        List<HousingCplxPtype> housingCplxPtypeList = housingCplxMapper.selectHousingCplxPtypeList(housingCplxPtype);

        return housingCplxPtypeList;
    }

    /**
     * 시스템 및 단지 관리자용 우리 단지 알림 및 단지 배치도 및 타입별 평면도 이미지 개수 조회
     * @param housingCplxPtype
     * @return
     */
    public int getHousingCplxPtypeCnt(HousingCplxPtype housingCplxPtype) {
        int housingCplxPtypeCnt = housingCplxMapper.selectHousingCplxPtypeCnt(housingCplxPtype);

        return housingCplxPtypeCnt;
    }

    /**
     * 시스템 및 단지 관리자용 세대별 평형정보 조회
     * @param household
     * @return
     */
    public List<Household> getHouseholdList(Household household) {
        List<Household> householdDetail = housingCplxMapper.selectHouseholdList(household);

        return householdDetail;
    }

    /**
     * 시스템 및 단지 관리자용 세대별 평형정보 동 리스트 조회
     * @param household
     * @return
     */
    public List<Household> getHouseholdDongList(Household household) {
        List<Household> householdDongList = housingCplxMapper.selectHouseholdDongList(household);

        return householdDongList;
    }

    /**
     * 시스템 및 단지 관리자용 세대별 평형정보 조회
     * @param household
     * @return
     */
    public List<Household> getHouseholdDetail(Household household) {
        List<Household> householdDetail = housingCplxMapper.selectHouseholdDetail(household);

        return householdDetail;
    }

    /**
     * 시스템 및 단지 관리자용 세대별 입주민 수 조회
     * @param household
     * @return
     */
    public int getHouseholdCnt(Household household) {
        int householdCnt = housingCplxMapper.selectHouseholdCnt(household);

        return householdCnt;
    }

    /**
     * 시스템 및 단지 관리자용 관리실/경비실 연락처 정보 조회
     * @param housingCplxCaddr
     * @return
     */
    public List<HousingCplxCaddr> getHousingCplxCaddrDetail(HousingCplxCaddr housingCplxCaddr) {
        String houscplxCd = housingCplxCaddr.getHouscplxCd();
        pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

        List<HousingCplxCaddr> housingCplxCaddrDetail = housingCplxMapper.selectHousingCplxCaddrList(houscplxCd, pipsEncryptKey);

        return housingCplxCaddrDetail;
    }

    /**
     * 시스템 및 단지 관리자용 관리실/경비실 부가정보 조회
     * @param housingCplxCaddr
     * @return
     */
    public List<HousingCplxCaddrGdnc> getHousingCplxCaddrGdncDetail(HousingCplxCaddr housingCplxCaddr) {
        List<HousingCplxCaddrGdnc> housingCplxCaddrGdncDetail = housingCplxMapper.selectHousingCplxCaddrGdncList(housingCplxCaddr);

        return housingCplxCaddrGdncDetail;
    }

    /**
     * 시스템 및 단지 관리자용 CCTV 정보 조회
     * @param cctv
     * @return
     */
    public List<CCTV> getCCTVInfo(CCTV cctv) {
        List<CCTV> cctvDetail = housingCplxMapper.selectCCTVInfo(cctv);

        return cctvDetail;
    }

    /**
     * 시스템 및 단지 관리자용 기타 정보 상세 조회 (검침일 및 반경)
     * @param housingCplx
     * @return
     */
    public HousingCplx getEtcHousingCplx(HousingCplx housingCplx) {
        HousingCplx etcHousingCplxInfo = housingCplxMapper.selectEtcHousingCplx(housingCplx);

        return etcHousingCplxInfo;
    }

    /**
     * 시스템 및 단지 관리자용 기타 정보 상세 조회 (에너지 단위 및 그래프 최대값)
     * @param housingCplx
     * @return
     */
    public List<HousingCplxEnergyUnit> getEtcEnergyUnit(HousingCplx housingCplx) {
        List<HousingCplxEnergyUnit> etcEnergyUnitList = housingCplxMapper.selectEtcEnergyUnit(housingCplx);

        return etcEnergyUnitList;
    }

    /**
     * 시스템 관리자용 단지정보 등록 (개요 > 세대별평형 > 기타)
     * @param housingCplx
     * @param householdMap
     * @param serviceLinkMap
     * @param energyUnitMap
     */
    public void insertHousingCplxInfo(HousingCplx housingCplx, Map<String, Object> householdMap,
                                      Map<String, Object> serviceLinkMap, Map<String, Object> energyUnitMap) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        boolean bResult = false;
        try {
            bResult = housingCplxMapper.insertHousingCplx(housingCplx) > 0;
            bResult = housingCplxMapper.insertHousehold(householdMap) > 0;
            if (serviceLinkMap.size() > 0) {
                bResult = housingCplxMapper.insertHousingCplxServiceLink(serviceLinkMap) > 0;
            }
            bResult = housingCplxMapper.insertHousingCplxEnrgUnt(energyUnitMap) > 0;

        } catch (Exception e) {
            logger.debug("insertHousingCplxInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
        // TODO
        if (bResult) {
            // 편의 서비스 조회 요청
            //serviceFunctionControlRequest(housingCplx.getHouscplxCd(), housingCplx.getHmnetId());

            Map<String, Object> paramMap = new HashMap<String, Object>();

            String[] convenienceList = {"CAR", "CCTV", "CHARGING_POINT_STATE", "CNVC_FACL", "ELEVATOR", "FMLY_SCH", "HOUSCPLX_INFO",
                                        "PARCEL_BOX", "PARKING_LOCATION", "VISITOR", "VISIT_CAR", "WEATHER_INFO", "S_ELECT"};

            paramMap.put("convenienceList", convenienceList);
            paramMap.put("houscplxCd", housingCplx.getHouscplxCd());

            insertConvenience(paramMap);

            Map<String, Object> paramMap2 = new HashMap<String, Object>();

            String[] deviceList = {"AIRCON", "CURTAIN", "GASLOCK", "HEATING", "LIGHTS",
                                   "LIGHT_SWITCH", "SMART_CONSENT", "VENTILATOR"};

            paramMap2.put("deviceList", deviceList);
            paramMap2.put("houscplxCd", housingCplx.getHouscplxCd());

            insertDevice(paramMap2);

        }
    }

    public void serviceFunctionControlRequest(String houscplxCd, String hmnetId) {
        HousingCplx housingCplx = new HousingCplx();
        housingCplx.setDelYn("N");
        HomenetServiceFunctionSendVo homenetServiceFunctionSendVo =  new HomenetServiceFunctionSendVo("admin", hmnetId, houscplxCd, houscplxCd);
        String jsonData = JsonUtil.toJson(homenetServiceFunctionSendVo);
        // 홈넷 설정 제어 URL
        serviceServerURL = xssUtil.replaceAll(StringUtils.defaultString(serviceServerURL));
        serviceFuctionPath = xssUtil.replaceAll(StringUtils.defaultString(serviceFuctionPath));
        serviceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(serviceServerAuth));
        serviceServerURL="http://localhost:8888/v1/admin";
        String strSvcSvrURL = serviceServerURL + serviceFuctionPath;

        HTTPClientUtil httpClientUtil = new HTTPClientUtil();
        //  Service Server 제어 요청
        HttpResult httpResult = httpClientUtil.sendData(strSvcSvrURL, jsonData, serviceServerAuth);
        if ("201".equals(httpResult.getStatus())) {
            logger.debug("Service Function Data Request Success. ComplexCode : " + homenetServiceFunctionSendVo.getHouscplxCd()+" Homenet ID : "+homenetServiceFunctionSendVo.getHmnetId());
        } else {
            logger.debug("Service Function Data Request Fail. ComplexCode : " + homenetServiceFunctionSendVo.getHouscplxCd()+" Homenet ID : "+homenetServiceFunctionSendVo.getHmnetId());
        }
    }


    /**
     * 시스템 및 단지 관리자용 단지 경비실/관리실/생활불편신고 전화번호 등록
     * @param mgmtOfcMap
     * @param scrtOfcMap
     * @param lifeIcvncOfcMap
     * @param descriptionMap
     */
    public void insertHousingCplxCaddrInfo(Map<String, Object> mgmtOfcMap, Map<String, Object> scrtOfcMap,
                                           Map<String, Object> lifeIcvncOfcMap, Map<String, Object> descriptionMap) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            if (mgmtOfcMap.size() > 0) {
                housingCplxMapper.insertHousingCplxCaddr(mgmtOfcMap);
            }

            if (scrtOfcMap.size() > 0) {
                housingCplxMapper.insertHousingCplxCaddr(scrtOfcMap);
            }

            if (lifeIcvncOfcMap.size() > 0) {
                housingCplxMapper.insertHousingCplxCaddr(lifeIcvncOfcMap);
            }

            if (descriptionMap.size() > 0) {
                housingCplxMapper.insertHousingCplxCaddrGdnc(descriptionMap);
            }
        } catch (Exception e) {
            logger.debug("insertHousingCplxCaddrInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 시스템 및 단지 관리자용 단지개요 정보 수정
     * @param housingCplx
     * @return
     */
    public boolean updateHousingCplx(HousingCplx housingCplx) {
        return housingCplxMapper.updateHousingCplx(housingCplx) > 0;
    }

    /**
     * 시스템 관리자용 단지 활성화 or 비활성화
     * @param housingCplx
     * @return
     */
    public boolean updateHousingCplxDelyn(HousingCplx housingCplx) {
        return housingCplxMapper.updateHousingCplxDelyn(housingCplx) > 0;
    }

    /**
     * 시스템 및 단지 관리자용 단지배치도 or 타입별 평면도 등록
     * @param housingCplxPtypeMap
     * @return
     */
    public boolean insertHousingCplxImageInfo(Map<String, Object> housingCplxPtypeMap) {
        return housingCplxMapper.insertHousingCplxImageInfo(housingCplxPtypeMap) > 0;
    }

    /**
     * 시스템 및 단지 관지라용 단지배치도 or 타입별 평면도 수정
     * @param housingCplxPtypeMap
     * @return
     */
    public boolean updateHousingCplxImageInfo(Map<String, Object> housingCplxPtypeMap, String ptypeTpCd) {
        boolean result = false;

        if (ptypeTpCd.equals("PLOT_PLAN")) {
            result = housingCplxMapper.updateHousingCplxPlotImageInfo(housingCplxPtypeMap) > 0;
        } else if (ptypeTpCd.equals("FLOOR_PLAN")) {
            result = housingCplxMapper.updateHousingCplxFloorImageInfo(housingCplxPtypeMap) > 0;
        } else if (ptypeTpCd.equals("HOUSCPLX_INFO")) {
            result = housingCplxMapper.updateHousingCplxNoticeImageInfo(housingCplxPtypeMap) > 0;
        }

        return result;
    }

    /**
     * 시스템 및 단지 관리자용 타입별 평면도 부가정보 수정
     * @param housingCplxPtype
     * @return
     */
    public boolean updateHousingCplxImageDataInfo(HousingCplxPtype housingCplxPtype) {
        return housingCplxMapper.updateHousingCplxImageDataInfo(housingCplxPtype) > 0;
    }

    /**
     * 시스템 및 단지 관리자용 단지배치도 삭제처리할 이미지 정보 수정
     * @param removeFileMap
     */
    public boolean deleteHousingCplxImageInfo(Map<String, Object> removeFileMap) {
        return housingCplxMapper.updateHousingCplxImageStatus(removeFileMap) > 0;
    }

    /**
     * 시스템 및 단지 관리자용 단지배치도 이미지 순서 수정
     * @param orderFileMap
     * @return
     */
    public boolean updateHousingCplxImageOrder(Map<String, Object> orderFileMap) {
        return housingCplxMapper.updateHousingCplxImageOrder(orderFileMap) > 0;
    }

    /**
     * 시스템 관리자용 세대별 평형정보 수정
     * @param householdMap
     * @return
     */
    public boolean updateHousehold(Map<String, Object> householdMap) {
        return housingCplxMapper.updateHousehold(householdMap) > 0;
    }

    /**
     * 시스템 및 단지 관리자용 단지 경비실/관리실/생활불편신고 전화번호 수정
     * @param mgmtOfcMap
     * @param scrtOfcMap
     * @param lifeIcvncOfcMap
     * @param descriptionMap
     */
    public void updateHousingCplxCaddrInfo(Map<String, Object> mgmtOfcMap, Map<String, Object> scrtOfcMap,
                                           Map<String, Object> lifeIcvncOfcMap, Map<String, Object> descriptionMap) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            if (mgmtOfcMap.size() > 0) {
                housingCplxMapper.updateHousingCplxCaddr(mgmtOfcMap);
            }

            if (scrtOfcMap.size() > 0) {
                housingCplxMapper.updateHousingCplxCaddr(scrtOfcMap);
            }

            if (lifeIcvncOfcMap.size() > 0) {
                housingCplxMapper.updateHousingCplxCaddr(lifeIcvncOfcMap);
            }

            if (descriptionMap.size() > 0) {
                housingCplxMapper.updateHousingCplxCaddrGdnc(descriptionMap);
            }
        } catch (Exception e) {
            logger.debug("updateHousingCplxCaddrInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 시스템 및 단지 관리자용 단지 기타정보 수정
     * @param housingCplx
     * @param serviceLinkMap
     * @param energyUnitMap
     */
    public void updateHousingCplxEtcInfo(String groupName, HousingCplx housingCplx, Map<String, Object> serviceLinkMap,
                                         Map<String, Object> energyUnitMap, Map<String, Object> bannerMap) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            String houscplxCd = housingCplx.getHouscplxCd();

            housingCplxMapper.updateHousingCplxEtc(housingCplx);
            if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
                housingCplxMapper.deleteHousingCplxServiceLink(houscplxCd);
                housingCplxMapper.deleteHousingCplxBanner(houscplxCd);

                if (serviceLinkMap.size() > 0) {
                    housingCplxMapper.insertHousingCplxServiceLink(serviceLinkMap);
                }
                if (bannerMap.size() > 0) {
                    housingCplxMapper.insertHousingCplxBanner(bannerMap);
                }
            }
            housingCplxMapper.updateHousingCplxEnrgUnt(energyUnitMap);
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            logger.debug("updateHousingCplxEtcInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

    }

    /**
     * 시스템 관리자용 세대별 평형정보 삭제
     * @param household
     * @return
     */
    public boolean deleteHouseholdPtype(Household household) {
        return housingCplxMapper.deleteHouseholdPtype(household) > 0;
    }

    public boolean deleteAddress(HousingCplxCaddr housingCplxCaddr) {
        return housingCplxMapper.deleteAddress(housingCplxCaddr) > 0;
    }

    public boolean deleteAddressDescription(HousingCplxCaddr housingCplxCaddr) {
        return housingCplxMapper.deleteAddressDescription(housingCplxCaddr) > 0;
    }

    public boolean insertHousingCplxCaddr(Map<String, Object> addressMap) {
        return housingCplxMapper.insertHousingCplxCaddr(addressMap) > 0;
    }

    public boolean insertHousingCplxCaddrGdnc(Map<String, Object> descriptionMap) {
        return housingCplxMapper.insertHousingCplxCaddrGdnc(descriptionMap) > 0;
    }

    public boolean insertCCTVInfo(CCTV cctv) {
        return housingCplxMapper.insertCCTVInfo(cctv) > 0;
    }

    public boolean updateHousingCplxCaddr(Map<String, Object> addressMap) {
        return housingCplxMapper.updateHousingCplxCaddr(addressMap) > 0;
    }

    public boolean updateHousingCplxCaddrGdnc(Map<String, Object> descriptionMap) {
        return housingCplxMapper.updateHousingCplxCaddrGdnc(descriptionMap) > 0;
    }

    public boolean insertConvenience(Map<String, Object> paramMap){
        return housingCplxMapper.insertConvenience(paramMap) > 0;
    }

    public boolean insertDevice(Map<String, Object> paramMap){
        return housingCplxMapper.insertDevice(paramMap) > 0;
    }

    public boolean updateCCTV(Map<String, Object> cctvMap) {
        return housingCplxMapper.updateCCTV(cctvMap) > 0;
    }
    public boolean deleteCCTV(String houscplxCd) {
        return housingCplxMapper.deleteCCTV(houscplxCd) > 0;
    }

    public String selectHouscplxNm(String houscplxCd){
        String houscplxNm = housingCplxMapper.selectHouscplxNm(houscplxCd);
        return houscplxNm;
    }

    public List<HousingCplx> selectFailureOccurrence(){
        List<HousingCplx> houscplxList = housingCplxMapper.selectFailureOccurrence();
        return houscplxList;
    }
}