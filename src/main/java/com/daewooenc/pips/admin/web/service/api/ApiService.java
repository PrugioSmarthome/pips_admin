package com.daewooenc.pips.admin.web.service.api;

import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import com.daewooenc.pips.admin.web.util.HTTPClientUtil;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 대우건설 스마트홈 푸르지오 플랫폼용 Service Server API 호출관련 Service
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-10-21      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-21
 **/
@Service
public class ApiService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private @Value("${pips.serviceServer.auth}") String pipsServiceServerAuth;
    private @Value("${pips.serviceServer.url}") String pipsServiceServerUrl;
    private @Value("${pips.serviceServer.path.push}") String pushPath;
    private @Value("${pips.serviceServer.path.device.sync}") String deviceSyncPath;
    private @Value("${pips.serviceServer.path.device.regi}") String deviceRegiPath;
    private @Value("${pips.serviceServer.path.user}") String userPath;
    private @Value("${pips.serviceServer.path.family.represent}") String familyRepresentPath;
    private @Value("${pips.serviceServer.path.hmnet.conf}") String hmnetConfPath;
    private @Value("${pips.serviceServer.path.hmnet.conf.send}") String hmnetConfSendPath;
    private @Value("${pips.serviceServer.path.report.push}") String reportPushPath;
    private @Value("${pips.serviceServer.path.user.use}") String userUsePath;
    private @Value("${pips.serviceServer.path.hmnet.useyn}") String hmnetUseynPath;
    private @Value("${pips.serviceServer.path.houscplx.noti}") String houscplxNotiPath;
    private @Value("${pips.serviceServer.path.user.info}") String userInfoPath;
    private @Value("${pips.serviceServer.url.reserve}") String pipsServiceServerReserveUrl;
    private @Value("${pips.serviceServer.path.reserve.control}") String reserveControlPath;
    private @Value("${pips.serviceServer.path.reserve.push}") String reservePushPath;

    @Autowired
    private XSSUtil xssUtil;

    /**
     * 1.1 - APP Push 전송 요청
     * Admin Web 에서 서비스 서버에 App Push를 요청하고 홈넷서버에 공지사항 제어를 요청한다.
     * @param pushTargetNo
     * @param tpCd
     * @param houscplxList
     * @return
     */
    public JSONObject sendPush(String pushTargetNo, String tpCd, List<String> houscplxList) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("no", pushTargetNo);
            map.put("tp_cd", tpCd);
            map.put("houscplx_list", houscplxList);

            ObjectMapper om = new ObjectMapper();
            om.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            String paramData = om.writeValueAsString(map).replace("\\", "");

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerUrl));
            pushPath = xssUtil.replaceAll(StringUtils.defaultString(pushPath));

            String requestUrl = pipsServiceServerUrl + pushPath;

            HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, pipsServiceServerAuth);

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.CREATED.value()))) {
                param.put("reqStatus", true);
                params.put(param);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);
            }

            result.put("status", true);
            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.push.error")));

            logger.error("sendPush Service Server API Call Exception: {}" + e.getCause());
        }

        return result;
    }

    /**
     * 1.2 - 장치정보 동기화
     * 세대장치 목록 페이지에서 장치정보 동기화 버튼(Admin Web에서 서비스 서버에 장치정보 동기화를 요청)을 누른다.
     * @param adminId
     * @param hmnetId
     * @param houscplxCd
     * @param dongNo
     * @param hoseNo
     * @return
     */
    public JSONObject sendDeviceSync(String adminId, String hmnetId, String houscplxCd, String dongNo, String hoseNo) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("admin_id", adminId);
            map.put("hmnet_id", hmnetId);
            map.put("houscplx_cd", houscplxCd);
            map.put("dong_no", dongNo);
            map.put("hose_no", hoseNo);

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerUrl));
            deviceSyncPath = xssUtil.replaceAll(StringUtils.defaultString(deviceSyncPath));

            String requestUrl = pipsServiceServerUrl + deviceSyncPath;
            String paramData = JsonUtil.toJson(map);

            HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, pipsServiceServerAuth);

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.CREATED.value()))) {
                param.put("reqStatus", true);
                params.put(param);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);
            }

            result.put("status", true);
            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.device.sync.error")));

            logger.error("sendDeviceSync Service Server API Call Exception: {}" + e.getCause());
        }

        return result;
    }

    /**
     * 1.3 - 세대 및 장치 등록 제어
     * Admin Web 에서 세대주 승인이 완료 후 세대 및 장치 등록 제어를 요청한다.
     * @param userList
     * @return
     */
    public JSONObject sendDeviceRegi(String adminId, String userList) {
        JSONObject result = new JSONObject();

        try {
            JSONArray userListArray = new JSONArray(userList);
            JSONArray params = new JSONArray();

            for (int i=0; i<userListArray.length(); i++) {
                JSONObject param = new JSONObject();

                String userId = xssUtil.replaceAll(StringUtils.defaultString(userListArray.getJSONObject(i).getString("userId")));
                String hsholdId = xssUtil.replaceAll(StringUtils.defaultString(userListArray.getJSONObject(i).getString("hsholdId")));
                String apprStsCd = xssUtil.replaceAll(StringUtils.defaultString(userListArray.getJSONObject(i).getString("apprStsCd")));

                Map<String, String> map = new HashMap<String, String>();
                map.put("admin_id", adminId);
                map.put("user_id", userId);
                map.put("hshold_id", hsholdId);
                map.put("appr_sts_cd", apprStsCd);

                HTTPClientUtil httpClientUtil = new HTTPClientUtil();

                pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
                pipsServiceServerUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerUrl));
                deviceRegiPath = xssUtil.replaceAll(StringUtils.defaultString(deviceRegiPath));

                String requestUrl = pipsServiceServerUrl + deviceRegiPath;
                String paramData = JsonUtil.toJson(map);

                HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, pipsServiceServerAuth);

                if (httpResult.getStatus().equals(String.valueOf(HttpStatus.CREATED.value()))) {
                    param.put("hshold_id", hsholdId);
                    param.put("reqStatus", true);
                } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                    JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                    logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                    param.put("hshold_id", hsholdId);
                    param.put("reqStatus", false);
                }
                params.put(param);
            }

            result.put("status", true);
            result.put("params", params);
            result.put("cnt", userListArray.length());
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.device.regi.error")));

            logger.error("sendDeviceRegi Service Server API Call Exception: {}" + e.getCause());
        }

        return result;
    }

    /**
     * 1.4 - 회원/입주민 탈퇴
     * Admin Web 에서 회원/입주민 탈퇴를 요청한다.
     * @param userList
     * @return
     */
    public JSONObject sendUser(String userList) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        try {
            JSONArray userListArray = new JSONArray(userList);

            JSONArray userDataArray = new JSONArray();

            for (int i=0; i<userListArray.length(); i++) {
                String userId = xssUtil.replaceAll(StringUtils.defaultString(userListArray.getJSONObject(i).getString("userId")));
                String cancelTpCd = xssUtil.replaceAll(StringUtils.defaultString(userListArray.getJSONObject(i).getString("cancelTpCd")));
                String fmlyTpCd = xssUtil.replaceAll(StringUtils.defaultString(userListArray.getJSONObject(i).getString("fmlyTpCd")));
                String hsholdId = xssUtil.replaceAll(StringUtils.defaultString(userListArray.getJSONObject(i).getString("hsholdId")));

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("user_id", userId);
                jsonObject.put("cancel_tp_cd", cancelTpCd);
                jsonObject.put("fmly_tp_cd", fmlyTpCd);
                jsonObject.put("hshold_id", hsholdId);

                userDataArray.put(jsonObject);
            }

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerUrl));
            userPath = xssUtil.replaceAll(StringUtils.defaultString(userPath));

            String requestUrl = pipsServiceServerUrl + userPath;

            HttpResult httpResult = httpClientUtil.sendDataForPut(requestUrl, userDataArray.toString(), pipsServiceServerAuth);

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.CREATED.value()))) {
                param.put("reqStatus", true);
                params.put(param);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);
            }

            result.put("status", true);
            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.error")));

            logger.error("sendUser Service Server API Call Exception: {}" + e.getCause());
        }

        return result;
    }

    /**
     * 1.5 - 가족대표 변경
     * Admin Web 에서 가족대표를 변경 한다.
     * @param prevUserId
     * @param afterUserId
     * @return
     */
    public JSONObject sendFamilyRepresent(String prevUserId, String afterUserId) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("prev_user_id", prevUserId);
            map.put("after_user_id", afterUserId);

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerUrl));
            familyRepresentPath = xssUtil.replaceAll(StringUtils.defaultString(familyRepresentPath));

            String requestUrl = pipsServiceServerUrl + familyRepresentPath;
            String paramData = JsonUtil.toJson(map);

            HttpResult httpResult = httpClientUtil.sendDataForPut(requestUrl, paramData, pipsServiceServerAuth);

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.CREATED.value()))) {
                param.put("reqStatus", true);
                params.put(param);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);
            }

            result.put("status", true);
            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.family.represent.error")));

            logger.error("sendFamilyRepresent Service Server API Call Exception: {}" + e.getCause());
        }

        return result;
    }

    /**
     * 1.6 - 홈넷 설정 제어
     * Admin Web 에서 데이터 전송 여부 설정 제어를 요청한다.
     * @param adminId
     * @param hmnetId
     * @param keepAliveCycle
     * @param datTrnsmCycle
     * @param ctlExprtnCycle
     * @return
     */
    public JSONObject sendHmnetConf(String adminId, String hmnetId, String keepAliveCycle, String datTrnsmCycle, String ctlExprtnCycle) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("admin_id", adminId);
            map.put("hmnet_id", hmnetId);
            map.put("keep_alive_cycle", keepAliveCycle);
            map.put("dat_trnsm_cycle", datTrnsmCycle);
            map.put("ctl_exprtn_cycle", ctlExprtnCycle);

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerUrl));
            hmnetConfPath = xssUtil.replaceAll(StringUtils.defaultString(hmnetConfPath));

            String requestUrl = pipsServiceServerUrl + hmnetConfPath;
            String paramData = JsonUtil.toJson(map);

            HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, pipsServiceServerAuth);

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.CREATED.value()))) {
                param.put("reqStatus", true);
                params.put(param);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);
            }

            result.put("status", true);
            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.hmnet.conf.send.error")));

            logger.error("sendHmnetConfSend Service Server API Call Exception: {}" + e.getCause());
        }

        return result;
    }

    /**
     * 1.7 - 데이터 전송 여부 설정 제어
     * Admin Web 에서 데이터 전송 여부 설정 제어를 요청한다.
     * @param adminId
     * @param hmnetId
     * @param tgtTp
     * @param houscplxCd
     * @param trnsmYn
     * @return
     */
    public JSONObject sendHmnetConfSend(String adminId, String hmnetId, String tgtTp, String houscplxCd, String trnsmYn) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("admin_id", adminId);
            map.put("hmnet_id", hmnetId);
            map.put("tgt_tp", tgtTp);
            map.put("houscplx_cd", houscplxCd);
            map.put("trnsm_yn", trnsmYn);

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerUrl));
            hmnetConfSendPath = xssUtil.replaceAll(StringUtils.defaultString(hmnetConfSendPath));

            String requestUrl = pipsServiceServerUrl + hmnetConfSendPath;
            String paramData = JsonUtil.toJson(map);

            HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, pipsServiceServerAuth);

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.CREATED.value()))) {
                param.put("reqStatus", true);
                params.put(param);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);
            }

            result.put("status", true);
            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.hmnet.conf.error")));

            logger.error("sendHmnetConf Service Server API Call Exception: {}" + e.getCause());
        }

        return result;
    }

    /**
     * 1.9 - 생활불편 신고 답변 알림
     * Admin Web 에서 생활불편 신고 답변 알림에 대한 APP Push를 요청한다.
     * @return
     */
    public JSONObject sendReportPush(String blltNo, String userId) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("bllt_no", blltNo);
            map.put("user_id", userId);

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerUrl));
            reportPushPath = xssUtil.replaceAll(StringUtils.defaultString(reportPushPath));

            String requestUrl = pipsServiceServerUrl + reportPushPath;
            String paramData = JsonUtil.toJson(map);

            HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, pipsServiceServerAuth);

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.OK.value()))) {
                param.put("reqStatus", true);
                params.put(param);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);
            }

            result.put("status", true);
            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.report.push.error")));

            logger.error("sendReportPush Service Server API Call Exception: {}" + e.getCause());
        }

        return result;
    }

    /**
     * 1.11 - 홈넷 서버 사용 여부 변경 알림
     * 서비스 서버의 Cache 갱신을 위해 홈넷 서버 사용 여부 변경을 알린다
     * @param adminId
     * @param hmnetId
     * @param hmnetYseYn
     * @return
     */
    public JSONObject sendHmnetUseyn(String adminId, String hmnetId, String hmnetYseYn) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("admin_id", adminId);
            map.put("hmnet_id", hmnetId);
            map.put("hmnet_use_yn", hmnetYseYn);

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerUrl));
            hmnetUseynPath = xssUtil.replaceAll(StringUtils.defaultString(hmnetUseynPath));

            String requestUrl = pipsServiceServerUrl + hmnetUseynPath;
            String paramData = JsonUtil.toJson(map);

            HttpResult httpResult = httpClientUtil.sendDataForPut(requestUrl, paramData, pipsServiceServerAuth);

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.OK.value()))) {
                param.put("reqStatus", true);
                params.put(param);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);
            }

            result.put("status", true);
            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.hmnet.useyn.error")));

            logger.error("sendHmnetUseyn Service Server API Call Exception: {}" + e.getCause());
        }

        return result;
    }

    /**
     * 1.12 - 단지 공지사항 홈넷 서버 전송
     * Admin Web 에서 서비스 서버에 단지 공지사항 등록 내용을 전송 한다.
     * @param adminId
     * @param blltTpDtlCd
     * @param hmnetId
     * @param houscplxList
     * @param blltNo
     * @return
     */
    public JSONObject sendHouscplxNoti(String adminId, String blltTpDtlCd, String hmnetId, String houscplxList, String blltNo) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        try {
            JSONArray houscplxListArray = new JSONArray(houscplxList);

            List<String> houscplxArrayList = new ArrayList<String>();
            for(int i = 0; i < houscplxListArray.length(); i++){
                houscplxArrayList.add(xssUtil.replaceAll(StringUtils.defaultString(houscplxListArray.getString(i))));
            }

            String[] houscplxStrArray = houscplxArrayList.toArray(new String[houscplxArrayList.size()]);
            String houscplxStr = JsonUtil.toJson(houscplxStrArray);

            Map<String, String> map = new HashMap<String, String>();
            map.put("admin_id", adminId);
            map.put("bllt_tp_dtl_cd", blltTpDtlCd);
            map.put("hmnet_id", hmnetId);
            map.put("houscplx_list", houscplxStr);
            map.put("bllt_no", blltNo);

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerUrl));
            houscplxNotiPath = xssUtil.replaceAll(StringUtils.defaultString(houscplxNotiPath));

            String requestUrl = pipsServiceServerUrl + houscplxNotiPath;
            String paramData = JsonUtil.toJson(map);

            HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, pipsServiceServerAuth);

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.CREATED.value()))) {
                param.put("reqStatus", true);
                params.put(param);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);
            }

            result.put("status", true);
            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.houscplx.noti.error")));

            logger.error("sendHouscplxNoti Service Server API Call Exception: {}" + e.getCause());
        }

        return result;
    }

    /**
     * 1.13 - 사용자 사용 여부 처리
     * Admin Web 에서 서비스 서버에 푸르지오 앱 사용자의 사용 여부 처리를 호출한다.
     * @param userId
     * @param useYn
     * @return
     */
    public JSONObject sendUserUse(String userId, String useYn) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("user_id", userId);
            map.put("use_yn", useYn);

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerUrl));
            userUsePath = xssUtil.replaceAll(StringUtils.defaultString(userUsePath));

            String requestUrl = pipsServiceServerUrl + userUsePath;
            String paramData = JsonUtil.toJson(map);

            HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, pipsServiceServerAuth);

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.CREATED.value()))) {
                param.put("reqStatus", true);
                params.put(param);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);
            }

            result.put("status", true);
            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.hmnet.useyn.error")));

            logger.error("sendUserUse Service Server API Call Exception: {}" + e.getCause());
        }

        return result;
    }

    /**
     * 1.17 - 사용자 정보 수정
     * Admin Web 에서 서비스 서버에 푸르지오 앱 사용자의 정보를 수정 요청한다.
     * @param userId
     * @param userNm
     * @param emailNm
     * @param mphoneNo
     * @return
     */
    public JSONObject sendUserInfo(String userId, String userNm, String emailNm, String mphoneNo) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("user_id", userId);
            map.put("user_nm", userNm);
            map.put("email_nm", emailNm);
            map.put("mphone_no", mphoneNo);

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerUrl));
            userInfoPath = xssUtil.replaceAll(StringUtils.defaultString(userInfoPath));

            String requestUrl = pipsServiceServerUrl + userInfoPath;
            String paramData = JsonUtil.toJson(map);

            HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, pipsServiceServerAuth);

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.CREATED.value()))) {
                param.put("reqStatus", true);
                params.put(param);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);
            }

            result.put("status", true);
            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.hmnet.useyn.error")));

            logger.error("sendUserInfo Service Server API Call Exception: {}" + e.getCause());
        }

        return result;
    }

    public String sendControlHsholdId(String hsholdId, String resrvCtlId) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();
        String results;

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("reserve_id", resrvCtlId);

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerReserveUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerReserveUrl));
            reserveControlPath = xssUtil.replaceAll(StringUtils.defaultString(reserveControlPath));

            String requestUrl = pipsServiceServerReserveUrl + reserveControlPath + "/" + resrvCtlId;
            String paramData = JsonUtil.toJson(map);

            HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, pipsServiceServerAuth);

            results = "true";

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.CREATED.value()))) {
                param.put("reqStatus", true);
                params.put(param);

                result.put("status", true);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);

                result.put("status", false);
                results = "false";
            }

            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.error")));
            results = "false";

            logger.error("sendUserInfo Service Server API Call Exception: {}" + e.getCause());
        }

        return results;
    }

    public String sendPushHsholdId(String hsholdId, String resrvCtlId) {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();
        String results;

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("reserve_id", resrvCtlId);

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();

            pipsServiceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerAuth));
            pipsServiceServerReserveUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerReserveUrl));
            reservePushPath = xssUtil.replaceAll(StringUtils.defaultString(reservePushPath));

            String requestUrl = pipsServiceServerReserveUrl + reservePushPath + "/" + resrvCtlId;
            String paramData = JsonUtil.toJson(map);

            HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, pipsServiceServerAuth);

            results = "true";

            if (httpResult.getStatus().equals(String.valueOf(HttpStatus.CREATED.value()))) {
                param.put("reqStatus", true);
                params.put(param);

                result.put("status", true);
            } else if (httpResult.getStatus().equals(String.valueOf(HttpStatus.BAD_REQUEST.value()))) {
                JSONObject resultMessageJson = new JSONObject(httpResult.getMessage());
                logger.error("httpResult is BAD_REQUEST: {}" + resultMessageJson.getString("error_message"));

                param.put("reqStatus", false);
                params.put(param);

                result.put("status", false);
                results = "false";
            }

            result.put("params", params);
        } catch (Exception e) {
            result.put("status", false);
            result.put("params", new JSONObject(MessageUtil.getMessage("msg.api.error")));
            results = "false";

            logger.error("sendUserInfo Service Server API Call Exception: {}" + e.getCause());
        }

        return results;
    }

}