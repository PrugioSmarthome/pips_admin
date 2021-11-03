package com.daewooenc.pips.admin.web.service.system.banner;

import com.daewooenc.pips.admin.web.dao.banner.BannerMapper;
import com.daewooenc.pips.admin.web.domain.dto.banner.Banner;
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
 *       2019-10-25      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-25
 **/
@Service
public class BannerService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 시스템 관리자용 배너 목록 조회
     * @return
     */
    public List<Banner> getSystemBannerList() {
        List<Banner> bannerList = bannerMapper.selectSystemBannerList();

        return bannerList;
    }

    /**
     * 시스템 관리자용 배너 목록 배너 수정 조회
     * @return
     */
    public List<Banner> getSystemBannerEditList() {
        List<Banner> bannerList = bannerMapper.selectSystemBannerEditList();

        return bannerList;
    }

    public int getSystemBannerNonResident(int lnkSvcId) {
        int nonResident = bannerMapper.selectSystemBannerNonResident(lnkSvcId);

        return nonResident;
    }

    /**
     * 시스템 관리자용 배너 목록 조회(단지관리>상세>기타)
     * @return
     */
    public List<Banner> getBannerList(String houscplxCd) {
        List<Banner> bannerList = bannerMapper.selectBannerList(houscplxCd);

        return bannerList;
    }

    /**
     * 시스템 관리자용 배너 목록 조회(단지관리>상세>기타>수정)
     * @return
     */
    public List<Banner> getEditBannerList(Banner banner) {
        List<Banner> bannerList = bannerMapper.editBannerList(banner);

        return bannerList;
    }

    /**
     * 시스템 관리자용 등록된 배너 갯수 조회
     * @return
     */
    public int getBannerCnt() {
        int bannerCnt = bannerMapper.selectBannerCnt();

        return bannerCnt;
    }

    /**
     * 시스템 관리자용 배너 정보 등록
     * @param bannerImageMap
     * @return
     */
    public boolean insertSystemBannerInfo(Map<String, Object> bannerImageMap) {
        return bannerMapper.insertSystemBannerInfo(bannerImageMap) > 0;
    }

    /**
     * 시스템 관리자용 배너 정보 등록(단지리스트 등록)
     * @param bannerDanjiMap
     * @return
     */
    public boolean insertSystemBannerDanjiInfo(Map<String, Object> bannerDanjiMap) {
        return bannerMapper.insertSystemBannerDanjiInfo(bannerDanjiMap) > 0;
    }

    /**
     * 시스템 관리자용 배너 정보 등록(비입주민단지 등록)
     * @param bannerDanjiMap
     * @return
     */
    public boolean insertSystemBannerNoResident(Map<String, Object> bannerDanjiMap) {
        return bannerMapper.insertSystemBannerNoResident(bannerDanjiMap) > 0;
    }

    /**
     * 시스템 관리자용 배너 정보 등록(단지리스트 삭제)
     * @param systemBannerDanjiKeyInfo
     * @return
     */
    public boolean deleteSystemBannerDanjiInfo(String systemBannerDanjiKeyInfo) {
        return bannerMapper.deleteSystemBannerDanjiInfo(systemBannerDanjiKeyInfo) > 0;
    }

    /**
     * 시스템 관리자용 배너 정보 등록(비입주민단지 삭제)
     * @param lnkSvcId
     * @return
     */
    public boolean deleteSystemBannerNoResident(String lnkSvcId) {
        return bannerMapper.deleteSystemBannerNoResident(lnkSvcId) > 0;
    }

    /**
     * 시스템 관리자용 배너 등록후 키 값 조회
     * @return
     */
    public List<Banner> getSystemBannerKeyInfo(int fileCount) {
        List<Banner> bannerList = bannerMapper.selectSystemBannerKeyInfo(fileCount);

        return bannerList;
    }

    /**
     * 시스템 관리자용 배너 이미지 수정
     * @param bannerImageMap
     * @return
     */
    public boolean updateSystemBannerImageInfo(Map<String, Object> bannerImageMap) {
        return bannerMapper.updateSystemBannerImageInfo(bannerImageMap) > 0;
    }

    /**
     * 시스템 관리자용 배너 부가정보 수정
     * @param banner
     * @return
     */
    public boolean updateSystemBannerDataInfo(Banner banner) {
        return bannerMapper.updateSystemBannerDataInfo(banner) > 0;
    }

    /**
     * 시스템 관리자용 배너 이미지 순서 수정
     * @param bannerOrderMap
     * @return
     */
    public boolean updateSystemBannerImageOrder(Map<String, Object> bannerOrderMap) {
        return bannerMapper.updateSystemBannerImageOrder(bannerOrderMap) > 0;
    }

    /**
     * 시스템 관리자용 배너 삭제처리
     * @param bannerImageDeleteMap
     * @return
     */
    public boolean updateSystemBannerImageStatus(Map<String, Object> bannerImageDeleteMap) {
        return bannerMapper.updateSystemBannerImageStatus(bannerImageDeleteMap) > 0;
    }

    /**
     * 시스템 관리자용 배너 삭제처리
     * @param blltOrdNo
     * @return
     */
    public boolean checkBannerBlltOrdNo(String blltOrdNo) {
        return bannerMapper.checkBannerBlltOrdNo(blltOrdNo) > 0;
    }
}