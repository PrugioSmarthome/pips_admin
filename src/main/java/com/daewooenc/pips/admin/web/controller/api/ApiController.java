package com.daewooenc.pips.admin.web.controller.api;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import com.daewooenc.pips.admin.web.service.api.ApiService;
import com.daewooenc.pips.admin.web.util.HTTPClientUtil;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Admin Web에서 Service Server로 기능 수행을 요청하는 일부 API Controller
 *
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-10-18      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-18
 **/
@Controller
@RequestMapping("/cm/api")
public class ApiController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApiService apiService;

    @Autowired
    private XSSUtil xssUtil;

    // TODO: Service Server에서 구현완료시 테스트 필요
    /**
     * 단지 커뮤니티 관리 > 공지사항 관리 > 공지사항 상세
     * Ajax 방식 -> Service Server API 호출
     *
     * 1.1 - APP Push 전송 요청
     * Admin Web 에서 서비스 서버에 App Push를 요청하고 홈넷서버에 공지사항 제어를 요청한다.
     * @link https://daewooenc.atlassian.net/wiki/spaces/PIPS/pages/26738689/1.1+-+APP+Push
     * @param request
     * @return
     */
    @RequestMapping(value = "push", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ResponseBody
    public String sendPush(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isNotEmpty(adminId)) {
            String paramNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("no")));
            String paramTpCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("tpCd")));
            String paramHouscplxList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxList")));

            String pushTargetNo = StringUtils.defaultIfEmpty(paramNo, "");
            String tpCd = StringUtils.defaultIfEmpty(paramTpCd, "");
            String houscplxList = StringUtils.defaultIfEmpty(paramHouscplxList, "");

            if (StringUtils.isEmpty(pushTargetNo)) {
                param.put("id", "no");
                param.put("msg", MessageUtil.getMessage("notEmpty.pushTargetNo.pushTargetNo"));
                params.put(param);
            }

            if (StringUtils.isEmpty(tpCd)) {
                param.put("id", "tpCd");
                param.put("msg", MessageUtil.getMessage("notEmpty.tpCd.tpCd"));
                params.put(param);
            }

            if (StringUtils.isEmpty(houscplxList)) {
                param.put("id", "houscplxList");
                param.put("msg", MessageUtil.getMessage("notEmpty.houscplxList.houscplxList"));
                params.put(param);
            }

            if (params.length() > 0) {
                result.put("status", false);
                result.put("params", params);

                return result.toString();
            }

            logger.debug("sendPush param info no: {}, tpCd: {}, houscplxList: {}", pushTargetNo, tpCd, houscplxList);

            String[] houscplxArray = houscplxList.split(",");

            List<String> houscplxArrayList = new ArrayList<>();

            for(int i=0; i<houscplxArray.length; i++) {
                houscplxArrayList.add(houscplxArray[i]);
            }

            result = apiService.sendPush(pushTargetNo, tpCd, houscplxArrayList);
        } else {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("notFound.adminId.adminId")));
        }

        return result.toString();
    }

    /**
     * 단지 관리 > 세대장치 관리 > 세대장치 목록
     * Ajax 방식 -> Service Server API 호출
     *
     * 1.2 - 장치정보 동기화
     * 세대장치 목록 페이지에서 장치정보 동기화 버튼(Admin Web에서 서비스 서버에 장치정보 동기화를 요청)을 누른다.
     * @link https://daewooenc.atlassian.net/wiki/spaces/PIPS/pages/26607633/1.2+-
     * @return
     */
    @RequestMapping(value = "device/sync", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ResponseBody
    public String sendDeviceSync(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isNotEmpty(adminId)) {
                String paramHmnetId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("hmnetId")));
                String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
                String paramDongNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("dongNo")));
                String paramHoseNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("hoseNo")));

                String hmnetId = StringUtils.defaultIfEmpty(paramHmnetId, "");
                String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, "");
                String dongNo = StringUtils.defaultIfEmpty(paramDongNo, "");
                String hoseNo = StringUtils.defaultIfEmpty(paramHoseNo, "");

                if (StringUtils.isEmpty(adminId)) {
                    param.put("id", "adminId");
                    param.put("msg", MessageUtil.getMessage("notEmpty.adminId.adminId"));
                    params.put(param);
                }

                if (StringUtils.isEmpty(hmnetId)) {
                    param.put("id", "hmnetId");
                    param.put("msg", MessageUtil.getMessage("notEmpty.hmnetId.hmnetId"));
                    params.put(param);
                }

                if (StringUtils.isEmpty(houscplxCd)) {
                    param.put("id", "houscplxCd");
                    param.put("msg", MessageUtil.getMessage("notEmpty.houscplxCd.houscplxCd"));
                    params.put(param);
                }

                if (StringUtils.isEmpty(dongNo)) {
                    param.put("id", "dongNo");
                    param.put("msg", MessageUtil.getMessage("notEmpty.dongNo.dongNo"));
                    params.put(param);
                }

                if (StringUtils.isEmpty(hoseNo)) {
                    param.put("id", "hoseNo");
                    param.put("msg", MessageUtil.getMessage("notEmpty.hoseNo.hoseNo"));
                    params.put(param);
                }

                if (houscplxCd.equals("*") && dongNo.equals("*") && hoseNo.equals("*")) {
                    param.put("id", "hmnetId");
                    param.put("msg", MessageUtil.getMessage("notAvailable.hmnetId.hmnetId"));
                    params.put(param);
                }

                if (dongNo.equals("*") && hoseNo.equals("*")) {
                    param.put("id", "hmnetId");
                    param.put("msg", MessageUtil.getMessage("notAvailable.hmnetId.hmnetId"));
                    params.put(param);
                }

                if (params.length() > 0) {
                    result.put("status", false);
                    result.put("params", params);

                    return result.toString();
                }

                logger.debug("sendDeviceSync param info adminId: {}, hmnetId: {}, houscplxCd: {}, dongNo: {}, hoseNo: {}", adminId, hmnetId, houscplxCd, dongNo, hoseNo);

                result = apiService.sendDeviceSync(adminId, hmnetId, houscplxCd, dongNo, hoseNo);
        } else {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("notFound.adminId.adminId")));
        }

        return result.toString();
    }

    /**
     * 회원 or 세대 관리 > 세대구성 신청정보
     * Ajax 방식 -> Service Server API 호출
     *
     * 1.3 - 세대 및 장치 등록 제어
     * Admin Web 에서 세대주 승인이 완료 후 세대 및 장치 등록 제어를 요청한다.
     * @link https://daewooenc.atlassian.net/wiki/spaces/PIPS/pages/28180484/1.3+-
     * @param request
     * @return
     */
    @RequestMapping(value = "device/regi", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ResponseBody
    public String sendDeviceRegi(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isNotEmpty(adminId)) {
            String paramUserList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userList")));
            String userList = StringUtils.defaultIfEmpty(paramUserList, "");

            if (StringUtils.isEmpty(userList)) {
                param.put("id", "userList");
                param.put("msg", MessageUtil.getMessage("notEmpty.userList.userList"));
                params.put(param);
            }

            if (params.length() > 0) {
                result.put("status", false);
                result.put("params", params);

                return result.toString();
            }

            logger.debug("sendDeviceRegi param info userList: {}", userList);

            result = apiService.sendDeviceRegi(adminId, userList);
        } else {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("notFound.adminId.adminId")));
        }

        return result.toString();
    }

    /**
     * 회원 or 세대 관리 > 회원정보 or 세대정보 관리
     * Ajax 방식 -> Service Server API 호출
     *
     * 1.4 - 회원/입주민 탈퇴
     * Admin Web 에서 회원/입주민 탈퇴를 요청한다.
     * @link https://daewooenc.atlassian.net/wiki/spaces/PIPS/pages/36864006/1.4+-
     * @param request
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ResponseBody
    public String sendUser(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isNotEmpty(adminId)) {
            String paramUserList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userList")));
            String userList = StringUtils.defaultIfEmpty(paramUserList, "");

            if (StringUtils.isEmpty(userList)) {
                param.put("id", "userList");
                param.put("msg", MessageUtil.getMessage("notEmpty.userList.userList"));
                params.put(param);
            }

            if (params.length() > 0) {
                result.put("status", false);
                result.put("params", params);

                return result.toString();
            }

            logger.debug("sendUser param info userList: {}", userList);

            result = apiService.sendUser(userList);
        } else {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("notFound.adminId.adminId")));
        }

        return result.toString();
    }

    /**
     * 회원 or 세대 관리 > 회원정보 or 세대정보 관리
     * Ajax 방식 -> Service Server API 호출
     *
     * 1.5 - 가족대표 변경
     * Admin Web 에서 가족대표를 변경 한다.
     * @link https://daewooenc.atlassian.net/wiki/spaces/PIPS/pages/36765741/1.5+-
     * @param request
     * @return
     */
    @RequestMapping(value = "family/represent", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ResponseBody
    public String sendFamilyRepresent(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isNotEmpty(adminId)) {
            String paramPrevUserId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("prevUserId")));
            String paramAfterUserId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("afterUserId")));

            String prevUserId = StringUtils.defaultIfEmpty(paramPrevUserId, "");
            String afterUserId = StringUtils.defaultIfEmpty(paramAfterUserId, "");

            if (StringUtils.isEmpty(prevUserId)) {
                param.put("id", "prevUserId");
                param.put("msg", MessageUtil.getMessage("notEmpty.prevUserId.prevUserId"));
                params.put(param);
            }

            if (StringUtils.isEmpty(afterUserId)) {
                param.put("id", "afterUserId");
                param.put("msg", MessageUtil.getMessage("notEmpty.afterUserId.afterUserId"));
                params.put(param);
            }

            if (params.length() > 0) {
                result.put("status", false);
                result.put("params", params);

                return result.toString();
            }

            logger.debug("sendFamilyRepresent param info prevUserId: {}, afterUserId: {}", prevUserId, afterUserId);

            result = apiService.sendFamilyRepresent(prevUserId, afterUserId);
        } else {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("notFound.adminId.adminId")));
        }

        return result.toString();
    }

    /**
     * 홈넷 서버 목록 > 홈넷 서버 정보 수정
     * Ajax 방식 -> Service Server API 호출
     *
     * 1.6 - 홈넷 설정 제어
     * Admin Web 에서 홈넷 설정 제어를 요청한다.
     * @link https://daewooenc.atlassian.net/wiki/spaces/PIPS/pages/36732985/1.6+-
     * @param request
     * @return
     */
    @RequestMapping(value = "hmnet/conf", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ResponseBody
    public String sendHmnetConf(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isNotEmpty(adminId)) {
            String paramHmnetId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("hmnetId")));
            String paramKeepAliveCycle = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("keepAliveCycle")));
            String paramDatTrnsmCycle = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("datTrnsmCycle")));
            String paramCtlExprtnCycle = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("ctlExprtnCycle")));

            String hmnetId = StringUtils.defaultIfEmpty(paramHmnetId, "");
            String keepAliveCycle = StringUtils.defaultIfEmpty(paramKeepAliveCycle, "");
            String datTrnsmCycle = StringUtils.defaultIfEmpty(paramDatTrnsmCycle, "");
            String ctlExprtnCycle = StringUtils.defaultIfEmpty(paramCtlExprtnCycle, "");

            if (StringUtils.isEmpty(adminId)) {
                param.put("id", "adminId");
                param.put("msg", MessageUtil.getMessage("notEmpty.adminId.adminId"));
                params.put(param);
            }

            if (StringUtils.isEmpty(hmnetId)) {
                param.put("id", "hmnetId");
                param.put("msg", MessageUtil.getMessage("notEmpty.hmnetId.hmnetId"));
                params.put(param);
            }

            if (StringUtils.isEmpty(keepAliveCycle)) {
                param.put("id", "keepAliveCycle");
                param.put("msg", MessageUtil.getMessage("notEmpty.keepAliveCycle.keepAliveCycle"));
                params.put(param);
            }

            if (StringUtils.isEmpty(datTrnsmCycle)) {
                param.put("id", "datTrnsmCycle");
                param.put("msg", MessageUtil.getMessage("notEmpty.datTrnsmCycle.datTrnsmCycle"));
                params.put(param);
            }

            if (StringUtils.isEmpty(ctlExprtnCycle)) {
                param.put("id", "ctlExprtnCycle");
                param.put("msg", MessageUtil.getMessage("notEmpty.ctlExprtnCycle.ctlExprtnCycle"));
                params.put(param);
            }

            if (params.length() > 0) {
                result.put("status", false);
                result.put("params", params);

                return result.toString();
            }

            logger.debug("sendHmnetConf param info adminId: {}, hmnetId: {}, keepAliveCycle: {}, datTrnsmCycle: {}, ctlExprtnCycle: {}",
                    adminId, hmnetId, keepAliveCycle, datTrnsmCycle, ctlExprtnCycle);

            result = apiService.sendHmnetConf(adminId, hmnetId, keepAliveCycle, datTrnsmCycle, ctlExprtnCycle);
        } else {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("notFound.adminId.adminId")));
        }

        return result.toString();
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지정보
     * 홈넷 서버 목록 > 홈넷 서버 정보 등록 및 수정
     * Ajax 방식 -> Service Server API 호출
     *
     * 1.7 - 데이터 전송 여부 설정 제어
     * Admin Web 에서 데이터 전송 여부 설정 제어를 요청한다.
     * @link https://daewooenc.atlassian.net/wiki/spaces/PIPS/pages/36831272/1.7+-
     * @param request
     * @return
     */
    @RequestMapping(value = "hmnet/conf/send", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ResponseBody
    public String sendHmnetConfSend(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isNotEmpty(adminId)) {
                String paramHmnetId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("hmnetId")));
                String paramTgtTp = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("tgtTp")));
                String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
                String paramTrnsmYn = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("trnsmYn")));

                String hmnetId = StringUtils.defaultIfEmpty(paramHmnetId, "");
                String tgtTp = StringUtils.defaultIfEmpty(paramTgtTp, "");
                String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, "");
                String trnsmYn = StringUtils.defaultIfEmpty(paramTrnsmYn, "");

                if (StringUtils.isEmpty(adminId)) {
                    param.put("id", "adminId");
                    param.put("msg", MessageUtil.getMessage("notEmpty.adminId.adminId"));
                    params.put(param);
                }

                if (StringUtils.isEmpty(hmnetId)) {
                    param.put("id", "hmnetId");
                    param.put("msg", MessageUtil.getMessage("notEmpty.hmnetId.hmnetId"));
                    params.put(param);
                }

                if (StringUtils.isEmpty(tgtTp)) {
                    param.put("id", "tgtTp");
                    param.put("msg", MessageUtil.getMessage("notEmpty.tgtTp.tgtTp"));
                    params.put(param);
                }

                if (tgtTp.equals("complex")) {
                    if (StringUtils.isEmpty(houscplxCd)) {
                        param.put("id", "houscplxCd");
                        param.put("msg", MessageUtil.getMessage("notEmpty.houscplxCd.houscplxCd"));
                        params.put(param);
                    }
                }

                if (StringUtils.isEmpty(trnsmYn)) {
                    param.put("id", "trnsmYn");
                    param.put("msg", MessageUtil.getMessage("notEmpty.trnsmYn.trnsmYn"));
                    params.put(param);
                }

                if (params.length() > 0) {
                    result.put("status", false);
                    result.put("params", params);

                    return result.toString();
                }

                logger.debug("sendHmnetConfSend param info adminId: {}, hmnetId: {}, tgtTp: {}, houscplxCd: {}, trnsmYn: {}", adminId, hmnetId, tgtTp, houscplxCd, trnsmYn);

            result = apiService.sendHmnetConfSend(adminId, hmnetId, tgtTp, houscplxCd, trnsmYn);
        } else {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("notFound.adminId.adminId")));
        }

        return result.toString();
    }

    // TODO: Service Server에서 구현완료시 테스트 필요
    /**
     * 단지 커뮤니티 관리 > 생활불편신고 관리 > 생활불편신고 목록 > 생활불편신고 상세_답변작성
     * Ajax 방식 -> Service Server API 호출
     *
     * 1.9 - 생활불편 신고 답변 알림
     * Admin Web 에서 생활불편 신고 답변 알림에 대한 APP Push를 요청한다.
     * @link https://daewooenc.atlassian.net/wiki/spaces/PIPS/pages/41549878/1.9+-
     * @param request
     * @return
     */
    @RequestMapping(value = "report/push", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendReportPush(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isNotEmpty(adminId)) {
            String paramBlltNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("blltNo")));
            String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userId")));

            String blltNo = StringUtils.defaultIfEmpty(paramBlltNo, "");
            String userId = StringUtils.defaultIfEmpty(paramUserId, "");

            if (StringUtils.isEmpty(blltNo)) {
                param.put("id", "blltNo");
                param.put("msg", MessageUtil.getMessage("notEmpty.blltNo.blltNo"));
                params.put(param);
            }

            if (StringUtils.isEmpty(userId)) {
                param.put("id", "userId");
                param.put("msg", MessageUtil.getMessage("notEmpty.userId.userId"));
                params.put(param);
            }

            if (params.length() > 0) {
                result.put("status", false);
                result.put("params", params);

                return result.toString();
            }

            logger.debug("sendReportPush param info blltNo: {}, userId: {}", blltNo, userId);

            result = apiService.sendReportPush(blltNo, userId);
        } else {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("notFound.adminId.adminId")));
        }

        return result.toString();
    }

    /**
     * 홈넷 서버 목록 > 홈넷 서버 정보 등록 및 수정
     * Ajax 방식 -> Service Server API 호출
     *
     * 1.11 - 홈넷 서버 사용 여부 변경 알림
     * 서비스 서버의 Cache 갱신을 위해 홈넷 서버 사용 여부 변경을 알린다
     * @param request
     * @return
     */
    @RequestMapping(value = "hmnet/useyn", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendHmnetUseyn(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isNotEmpty(adminId)) {
            String paramHmnetId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("hmnetId")));
            String paramHmnetYseYn = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("hmnetYseYn")));

            String hmnetId = StringUtils.defaultIfEmpty(paramHmnetId, "");
            String hmnetYseYn = StringUtils.defaultIfEmpty(paramHmnetYseYn, "");

            if (StringUtils.isEmpty(adminId)) {
                param.put("id", "adminId");
                param.put("msg", MessageUtil.getMessage("notEmpty.adminId.adminId"));
                params.put(param);
            }

            if (StringUtils.isEmpty(hmnetId)) {
                param.put("id", "hmnetId");
                param.put("msg", MessageUtil.getMessage("notEmpty.hmnetId.hmnetId"));
                params.put(param);
            }

            if (StringUtils.isEmpty(hmnetYseYn)) {
                param.put("id", "hmnetYseYn");
                param.put("msg", MessageUtil.getMessage("notEmpty.hmnetYseYn.hmnetYseYn"));
                params.put(param);
            }

            if (params.length() > 0) {
                result.put("status", false);
                result.put("params", params);

                return result.toString();
            }

            logger.debug("sendHmnetUseyn param info adminId: {}, hmnetId: {}, hmnetYseYn: {}", adminId, hmnetId, hmnetYseYn);

            result = apiService.sendHmnetUseyn(adminId, hmnetId, hmnetYseYn);
        } else {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("notFound.adminId.adminId")));
        }

        return result.toString();
    }

    /**
     * 단지 커뮤니티 관리 > 단지 공지사항 관리 > 단지 공지사항 목록 > 단지 공지사항 상세
     * Ajax 방식 -> Service Server API 호출
     *
     * 1.12 - 단지 공지사항 홈넷 서버 전송
     * Admin Web 에서 서비스 서버에 단지 공지사항 등록 내용을 전송 한다.
     * @link https://daewooenc.atlassian.net/wiki/spaces/PIPS/pages/51838981/1.12+-
     * @param request
     * @return
     */
    @RequestMapping(value = "houscplx/noti", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendHouscplxNoti(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isNotEmpty(adminId)) {
            String paramBlltTpDtlCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("blltTpDtlCd")));
            String paramHmnetId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("hmnetId")));
            String paramHouscplxList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxList")));
            String paramBlltNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("blltNo")));

            String blltTpDtlCd = StringUtils.defaultIfEmpty(paramBlltTpDtlCd, "");
            String hmnetId = StringUtils.defaultIfEmpty(paramHmnetId, "");
            String houscplxList = StringUtils.defaultIfEmpty(paramHouscplxList, "");
            String blltNo = StringUtils.defaultIfEmpty(paramBlltNo, "");

            if (StringUtils.isEmpty(adminId)) {
                param.put("id", "adminId");
                param.put("msg", MessageUtil.getMessage("notEmpty.adminId.adminId"));
                params.put(param);
            }

            if (StringUtils.isEmpty(blltTpDtlCd)) {
                param.put("id", "blltTpDtlCd");
                param.put("msg", MessageUtil.getMessage("notEmpty.blltTpDtlCd.blltTpDtlCd"));
                params.put(param);
            }

            if (StringUtils.isEmpty(hmnetId)) {
                param.put("id", "hmnetId");
                param.put("msg", MessageUtil.getMessage("notEmpty.hmnetId.hmnetId"));
                params.put(param);
            }

            if (StringUtils.isEmpty(houscplxList)) {
                param.put("id", "houscplxList");
                param.put("msg", MessageUtil.getMessage("notEmpty.houscplxList.houscplxList"));
                params.put(param);
            }

            if (StringUtils.isEmpty(blltNo)) {
                param.put("id", "blltNo");
                param.put("msg", MessageUtil.getMessage("notEmpty.blltNo.blltNo"));
                params.put(param);
            }

            if (params.length() > 0) {
                result.put("status", false);
                result.put("params", params);

                return result.toString();
            }

            logger.debug("sendHouscplxNoti param info adminId: {}, bllt_tp_dtl_cd: {}, hmnetId: {}, houscplx_list: {}, bllt_no: {}",
                        adminId, blltTpDtlCd, hmnetId, houscplxList, blltNo);

            result = apiService.sendHouscplxNoti(adminId, blltTpDtlCd, hmnetId, houscplxList, blltNo);
        } else {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("notFound.adminId.adminId")));
        }

        return result.toString();
    }

    /**
     * 회원 관리 > 회원정보 관리 > 회원정보 목록 > 회원정보 수정
     * 세대 관리 > 세대정보 관리 > 세대정보 목록 > 세대정보 관리 수정
     * Ajax 방식 -> Service Server API 호출
     *
     * 1.13 - 사용자 사용 여부 처리
     * Admin Web 에서 서비스 서버에 푸르지오 앱 사용자의 사용 여부 처리를 호출한다.
     * @link https://daewooenc.atlassian.net/wiki/spaces/PIPS/pages/53248086/1.13+-
     * @param request
     * @return
     */
    @RequestMapping(value = "user/use", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendUserUse(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isNotEmpty(adminId)) {
            String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userId")));
            String paramUseYn = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("useYn")));

            String userId = StringUtils.defaultIfEmpty(paramUserId, "");
            String useYn = StringUtils.defaultIfEmpty(paramUseYn, "");

            if (StringUtils.isEmpty(adminId)) {
                param.put("id", "adminId");
                param.put("msg", MessageUtil.getMessage("notEmpty.adminId.adminId"));
                params.put(param);
            }

            if (StringUtils.isEmpty(userId)) {
                param.put("id", "userId");
                param.put("msg", MessageUtil.getMessage("notEmpty.userId.userId"));
                params.put(param);
            }

            if (StringUtils.isEmpty(useYn)) {
                param.put("id", "useYn");
                param.put("msg", MessageUtil.getMessage("notEmpty.useYn.useYn"));
                params.put(param);
            }

            if (params.length() > 0) {
                result.put("status", false);
                result.put("params", params);

                return result.toString();
            }

            logger.debug("sendUserUse param info userId: {}, useYn: {}", userId, useYn);

            result = apiService.sendUserUse(userId, useYn);
        } else {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("notFound.adminId.adminId")));
        }

        return result.toString();
    }

    /**
     * 회원 관리 > 회원정보 관리 > 회원정보 목록 > 회원정보 수정
     * Ajax 방식 -> Service Server API 호출
     *
     * 1.17 - 사용자 정보 수정
     * Admin Web 에서 서비스 서버에 푸르지오 앱 사용자의 정보를 수정 요청한다.
     * @link https://daewooenc.atlassian.net/wiki/spaces/PIPS/pages/63209476/1.17+-
     * @param request
     * @return
     */
    @RequestMapping(value = "user/info", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendUserInfo(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isNotEmpty(adminId)) {
            String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userId")));
            String paramUserNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userNm")));
            String paramEmailNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("emailNm")));
            String paramMphoneNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("mphoneNo")));

            String userId = StringUtils.defaultIfEmpty(paramUserId, "");
            String userNm = StringUtils.defaultIfEmpty(paramUserNm, "");
            String emailNm = StringUtils.defaultIfEmpty(paramEmailNm, "");
            String mphoneNo = StringUtils.defaultIfEmpty(paramMphoneNo, "");

            if (StringUtils.isEmpty(adminId)) {
                param.put("id", "adminId");
                param.put("msg", MessageUtil.getMessage("notEmpty.adminId.adminId"));
                params.put(param);
            }

            if (StringUtils.isEmpty(userId)) {
                param.put("id", "userId");
                param.put("msg", MessageUtil.getMessage("notEmpty.userId.userId"));
                params.put(param);
            }

            if (StringUtils.isEmpty(userNm)) {
                param.put("id", "userNm");
                param.put("msg", MessageUtil.getMessage("notEmpty.userNm.userNm"));
                params.put(param);
            }

            if (StringUtils.isEmpty(emailNm)) {
                param.put("id", "emailNm");
                param.put("msg", MessageUtil.getMessage("notEmpty.emailNm.emailNm"));
                params.put(param);
            }

            if (StringUtils.isEmpty(mphoneNo)) {
                param.put("id", "mphoneNo");
                param.put("msg", MessageUtil.getMessage("notEmpty.mphoneNo.mphoneNo"));
                params.put(param);
            }

            if (params.length() > 0) {
                result.put("status", false);
                result.put("params", params);

                return result.toString();
            }

            logger.debug("sendUserInfo param info userId: {}, userNm: {}, emailNm: {}, mphoneNo: {}", userId, userNm, emailNm, mphoneNo);

            result = apiService.sendUserInfo(userId, userNm, emailNm, mphoneNo);
        } else {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("notFound.adminId.adminId")));
        }

        return result.toString();
    }
}