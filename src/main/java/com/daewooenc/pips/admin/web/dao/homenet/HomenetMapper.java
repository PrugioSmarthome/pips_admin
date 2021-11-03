package com.daewooenc.pips.admin.web.dao.homenet;

import com.daewooenc.pips.admin.web.domain.dto.homenet.HomenetSvr;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-10-10      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-10
 **/
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface HomenetMapper {
    /**
     * 공통: 단지 등록 및 수정시 홈넷서버 기본정보 목록조회
     * @return
     */
    List<HomenetSvr> selectHomenetMetaList(HomenetSvr homenetSvr);

    /**
     * 공통: 사용되고 있는 홈넷 체크
     * @return
     */
    HomenetSvr checkUsedHomenet(HomenetSvr homenetSvr);

    String getHomenetId(String houscplxCd);

    String getHomenetNm(String hmnetId);
}
