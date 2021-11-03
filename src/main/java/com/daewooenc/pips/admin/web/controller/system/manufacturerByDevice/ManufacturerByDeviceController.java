package com.daewooenc.pips.admin.web.controller.system.manufacturerByDevice;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.domain.dto.manufacturerByDevice.ManufacturerByDevice;
import com.daewooenc.pips.admin.web.service.system.manufacturerByDevice.ManufacturerByDeviceService;
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
 * 장치별 제조사 관리 관련 Controller
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
@RequestMapping("/cm/system/manufacturerByDevice")
public class ManufacturerByDeviceController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/manufacturerByDevice";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private ManufacturerByDeviceService manufacturerByDeviceService;


    /**
     * 장치 및 편의시설 설정 > 장치별 제조사 관리 > 장치별 제조사 목록
     * @param manufacturerByDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String manufacturerByDeviceList(ManufacturerByDevice manufacturerByDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        List<ManufacturerByDevice> manufacturerByDeviceList = manufacturerByDeviceService.getManufacturerByDeviceList(manufacturerByDevice);

        model.addAttribute("manufacturerByDeviceList", manufacturerByDeviceList);
        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(request.getParameter("houscplxCd"), ""));
        model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(request.getParameter("houscplxNm"), ""));
        model.addAttribute("deviceTpCd", StringUtils.defaultIfEmpty(request.getParameter("deviceTpCd"), "all"));

        return thisUrl + "/list";
    }

    /**
     * 장치 및 편의시설 설정 > 장치별 제조사 관리 > 장치별 제조사 목록 > 삭제
     * @param manufacturerByDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "delete", method = {RequestMethod.GET, RequestMethod.POST})
    public String delete(ManufacturerByDevice manufacturerByDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String manufacturerByDeviceArray = request.getParameter("manufacturerByDeviceArray");
        String[] manufacturerByDeviceList = manufacturerByDeviceArray.split(",");
        for(int i=0; i<manufacturerByDeviceList.length; i++){
            String[] manufacturerByDeviceValue = manufacturerByDeviceList[i].split("/");
            String houscplxCd = manufacturerByDeviceValue[0];
            String deviceTpCd = manufacturerByDeviceValue[1];
            String deviceMfCd = manufacturerByDeviceValue[2];

            manufacturerByDevice.setHouscplxCd(houscplxCd);
            manufacturerByDevice.setDeviceTpCd(deviceTpCd);
            manufacturerByDevice.setDeviceMfCd(deviceMfCd);

            manufacturerByDeviceService.deleteManufacturerByDevice(manufacturerByDevice);
        }

        return "redirect:/" + thisUrl + "/list";
    }

    /**
     * 장치 및 편의시설 설정 > 장치별 제조사 관리 > 장치별 제조사 등록 > 장치제조사 선택
     * Ajax 방식
     * @param manufacturerByDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "deviceMfList", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String getDeviceMfList(ManufacturerByDevice manufacturerByDevice, HttpServletRequest request) {
        String result = "";

        List<ManufacturerByDevice> nDeviceMfList = new ArrayList();
        ManufacturerByDevice mfbdp = new ManufacturerByDevice();

        List<ManufacturerByDevice> deviceMfList = manufacturerByDeviceService.getDeviceMfList(manufacturerByDevice);
        for (int i = 0; i < deviceMfList.size(); i++) {
            ManufacturerByDevice mfbd = deviceMfList.get(i);
            nDeviceMfList.add(mfbd);
        }

        String deviceMfListJsonArray = JsonUtil.toJsonNotZero(nDeviceMfList);

        result = deviceMfListJsonArray;

        return result;
    }

    /**
     * 장치 및 편의시설 설정 > 장치별 제조사 관리 > 장치별 제조사 등록
     * @param manufacturerByDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public String add(ManufacturerByDevice manufacturerByDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        return thisUrl + "/add";
    }

    /**
     * 장치 및 편의시설 설정 > 장치별 제조사 관리 > 장치별 제조사 등록 > 등록
     * @param manufacturerByDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "addManufacturerByDevice", method = {RequestMethod.GET, RequestMethod.POST})
    public String addManufacturerByDevice(ManufacturerByDevice manufacturerByDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        manufacturerByDevice.setCrerId(session.getUserId());

        manufacturerByDeviceService.insertManufacturerByDevice(manufacturerByDevice);

        return "redirect:/" + thisUrl + "/list";
    }

    /**
     * 장치 및 편의시설 설정 > 장치별 제조사 관리 > 장치별 제조사 등록 > 장치제조사 중복 체크
     * Ajax 방식
     * @param manufacturerByDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "checkDeviceMf", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkDeviceMf(ManufacturerByDevice manufacturerByDevice, HttpServletRequest request) {
        manufacturerByDevice.setHouscplxCd(request.getParameter("houscplxCd"));
        manufacturerByDevice.setDeviceTpCd(request.getParameter("deviceTpCd"));
        manufacturerByDevice.setDeviceMfCd(request.getParameter("deviceMfCd"));

        boolean check = manufacturerByDeviceService.checkDeviceMf(manufacturerByDevice);

        return check;
    }

    /**
     * 장치 및 편의시설 설정 > 장치별 제조사 관리 > 장치별 제조사 수정
     * @param manufacturerByDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String edit(ManufacturerByDevice manufacturerByDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        manufacturerByDevice.setHouscplxCd(request.getParameter("houscplxCdEdit"));
        manufacturerByDevice.setDeviceTpCd(request.getParameter("deviceTpCdEdit"));
        manufacturerByDevice.setDeviceMfCd(request.getParameter("deviceMfCdEdit"));

        ManufacturerByDevice manufacturerByDeviceEdit = manufacturerByDeviceService.getManufacturerByDeviceEdit(manufacturerByDevice);

        model.addAttribute("manufacturerByDeviceEdit", manufacturerByDeviceEdit);

        return thisUrl + "/edit";
    }

    /**
     * 장치 및 편의시설 설정 > 장치별 제조사 관리 > 장치별 제조사 수정
     * @param manufacturerByDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "editAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String editAction(ManufacturerByDevice manufacturerByDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        manufacturerByDevice.setEditerId(session.getUserId());
        manufacturerByDevice.setHouscplxCdDB(request.getParameter("houscplxCdDB"));
        manufacturerByDevice.setDeviceTpCdDB(request.getParameter("deviceTpCdDB"));
        manufacturerByDevice.setDeviceMfCdDB(request.getParameter("deviceMfCdDB"));

        manufacturerByDeviceService.updateManufacturerByDevice(manufacturerByDevice);

        return "redirect:/" + thisUrl + "/list";
    }
}