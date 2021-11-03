package com.daewooenc.pips.admin.web.dao.housingcplx;

import com.daewooenc.pips.admin.web.domain.dto.cctv.CCTV;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizco;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizcoCaddr;
import com.daewooenc.pips.admin.web.domain.dto.household.Household;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.*;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLink;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLinkDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 단지관리 관련 HousingCplxMapper.xml 맵핑 Interface
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-28      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-28
 **/
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface HousingCplxMapper {
    /**
     * 공통: 단지선택 클릭시 단지목록 조회 (모달영역)
     * @param housingCplx
     * @return
     */
    List<HousingCplx> selectHousingcplxMetaList(HousingCplx housingCplx);

    /**
     * 공통: 단지선택 클릭시 단지목록 조회 (모달영역, 멀티 단지 관리자)
     * @param housingCplx
     * @return
     */
    List<HousingCplx> selectMultiHousingcplxMetaList(HousingCplx housingCplx);

    /**
     * 공통: 단지선택 클릭시 단지목록 조회 (수정,모달영역)
     * @param housingCplx
     * @return
     */
    List<HousingCplx> selectHousingcplxMetaListSelectModify(HousingCplx housingCplx);

    /**
     * 시스템관리자: 서비스공지사항 단지선택 클릭시 단지목록 조회 (수정,모달영역)
     * @param blltNo
     * @return
     */
    List<HousingCplx> selectHousingcplxMetaListSelectModifyNoti(String blltNo);

    /**
     * 시스템 괸리자: 등록된 단지 개수 조회
     * @return
     */
    int selectHousingCplxCnt();


    /**
     * 시스템 괸리자: 단지에 등록된 회원 Count조회
     * @return
     */
    int selectHousingCplxUserCnt(HousingCplx housingCplx);


    /**
     * 시스템 관리자: 등록된 단지목록 조회
     * @param housingCplx
     * @return
     */
    List<HousingCplx> selectAllHousingCplxList(HousingCplx housingCplx);

    /**
     * 멀티 단지 관리자: 등록된 단지목록 조회
     * @param housingCplx
     * @return
     */
    List<HousingCplx> selectMultiHousingCplxList(HousingCplx housingCplx);

    /**
     * 공통: 단지 정보 상세 조회
     * @param housingCplx
     * @return
     */
    HousingCplx selectHousingCplxDetail(HousingCplx housingCplx);

    /**
     * 공통: 단지 배치도 or 타입별 평면도 이미지 슬라이드, 수정, 목록 조회
     * @param housingCplxPtype
     * @return
     */
    List<HousingCplxPtype> selectHousingCplxPtypeList(HousingCplxPtype housingCplxPtype);

    /**
     * 공통: 단지 배치도 or 타입별 평면도 이미지 개수 조회
     * @param housingCplxPtype
     * @return
     */
    int selectHousingCplxPtypeCnt(HousingCplxPtype housingCplxPtype);

    /**
     * 공통: 세대별 평형정보 목록 조회
     * @param household
     * @return
     */
    List<Household> selectHouseholdList(Household household);

    /**
     * 공통: 세대별 평형정보 상세 동 리스트 조회
     * @param household
     * @return
     */
    List<Household> selectHouseholdDongList(Household household);

    /**
     * 공통: 세대별 평형정보 상세 조회
     * @param household
     * @return
     */
    List<Household> selectHouseholdDetail(Household household);

    /**
     * 공통: 세대별 입주민 수 조회
     * @param household
     * @return
     */
    int selectHouseholdCnt(Household household);

    /**
     * 공통: 단지 관리실/경비실 연락처 정보 조회
     * @param houscplxCd
     * @param encKey
     * @return
     */
    List<HousingCplxCaddr> selectHousingCplxCaddrList(@Param("houscplxCd") String houscplxCd, @Param("encKey") String encKey);

    /**
     * 공통: 단지 관리실/경비실 부가 정보 조회
     * @param housingCplxCaddr
     * @return
     */
    List<HousingCplxCaddrGdnc> selectHousingCplxCaddrGdncList(HousingCplxCaddr housingCplxCaddr);

    /**
     * 공통: 단지 CCTV 목록 조회
     * @param cctv
     * @return
     */
    List<CCTV> selectCCTVInfo(CCTV cctv);

    /**
     * 공통: 단지 기타 정보 (검침일 및 단지반경)
     * @param housingCplx
     * @return
     */
    HousingCplx selectEtcHousingCplx(HousingCplx housingCplx);

    /**
     * 공통: 단지 기타 정보 (에너지 단위 및 그래프 최대값)
     * @param housingCplx
     * @return
     */
    List<HousingCplxEnergyUnit> selectEtcEnergyUnit(HousingCplx housingCplx);

    /**
     * 시스템 관리자: 단지 정보 등록
     * @param housingCplx
     * @return
     */
    int insertHousingCplx(HousingCplx housingCplx);

    /**
     * 시스템 관리자: 단지 세대별 평형 정보 등록
     * @param householdMap
     * @return
     */
    int insertHousehold(Map<String, Object> householdMap);

    /**
     * 시스템 관리자: 단지 연계 웹/앱 정보 등록
     * @param appInfoMap
     * @return
     */
    int insertHousingCplxServiceLink(Map<String, Object> appInfoMap);

    /**
     * 시스템 관리자: 단지 배너 정보 등록
     * @param bannerMap
     * @return
     */
    int insertHousingCplxBanner(Map<String, Object> bannerMap);

    /**
     * 시스템 관리자: 단지 에너지 관련 정보 등록
     * @param energyUnitMap
     * @return
     */
    int insertHousingCplxEnrgUnt(Map<String, Object> energyUnitMap);

    /**
     * 시스템 및 단지 관리자용 단지배치도 or 평면도 등록
     * @param housingCplxPtypeMap
     * @return
     */
    int insertHousingCplxImageInfo(Map<String, Object> housingCplxPtypeMap);

    /**
     * 시스템 및 단지 관리자용 경비실/관리실 연락처 정보 등록
     * @param addressMap
     * @return
     */
    int insertHousingCplxCaddr(Map<String, Object> addressMap);

    /**
     * 시스템 및 단지 관리자용 경비실/관리실 연락처 부가정보 등록
     * @param descriptionMap
     * @return
     */
    int insertHousingCplxCaddrGdnc(Map<String, Object> descriptionMap);

    /**
     * 시스템 및 단지 관리자용 단지개요 정보 수정
     * @param housingCplx
     * @return
     */
    int updateHousingCplx(HousingCplx housingCplx);

    /**
     * 시스템 및 단지 관리자용 우리 단지 알림 수정
     * @param housingCplxPtypeMap
     * @returnc
     */
    int updateHousingCplxNoticeImageInfo(Map<String, Object> housingCplxPtypeMap);

    /**
     * 시스템 및 단지 관리자용 단지배치도 수정
     * @param housingCplxPtypeMap
     * @returnc
     */
    int updateHousingCplxPlotImageInfo(Map<String, Object> housingCplxPtypeMap);

    /**
     * 시스템 및 단지 관리자용 타입별 평면도 수정
     * @param housingCplxPtypeMap
     * @return
     */
    int updateHousingCplxFloorImageInfo(Map<String, Object> housingCplxPtypeMap);

    /**
     * 시스템 및 단지 관리자용 타입별 평면도 부가정보 수정
     * @param housingCplxPtype
     * @return
     */
    int updateHousingCplxImageDataInfo(HousingCplxPtype housingCplxPtype);

    /**
     * 시스템 및 단지 관리자용 단지 배치도 or 타입별 평면도 이미지 순서 수정
     * @param orderFileMap
     * @return
     */
    int updateHousingCplxImageOrder(Map<String, Object> orderFileMap);

    /**
     * 시스템 및 단지 관리자용 단지 배치도 or 타입별 평면도 삭제할 이미지 정보 상태 변경
     * @param removeFileMap
     * @return
     */
    int updateHousingCplxImageStatus(Map<String, Object> removeFileMap);

    /**
     * 시스템 관리자용 세대별 평형정보 수정
     * @param householdMap
     * @return
     */
    int updateHousehold(Map<String, Object> householdMap);

    /**
     * 시스템 및 단지 관리자용 경비실/관리실 기본정보 수정
     * @param addressMap
     * @return
     */
    int updateHousingCplxCaddr(Map<String, Object> addressMap);

    /**
     * 시스템 및 단지 관리자용 경비실/관리실 부가정보 수정
     * @param descriptionMap
     * @return
     */
    int updateHousingCplxCaddrGdnc(Map<String, Object> descriptionMap);

    /**
     * 시스템 및 단지 관리자용 기타(에너지 검침일, 반경) 수정
     * @param housingCplx
     * @return
     */
    int updateHousingCplxEtc(HousingCplx housingCplx);

    /**
     * 시스템 및 단지 관리자용 에너지 단위 수정
     * @param energyUnitMap
     * @return
     */
    int updateHousingCplxEnrgUnt(Map<String, Object> energyUnitMap);

    /**
     * 시스템 관리자용 세대별 평형정보 삭제
     * @param household
     * @return
     */
    int deleteHouseholdPtype(Household household);

    /**
     * 시스템 및 단지 관리자용 관리실/경비실 연락처 삭제
     * @param housingCplxCaddr
     * @return
     */
    int deleteAddress(HousingCplxCaddr housingCplxCaddr);

    /**
     * 시스템 및 단지 관리자용 관리실/경비실 설명정보 삭제
     * @param housingCplxCaddr
     * @return
     */
    int deleteAddressDescription(HousingCplxCaddr housingCplxCaddr);

    /**
     * 시스템 관리자용 연계 웹/앱 정보 삭제
     * @param houscplxCd
     * @return
     */
    int deleteHousingCplxServiceLink(@Param("houscplxCd") String houscplxCd);

    /**
     * 시스템 관리자용 배너 정보 삭제
     * @param houscplxCd
     * @return
     */
    int deleteHousingCplxBanner(@Param("houscplxCd") String houscplxCd);

    /**
     * 단지 등록시 편의시설 정보 자동 등록
     * @param paramMap
     * @return
     */
    int insertConvenience(Map<String, Object> paramMap);

    /**
     * 단지 등록시 장치 정보 자동 등록
     * @param paramMap
     * @return
     */
    int insertDevice(Map<String, Object> paramMap);

    int insertCCTVInfo(CCTV cctv);

    int updateHousingCplxDelyn(HousingCplx housingCplx);

    int updateCCTV(Map<String, Object> cctvMap);

    int deleteCCTV(String houscplxCd);

    String selectHouscplxNm(String houscplxCd);

    List<HousingCplx> selectFailureOccurrence();

    List<HousingCplx> selectFailureOccurrenceNotOrderBy();
}
