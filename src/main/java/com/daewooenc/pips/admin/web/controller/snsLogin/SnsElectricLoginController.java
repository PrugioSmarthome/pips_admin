package com.daewooenc.pips.admin.web.controller.snsLogin;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.crypto.Sha256Cipher;
import com.daewooenc.pips.admin.web.domain.dto.sns.CommunicationAuthCode;
import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import com.daewooenc.pips.admin.web.service.sns.SnsService;
import com.daewooenc.pips.admin.web.util.HTTPClientUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/elect")
public class SnsElectricLoginController {
    /**
     * 로그 출력.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 기본 주소.
     */
    private final String thisUrl = "elect";
    private @Value("${pips.prugio.domain}") String prugioDomain;
    private @Value("${redis.host}") String redisHost;
    private @Value("${redis.port}") int redisPort;
    private @Value("${redis.masterName}") String redisMasterName;
    private @Value("${redis.password}") String redisPassword;
    private @Value("${redis.sentinel.use}") String redisSentinelUse;
    // http://localhost:8889/elect/snscallback,    encodeURIComponent('http://localhost:8889/elect/snscallback')
    //개발
//    private static final String callBackURL = "http%3A%2F%2Fpips.daewooenc.com%3A8889%2Felect%2Fsnscallback";
    // 상용
//    private String callBackURL = "http%3A%2F%2F"+prugioDomain+"%3A8889%2Felect%2Fsnscallback";
//    private static final String naverCallBackURL = "https%3A%2F%2Fauth.expo.io%2Felect%2Fsnscallback";
//    private static final String kakaoCallBackURL = "http://localhost/8889/elect/snscallback";
    // 개인
    /*
    private static final String naverClientId = "9yqcwTAcVdSoBU_pa84P";
    private static final String naverClientSecret = "wgH3N0rNpk";
    private static final String googleClientId = "776273939468-v57b7cugs63mgd2ld5jivkimrj7uioeq.apps.googleusercontent.com";
    private static final String googleClientSecret = "WxQ0WYZjjDP8z3beKO7Cobg5";
    private static final String kakaoClientId = "0d094be76431976d8f4d57e0bc881fec";
    private static final String kakaoClientSecret = "bpvH9RU3vlKXeBdsRqrOpDklgccqJETI";
    */



    // 대우 APP id/key
    // naver
    private static final String naverClientId = "r9mhTyVUKs19JK9T8bSu";
    private static final String naverClientSecret = "iR0fCmE5cu";

    // Google
//    private static final String googleClientId = "967617877919-avsfgdf12pvqnjeinrmi9o0cr6sbcs50.apps.googleusercontent.com";
//    private static final String googleClientSecret = "hB3mrhpcg4GUdMGdbJ4VstH6";

    //private static final String googleClientId = "514408568544-ausnhvclk0fr8o4tbjk9vtir4v38q4j3.apps.googleusercontent.com";
    //private static final String googleClientSecret = "YXrv1O75nL5azMzijuNI40B2";
    // test
    private static final String googleClientId = "602581125571-m0u7sitvrqpjoc3rv6ok5tmgtbmcrmjm.apps.googleusercontent.com";
    private static final String googleClientSecret = "D3kEd8CPmX_xTFKqKwRlcB31";

    // Kakao
    private static final String kakaoClientId = "d71f580141dd44f5e9fc48cf0aedb865";
    private static final String kakaoClientSecret = "HqWIbNPHXf3YLYQlLkfayJNXhwzQCxXm";

    // auth Code 발급 URL
//    private static final String naverRequestUrl = "https://nid.naver.com/oauth2.0/authorize?client_id=" + naverClientId + "&response_type=code&redirect_uri="+ naverCallBackURL + "&state=";

    private @Value("${pips.encrypt.key}") String pipsEncryptKey;
    @Autowired
    private SnsService snsService;


