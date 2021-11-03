package com.daewooenc.pips.admin.web.controller.servermgmt;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.service.authorization.UserGroupService;
import com.daewooenc.pips.admin.core.service.common.CommonDataService;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.common.WebConsts;
import com.daewooenc.pips.admin.web.domain.dto.common.CommCodeDetail;
import com.daewooenc.pips.admin.web.domain.dto.servermgmt.ServerMgmt;
import com.daewooenc.pips.admin.web.domain.dto.servermgmt.ServerMgmtCondition;
import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import com.daewooenc.pips.admin.web.domain.vo.nmas.MasDeviceVo;
import com.daewooenc.pips.admin.web.domain.vo.servermgmt.ServerConfCtrVo;
import com.daewooenc.pips.admin.web.domain.vo.servermgmt.ServerDataSendCtrVo;
import com.daewooenc.pips.admin.web.domain.vo.servermgmt.ServerMgmtVo;
import com.daewooenc.pips.admin.web.service.common.CommCodeService;
import com.daewooenc.pips.admin.web.service.household.HouseholdService;
import com.daewooenc.pips.admin.web.service.servermgmt.ServerMgmtService;
import com.daewooenc.pips.admin.web.util.HTTPClientUtil;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-08-07       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-07
 **/
@Controller
@RequestMapping(value = "/cm/homenet/info")
public class ServerMgmtController {
    /**
     * 로그 출력.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 기본 주소.
     */
    private String thisUrl = "cm/homenet/info";


    /**
     * UserGroupService Autowired.
     */
    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private CommonDataService commonDataService;

    @Autowired
    private ServerMgmtService serverMgmtService;

    @Autowired
    private CommCodeService commCodeService;

    @Autowired
    HouseholdService householdService;

    @Autowired
    private XSSUtil xssUtil;

    private @Value("${pips.serviceServer.url}") String serviceServerURL;
    // 데이터 전송 여부 설정 제어
    private @Value("${pips.serviceServer.path.hmnet.conf.send}") String hmnetConfSendPath;

    // 홈넷 설정 제어
    private @Value("${pips.serviceServer.path.hmnet.conf}") String serverConfURL;

    // 홈넷 서버 사용 여부
    private @Value("${pips.serviceServer.path.hmnet.useyn}") String hmnetUseynPath;

    private @Value("${pips.serviceServer.auth}") String serviceServerAuth;


    private @Value("${pips.masServer.url}") String masServerURL;
    private @Value("${pips.masServer.auth}") String masServerAuth;
    private @Value("${pips.masServer.device.path}") String masDevicePath;
    private @Value("${pips.gatewayServer.url}") String gatewayURL;


