package com.daewooenc.pips.admin.web.controller.system.platform;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.common.IdKeyGenerator;
import com.daewooenc.pips.admin.web.domain.dto.platform.Platform;
import com.daewooenc.pips.admin.web.service.system.platform.PlatformService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 타 플랫폼 관리 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-11-13      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-11-13
 **/
@Controller
@RequestMapping("/cm/system/platform")
public class PlatformController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/platform";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private IdKeyGenerator idKeyGenerator;

    /**
     * 시스템 관리 > 타 플랫폼 관리 > 타 플랫폼 목록
     * @param platform
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String platformList(Platform platform, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("platformList", platformService.getSystemPlatformList(platform));
        model.addAttribute("platformTpNm", platform.getPlatformTpNm());
        model.addAttribute("platformNm", platform.getPlatformNm());
        model.addAttribute("platformCompany", platform.getPlatformCompany());

        return thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 타 플랫폼 관리 > 타 플랫폼 목록 > 타 플랫폼 상세
     * @param platform
     * @param request
     * @return
     */
    @RequestMapping(value = "view", method = {RequestMethod.GET, RequestMethod.POST})
    public String platformView(Platform platform, Model model, HttpServletRequest request, @RequestParam(value="platformId", required=false) String platformId) {
        SessionUser session = SessionUtil.getSessionUser(request);

        if(!"".equals(platformId) && platformId != null){
            platform.setPlatformId(platformId);
        }

        model.addAttribute("platformDetail", platformService.getSystemPlatformDetail(platform));

        return thisUrl + "/view";
    }

    /**
     * 시스템 관리 > 타 플랫폼 관리 > 타 플랫폼 목록 > 타 플랫폼 상세 > 타 플랫폼 수정
     * @param platform
     * @param request
     * @return
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String platformEdit(Platform platform, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("platformDetail", platformService.getSystemPlatformDetail(platform));

        return thisUrl + "/edit";
    }

    /**
     * 시스템 관리 > 타 플랫폼 관리 > 타 플랫폼 목록 > 타 플랫폼 등록
     * @param platform
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public String platformAdd(Platform platform, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        return thisUrl + "/add";
    }

    /**
     * 시스템 관리 > 타 플랫폼 관리 > 타 플랫폼 목록 > 타 플랫폼 상세 > 타 플랫폼 수정 > 수정하기
     * @param platform
     * @param request
     * @return
     */
    @RequestMapping(value = "editAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String platformEditAction(Platform platform, Model model, HttpServletRequest request, RedirectAttributes redirect) {
        SessionUser session = SessionUtil.getSessionUser(request);

        platform.setEditerId(session.getUserId());

        platformService.updatePlatform(platform);

        redirect.addAttribute("platformId", platform.getPlatformId());

        return "redirect:/" + thisUrl + "/view";
    }

    /**
     * 시스템 관리 > 타 플랫폼 관리 > 타 플랫폼 목록 > 타 플랫폼 등록 > 등록하기
     * @param platform
     * @param request
     * @return
     */
    @RequestMapping(value = "insertAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String platformInsertAction(Platform platform, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        platform.setCrerId(session.getUserId());

        // ID 생성
        String id = idKeyGenerator.getID(0);
        platform.setPlatformId(id);

        // KEY 생성
        String key = idKeyGenerator.getKey();
        platform.setPlatformAuthKey(key);

        platformService.insertPlatform(platform);

        return "redirect:/" + thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 타 플랫폼 관리 > 타 플랫폼 목록 > 타 플랫폼 상세 > 삭제하기
     * @param platform
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String platformDeleteAction(Platform platform, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        platform.setEditerId(session.getUserId());

        platform.setPlatformId(request.getParameter("platformId_"));

        platformService.deletePlatform(platform);

        return "redirect:/" + thisUrl + "/list";
    }


}