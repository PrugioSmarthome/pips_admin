package com.daewooenc.pips.admin.web.dao.statistics;

import com.daewooenc.pips.admin.web.domain.dto.platform.Platform;
import com.daewooenc.pips.admin.web.domain.dto.statistics.Statistics;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 대우건설 스마트홈 푸르지오 통계 관련 statisticsMapper.xml 맵핑 Interface
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-12-08    :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-12-08
 **/

@Component
@Resource(name = "sqlDataSourceAdmin")
public interface StatisticsMapper {

    /**
     * 시스템 관리자용 입주민 현황 통계 조회
     * @return
     */
    List<Statistics> selectSystemStatisticsList(Statistics statistics);


}