    /**
     * 등록 화면.
     *
     * @param serverMgmtVo 서버 정보
     * @param model        Model
     * @param request      HttpServletRequest
     * @return String
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String insert(
            ServerMgmtVo serverMgmtVo,
            Model model,
            HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        model.addAttribute("listUserGroup", userGroupService.list(session.getUserGroupLevel()));
        List<HashMap<String, String>> bizCo_code = commonDataService.listCommonCode(WebConsts.BIZCO_CD);
        List<HashMap<String, String>> svr_type_code = commonDataService.listCommonCode(WebConsts.SVR_TP_CD);
        model.addAttribute("bizCo_code", bizCo_code);
        model.addAttribute("svr_type_code", svr_type_code);

        return thisUrl + "/insert";
    }

    /**
     * 등록
     *
     * @param serverMgmtVo 서버 정보
     * @param model        Model
     * @param request      HttpServletRequest
     * @return String
     */
    @RequestMapping(value = "insertAction", method = RequestMethod.POST)
    public String insertAction(ServerMgmtVo serverMgmtVo, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        masServerURL = xssUtil.replaceAll(StringUtils.defaultString(masServerURL));
        masDevicePath = xssUtil.replaceAll(StringUtils.defaultString(masDevicePath));

        String strNMasURL = masServerURL + masDevicePath;
        String resultUrl = "redirect:/cm/homenet/info/list";
        boolean bExit = false;
        String strSn = "";
        List<HashMap<String, String>> bizCo_code = commonDataService.listCommonCode(WebConsts.BIZCO_CD);
        List<HashMap<String, String>> svrTypeCode = commonDataService.listCommonCode(WebConsts.SVR_TP_CD);

        model.addAttribute("bizCo_code", bizCo_code);
        model.addAttribute("svr_type_code", svrTypeCode);

        if (session != null) {
            model.addAttribute("listUserGroup", userGroupService.list(session.getUserGroupLevel()));
        }

        if (serverMgmtVo != null) {
            logger.debug("JsonUtil.toJson(serverMgmtVo)=" + JsonUtil.toJson(serverMgmtVo));
        } else {
            logger.debug("serverMgmtVo is null");
        }

        String svrTpCd = xssUtil.replaceAll(StringUtils.defaultString(serverMgmtVo.getSvrTpCd()));
        svrTpCd = StringUtils.defaultIfEmpty(svrTpCd, "");

        // 서버 유형 코드 체크
        for (int i = 0; i < svrTypeCode.size(); i++) {
            HashMap<String, String> common_code = svrTypeCode.get(i);

            logger.debug("common_code="+common_code);
            logger.debug("common_code.get(\"UNIFY_SVR\")="+common_code.get("UNIFY_SVR"));
            logger.debug("common_code.get(\"ID\")="+common_code.get("ID"));
            logger.debug("==================================common_code.get(serverMgmtVo.getSvrTpCd())="+common_code.get(serverMgmtVo.getSvrTpCd()));
            logger.debug("==================================common_code.containsKey(serverMgmtVo.getSvrTpCd())="+common_code.containsKey(serverMgmtVo.getSvrTpCd()));
            if (common_code.get("ID").equals(svrTpCd)) {
                bExit = true;
                break;
            }
        }

        // 서버 유형 코드 미 존재
        if (!bExit) {
            logger.error("Not Allowed Server Type Code : "+serverMgmtVo.getSvrTpCd());
            model.addAttribute("resultMsg", "Not Allowed Server Type Code : "+serverMgmtVo.getSvrTpCd());
            resultUrl = thisUrl + "/insert";
            return resultUrl;

        }

        bExit = false;

        String bizcoCd = xssUtil.replaceAll(StringUtils.defaultString(serverMgmtVo.getBizcoCd()));
        bizcoCd = StringUtils.defaultIfEmpty(bizcoCd, "");

        for (int i = 0; i < bizCo_code.size(); i++) {
            HashMap<String, String> common_code = bizCo_code.get(i);
            logger.debug("==================================common_code.get(serverMgmtVo.getBizcoCd())="+common_code.get(serverMgmtVo.getBizcoCd()));
            logger.debug("==================================common_code.get(ID)="+common_code.get("ID"));
            logger.debug("==================================common_code.containsKey(serverMgmtVo.getBizcoCd())="+common_code.containsKey(serverMgmtVo.getBizcoCd()));

            if (common_code.get("ID").equals(bizcoCd)) {
                bExit = true;
                break;
            }
        }

        logger.debug("==================================serverMgmtVo.getSvrTpCd()=["+svrTpCd+"]");
        logger.debug("==================================serverMgmtVo.getBizcoCd()=["+bizcoCd+"]");

        // 홈넷사 코드 미 존재
        if (!bExit) {
            logger.error("Not Allowed HomeNet Code : "+serverMgmtVo.getBizcoCd());
            model.addAttribute("resultMsg", "Not Allowed Server Type Code : "+ bizcoCd);
            resultUrl = thisUrl + "/insert";
            return resultUrl;
        }

        String urlCont = xssUtil.replaceAll(StringUtils.defaultString(serverMgmtVo.getUrlCont()));
        urlCont = StringUtils.defaultIfEmpty(urlCont, "");

        // 홈넷 서버 URL 미 입력
        if ("".equals(urlCont) || urlCont == null) {
            logger.error("HomeNet Server URL is NULL");
            model.addAttribute("resultMsg", "HomeNet Server URL is NULL");
            resultUrl = thisUrl + "/insert";
            return resultUrl;
        } else {
            String[] arrProtocol = urlCont.split("://");
            if (arrProtocol.length >= 1) {
                // HTTP or HTTPS가 아닐 경우 에러
                if (!"HTTP".equalsIgnoreCase(arrProtocol[0]) && !"HTTPS".equalsIgnoreCase(arrProtocol[0])) {
                    logger.error("Not Allowed Protocol. Allow Protocol is HTTP/HTTPS Input Data :"+arrProtocol[0]);
                    resultUrl = thisUrl + "/insert";
                    model.addAttribute("resultMsg", "Not Allowed Protocol. Allow Protocol is HTTP/HTTPS Input Data :"+arrProtocol[0]);
                    return resultUrl;
                }
            } else {
                logger.error("Not Allowed URL Format.  Input Data :"+arrProtocol[0]);
                model.addAttribute("resultMsg", "Not Allowed URL Format.  Input Data :"+arrProtocol[0]);
                // URL 포맷 불일치
                resultUrl = thisUrl + "/insert";
                return resultUrl;
            }
        }

        strSn = Calendar.getInstance().getTime().getTime() + "";
        serverMgmtVo.setSerlNo(strSn);
        MasDeviceVo masDeviceVo = new MasDeviceVo(WebConsts.DEVICE_PROTOCOL, gatewayURL, svrTpCd, bizcoCd, strSn);
        List<MasDeviceVo> masDeviceVoList = new ArrayList<MasDeviceVo>();
        masDeviceVoList.add(masDeviceVo);
        // Device 등록 제어 호출하여 성공 시 등록
        HTTPClientUtil httpClient = new HTTPClientUtil();

        masServerAuth = xssUtil.replaceAll(StringUtils.defaultString(masServerAuth));
        HttpResult httpResult = httpClient.sendData(strNMasURL, JsonUtil.toJson(masDeviceVoList), masServerAuth);
        if (httpResult != null && httpResult.getStatus().equals("201")) {
            try {
                List<MasDeviceVo> deviceRegiResult = JsonUtil.toObjectList(httpResult.getMessage(), MasDeviceVo.class);
                for (MasDeviceVo masDeviceVoResult : deviceRegiResult) {
                    String hmnetId = xssUtil.replaceAll(StringUtils.defaultString(masDeviceVoResult.getDeviceId()));
                    String hmnetKeyCd = xssUtil.replaceAll(StringUtils.defaultString(masDeviceVoResult.getDeviceKey()));

                    serverMgmtVo.setHmnetId(hmnetId);
                    serverMgmtVo.setHmnetKeyCd(hmnetKeyCd);
                    if (serverMgmtService.insert(serverMgmtVo)) {
                        model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.save.result"));
                    } else {
                        model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.error.result"));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                model.addAttribute("resultMsg", "Internal Server Error");
            }
        }
        // N-MAS에 등록 실패
        else {
            model.addAttribute("svr_type_code", svrTypeCode);
            resultUrl = thisUrl + "/insert";
            model.addAttribute("resultMsg", "N-MAS Regi Fail");
            return resultUrl;
        }
        model.addAttribute("serverInfo", serverMgmtVo);
        return resultUrl;
    }

    /**
     * 수정
     *
     * @param serverMgmtVo 서버 정보
     * @param model        Model
     * @param request      HttpServletRequest
     * @return String
     */
    @RequestMapping(value = "update", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public String update(@RequestBody ServerMgmtVo serverMgmtVo, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String resultUrl = thisUrl + "/list";

        if (session != null) {
            model.addAttribute("listUserGroup", userGroupService.list(session.getUserGroupLevel()));
        }

        if (serverMgmtVo != null) {
            logger.debug("JsonUtil.toJson(serverMgmtVo)=" + JsonUtil.toJson(serverMgmtVo));
        } else {
            logger.debug("serverMgmtVo is null");
            resultUrl = thisUrl + "/update";
            return resultUrl;
        }

        String hmnetId = xssUtil.replaceAll(StringUtils.defaultString(serverMgmtVo.getHmnetId()));
        hmnetId = StringUtils.defaultIfEmpty(hmnetId, "");

        // 홈넷 서버 ID 체크
        if ("".equals(hmnetId) || hmnetId == null) {
            logger.error("HomeNet Server ID is NULL");
            model.addAttribute("resultMsg", "HomeNet Server ID is NULL");
            resultUrl = thisUrl + "/update";
            return resultUrl;
        }

        ServerMgmt serverMgmt = serverMgmtService.getServerMgmt(hmnetId);
        model.addAttribute("serverInfo", serverMgmt);
        return resultUrl;
    }
    /**
     * 수정
     *
     * @param serverMgmtVo 서버 정보
     * @param model        Model
     * @param request      HttpServletRequest
     * @return String
     */
    @RequestMapping(value = "updateAction", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public String updateAction(@RequestBody ServerMgmtVo serverMgmtVo, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String resultUrl = thisUrl + "/list";

        if (session != null) {
            model.addAttribute("listUserGroup", userGroupService.list(session.getUserGroupLevel()));
        }

        if (serverMgmtVo != null) {
            logger.debug("JsonUtil.toJson(serverMgmtVo)=" + JsonUtil.toJson(serverMgmtVo));
        } else {
            logger.debug("serverMgmtVo is null");
            resultUrl = thisUrl + "/update";
            return resultUrl;
        }

        String urlCont = xssUtil.replaceAll(StringUtils.defaultString(serverMgmtVo.getUrlCont()));
        urlCont = StringUtils.defaultIfEmpty(urlCont, "");

        // 홈넷 서버 URL 미 입력
        if ("".equals(urlCont) || urlCont == null) {
            logger.error("HomeNet Server URL is NULL");
            model.addAttribute("resultMsg", "HomeNet Server URL is NULL");
            resultUrl = thisUrl + "update";
            return resultUrl;
        } else {
            String[] arrProtocol = urlCont.split("://");
            if (arrProtocol.length >= 1) {
                // HTTP or HTTPS가 아닐 경우 에러
                if (!"HTTP".equalsIgnoreCase(arrProtocol[0]) && !"HTTPS".equalsIgnoreCase(arrProtocol[0])) {
                    logger.error("Not Allowed Protocol. Allow Protocol is HTTP/HTTPS Input Data :"+arrProtocol[0]);
                    resultUrl = thisUrl + "/update";
                    model.addAttribute("resultMsg", "Not Allowed Protocol. Allow Protocol is HTTP/HTTPS Input Data :"+arrProtocol[0]);
                    return resultUrl;
                }
            } else {
                logger.error("Not Allowed URL Format.  Input Data :"+arrProtocol[0]);
                model.addAttribute("resultMsg", "Not Allowed URL Format.  Input Data :"+arrProtocol[0]);
                // URL 포맷 불일치
                resultUrl = thisUrl + "/update";
                return resultUrl;
            }
        }

        String hmnetId = xssUtil.replaceAll(StringUtils.defaultString(serverMgmtVo.getHmnetId()));
        hmnetId = StringUtils.defaultIfEmpty(hmnetId, "");

        // 홈넷 서버 ID 체크
        if ("".equals(hmnetId) || hmnetId == null) {
            logger.error("HomeNet Server ID is NULL");
            model.addAttribute("resultMsg", "HomeNet Server URL is NULL");
            resultUrl = thisUrl + "/update";
            return resultUrl;
        }

        if (serverMgmtService.update(serverMgmtVo)) {
            model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.save.result"));
        } else {
            model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.error.result"));
        }
        model.addAttribute("serverInfo", serverMgmtVo);
        return resultUrl;
    }

    /**
     * List.
     *
     * @param model the model
     * @param request the request
     * @return the string
     *
     * 설명 : 조회 페이지로 이동
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(ServerMgmtCondition serverMgmtCondition,Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        model.addAttribute("homenetList",serverMgmtService.listServerMgmt(serverMgmtCondition));
        CommCodeDetail commCodeDetail = new CommCodeDetail();

        commCodeDetail.setCommCdGrpCd("BIZCO_CD");
        model.addAttribute("bizcocdList", commCodeService.getCommCodeList(commCodeDetail));

        String svrTpCd = xssUtil.replaceAll(StringUtils.defaultString(serverMgmtCondition.getSvrTpCd()));
        String bizcoCd = xssUtil.replaceAll(StringUtils.defaultString(serverMgmtCondition.getBizcoCd()));
        String stsCd = xssUtil.replaceAll(StringUtils.defaultString(serverMgmtCondition.getStsCd()));

        model.addAttribute("svrTpCd", StringUtils.defaultIfEmpty(svrTpCd,"all"));
        model.addAttribute("bizcoCd", StringUtils.defaultIfEmpty(bizcoCd,"all"));
        model.addAttribute("stsCd", StringUtils.defaultIfEmpty(stsCd,"all"));

        return thisUrl + "/list";
    }

    //홈넷서버 상세페이지
    @RequestMapping(value = "view", method = {RequestMethod.GET, RequestMethod.POST})
    public String Detail(String homenetId, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String hmnetId = xssUtil.replaceAll(StringUtils.defaultString(homenetId));
        hmnetId = StringUtils.defaultIfEmpty(hmnetId, "");

        model.addAttribute("homenetDetail",serverMgmtService.getServerMgmt(hmnetId));

        return thisUrl + "/view";
    }

    //홈넷서버 수정페이지
    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String Modify(String homenetId, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String hmnetId = xssUtil.replaceAll(StringUtils.defaultString(homenetId));
        hmnetId = StringUtils.defaultIfEmpty(hmnetId, "");

        model.addAttribute("homenetDetail",serverMgmtService.getServerMgmt(hmnetId));

        return thisUrl + "/edit";
    }

    //홈넷서버 도메인, 홈넷서버이름 수정
    @RequestMapping(value = "editAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String EditAction(ServerMgmtVo serverMgmtVo, Model model, HttpServletRequest request) {
        String resultUrl = "redirect:/cm/homenet/info/list";
        SessionUser session = SessionUtil.getSessionUser(request);
        serverMgmtService.update(serverMgmtVo);

        return resultUrl;
    }

    //confControlUpdateServerMgmt
    //Keep alive, 장치상태전송주기, 제어타임아웃전송주기 수정
    @RequestMapping(value = "confeditAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String ConfEditAction(ServerConfCtrVo serverCtrVo, Model model, HttpServletRequest request){
        String resultUrl = "redirect:/cm/homenet/info/edit";
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        serverCtrVo.setAdminId(adminId);
        serverMgmtService.confControlUpdate(serverCtrVo);

        return resultUrl;
    }

    //홈넷서버 등록페이지
    @RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public String Add(String homenetId,Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        CommCodeDetail commCodeDetail = new CommCodeDetail();

        commCodeDetail.setCommCdGrpCd("BIZCO_CD");
        model.addAttribute("bizcocdList", commCodeService.getCommCodeList(commCodeDetail));


        return thisUrl + "/add";
    }


    /**
     * 조회 처리.
     *
     * @param condition the condition
     * @param model the model
     * @param request the request
     * @return the string
     */
    @RequestMapping(value = "listAction", method = RequestMethod.POST)
    public String listAction(@RequestBody ServerMgmtCondition condition, Model model, HttpServletRequest request) {
        //paging을 위한 DTO를 생성
        List<ServerMgmt> list = serverMgmtService.listServerMgmt(condition);

        //DTO를 결과 model에 저장
        model.addAttribute("listServerMgmt", list);
        model.addAttribute("condition", condition);

        return thisUrl + "/list";
    }


}