    @Autowired
    private XSSUtil xssUtil;
    /**
     * 등록 화면.
     *
     * @param request      HttpServletRequest
     * @return String
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String insert(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        HttpSession httpSession = request.getSession();
        String commRedirectUri = request.getParameter("redirect_uri");
        String state = request.getParameter("state");
        String company = request.getParameter("company");
        String houscplxCode = request.getParameter("houscplxCode");

        httpSession.setAttribute("commRedirectUri", commRedirectUri);
        httpSession.setAttribute("state", state);
        httpSession.setAttribute("company", company);
        httpSession.setAttribute("houscplxCode", houscplxCode);

        logger.debug("snslogin start");

        return thisUrl+"/snslogin";   //만들어진 URL로 인증을 요청합니다.

    }

    /**
     * 등록 화면.
     *
     * @param request      HttpServletRequest
     * @return String
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        HttpSession httpSession = request.getSession();
        String commClientId = request.getParameter("client_id");
        String commRedirectUri = request.getParameter("redirect_uri");
        String commnunityState = request.getParameter("state");
        httpSession.setAttribute("commClientId", commClientId);
        httpSession.setAttribute("commRedirectUri", commRedirectUri);
        httpSession.setAttribute("commnunityState", commnunityState);
        logger.debug("logout start");

        return thisUrl+"/logout";   //만들어진 URL로 인증을 요청합니다.

    }

    /**
     * SNS 서버 인증 및 IP/PWD 인증
     *
     * @param request      HttpServletRequest
     * @param model
     * @return String
     */
    @RequestMapping(value = "oauth",  method = RequestMethod.GET)
    public String oauth(Model model, HttpServletRequest request, CommunicationAuthCode communicationAuthCodeDB) {

        SessionUser session = SessionUtil.getSessionUser(request);

        String callBackURL = "http%3A%2F%2F"+prugioDomain+"%3A8889%2Felect%2Fsnscallback";

        if(request.isSecure()){
            callBackURL = "https%3A%2F%2F"+prugioDomain+"%3A18889%2Felect%2Fsnscallback";
        } else {
            callBackURL = "http%3A%2F%2F"+prugioDomain+"%3A8889%2Felect%2Fsnscallback";
        }

        HttpSession httpSession = request.getSession();
        String session_id = httpSession.getId();

        String naverRequestUrl = "https://nid.naver.com/oauth2.0/authorize?client_id=" + naverClientId + "&response_type=code&redirect_uri="+ callBackURL + "&state=" + session_id;
        String kakaoRequestUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + kakaoClientId + "&response_type=code&redirect_uri="+ callBackURL + "&response_type=code&state=" + session_id;
        String googleRequestUrl = "https://accounts.google.com/o/oauth2/auth?client_id=" + googleClientId + "&scope=https://www.googleapis.com/auth/userinfo.profile&approval_prompt=force&access_type=offline&redirect_uri="+ callBackURL + "&response_type=code&state=" + session_id;

        String state = generateState();     //토큰을 생성합니다.
        String snsType = request.getParameter("snsType");
        String commClientId = (String)httpSession.getAttribute("commClientId");
        String snsRequestUrl = "";

        String commnunityState = (String)httpSession.getAttribute("state");
        String company = (String)httpSession.getAttribute("company");
        String houscplxCode = (String)httpSession.getAttribute("houscplxCode");

        // id/pwd 로그인
        if ("NORMAL".equals(snsType)) {
            String text_id = request.getParameter("text_idh");
            String text_nm = request.getParameter("text_nmh");

            //String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(text_id));
            //String paramPassword = xssUtil.replaceAll(StringUtils.defaultString(text_nm));

            //String userId = StringUtils.defaultIfEmpty(paramUserId, "");
            //String password = StringUtils.defaultIfEmpty(paramPassword, "");

            String userId = StringUtils.defaultIfEmpty(text_id, "");
            String password = StringUtils.defaultIfEmpty(text_nm, "");

            String pipsAuthCode = "";

            boolean existLoginID = false;
            boolean oauthLink = false;
            String resultMsg = "";
            String resultMsg2 = "";

            // id 조회
            existLoginID = snsService.electExistNormalID(userId, new Sha256Cipher(password).encrypt(), houscplxCode);

            communicationAuthCodeDB.setUserId(userId);
            communicationAuthCodeDB.setCompany(company);

            if (existLoginID) {
                if(company != null && !"".equals(company)){
                    oauthLink = snsService.selectOauthLinkInfo(communicationAuthCodeDB);
                } else {
                    oauthLink = true;
                }
                if(oauthLink) {
                    String houscplxCd = snsService.houscplxCdNormalID(userId);
                    int houscplxCdVal = Integer.parseInt(houscplxCd.substring(4));

                    if (houscplxCdVal < 20 && houscplxCdVal != 6 && houscplxCdVal != 4) {
                        logger.info("연동 대상 단지가 아닙니다.");
                        logger.info("HOUSCPLX_CD : " + houscplxCd);
                        resultMsg = "연동 대상 단지가 아닙니다.";
                        resultMsg2 = "확인 후 다시 실행해주세요.";

                        model.addAttribute("resultMsg", resultMsg);
                        model.addAttribute("resultMsg2", resultMsg2);
                        return "snsexception";
                    } else {
                        // Auth Code 생성
                        pipsAuthCode = generateState();
                        CommunicationAuthCode communicationAuthCode = new CommunicationAuthCode(pipsAuthCode, "N", userId, userId, "NORMAL");
                        if (snsService.insertCommunicationAuthCode(communicationAuthCode)) {
                            String commRedirectUri = (String) httpSession.getAttribute("commRedirectUri");

                            if (!"".equals(company) || company != null) {
                                commRedirectUri += "?authorize_code=" + pipsAuthCode + "&state=" + commnunityState + "&sns_type=" + snsType.toUpperCase() + "&company=" + company;
                            } else {
                                commRedirectUri += "?authorize_code=" + pipsAuthCode + "&state=" + commnunityState + "&sns_type=" + snsType.toUpperCase();
                            }

                            logger.debug("redirect commRedirectUri=====================" + commRedirectUri);
                            return "redirect:" + commRedirectUri;
                        } else {
                            logger.info("통신사 인증 코드 저장 에러");
                            logger.info("USER ID: " + userId);

                            resultMsg = "내부 에러. 관리자에게 문의하세요.";

                            model.addAttribute("resultMsg", resultMsg);
                            return "snsexception";
                        }
                    }
                } else {
                    logger.info("ID Not Exist : " + userId);
                    resultMsg = "이미 연동되어 있는 정보입니다.";
                    resultMsg2 = "연동 정보를 확인 후 이용해주세요.";
                    model.addAttribute("resultMsg", resultMsg);
                    model.addAttribute("resultMsg2", resultMsg2);
                    return "snsexception";
                }
            } else {
                logger.info("ID Not Exist : " + userId);
                resultMsg = "등록된 계정 정보를 확인해 주세요.";
                resultMsg2 = "푸르지오 App을 이용하여 회원 가입 후 다시 이용해주세요.";
                model.addAttribute("resultMsg", resultMsg);
                model.addAttribute("resultMsg2", resultMsg2);
                return "snsexception";
            }
            // sns Login
        } else {
            String commRedirectUri = (String)httpSession.getAttribute("commRedirectUri");
            if (commRedirectUri != null && !"".equals(commRedirectUri)) {
                commRedirectUri = commRedirectUri.replaceAll("amp;", "&");
            }

            if(redisSentinelUse.equals("Y")){
                Set<String> sentinel = new HashSet<>();
                sentinel.add(redisHost+":"+redisPort);

                GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
                JedisSentinelPool pool = new JedisSentinelPool(redisMasterName, sentinel, poolConfig, 3000, redisPassword, 12);

                Jedis jedis = pool.getResource();

                String redisKey = "oAuthSession:" + session_id;

                if(jedis.get(redisKey) == null){
                    JSONObject json = new JSONObject();
                    json.put("redirect_uri", commRedirectUri);
                    json.put("sns_type", snsType);
                    json.put("state", commnunityState);
                    json.put("houscplxCode", houscplxCode);

                    jedis.set(redisKey, String.valueOf(json));
                }

                if (jedis != null) {
                    jedis.close();
                }

                pool.close();

            }else if(redisSentinelUse.equals("N")){
                JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                JedisPool pool = new JedisPool(jedisPoolConfig, redisHost, redisPort, 3000, redisPassword, 12);

                Jedis jedis = pool.getResource();

                String redisKey = "oAuthSession:" + session_id;

                if(jedis.get(redisKey) == null){
                    JSONObject json = new JSONObject();
                    json.put("redirect_uri", commRedirectUri);
                    json.put("sns_type", snsType);
                    json.put("state", commnunityState);
                    json.put("company", company);
                    json.put("houscplxCode", houscplxCode);

                    jedis.set(redisKey, String.valueOf(json));
                }

                if (jedis != null) {
                    jedis.close();
                }

                pool.close();
            }

            if ("naver".equals(snsType)) {
                snsRequestUrl = "redirect:" + naverRequestUrl;
            } else if ("kakao".equals(snsType)) {
                snsRequestUrl = "redirect:" + kakaoRequestUrl;
            } else if ("google".equals(snsType)) {
                snsRequestUrl = "redirect:" + googleRequestUrl;
            } else if ("sk".equals(snsType)) {

            } else {
                logger.debug("Not allow snsType : " + snsType);
                return "";
            }
            logger.debug("oauth requestUrl================" + snsRequestUrl);
            return snsRequestUrl;   //만들어진 URL로 인증을 요청합니다.
        }
    }

