package com.daewooenc.pips.admin.web.dao.common;

import com.daewooenc.pips.admin.web.domain.dto.common.CommCodeDetail;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 대우건설 스마트홈 푸르지오 플랫폼용 공통코드 관련 CommCodeMapper.xml 맵핑 Interface
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
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface CommCodeMapper {
    /**
     * 공통: 대우건설 스마트홈 푸르지오 플랫폼용 그룹공통코드를 통해 세부공통코드 목록조회
     * @param commCodeDetail
     * @return
     */
    List<CommCodeDetail> selectCommCodeList(CommCodeDetail commCodeDetail);
}
