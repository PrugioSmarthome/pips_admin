package com.daewooenc.pips.admin.web.dao.banner;

import com.daewooenc.pips.admin.web.domain.dto.banner.Banner;
import com.daewooenc.pips.admin.web.domain.dto.common.CommCodeDetail;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 대우건설 스마트홈 푸르지오 플랫폼용 배너 관련 BannerMapper.xml 맵핑 Interface
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
 * @since : 2019-11-01
 **/
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface BannerMapper {
    /**
     * 시스템 관리자용 배너목록 조회
     * @return
     */
    List<Banner> selectSystemBannerList();
    /**
     * 시스템 관리자용 배너목록 배너수정 조회
     * @return
     */
    List<Banner> selectSystemBannerEditList();

    /**
     * 시스템 관리자용 배너목록 비입주민여부 조회
     * @return
     */
    int selectSystemBannerNonResident(int lnkSvcId);

    /**
     * 시스템 관리자용 배너목록 조회(단지 관리>상세>기타)
     * @return
     */
    List<Banner> selectBannerList(String houscplxCd);

    /**
     * 시스템 관리자용 배너목록 조회(단지 관리>상세>기타>수정)
     * @return
     */
    List<Banner> editBannerList(Banner banner);

    /**
     * 등록된 배너갯수 조회
     * @return
     */
    int selectBannerCnt();

    /**
     * 시스템 관리자용 배너정보 등록
     * @param bannerImageMap
     * @return
     */
    int insertSystemBannerInfo(Map<String, Object> bannerImageMap);

    /**
     * 시스템 관리자용 배너정보 등록(단지리스트 등록)
     * @param bannerDanjiMap
     * @return
     */
    int insertSystemBannerDanjiInfo(Map<String, Object> bannerDanjiMap);

    /**
     * 시스템 관리자용 배너정보 등록(비입주민단지 등록)
     * @param bannerDanjiMap
     * @return
     */
    int insertSystemBannerNoResident(Map<String, Object> bannerDanjiMap);

    /**
     * 시스템 관리자용 배너정보 등록(단지리스트 삭제)
     * @param systemBannerDanjiKeyInfo
     * @return
     */
    int deleteSystemBannerDanjiInfo(String systemBannerDanjiKeyInfo);

    /**
     * 시스템 관리자용 배너정보 등록(비입주민단지 삭제)
     * @param lnkSvcId
     * @return
     */
    int deleteSystemBannerNoResident(String lnkSvcId);

    /**
     * 시스템 관리자용 배너정보 등록후 키 값 조회
     * @return
     */
    List<Banner> selectSystemBannerKeyInfo(int fileCount);

    /**
     * 시스템 관리자용 배너 이미지 정보 수정
     * @param bannerImageMap
     * @return
     */
    int updateSystemBannerImageInfo(Map<String, Object> bannerImageMap);

    /**
     * 시스템 관리자용 배너 부가정보 수정
     * @param banner
     * @return
     */
    int updateSystemBannerDataInfo(Banner banner);

    /**
     * 시스템 관리자용 배너 이미지 순서 수정
     * @param bannerImageOrderMap
     * @return
     */
    int updateSystemBannerImageOrder(Map<String, Object> bannerImageOrderMap);

    /**
     * 시스템 관리자용 배너 삭제 수정처리
     * @param bannerImageDeleteMap
     * @return
     */
    int updateSystemBannerImageStatus(Map<String, Object> bannerImageDeleteMap);

    /**
     * 시스템 관리자용 배너 등록 정렬순서 중복 체크
     * @param blltOrdNo
     * @return
     */
    int checkBannerBlltOrdNo(String blltOrdNo);
}
