package com.daewooenc.pips.admin.web.controller.system.device;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.domain.dto.device.SystemDevice;
import com.daewooenc.pips.admin.web.service.system.device.DeviceService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 장치 설정 관련 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-11-23      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-11-23
 **/
@Controller
@RequestMapping("/cm/system/device")
public class DeviceController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/device";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private DeviceService deviceService;


    /**
     * 장치 및 편의 시설 관리 > 장치 설정 > 장치 설정 목록 (단지별)
     * @param systemDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String deviceList(SystemDevice systemDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        model.addAttribute("houscplxCd", systemDevice.getHouscplxCd());
        model.addAttribute("houscplxNm", systemDevice.getHouscplxNm());

        return thisUrl + "/list";
    }
    /**
     * 장치 및 편의 시설 관리 > 장치 설정 > 장치 설정 목록 (단지별)
     * @param systemDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "search", method = {RequestMethod.GET, RequestMethod.POST})
    public String deviceSearch(SystemDevice systemDevice, Model model, HttpServletRequest request, @RequestParam(value="houscplxCd", required=false) String houscplxCd) {
        SessionUser session = SessionUtil.getSessionUser(request);

        if(houscplxCd != null && !"".equals(houscplxCd)){
            systemDevice.setHouscplxCd(houscplxCd);
        }

        List<SystemDevice> deviceList = deviceService.getSystemDeviceList(systemDevice);

        model.addAttribute("deviceList", deviceList);
        model.addAttribute("houscplxCd", systemDevice.getHouscplxCd());
        model.addAttribute("houscplxNm", systemDevice.getHouscplxNm());

        return thisUrl + "/list";
    }

    /**
     * 장치 및 편의 시설 관리 > 장치 설정 > 장치 설정 수정 (단지별)
     * @param systemDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String deviceEdit(SystemDevice systemDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        systemDevice.setHouscplxCd(request.getParameter("houscplxCd_"));

        SystemDevice deviceDetail = deviceService.getSystemDeviceEdit(systemDevice);

        model.addAttribute("deviceDetail", deviceDetail);

        model.addAttribute("houscplxCd", systemDevice.getHouscplxCd());
        model.addAttribute("houscplxCd_search", request.getParameter("houscplxCd_search"));

        return thisUrl + "/edit";
    }

    /**
     * 장치 및 편의 시설 관리 > 장치 설정 > 장치 설정 수정 > 수정하기 (단지별)
     * @param systemDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "editAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String editAction(SystemDevice systemDevice, Model model, HttpServletRequest request, RedirectAttributes redirect) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String deviceArray = request.getParameter("deviceArray");
        String[] deviceList = deviceArray.split(",");

        for(int i=0; i<deviceList.length; i++){
            int idx = deviceList[i].indexOf("/");
            String deviceTpCd = deviceList[i].substring(0, idx);
            String screenYn = deviceList[i].substring(idx+1);
            systemDevice.setDeviceTpCd(deviceTpCd);
            systemDevice.setScreenYn(screenYn);
            deviceService.updateDevice(systemDevice);
            deviceService.updateDeviceHshold(systemDevice);
        }

        redirect.addAttribute("houscplxCd", request.getParameter("houscplxCd_search"));

        return "redirect:/" + thisUrl + "/search";
    }


    /**
     * 장치 및 편의 시설 관리 > 장치 설정 > 장치 설정 목록 최초 접근시 (세대별)
     * @param systemDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "hsholdList", method = {RequestMethod.GET, RequestMethod.POST})
    public String deviceHsholdList(SystemDevice systemDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        return thisUrl + "/hsholdList";
    }

    /**
     * 장치 및 편의 시설 관리 > 장치 설정 > 장치 설정 목록 검색시 (세대별)
     * @param systemDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "hsholdSearch", method = {RequestMethod.GET, RequestMethod.POST})
    public String deviceHsholdSearch(SystemDevice systemDevice, Model model, HttpServletRequest request, @RequestParam(value="houscplxCd", required=false) String houscplxCd, @RequestParam(value="houscplxNm", required=false) String houscplxNm, @RequestParam(value="dongNo", required=false) String dongNo, @RequestParam(value="hoseNo", required=false) String hoseNo) {
        SessionUser session = SessionUtil.getSessionUser(request);

        List<SystemDevice> deviceList = deviceService.getSystemDeviceHsholdList(systemDevice);

        //조명
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getLights();
            int Ycnt = deviceList.get(i).getLightsY();
            int Ncnt = deviceList.get(i).getLightsN();

            if(Cnt == 0){
                deviceList.get(i).setLightsYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setLightsYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setLightsYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setLightsYn("일부");
            }
        }

        //밸브
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getGaslock();
            int Ycnt = deviceList.get(i).getGaslockY();
            int Ncnt = deviceList.get(i).getGaslockN();

            if(Cnt == 0){
                deviceList.get(i).setGaslockYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setGaslockYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setGaslockYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setGaslockYn("일부");
            }
        }

        //에어컨
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getAircon();
            int Ycnt = deviceList.get(i).getAirconY();
            int Ncnt = deviceList.get(i).getAirconN();

            if(Cnt == 0){
                deviceList.get(i).setAirconYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setAirconYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setAirconYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setAirconYn("일부");
            }
        }

        //난방
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getHeating();
            int Ycnt = deviceList.get(i).getHeatingY();
            int Ncnt = deviceList.get(i).getHeatingN();

            if(Cnt == 0){
                deviceList.get(i).setHeatingYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setHeatingYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setHeatingYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setHeatingYn("일부");
            }
        }

        //환기
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getVentilator();
            int Ycnt = deviceList.get(i).getVentilatorY();
            int Ncnt = deviceList.get(i).getVentilatorN();

            if(Cnt == 0){
                deviceList.get(i).setVentilatorYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setVentilatorYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setVentilatorYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setVentilatorYn("일부");
            }
        }

        //대기전력
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getSmartConsent();
            int Ycnt = deviceList.get(i).getSmartConsentY();
            int Ncnt = deviceList.get(i).getSmartConsentN();

            if(Cnt == 0){
                deviceList.get(i).setSmartConsentYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setSmartConsentYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setSmartConsentYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setSmartConsentYn("일부");
            }
        }

        //전동커튼
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getCurtain();
            int Ycnt = deviceList.get(i).getCurtainY();
            int Ncnt = deviceList.get(i).getCurtainN();

            if(Cnt == 0){
                deviceList.get(i).setCurtainYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setCurtainYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setCurtainYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setCurtainYn("일부");
            }
        }

        //일괄스위치
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getLightSwitch();
            int Ycnt = deviceList.get(i).getLightSwitchY();
            int Ncnt = deviceList.get(i).getLightSwitchN();

            if(Cnt == 0){
                deviceList.get(i).setLightSwitchYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setLightSwitchYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setLightSwitchYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setLightSwitchYn("일부");
            }
        }

        String dongNo_ = StringUtils.defaultIfEmpty(request.getParameter("dongNo"),"all");
        String hoseNo_ = StringUtils.defaultIfEmpty(request.getParameter("hoseNo"),"all");

        if(houscplxCd != null && houscplxCd != ""){
            systemDevice.setHouscplxCd(houscplxCd);
        }
        if(houscplxNm != null && houscplxNm != ""){
            systemDevice.setHouscplxNm(houscplxNm);
        }
        if(dongNo != null && dongNo != ""){
            dongNo_ = dongNo;
        }
        if(hoseNo != null && hoseNo != ""){
            hoseNo_ = hoseNo;
        }

        model.addAttribute("deviceList", deviceList);
        model.addAttribute("houscplxCd", systemDevice.getHouscplxCd());
        model.addAttribute("houscplxNm", systemDevice.getHouscplxNm());
        model.addAttribute("dongNo", dongNo_);
        model.addAttribute("hoseNo", hoseNo_);
        model.addAttribute("searchingYn", "Y");

        return thisUrl + "/hsholdList";
    }

    /**
     * 장치 및 편의 시설 관리 > 장치 설정 > 장치 설정 수정 (세대별)
     * @param systemDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "hsholdEdit", method = {RequestMethod.GET, RequestMethod.POST})
    public String deviceHsholdEdit(SystemDevice systemDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        systemDevice.setHouscplxCd(request.getParameter("houscplxCd_"));
        systemDevice.setDongNo(request.getParameter("dongNo_"));
        systemDevice.setHoseNo(request.getParameter("hoseNo_"));

        List<SystemDevice> deviceDetail = deviceService.getSystemDeviceHsholdList(systemDevice);

        //조명
        for(int i=0; i<deviceDetail.size(); i++){
            if(deviceDetail.get(i).getLights() == 0){
                deviceDetail.get(i).setLightsYn("X");
            }else if(deviceDetail.get(i).getLightsY() > 0){
                deviceDetail.get(i).setLightsYn("Y");
            }else{
                deviceDetail.get(i).setLightsYn("N");
            }
        }

        //밸브
        for(int i=0; i<deviceDetail.size(); i++) {
            if (deviceDetail.get(i).getGaslock() == 0) {
                deviceDetail.get(i).setGaslockYn("X");
            } else if (deviceDetail.get(i).getGaslockY() > 0) {
                deviceDetail.get(i).setGaslockYn("Y");
            } else {
                deviceDetail.get(i).setGaslockYn("N");
            }
        }

        //에어컨
        for(int i=0; i<deviceDetail.size(); i++) {
            if (deviceDetail.get(i).getAircon() == 0) {
                deviceDetail.get(i).setAirconYn("X");
            } else if (deviceDetail.get(i).getAirconY() > 0) {
                deviceDetail.get(i).setAirconYn("Y");
            } else {
                deviceDetail.get(i).setAirconYn("N");
            }
        }

        //난방
        for(int i=0; i<deviceDetail.size(); i++) {
            if (deviceDetail.get(i).getHeating() == 0) {
                deviceDetail.get(i).setHeatingYn("X");
            } else if (deviceDetail.get(i).getHeatingY() > 0) {
                deviceDetail.get(i).setHeatingYn("Y");
            } else {
                deviceDetail.get(i).setHeatingYn("N");
            }
        }

        //환기
        for(int i=0; i<deviceDetail.size(); i++) {
            if (deviceDetail.get(i).getVentilator() == 0) {
                deviceDetail.get(i).setVentilatorYn("X");
            } else if (deviceDetail.get(i).getVentilatorY() > 0) {
                deviceDetail.get(i).setVentilatorYn("Y");
            } else {
                deviceDetail.get(i).setVentilatorYn("N");
            }
        }

        //대기전력
        for(int i=0; i<deviceDetail.size(); i++) {
            if (deviceDetail.get(i).getSmartConsent() == 0) {
                deviceDetail.get(i).setSmartConsentYn("X");
            } else if (deviceDetail.get(i).getSmartConsentY() > 0) {
                deviceDetail.get(i).setSmartConsentYn("Y");
            } else {
                deviceDetail.get(i).setSmartConsentYn("N");
            }
        }

        //전동커튼
        for(int i=0; i<deviceDetail.size(); i++) {
            if (deviceDetail.get(i).getCurtain() == 0) {
                deviceDetail.get(i).setCurtainYn("X");
            } else if (deviceDetail.get(i).getCurtainY() > 0) {
                deviceDetail.get(i).setCurtainYn("Y");
            } else {
                deviceDetail.get(i).setCurtainYn("N");
            }
        }

        //일괄스위치
        for(int i=0; i<deviceDetail.size(); i++) {
            if (deviceDetail.get(i).getLightSwitch() == 0) {
                deviceDetail.get(i).setLightSwitchYn("X");
            } else if (deviceDetail.get(i).getLightSwitchY() > 0) {
                deviceDetail.get(i).setLightSwitchYn("Y");
            } else {
                deviceDetail.get(i).setLightSwitchYn("N");
            }
        }

        model.addAttribute("deviceDetail", deviceDetail);
        model.addAttribute("houscplxCd", request.getParameter("houscplxCd_"));
        model.addAttribute("houscplxNm", request.getParameter("houscplxNm__"));
        model.addAttribute("dongNo", request.getParameter("dongNo_"));
        model.addAttribute("hoseNo", request.getParameter("hoseNo_"));

        return thisUrl + "/hsholdEdit";
    }

    /**
     * 장치 및 편의 시설 관리 > 장치 설정 > 장치 설정 수정에서 리스트로 이동 (세대별)
     * @param systemDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "hsholdEditGoList", method = {RequestMethod.GET, RequestMethod.POST})
    public String deviceHsholdEditGoList(SystemDevice systemDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String houscplxCd = request.getParameter("houscplxCd_");
        String houscplxNm = request.getParameter("houscplxNm_");
        String dongNo = StringUtils.defaultIfEmpty(request.getParameter("dongNo_"),"all");
        String hoseNo = StringUtils.defaultIfEmpty(request.getParameter("hoseNo_"),"all");

        if(houscplxCd != null && houscplxCd != ""){
            systemDevice.setHouscplxCd(houscplxCd);
        }
        if(houscplxNm != null && houscplxNm != ""){
            systemDevice.setHouscplxNm(houscplxNm);
        }
        systemDevice.setDongNo(dongNo);
        systemDevice.setHoseNo(hoseNo);

        List<SystemDevice> deviceList = deviceService.getSystemDeviceHsholdList(systemDevice);

        //조명
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getLights();
            int Ycnt = deviceList.get(i).getLightsY();
            int Ncnt = deviceList.get(i).getLightsN();

            if(Cnt == 0){
                deviceList.get(i).setLightsYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setLightsYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setLightsYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setLightsYn("일부");
            }
        }

        //밸브
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getGaslock();
            int Ycnt = deviceList.get(i).getGaslockY();
            int Ncnt = deviceList.get(i).getGaslockN();

            if(Cnt == 0){
                deviceList.get(i).setGaslockYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setGaslockYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setGaslockYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setGaslockYn("일부");
            }
        }

        //에어컨
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getAircon();
            int Ycnt = deviceList.get(i).getAirconY();
            int Ncnt = deviceList.get(i).getAirconN();

            if(Cnt == 0){
                deviceList.get(i).setAirconYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setAirconYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setAirconYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setAirconYn("일부");
            }
        }

        //난방
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getHeating();
            int Ycnt = deviceList.get(i).getHeatingY();
            int Ncnt = deviceList.get(i).getHeatingN();

            if(Cnt == 0){
                deviceList.get(i).setHeatingYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setHeatingYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setHeatingYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setHeatingYn("일부");
            }
        }

        //환기
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getVentilator();
            int Ycnt = deviceList.get(i).getVentilatorY();
            int Ncnt = deviceList.get(i).getVentilatorN();

            if(Cnt == 0){
                deviceList.get(i).setVentilatorYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setVentilatorYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setVentilatorYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setVentilatorYn("일부");
            }
        }

        //대기전력
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getSmartConsent();
            int Ycnt = deviceList.get(i).getSmartConsentY();
            int Ncnt = deviceList.get(i).getSmartConsentN();

            if(Cnt == 0){
                deviceList.get(i).setSmartConsentYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setSmartConsentYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setSmartConsentYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setSmartConsentYn("일부");
            }
        }

        //전동커튼
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getCurtain();
            int Ycnt = deviceList.get(i).getCurtainY();
            int Ncnt = deviceList.get(i).getCurtainN();

            if(Cnt == 0){
                deviceList.get(i).setCurtainYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setCurtainYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setCurtainYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setCurtainYn("일부");
            }
        }

        //일괄스위치
        for(int i=0; i<deviceList.size(); i++){
            int Cnt = deviceList.get(i).getLightSwitch();
            int Ycnt = deviceList.get(i).getLightSwitchY();
            int Ncnt = deviceList.get(i).getLightSwitchN();

            if(Cnt == 0){
                deviceList.get(i).setLightSwitchYn("");
            }else if((Cnt - Ycnt) == 0){
                deviceList.get(i).setLightSwitchYn("전체");
            }else if((Cnt - Ycnt) == Cnt){
                deviceList.get(i).setLightSwitchYn("미표시");
            }else if((Cnt - Ycnt) == Ncnt){
                deviceList.get(i).setLightSwitchYn("일부");
            }
        }

        model.addAttribute("deviceList", deviceList);
        model.addAttribute("houscplxCd", systemDevice.getHouscplxCd());
        model.addAttribute("houscplxNm", systemDevice.getHouscplxNm());
        model.addAttribute("dongNo", systemDevice.getDongNo());
        model.addAttribute("hoseNo", systemDevice.getHoseNo());
        model.addAttribute("searchingYn", "Y");

        return thisUrl + "/hsholdList";
    }


    /**
     * 장치 및 편의 시설 관리 > 장치 설정 > 장치 설정 수정 > 수정하기 (세대별)
     * @param systemDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "hsholdEditAction", method = RequestMethod.POST)
    @ResponseBody
    public String hsholdEditAction(SystemDevice systemDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        systemDevice.setCrerId(session.getUserId());

        String result = "fail";

        String wpadId = request.getParameter("wpadId");
        systemDevice.setWpadId(wpadId);

        //String deviceArray = request.getParameter("deviceArray");
        //String[] deviceList = deviceArray.split(",");

        String[] deviceList = request.getParameterValues("deviceArray[]");

        for(int i=0; i<deviceList.length; i++){
            int idx = deviceList[i].indexOf("/");
            String deviceTpCd = deviceList[i].substring(0, idx);
            String screenYn = deviceList[i].substring(idx+1);
            systemDevice.setDeviceTpCd(deviceTpCd);
            systemDevice.setScreenYn(screenYn);
            deviceService.updateHsholdDevice(systemDevice);
        }

        result = "success";

        return result;
    }

    /**
     * 장치 및 편의 시설 관리 > 장치 설정 > 장치 설정 수정 > 장치 설정 상세 (세대별)
     * @param systemDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "hsholdView", method = {RequestMethod.GET, RequestMethod.POST})
    public String deviceHsholdView(SystemDevice systemDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        List<SystemDevice> deviceDetail = deviceService.getDeviceHsholdDetail(systemDevice);

        int idx = systemDevice.getWpadId().indexOf(".");
        String houscplxCd = systemDevice.getWpadId().substring(0, idx);
        String dong = systemDevice.getWpadId().substring(idx+1, idx+5);
        String hose = systemDevice.getWpadId().substring(idx+6);

        model.addAttribute("houscplxNm_", deviceService.getDeviceHsholdDetailNm(houscplxCd));
        model.addAttribute("dong", dong);
        model.addAttribute("hose", hose);
        model.addAttribute("houscplxCd", request.getParameter("houscplxCd"));
        model.addAttribute("houscplxNm", request.getParameter("houscplxNm"));
        model.addAttribute("dongNo", request.getParameter("dongNo"));
        model.addAttribute("hoseNo", request.getParameter("hoseNo"));
        model.addAttribute("deviceDetail", deviceDetail);

        return thisUrl + "/hsholdView";
    }

    /**
     * 장치 및 편의 시설 관리 > 장치 설정 > 장치 설정 수정 > 장치 설정 상세 > 수정하기 (세대별)
     * @param systemDevice
     * @param request
     * @return
     */
    @RequestMapping(value = "hsholdViewAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String deviceHsholdViewAction(SystemDevice systemDevice, Model model, HttpServletRequest request, RedirectAttributes redirect) {
        SessionUser session = SessionUtil.getSessionUser(request);
        systemDevice.setCrerId(session.getUserId());

        String deviceArray = request.getParameter("deviceArray");
        String[] deviceList = deviceArray.split(",");

        for(int i=0; i<deviceList.length; i++){
            int idx = deviceList[i].indexOf("/");
            int idx2 = deviceList[i].indexOf("_");
            String deviceId = deviceList[i].substring(0, idx);
            String wpadId = deviceList[i].substring(idx+1, idx2);
            String screenYn = deviceList[i].substring(idx2+1);
            systemDevice.setDeviceId(deviceId);
            systemDevice.setWpadId(wpadId);
            systemDevice.setScreenYn(screenYn);
            deviceService.updateHsholdDeviceView(systemDevice);
        }

        redirect.addAttribute("houscplxCd", request.getParameter("houscplxCd"));
        redirect.addAttribute("houscplxNm", request.getParameter("houscplxNm"));
        redirect.addAttribute("dongNo", request.getParameter("dongNo"));
        redirect.addAttribute("hoseNo", request.getParameter("hoseNo"));

        return "redirect:/" + thisUrl + "/hsholdSearch";
    }

}