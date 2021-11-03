package com.daewooenc.pips.admin.web.controller.system;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.domain.dto.common.DataTableWrapper;
import com.daewooenc.pips.admin.web.domain.dto.mongo.AppAccessHistory;
import com.daewooenc.pips.admin.web.domain.dto.mongo.AppAccessHistoryItem;
import com.daewooenc.pips.admin.web.domain.dto.mongo.ExternalApiUseHistory;
import com.daewooenc.pips.admin.web.domain.dto.mongo.ExternalApiUseHistoryItem;
import com.daewooenc.pips.admin.web.domain.vo.mongodb.AppAccessHistoryConditionVo;
import com.daewooenc.pips.admin.web.domain.vo.mongodb.ExternalServiceApiUseConditionVo;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import com.daewooenc.pips.admin.web.util.mongo.AppAccessHistoryMongo;
import com.daewooenc.pips.admin.web.util.mongo.ExternalServiceApiUseHistoryMongo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-10-16      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-16
 **/
@Controller
@RequestMapping("/cm/system")
public class SystemController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private AppAccessHistoryMongo appAccessHistoryMongo;

    @Autowired
    private ExternalServiceApiUseHistoryMongo externalServiceApiUseHistoryMongo;

    private String thisUrl = "cm/system";

    @RequestMapping(value = "appAccess/list", method = {RequestMethod.GET,RequestMethod.POST})
    public String appAccessList(Model model) {
        return thisUrl + "/appAccess/list";
    }

    @RequestMapping(value = "appAccess/ajax/list", method = {RequestMethod.POST})
    public @ResponseBody
    DataTableWrapper appAccessAjaxList(AppAccessHistoryItem appAccessHistoryItem, HttpServletRequest request) {
        logger.debug("/cm/system/appAccess/ajax/list start");

        logger.debug("appAccessHistoryItem : " + appAccessHistoryItem.toString());
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");


        AppAccessHistoryConditionVo aahcVo = new AppAccessHistoryConditionVo();

        String startDate = xssUtil.replaceAll(StringUtils.defaultString(appAccessHistoryItem.getStartCrDt()));
        String endDate = xssUtil.replaceAll(StringUtils.defaultString(appAccessHistoryItem.getEndCrDt()));
        String userType = xssUtil.replaceAll(StringUtils.defaultString(appAccessHistoryItem.getUserTpCd()));
        String userId = xssUtil.replaceAll(StringUtils.defaultString(appAccessHistoryItem.getUserId()));
        String complexCode = xssUtil.replaceAll(StringUtils.defaultString(appAccessHistoryItem.getHouscplxCd()));

        startDate = StringUtils.defaultIfEmpty(startDate, "");
        endDate = StringUtils.defaultIfEmpty(endDate, "");
        userType = StringUtils.defaultIfEmpty(userType, "");
        userId = StringUtils.defaultIfEmpty(userId, "");
        complexCode = StringUtils.defaultIfEmpty(complexCode, "");

        aahcVo.setStartDate(startDate + "000000");
        aahcVo.setEndDate(endDate + "235959");
        aahcVo.setUserType(userType);
        aahcVo.setUserId(userId);
        aahcVo.setComplexCode(complexCode);
        aahcVo.setPage(appAccessHistoryItem.getStart() / 10 + 1);
        aahcVo.setPageForCnt(10);

        long totalCount = appAccessHistoryMongo.countAppAccessHistory(aahcVo);
        List<AppAccessHistory> resultList = appAccessHistoryMongo.selectAppAccessHistory(aahcVo);
        logger.debug("resultList : " + resultList.size());

        DataTableWrapper rtnVO = new DataTableWrapper();
        rtnVO.setData(resultList);
        rtnVO.setRecordsTotal((int) totalCount);
        rtnVO.setRecordsFiltered((int) totalCount);
        rtnVO.setDraw(appAccessHistoryItem.getDraw());
        logger.info("****************************  사용자 앱 접근 이력 조회  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User App History Search]");
//        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getHeader("Proxy-Client-IP")+", action : User App History Search]");
        logger.info("***********************************************************************");
        return rtnVO;
    }

    @RequestMapping(value = "apiAccess/list", method = {RequestMethod.GET,RequestMethod.POST})
    public String externalApiList() {
        return thisUrl + "/apiAccess/list";
    }

    @RequestMapping(value = "apiAccess/ajax/list", method = {RequestMethod.POST})
    public @ResponseBody DataTableWrapper externalApiAjaxList(ExternalApiUseHistoryItem externalApiUseHistoryItem) {
        logger.debug("/cm/system/apiAccess/ajax/list start");

        logger.debug("externalApiUseHistoryItem : " + externalApiUseHistoryItem.toString());

        String startDate = xssUtil.replaceAll(StringUtils.defaultString(externalApiUseHistoryItem.getStartCrDt()));
        String endDate = xssUtil.replaceAll(StringUtils.defaultString(externalApiUseHistoryItem.getEndCrDt()));
        String userId = xssUtil.replaceAll(StringUtils.defaultString(externalApiUseHistoryItem.getUserId()));
        String userType = xssUtil.replaceAll(StringUtils.defaultString(externalApiUseHistoryItem.getUserTpCd()));
        String complexCode = xssUtil.replaceAll(StringUtils.defaultString(externalApiUseHistoryItem.getHouscplxCd()));
        String svcTpCd = xssUtil.replaceAll(StringUtils.defaultString(externalApiUseHistoryItem.getSvcTpCd()));
        String svcTpDtlCd = xssUtil.replaceAll(StringUtils.defaultString(externalApiUseHistoryItem.getSvcTpDtlCd()));

        startDate = StringUtils.defaultIfEmpty(startDate, "");
        endDate = StringUtils.defaultIfEmpty(endDate, "");
        userId = StringUtils.defaultIfEmpty(userId, "");
        userType = StringUtils.defaultIfEmpty(userType, "");
        complexCode = StringUtils.defaultIfEmpty(complexCode, "");
        svcTpCd = StringUtils.defaultIfEmpty(svcTpCd, "");
        svcTpDtlCd = StringUtils.defaultIfEmpty(svcTpDtlCd, "");

        ExternalServiceApiUseConditionVo esaucVO = new ExternalServiceApiUseConditionVo();
        esaucVO.setStartDate(startDate + "000000");
        esaucVO.setEndDate(endDate + "235959");
        esaucVO.setUserId(userId);
        esaucVO.setUserType(userType);
        esaucVO.setComplexCode(complexCode);
        esaucVO.setSvcTpCd(svcTpCd);
        esaucVO.setSvcTpDtlCd(svcTpDtlCd);
        esaucVO.setPage(externalApiUseHistoryItem.getStart() / 10 + 1);
        esaucVO.setPageForCnt(10);

        long totalCount = externalServiceApiUseHistoryMongo.countExternalServiceApiUseHistory(esaucVO);
        List<ExternalApiUseHistory> resultList = externalServiceApiUseHistoryMongo.selectExternalServiceApiUseHistory(esaucVO);
        logger.debug("resultList : " + resultList.size());

        DataTableWrapper rtnVO = new DataTableWrapper();
        rtnVO.setData(resultList);
        rtnVO.setRecordsTotal((int) totalCount);
        rtnVO.setRecordsFiltered((int) totalCount);
        rtnVO.setDraw(externalApiUseHistoryItem.getDraw());

        return rtnVO;

    }

}