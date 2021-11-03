package com.daewooenc.pips.admin.web.dao.platform;

import com.daewooenc.pips.admin.web.domain.dto.platform.Platform;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 대우건설 스마트홈 푸르지오 타 플랫폼 관련 PlatformMapper.xml 맵핑 Interface
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-11-16    :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-11-16
 **/
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface PlatformMapper {
    /**
     * 시스템 관리자용 타 플랫폼 목록 조회
     * @return
     */
    List<Platform> selectSystemPlatformList(Platform platform);

    /**
     * 시스템 관리자용 타 플랫폼 상세
     * @return
     */
    Platform selectSystemPlatformDetail(Platform platform);

    /**
     * 시스템 관리자용 타 플랫폼 수정
     * @return
     */
    int updatePlatform(Platform platform);

    /**
     * 시스템 관리자용 타 플랫폼 등록
     * @return
     */
    int insertPlatform(Platform platform);

    /**
     * 시스템 관리자용 타 플랫폼 등록시 ID 존재 여부 확인
     * @return
     */
    int selectSystemPlatformIdCheck(String Id);

    /**
     * 시스템 관리자용 타 플랫폼 삭제
     * @return
     */
    int deletePlatform(Platform platform);

}
