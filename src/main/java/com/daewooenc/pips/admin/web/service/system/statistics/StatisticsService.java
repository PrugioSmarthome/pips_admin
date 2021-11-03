package com.daewooenc.pips.admin.web.service.system.statistics;

import com.daewooenc.pips.admin.web.dao.statistics.StatisticsMapper;
import com.daewooenc.pips.admin.web.domain.dto.statistics.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-12-08      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-12-08
 **/
@Service
public class StatisticsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatisticsMapper statisticsMapper;

    /**
     * 시스템 관리자용 입주민 현황 목록 조회
     * @return
     */
    public List<Statistics> getSystemStatisticsList(Statistics statistics) {
        List<Statistics> statisticsList = statisticsMapper.selectSystemStatisticsList(statistics);

        return statisticsList;
    }

}