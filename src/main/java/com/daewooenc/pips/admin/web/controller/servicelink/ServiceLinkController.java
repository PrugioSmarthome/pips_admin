package com.daewooenc.pips.admin.web.controller.servicelink;

import com.daewooenc.pips.admin.core.domain.UploadFileInfo;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.FileUploadUtil;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.domain.dto.common.CommCodeDetail;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLink;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLinkDetail;
import com.daewooenc.pips.admin.web.service.common.CommCodeService;
import com.daewooenc.pips.admin.web.service.servicelink.ServiceLinkService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 연계 웹/앱 관리 관련 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-23      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-23
 **/
@Controller
@RequestMapping(value = "/cm/system/serviceLink")
public class ServiceLinkController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/serviceLink";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private ServiceLinkService serviceLinkService;

    @Autowired
    private CommCodeService commCodeService;

    private @Value("${file.rootPath}") String fileRootPath;
    private @Value("${file.contents.systemUploadPath}") String fileContentsSystemUploadPath;
    private @Value("${file.image.icon.fixedWidth}") int fileImageIconFixedWidth;
    private @Value("${pips.serviceServer.icon.image.url}") String imageUrl;
    /**
     * 시스템 관리 > 연계 웹/앱 관리 > 연계 웹/앱 목록
     * 시스템 관리자용 연계 웹/앱 목록
     * @param serviceLink
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String serviceLinkList(ServiceLink serviceLink, Model model, HttpServletRequest request) {
        CommCodeDetail commCodeDetail = new CommCodeDetail();
        commCodeDetail.setCommCdGrpCd("LNK_SVC_GRP_TP_CD");

        model.addAttribute("typeList", commCodeService.getCommCodeList(commCodeDetail));
        model.addAttribute("serviceLinkList", serviceLinkService.getServiceLinkList(serviceLink));

        String startCrDt = xssUtil.replaceAll(StringUtils.defaultString(serviceLink.getStartCrDt()));
        String endCrDt = xssUtil.replaceAll(StringUtils.defaultString(serviceLink.getEndCrDt()));
        String lnkSvcGrpTpCd = xssUtil.replaceAll(StringUtils.defaultString(serviceLink.getLnkSvcGrpTpCd()));
        String lnkSvcNm = xssUtil.replaceAll(StringUtils.defaultString(serviceLink.getLnkSvcNm()));

        model.addAttribute("startCrDt", StringUtils.defaultIfEmpty(startCrDt, ""));
        model.addAttribute("endCrDt", StringUtils.defaultIfEmpty(endCrDt, ""));
        model.addAttribute("lnkSvcGrpTpCd", StringUtils.defaultIfEmpty(lnkSvcGrpTpCd, ""));
        model.addAttribute("lnkSvcNm", StringUtils.defaultIfEmpty(lnkSvcNm, ""));

        return thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 연계 웹/앱 관리 > 연계 웹/앱 등록
     * 시스템 관리자용 연계 웹/앱 등록
     * @param model
     * @return
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public String serviceLinkAdd(Model model) {
        logger.debug("serviceLinkAdd start");

        CommCodeDetail commCodeDetail = new CommCodeDetail();
        commCodeDetail.setCommCdGrpCd("LNK_SVC_GRP_TP_CD");

        model.addAttribute("typeList", commCodeService.getCommCodeList(commCodeDetail));

        commCodeDetail.setCommCdGrpCd("LNK_SVC_TP_CD");

        model.addAttribute("typeSvcList", commCodeService.getCommCodeList(commCodeDetail));

        return thisUrl + "/add";
    }

    /**
     * 시스템 관리 > 연계 웹/앱 관리 > 연계 웹/앱 등록을 처리
     * 시스템 관리자용 연계 웹/앱 등록을 처리
     * @param serviceLinkDetail
     * @param model
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "addServiceLinkAction", method = RequestMethod.POST)
    public String addServiceLinkAction(ServiceLinkDetail serviceLinkDetail, Model model, HttpServletRequest request, BindingResult result) {
        String resultUrl = thisUrl;
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        serviceLinkDetail.setCrerId(adminId);

        String lnkSvcGrpTpCd = xssUtil.replaceAll(StringUtils.defaultString(serviceLinkDetail.getLnkSvcGrpTpCd()));
        String lnkSvcNm = xssUtil.replaceAll(StringUtils.defaultString(serviceLinkDetail.getLnkSvcNm()));

        lnkSvcGrpTpCd = StringUtils.defaultIfEmpty(lnkSvcGrpTpCd, "");
        lnkSvcNm = StringUtils.defaultIfEmpty(lnkSvcNm, "");

        if(StringUtils.isEmpty(lnkSvcGrpTpCd)) {
            result.addError(new FieldError("lnkSvcGrpTpCd", "lnkSvcGrpTpCd", MessageUtil.getMessage("notEmpty.lnkSvcGrpTpCd.lnkSvcGrpTpCd")));
        }

        if(StringUtils.isEmpty(lnkSvcNm)) {
            result.addError(new FieldError("lnkSvcNm", "lnkSvcNm", MessageUtil.getMessage("notEmpty.lnkSvcNm.lnkSvcNm")));
        }

        //String serviceLinkList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("serviceLinkList")));
        String serviceLinkList = StringUtils.defaultString(request.getParameter("serviceLinkList"));
        serviceLinkList = StringUtils.defaultIfEmpty(serviceLinkList, "");

        if (serviceLinkList == null) {
            result.addError(new FieldError("serviceLinkList", "serviceLinkList", MessageUtil.getMessage("notEmpty.serviceLinkList.serviceLinkList")));
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.error"));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("addServiceLinkAction", null);
            model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.error"));

            resultUrl = resultUrl + "/add";
        }

        try {
            List<UploadFileInfo> fileList = new ArrayList<UploadFileInfo>();

            FileUploadUtil fileUploadUtil = new FileUploadUtil();

            fileRootPath = xssUtil.replaceAll(StringUtils.defaultString(fileRootPath));
            fileContentsSystemUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsSystemUploadPath));

            String realPath = fileRootPath + fileContentsSystemUploadPath;

            MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
            Iterator<String> fileNames = mpRequest.getFileNames();

            String originalFileName = "";
            String thumbnailFileName = "";

            while (fileNames.hasNext()) {
                MultipartFile mpf = mpRequest.getFile(fileNames.next());
                String realOriginalFileName = xssUtil.replaceTag(StringUtils.defaultString(mpf.getOriginalFilename()));
                originalFileName = fileUploadUtil.makeFileName(false, "", "icon", true, realOriginalFileName);

                if (mpf.getSize() > 0) {
                    UploadFileInfo originalUploadFileInfo = fileUploadUtil.uploadOriginalFile(mpf, realPath , originalFileName);

                    fileList.add(originalUploadFileInfo);
                    logger.info("\noriginalUploadFileInfo : {} ", originalUploadFileInfo.toString().replaceAll("\\]", "\n]").replaceAll("\\[", "\n[\n").replaceAll(",", ",\n"));

                    File thumbnailFile = fileUploadUtil.multipartToFile(mpf);
                    thumbnailFileName = fileUploadUtil.makeFileName(false, "", "icon", false, "");

                    String originalRealPath = realPath + "/" + originalFileName;
                    BufferedImage image = ImageIO.read(new FileInputStream(originalRealPath));
                    int originalWidth = image.getWidth();
                    int originalHeight = image.getHeight();

//                    if (originalWidth > fileImageIconFixedWidth) {
//                        double compareWidth = image.getWidth();
//                        double rate = fileImageIconFixedWidth / compareWidth;
//
//                        originalWidth = (int) Math.round(originalWidth * rate) ;
//                        originalHeight = (int) Math.round(originalHeight * rate);
//                    }

                    int extIndex = mpf.getOriginalFilename().lastIndexOf(".");
                    String fileExt = mpf.getOriginalFilename().substring(extIndex + 1);

                    fileUploadUtil.uploadThumbnailImageFile(thumbnailFile, realPath, thumbnailFileName, originalWidth, originalHeight, fileExt);

                    boolean isTempFileDeleted =  thumbnailFile.delete();

                    logger.debug("thumbnailFile AbsolutePath: " + thumbnailFile.getAbsolutePath());
                    logger.debug("thumbnailFile CanonicalPath: " + thumbnailFile.getCanonicalPath());
                    logger.debug("isTempDeleted is True? : " + BooleanUtils.isTrue(isTempFileDeleted));

                    serviceLinkDetail.setOrgnlFileNm(originalFileName);
                    serviceLinkDetail.setFileNm(thumbnailFileName);
                    serviceLinkDetail.setFilePathCont(realPath);
                    serviceLinkDetail.setFileUrlCont(imageUrl);
                }
            }

            JSONArray serviceLinkListArray = new JSONArray(serviceLinkList);

            String danjiInfo = request.getParameter("danjiArray");

            serviceLinkService.insertServiceLinkInfo(serviceLinkDetail, serviceLinkListArray, danjiInfo);

            resultUrl = resultUrl + "/list";
        } catch (Exception e) {
            logger.debug("addServiceLinkAction Exception: " + e.getCause());

            model.addAttribute("addServiceLinkAction", null);
            model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.error"));

            resultUrl = resultUrl + "/add";
        }

        return "redirect:/" + resultUrl;
    }

    /**
     * 시스템 관리 > 연계 웹/앱 관리 > 연계 웹/앱 상세 조회
     * @param serviceLinkDetail
     * @param model
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "view", method = {RequestMethod.GET, RequestMethod.POST})
    public String serviceLinkDetail(ServiceLinkDetail serviceLinkDetail, Model model, HttpServletRequest request, BindingResult result) {
        logger.debug("serviceLinkDetail view12 : " + serviceLinkDetail.getLnkSvcId());

        String resultUrl = thisUrl + "/view";

        Integer lnkSvcId = null;
        lnkSvcId = serviceLinkDetail.getLnkSvcId();

        if(lnkSvcId == null) {
            result.addError(new FieldError("lnkSvcId", "lnkSvcId", MessageUtil.getMessage("notEmpty.lnkSvcId.lnkSvcId")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", MessageUtil.getMessage("msg.serviceLink.error.result"));
        } else {
            ServiceLinkDetail serviceLinkDetailInfo = serviceLinkService.getServiceLinkDetail(serviceLinkDetail);
            model.addAttribute("serviceLinkDetailInfo", serviceLinkDetailInfo);

            List lnkSvcInfoList = serviceLinkService.getLnkSvcDetailList(serviceLinkDetail);
            for (int i = 0; i < lnkSvcInfoList.size(); i++) {
                ServiceLinkDetail tmp = (ServiceLinkDetail) lnkSvcInfoList.get(i);
                String lnkAttrTpCd = xssUtil.replaceAllOrg(StringUtils.defaultString(tmp.getLnkAttrTpCd()));
                String lnkTpCd = xssUtil.replaceAllOrg(StringUtils.defaultString(tmp.getLnkTpCd()));
                //String lnkAttrCont = xssUtil.replaceAllOrg(StringUtils.defaultString(tmp.getLnkAttrCont()));
                String lnkAttrCont = StringUtils.defaultString(tmp.getLnkAttrCont());
                lnkAttrTpCd = StringUtils.defaultIfEmpty(lnkAttrTpCd, "");
                lnkTpCd = StringUtils.defaultIfEmpty(lnkTpCd, "");
                lnkAttrCont = StringUtils.defaultIfEmpty(lnkAttrCont, "");

                if ("URL".equals(lnkAttrTpCd)) {
                    if ("IOS".equals(lnkTpCd)) {
                        model.addAttribute("url_ios_cont", lnkAttrCont);
                    }
                    if ("ANDROID".equals(lnkTpCd)) {
                        model.addAttribute("url_android_cont", lnkAttrCont);
                    }
                    if ("WEB".equals(lnkTpCd)) {
                        model.addAttribute("url_web_cont", lnkAttrCont);
                    }
                }

                if ("SCHEMA".equals(lnkAttrTpCd)) {
                    if ("IOS".equals(lnkTpCd)) {
                        model.addAttribute("schema_ios_cont", lnkAttrCont);
                    }
                    if ("ANDROID".equals(lnkTpCd)) {
                        model.addAttribute("schema_android_cont", lnkAttrCont);
                    }
                    if ("WEB".equals(lnkTpCd)) {
                        model.addAttribute("schema_web_cont", lnkAttrCont);
                    }
                }

                if ("DEEP_LINK".equals(lnkAttrTpCd)) {
                    if ("IOS".equals(lnkTpCd)) {
                        model.addAttribute("deeplink_ios_cont", lnkAttrCont);
                    }
                    if ("ANDROID".equals(lnkTpCd)) {
                        model.addAttribute("deeplink_android_cont", lnkAttrCont);
                    }
                    if ("WEB".equals(lnkTpCd)) {
                        model.addAttribute("deeplink_web_cont", lnkAttrCont);
                    }
                }

                if ("APP_ID".equals(lnkAttrTpCd)) {
                    if ("IOS".equals(lnkTpCd)) {
                        model.addAttribute("app_id_ios_cont", lnkAttrCont);
                    }
                    if ("ANDROID".equals(lnkTpCd)) {
                        model.addAttribute("app_id_android_cont", lnkAttrCont);
                    }
                }
            }
        }

        return resultUrl;
    }

    /**
     * 시스템 관리 > 연계 웹/앱 관리 > 연계 웹/앱 상세 수정
     * @param serviceLinkDetail
     * @param model
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String serviceLinkEdit(ServiceLinkDetail serviceLinkDetail, Model model, HttpServletRequest request, BindingResult result) {
        logger.debug("serviceLinkEdit start");
        String resultUrl = thisUrl;

        Integer lnkSvcId = null;
        lnkSvcId = serviceLinkDetail.getLnkSvcId();

        if(lnkSvcId == null) {
            result.addError(new FieldError("lnkSvcId", "lnkSvcId", MessageUtil.getMessage("notEmpty.lnkSvcId.lnkSvcId")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", MessageUtil.getMessage("msg.serviceLink.error.result"));
        } else {
            CommCodeDetail commCodeDetail = new CommCodeDetail();
            commCodeDetail.setCommCdGrpCd("LNK_SVC_GRP_TP_CD");
            model.addAttribute("typeList", commCodeService.getCommCodeList(commCodeDetail));

            ServiceLinkDetail serviceLinkDetailInfo = serviceLinkService.getServiceLinkDetail(serviceLinkDetail);
            model.addAttribute("info", serviceLinkDetailInfo);

            List lnkSvcInfoList = serviceLinkService.getLnkSvcDetailList(serviceLinkDetail);
            for (int i = 0; i < lnkSvcInfoList.size(); i++) {
                ServiceLinkDetail tmp = (ServiceLinkDetail) lnkSvcInfoList.get(i);
                String lnkAttrTpCd = xssUtil.replaceAllOrg(StringUtils.defaultString(tmp.getLnkAttrTpCd()));
                String lnkTpCd = xssUtil.replaceAllOrg(StringUtils.defaultString(tmp.getLnkTpCd()));
                //String lnkAttrCont = xssUtil.replaceAllOrg(StringUtils.defaultString(tmp.getLnkAttrCont()));
                String lnkAttrCont = StringUtils.defaultString(tmp.getLnkAttrCont());

                lnkAttrTpCd = StringUtils.defaultIfEmpty(lnkAttrTpCd, "");
                lnkTpCd = StringUtils.defaultIfEmpty(lnkTpCd, "");
                lnkAttrCont = StringUtils.defaultIfEmpty(lnkAttrCont, "");

                if ("URL".equals(lnkAttrTpCd)) {
                    if ("IOS".equals(lnkTpCd)) {
                        model.addAttribute("url_ios_cont", lnkAttrCont);
                    }
                    if ("ANDROID".equals(lnkTpCd)) {
                        model.addAttribute("url_android_cont", lnkAttrCont);
                    }
                    if ("WEB".equals(lnkTpCd)) {
                        model.addAttribute("url_web_cont", lnkAttrCont);
                    }
                }

                if ("SCHEMA".equals(lnkAttrTpCd)) {
                    if ("IOS".equals(lnkTpCd)) {
                        model.addAttribute("schema_ios_cont", lnkAttrCont);
                    }
                    if ("ANDROID".equals(lnkTpCd)) {
                        model.addAttribute("schema_android_cont", lnkAttrCont);
                    }
                    if ("WEB".equals(lnkTpCd)) {
                        model.addAttribute("schema_web_cont", lnkAttrCont);
                    }
                }

                if ("DEEP_LINK".equals(lnkAttrTpCd)) {
                    if ("IOS".equals(lnkTpCd)) {
                        model.addAttribute("deeplink_ios_cont", lnkAttrCont);
                    }
                    if ("ANDROID".equals(lnkTpCd)) {
                        model.addAttribute("deeplink_android_cont", lnkAttrCont);
                    }
                    if ("WEB".equals(lnkTpCd)) {
                        model.addAttribute("deeplink_web_cont", lnkAttrCont);
                    }
                }

                if ("APP_ID".equals(lnkAttrTpCd)) {
                    if ("IOS".equals(lnkTpCd)) {
                        model.addAttribute("app_id_ios_cont", lnkAttrCont);
                    }
                    if ("ANDROID".equals(lnkTpCd)) {
                        model.addAttribute("app_id_android_cont", lnkAttrCont);
                    }
                }
            }

            resultUrl = resultUrl + "/edit";
        }

        return resultUrl;
    }

    /**
     * 시스템 관리 > 연계 웹/앱 관리 > 연계 웹/앱 수정을 처리
     * @param serviceLinkDetail
     * @param model
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "editServiceLinkAction", method = RequestMethod.POST)
    public String editServiceLinkAction(ServiceLinkDetail serviceLinkDetail, Model model, HttpServletRequest request, BindingResult result) {
        logger.debug("editServiceLinkAction start");
        String resultUrl = thisUrl + "/edit";

        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        serviceLinkDetail.setCrerId(adminId);
        serviceLinkDetail.setEditerId(adminId);

        String lnkSvcGrpTpCd = xssUtil.replaceAll(StringUtils.defaultString(serviceLinkDetail.getLnkSvcGrpTpCd()));
        String lnkSvcNm = xssUtil.replaceAll(StringUtils.defaultString(serviceLinkDetail.getLnkSvcNm()));
        //String serviceLinkList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("serviceLinkList")));
        String serviceLinkList = StringUtils.defaultString(request.getParameter("serviceLinkList"));

        lnkSvcGrpTpCd = StringUtils.defaultIfEmpty(lnkSvcGrpTpCd, "");
        lnkSvcNm = StringUtils.defaultIfEmpty(lnkSvcNm, "");
        serviceLinkList = StringUtils.defaultIfEmpty(serviceLinkList, "");

        if(StringUtils.isEmpty(lnkSvcGrpTpCd)) {
            result.addError(new FieldError("lnkSvcGrpTpCd", "lnkSvcGrpTpCd", MessageUtil.getMessage("notEmpty.lnkSvcGrpTpCd.lnkSvcGrpTpCd")));
        }

        if(StringUtils.isEmpty(lnkSvcNm)) {
            result.addError(new FieldError("lnkSvcNm", "lnkSvcNm", MessageUtil.getMessage("notEmpty.lnkSvcNm.lnkSvcNm")));
        }

        if (serviceLinkList == null) {
            result.addError(new FieldError("serviceLinkList", "serviceLinkList", MessageUtil.getMessage("notEmpty.serviceLinkList.serviceLinkList")));
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.error"));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("editServiceLinkAction", null);
            model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.error"));

            return resultUrl;
        }

        String isNewFile = xssUtil.replaceSpecialCharacter(StringUtils.defaultString(request.getParameter("isNewFile")));
        isNewFile = StringUtils.defaultIfEmpty(isNewFile, "N");

        if (isNewFile.equals("Y")) {
            try {
                List<UploadFileInfo> fileList = new ArrayList<UploadFileInfo>();

                FileUploadUtil fileUploadUtil = new FileUploadUtil();

                fileRootPath = xssUtil.replaceAll(StringUtils.defaultString(fileRootPath));
                fileContentsSystemUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsSystemUploadPath));

                String realPath = fileRootPath + fileContentsSystemUploadPath;

                MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
                Iterator<String> fileNames = mpRequest.getFileNames();

                String originalFileName = "";
                String thumbnailFileName = "";

                while (fileNames.hasNext()) {
                    MultipartFile mpf = mpRequest.getFile(fileNames.next());
                    String realOriginalFileName = xssUtil.replaceTag(StringUtils.defaultString(mpf.getOriginalFilename()));
                    originalFileName = fileUploadUtil.makeFileName(false, "", "icon", true, realOriginalFileName);

                    if (mpf.getSize() > 0) {
                        UploadFileInfo originalUploadFileInfo = fileUploadUtil.uploadOriginalFile(mpf, realPath , originalFileName);

                        fileList.add(originalUploadFileInfo);
                        logger.info("\noriginalUploadFileInfo : {} ", originalUploadFileInfo.toString().replaceAll("\\]", "\n]").replaceAll("\\[", "\n[\n").replaceAll(",", ",\n"));

                        File thumbnailFile = fileUploadUtil.multipartToFile(mpf);
                        thumbnailFileName = fileUploadUtil.makeFileName(false, "", "icon", false, "");

                        String originalRealPath = realPath + "/" + originalFileName;
                        BufferedImage image = ImageIO.read(new FileInputStream(originalRealPath));
                        int originalWidth = image.getWidth();
                        int originalHeight = image.getHeight();

//                        if (originalWidth > fileImageIconFixedWidth) {
//                            double compareWidth = image.getWidth();
//                            double rate = fileImageIconFixedWidth / compareWidth;
//
//                            originalWidth = (int) Math.round(originalWidth * rate) ;
//                            originalHeight = (int) Math.round(originalHeight * rate);
//                        }

                        int extIndex = mpf.getOriginalFilename().lastIndexOf(".");
                        String fileExt = mpf.getOriginalFilename().substring(extIndex + 1);

                        fileUploadUtil.uploadThumbnailImageFile(thumbnailFile, realPath, thumbnailFileName, originalWidth, originalHeight, fileExt);

                        boolean isTempFileDeleted =  thumbnailFile.delete();

                        logger.debug("thumbnailFile AbsolutePath: " + thumbnailFile.getAbsolutePath());
                        logger.debug("thumbnailFile CanonicalPath: " + thumbnailFile.getCanonicalPath());
                        logger.debug("isTempDeleted is True? : " + BooleanUtils.isTrue(isTempFileDeleted));

                        serviceLinkDetail.setOrgnlFileNm(originalFileName);
                        serviceLinkDetail.setFileNm(thumbnailFileName);
                        serviceLinkDetail.setFilePathCont(realPath);
                        serviceLinkDetail.setFileUrlCont(imageUrl + "&file_nm=" + serviceLinkDetail.getFileNm());
                    }
                }

                JSONArray serviceLinkListArray = new JSONArray(serviceLinkList);

                String danjiInfo = request.getParameter("danjiArray");

                serviceLinkService.updateServiceLinkInfo(serviceLinkDetail, serviceLinkListArray, danjiInfo);

                model.addAttribute("serviceLinkDetail", serviceLinkDetail);

                resultUrl = "redirect:/" + thisUrl + "/list";
            } catch (Exception e) {
                logger.debug("editServiceLinkAction Exception: " + e.getCause());

                model.addAttribute("editServiceLinkAction", null);
                model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.error"));
            }
        } else {
            try {
                JSONArray serviceLinkListArray = new JSONArray(serviceLinkList);

                String danjiInfo = request.getParameter("danjiArray");

                serviceLinkService.updateServiceLinkInfo(serviceLinkDetail, serviceLinkListArray, danjiInfo);

                model.addAttribute("serviceLinkDetail", serviceLinkDetail);

                resultUrl = "redirect:/" + thisUrl + "/list";
            } catch (Exception e) {
                logger.debug("editServiceLinkAction Exception: " + e.getCause());

                model.addAttribute("editServiceLinkAction", null);
                model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.error"));
            }
        }

        return resultUrl;
    }

    /**
     * 시스템 관리 > 연계 웹/앱 관리 > 연계 웹/앱 삭제 처리
     * @param model
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "deleteServiceLinkAction", method = RequestMethod.POST)
    public String deleteServiceLinkAction(ServiceLink serviceLink, Model model, HttpServletRequest request, BindingResult result) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        serviceLinkService.deleteServiceLinkInfo(serviceLink.getLnkSvcId(), adminId);

        return "redirect:/" + thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 연계 웹/앱 관리 > 정렬순서 중복 체크
     * @param model
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "checkServiceLinkOrd", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkServiceLinkOrd(ServiceLink serviceLink, Model model, HttpServletRequest request, BindingResult result) {
        String lnkOrdNo = request.getParameter("lnkOrdNo");

        boolean check = serviceLinkService.checkServiceLinkOrd(Integer.parseInt(lnkOrdNo));

        return check;
    }

}