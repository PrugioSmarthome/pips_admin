package com.daewooenc.pips.admin.web.controller.system.groupCode;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.domain.dto.groupCode.GroupCode;
import com.daewooenc.pips.admin.web.domain.dto.manufacturerByDevice.ManufacturerByDevice;
import com.daewooenc.pips.admin.web.service.system.groupCode.GroupCodeService;
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
 * 그룹코드 관리 관련 Controller
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
 * @since : 2021-03-23
 **/
@Controller
@RequestMapping("/cm/system/groupCode")
public class GroupCodeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/groupCode";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private GroupCodeService groupCodeService;

    /**
     * 시스템 관리 > 그룹코드 관리 > 그룹코드 목록
     * @param groupCode
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(GroupCode groupCode, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String commCdGrpCd = request.getParameter("commCdGrpCd__");
        if(!"".equals(commCdGrpCd) && commCdGrpCd != null){
            groupCode.setCommCdGrpCd(commCdGrpCd);
        }

        List<GroupCode> groupCodeList = groupCodeService.getGroupCodeList(groupCode);
        List<GroupCode> groupCodeNameList = groupCodeService.getGroupCodeNameList(groupCode);

        model.addAttribute("groupCodeList", groupCodeList);
        model.addAttribute("groupCodeNameList", groupCodeNameList);
        model.addAttribute("commCdGrpCd", StringUtils.defaultIfEmpty(groupCode.getCommCdGrpCd(), "all"));

        return thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 그룹코드 관리 > 그룹코드 목록 > 삭제
     * @param groupCode
     * @param request
     * @return
     */
    @RequestMapping(value = "delete", method = {RequestMethod.GET, RequestMethod.POST})
    public String delete(GroupCode groupCode, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String groupCodeArray = request.getParameter("groupCodeArray");
        String[] groupCodeList = groupCodeArray.split(",");
        for(int i=0; i<groupCodeList.length; i++){
            groupCode.setCommCdGrpCd(groupCodeList[i]);
            groupCodeService.deleteGroupCodeDtl(groupCode);
            groupCodeService.deleteGroupCodeBas(groupCode);
        }

        return "redirect:/" + thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 그룹코드 관리 > 그룹코드 목록 > 그룹코드 등록
     * @param groupCode
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public String add(GroupCode groupCode, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        return thisUrl + "/add";
    }

    /**
     * 시스템 관리 > 그룹코드 관리 > 그룹코드 목록 > 그룹코드 등록 > 그룹코드 중복 체크
     * Ajax 방식
     * @param groupCode
     * @param request
     * @return
     */
    @RequestMapping(value = "checkGroupCode", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkGroupCode(GroupCode groupCode, HttpServletRequest request) {

        boolean check = groupCodeService.checkGroupCode(groupCode);

        return check;
    }

    /**
     * 시스템 관리 > 그룹코드 관리 > 그룹코드 목록 > 그룹코드 등록
     * @param groupCode
     * @param request
     * @return
     */
    @RequestMapping(value = "addGroupCode", method = {RequestMethod.GET, RequestMethod.POST})
    public String addGroupCode(GroupCode groupCode, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        groupCode.setCrerId(session.getUserId());

        if("".equals(groupCode.getCommCdGrpNm()) || groupCode.getCommCdGrpNm() == null){
            groupCode.setCommCdGrpNm(null);
        }
        if("".equals(groupCode.getRem()) || groupCode.getRem() == null){
            groupCode.setRem(null);
        }

        groupCodeService.insertGroupCode(groupCode);

        return "redirect:/" + thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 그룹코드 관리 > 그룹코드 목록 > 그룹코드 수정
     * @param groupCode
     * @param request
     * @return
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String edit(GroupCode groupCode, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        GroupCode groupCodeEdit = groupCodeService.selectGroupCodeEdit(groupCode);
        model.addAttribute("groupCodeEdit", groupCodeEdit);

        return thisUrl + "/edit";
    }

    /**
     * 시스템 관리 > 그룹코드 관리 > 그룹코드 목록 > 그룹코드 수정
     * @param groupCode
     * @param request
     * @return
     */
    @RequestMapping(value = "editGroupCode", method = {RequestMethod.GET, RequestMethod.POST})
    public String editGroupCode(GroupCode groupCode, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        groupCode.setEditerId(session.getUserId());

        if("".equals(groupCode.getCommCdGrpNm()) || groupCode.getCommCdGrpNm() == null){
            groupCode.setCommCdGrpNm(null);
        }
        if("".equals(groupCode.getRem()) || groupCode.getRem() == null){
            groupCode.setRem(null);
        }

        groupCodeService.updateGroupCode(groupCode);

        return "redirect:/" + thisUrl + "/list";
    }

}