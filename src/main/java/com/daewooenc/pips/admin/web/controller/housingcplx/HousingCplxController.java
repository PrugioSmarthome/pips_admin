package com.daewooenc.pips.admin.web.controller.housingcplx;

import com.daewooenc.pips.admin.core.domain.UploadFileInfo;
import com.daewooenc.pips.admin.core.domain.authorization.UserGroupAuth;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.service.authorization.UserGroupAuthService;
import com.daewooenc.pips.admin.core.util.FileUploadUtil;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.common.WebConsts;
import com.daewooenc.pips.admin.web.domain.dto.banner.Banner;
import com.daewooenc.pips.admin.web.domain.dto.cctv.CCTV;
import com.daewooenc.pips.admin.web.domain.dto.common.CommCodeDetail;
import com.daewooenc.pips.admin.web.domain.dto.externalsvcinfo.ExternalServiceInfo;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizco;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizcoCaddr;
import com.daewooenc.pips.admin.web.domain.dto.homenet.HomenetSvr;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLink;
import com.daewooenc.pips.admin.web.domain.dto.household.Household;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.*;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLinkDetail;
import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import com.daewooenc.pips.admin.web.service.common.CommCodeService;
import com.daewooenc.pips.admin.web.service.externalsvcinfo.ExternalSvcInfoService;
import com.daewooenc.pips.admin.web.service.facility.FacilityService;
import com.daewooenc.pips.admin.web.service.servicelink.ServiceLinkService;
import com.daewooenc.pips.admin.web.service.homenet.HomenetService;
import com.daewooenc.pips.admin.web.service.housingcplx.HousingCplxService;
import com.daewooenc.pips.admin.web.service.system.banner.BannerService;
import com.daewooenc.pips.admin.web.util.DateUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import com.daewooenc.pips.admin.web.util.geo.GeoGridTrans;
import com.daewooenc.pips.admin.web.util.HTTPClientUtil;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.geo.GeoPoint;
import com.daewooenc.pips.admin.web.util.geo.GeoTrans;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;


/**
 * 단지관리 관련 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-07-30      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-07-30
 **/
