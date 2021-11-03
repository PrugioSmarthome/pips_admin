package com.daewooenc.pips.admin.web.dao.servicelink;

import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLink;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLinkDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 연계 웹/앱 관련 ServiceLinkMapper.xml 맵핑 Interface
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-23      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-23
 **/
public interface ServiceLinkMapper {

    /**
     * 공통: 연계 웹/앱 기본 정보 (연계 웹/앱 기본)
     * @return
     */
    List<ServiceLink> selectServiceLinkMetaInfo(HousingCplx housingCplx);

    /**
     * 공통: 연계 웹/앱 상세 정보 (연계 웹/앱 부가)
     * @param housingCplx
     * @return
     */
    List<ServiceLinkDetail> selectServiceLink(HousingCplx housingCplx);

    /**
     * 시스템 관리자: 연계 웹/앱 상세 목록조회
     * @param serviceLinkDetail
     * @return
     */
    List<ServiceLinkDetail> selectServiceDetailLinkList(ServiceLinkDetail serviceLinkDetail);

    /**
     * 시스템 관리자: 연계 웹/앱 목록조회
     * @param serviceLink
     * @return
     */
    List<ServiceLink> selectServiceLinkList(ServiceLink serviceLink);

    /**
     * 시스템 관리자: 연계 웹/앱 상세정보조회
     * @param serviceLinkDetail
     * @return
     */
    ServiceLinkDetail selectServiceLinkDetail(ServiceLinkDetail serviceLinkDetail);

    /**
     * 시스템 관리자: 연계 웹/앱 기본정보 등록
     * @param serviceLinkDetail
     * @return
     */
    int insertServiceLinkInfo(ServiceLinkDetail serviceLinkDetail);

    /**
     * 시스템 관리자: 연계 웹/앱 상세정보 등록
     * @param serviceLinkDetailMap
     * @returninsertServiceLinkInfo
     */
    int insertServiceLinkDetailInfo(Map<String, Object> serviceLinkDetailMap);

    /**
     * 시스템 관리자: 연계 웹/앱 상세정보 등록(단지리스트 등록)
     * @param serviceLinkDanjiMap
     * @return
     */
    int insertServiceLinkDanjiInfo(Map<String, Object> serviceLinkDanjiMap);

    /**
     * 시스템 관리자: 연계 웹/앱 상세정보 등록(단지리스트 삭제)
     * @param serviceLinkDanjiKeyInfo
     * @return
     */
    int deleteServiceLinkDanjiInfo(int serviceLinkDanjiKeyInfo);

    /**
     * 시스템 관리자: 연계 웹/앱 상세정보 등록(연계 웹/앱 키 값 조회)
     * @return
     */
    int selectServiceLinkKeyInfo();

    /**
     * 시스템 관리자: 연계 웹/앱 기본정보 수정
     * @param serviceLinkDetail
     * @return
     */
    int updateServiceLinkInfo(ServiceLinkDetail serviceLinkDetail);

    int updateServiceFileUrl(ServiceLinkDetail serviceLinkDetail);

    /**
     * 시스템 관리자: 연계 웹/앱 상세정보 수정
     * @param serviceLinkDetailMap
     * @return
     */
    int updateServiceLinkDetailInfo(Map<String, Object> serviceLinkDetailMap);

    /**
     * 시스템 관리자: 연계 웹/앱 기본정보 삭제
     * @param lnkSvcId
     * @param editerId
     * @return
     */
    int deleteServiceLinkInfo(@Param("lnkSvcId") int lnkSvcId, @Param("editerId") String editerId);

    /**
     * 시스템 관리자: 연계 웹/앱 상세정보 삭제
     * @param lnkSvcId
     * @param editerId
     * @return
     */
    int deleteServiceLinkDetailInfo(@Param("lnkSvcId") int lnkSvcId, @Param("editerId") String editerId);


    /**
     * 시스템 관리자: 연계 웹/앱 상세정보 등록(삭제 후 등록 기능을 위해 사용)
     * @param serviceLinkDetailMap
     * @return
     */
    int insertServiceLinkDetailInfoForDel(Map<String, Object> serviceLinkDetailMap);

    /**
     * 시스템 관리자: 연계 웹/앱 정렬순서 중복 체크
     * @param lnkOrdNo
     * @return
     */
    int checkServiceLinkOrd(int lnkOrdNo);

}
