package com.daewooenc.pips.admin.web.dao.facility;

import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizco;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizcoCaddr;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizcoCaddrRelation;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizcodHousingCplxRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 시설업체 관리 관련 FacilityMapper.xml 맵핑 Interface
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-18      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-18
 **/
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface FacilityMapper {
    /**
     * 시설업체 업체 id 목록 조회
     * @param facilityBizco
     * @return
     */
    List<FacilityBizco> selectFacilityBizcoIdList(FacilityBizco facilityBizco);

    /**
     * 시설업체 업체 연락처 id 목록 조회
     * @param facilityBizco
     * @return
     */
    List<FacilityBizcoCaddr> selectFacilityBizcoCaddrIdList(FacilityBizco facilityBizco);

    /**
     * 시설업체 엑셀다운로드 목록 조회
     * @param facilityBizco
     * @return
     */
    List<FacilityBizco> selectFacilityInfoExcelList(FacilityBizco facilityBizco);

    /**
     * 시설업체 기본정보 목록 조회
     * @param facilityBizco
     * @return
     */
    List<FacilityBizco> selectFacilityInfoList(FacilityBizco facilityBizco);

    /**
     * 시설업체 해당 연락처 목록 조회
     * @param facilityBizco
     * @return
     */
    List<FacilityBizcoCaddr> selectFacilityInfoCaddrList(FacilityBizco facilityBizco);

    /**
     * 시설업체 해당 연락처 관계 ID 조회
     * @param facilityBizco
     * @return
     */
    List<FacilityBizcoCaddr> selectFacilityInfoCaddrIdList(FacilityBizco facilityBizco);

    /**
     * 시스템 및 단지 관리자용 시설업체 기본정보 등록
     * @param facilityBizco
     * @return
     */
    int insertFacilityInfo(FacilityBizco facilityBizco);

    /**
     * 시스템 및 단지 관리자용 시설업체 연락처 정보 등록
     * @param facilityBizcoCaddr
     * @return
     */
    int insertFacilityCaddrInfo(FacilityBizcoCaddr facilityBizcoCaddr);

    /**
     * 시설업체와 시설업체 연락처 맵핑 정보 등록
     * @param relationMap
     * @return
     */
    int insertFacilityCaddrInfoRelation(Map<String, Object> relationMap);

    /**
     * 시설업체와 시설업체 연락처 맵핑 단일 정보 등록
     * @param facilityBizcoCaddrRelation
     * @return
     */
    int insertFacilityCaddrInfoRelationOne(FacilityBizcoCaddrRelation facilityBizcoCaddrRelation);

    /**
     * 시설업체와 단지 맵핑 정보 등록
     * @param facilityBizcodHousingCplxRelation
     * @return
     */
    int insertFacilityInfoRelation(FacilityBizcodHousingCplxRelation facilityBizcodHousingCplxRelation);

    /**
     * 시설업체 기본정보 수정 처리
     * @param facilityBizco
     * @return
     */
    int updateFacilityInfo(FacilityBizco facilityBizco);

    /**
     * 시스템 및 단지 관리자용 시설업체 연락처 정보수정
     * @param facilityBizcoCaddr
     * @return
     */
    int updateFacilityCaddrInfo(FacilityBizcoCaddr facilityBizcoCaddr);

    /**
     * 시설업체 기본정보 삭제 처리
     * @param facilityBizco
     * @return
     */
    int deleteFacilityInfo(FacilityBizco facilityBizco);

    /**
     * 시설업체 목록 기본정보 삭제 처리
     * @param facilityMap
     * @return
     */
    int deleteFacilityInfoList(Map<String, Object> facilityMap);

    /**
     * 해당시설업체 연락처 정보 리스트 삭제 처리
     * @param facilityBizcoCaddr
     * @return
     */
    int deleteFacilityInfoCaddr(FacilityBizcoCaddr facilityBizcoCaddr);

    /**
     * 해당시설업체 연락처 정보 리스트 삭제 처리
     * @param caddrMap
     * @return
     */
    int deleteFacilityInfoCaddrList(Map<String, Object> caddrMap);

    /**
     * 시설업체와 단지 관계 테이블 정보 삭제
     * @param facilityBizco
     * @return
     */
    int deleteFacilityInfoHousingCplxRelation(FacilityBizco facilityBizco);

    /**
     * 단지의 시설업체 정보와 단지 관계 테이블 정보 삭제
     * @param facilityBizco
     * @return
     */
    int deleteFacilityInfoHousingCplxRelationAll(FacilityBizco facilityBizco);

    /**
     * 시설업체와 업체 연락처 정보 관계 단일 삭제처리
     * @param facilityBizcoCaddrRelation
     * @return
     */
    int deleteFacilityInfoCaddrRelationOne(FacilityBizcoCaddrRelation facilityBizcoCaddrRelation);

    /**
     * 시설업체와 업체 연락처 정보 관계 삭제처리
     * @param facilityBizco
     * @return
     */
    int deleteFacilityInfoCaddrRelation(FacilityBizco facilityBizco);

    /**
     * 시설업체와 업체 연락처 정보 관계 전체삭제처리
     * @param caddrMap
     * @return
     */
    int deleteFacilityInfoCaddrRelationAll(Map<String, Object> caddrMap);
}
