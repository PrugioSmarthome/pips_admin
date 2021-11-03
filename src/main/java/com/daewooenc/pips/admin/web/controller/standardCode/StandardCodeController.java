package com.daewooenc.pips.admin.web.controller.standardCode;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.domain.dto.standardCode.StandardCode;
import com.daewooenc.pips.admin.web.service.system.standardCode.StandardCodeService;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 공통코드 관리 관련 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2021-03-23      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2021-04-07
 **/
@Controller
@RequestMapping("/cm/system/standardCode")
public class StandardCodeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/standardCode";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private StandardCodeService standardCodeService;

    /**
     * 시스템 관리 > 공통코드 관리 > 공통코드 목록
     * @param standardCode
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(StandardCode standardCode, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        List<StandardCode> standardCodeList = standardCodeService.getStandardCodeList(standardCode);
        List<StandardCode> groupStandardCodeNameList = standardCodeService.getGroupStandardCodeNameList();

        model.addAttribute("standardCodeList", standardCodeList);
        model.addAttribute("groupStandardCodeNameList", groupStandardCodeNameList);
        model.addAttribute("commCdGrpCd", StringUtils.defaultIfEmpty(standardCode.getCommCdGrpCd(), "all"));
        model.addAttribute("commCd", StringUtils.defaultIfEmpty(standardCode.getCommCd(), "all"));
        model.addAttribute("searchingYn", request.getParameter("searchingYn"));

        return thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 공통코드 관리 > 공통코드 목록 > 공통코드 이름 조회
     * Ajax 방식
     * @param standardCode
     * @param request
     * @return
     */
    @RequestMapping(value = "getStandardCodeNameList", method = {RequestMethod.GET, RequestMethod.POST},produces = "application/text; charset=utf8")
    @ResponseBody
    public String getStandardCodeNameList(StandardCode standardCode, HttpServletRequest request) {

        List<StandardCode> standardCodeNameList = standardCodeService.getStandardCodeNameList(standardCode);

        String standardCodeNameJsonArray = JsonUtil.toJsonNotZero(standardCodeNameList);

        return standardCodeNameJsonArray;
    }

    /**
     * 시스템 관리 > 공통코드 관리 > 공통코드 목록 > 삭제
     * @param standardCode
     * @param request
     * @return
     */
    @RequestMapping(value = "delete", method = {RequestMethod.GET, RequestMethod.POST})
    public String delete(StandardCode standardCode, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String standardCodeArray = request.getParameter("standardCodeArray");
        String[] standardCodeList = standardCodeArray.split(",");
        for(int i=0; i<standardCodeList.length; i++){
            int idx = standardCodeList[i].indexOf("/");
            String commCdGrpCd = standardCodeList[i].substring(0,idx);
            String commCd = standardCodeList[i].substring(idx+1);

            standardCode.setCommCdGrpCd(commCdGrpCd);
            standardCode.setCommCd(commCd);
            standardCodeService.deleteStandardCode(standardCode);
        }

        return "redirect:/" + thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 공통코드 관리 > 공통코드 목록 > 공통코드 등록
     * @param standardCode
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public String add(StandardCode standardCode, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        return thisUrl + "/add";
    }


    /**
     * 시스템 관리 > 공통코드 관리 > 공통코드 목록 > 공통코드 등록 > 공통코드 선택
     * Ajax 방식
     * @param standardCode
     * @param request
     * @return
     */
    @RequestMapping(value = "groupStandardCodeNameList", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String groupStandardCodeNameList(StandardCode standardCode, HttpServletRequest request) {
        String result = "";

        List<StandardCode> nStandardCodeNameList = new ArrayList();
        StandardCode scnlp = new StandardCode();

        List<StandardCode> groupStandardCodeNameList = standardCodeService.getGroupStandardCodeNameList();
        for (int i = 0; i < groupStandardCodeNameList.size(); i++) {
            StandardCode scnl = groupStandardCodeNameList.get(i);
            nStandardCodeNameList.add(scnl);
        }

        String groupStandardCodeNameListJsonArray = JsonUtil.toJsonNotZero(nStandardCodeNameList);

        result = groupStandardCodeNameListJsonArray;

        return result;
    }

    /**
     * 시스템 관리 > 공통코드 관리 > 공통코드 목록 > 공통코드 등록 > 공통코드 중복 체크
     * Ajax 방식
     * @param standardCode
     * @param request
     * @return
     */
    @RequestMapping(value = "checkStandardCode", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkStandardCode(StandardCode standardCode, HttpServletRequest request) {

        boolean check = standardCodeService.checkStandardCode(standardCode);

        return check;
    }

    /**
     * 시스템 관리 > 공통코드 관리 > 공통코드 목록 > 공통코드 등록 > 등록
     * @param standardCode
     * @param request
     * @return
     */
    @RequestMapping(value = "addStandardCode", method = {RequestMethod.GET, RequestMethod.POST})
    public String addStandardCode(StandardCode standardCode, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        standardCode.setCrerId(session.getUserId());

        standardCodeService.insertStandardCode(standardCode);

        return "redirect:/" + thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 공통코드 관리 > 공통코드 목록 > 공통코드 수정
     * @param standardCode
     * @param request
     * @return
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String edit(StandardCode standardCode, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        standardCode.setCommCdGrpCd(request.getParameter("commCdGrpCd__"));
        standardCode.setCommCd(request.getParameter("commCd__"));

        StandardCode standardCodeEdit = standardCodeService.getStandardCodeEdit(standardCode);

        model.addAttribute("standardCodeEdit", standardCodeEdit);

        return thisUrl + "/edit";
    }

    /**
     * 시스템 관리 > 공통코드 관리 > 공통코드 목록 > 공통코드 수정 > 수정
     * @param standardCode
     * @param request
     * @return
     */
    @RequestMapping(value = "editStandardCode", method = {RequestMethod.GET, RequestMethod.POST})
    public String editStandardCode(StandardCode standardCode, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        standardCode.setEditerId(session.getUserId());

        standardCodeService.updateStandardCode(standardCode);

        return "redirect:/" + thisUrl + "/list";
    }


}