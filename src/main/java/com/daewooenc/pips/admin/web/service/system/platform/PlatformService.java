package com.daewooenc.pips.admin.web.service.system.platform;

import com.daewooenc.pips.admin.web.dao.banner.BannerMapper;
import com.daewooenc.pips.admin.web.dao.platform.PlatformMapper;
import com.daewooenc.pips.admin.web.domain.dto.banner.Banner;
import com.daewooenc.pips.admin.web.domain.dto.platform.Platform;
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
public class PlatformService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlatformMapper platformMapper;

    /**
     * 시스템 관리자용 타 플랫폼 목록 조회
     * @return
     */
    public List<Platform> getSystemPlatformList(Platform platform) {
        List<Platform> platformList = platformMapper.selectSystemPlatformList(platform);

        return platformList;
    }

    /**
     * 시스템 관리자용 타 플랫폼 상세/수정 조회
     * @return
     */
    public Platform getSystemPlatformDetail(Platform platform) {
        Platform Platform = platformMapper.selectSystemPlatformDetail(platform);

        return Platform;
    }

    /**
     * 시스템 관리자용 타 플랫폼 수정
     * @return
     */
    public boolean updatePlatform(Platform platform) {
        return platformMapper.updatePlatform(platform) > 0;
    }

    /**
     * 시스템 관리자용 타 플랫폼 등록
     * @return
     */
    public boolean insertPlatform(Platform platform) {
        return platformMapper.insertPlatform(platform) > 0;
    }

    /**
     * 시스템 관리자용 타 플랫폼 등록시 ID 존재 여부 확인
     * @return
     */
    public int getSystemPlatformIdCheck(String Id) {
        int cnt = platformMapper.selectSystemPlatformIdCheck(Id);

        return cnt;
    }

    /**
     * 시스템 관리자용 타 플랫폼 삭제
     * @return
     */
    public boolean deletePlatform(Platform platform) {
        return platformMapper.deletePlatform(platform) > 0;
    }

}