    /**
     * SNS 서버 인증 후 AccessToken 조회
     *
     * @param request      HttpServletRequest
     * @param model
     * @return String
     */
    @RequestMapping(value = "snscallback", method = RequestMethod.GET)
    public String callback(Model model, HttpServletRequest request, CommunicationAuthCode communicationAuthCodeDB) throws UnsupportedEncodingException {

        String resultMsg = "";
        String resultMsg2 = "";
        String responseState = "";
        String snsAuthCode = "";
        String pipsAuthCode = "";
        String snsAccessToken = "";
        String authHeaderType = "";
        JSONObject jsonObject = null;
        String snsID = "";
        boolean existSnsID = false;
        boolean oauthLink = false;

        HttpSession httpSession = request.getSession();
        HTTPClientUtil httpClientUtil = new HTTPClientUtil();
        HttpServletResponse response = null;

        responseState = request.getParameter("state");  // sns에서 전송된 state값(session_id)
        snsAuthCode = request.getParameter("code");  // sns에서 전송된 인증 값

        String snsType = "";
        String commRedirectUri = "";
        String commnunityState = "";
        String company = "";
        String houscplxCode = "";

        if(redisSentinelUse.equals("Y")){
            Set<String> sentinel = new HashSet<>();
            sentinel.add(redisHost+":"+redisPort);

            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            JedisSentinelPool pool = new JedisSentinelPool(redisMasterName, sentinel, poolConfig, 3000, redisPassword, 12);

            Jedis jedis = pool.getResource();

            String redisKey = "oAuthSession:" + responseState;

            if(jedis.get(redisKey) == null){
                logger.info("SNS ID Not Exist: " + snsID);
                resultMsg = "연동에 실패했습니다.";
                resultMsg2 = "관리자에게 문의해주세요.";
                model.addAttribute("resultMsg", resultMsg);
                model.addAttribute("resultMsg2", resultMsg2);
                return "snsexception";
            }

            JSONObject json = new JSONObject(jedis.get(redisKey));

            snsType = (String) json.get("sns_type");
            commRedirectUri = (String) json.get("redirect_uri");
            commnunityState = (String) json.get("state");
            houscplxCode = (String) json.get("houscplxCode");
            company = "";
            if(json.has("company")){
                company = (String) json.get("company");
            }

            jedis.del(redisKey);

            if (jedis != null) {
                jedis.close();
            }

            pool.close();

        }else if(redisSentinelUse.equals("N")){

            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            JedisPool pool = new JedisPool(jedisPoolConfig, redisHost, redisPort, 3000, redisPassword, 12);

            Jedis jedis = pool.getResource();

            String redisKey = "oAuthSession:" + responseState;

            if(jedis.get(redisKey) == null){
                logger.info("SNS ID Not Exist: " + snsID);
                resultMsg = "연동에 실패했습니다.";
                resultMsg2 = "관리자에게 문의해주세요.";
                model.addAttribute("resultMsg", resultMsg);
                model.addAttribute("resultMsg2", resultMsg2);
                return "snsexception";
            }

            JSONObject json = new JSONObject(jedis.get(redisKey));

            snsType = (String) json.get("sns_type");
            commRedirectUri = (String) json.get("redirect_uri");
            commnunityState = (String) json.get("state");
            houscplxCode = (String) json.get("houscplxCode");
            company = "";
            if(json.has("company")){
                company = (String) json.get("company");
            }

            jedis.del(redisKey);

            if (jedis != null) {
                jedis.close();
            }

            pool.close();
        }

        if(commnunityState != null && !"".equals(commnunityState)) {
            if ("naver".equals(snsType)) {
                logger.debug("resposne stats================" + responseState);
                logger.debug("commnunityState================" + commnunityState);
                logger.debug("snsAuthCode================" + snsAuthCode);

                    String accessTokenReqUrl = getAccessTokenUrl(commnunityState, snsAuthCode, snsType, "");

                    HttpResult httpResult = httpClientUtil.sendDataForGet(accessTokenReqUrl);
                    String message = httpResult.getMessage();
                    try {
                        jsonObject = new JSONObject(message);
                        // Access Token 조회 결과 확인
                        if (!jsonObject.has("error")) {
                            snsAccessToken = (String) jsonObject.get("access_token");
                            // SNS 프로파일 조회
                            authHeaderType = "Bearer";
                            logger.debug("getProfileUrl(snsType)=========" + getProfileUrl(snsType));
                            logger.debug("snsAccessToken=========" + snsAccessToken);
                            logger.debug("authHeaderType=========" + authHeaderType);
                            httpResult = httpClientUtil.getSnsProfile(getProfileUrl(snsType), snsAccessToken, authHeaderType);
                            jsonObject = new JSONObject(httpResult.getMessage());
                            String profileResultCode = (String) jsonObject.get("resultcode");
                            logger.debug("httpResult.getStatus()=" + httpResult.getStatus());
                            if ("00".equals(profileResultCode)) {
                                JSONObject responseObject = (JSONObject) jsonObject.get("response");
                                snsID = (String) responseObject.get("id");
                            } else {
                                logger.info("SNS Profile Search Fail. error code : " + profileResultCode + " description : " + jsonObject.get("error_description"));
                                model.addAttribute("resultMsg", jsonObject.get("error_description"));
                                return "snsexception";
                            }
                        } else {
                            logger.info("SNS AccessToken Fail. error code : " + jsonObject.get("error") + " description : " + jsonObject.get("message"));
                            model.addAttribute("resultMsg", jsonObject.get("error_description"));
                            return "snsexception";
                        }

                    } catch (Exception ex) {
                        logger.debug(ex.getMessage());
                    }
            } else if ("kakao".equals(snsType)) {
                logger.debug("resposne stats================" + responseState);
                logger.debug("commnunityState================" + commnunityState);
                logger.debug("snsAuthCode================" + snsAuthCode);

                    String accessTokenReqUrl = getAccessTokenUrl(commnunityState, snsAuthCode, snsType, "");
                    logger.debug("accessTokenReqUrl================" + accessTokenReqUrl);

                    String redirect_uri = "http://smarthome.prugio.com:8889/elect/snscallback";
                    if(request.isSecure()){
                        redirect_uri = "https://smarthome.prugio.com:18889/elect/snscallback";
                    } else {
                        redirect_uri = "http://smarthome.prugio.com:8889/elect/snscallback";
                    }

                    HttpResult httpResult = httpClientUtil.snsPostData(accessTokenReqUrl, kakaoClientId, redirect_uri, snsAuthCode, kakaoClientSecret);
                    String message = httpResult.getMessage();
                    logger.debug("message================" + message);
                    try {
                        jsonObject = new JSONObject(message);
                        // Access Token 조회 결과 확인
                        if (!jsonObject.has("error")) {
                            snsAccessToken = (String) jsonObject.get("access_token");
                            // SNS 프로파일 조회
                            authHeaderType = "Bearer";
                            logger.debug("getProfileUrl(snsType)=========" + getProfileUrl(snsType));
                            logger.debug("snsAccessToken=========" + snsAccessToken);
                            logger.debug("authHeaderType=========" + authHeaderType);
                            httpResult = httpClientUtil.getSnsProfile(getProfileUrl(snsType), snsAccessToken, authHeaderType);
                            jsonObject = new JSONObject(httpResult.getMessage());
                            logger.debug("httpResult.getStatus()=" + httpResult.getStatus());
                            if ("200".equals(httpResult.getStatus())) {
                                snsID = (int) jsonObject.get("id") + "";
                                logger.debug("snsID=========" + snsID);
                            } else {
                                logger.info("SNS Profile Search Fail. error code : " + httpResult.getStatus() + " description : " + jsonObject.get("error_description"));
                                model.addAttribute("resultMsg", jsonObject.get("message"));
                                return "snsexception";
                            }
                        } else {
                            logger.info("SNS AccessToken Fail. error code : " + jsonObject.get("error") + " description : " + jsonObject.get("error_description"));
                            model.addAttribute("resultMsg", jsonObject.get("message"));
                            return "snsexception";
                        }

                    } catch (Exception ex) {
                        logger.info(ex.getMessage());
                        model.addAttribute("resultMsg", ex.getMessage());
                        return "snsexception";
                    }
            } else if ("google".equals(snsType)) {

                String callBackErrorCode = request.getParameter("error");  //세션에 저장된 토큰을 받아옵니다.
                logger.debug("resposne stats================" + responseState);
                logger.debug("commnunityState================" + commnunityState);
                logger.debug("snsAuthCode================" + snsAuthCode);
                logger.debug("callBackErrorCode================[" + callBackErrorCode + "]");

                    // error 발생 안할 경우
                    if (!"error".equals(callBackErrorCode) && callBackErrorCode == null && !"".equals(callBackErrorCode)) {
                        String accessTokenReqUrl = getAccessTokenUrl(commnunityState, snsAuthCode, snsType, "");
                        logger.debug("accessTokenReqUrl================" + accessTokenReqUrl);

                        String redirect_uri = "http://smarthome.prugio.com:8889/elect/snscallback";
                        if(request.isSecure()){
                            redirect_uri = "https://smarthome.prugio.com:18889/elect/snscallback";
                        } else {
                            redirect_uri = "http://smarthome.prugio.com:8889/elect/snscallback";
                        }

                        redirect_uri = "http://pips.daewooenc.com:8889/elect/snscallback";
                        HttpResult httpResult = httpClientUtil.snsPostData(accessTokenReqUrl, googleClientId, redirect_uri, snsAuthCode, googleClientSecret);
                        String message = httpResult.getMessage();
                        logger.debug("message================" + message);
                        try {
                            jsonObject = new JSONObject(message);
                            // Access Token 조회 결과 확인
                            if (!jsonObject.has("error")) {
                                snsAccessToken = (String) jsonObject.get("access_token");
                                // SNS 프로파일 조회
                                authHeaderType = "Bearer";
                                logger.debug("getProfileUrl(snsType)=========" + getProfileUrl(snsType));
                                logger.debug("snsAccessToken=========" + snsAccessToken);
                                logger.debug("authHeaderType=========" + authHeaderType);
                                httpResult = httpClientUtil.getSnsProfile(getProfileUrl(snsType), snsAccessToken, authHeaderType);
                                jsonObject = new JSONObject(httpResult.getMessage());
                                logger.debug("httpResult.getStatus()=" + httpResult.getStatus());
                                if ("200".equals(httpResult.getStatus())) {
                                    snsID = (String) jsonObject.get("id");
                                    logger.debug("snsID=========" + snsID);
                                } else {
                                    logger.info("SNS Profile Search Fail. error code : " + httpResult.getStatus() + " description : " + jsonObject.get("error_description"));
                                    return (String) jsonObject.get("error_description");
                                }
                            } else {
                                logger.info("SNS AccessToken Fail. error code : " + jsonObject.get("error") + " description : " + jsonObject.get("message"));
                                model.addAttribute("resultMsg", jsonObject.get("message"));
                                return "snsexception";
                            }

                        } catch (Exception ex) {
                            logger.debug(ex.getMessage());
                        }
                    } else {
                        resultMsg = "SNS 연동 실패 되었습니다.";
                        logger.info("SNS 연동 실패 Error Code : " + callBackErrorCode);
                        model.addAttribute("resultMsg", resultMsg);
                        return "snsexception";
                    }
            } else if ("sk".equals(snsType)) {

            }


            // SNS ID가 등록되어 있는지 확인 후 통신사에게 전달할 Auth code 생성
            if (snsID != null && !"".equals(snsID)) {
                // id 조회
                existSnsID = snsService.electExistSnsID(snsID, houscplxCode);
                communicationAuthCodeDB.setUserId(snsID);
                communicationAuthCodeDB.setCompany(company);
                if (existSnsID) {
                    if(company != null && !"".equals(company)){
                        oauthLink = snsService.selectOauthLinkInfo(communicationAuthCodeDB);
                    } else {
                        oauthLink = true;
                    }
                    if(oauthLink){
                        String houscplxCd = snsService.houscplxCdSnsID(snsID);
                        int houscplxCdVal = Integer.parseInt(houscplxCd.substring(4));

                        if (houscplxCdVal < 20 && houscplxCdVal != 6 && houscplxCdVal != 4){
                            logger.info("연동 대상 단지가 아닙니다.");
                            logger.info("HOUSCPLX_CD : " + houscplxCd);
                            resultMsg = "연동 대상 단지가 아닙니다.";
                            resultMsg2 = "확인 후 다시 실행해주세요.";

                            model.addAttribute("resultMsg", resultMsg);
                            model.addAttribute("resultMsg2", resultMsg2);
                            return "snsexception";
                        } else {
                            // Auth Code 생성
                            pipsAuthCode = generateState();
                            CommunicationAuthCode communicationAuthCode = new CommunicationAuthCode(pipsAuthCode, "N", snsID, snsID, "SNS");
                            if (snsService.insertCommunicationAuthCode(communicationAuthCode)) {

                                if(!"".equals(company) || company != null){
                                    commRedirectUri += "?authorize_code=" + pipsAuthCode + "&state=" + commnunityState+"&sns_type="+snsType.toUpperCase() + "&company=" + company;
                                } else {
                                    commRedirectUri += "?authorize_code=" + pipsAuthCode + "&state=" + commnunityState+"&sns_type="+snsType.toUpperCase();
                                }

                                logger.debug("RedirectUri===================" + commRedirectUri);
                                return "redirect:" + commRedirectUri;
                            } else {
                                resultMsg = "내부 에러. 관리자에게 문의하세요.";
                                model.addAttribute("resultMsg", resultMsg);
                                return "snsexception";
                            }
                        }
                    } else {
                        logger.info("SNS ID Not Exist: " + snsID);
                        resultMsg = "이미 연동되어 있는 정보입니다.";
                        resultMsg2 = "연동 정보를 확인 후 이용해주세요.";
                        model.addAttribute("resultMsg", resultMsg);
                        model.addAttribute("resultMsg2", resultMsg2);
                        return "snsexception";
                    }
                } else {
                    logger.info("SNS ID Not Exist");
                    resultMsg = "등록되지 않은 정보입니다.";
                    resultMsg2 = "푸르지오 App을 이용하여 회원 가입 후 다시 이용해주세요.";
                    model.addAttribute("resultMsg", resultMsg);
                    model.addAttribute("resultMsg2", resultMsg2);
                    return "snsexception";
                }
            } else {
                logger.info("SNS ID Not Exist");
                resultMsg = "등록되지 않은 정보입니다.";
                resultMsg2 = "푸르지오 App을 이용하여 회원 가입 후 다시 이용해주세요.";
                model.addAttribute("resultMsg", resultMsg);
                model.addAttribute("resultMsg2", resultMsg2);
                return "snsexception";
            }
        } else {
            logger.info("SNS ID Not Exist: " + snsID);
            resultMsg = "연동에 실패했습니다.";
            resultMsg2 = "관리자에게 문의해주세요.";
            model.addAttribute("resultMsg", resultMsg);
            model.addAttribute("resultMsg2", resultMsg2);
            return "snsexception";
        }
    }

