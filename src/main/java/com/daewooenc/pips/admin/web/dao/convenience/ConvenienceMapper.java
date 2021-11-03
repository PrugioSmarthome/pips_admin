package com.daewooenc.pips.admin.web.dao.convenience;

import com.daewooenc.pips.admin.web.domain.dto.convenience.Convenience;
import com.daewooenc.pips.admin.web.domain.dto.platform.Platform;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 대우건설 스마트홈 푸르지오 플랫폼용 배너 관련 ConvenienceMapper.xml 맵핑 Interface
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
public interface ConvenienceMapper {
    /**
     * 시스템 관리자용 편의 시설 목록 조회
     * @return
     */
    List<Convenience> selectSystemConvenienceList(Convenience convenience);

    /**
     * 시스템 관리자용 편의 시설 수정 조회
     * @return
     */
    Convenience selectSystemConvenienceEdit(Convenience convenience);

    /**
     * 시스템 관리자용 편의 시설 수정
     * @return
     */
    int insertConvenience(Convenience convenience);

    /**
     * 시스템 관리자용 편의 시설 삭제
     * @return
     */
    int deleteConvenience(Convenience convenience);

}
