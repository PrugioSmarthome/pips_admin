package com.daewooenc.pips.admin.web.controller.system.statistics;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.domain.dto.statistics.Statistics;
import com.daewooenc.pips.admin.web.service.system.statistics.StatisticsService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 입주민 현황 통계 Controller
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
@Controller
@RequestMapping("/cm/system/statistics")
public class StatisticsController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/statistics";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 통계 > 입주민 현황 통계 > 입주민 현황 목록
     * @param statistics
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String statisticsList(Statistics statistics, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("statisticsList", statisticsService.getSystemStatisticsList(statistics));

        return thisUrl + "/list";
    }

}