@Controller
@RequestMapping("/cm/housingcplx/info")
public class HousingCplxController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/housingcplx/info";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private HousingCplxService housingCplxService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private CommCodeService commCodeService;

    @Autowired
    private HomenetService homenetService;

    @Autowired
    private ServiceLinkService serviceLinkService;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private ExternalSvcInfoService externalSvcInfoService;

    private @Value("${pips.encrypt.key}") String pipsEncryptKey;
    private @Value("${file.rootPath}") String fileRootPath;
    private @Value("${file.contents.complexUploadPath}") String fileContentsComplexUploadPath;
    private @Value("${file.image.ptype.fixedWidth}") int fileImagePtypeFixedWidth;
    private @Value("${pips.serviceServer.ptype.plot.image.url}") String pipsServiceServerPtypePlotImageUrl;
    private @Value("${pips.serviceServer.ptype.floor.image.url}") String pipsServiceServerPtypeFloorImageUrl;
    private @Value("${pips.serviceServer.ptype.notice.image.url}") String pipsServiceServerPtypeNoticeImageUrl;
    private @Value("${pips.prugio.domain}") String prugioDomain;

    /**
     * 단지관리 > 단지정보 관리 > 단지목록
     * 시스템 관리자용 단지관리 메인
     * @param housingCplx
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(HousingCplx housingCplx, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        Calendar cal = Calendar.getInstance();
        String time = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMddHHmmss_2, cal.getTime());

        model.addAttribute("time", time);
        model.addAttribute("userId", StringUtils.defaultIfEmpty(session.getUserId(), ""));
        model.addAttribute("userGroupName", groupName);

        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");

        if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
//            model.addAttribute("housingCplxList", housingCplxService.getHousingCplxList(housingCplx, UserType.SYSTEM.getGroupName()));
            model.addAttribute("housingCplxList", housingCplxService.getHousingCplxList(housingCplx));
            model.addAttribute("multiUserYn", "N");

//        } else if (UserType.COMPLEX_GROUP.getGroupName().equals(groupName)) {
//            model.addAttribute("housingCplxList", housingCplxService.getHousingCplxList(housingCplx, UserType.COMPLEX_GROUP.getGroupName()));
//            model.addAttribute("housingCplxList", housingCplxService.getHousingCplxList(housingCplx, UserType.COMPLEX_GROUP.getGroupName()));
        } else if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            return "redirect:/" + thisUrl + "/intro/view";
        } else if (UserType.MULTI_COMPLEX.getGroupName().equals(groupName)) {
            housingCplx.setUserId(session.getUserId());
            model.addAttribute("multiUserYn", "Y");
            model.addAttribute("housingCplxList", housingCplxService.getMultiHousingCplxList(housingCplx));
        }

        String paramHouscplxNm = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getHouscplxNm()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getHouscplxCd()));
        String paramStartCrDt = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getStartCrDt()));
        String paramEndCrDt = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getEndCrDt()));
        String paramDelYn = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getDelYn()));
        String paramSvrTpCd = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getSvrTpCd()));

        model.addAttribute("groupName", StringUtils.defaultIfEmpty(groupName, ""));
        model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(paramHouscplxNm, ""));
        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(paramHouscplxCd, ""));
        model.addAttribute("startCrDt", StringUtils.defaultIfEmpty(paramStartCrDt, ""));
        model.addAttribute("endCrDt", StringUtils.defaultIfEmpty(paramEndCrDt, ""));
        model.addAttribute("delYn", StringUtils.defaultIfEmpty(paramDelYn,"all"));
        model.addAttribute("svrTpCd", StringUtils.defaultIfEmpty(paramSvrTpCd,"all"));

        return thisUrl + "/list";
    }

    /**
     * 단지관리 > 단지정보 관리 > 단지목록 > 단지 신규등록 단지개요
     * 시스템 관리자용 단지기본정보 등록 (단지개요 > 세대별평형 > 기타)
     * @param housingCplx
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addHousingCplxInfo(HousingCplx housingCplx, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        CommCodeDetail commCodeDetail = new CommCodeDetail();
        commCodeDetail.setCommCdGrpCd("HEAT_TP_CD");
        model.addAttribute("heatTypeList", commCodeService.getCommCodeList(commCodeDetail));

        return thisUrl + "/add";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지정보 등록 및 수정
     * 시스템 및 단지 관리자용 단지 등록, 수정시에 주소팝업 활성화
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "search/addressService", method = {RequestMethod.GET, RequestMethod.POST})
    public String searchAddressService(Model model, HttpServletRequest request) {
        ExternalServiceInfo externalServiceInfo = new ExternalServiceInfo();
        externalServiceInfo.setSvcGrpTpCd("PUBLIC_DATA");
        externalServiceInfo.setSvcTpCd("ADDRESS_SEARCH");

        ExternalServiceInfo addressServiceInfo = externalSvcInfoService.getExternalServiceDetail(externalServiceInfo);

        String svcKeyCd = xssUtil.replaceAll(StringUtils.defaultString(addressServiceInfo.getSvcKeyCd()));

        svcKeyCd = StringUtils.defaultIfEmpty(svcKeyCd, "");

        model.addAttribute("svcKeyCd", svcKeyCd);

        return thisUrl + "/search/addressService";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지정보 등록 및 수정
     * 시스템 및 단지 관리자용 단지 등록, 수정시에 홈넷서버 목록 조회
     * Ajax 방식
     * @param homenetSvr
     * @return
     */
    @RequestMapping(value = "homenet/list", method = {RequestMethod.GET, RequestMethod.POST},produces = "application/text; charset=utf8")
    @ResponseBody
    public String homenetList(HomenetSvr homenetSvr) {
        String result = "";
        JSONObject jsonResult = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        String paramSvrTpCd = xssUtil.replaceAll(StringUtils.defaultString(homenetSvr.getSvrTpCd()));
        String svrTpCd = StringUtils.defaultIfEmpty(paramSvrTpCd, "");

        if (StringUtils.isEmpty(svrTpCd)) {
            param.put("id", "svrTpCd");
            param.put("msg", MessageUtil.getMessage("notEmpty.svrTpCd.svrTpCd"));
            params.put(param);
        }

        if (params.length() > 0) {
            jsonResult.put("status", false);
            jsonResult.put("params", params);

            return jsonResult.toString();
        }

        List<HomenetSvr> homenetMetaList = homenetService.getHomenetMetaList(homenetSvr);
        result = JsonUtil.toJsonNotZero(homenetMetaList);

        return result;
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지정보 등록 및 수정
     * 시스템 및 단지 관리자용 단지 등록, 수정시에 홈넷서버 중복체크
     * Ajax 방식
     * @param homenetSvr
     * @return
     */
    @RequestMapping(value = "checkHomenetInfo", method = {RequestMethod.GET, RequestMethod.POST},produces = "application/text; charset=utf8")
    @ResponseBody
    public String checkHomenetInfo(HomenetSvr homenetSvr) {
        String paramSvrTpCd = xssUtil.replaceAll(StringUtils.defaultString(homenetSvr.getSvrTpCd()));
        String paramHmnetId = xssUtil.replaceAll(StringUtils.defaultString(homenetSvr.getHmnetId()));
        String svrTpCd = StringUtils.defaultIfEmpty(paramSvrTpCd, "");
        String hmnetId = StringUtils.defaultIfEmpty(paramHmnetId, "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (StringUtils.isEmpty(svrTpCd)) {
            param.put("id", "svrTpCd");
            param.put("msg", MessageUtil.getMessage("notEmpty.svrTpCd.svrTpCd"));
            params.put(param);
        }

        if (StringUtils.isEmpty(hmnetId)) {
            param.put("id", "hmnetId");
            param.put("msg", MessageUtil.getMessage("notEmpty.hmnetId.hmnetId"));
            params.put(param);
        }

        if (params.length() > 0) {
            result.put("status", false);
            result.put("params", params);

            return result.toString();
        }

        HomenetSvr homenetSvrInfo = homenetService.checkUsedHomenet(homenetSvr);

        int usedCnt = homenetSvrInfo.getUsedCnt();

        if (usedCnt > 0) {
            param.put("id", "hmnetId");
            param.put("msg", MessageUtil.getMessage("notUse.hmnetId.hmnetId"));
            params.put(param);

            result.put("status", false);
            result.put("params", params);

            return result.toString();
        } else {
            result.put("status", true);
        }

        return result.toString();
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지정보 등록 (기타)
     * 시스템 및 단지 관리자용 단지 등록, 수정시에 연계 웹/앱 Type 목록 조회
     * Ajax 방식
     * @param housingCplx
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "serviceLinkType/list", method = {RequestMethod.GET, RequestMethod.POST},produces = "application/text; charset=utf8")
    @ResponseBody
    public String serviceLinkTypeList(HousingCplx housingCplx, Model model, HttpServletRequest request) {
        CommCodeDetail commCodeDetail = new CommCodeDetail();
        commCodeDetail.setCommCdGrpCd("LNK_SVC_GRP_TP_CD");
        List<CommCodeDetail> webappList = commCodeService.getCommCodeList(commCodeDetail);

        String json = JsonUtil.toJsonNotZero(webappList);

        return json;
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지정보 등록 (기타)
     * 시스템 및 단지 관리자용 단지 등록, 수정시에 연계 웹/앱 목록 조회
     * Ajax 방식
     * @param housingCplx
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "serviceLinkInfo/list", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String serviceLinkTypeInfo(HousingCplx housingCplx, Model model, HttpServletRequest request) {
        ServiceLink serviceLink = new ServiceLink();
        String paramLnkSvcGrpTpCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("lnkSvcGrpTpCd")));

        String lnkSvcGrpTpCd = StringUtils.defaultIfEmpty(paramLnkSvcGrpTpCd, "all");
        serviceLink.setLnkSvcGrpTpCd(lnkSvcGrpTpCd);
        serviceLink.setUseYn("Y");
        List<ServiceLink> serviceList = serviceLinkService.getServiceLinkList(serviceLink);

        String json = JsonUtil.toJsonNotZero(serviceList);

        return json;
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지정보 등록 처리
     * 시스템 관리자용 단지 등록을 처리 (단지개요 > 세대별평형 > 기타)
     * @param housingCplx
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "addHousingCplxInfoAction", method = RequestMethod.POST)
    public String addHousingCplxInfoAction(HousingCplx housingCplx, BindingResult result, Model model, HttpServletRequest request) {
        String resultUrl = "redirect:/cm/housingcplx/info/list";
        String resultMsg = "";

        try {
            logger.debug("addHousingCplxInfoAction start");

            int currentHouscplxId = housingCplxService.getHousingCplxCnt() + 1;
            String houscplxCd = "";

            if(prugioDomain.equals("smarthome.prugio.com")){
                houscplxCd = String.format("%06d", currentHouscplxId);
            }else{
                houscplxCd = String.format("%05d", currentHouscplxId);
                houscplxCd = '9' + houscplxCd;
            }

            housingCplx.setHouscplxCd(houscplxCd);

            String paramLatudCont = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getLatudCont()));
            String paramLotudCont = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getLotudCont()));
            String paramAddrSiDoNm = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getAddrSiDoNm()));
            String paramAddrSiGunGuNm = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getAddrSiGunGuNm()));
            String paramAddrEupMyeonDongNm = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getAddrEupMyeonDongNm()));

            // 격자정보 조회 및 셋팅
            String latudCont = StringUtils.defaultIfEmpty(paramLatudCont, "");
            String lotudCont = StringUtils.defaultIfEmpty(paramLotudCont, "");

            String addrSiDoNm = StringUtils.defaultIfEmpty(paramAddrSiDoNm, "");
            String addrSiGunGuNm = StringUtils.defaultIfEmpty(paramAddrSiGunGuNm, "");
            String addrEupMyeonDongNm = StringUtils.defaultIfEmpty(paramAddrEupMyeonDongNm, "");

            if (!StringUtils.isEmpty(latudCont) && !StringUtils.isEmpty(lotudCont)) {
                Double convLatudCont = Double.parseDouble(latudCont);
                Double convLotudCont = Double.parseDouble(lotudCont);

                GeoPoint geoPoint = new GeoPoint(convLatudCont, convLotudCont);
                GeoPoint convertGeoPoint = GeoTrans.convert(GeoTrans.UTMK, GeoTrans.GEO, geoPoint);
                logger.debug("geo out : x=" + convertGeoPoint.getX() + ", y=" + convertGeoPoint.getY());

                Double realLatudCont = convertGeoPoint.getY();
                Double realLotudCont = convertGeoPoint.getX();

                housingCplx.setLatudCont(String.valueOf(realLatudCont));
                housingCplx.setLotudCont(String.valueOf(realLotudCont));

                GeoGridTrans geoGridTrans = new GeoGridTrans();

                GeoGridTrans.LatXLngY convertGeoResult = geoGridTrans.convertToGridOrGps(GeoGridTrans.TO_GRID, convertGeoPoint.getY(), convertGeoPoint.getX());

                int x = (int) convertGeoResult.x;
                int y = (int) convertGeoResult.y;

                housingCplx.setWrMeasXCoorNo(x);
                housingCplx.setWrMeasYCoorNo(y);
            } else {
                housingCplx.setWrMeasXCoorNo(0);
                housingCplx.setWrMeasYCoorNo(0);
            }

            String paramHouscplxNm = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getHouscplxNm()));
            String paramPostNo = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getPostNo()));
            String paramRoadnmBasAddrCont = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getRoadnmBasAddrCont()));
            String paramAreaBasAddrCont = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getAreaBasAddrCont()));
            String paramLandDimQty = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getLandDimQty()));
            String paramArchDimQty = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getArchDimQty()));
            String paramBusiApprYmd = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getBusiApprYmd()));

            String houscplxNm = StringUtils.defaultIfEmpty(paramHouscplxNm, "");
            String postNo = StringUtils.defaultIfEmpty(paramPostNo, "");
            String roadnmBasAddrCont = StringUtils.defaultIfEmpty(paramRoadnmBasAddrCont, "");
            String areaBasAddrCont = StringUtils.defaultIfEmpty(paramAreaBasAddrCont, "");
            String landDimQty = StringUtils.defaultIfEmpty(paramLandDimQty, "");
            String archDimQty = StringUtils.defaultIfEmpty(paramArchDimQty, "");
            String busiApprYmd = StringUtils.defaultIfEmpty(paramBusiApprYmd, "");

            if(StringUtils.isEmpty(houscplxNm)) {
                result.addError(new FieldError("houscplxNm", "houscplxNm", MessageUtil.getMessage("notEmpty.houscplxNm.houscplxNm")));
            }

            if(StringUtils.isEmpty(postNo)) {
                result.addError(new FieldError("postNo", "postNo", MessageUtil.getMessage("notEmpty.postNo.postNo")));
            }

            if(StringUtils.isEmpty(roadnmBasAddrCont)) {
                result.addError(new FieldError("roadnmBasAddrCont", "roadnmBasAddrCont", MessageUtil.getMessage("notEmpty.roadnmBasAddrCont.roadnmBasAddrCont")));
            }

            if(StringUtils.isEmpty(areaBasAddrCont)) {
                result.addError(new FieldError("areaBasAddrCont", "areaBasAddrCont", MessageUtil.getMessage("notEmpty.areaBasAddrCont.areaBasAddrCont")));
            }

            if(StringUtils.isEmpty(landDimQty)) {
                result.addError(new FieldError("landDimQty", "landDimQty", MessageUtil.getMessage("notEmpty.landDimQty.landDimQty")));
            }

            if(StringUtils.isEmpty(archDimQty)) {
                result.addError(new FieldError("archDimQty", "archDimQty", MessageUtil.getMessage("notEmpty.archDimQty.archDimQty")));
            }

            /*if(StringUtils.isEmpty(busiApprYmd)) {
                result.addError(new FieldError("busiApprYmd", "busiApprYmd", MessageUtil.getMessage("notEmpty.busiApprYmd.busiApprYmd")));
            }*/

            Integer wholeHsholdCnt = null;
            Integer wholeDongCnt = null;
            Integer lwstUngrFlrcnt = null;
            Integer hgstAbgrFlrcnt = null;
            Integer wholeWlLcnt = null;
            Integer hsholdWlLcnt = null;

            wholeHsholdCnt = housingCplx.getWholeHsholdCnt();
            wholeDongCnt = housingCplx.getWholeDongCnt();
            lwstUngrFlrcnt = housingCplx.getLwstUngrFlrcnt();
            hgstAbgrFlrcnt = housingCplx.getHgstAbgrFlrcnt();
            wholeWlLcnt = housingCplx.getWholeWlLcnt();

            if(wholeHsholdCnt == null) {
                result.addError(new FieldError("wholeHsholdCnt", "wholeHsholdCnt", MessageUtil.getMessage("notEmpty.wholeHsholdCnt.wholeHsholdCnt")));
            }

            if(wholeDongCnt == null) {
                result.addError(new FieldError("wholeDongCnt", "wholeDongCnt", MessageUtil.getMessage("notEmpty.wholeDongCnt.wholeDongCnt")));
            }

            if(lwstUngrFlrcnt == null) {
                result.addError(new FieldError("lwstUngrFlrcnt", "lwstUngrFlrcnt", MessageUtil.getMessage("notEmpty.lwstUngrFlrcnt.lwstUngrFlrcnt")));
            }

            if(hgstAbgrFlrcnt == null) {
                result.addError(new FieldError("hgstAbgrFlrcnt", "hgstAbgrFlrcnt", MessageUtil.getMessage("notEmpty.hgstAbgrFlrcnt.hgstAbgrFlrcnt")));
            }

            if(StringUtils.isEmpty(housingCplx.getHmnetId())) {
                result.addError(new FieldError("hmnetId", "hmnetId", MessageUtil.getMessage("notEmpty.hmnetId.hmnetId")));
            }

            if(StringUtils.isEmpty(housingCplx.getHeatTpCd())) {
                result.addError(new FieldError("heatTpCd", "heatTpCd", MessageUtil.getMessage("notEmpty.heatTpCd.heatTpCd")));
            }

            if(wholeWlLcnt == null) {
                result.addError(new FieldError("wholeWlLcnt", "wholeWlLcnt", MessageUtil.getMessage("notEmpty.wholeWlLcnt.wholeWlLcnt")));
            }

            if(StringUtils.isEmpty(housingCplx.getHsholdWlLcnt())) {
                result.addError(new FieldError("hsholdWlLcnt", "hsholdWlLcnt", MessageUtil.getMessage("notEmpty.hsholdWlLcnt.hsholdWlLcnt")));
            }

            String paramHouseholdList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("householdList")));
            String paramServiceLinkList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("serviceLinkList")));
            String paramEnergyUnitList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("energyUnitList")));

            String householdList = StringUtils.defaultIfEmpty(paramHouseholdList, "");
            String serviceLinkList = StringUtils.defaultIfEmpty(paramServiceLinkList, "");
            String energyUnitList = StringUtils.defaultIfEmpty(paramEnergyUnitList, "");

            if (StringUtils.isEmpty(householdList)) {
                result.addError(new FieldError("householdList", "householdList", MessageUtil.getMessage("notEmpty.householdList.householdList")));
                logger.debug("result.hasErrors()=>{}", result.getFieldError());

                model.addAttribute("insertHousingCplx", null);
                model.addAttribute("resultMsg", resultMsg);
            }

            if (StringUtils.isEmpty(serviceLinkList)) {
                /*
                result.addError(new FieldError("serviceLinkList", "serviceLinkList", MessageUtil.getMessage("notEmpty.serviceLinkList.serviceLinkList")));
                logger.debug("result.hasErrors()=>{}", result.getFieldError());

                model.addAttribute("insertHousingCplx", null);
                model.addAttribute("resultMsg", resultMsg);

                 */
            }

            if (StringUtils.isEmpty(energyUnitList)) {
                result.addError(new FieldError("energyUnitList", "energyUnitList", MessageUtil.getMessage("notEmpty.energyUnitList.energyUnitList")));
                logger.debug("result.hasErrors()=>{}", result.getFieldError());

                model.addAttribute("insertHousingCplx", null);
                model.addAttribute("resultMsg", resultMsg);
            }

            if (result.hasErrors()) {
                logger.debug("result.hasErrors()=>{}", result.getFieldError());
                model.addAttribute("resultMsg", resultMsg);

                return resultUrl;
            }

            JSONArray householdJsonArray = new JSONArray(householdList);
            Map<String, Object> householdMap = new HashMap<String, Object>();
            int length = householdJsonArray.length();

            if (length > 0) {
                List<Household> list = new ArrayList<Household>();

                for(int i=0; i<length; i++) {
                    Household household = new Household();
                    String paramDongNo = xssUtil.replaceAll(StringUtils.defaultString((String) householdJsonArray.getJSONObject(i).get("dongNo")));
                    String paramHoseNo = xssUtil.replaceAll(StringUtils.defaultString((String) householdJsonArray.getJSONObject(i).get("hoseNo")));
                    String paramPtypeNm = xssUtil.replaceAll(StringUtils.defaultString((String) householdJsonArray.getJSONObject(i).get("ptypeNm")));
                    String paramDimQty = xssUtil.replaceAll(StringUtils.defaultString((String) householdJsonArray.getJSONObject(i).get("dimQty")));
                    String paramCrerId = xssUtil.replaceAll(StringUtils.defaultString((String) householdJsonArray.getJSONObject(i).get("crerId")));

                    String dongNo = StringUtils.defaultIfEmpty(paramDongNo, "");
                    String hoseNo = StringUtils.defaultIfEmpty(paramHoseNo, "");
                    String ptypeNm = StringUtils.defaultIfEmpty(paramPtypeNm, "");
                    String dimQty = StringUtils.defaultIfEmpty(paramDimQty, "");
                    String crerId = StringUtils.defaultIfEmpty(paramCrerId, "");

                    String hsholdId = houscplxCd + "." + dongNo + "." + hoseNo;

                    // 에너지 평형 구하기
                    int value = Integer.parseInt(dimQty);
                    int mok = value / 5;
                    int remainder = value % 5;
                    int energyPvalue = 0;
                    if (remainder >= 0 && remainder <= 2) {
                        energyPvalue = mok * 5;
                    } else {
                        energyPvalue = mok * 5 + 5;
                    }

                    household.setEnrgDimQty(String.valueOf(energyPvalue));
                    household.setHsholdId(hsholdId);
                    household.setHouscplxCd(houscplxCd);
                    household.setDongNo(dongNo);
                    household.setHoseNo(hoseNo);
                    household.setPtypeNm(ptypeNm);
                    household.setDimQty(dimQty);
                    household.setCrerId(crerId);

                    list.add(household);
                }
                householdMap.put("list", list);
            }

            JSONArray housingCplxServiceLinkJsonArray = new JSONArray(serviceLinkList);
            Map<String, Object> serviceLinkMap = new HashMap<String, Object>();
            int serviceLinkLength = housingCplxServiceLinkJsonArray.length();

            if (serviceLinkLength > 0) {
                List<HousingCplxServiceLink> list = new ArrayList<HousingCplxServiceLink>();

                for(int i=0; i<serviceLinkLength; i++) {
                    HousingCplxServiceLink housingCplxServiceLink = new HousingCplxServiceLink();
                    String paramLnkSvcId = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxServiceLinkJsonArray.getJSONObject(i).get("lnkSvcId")));
                    String paramCrerId = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxServiceLinkJsonArray.getJSONObject(i).get("crerId")));

                    String lnkSvcId = StringUtils.defaultIfEmpty(paramLnkSvcId, "");
                    String crerId = StringUtils.defaultIfEmpty(paramCrerId, "");

                    if (!"all".equals(lnkSvcId) && !"".equals(lnkSvcId) && lnkSvcId != null) {
                        housingCplxServiceLink.setHouscplxCd(houscplxCd);
                        housingCplxServiceLink.setLnkSvcId(Integer.valueOf(lnkSvcId));
                        housingCplxServiceLink.setCrerId(crerId);

                        list.add(housingCplxServiceLink);
                    }
                }
                serviceLinkMap.put("list", list);
            }

            JSONArray housingCplxEnrgUntJsonArray = new JSONArray(energyUnitList);
            Map<String, Object> energyUnitMap = new HashMap<String, Object>();
            int energyUnitLength = housingCplxEnrgUntJsonArray.length();

            if (energyUnitLength > 0) {
                List<HousingCplxEnergyUnit> list = new ArrayList<HousingCplxEnergyUnit>();

                for(int i=0; i<energyUnitLength; i++) {
                    HousingCplxEnergyUnit housingCplxEnergyUnit = new HousingCplxEnergyUnit();

                    String paramEnrgTpCd = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxEnrgUntJsonArray.getJSONObject(i).get("enrgTpCd")));
                    String paramEnrgUntCd = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxEnrgUntJsonArray.getJSONObject(i).get("enrgUntCd")));
                    String paramEnrgMaxQty = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxEnrgUntJsonArray.getJSONObject(i).get("enrgMaxQty")));
                    String paramCrerId = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxEnrgUntJsonArray.getJSONObject(i).get("crerId")));

                    String enrgTpCd = StringUtils.defaultIfEmpty(paramEnrgTpCd, "");
                    String enrgUntCd = StringUtils.defaultIfEmpty(paramEnrgUntCd, "");
                    String enrgMaxQty = StringUtils.defaultIfEmpty(paramEnrgMaxQty, "");
                    String crerId = StringUtils.defaultIfEmpty(paramCrerId, "");

                    housingCplxEnergyUnit.setHouscplxCd(houscplxCd);
                    housingCplxEnergyUnit.setEnrgTpCd(enrgTpCd);
                    housingCplxEnergyUnit.setEnrgUntCd(enrgUntCd);
                    housingCplxEnergyUnit.setEnrgMaxQty(enrgMaxQty);
                    housingCplxEnergyUnit.setCrerId(crerId);

                    list.add(housingCplxEnergyUnit);
                }
                energyUnitMap.put("list", list);
            }

            housingCplxService.insertHousingCplxInfo(housingCplx, householdMap, serviceLinkMap, energyUnitMap);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("addHousingCplxInfoAction Exception: " + e.getCause());
        }

        return resultUrl;
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지정보 상세
     * 시스템 및 단지 관리자용 단지정보 상세
     * @param housingCplx
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "intro/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String introView(HousingCplx housingCplx, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        String beanParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getHouscplxCd()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        if (StringUtils.isEmpty(beanParamHouscplxCd)) {
            housingCplx.setHouscplxCd(houscplxCd);
        }

        Calendar cal = Calendar.getInstance();
        String time = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMddHHmmss_2, cal.getTime());

        model.addAttribute("time", time);
        model.addAttribute("userId", StringUtils.defaultIfEmpty(session.getUserId(), ""));
        model.addAttribute("userGroupName", groupName);
        model.addAttribute("housingCplxCount",housingCplxService.getHousingCplxUserCnt(housingCplx));
        model.addAttribute("housingCplx", housingCplxService.getHousingCplxDetail(housingCplx));

        return thisUrl + "/intro/view";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지정보 수정
     * 시스템 및 단지 관리자용 단지정보 수정
     * @param housingCplx
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "intro/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String introEdit(HousingCplx housingCplx, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("housingCplx", housingCplxService.getHousingCplxDetail(housingCplx));

        CommCodeDetail commCodeDetail = new CommCodeDetail();
        commCodeDetail.setCommCdGrpCd("HEAT_TP_CD");
        model.addAttribute("heatTypeList", commCodeService.getCommCodeList(commCodeDetail));

        return thisUrl + "/intro/edit";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지정보 수정처리
     * 시스템 및 단지 관리자용 단지정보 수정을 처리
     * @param housingCplx
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "editHousingCplxIntroAction", method = RequestMethod.POST)
    public String editHousingCplxIntroAction(HousingCplx housingCplx, BindingResult result,
                                    Model model, HttpServletRequest request) {
        String resultUrl = "redirect:/cm/housingcplx/info/list";
        String resultMsg = "";

        // 격자정보 조회 및 셋팅
        String paramLatudCont = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getLatudCont()));
        String paramLotudCont = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getLotudCont()));
        String latudCont = StringUtils.defaultIfEmpty(paramLatudCont, "");
        String lotudCont = StringUtils.defaultIfEmpty(paramLotudCont, "");

        String paramFlag = xssUtil.replaceSpecialCharacter(StringUtils.defaultString(request.getParameter("flag")));
        String flag = StringUtils.defaultIfEmpty(paramFlag, "N");

        if (!StringUtils.isEmpty(latudCont) && !StringUtils.isEmpty(lotudCont) && flag.equals("Y")) {
            Double convLatudCont = Double.parseDouble(latudCont);
            Double convLotudCont = Double.parseDouble(lotudCont);

            GeoPoint geoPoint = new GeoPoint(convLatudCont, convLotudCont);
            GeoPoint convertGeoPoint = GeoTrans.convert(GeoTrans.UTMK, GeoTrans.GEO, geoPoint);
            logger.debug("geo out : x=" + convertGeoPoint.getX() + ", y=" + convertGeoPoint.getY());

            Double realLatudCont = convertGeoPoint.getY();
            Double realLotudCont = convertGeoPoint.getX();

            housingCplx.setLatudCont(String.valueOf(realLatudCont));
            housingCplx.setLotudCont(String.valueOf(realLotudCont));

            GeoGridTrans geoGridTrans = new GeoGridTrans();

            GeoGridTrans.LatXLngY convertGeoResult = geoGridTrans.convertToGridOrGps(GeoGridTrans.TO_GRID, convertGeoPoint.getY(), convertGeoPoint.getX());

            int x = (int) convertGeoResult.x;
            int y = (int) convertGeoResult.y;

            housingCplx.setWrMeasXCoorNo(x);
            housingCplx.setWrMeasYCoorNo(y);
        } else {
            housingCplx.setWrMeasXCoorNo(housingCplx.getWrMeasXCoorNo());
            housingCplx.setWrMeasYCoorNo(housingCplx.getWrMeasYCoorNo());
            housingCplx.setLatudCont(housingCplx.getLatudCont());
            housingCplx.setLotudCont(housingCplx.getLotudCont());
        }

        String paramHouscplxNm = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getHouscplxNm()));
        String paramPostNo = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getPostNo()));
        String paramRoadnmBasAddrCont = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getRoadnmBasAddrCont()));
        String paramAreaBasAddrCont = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getAreaBasAddrCont()));
        String paramLandDimQty = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getLandDimQty()));
        String paramArchDimQty = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getArchDimQty()));
        String paramBusiApprYmd = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getBusiApprYmd()));

        String houscplxNm = StringUtils.defaultIfEmpty(paramHouscplxNm, "");
        String postNo = StringUtils.defaultIfEmpty(paramPostNo, "");
        String roadnmBasAddrCont = StringUtils.defaultIfEmpty(paramRoadnmBasAddrCont, "");
        String areaBasAddrCont = StringUtils.defaultIfEmpty(paramAreaBasAddrCont, "");
        String landDimQty = StringUtils.defaultIfEmpty(paramLandDimQty, "");
        String archDimQty = StringUtils.defaultIfEmpty(paramArchDimQty, "");
        String busiApprYmd = StringUtils.defaultIfEmpty(paramBusiApprYmd, "");

        if(StringUtils.isEmpty(houscplxNm)) {
            result.addError(new FieldError("houscplxNm", "houscplxNm", MessageUtil.getMessage("notEmpty.houscplxNm.houscplxNm")));
        }

        if(StringUtils.isEmpty(postNo)) {
            result.addError(new FieldError("postNo", "postNo", MessageUtil.getMessage("notEmpty.postNo.postNo")));
        }

        if(StringUtils.isEmpty(roadnmBasAddrCont)) {
            result.addError(new FieldError("roadnmBasAddrCont", "roadnmBasAddrCont", MessageUtil.getMessage("notEmpty.roadnmBasAddrCont.roadnmBasAddrCont")));
        }

        if(StringUtils.isEmpty(areaBasAddrCont)) {
            result.addError(new FieldError("areaBasAddrCont", "areaBasAddrCont", MessageUtil.getMessage("notEmpty.areaBasAddrCont.areaBasAddrCont")));
        }

        if(StringUtils.isEmpty(landDimQty)) {
            result.addError(new FieldError("landDimQty", "landDimQty", MessageUtil.getMessage("notEmpty.landDimQty.landDimQty")));
        }

        if(StringUtils.isEmpty(archDimQty)) {
            result.addError(new FieldError("archDimQty", "archDimQty", MessageUtil.getMessage("notEmpty.archDimQty.archDimQty")));
        }

        /*if(StringUtils.isEmpty(busiApprYmd)) {
            result.addError(new FieldError("busiApprYmd", "busiApprYmd", MessageUtil.getMessage("notEmpty.busiApprYmd.busiApprYmd")));
        }*/

        Integer wholeHsholdCnt = null;
        Integer wholeDongCnt = null;
        Integer lwstUngrFlrcnt = null;
        Integer hgstAbgrFlrcnt = null;
        Integer wholeWlLcnt = null;

        wholeHsholdCnt = housingCplx.getWholeHsholdCnt();
        wholeDongCnt = housingCplx.getWholeDongCnt();
        lwstUngrFlrcnt = housingCplx.getLwstUngrFlrcnt();
        hgstAbgrFlrcnt = housingCplx.getHgstAbgrFlrcnt();
        wholeWlLcnt = housingCplx.getWholeWlLcnt();

        if(wholeHsholdCnt == null) {
            result.addError(new FieldError("wholeHsholdCnt", "wholeHsholdCnt", MessageUtil.getMessage("notEmpty.wholeHsholdCnt.wholeHsholdCnt")));
        }

        if(wholeDongCnt == null) {
            result.addError(new FieldError("wholeDongCnt", "wholeDongCnt", MessageUtil.getMessage("notEmpty.wholeDongCnt.wholeDongCnt")));
        }

        if(lwstUngrFlrcnt == null) {
            result.addError(new FieldError("lwstUngrFlrcnt", "lwstUngrFlrcnt", MessageUtil.getMessage("notEmpty.lwstUngrFlrcnt.lwstUngrFlrcnt")));
        }

        if(hgstAbgrFlrcnt == null) {
            result.addError(new FieldError("hgstAbgrFlrcnt", "hgstAbgrFlrcnt", MessageUtil.getMessage("notEmpty.hgstAbgrFlrcnt.hgstAbgrFlrcnt")));
        }

        if(StringUtils.isEmpty(housingCplx.getHmnetId())) {
            result.addError(new FieldError("hmnetId", "hmnetId", MessageUtil.getMessage("notEmpty.hmnetId.hmnetId")));
        }

        if(StringUtils.isEmpty(housingCplx.getHeatTpCd())) {
            result.addError(new FieldError("heatTpCd", "heatTpCd", MessageUtil.getMessage("notEmpty.heatTpCd.heatTpCd")));
        }

        if(wholeWlLcnt == null) {
            result.addError(new FieldError("wholeWlLcnt", "wholeWlLcnt", MessageUtil.getMessage("notEmpty.wholeWlLcnt.wholeWlLcnt")));
        }

        if(StringUtils.isEmpty(housingCplx.getHsholdWlLcnt())) {
            result.addError(new FieldError("hsholdWlLcnt", "hsholdWlLcnt", MessageUtil.getMessage("notEmpty.hsholdWlLcnt.hsholdWlLcnt")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("editHousingCplxIntroAction", null);
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        } else {
            if (!housingCplxService.updateHousingCplx(housingCplx)) {
                logger.debug("housingCplxService.editHousingCplxIntroAction not updated");
            }
        }

        return resultUrl;
    }


    /**
     * 단지 관리 > 단지정보 관리 > 우리 단지 알림 상세
     * 시스템 및 단지 관리자용 우리 단지 알림 상세 조회
     * @param housingCplxPtype
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "notice/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String noticeView(HousingCplxPtype housingCplxPtype, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String beanParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(housingCplxPtype.getHouscplxCd()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        if (StringUtils.isEmpty(beanParamHouscplxCd)) {
            housingCplxPtype.setHouscplxCd(houscplxCd);
        }

        String paramPtypeTpCd = xssUtil.replaceAll(StringUtils.defaultString(housingCplxPtype.getPtypeTpCd()));
        String ptypeTpCd = StringUtils.defaultIfEmpty(paramPtypeTpCd, "");

        if (StringUtils.isEmpty(ptypeTpCd)) {
            housingCplxPtype.setPtypeTpCd("HOUSCPLX_INFO");
        }

        model.addAttribute("houscplxInfoList", housingCplxService.getHousingCplxPtypeDetail(housingCplxPtype));
        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/notice/view";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지배치도 상세
     * 시스템 및 단지 관리자용 단지배치도 상세 조회
     * @param housingCplxPtype
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "plot/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String plotView(HousingCplxPtype housingCplxPtype, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String beanParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(housingCplxPtype.getHouscplxCd()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        if (StringUtils.isEmpty(beanParamHouscplxCd)) {
            housingCplxPtype.setHouscplxCd(houscplxCd);
        }

        String paramPtypeTpCd = xssUtil.replaceAll(StringUtils.defaultString(housingCplxPtype.getPtypeTpCd()));
        String ptypeTpCd = StringUtils.defaultIfEmpty(paramPtypeTpCd, "");

        if (StringUtils.isEmpty(ptypeTpCd)) {
            housingCplxPtype.setPtypeTpCd("PLOT_PLAN");
        }

        model.addAttribute("plotPlanList", housingCplxService.getHousingCplxPtypeDetail(housingCplxPtype));
        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/plot/view";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 타입별 평면도 상세
     * 시스템 및 단지 관리자용 타입별 평면도 상세 조회
     * @param housingCplxPtype
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "floor/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String floorView(HousingCplxPtype housingCplxPtype, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String beanParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(housingCplxPtype.getHouscplxCd()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        if (StringUtils.isEmpty(beanParamHouscplxCd)) {
            housingCplxPtype.setHouscplxCd(houscplxCd);
        }

        String paramPtypeTpCd = xssUtil.replaceAll(StringUtils.defaultString(housingCplxPtype.getPtypeTpCd()));
        String ptypeTpCd = StringUtils.defaultIfEmpty(paramPtypeTpCd, "");

        if (StringUtils.isEmpty(ptypeTpCd)) {
            housingCplxPtype.setPtypeTpCd("FLOOR_PLAN");
        }

        model.addAttribute("floorPlanList", housingCplxService.getHousingCplxPtypeDetail(housingCplxPtype));
        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/floor/view";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 우리 단지 알림 or 단지배치도 or 타입별 평면도 이미지 등록
     * 시스템 및 단지 관리자용 우리 단지 알림 or 단지배치도 or 타입별 평면도 이미지 등록을 처리
     * @param housingCplxPtype
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "addHousingCplxImageInfoAction", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String addHousingCplxImageInfoAction(HousingCplxPtype housingCplxPtype, Model model, HttpServletRequest request) throws IOException {
        JSONObject result = new JSONObject();

        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramPtypeTpCd = xssUtil.replaceAllOrg(StringUtils.defaultString(request.getParameter("ptypeTpCd")));
        String ptypeTpCd = StringUtils.defaultIfEmpty(paramPtypeTpCd, "");

        String imageType = "";

        if (ptypeTpCd.equals("PLOT_PLAN")) {
            imageType = "plot";
            housingCplxPtype.setPtypeTpCd("PLOT_PLAN");
        } else if (ptypeTpCd.equals("FLOOR_PLAN")) {
            imageType = "floor";
            housingCplxPtype.setPtypeTpCd("FLOOR_PLAN");
        } else if (ptypeTpCd.equals("HOUSCPLX_INFO")) {
            imageType = "notice";
            housingCplxPtype.setPtypeTpCd("HOUSCPLX_INFO");
        }

        List<UploadFileInfo> fileList = new ArrayList<UploadFileInfo>();

        FileUploadUtil fileUploadUtil = new FileUploadUtil();

        fileRootPath = xssUtil.replaceAllOrg(StringUtils.defaultString(fileRootPath));
        fileContentsComplexUploadPath = xssUtil.replaceAllOrg(StringUtils.defaultString(fileContentsComplexUploadPath));

        String realPath = fileRootPath + fileContentsComplexUploadPath;

        MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;

        List<String> originalFileNameList = new ArrayList<>();
        List<String> thumbnailFileNameList = new ArrayList<>();

        int fileCount = mpRequest.getMultiFileMap().get("file[]").size();

        for (int i=0; i<fileCount; i++) {
            MultipartFile mpf = mpRequest.getMultiFileMap().get("file[]").get(i);
            String fileNameInfo = mpRequest.getParameterMap().get("_fileNames")[0];
            String[] fileNameInfoArray = fileNameInfo.split("/");
            String realOriginalFileName = xssUtil.replaceTag(StringUtils.defaultString(fileNameInfoArray[i]));
            String originalFileName = fileUploadUtil.makeFileName(true, housingCplxPtype.getHouscplxCd(), imageType, true, realOriginalFileName);

            if (mpf.getSize() > 0) {
                UploadFileInfo originalUploadFileInfo = fileUploadUtil.uploadOriginalFile(mpf, realPath , originalFileName);

                fileList.add(originalUploadFileInfo);
                logger.info("\noriginalUploadFileInfo : {} ", originalUploadFileInfo.toString().replaceAll("\\]", "\n]").replaceAll("\\[", "\n[\n").replaceAll(",", ",\n"));

                File thumbnailFile = fileUploadUtil.multipartToFile(mpf);
                String thumbnailFileName = fileUploadUtil.makeFileName(true, housingCplxPtype.getHouscplxCd(), imageType, false, "");

                String originalRealPath = realPath + "/" + originalFileName;

                BufferedImage image = ImageIO.read(new FileInputStream(originalRealPath));
                int originalWidth = image.getWidth();
                int originalHeight = image.getHeight();

                if (originalWidth > fileImagePtypeFixedWidth) {
                    double compareWidth = image.getWidth();
                    double rate = fileImagePtypeFixedWidth / compareWidth;

                    originalWidth = (int) Math.round(originalWidth * rate) ;
                    originalHeight = (int) Math.round(originalHeight * rate);
                }

                int extIndex = mpf.getOriginalFilename().lastIndexOf(".");
                String fileExt = mpf.getOriginalFilename().substring(extIndex + 1);

                fileUploadUtil.uploadThumbnailImageFile(thumbnailFile, realPath, thumbnailFileName, originalWidth, originalHeight, fileExt);

                boolean isTempFileDeleted =  thumbnailFile.delete();

                logger.debug("thumbnailFile AbsolutePath: " + thumbnailFile.getAbsolutePath());
                logger.debug("thumbnailFile CanonicalPath: " + thumbnailFile.getCanonicalPath());
                logger.debug("isTempDeleted is True? : " + BooleanUtils.isTrue(isTempFileDeleted));

                originalFileNameList.add(originalFileName);
                thumbnailFileNameList.add(thumbnailFileName);
            }
        }

        Map<String, Object> housingCplxPtypeMap = new HashMap<>();

        int fileListSize = originalFileNameList.size();

        if (fileListSize > 0) {
            List<HousingCplxPtype> list = new ArrayList<HousingCplxPtype>();

            int prevImgCnt = housingCplxService.getHousingCplxPtypeCnt(housingCplxPtype);

            for(int i=0; i<fileListSize; i++) {
                String orgnlPlnfigFileUrl = "";
                String reducePlnfigFileUrl = "";

                if (ptypeTpCd.equals("PLOT_PLAN")) {
                    pipsServiceServerPtypePlotImageUrl = xssUtil.replaceAllOrg(StringUtils.defaultString(pipsServiceServerPtypePlotImageUrl));

                    orgnlPlnfigFileUrl = pipsServiceServerPtypePlotImageUrl + "&itype=original&houscplx_cd=" + houscplxCd + "&file_nm=" + originalFileNameList.get(i);
                    reducePlnfigFileUrl = pipsServiceServerPtypePlotImageUrl + "&itype=thumbnail&houscplx_cd=" + houscplxCd + "&file_nm=" + thumbnailFileNameList.get(i);
                } else if (ptypeTpCd.equals("FLOOR_PLAN")) {
                    pipsServiceServerPtypeFloorImageUrl = xssUtil.replaceAllOrg(StringUtils.defaultString(pipsServiceServerPtypeFloorImageUrl));

                    orgnlPlnfigFileUrl = pipsServiceServerPtypeFloorImageUrl + "&itype=original&houscplx_cd=" + houscplxCd + "&file_nm=" + originalFileNameList.get(i);
                    reducePlnfigFileUrl = pipsServiceServerPtypeFloorImageUrl + "&itype=thumbnail&houscplx_cd=" + houscplxCd + "&file_nm=" + thumbnailFileNameList.get(i);
                } else if (ptypeTpCd.equals("HOUSCPLX_INFO")) {
                    pipsServiceServerPtypeNoticeImageUrl = xssUtil.replaceAllOrg(StringUtils.defaultString(pipsServiceServerPtypeNoticeImageUrl));

                    orgnlPlnfigFileUrl = pipsServiceServerPtypeNoticeImageUrl + "&itype=original&houscplx_cd=" + houscplxCd + "&file_nm=" + originalFileNameList.get(i);
                    reducePlnfigFileUrl = pipsServiceServerPtypeNoticeImageUrl + "&itype=thumbnail&houscplx_cd=" + houscplxCd + "&file_nm=" + thumbnailFileNameList.get(i);
                }


                int ptypeNum = (i + prevImgCnt) + 1;

                HousingCplxPtype housingCplxPtypeInfo = new HousingCplxPtype();

                housingCplxPtypeInfo.setCrerId(adminId);
                housingCplxPtypeInfo.setStosOrgnlPlnfigNm(originalFileNameList.get(i));
                housingCplxPtypeInfo.setOrgnlPlnfigFilePathCont(realPath);
                housingCplxPtypeInfo.setOrgnlPlnfigFileUrlCont(orgnlPlnfigFileUrl);
                housingCplxPtypeInfo.setStosReducePlnfigNm(thumbnailFileNameList.get(i));
                housingCplxPtypeInfo.setReducePlnfigFilePathCont(realPath);
                housingCplxPtypeInfo.setReducePlnfigFileUrlCont(reducePlnfigFileUrl);
                housingCplxPtypeInfo.setPtypeTpCd(ptypeTpCd);
                housingCplxPtypeInfo.setHouscplxCd(houscplxCd);
                housingCplxPtypeInfo.setOrdNo(ptypeNum);

                if (ptypeTpCd.equals("PLOT_PLAN")) {
                    String ptypeNm = "P" + ptypeNum;

                    housingCplxPtypeInfo.setPtypeNm(ptypeNm);
                    housingCplxPtypeInfo.setPtypeDimQty(String.valueOf(ptypeNum));
                    housingCplxPtypeInfo.setSupDimQty("");
                } else if (ptypeTpCd.equals("FLOOR_PLAN")) {
                    String fileNameInfo = mpRequest.getParameterMap().get("_fileNames")[0];
                    String ptypeNameInfo = mpRequest.getParameterMap().get("_ptypeNames")[0];
                    String ptypeQtysInfo = mpRequest.getParameterMap().get("_ptypeQtys")[0];
                    String supQtysInfo = mpRequest.getParameterMap().get("_supQtys")[0];

                    String[] fileNameInfoArray = fileNameInfo.split("/");
                    String[] ptypeNameArray = ptypeNameInfo.split(",");
                    String[] ptypeQtysArray = ptypeQtysInfo.split(",");
                    String[] supQtysInfoArray = supQtysInfo.split(",");

                    int fileListLength = fileNameInfoArray.length;

                    if (fileListLength > 0) {
                        for(int j=0; j<fileListLength; j++) {
                            String originalFileName = fileUploadUtil.makeFileName(true, housingCplxPtype.getHouscplxCd(), imageType, true, fileNameInfoArray[j]);
                            if(originalFileNameList.get(i).equals(originalFileName)) {
                                String paramPtypeNm = xssUtil.replaceAllOrg(StringUtils.defaultString(ptypeNameArray[j]));
                                String paramPtypeDimQty = xssUtil.replaceAllOrg(StringUtils.defaultString(ptypeQtysArray[j]));
                                String paramSupDimQty = xssUtil.replaceAllOrg(StringUtils.defaultString(supQtysInfoArray[j]));

                                String ptypeNm = StringUtils.defaultIfEmpty(paramPtypeNm, "");
                                String ptypeDimQty = StringUtils.defaultIfEmpty(paramPtypeDimQty, "");
                                String supDimQty = StringUtils.defaultIfEmpty(paramSupDimQty, "");

                                if (StringUtils.isEmpty(ptypeNm) || StringUtils.isEmpty(ptypeDimQty) || StringUtils.isEmpty(supDimQty)) {
                                    result.put("uploaded", "ERROR");

                                    return result.toString();
                                } else if (StringUtils.isNotEmpty(ptypeNm) && StringUtils.isNotEmpty(ptypeDimQty) && StringUtils.isNotEmpty(supDimQty)) {
                                    housingCplxPtypeInfo.setPtypeNm(ptypeNm);
                                    housingCplxPtypeInfo.setPtypeDimQty(ptypeDimQty);
                                    housingCplxPtypeInfo.setSupDimQty(supDimQty);
                                }
                            }
                        }
                    }
                } else if (ptypeTpCd.equals("HOUSCPLX_INFO")) {
                    String ptypeNm = "N" + ptypeNum;

                    housingCplxPtypeInfo.setPtypeNm(ptypeNm);
                    housingCplxPtypeInfo.setPtypeDimQty(String.valueOf(ptypeNum));
                    housingCplxPtypeInfo.setSupDimQty("");
                }

                list.add(housingCplxPtypeInfo);
            }
            housingCplxPtypeMap.put("list", list);
        }

        housingCplxService.insertHousingCplxImageInfo(housingCplxPtypeMap);

        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("ptypeTpCd", ptypeTpCd);

        result.put("uploaded", "OK");

        return result.toString();
    }

    /**
     * 단지 관리 > 단지정보 관리 > 우리 단지 알림 수정
     * 시스템 및 단지 관리자용 우리 단지 알림 수정
     * @param housingCplxPtype
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "notice/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String noticeEdit(HousingCplxPtype housingCplxPtype, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("noticePlanList", housingCplxService.getHousingCplxPtypeDetail(housingCplxPtype));

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/notice/edit";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지배치도 수정
     * 시스템 및 단지 관리자용 단지배치도 수정
     * @param housingCplxPtype
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "plot/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String plotEdit(HousingCplxPtype housingCplxPtype, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("plotPlanList", housingCplxService.getHousingCplxPtypeDetail(housingCplxPtype));

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/plot/edit";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 타입별 평면도 수정
     * 시스템 및 단지 관리자용 타입별 평면도 수정
     * @param housingCplxPtype
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "floor/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String floorEdit(HousingCplxPtype housingCplxPtype, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("floorPlanList", housingCplxService.getHousingCplxPtypeDetail(housingCplxPtype));

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/floor/edit";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 우리 단지 알림 or 단지배치도 or 타입별 평면도 이미지 정보 수정처리
     * 시스템 및 단지 관리자용 우리 단지 알림 or 단지배치도 or 타입별 평면도 이미지 정보 수정처리
     * @param housingCplxPtype
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "editHousingCplxImageInfoAction", method = RequestMethod.POST)
    public String editHousingCplxImageInfoAction(HousingCplxPtype housingCplxPtype, Model model, HttpServletRequest request) throws IOException {
        SessionUser session = SessionUtil.getSessionUser(request);
        String resultMsg = "";
        String resultUrl = "redirect:/" + thisUrl;

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramRequestType = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("requestType")));
        String paramPtypeTpCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("ptypeTpCd")));

        String requestType = StringUtils.defaultIfEmpty(paramRequestType, "");
        String ptypeTpCd = StringUtils.defaultIfEmpty(paramPtypeTpCd, "");

        if (ptypeTpCd.equals("PLOT_PLAN")) {
            resultUrl += "/plot/edit";
            housingCplxPtype.setPtypeTpCd("PLOT_PLAN");
        } else if (ptypeTpCd.equals("FLOOR_PLAN")) {
            resultUrl += "/floor/edit";
            housingCplxPtype.setPtypeTpCd("FLOOR_PLAN");
        } else if (ptypeTpCd.equals("HOUSCPLX_INFO")) {
            resultUrl += "/notice/edit";
            housingCplxPtype.setPtypeTpCd("HOUSCPLX_INFO");
        }

        String imageType = "";

        if (ptypeTpCd.equals("PLOT_PLAN")) {
            imageType = "plot";
        } else if (ptypeTpCd.equals("FLOOR_PLAN")) {
            imageType = "floor";
        } else if (ptypeTpCd.equals("HOUSCPLX_INFO")) {
            imageType = "notice";
        }

        String prevFileNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("prevFileNm")));

        if(requestType.equals("CHANGE")){
            prevFileNm = StringUtils.defaultIfEmpty(prevFileNm, "");
        }

        if (requestType.equals("CHANGE") && StringUtils.isNotEmpty(prevFileNm)) {
            String paramIsFileChange = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isFileChange")));
            String isFileChange = StringUtils.defaultIfEmpty(paramIsFileChange, "N");

            if ((ptypeTpCd.equals("PLOT_PLAN") || ptypeTpCd.equals("FLOOR_PLAN") || ptypeTpCd.equals("HOUSCPLX_INFO")) && isFileChange.equals("Y")) {
                List<UploadFileInfo> fileList = new ArrayList<UploadFileInfo>();

                fileRootPath = xssUtil.replaceAll(StringUtils.defaultString(fileRootPath));
                fileContentsComplexUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsComplexUploadPath));

                String realPath = fileRootPath + fileContentsComplexUploadPath;

                FileUploadUtil fileUploadUtil = new FileUploadUtil();

                MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
                Iterator<String> fileNames = mpRequest.getFileNames();

                List<String> originalFileNameList = new ArrayList<>();
                List<String> thumbnailFileNameList = new ArrayList<>();

                while (fileNames.hasNext()) {
                    MultipartFile mpf = mpRequest.getFile(fileNames.next());
                    String realOriginalFileName = xssUtil.replaceTag(StringUtils.defaultString(mpf.getOriginalFilename()));
                    String originalFileName = fileUploadUtil.makeFileName(true, housingCplxPtype.getHouscplxCd(), imageType, true, realOriginalFileName);

                    if (mpf.getSize() > 0) {
                        UploadFileInfo originalUploadFileInfo = fileUploadUtil.uploadOriginalFile(mpf, realPath , originalFileName);

                        fileList.add(originalUploadFileInfo);
                        logger.info("\noriginalUploadFileInfo : {} ", originalUploadFileInfo.toString().replaceAll("\\]", "\n]").replaceAll("\\[", "\n[\n").replaceAll(",", ",\n"));

                        File thumbnailFile = fileUploadUtil.multipartToFile(mpf);
                        String thumbnailFileName = fileUploadUtil.makeFileName(true, housingCplxPtype.getHouscplxCd(), imageType, false, "");

                        String originalRealPath = realPath + "/" + originalFileName;

                        BufferedImage image = ImageIO.read(new FileInputStream(originalRealPath));
                        int originalWidth = image.getWidth();
                        int originalHeight = image.getHeight();

                        if (originalWidth > fileImagePtypeFixedWidth) {
                            double compareWidth = image.getWidth();
                            double rate = fileImagePtypeFixedWidth / compareWidth;

                            originalWidth = (int) Math.round(originalWidth * rate) ;
                            originalHeight = (int) Math.round(originalHeight * rate);
                        }

                        int extIndex = mpf.getOriginalFilename().lastIndexOf(".");
                        String fileExt = mpf.getOriginalFilename().substring(extIndex + 1);

                        fileUploadUtil.uploadThumbnailImageFile(thumbnailFile, realPath, thumbnailFileName, originalWidth, originalHeight, fileExt);

                        boolean isTempFileDeleted =  thumbnailFile.delete();

                        logger.debug("thumbnailFile AbsolutePath: " + thumbnailFile.getAbsolutePath());
                        logger.debug("thumbnailFile CanonicalPath: " + thumbnailFile.getCanonicalPath());
                        logger.debug("isTempDeleted is True? : " + BooleanUtils.isTrue(isTempFileDeleted));

                        originalFileNameList.add(originalFileName);
                        thumbnailFileNameList.add(thumbnailFileName);
                    }
                }

                Map<String, Object> editHousingCplxImageInfoMap = new HashMap<>();

                int fileListSize = originalFileNameList.size();

                if (fileListSize > 0) {
                    List<HousingCplxPtype> list = new ArrayList<HousingCplxPtype>();

                    for(int i=0; i<fileListSize; i++) {
                        String orgnlPlnfigFileUrl = "";
                        String reducePlnfigFileUrl = "";

                        if (ptypeTpCd.equals("PLOT_PLAN")) {
                            pipsServiceServerPtypePlotImageUrl = xssUtil.replaceAllOrg(StringUtils.defaultString(pipsServiceServerPtypePlotImageUrl));

                            orgnlPlnfigFileUrl = pipsServiceServerPtypePlotImageUrl + "&itype=original&houscplx_cd=" + houscplxCd + "&file_nm=" + originalFileNameList.get(i);
                            reducePlnfigFileUrl = pipsServiceServerPtypePlotImageUrl + "&itype=thumbnail&houscplx_cd=" + houscplxCd + "&file_nm=" + thumbnailFileNameList.get(i);
                        } else if (ptypeTpCd.equals("FLOOR_PLAN")) {
                            pipsServiceServerPtypeFloorImageUrl = xssUtil.replaceAllOrg(StringUtils.defaultString(pipsServiceServerPtypeFloorImageUrl));

                            orgnlPlnfigFileUrl = pipsServiceServerPtypeFloorImageUrl + "&itype=original&houscplx_cd=" + houscplxCd + "&file_nm=" + originalFileNameList.get(i);
                            reducePlnfigFileUrl = pipsServiceServerPtypeFloorImageUrl + "&itype=thumbnail&houscplx_cd=" + houscplxCd + "&file_nm=" + thumbnailFileNameList.get(i);
                        } else if (ptypeTpCd.equals("HOUSCPLX_INFO")) {
                            pipsServiceServerPtypeNoticeImageUrl = xssUtil.replaceAllOrg(StringUtils.defaultString(pipsServiceServerPtypeNoticeImageUrl));

                            orgnlPlnfigFileUrl = pipsServiceServerPtypeNoticeImageUrl + "&itype=original&houscplx_cd=" + houscplxCd + "&file_nm=" + originalFileNameList.get(i);
                            reducePlnfigFileUrl = pipsServiceServerPtypeNoticeImageUrl + "&itype=thumbnail&houscplx_cd=" + houscplxCd + "&file_nm=" + thumbnailFileNameList.get(i);
                        }

                        HousingCplxPtype editHousingCplxImageInfo = new HousingCplxPtype();

                        editHousingCplxImageInfo.setEditerId(adminId);
                        editHousingCplxImageInfo.setStosOrgnlPlnfigNm(originalFileNameList.get(i));
                        editHousingCplxImageInfo.setOrgnlPlnfigFileUrlCont(orgnlPlnfigFileUrl);
                        editHousingCplxImageInfo.setStosReducePlnfigNm(thumbnailFileNameList.get(i));
                        editHousingCplxImageInfo.setReducePlnfigFileUrlCont(reducePlnfigFileUrl);
                        editHousingCplxImageInfo.setPrevFileNm(prevFileNm);
                        editHousingCplxImageInfo.setHouscplxCd(houscplxCd);

                        if (ptypeTpCd.equals("FLOOR_PLAN")) {
                            String paramPtypeNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("ptypeNm")));
                            String paramPtypeDimQty = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("ptypeDimQty")));
                            String paramSupDimQty = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("supDimQty")));

                            String ptypeNm = StringUtils.defaultIfEmpty(paramPtypeNm, "");
                            String ptypeDimQty = StringUtils.defaultIfEmpty(paramPtypeDimQty, "");
                            String supDimQty = StringUtils.defaultIfEmpty(paramSupDimQty, "");

                            editHousingCplxImageInfo.setPtypeNm(ptypeNm);
                            editHousingCplxImageInfo.setPtypeDimQty(ptypeDimQty);
                            editHousingCplxImageInfo.setSupDimQty(supDimQty);
                        }

                        list.add(editHousingCplxImageInfo);

                    }
                    editHousingCplxImageInfoMap.put("list", list);

                }
                housingCplxService.updateHousingCplxImageInfo(editHousingCplxImageInfoMap, ptypeTpCd);
            } else if (ptypeTpCd.equals("FLOOR_PLAN") && isFileChange.equals("N")) {
                String paramPtypeNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("ptypeNm")));
                String paramPtypeDimQty = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("ptypeDimQty")));
                String paramSupDimQty = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("supDimQty")));

                String ptypeNm = StringUtils.defaultIfEmpty(paramPtypeNm, "");
                String ptypeDimQty = StringUtils.defaultIfEmpty(paramPtypeDimQty, "");
                String supDimQty = StringUtils.defaultIfEmpty(paramSupDimQty, "");

                HousingCplxPtype editHousingCplxDataInfo = new HousingCplxPtype();

                editHousingCplxDataInfo.setEditerId(adminId);
                editHousingCplxDataInfo.setHouscplxCd(houscplxCd);
                editHousingCplxDataInfo.setPtypeNm(ptypeNm);
                editHousingCplxDataInfo.setPtypeDimQty(ptypeDimQty);
                editHousingCplxDataInfo.setSupDimQty(supDimQty);
                editHousingCplxDataInfo.setPrevFileNm(prevFileNm);

                housingCplxService.updateHousingCplxImageDataInfo(editHousingCplxDataInfo);
            }
        } else if (requestType.equals("DELETE")) {
            String removeFileList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("removeFileList")));
            removeFileList = StringUtils.defaultIfEmpty(removeFileList, "");

            JSONArray removeFileJsonArray = new JSONArray(removeFileList);
            Map<String, Object> removeFileMap = new HashMap<String, Object>();
            int length = removeFileJsonArray.length();

            if (length > 0) {
                List<HousingCplxPtype> list = new ArrayList<HousingCplxPtype>();

                for(int i=0; i<length; i++) {
                    HousingCplxPtype deleteHousingCplxPtypeInfo = new HousingCplxPtype();

                    String paramStosReducePlnfigNm = xssUtil.replaceAll(StringUtils.defaultString(removeFileJsonArray.getJSONObject(i).getString("fileNm")));
                    String paramOrdNo = xssUtil.replaceAll(StringUtils.defaultString(removeFileJsonArray.getJSONObject(i).getString("ordNo")));

                    String stosReducePlnfigNm = StringUtils.defaultIfEmpty(paramStosReducePlnfigNm, "");
                    String ordNo = StringUtils.defaultIfEmpty(paramOrdNo, "");

                    deleteHousingCplxPtypeInfo.setEditerId(adminId);
                    deleteHousingCplxPtypeInfo.setHouscplxCd(houscplxCd);
                    deleteHousingCplxPtypeInfo.setStosReducePlnfigNm(stosReducePlnfigNm);
                    deleteHousingCplxPtypeInfo.setOrdNo(Integer.parseInt(ordNo));
                    deleteHousingCplxPtypeInfo.setPtypeTpCd(ptypeTpCd);

                    list.add(deleteHousingCplxPtypeInfo);
                }
                removeFileMap.put("list", list);
            }

            housingCplxService.deleteHousingCplxImageInfo(removeFileMap);
        }

        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("ptypeTpCd", ptypeTpCd);

        return resultUrl;
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지배치도 or 타입별 평면도 정보 이미지 순서 정보 수정
     * 시스템 및 단지 관리자용 단지배치도 or 타입별 평면도 정보 이미지 순서 정보 수정
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "editHousingCplxImageAddInfoAction", method = RequestMethod.POST)
    public String editHousingCplxImageAddInfoAction(Model model, HttpServletRequest request) throws IOException {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramPtypeTpCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("ptypeTpCd")));
        String ptypeTpCd = StringUtils.defaultIfEmpty(paramPtypeTpCd, "");

        String resultUrl = "redirect:/" + thisUrl;

        if (ptypeTpCd.equals("PLOT_PLAN")) {
            resultUrl += "/plot/edit";
        } else if (ptypeTpCd.equals("FLOOR_PLAN")) {
            resultUrl += "/floor/edit";
        } else if (ptypeTpCd.equals("HOUSCPLX_INFO")) {
            resultUrl += "/notice/edit";
        }

        String paramOrderFileList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("orderFileList")));
        String orderFileList = StringUtils.defaultIfEmpty(paramOrderFileList, "");

        JSONArray orderFileJsonArray = new JSONArray(orderFileList);
        Map<String, Object> orderFileMap = new HashMap<String, Object>();
        int orderFileLength = orderFileJsonArray.length();

        if (orderFileLength > 0) {
            List<HousingCplxPtype> list = new ArrayList<HousingCplxPtype>();

            for(int i=0; i<orderFileLength; i++) {
                HousingCplxPtype orderHousingCplxPtypeInfo = new HousingCplxPtype();
                String paramStosReducePlnfigNm = xssUtil.replaceAll(StringUtils.defaultString(orderFileJsonArray.getJSONObject(i).getString("fileNm")));
                String paramOrdNo = xssUtil.replaceAll(StringUtils.defaultString(orderFileJsonArray.getJSONObject(i).getString("ordNo")));

                String stosReducePlnfigNm = StringUtils.defaultIfEmpty(paramStosReducePlnfigNm, "");
                String ordNo = StringUtils.defaultIfEmpty(paramOrdNo, "");

                orderHousingCplxPtypeInfo.setEditerId(adminId);
                orderHousingCplxPtypeInfo.setHouscplxCd(houscplxCd);
                orderHousingCplxPtypeInfo.setStosReducePlnfigNm(stosReducePlnfigNm);
                orderHousingCplxPtypeInfo.setOrdNo(Integer.parseInt(ordNo));
                orderHousingCplxPtypeInfo.setPtypeTpCd(ptypeTpCd);

                list.add(orderHousingCplxPtypeInfo);
            }
            orderFileMap.put("list", list);
        }

        housingCplxService.updateHousingCplxImageOrder(orderFileMap);

        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("ptypeTpCd", ptypeTpCd);

        return resultUrl;
    }

    /**
     * 단지 관리 > 단지정보 관리 > 세대별 평형 상세
     * 시스템 및 단지 관리자용 세대별 평형 상세 조회
     * @param household
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "householdPtype/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String householdPtypeView(Household household, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String beanParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(household.getHouscplxCd()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        if (StringUtils.isEmpty(beanParamHouscplxCd)) {
            household.setHouscplxCd(houscplxCd);
        }

        String paramDongNo = xssUtil.replaceAll(StringUtils.defaultString(household.getDongNo()));
        String dongNo = StringUtils.defaultIfEmpty(paramDongNo, "");

        model.addAttribute("householdDongList", housingCplxService.getHouseholdDongList(household));
        model.addAttribute("household", housingCplxService.getHouseholdDetail(household));
        model.addAttribute("householdCnt", housingCplxService.getHouseholdCnt(household));
        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("dongNo", StringUtils.defaultIfEmpty(dongNo,"all"));

        return thisUrl + "/householdPtype/view";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 세대별 평형 수정
     * 시스템 및 단지 관리자용 세대별 평형 수정
     * @param household
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "householdPtype/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String householdPtypeEdit(Household household, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("household", housingCplxService.getHouseholdDetail(household));
        model.addAttribute("householdCnt", housingCplxService.getHouseholdCnt(household));

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/householdPtype/edit";
    }


    /**
     * 단지 관리 > 단지정보 관리 > 세대별 평형 등록
     * 시스템 및 단지 관리자용 세대별 평형 정보 등록
     * @param household
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "householdPtype/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addressAdd(Household household, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/householdPtype/add";
    }


    /**
     * 단지 관리 > 단지정보 관리 > 세대별 평형 수정
     * 시스템 및 단지 관리자용 세대별 평형 수정
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "editHouseholdPtypeAction", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    public String editHouseholdPtypeAction(Model model, HttpServletRequest request) throws IOException {
        SessionUser session = SessionUtil.getSessionUser(request);
        String resultUrl = "redirect:/cm/housingcplx/info/householdPtype/edit";
        boolean isDeleted = false;

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String householdList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("householdList")));
        householdList = StringUtils.defaultIfEmpty(householdList, "");

        JSONArray householdJsonArray = new JSONArray(householdList);
        Map<String, Object> householdMap = new HashMap<String, Object>();
        int length = householdJsonArray.length();

        if (length > 0) {
            List<Household> list = new ArrayList<Household>();

            for(int i=0; i<length; i++) {
                Household household = new Household();
                String paramDongNo = xssUtil.replaceAll(StringUtils.defaultString((String) householdJsonArray.getJSONObject(i).get("dongNo")));
                String paramHoseNo = xssUtil.replaceAll(StringUtils.defaultString((String) householdJsonArray.getJSONObject(i).get("hoseNo")));
                String paramPtypeNm = xssUtil.replaceAll(StringUtils.defaultString((String) householdJsonArray.getJSONObject(i).get("ptypeNm")));
                String paramDimQty = xssUtil.replaceAll(StringUtils.defaultString((String) householdJsonArray.getJSONObject(i).get("dimQty")));
                String paramCrerId = xssUtil.replaceAll(StringUtils.defaultString((String) householdJsonArray.getJSONObject(i).get("crerId")));

                String dongNo = StringUtils.defaultIfEmpty(paramDongNo, "");
                String hoseNo = StringUtils.defaultIfEmpty(paramHoseNo, "");
                String ptypeNm = StringUtils.defaultIfEmpty(paramPtypeNm, "");
                String dimQty = StringUtils.defaultIfEmpty(paramDimQty, "");
                String crerId = StringUtils.defaultIfEmpty(paramCrerId, "");

                String hsholdId = houscplxCd + "." + dongNo + "." + hoseNo;

                Integer parseDimQty = Integer.parseInt(dimQty);

                // 에너지 평형 구하기
                int value = parseDimQty;
                int mok = value / 5;
                int remainder = value % 5;
                int energyPvalue = 0;
                if (remainder >= 0 && remainder <= 2) {
                    energyPvalue = mok * 5;
                } else {
                    energyPvalue = mok * 5 + 5;
                }

                household.setEnrgDimQty(String.valueOf(energyPvalue));
                household.setHsholdId(hsholdId);
                household.setHouscplxCd(houscplxCd);
                household.setDongNo(dongNo);
                household.setHoseNo(hoseNo);
                household.setPtypeNm(ptypeNm);
                household.setDimQty(dimQty);
                household.setCrerId(crerId);
                household.setEditerId(crerId);

                list.add(household);
            }
            householdMap.put("list", list);

            housingCplxService.updateHousehold(householdMap);

            isDeleted = true;
        }

        model.addAttribute("isDeleted", isDeleted);
        model.addAttribute("houscplxCd", houscplxCd);

        String pageType = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("pagename")));
        pageType = StringUtils.defaultIfEmpty(pageType, "");

        if (pageType.equals("add")){
            resultUrl = "redirect:/cm/housingcplx/info/householdPtype/view";
        }
        if (pageType.equals("update")){
            resultUrl = "redirect:/cm/housingcplx/info/householdPtype/view";
        }

        return resultUrl;
    }

    /**
     * 단지 관리 > 단지정보 관리 > 세대별 평형 삭제 (개별 or 전체)
     * 시스템 및 단지 관리자용 세대별 평형 삭제 (개별 or 전체)
     * @param model
     * @param result
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "deleteHouseholdPtypeAction", method = RequestMethod.POST)
    public String deleteHouseholdPtypeAction(Household household, Model model, BindingResult result,
                                           HttpServletRequest request) throws IOException {
        SessionUser session = SessionUtil.getSessionUser(request);
        String resultUrl = "redirect:/cm/housingcplx/info/householdPtype/view";
        String resultMsg = "";
        boolean isDeleted = false;

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        if (StringUtils.isEmpty(houscplxCd)) {
            result.addError(new FieldError("houscplxCd", "houscplxCd", MessageUtil.getMessage("notEmpty.houscplxCd.houscplxCd")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        String paramIsAll = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isAll")));
        String isAll = StringUtils.defaultIfEmpty(paramIsAll, "");

        if (isAll.equals("Y")) {
            if (housingCplxService.getHouseholdCnt(household) > 0) {
                result.addError(new FieldError("deleteAllHousehold", "deleteAllHousehold", MessageUtil.getMessage("notEmpty.deleteAllHousehold.deleteAllHousehold")));
                logger.debug("result.hasErrors()=>{}", result.getFieldError());

                model.addAttribute("deleteHouseholdPtypeAction", null);
                model.addAttribute("resultMsg", resultMsg);

                if (result.hasErrors()) {
                    logger.debug("result.hasErrors()=>{}", result.getFieldError());
                    model.addAttribute("resultMsg", resultMsg);

                    return resultUrl;
                }
            } else if (housingCplxService.getHouseholdCnt(household) == 0) {
                household.setHouscplxCd(houscplxCd);
                housingCplxService.deleteHouseholdPtype(household);

                resultUrl = "redirect:/cm/housingcplx/info/householdPtype/view";
                isDeleted = true;
            }
        } else if (isAll.equals("N")) {
            String parmaHsholdId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("hsholdId")));
            String hsholdId = StringUtils.defaultIfEmpty(parmaHsholdId, "");

            if (StringUtils.isEmpty(houscplxCd)) {
                result.addError(new FieldError("houscplxCd", "houscplxCd", MessageUtil.getMessage("notEmpty.houscplxCd.houscplxCd")));
            }

            if (StringUtils.isEmpty(hsholdId)) {
                result.addError(new FieldError("hsholdId", "hsholdId", MessageUtil.getMessage("notEmpty.hsholdId.hsholdId")));
            }

            if (result.hasErrors()) {
                logger.debug("result.hasErrors()=>{}", result.getFieldError());
                model.addAttribute("resultMsg", resultMsg);

                return resultUrl;
            }

            household.setHouscplxCd(houscplxCd);
            household.setHsholdId(hsholdId);
            housingCplxService.deleteHouseholdPtype(household);

            isDeleted = true;
        }

        model.addAttribute("isDeleted", isDeleted);
        model.addAttribute("houscplxCd", houscplxCd);

        if (isAll.equals("N")) {
            resultUrl = "redirect:/cm/housingcplx/info/householdPtype/edit";
        }

        return resultUrl;
    }

    /**
     * 단지 관리 > 단지정보 관리 > 관리실/경비실 정보 등록
     * 시스템 및 단지 관리자용 관리실/경비실 정보 등록
     * @param housingCplxCaddr
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "address/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addressAdd(HousingCplxCaddr housingCplxCaddr, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/address/add";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 관리실/경비실 등록처리
     * 시스템 및 단지 관리자용 관리실/경비실 등록처리
     * @param housingCplxCaddr
     * @param housingCplxCaddrGdnc
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "addAddressAction", method = RequestMethod.POST)
    public String addAddressAction(HousingCplxCaddr housingCplxCaddr, HousingCplxCaddrGdnc housingCplxCaddrGdnc,
                                   Model model, BindingResult result, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String resultUrl = thisUrl + "/address/view";
        String resultMsg = "";

        String paramIsMgmtOfc = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isMgmtOfc")));
        String paramIsScrtOfc = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isScrtOfc")));
        String paramIsLifeIcvncOfc = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isLifeIcvncOfc")));

        String isMgmtOfc = StringUtils.defaultIfEmpty(paramIsMgmtOfc, "N");
        String isScrtOfc = StringUtils.defaultIfEmpty(paramIsScrtOfc, "N");
        String isLifeIcvncOfc = StringUtils.defaultIfEmpty(paramIsLifeIcvncOfc, "N");

        Map<String, Object> mgmtOfcMap = new HashMap<String, Object>();
        Map<String, Object> scrtOfcMap = new HashMap<String, Object>();
        Map<String, Object> lifeIcvncOfcMap = new HashMap<String, Object>();

        if (isMgmtOfc.equals("Y")) {
            String mgmtOfcList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("mgmtOfcList")));
            mgmtOfcList = StringUtils.defaultIfEmpty(mgmtOfcList, "");

            if (mgmtOfcList == null) {
                result.addError(new FieldError("mgmtOfcList", "mgmtOfcList", MessageUtil.getMessage("notEmpty.mgmtOfcList.mgmtOfcList")));
                logger.debug("result.hasErrors()=>{}", result.getFieldError());

                model.addAttribute("addAddressAction", null);
                model.addAttribute("resultMsg", resultMsg);
            } else {
                JSONArray mgmtOfcJsonArray = new JSONArray(mgmtOfcList);
                int mgmtOfcLength = mgmtOfcJsonArray.length();

                if (mgmtOfcLength > 0) {
                    List<HousingCplxCaddr> mgmtOfcCaddrList = new ArrayList<HousingCplxCaddr>();

                    for(int i=0; i<mgmtOfcLength; i++) {
                        HousingCplxCaddr housingCplxCaddrInfo = new HousingCplxCaddr();

                        String paramRem = xssUtil.replaceAll(StringUtils.defaultString((String) mgmtOfcJsonArray.getJSONObject(i).get("rem")));
                        String paramCaddrCont = xssUtil.replaceAll(StringUtils.defaultString((String) mgmtOfcJsonArray.getJSONObject(i).get("caddrCont")));
                        pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

                        String rem = StringUtils.defaultIfEmpty(paramRem, "");
                        String caddrCont = StringUtils.defaultIfEmpty(paramCaddrCont, "");

                        housingCplxCaddrInfo.setEncKey(pipsEncryptKey);
                        housingCplxCaddrInfo.setHouscplxCd(houscplxCd);
                        housingCplxCaddrInfo.setRem(rem);
                        housingCplxCaddrInfo.setCaddrTpCd("TEL");
                        housingCplxCaddrInfo.setWorkpTpCd("MGMT_OFC");
                        housingCplxCaddrInfo.setCaddrCont(caddrCont);
                        housingCplxCaddrInfo.setCntctStime("");
                        housingCplxCaddrInfo.setCntctEtime("");
                        housingCplxCaddrInfo.setCrerId(adminId);

                        mgmtOfcCaddrList.add(housingCplxCaddrInfo);
                    }
                    mgmtOfcMap.put("list", mgmtOfcCaddrList);
                }
            }
        }

        if (isScrtOfc.equals("Y")) {
            String scrtOfcList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("scrtOfcList")));
            scrtOfcList = StringUtils.defaultIfEmpty(scrtOfcList, "");

            if (scrtOfcList == null) {
                result.addError(new FieldError("scrtOfcList", "scrtOfcList", MessageUtil.getMessage("notEmpty.scrtOfcList.scrtOfcList")));
                logger.debug("result.hasErrors()=>{}", result.getFieldError());

                model.addAttribute("addAddressAction", null);
                model.addAttribute("resultMsg", resultMsg);
            } else {
                JSONArray scrtOfcJsonArray = new JSONArray(scrtOfcList);
                int scrtOfcLength = scrtOfcJsonArray.length();

                if (scrtOfcLength > 0) {
                    List<HousingCplxCaddr> scrtOfcCaddrList = new ArrayList<HousingCplxCaddr>();

                    for(int i=0; i<scrtOfcLength; i++) {
                        HousingCplxCaddr housingCplxCaddrInfo = new HousingCplxCaddr();

                        String paramRem = xssUtil.replaceAll(StringUtils.defaultString((String) scrtOfcJsonArray.getJSONObject(i).get("rem")));
                        String paramCaddrCont = xssUtil.replaceAll(StringUtils.defaultString((String) scrtOfcJsonArray.getJSONObject(i).get("caddrCont")));
                        pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

                        String rem = StringUtils.defaultIfEmpty(paramRem, "");
                        String caddrCont = StringUtils.defaultIfEmpty(paramCaddrCont, "");

                        housingCplxCaddrInfo.setEncKey(pipsEncryptKey);
                        housingCplxCaddrInfo.setHouscplxCd(houscplxCd);
                        housingCplxCaddrInfo.setRem(rem);
                        housingCplxCaddrInfo.setCaddrTpCd("TEL");
                        housingCplxCaddrInfo.setWorkpTpCd("SCRT_OFC");
                        housingCplxCaddrInfo.setCaddrCont(caddrCont);
                        housingCplxCaddrInfo.setCntctStime("");
                        housingCplxCaddrInfo.setCntctEtime("");
                        housingCplxCaddrInfo.setCrerId(adminId);

                        scrtOfcCaddrList.add(housingCplxCaddrInfo);
                    }
                    scrtOfcMap.put("list", scrtOfcCaddrList);
                }
            }
        }

        if (isLifeIcvncOfc.equals("Y")) {
            String lifeIcvncOfcList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("lifeIcvncOfcList")));
            lifeIcvncOfcList = StringUtils.defaultIfEmpty(lifeIcvncOfcList, "");

            if (lifeIcvncOfcList == null) {
                result.addError(new FieldError("lifeIcvncOfcList", "lifeIcvncOfcList", MessageUtil.getMessage("notEmpty.lifeIcvncOfcList.lifeIcvncOfcList")));
                logger.debug("result.hasErrors()=>{}", result.getFieldError());

                model.addAttribute("addAddressAction", null);
                model.addAttribute("resultMsg", resultMsg);
            } else {
                JSONArray lifeIcvncOfcJsonArray = new JSONArray(lifeIcvncOfcList);
                int lifeIcvncOfcLength = lifeIcvncOfcJsonArray.length();

                if (lifeIcvncOfcLength > 0) {
                    List<HousingCplxCaddr> lifeIcvncOfcCaddrList = new ArrayList<HousingCplxCaddr>();

                    for(int i=0; i<lifeIcvncOfcLength; i++) {
                        HousingCplxCaddr housingCplxCaddrInfo = new HousingCplxCaddr();

                        String paramCaddrCont = xssUtil.replaceAll(StringUtils.defaultString((String) lifeIcvncOfcJsonArray.getJSONObject(i).get("caddrCont")));
                        String paramCntctStime = xssUtil.replaceAll(StringUtils.defaultString((String) lifeIcvncOfcJsonArray.getJSONObject(i).get("cntctStime")));
                        String paramCntctEtime = xssUtil.replaceAll(StringUtils.defaultString((String) lifeIcvncOfcJsonArray.getJSONObject(i).get("cntctEtime")));
                        pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

                        String caddrCont = StringUtils.defaultIfEmpty(paramCaddrCont, "");
                        String cntctStime = StringUtils.defaultIfEmpty(paramCntctStime, "");
                        String cntctEtime = StringUtils.defaultIfEmpty(paramCntctEtime, "");

                        housingCplxCaddrInfo.setEncKey(pipsEncryptKey);
                        housingCplxCaddrInfo.setHouscplxCd(houscplxCd);
                        housingCplxCaddrInfo.setCaddrTpCd("TEL");
                        housingCplxCaddrInfo.setWorkpTpCd("LIFE_ICVNC_OFC");
                        housingCplxCaddrInfo.setCaddrCont(caddrCont);
                        housingCplxCaddrInfo.setCntctStime(cntctStime);
                        housingCplxCaddrInfo.setCntctEtime(cntctEtime);
                        housingCplxCaddrInfo.setCrerId(adminId);

                        lifeIcvncOfcCaddrList.add(housingCplxCaddrInfo);
                    }
                    lifeIcvncOfcMap.put("list",lifeIcvncOfcCaddrList);
                }
            }
        }

        Map<String, Object> descriptionMap = new HashMap<String, Object>();

        String descriptionList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("descriptionList")));
        descriptionList = StringUtils.defaultIfEmpty(descriptionList, "");

        if (descriptionList == null) {
            result.addError(new FieldError("descriptionList", "descriptionList", MessageUtil.getMessage("notEmpty.descriptionList.descriptionList")));
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("addAddressAction", null);
            model.addAttribute("resultMsg", resultMsg);
        } else {
            JSONArray descriptionJsonArray = new JSONArray(descriptionList);
            int descriptionLength = descriptionJsonArray.length();

            if (descriptionLength > 0) {
                List<HousingCplxCaddrGdnc> list = new ArrayList<HousingCplxCaddrGdnc>();

                for(int i=0; i<descriptionLength; i++) {
                    HousingCplxCaddrGdnc housingCplxCaddrGdncInfo = new HousingCplxCaddrGdnc();

                    String paramCont = xssUtil.replaceAll(StringUtils.defaultString((String) descriptionJsonArray.getJSONObject(i).get("cont")));
                    String paramWorkTpCd = xssUtil.replaceAll(StringUtils.defaultString((String) descriptionJsonArray.getJSONObject(i).get("workTpCd")));

                    String cont = StringUtils.defaultIfEmpty(paramCont, "");
                    String workTpCd = StringUtils.defaultIfEmpty(paramWorkTpCd, "");

                    housingCplxCaddrGdncInfo.setHouscplxCd(houscplxCd);
                    housingCplxCaddrGdncInfo.setCont(cont);
                    housingCplxCaddrGdncInfo.setWorkpTpCd(workTpCd);
                    housingCplxCaddrGdncInfo.setCrerId(adminId);

                    list.add(housingCplxCaddrGdncInfo);
                }

                if (lifeIcvncOfcMap.size() > 0) {
                    HousingCplxCaddrGdnc housingCplxCaddrGdncInfo = new HousingCplxCaddrGdnc();

                    housingCplxCaddrGdncInfo.setHouscplxCd(houscplxCd);
                    housingCplxCaddrGdncInfo.setCont("");
                    housingCplxCaddrGdncInfo.setWorkpTpCd("LIFE_ICVNC_OFC");
                    housingCplxCaddrGdncInfo.setCrerId(adminId);

                    list.add(housingCplxCaddrGdncInfo);
                }

                descriptionMap.put("list", list);
            }
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        } else {
            housingCplxService.insertHousingCplxCaddrInfo(mgmtOfcMap, scrtOfcMap, lifeIcvncOfcMap, descriptionMap);
        }

        model.addAttribute("houscplxCd", houscplxCd);

        return "redirect:/cm/housingcplx/info/address/view";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 관리실/경비실 상세
     * 시스템 및 단지 관리자용 관리실/경비실 상세 조회
     * @param housingCplxCaddr
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "address/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String addressView(HousingCplxCaddr housingCplxCaddr, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("housingCplxCaddr", housingCplxService.getHousingCplxCaddrDetail(housingCplxCaddr));
        model.addAttribute("housingCplxCaddrDescription", housingCplxService.getHousingCplxCaddrGdncDetail(housingCplxCaddr));

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/address/view";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 관리실/경비실 수정
     * 시스템 및 단지 관리자용 관리실/경비실 수정
     * @param housingCplxCaddr
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "address/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String addressEdit(HousingCplxCaddr housingCplxCaddr, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("housingCplxCaddr", housingCplxService.getHousingCplxCaddrDetail(housingCplxCaddr));
        model.addAttribute("housingCplxCaddrDescription", housingCplxService.getHousingCplxCaddrGdncDetail(housingCplxCaddr));

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd",houscplxCd);

        return thisUrl + "/address/edit";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 관리실/경비실 수정처리
     * 시스템 및 단지 관리자용 관리실/경비실 수정처리
     * @param housingCplxCaddr
     * @param housingCplxCaddrGdnc
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "editAddressAction", method = RequestMethod.POST)
    public String editAddressAction(HousingCplxCaddr housingCplxCaddr, HousingCplxCaddrGdnc housingCplxCaddrGdnc,
                                    Model model, BindingResult result, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String resultUrl = "redirect:/" + thisUrl + "/address/view";
        String resultMsg = "";

        String mgmtOfcList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("mgmtOfcList")));
        mgmtOfcList = StringUtils.defaultIfEmpty(mgmtOfcList, "");

        if (mgmtOfcList == null) {
            result.addError(new FieldError("mgmtOfcList", "mgmtOfcList", MessageUtil.getMessage("notEmpty.mgmtOfcList.mgmtOfcList")));
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("editAddressAction", null);
            model.addAttribute("resultMsg", resultMsg);
        }

        String scrtOfcList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("scrtOfcList")));
        scrtOfcList = StringUtils.defaultIfEmpty(scrtOfcList, "");

        if (scrtOfcList == null) {
            result.addError(new FieldError("scrtOfcList", "scrtOfcList", MessageUtil.getMessage("notEmpty.scrtOfcList.scrtOfcList")));
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("editAddressAction", null);
            model.addAttribute("resultMsg", resultMsg);
        }

        String lifeIcvncOfcList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("lifeIcvncOfcList")));
        lifeIcvncOfcList = StringUtils.defaultIfEmpty(lifeIcvncOfcList, "");

        if (lifeIcvncOfcList == null) {
            result.addError(new FieldError("lifeIcvncOfcList", "lifeIcvncOfcList", MessageUtil.getMessage("notEmpty.lifeIcvncOfcList.lifeIcvncOfcList")));
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("editAddressAction", null);
            model.addAttribute("resultMsg", resultMsg);
        }

        String descriptionList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("descriptionList")));
        descriptionList = StringUtils.defaultIfEmpty(descriptionList, "");

        if (descriptionList == null) {
            result.addError(new FieldError("descriptionList", "descriptionList", MessageUtil.getMessage("notEmpty.descriptionList.descriptionList")));
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("editAddressAction", null);
            model.addAttribute("resultMsg", resultMsg);
        }

        JSONArray mgmtOfcJsonArray = new JSONArray(mgmtOfcList);

        int mgmtOfcLength = mgmtOfcJsonArray.length();

        Map<String, Object> mgmtOfcMap = new HashMap<String, Object>();
        Map<String, Object> scrtOfcMap = new HashMap<String, Object>();
        Map<String, Object> lifeIcvncOfcMap = new HashMap<String, Object>();
        Map<String, Object> descriptionMap = new HashMap<String, Object>();

        if (mgmtOfcLength > 0) {
            List<HousingCplxCaddr> mgmtOfcCaddrList = new ArrayList<HousingCplxCaddr>();
            String ordNo;
            Integer parseOrdNo = null;

            for(int i=0; i<mgmtOfcLength; i++) {
                HousingCplxCaddr housingCplxCaddrInfo = new HousingCplxCaddr();

                String paramRem = xssUtil.replaceAll(StringUtils.defaultString((String) mgmtOfcJsonArray.getJSONObject(i).get("rem")));
                String paramCaddrCont = xssUtil.replaceAll(StringUtils.defaultString((String) mgmtOfcJsonArray.getJSONObject(i).get("caddrCont")));
                String paramIsNew = xssUtil.replaceAll(StringUtils.defaultString((String) mgmtOfcJsonArray.getJSONObject(i).get("isNew")));
                pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

                String rem = StringUtils.defaultIfEmpty(paramRem, "");
                String caddrCont = StringUtils.defaultIfEmpty(paramCaddrCont, "");
                String isNew = StringUtils.defaultIfEmpty(paramIsNew, "");

                housingCplxCaddrInfo.setEncKey(pipsEncryptKey);
                housingCplxCaddrInfo.setHouscplxCd(houscplxCd);
                housingCplxCaddrInfo.setRem(rem);
                housingCplxCaddrInfo.setCaddrTpCd("TEL");
                housingCplxCaddrInfo.setWorkpTpCd("MGMT_OFC");
                housingCplxCaddrInfo.setCaddrCont(caddrCont);
                housingCplxCaddrInfo.setCntctStime("");
                housingCplxCaddrInfo.setCntctEtime("");

                if (isNew.equals("N")){
                    String paramOrdNo = xssUtil.replaceAll(StringUtils.defaultString((String) mgmtOfcJsonArray.getJSONObject(i).get("ordNo")));
                    ordNo = StringUtils.defaultIfEmpty(paramOrdNo, "");
                    parseOrdNo = Integer.parseInt(ordNo);

                    housingCplxCaddrInfo.setOrdNo(parseOrdNo);
                    housingCplxCaddrInfo.setEditerId(adminId);
                } else if (isNew.equals("Y")) {
                    housingCplxCaddrInfo.setCrerId(adminId);
                }

                mgmtOfcCaddrList.add(housingCplxCaddrInfo);
            }
            mgmtOfcMap.put("list", mgmtOfcCaddrList);
        }

        JSONArray scrtOfcJsonArray = new JSONArray(scrtOfcList);
        int scrtOfcLength = scrtOfcJsonArray.length();

        if (scrtOfcLength > 0) {
            List<HousingCplxCaddr> scrtOfcCaddrList = new ArrayList<HousingCplxCaddr>();
            String ordNo;
            Integer parseOrdNo = null;

            for(int i=0; i<scrtOfcLength; i++) {
                HousingCplxCaddr housingCplxCaddrInfo = new HousingCplxCaddr();

                String paramRem = xssUtil.replaceAll(StringUtils.defaultString((String) scrtOfcJsonArray.getJSONObject(i).get("rem")));
                String paramCaddrCont = xssUtil.replaceAll(StringUtils.defaultString((String) scrtOfcJsonArray.getJSONObject(i).get("caddrCont")));
                String paramIsNew = xssUtil.replaceAll(StringUtils.defaultString((String) scrtOfcJsonArray.getJSONObject(i).get("isNew")));
                pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

                String rem = StringUtils.defaultIfEmpty(paramRem, "");
                String caddrCont = StringUtils.defaultIfEmpty(paramCaddrCont, "");
                String isNew = StringUtils.defaultIfEmpty(paramIsNew, "");

                housingCplxCaddrInfo.setEncKey(pipsEncryptKey);
                housingCplxCaddrInfo.setHouscplxCd(houscplxCd);
                housingCplxCaddrInfo.setRem(rem);
                housingCplxCaddrInfo.setCaddrTpCd("TEL");
                housingCplxCaddrInfo.setWorkpTpCd("SCRT_OFC");
                housingCplxCaddrInfo.setCaddrCont(caddrCont);
                housingCplxCaddrInfo.setCntctStime("");
                housingCplxCaddrInfo.setCntctEtime("");

                if (isNew.equals("N")){
                    String paramOrdNo = xssUtil.replaceAll(StringUtils.defaultString((String) scrtOfcJsonArray.getJSONObject(i).get("ordNo")));
                    ordNo = StringUtils.defaultIfEmpty(paramOrdNo, "");
                    parseOrdNo = Integer.parseInt(ordNo);

                    housingCplxCaddrInfo.setOrdNo(parseOrdNo);
                    housingCplxCaddrInfo.setEditerId(adminId);
                } else if (isNew.equals("Y")) {
                    housingCplxCaddrInfo.setCrerId(adminId);
                }

                scrtOfcCaddrList.add(housingCplxCaddrInfo);
            }
            scrtOfcMap.put("list", scrtOfcCaddrList);
        }

        JSONArray lifeIcvncOfcJsonArray = new JSONArray(lifeIcvncOfcList);
        int lifeIcvncOfcLength = lifeIcvncOfcJsonArray.length();

        if (lifeIcvncOfcLength > 0) {
            List<HousingCplxCaddr> lifeIcvncOfcCaddrList = new ArrayList<HousingCplxCaddr>();
            String ordNo;
            Integer parseOrdNo = null;

            for(int i=0; i<lifeIcvncOfcLength; i++) {
                HousingCplxCaddr housingCplxCaddrInfo = new HousingCplxCaddr();

                String paramCaddrCont = xssUtil.replaceAll(StringUtils.defaultString((String) lifeIcvncOfcJsonArray.getJSONObject(i).get("caddrCont")));
                String paramCntctStime = xssUtil.replaceAll(StringUtils.defaultString((String) lifeIcvncOfcJsonArray.getJSONObject(i).get("cntctStime")));
                String paramCntctEtime = xssUtil.replaceAll(StringUtils.defaultString((String) lifeIcvncOfcJsonArray.getJSONObject(i).get("cntctEtime")));
                String paramIsNew = xssUtil.replaceAll(StringUtils.defaultString((String) lifeIcvncOfcJsonArray.getJSONObject(i).get("isNew")));
                pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

                String caddrCont = StringUtils.defaultIfEmpty(paramCaddrCont, "");
                String cntctStime = StringUtils.defaultIfEmpty(paramCntctStime, "");
                String cntctEtime = StringUtils.defaultIfEmpty(paramCntctEtime, "");
                String isNew = StringUtils.defaultIfEmpty(paramIsNew, "");

                housingCplxCaddrInfo.setEncKey(pipsEncryptKey);
                housingCplxCaddrInfo.setHouscplxCd(houscplxCd);
                housingCplxCaddrInfo.setCaddrTpCd("TEL");
                housingCplxCaddrInfo.setWorkpTpCd("LIFE_ICVNC_OFC");
                housingCplxCaddrInfo.setCaddrCont(caddrCont);
                housingCplxCaddrInfo.setCntctStime(cntctStime);
                housingCplxCaddrInfo.setCntctEtime(cntctEtime);

                if (isNew.equals("N")){
                    String paramOrdNo = xssUtil.replaceAll(StringUtils.defaultString((String) lifeIcvncOfcJsonArray.getJSONObject(i).get("ordNo")));
                    ordNo = StringUtils.defaultIfEmpty(paramOrdNo, "");
                    parseOrdNo = Integer.parseInt(ordNo);

                    housingCplxCaddrInfo.setOrdNo(parseOrdNo);
                    housingCplxCaddrInfo.setEditerId(adminId);
                } else if (isNew.equals("Y")) {
                    housingCplxCaddrInfo.setCrerId(adminId);
                }

                lifeIcvncOfcCaddrList.add(housingCplxCaddrInfo);
            }
            lifeIcvncOfcMap.put("list",lifeIcvncOfcCaddrList);
        }

        JSONArray descriptionJsonArray = new JSONArray(descriptionList);
        int descriptionLength = descriptionJsonArray.length();

        if (descriptionLength > 0) {
            List<HousingCplxCaddrGdnc> list = new ArrayList<HousingCplxCaddrGdnc>();

            for(int i=0; i<descriptionLength; i++) {
                HousingCplxCaddrGdnc housingCplxCaddrGdncInfo = new HousingCplxCaddrGdnc();

                String paramCont = xssUtil.replaceAll(StringUtils.defaultString((String) descriptionJsonArray.getJSONObject(i).get("cont")));
                String paramWorkTpCd = xssUtil.replaceAll(StringUtils.defaultString((String) descriptionJsonArray.getJSONObject(i).get("workTpCd")));

                String cont = StringUtils.defaultIfEmpty(paramCont, "");
                String workTpCd = StringUtils.defaultIfEmpty(paramWorkTpCd, "");

                housingCplxCaddrGdncInfo.setHouscplxCd(houscplxCd);
                housingCplxCaddrGdncInfo.setCont(cont);
                housingCplxCaddrGdncInfo.setWorkpTpCd(workTpCd);
                housingCplxCaddrGdncInfo.setEditerId(adminId);

                list.add(housingCplxCaddrGdncInfo);
            }
            descriptionMap.put("list", list);
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            resultUrl = "redirect:/" + thisUrl + "/address/edit";

            return resultUrl;
        } else {
            housingCplxService.updateHousingCplxCaddrInfo(mgmtOfcMap, scrtOfcMap, lifeIcvncOfcMap, descriptionMap);
        }

        model.addAttribute("houscplxCd", houscplxCd);

        return resultUrl;
    }

    /**
     * 단지 관리 > 단지정보 관리 > 경비실/관리실/생활불편신고 삭제 (개별 or 전체)
     * 시스템 및 단지 관리자용 경비실/관리실/생활불편신고 삭제 (개별 or 전체)
     * @param model
     * @param result
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "deleteAddressAction", method = RequestMethod.POST)
    public String deleteAddressAction(HousingCplxCaddr housingCplxCaddr, Model model, BindingResult result,
                                      HttpServletRequest request) throws IOException {
        SessionUser session = SessionUtil.getSessionUser(request);
        String resultUrl = "redirect:/" + thisUrl + "/address/edit";
        String resultMsg = "";
        boolean isDeleted = false;

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        if (StringUtils.isEmpty(houscplxCd)) {
            result.addError(new FieldError("houscplxCd", "houscplxCd", MessageUtil.getMessage("notEmpty.houscplxCd.houscplxCd")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        String paramIsAll = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isAll")));
        String isAll = StringUtils.defaultIfEmpty(paramIsAll, "");

        if (isAll.equals("Y")) {
            housingCplxCaddr.setHouscplxCd(houscplxCd);
            housingCplxCaddr.setEditerId(adminId);
            housingCplxService.deleteAddress(housingCplxCaddr);

            resultUrl = "redirect:/" + thisUrl + "/address/view";
            isDeleted = true;
        } else if (isAll.equals("N")) {

            String paramWorkTpCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("workTpCd")));
            String paramOrdNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("ordNo")));

            String workTpCd = StringUtils.defaultIfEmpty(paramWorkTpCd, "");
            String ordNo = StringUtils.defaultIfEmpty(paramOrdNo, "");

            if (StringUtils.isEmpty(workTpCd)) {
                result.addError(new FieldError("workTpCd", "workTpCd", MessageUtil.getMessage("notEmpty.workTpCd.workTpCd")));
            }

            if (StringUtils.isEmpty(ordNo)) {
                result.addError(new FieldError("ordNo", "ordNo", MessageUtil.getMessage("notEmpty.ordNo.ordNo")));
            }

            if (result.hasErrors()) {
                logger.debug("result.hasErrors()=>{}", result.getFieldError());
                model.addAttribute("resultMsg", resultMsg);

                return resultUrl;
            }

            Integer parseOrdNo = null;
            parseOrdNo = Integer.parseInt(ordNo);

            if (workTpCd.equals("MGMT_OFC") || workTpCd.equals("SCRT_OFC")) {
                if (parseOrdNo == 0) {
                    housingCplxCaddr.setWorkpTpCd(workTpCd);
                    housingCplxCaddr.setHouscplxCd(houscplxCd);
                    housingCplxService.deleteAddressDescription(housingCplxCaddr);
                } else {
                    housingCplxCaddr.setEditerId(adminId);
                    housingCplxCaddr.setHouscplxCd(houscplxCd);
                    housingCplxCaddr.setWorkpTpCd(workTpCd);
                    housingCplxCaddr.setOrdNo(parseOrdNo);
                    housingCplxService.deleteAddress(housingCplxCaddr);
                }
            } else if (workTpCd.equals("LIFE_ICVNC_OFC")) {

                String paramCount = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("count")));
                String count = StringUtils.defaultIfEmpty(paramCount, "");
                Integer parseCount = null;
                parseCount = Integer.parseInt(count);

                housingCplxCaddr.setEditerId(adminId);
                housingCplxCaddr.setHouscplxCd(houscplxCd);
                housingCplxCaddr.setWorkpTpCd(workTpCd);
                housingCplxCaddr.setOrdNo(parseOrdNo);
                housingCplxService.deleteAddress(housingCplxCaddr);

                if (parseCount == 1) {
                    housingCplxService.deleteAddressDescription(housingCplxCaddr);
                }
            }

            isDeleted = true;
        }

        model.addAttribute("isDeleted", isDeleted);
        model.addAttribute("houscplxCd", houscplxCd);

        return resultUrl;
    }



    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 엑셀 다운로드
     * 시스템 및 단지 관리자용 시설업체 엑셀 다운로드
     * @param facilityBizco
     * @param request
     * @return
     */
    @RequestMapping(value = "facilityInfo/excel/list", method = {RequestMethod.GET, RequestMethod.POST},produces = "application/text; charset=utf8")
    @ResponseBody
    public String facilityInfoExcelList(FacilityBizco facilityBizco, HttpServletRequest request) {
        String paramDelYn = xssUtil.replaceAll(StringUtils.defaultString(facilityBizco.getDelYn()));
        String delYn = StringUtils.defaultIfEmpty(paramDelYn, "N");
        String result = "";

        if (StringUtils.isEmpty(delYn)) {
            JSONObject jsonResult = new JSONObject();
            JSONArray params = new JSONArray();
            JSONObject param = new JSONObject();

            param.put("id", "delYn");
            param.put("msg", MessageUtil.getMessage("notEmpty.delYn.delYn"));
            params.put(param);

            jsonResult.put("status", false);
            jsonResult.put("params", params);

            return jsonResult.toString();
        } else {
            pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

            facilityBizco.setEncKey(pipsEncryptKey);
            List<FacilityBizco> facilityBizcoExcelList = facilityService.getFacilityInfoExcelList(facilityBizco);
            String facilityBizcoExcelJsonArray = JsonUtil.toJsonNotZero(facilityBizcoExcelList);

            result = facilityBizcoExcelJsonArray;
        }

        return result;
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 등록
     * 시스템 및 단지 관리자용 시설업체 등록
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "facilityInfo/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String facilityInfoAdd(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/facilityInfo/add";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 등록처리
     * 시스템 및 단지 관리자용 시설업체 등록처리
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "addFacilityInfoAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String addFacilityInfoAction(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String facilityInfoList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facilityInfoList")));
        facilityInfoList = StringUtils.defaultIfEmpty(facilityInfoList, "");

        JSONArray facilityInfoJsonArray = new JSONArray(facilityInfoList);
        int length = facilityInfoJsonArray.length();

        if (length > 0) {
            for(int i=0; i<length; i++) {
                List<FacilityBizcoCaddr> addressList = new ArrayList<>();

                JSONObject facilityInfoJsonObject = facilityInfoJsonArray.getJSONObject(i);
                JSONArray facilityAddressInfoArray = facilityInfoJsonObject.getJSONArray("addressList");

                String facltBizcoTpNm = xssUtil.replaceAll(StringUtils.defaultString(facilityInfoJsonObject.getString("fa")));
                String twbsNm = xssUtil.replaceAll(StringUtils.defaultString(facilityInfoJsonObject.getString("tw")));
                String bizcoNm = xssUtil.replaceAll(StringUtils.defaultString(facilityInfoJsonObject.getString("biz")));
                String conCont = xssUtil.replaceAll(StringUtils.defaultString(facilityInfoJsonObject.getString("con")));

                FacilityBizco facilityBizco = new FacilityBizco();
                facilityBizco.setFacltBizcoTpNm(StringUtils.defaultIfEmpty(facltBizcoTpNm, ""));
                facilityBizco.setTwbsNm(StringUtils.defaultIfEmpty(twbsNm, ""));
                facilityBizco.setBizcoNm(StringUtils.defaultIfEmpty(bizcoNm, ""));
                facilityBizco.setConCont(StringUtils.defaultIfEmpty(conCont, ""));
                facilityBizco.setMgmTpCd("COMPLEX_MGMT");
                facilityBizco.setCrerId(adminId);

                for (int j=0; j<facilityAddressInfoArray.length(); j++) {
                    JSONObject facilityAddressJsonObject = facilityAddressInfoArray.getJSONObject(j);

                    String perchrgNm = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("per")));
                    String mphoneNo = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("mp")));
                    String offcPhoneNo = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("offc")));
                    String faxNo = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("fax")));

                    FacilityBizcoCaddr facilityBizcoCaddr = new FacilityBizcoCaddr();
                    facilityBizcoCaddr.setPerchrgNm(StringUtils.defaultIfEmpty(perchrgNm, ""));
                    facilityBizcoCaddr.setMphoneNo(StringUtils.defaultIfEmpty(mphoneNo, ""));
                    facilityBizcoCaddr.setOffcPhoneNo(StringUtils.defaultIfEmpty(offcPhoneNo, ""));
                    facilityBizcoCaddr.setFaxNo(StringUtils.defaultIfEmpty(faxNo, ""));
                    facilityBizcoCaddr.setMgmTpCd("COMPLEX_MGMT");
                    facilityBizcoCaddr.setCrerId(adminId);

                    addressList.add(facilityBizcoCaddr);
                }

                facilityService.insertFacilityInfo(facilityBizco, addressList, houscplxCd);
            }
        }

        model.addAttribute("houscplxCd", houscplxCd);

        return "redirect:/" + thisUrl + "/facilityInfo/view";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 상세
     * 시스템 및 단지 관리자용 시설업체 상세 조회
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "facilityInfo/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String facilityInfoView(FacilityBizco facilityBizco ,Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("facilityInfoList", facilityService.getFacilityInfoList(facilityBizco));

        return thisUrl + "/facilityInfo/view";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 상세 연락처
     * 시스템 및 단지 관리자용 시설업체 상세 연락처 조회
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "facilityInfo/detailView", method = {RequestMethod.GET, RequestMethod.POST})
    public String facilityInfoDetailView(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramFacltBizcoid = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoId")));
        String facltBizcoId = StringUtils.defaultIfEmpty(paramFacltBizcoid, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        Integer parseFacltBizcoId = Integer.parseInt(facltBizcoId);

        pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

        FacilityBizco facilityBizco = new FacilityBizco();
        facilityBizco.setFacltBizcoId(parseFacltBizcoId);
        facilityBizco.setEncKey(pipsEncryptKey);

        model.addAttribute("facltBizcoId", facltBizcoId);
        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("facilityInfoCaddrList", facilityService.getFacilityInfoCaddrList(facilityBizco));

        return thisUrl + "/facilityInfo/detailView";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 수정
     * 시스템 및 단지 관리자용 시설업체 수정
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "facilityInfo/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String facilityInfoEdit(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/facilityInfo/edit";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 수정
     * 시스템 및 단지 관리자용 시설업체 수정
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "facilityInfo/detailEdit", method = {RequestMethod.GET, RequestMethod.POST})
    public String facilityInfoDetailEdit(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramFacltBizcoid = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoId")));
        String facltBizcoId = StringUtils.defaultIfEmpty(paramFacltBizcoid, "");
        Integer parseFacltBizcoId = Integer.parseInt(facltBizcoId);

        pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

        FacilityBizco facilityBizco = new FacilityBizco();
        facilityBizco.setFacltBizcoId(parseFacltBizcoId);
        facilityBizco.setEncKey(pipsEncryptKey);

        model.addAttribute("facltBizcoId", facltBizcoId);
        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("facilityInfoCaddrList", facilityService.getFacilityInfoCaddrList(facilityBizco));

        return thisUrl + "/facilityInfo/detailEdit";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 수정처리
     * 시스템 및 단지 관리자용 시설업체 수정처리
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "editFacilityInfoAction", method = RequestMethod.POST)
    public String editFacilityInfoAction(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramFacltBizcoId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoId")));
        String paramFacltBizcoTpNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoTpNm")));
        String paramTwbsNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("twbsNm")));
        String paramBizcoNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("bizcoNm")));
        String paramConCont = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("conCont")));

        String facltBizcoId = StringUtils.defaultIfEmpty(paramFacltBizcoId, "");
        String facltBizcoTpNm = StringUtils.defaultIfEmpty(paramFacltBizcoTpNm, "");
        String twbsNm = StringUtils.defaultIfEmpty(paramTwbsNm, "");
        String bizcoNm = StringUtils.defaultIfEmpty(paramBizcoNm, "");
        String conCont = StringUtils.defaultIfEmpty(paramConCont, "");

        FacilityBizco facilityBizco = new FacilityBizco();

        Integer parseFacltBizcoId = Integer.parseInt(facltBizcoId);

        facilityBizco.setFacltBizcoId(parseFacltBizcoId);
        facilityBizco.setFacltBizcoTpNm(facltBizcoTpNm);
        facilityBizco.setTwbsNm(twbsNm);
        facilityBizco.setBizcoNm(bizcoNm);
        facilityBizco.setConCont(conCont);
        facilityBizco.setEditerId(adminId);

        facilityService.updateFacilityInfo(facilityBizco);

        model.addAttribute("houscplxCd", houscplxCd);

        return "redirect:/" + thisUrl + "/facilityInfo/view";
    }

    @RequestMapping(value = "deleteFacilityInfoAction", method = RequestMethod.POST)
    public String deleteFacilityInfoAction(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramIsAll = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isAll")));
        String isAll = StringUtils.defaultIfEmpty(paramIsAll, "");
        FacilityBizco facilityBizco = new FacilityBizco();

        if (isAll.equals("N")) {
            String paramFacltBizcoId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoId")));
            String facltBizcoId = StringUtils.defaultIfEmpty(paramFacltBizcoId, "");
            Integer parseFacltBizcoId = Integer.parseInt(facltBizcoId);

            facilityBizco.setFacltBizcoId(parseFacltBizcoId);
            facilityBizco.setHouscplxCd(houscplxCd);
            facilityBizco.setEditerId(adminId);

            facilityService.deleteFacilityInfo(facilityBizco);

            model.addAttribute("facltBizcoId", facltBizcoId);
        } else if (isAll.equals("Y")) {
            facilityBizco.setHouscplxCd(houscplxCd);
            facilityBizco.setEditerId(adminId);

            facilityService.deleteFacilityInfoAll(facilityBizco);
        }

        model.addAttribute("houscplxCd", houscplxCd);

        return "redirect:/" + thisUrl + "/facilityInfo/view";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 연락처 정보 수정처리
     * 시스템 및 단지 관리자용 시설업체 연락처 정보 수정처리
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "editFacilityInfoCaddrAction", method = RequestMethod.POST)
    public String editFacilityInfoCaddrAction(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramFacltBizcoId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoId")));
        String facltBizcoId = StringUtils.defaultIfEmpty(paramFacltBizcoId, "");

        String paramFacilityInfoCaddrList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facilityInfoCaddrList")));
        String facilityInfoCaddrList = StringUtils.defaultIfEmpty(paramFacilityInfoCaddrList, "");
        Integer parseFacltBizcoId = Integer.parseInt(facltBizcoId);

        if (StringUtils.isNotEmpty(facilityInfoCaddrList)) {
            JSONArray facilityInfoCaddrJsonArray = new JSONArray(paramFacilityInfoCaddrList);
            int length = facilityInfoCaddrJsonArray.length();

            if (length > 0) {
                FacilityBizco facilityBizco = new FacilityBizco();
                facilityBizco.setFacltBizcoId(parseFacltBizcoId);
                facilityBizco.setHouscplxCd(houscplxCd);
                facilityBizco.setEditerId(adminId);

                List<FacilityBizcoCaddr> addressList = new ArrayList<>();

                for (int j=0; j<length; j++) {
                    JSONObject facilityAddressJsonObject = facilityInfoCaddrJsonArray.getJSONObject(j);
                    FacilityBizcoCaddr facilityBizcoCaddr = new FacilityBizcoCaddr();

                    String paramFacltBizcoCaddrId = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("facltBizcoCaddrId")));
                    String facltBizcoCaddrId = StringUtils.defaultIfEmpty(paramFacltBizcoCaddrId, "");

                    Integer parsedFacltBizcoCaddrId = null;

                    if (StringUtils.isNotEmpty(facltBizcoCaddrId)) {
                        parsedFacltBizcoCaddrId = Integer.parseInt(facltBizcoCaddrId);
                        facilityBizcoCaddr.setFacltBizcoCaddrId(parsedFacltBizcoCaddrId);
                    }

                    String paramPerchrgNm = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("per")));
                    String paramMphoneNo = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("mp")));
                    String paramOffcPhoneNo = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("offc")));
                    String paramFaxNo = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("fax")));

                    facilityBizcoCaddr.setPerchrgNm(StringUtils.defaultIfEmpty(paramPerchrgNm, ""));
                    facilityBizcoCaddr.setMphoneNo(StringUtils.defaultIfEmpty(paramMphoneNo, ""));
                    facilityBizcoCaddr.setOffcPhoneNo(StringUtils.defaultIfEmpty(paramOffcPhoneNo, ""));
                    facilityBizcoCaddr.setFaxNo(StringUtils.defaultIfEmpty(paramFaxNo, ""));
                    facilityBizcoCaddr.setMgmTpCd("COMPLEX_MGMT");
                    facilityBizcoCaddr.setCrerId(adminId);
                    facilityBizcoCaddr.setEditerId(adminId);

                    addressList.add(facilityBizcoCaddr);
                }

                facilityService.insertFacilityInfoCaddrInfo(facilityBizco, addressList);
            }
        }

        model.addAttribute("facltBizcoId", facltBizcoId);
        model.addAttribute("houscplxCd", houscplxCd);

        return "redirect:/" + thisUrl + "/facilityInfo/detailView";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 연락처 정보 삭제처리
     * 시스템 및 단지 관리자용 시설업체 연락처 정보 삭제처리
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteFacilityInfoCaddrAction", method = RequestMethod.POST)
    public String deleteFacilityInfoCaddrAction(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramFacltBizcoId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoId")));
        String facltBizcoId = StringUtils.defaultIfEmpty(paramFacltBizcoId, "");

        String paramIsAll = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isAll")));
        String isAll = StringUtils.defaultIfEmpty(paramIsAll, "");

        Integer parseFacltBizcoId = Integer.parseInt(facltBizcoId);

        FacilityBizco facilityBizco = new FacilityBizco();
        facilityBizco.setFacltBizcoId(parseFacltBizcoId);
        facilityBizco.setEditerId(adminId);

        model.addAttribute("houscplxCd", houscplxCd);

        if (isAll.equals("Y")) {
            facilityService.deleteFacilityInfoCaddrInfoAll(facilityBizco);

            return "redirect:/" + thisUrl + "/facilityInfo/view";
        } else if (isAll.equals("N")) {
            String paramFacltBizcoCaddrId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoCaddrId")));
            String facltBizcoCaddrId = StringUtils.defaultIfEmpty(paramFacltBizcoCaddrId, "");
            Integer parseFacltBizcoCaddrId = Integer.parseInt(facltBizcoCaddrId);

            if (StringUtils.isNotEmpty(facltBizcoCaddrId)) {
                facilityService.deleteFacilityInfoCaddrInfoOne(parseFacltBizcoCaddrId, parseFacltBizcoId, adminId);
            }
        }

        model.addAttribute("facltBizcoId", facltBizcoId);

        return "redirect:/" + thisUrl + "/facilityInfo/detailEdit";
    }
    /**
     * 단지 관리 > 단지정보 관리 > CCTV 상세
     * 시스템 및 단지 관리자용 CCTV 상세 조회
     * @param cctv
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "cctv/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String cctvView(CCTV cctv, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("cctvInfo", housingCplxService.getCCTVInfo(cctv));

        return thisUrl + "/cctv/view";
    }

    /**
     * 단지 관리 > 단지정보 관리 > CCTV 수정
     * 시스템 및 단지 관리자용 CCTV 수정
     * @param cctv
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "cctv/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String cctvEdit(CCTV cctv, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("cctvInfo", housingCplxService.getCCTVInfo(cctv));

        return thisUrl + "/cctv/edit";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 세대별 평형 수정
     * 시스템 및 단지 관리자용 세대별 평형 수정
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "editCCTVAction", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    public String editCCTVAction(Model model, HttpServletRequest request) throws IOException {
        SessionUser session = SessionUtil.getSessionUser(request);
        String resultUrl = "redirect:/cm/housingcplx/info/cctv/view";
        boolean isDeleted = false;

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String cctvList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("cctvList")));

        cctvList = StringUtils.defaultIfEmpty(cctvList, "");
        System.out.println("1");
        JSONArray cctvJsonArray = new JSONArray(cctvList);
        System.out.println("2");
        Map<String, Object> cctvMap = new HashMap<String, Object>();
        System.out.println("3");
        int length = cctvJsonArray.length();
        System.out.println("4");

        if (length > 0) {
            List<CCTV> list = new ArrayList<CCTV>();

            for(int i=0; i<length; i++) {
                CCTV cctv = new CCTV();
                String paramCctvNm = xssUtil.replaceAll(StringUtils.defaultString((String) cctvJsonArray.getJSONObject(i).get("cctvNm")));
                String paramCont = xssUtil.replaceAll(StringUtils.defaultString((String) cctvJsonArray.getJSONObject(i).get("cont")));
                String paraUurlCont = xssUtil.replaceAll(StringUtils.defaultString((String) cctvJsonArray.getJSONObject(i).get("urlCont")));
                String paramCrerId = xssUtil.replaceAll(StringUtils.defaultString((String) cctvJsonArray.getJSONObject(i).get("crerId")));

                String cctvNm = StringUtils.defaultIfEmpty(paramCctvNm, "");
                String cont = StringUtils.defaultIfEmpty(paramCont, "");
                String urlCont = StringUtils.defaultIfEmpty(paraUurlCont, "");
                String crerId = StringUtils.defaultIfEmpty(paramCrerId, "");

                cctv.setCctvNm(cctvNm);
                cctv.setCont(cont);
                cctv.setUrlCont(urlCont);
                cctv.setDelYn("N");
                cctv.setCrerId(crerId);
                cctv.setEditerId(crerId);
                cctv.setHouscplxCd(houscplxCd);

                list.add(cctv);
            }
            cctvMap.put("list", list);
            boolean result = false;

            result = housingCplxService.deleteCCTV(houscplxCd);
            logger.debug("CCTV delete success");
            if (cctvMap.size() > 0) {
                result = housingCplxService.updateCCTV(cctvMap);
                logger.debug("CCTV update success");
            } else {
            }
            isDeleted = true;
        } else {
            housingCplxService.deleteCCTV(houscplxCd);
        }

        model.addAttribute("isDeleted", isDeleted);
        model.addAttribute("houscplxCd", houscplxCd);

        String pageType = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("pagename")));
        pageType = StringUtils.defaultIfEmpty(pageType, "");

        if (pageType.equals("add")){
            resultUrl = "redirect:/cm/housingcplx/info/cctv/view";
        }
        return resultUrl;
    }
    /**
     * 단지 관리 > 단지정보 관리 > 기타정보 상세
     * 시스템 및 단지 관리자용 기타정보 상세 조회
     * @param housingCplx
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "etc/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String etcView(HousingCplx housingCplx, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("etcInfo", housingCplxService.getEtcHousingCplx(housingCplx));
        model.addAttribute("energyInfo",housingCplxService.getEtcEnergyUnit(housingCplx));

        JSONArray lnkSvcInfoList = serviceLinkService.getLnkSvcInfoList(housingCplx);

        model.addAttribute("lnkSvcInfoList", lnkSvcInfoList);
        model.addAttribute("lnkSvcInfoListCnt", lnkSvcInfoList.length());

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);
        model.addAttribute("bannerInfo",bannerService.getBannerList(houscplxCd));

        model.addAttribute("houscplxCd",houscplxCd);

        return thisUrl + "/etc/view";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 기타정보 수정
     * 시스템 및 단지 관리자용 기타정보 수정
     * @param housingCplx
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "etc/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String etcEdit(HousingCplx housingCplx, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("etcInfo", housingCplxService.getEtcHousingCplx(housingCplx));
        model.addAttribute("serviceLinkInfo", serviceLinkService.getEtcServiceLinkMetaInfo(housingCplx));
        model.addAttribute("energyInfo", housingCplxService.getEtcEnergyUnit(housingCplx));
        model.addAttribute("bannerInfo",bannerService.getBannerList(houscplxCd));

        model.addAttribute("houscplxCd",houscplxCd);

        return thisUrl + "/etc/edit";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 단지정보 수정 (기타)
     * 시스템 및 단지 관리자용 단지 등록, 수정시에 배너 목록 조회
     * Ajax 방식
     * @param housingCplx
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "bannerInfo/list", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String bannerInfo(HousingCplx housingCplx, Model model, HttpServletRequest request) {

        List<Banner> bannerList = bannerService.getSystemBannerList();

        String json = JsonUtil.toJsonNotZero(bannerList);

        return json;
    }

    /**
     * 단지 관리 > 단지정보 관리 > 기타정보 수정처리
     * 시스템 및 단지 관리자용 기타정보 수정처리
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "editEtcAction", method = RequestMethod.POST)
    public String editEtcAction(HousingCplx housingCplx, Model model, BindingResult result, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String groupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String resultUrl = thisUrl + "/etc/edit";
        String resultMsg = "";

        String paramEnrgMeasYmd = xssUtil.replaceAll(StringUtils.defaultString(housingCplx.getEnrgMeasYmd()));
        String enrgMeasYmd = StringUtils.defaultIfEmpty(paramEnrgMeasYmd, "");
        Integer qryRangeCont = null;
        qryRangeCont = housingCplx.getQryRangeCont();

        if(StringUtils.isEmpty(enrgMeasYmd)) {
            result.addError(new FieldError("enrgMeasYmd", "enrgMeasYmd", MessageUtil.getMessage("notEmpty.enrgMeasYmd.enrgMeasYmd")));
        }

        if(qryRangeCont == null) {
            result.addError(new FieldError("qryRangeCont", "qryRangeCont", MessageUtil.getMessage("notEmpty.qryRangeCont.qryRangeCont")));
        }

        String serviceLinkList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("serviceLinkList")));
        serviceLinkList = StringUtils.defaultIfEmpty(serviceLinkList, "");

        if (serviceLinkList == null) {
            result.addError(new FieldError("serviceLinkList", "serviceLinkList", MessageUtil.getMessage("notEmpty.serviceLinkList.serviceLinkList")));
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("editEtcAction", null);
            model.addAttribute("resultMsg", resultMsg);
        }

        String energyUnitList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("energyUnitList")));
        energyUnitList = StringUtils.defaultIfEmpty(energyUnitList, "");

        if (energyUnitList == null) {
            result.addError(new FieldError("energyUnitList", "energyUnitList", MessageUtil.getMessage("notEmpty.energyUnitList.energyUnitList")));
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("editEtcAction", null);
            model.addAttribute("resultMsg", resultMsg);
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("editEtcAction", null);
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        JSONArray housingCplxServiceLinkJsonArray = new JSONArray(serviceLinkList);
        Map<String, Object> serviceLinkMap = new HashMap<String, Object>();
        int serviceLinkLength = housingCplxServiceLinkJsonArray.length();

        if (serviceLinkLength > 0) {
            List<HousingCplxServiceLink> list = new ArrayList<HousingCplxServiceLink>();

            for(int i=0; i<serviceLinkLength; i++) {
                HousingCplxServiceLink housingCplxServiceLink = new HousingCplxServiceLink();

                String paramLnkSvcId = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxServiceLinkJsonArray.getJSONObject(i).get("lnkSvcId")));
                String paramCrerId = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxServiceLinkJsonArray.getJSONObject(i).get("crerId")));

                String lnkSvcId = StringUtils.defaultIfEmpty(paramLnkSvcId, "");
                String crerId = StringUtils.defaultIfEmpty(paramCrerId, "");

                housingCplxServiceLink.setHouscplxCd(houscplxCd);
                housingCplxServiceLink.setLnkSvcId(Integer.valueOf(lnkSvcId));
                housingCplxServiceLink.setCrerId(crerId);

                list.add(housingCplxServiceLink);
            }
            serviceLinkMap.put("list", list);
        }

        JSONArray housingCplxEnrgUntJsonArray = new JSONArray(energyUnitList);
        Map<String, Object> energyUnitMap = new HashMap<String, Object>();
        int energyUnitLength = housingCplxEnrgUntJsonArray.length();

        if (energyUnitLength > 0) {
            List<HousingCplxEnergyUnit> list = new ArrayList<HousingCplxEnergyUnit>();

            for(int i=0; i<energyUnitLength; i++) {
                HousingCplxEnergyUnit housingCplxEnergyUnit = new HousingCplxEnergyUnit();

                String paramEnrgTpCd = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxEnrgUntJsonArray.getJSONObject(i).get("enrgTpCd")));
                String paramEnrgUntCd = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxEnrgUntJsonArray.getJSONObject(i).get("enrgUntCd")));
                String paramEnrgMaxQty = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxEnrgUntJsonArray.getJSONObject(i).get("enrgMaxQty")));
                String paramCrerId = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxEnrgUntJsonArray.getJSONObject(i).get("crerId")));

                String enrgTpCd = StringUtils.defaultIfEmpty(paramEnrgTpCd, "");
                String enrgUntCd = StringUtils.defaultIfEmpty(paramEnrgUntCd, "");
                String enrgMaxQty = StringUtils.defaultIfEmpty(paramEnrgMaxQty, "");
                String crerId = StringUtils.defaultIfEmpty(paramCrerId, "");

                housingCplxEnergyUnit.setHouscplxCd(houscplxCd);
                housingCplxEnergyUnit.setEnrgTpCd(enrgTpCd);
                housingCplxEnergyUnit.setEnrgUntCd(enrgUntCd);
                housingCplxEnergyUnit.setEnrgMaxQty(enrgMaxQty);
                housingCplxEnergyUnit.setCrerId(crerId);

                list.add(housingCplxEnergyUnit);
            }
            energyUnitMap.put("list", list);
        }

        String bannerList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("bannerList")));
        bannerList = StringUtils.defaultIfEmpty(bannerList, "");

        JSONArray housingCplxBannerJsonArray = new JSONArray(bannerList);
        Map<String, Object> bannerMap = new HashMap<String, Object>();
        int bannerLength = housingCplxBannerJsonArray.length();

        if (bannerLength > 0) {
            List<HousingCplx> list = new ArrayList<>();

            for(int i=0; i<bannerLength; i++) {
                HousingCplx housingCplxBanner = new HousingCplx();

                String paramLnkSvcId = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxBannerJsonArray.getJSONObject(i).get("blltNo")));
                String paramCrerId = xssUtil.replaceAll(StringUtils.defaultString((String) housingCplxBannerJsonArray.getJSONObject(i).get("crerId")));

                String lnkSvcId = StringUtils.defaultIfEmpty(paramLnkSvcId, "");
                String crerId = StringUtils.defaultIfEmpty(paramCrerId, "");

                housingCplxBanner.setHouscplxCd(houscplxCd);
                housingCplxBanner.setLnkSvcId(lnkSvcId);
                housingCplxBanner.setCrerId(crerId);

                list.add(housingCplxBanner);
            }
            bannerMap.put("list", list);
        }

        housingCplxService.updateHousingCplxEtcInfo(groupName, housingCplx, serviceLinkMap, energyUnitMap, bannerMap);

        model.addAttribute("etcInfo", housingCplxService.getEtcHousingCplx(housingCplx));
        model.addAttribute("energyInfo",housingCplxService.getEtcEnergyUnit(housingCplx));

        JSONArray lnkSvcInfoList = serviceLinkService.getLnkSvcInfoList(housingCplx);

        JSONObject jsonObjectResult = new JSONObject();
        jsonObjectResult.put("lnkSvcInfoList", lnkSvcInfoList);

        model.addAttribute("lnkSvcInfoList", JsonUtil.toJsonNotZero(jsonObjectResult));
        model.addAttribute("lnkSvcInfoListCnt", lnkSvcInfoList.length());

        model.addAttribute("houscplxCd", houscplxCd);

        return "redirect:/cm/housingcplx/info/etc/view";
    }
}