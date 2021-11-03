package com.daewooenc.pips.admin.web.controller.system.convenience;

import com.daewooenc.pips.admin.core.domain.UploadFileInfo;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.FileUploadUtil;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.domain.dto.banner.Banner;
import com.daewooenc.pips.admin.web.domain.dto.convenience.Convenience;
import com.daewooenc.pips.admin.web.domain.dto.platform.Platform;
import com.daewooenc.pips.admin.web.service.system.banner.BannerService;
import com.daewooenc.pips.admin.web.service.system.convenience.ConvenienceService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 편의 시설 관리 관련 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-11-17      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-11-17
 **/
@Controller
@RequestMapping("/cm/system/convenience")
public class ConvenienceController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/convenience";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private ConvenienceService convenienceservice;

    /**
     * 장치 및 편의 시설 관리 > 편의 시설 설정 > 편의 시설 목록
     * @param convenience
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String convenienceList(Convenience convenience, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        model.addAttribute("convenienceList", convenienceservice.getSystemConvenienceList(convenience));
        model.addAttribute("houscplxCd", convenience.getHouscplxCd());
        model.addAttribute("houscplxNm", convenience.getHouscplxNm());

        return thisUrl + "/list";
    }

    /**
     * 장치 및 편의 시설 관리 > 편의 시설 설정 > 편의 시설 목록 > 편의 시설 수정
     * @param convenience
     * @param request
     * @return
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String convenienceEdit(Convenience convenience, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        convenience.setHouscplxCd(request.getParameter("houscplxCd_"));

        model.addAttribute("convenienceDetail", convenienceservice.getSystemConvenienceEdit(convenience));
        model.addAttribute("houscplxCd", convenience.getHouscplxCd());

        return thisUrl + "/edit";
    }

    /**
     * 장치 및 편의 시설 관리 > 편의 시설 설정 > 편의 시설 목록 > 편의 시설 수정 > 수정하기
     * @param convenience
     * @param request
     * @return
     */
    @RequestMapping(value = "editAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String editAction(Convenience convenience, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        convenience.setCrerId(session.getUserId());

        String convenienceArray = request.getParameter("convenienceArray");
        String[] convenienceList = convenienceArray.split(",");

        convenienceservice.deleteConvenience(convenience);

        for(int i=0; i<convenienceList.length; i++){
            int idx = convenienceList[i].indexOf("/");
            String skilCd = convenienceList[i].substring(0, idx);
            String screenYn = convenienceList[i].substring(idx+1);
            convenience.setSkilCd(skilCd);
            convenience.setScreenYn(screenYn);
            convenienceservice.insertConvenience(convenience);
        }




        return "redirect:/" + thisUrl + "/list";
    }

}