    private String getProfileUrl(String snsType) {

        String profileUrl = "";
        if ("naver".equals(snsType)) {
            profileUrl = "https://openapi.naver.com/v1/nid/me";
        } else if ("kakao".equals(snsType)) {
            profileUrl = "https://kapi.kakao.com/v2/user/me";
        } else if ("google".equals(snsType)) {
            // https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=youraccess_token
            profileUrl = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=";
        } else if ("sk".equals(snsType)) {

        }
        return profileUrl;
    }
    private String getAccessTokenUrl(String state, String code, String snsType, String redirectURL) {

        String accessUrl = "";
        if ("naver".equals(snsType)) {
            accessUrl = "https://nid.naver.com/oauth2.0/token?client_id=" + naverClientId + "&client_secret=" + naverClientSecret
                    + "&grant_type=authorization_code" + "&state=" + state + "&code=" + code;
        } else if ("kakao".equals(snsType)) {
            accessUrl = "https://kauth.kakao.com/oauth/token";
        } else if ("google".equals(snsType)) {
            accessUrl = "https://oauth2.googleapis.com/token";
        } else if ("sk".equals(snsType)) {

        }
        return accessUrl;
    }

    public static String generateState() {

        SecureRandom random = new SecureRandom();

        return new BigInteger(130, random).toString(32);

    }

}