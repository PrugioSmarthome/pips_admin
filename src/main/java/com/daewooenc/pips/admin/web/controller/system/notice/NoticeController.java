package com.daewooenc.pips.admin.web.controller.system.notice;

import com.daewooenc.pips.admin.core.domain.UploadFileInfo;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.FileUploadUtil;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.domain.dto.community.NoticeFile;
import com.daewooenc.pips.admin.web.domain.dto.community.NoticeItem;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.service.api.ApiService;
import com.daewooenc.pips.admin.web.service.community.CommunityService;
import com.daewooenc.pips.admin.web.service.housingcplx.HousingCplxService;
import com.daewooenc.pips.admin.web.service.system.notice.NoticeService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import com.daewooenc.pips.admin.web.util.ValidationUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 시스템 관리의 서비스 공지사항 관련 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-10-25      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-25
 **/
@Controller
@RequestMapping("/cm/system/notice")
public class NoticeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/notice";
    private @Value("${file.rootPath}") String fileRootPath;
    private @Value("${file.contents.systemUploadPath}") String fileContentsSystemUploadPath;
    private @Value("${file.image.bllt.fixedWidth}") int fileImageBlltImageFixedWidth;
    private @Value("${pips.serviceServer.bllt.file.url}") String pipsServiceServerBlltFileUrl;
    //private @Value("${pips.serviceServer.bllt.file.download.url}") String pipsServiceServerBlltFileDownloadUrl;

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private HousingCplxService housingCplxService;

    @Autowired
    private ApiService apiService;

    /**
     * 시스템 관리 > 서비스 공지시항 > 서비스 공지사항 목록
     * @param noticeItem
     * @param model
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET,RequestMethod.POST})
    public String serviceNoticeList(NoticeItem noticeItem, Model model, BindingResult result, HttpServletRequest request) {
        List<NoticeItem> serviceNoticeList = noticeService.getServiceNoticeList(noticeItem);
        model.addAttribute("noticeList", serviceNoticeList);

        String start = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getStartCrDt()));
        String end = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getEndCrDt()));
        String title = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getTitle()));

        model.addAttribute("start", StringUtils.defaultIfEmpty(start, ""));
        model.addAttribute("end", StringUtils.defaultIfEmpty(end, ""));
        model.addAttribute("title", StringUtils.defaultIfEmpty(title, ""));

        return thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 서비스 공지시항 > 서비스 공지사항 상세
     * @param noticeItem
     * @param model
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "view", method = {RequestMethod.GET,RequestMethod.POST})
    public String serviceNoticeDetail(NoticeItem noticeItem, Model model, BindingResult result, HttpServletRequest request) {
        NoticeItem serviceNoticeDetail = noticeService.getServiceNoticeDetail(noticeItem);
        List<NoticeFile> serviceNoticeFileList = noticeService.getServiceNoticeDetailFile(noticeItem);
        List<NoticeItem> serviceHouscplxList = noticeService.getServiceHouscplxList(noticeItem);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < serviceHouscplxList.size(); i++) {
            NoticeItem tmp = serviceHouscplxList.get(i);
            sb.append(tmp.getHouscplxNm());

            if (i < serviceHouscplxList.size() - 1) {
                sb.append(", ");
            }
        }

        StringBuffer sb1 = new StringBuffer();
        for (int i = 0; i < serviceHouscplxList.size(); i++) {
            NoticeItem tmp = serviceHouscplxList.get(i);
            sb1.append(tmp.getHouscplxCd());

            if (i < serviceHouscplxList.size() - 1) {
                sb1.append(",");
            }
        }

        model.addAttribute("noticeDetail", serviceNoticeDetail);
        model.addAttribute("noticeFileList", serviceNoticeFileList);
        model.addAttribute("houscplxList", sb.toString());
        model.addAttribute("houscplxCdList", sb1.toString());
        //model.addAttribute("fileDownloadUrl", pipsServiceServerBlltFileDownloadUrl);

        return thisUrl + "/view";
    }

    /**
     * 시스템 관리 > 서비스 공지시항 > 서비스 공지사항 등록
     * @param housingCplx
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
    public String noticeAdd(HousingCplx housingCplx, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        housingCplx.setDelYn("N");

        return thisUrl + "/add";
    }

    /**
     * 시스템 관리 > 서비스 공지시항 > 서비스 공지사항 등록 처리
     * @param noticeItem
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "addNoticeAction", method = RequestMethod.POST)
    public String addNoticeAction(NoticeItem noticeItem, BindingResult result,
                                  Model model, HttpServletRequest request) {

        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
        noticeItem.setBlltTpCd("NOTICE");
        noticeItem.setBlltTpDtlCd("SERVICE");

        String mainPopupYn = request.getParameter("mainPopupYn");
        noticeItem.setMainPopupYn(mainPopupYn);

        String resultUrl = "redirect:/cm/system/notice/list";
        String resultMsg = "";

        String houscplxCdList = "";

        String paramNotiTitle = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getTitle()));
        String paramIsAttatchFile = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isAttachFile")));

        String notiTitle = StringUtils.defaultIfEmpty(paramNotiTitle, "");
        String notiCont = StringUtils.defaultIfEmpty(noticeItem.getCont(), "");
        String isAttachFile = StringUtils.defaultIfEmpty(paramIsAttatchFile, "");

        if (StringUtils.isEmpty(notiTitle)) {
            result.addError(new FieldError("title", "title", MessageUtil.getMessage("notEmpty.notiTitle.notiTitle")));
        }

        if (StringUtils.isEmpty(notiCont)) {
            result.addError(new FieldError("cont", "cont", MessageUtil.getMessage("notEmpty.notiCont.notiCont")));
        }

        String danjiArray = request.getParameter("danjiArray");
        danjiArray = StringUtils.defaultIfEmpty(danjiArray, "");

        if (StringUtils.isNotEmpty(danjiArray)) {
            String[] danjiList = danjiArray.split(",");
            List houscplxList = new ArrayList();

            for (int i = 0; i < danjiList.length; i++) {
                String targetHouscplxCd = danjiList[i];

                NoticeItem tmp = new NoticeItem();
                tmp.setHouscplxCd(targetHouscplxCd);
                tmp.setBlltNo(0);
                tmp.setCrerId(adminId);

                if(i == 0) {
                    houscplxCdList = targetHouscplxCd;
                } else {
                    houscplxCdList = houscplxCdList + "," + targetHouscplxCd;
                }

                houscplxList.add(tmp);
            }
            noticeItem.setHouscplxList(houscplxList);
        }

        String htmlNotiCont = StringEscapeUtils.unescapeHtml4(noticeItem.getCont()).replace("& lt;", "<").replace("& gt;", ">");
        noticeItem.setCont(htmlNotiCont);
        noticeItem.setCrerId(adminId);
        noticeItem.setHouscplxCd(houscplxCd);

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        if (isAttachFile.equals("Y")) {
            try {
                List<UploadFileInfo> fileList = new ArrayList<UploadFileInfo>();

                FileUploadUtil fileUploadUtil = new FileUploadUtil();

                fileRootPath = xssUtil.replaceAll(StringUtils.defaultString(fileRootPath));
                fileContentsSystemUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsSystemUploadPath));

                String realPath = fileRootPath + fileContentsSystemUploadPath;

                List<String> originalFileNameList = new ArrayList<>();
                List<String> thumbnailFileNameList = new ArrayList<>();

                MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
                MultipartFile mpf = mpRequest.getFile("inputGroupFile01");

                if (mpf != null) {
                    String realOriginalFileName = xssUtil.replaceTag(StringUtils.defaultString(mpf.getOriginalFilename()));

                    boolean isFile = ValidationUtil.validate(realOriginalFileName, ValidationUtil.FILE_PATTERN);
                    boolean isImageFile = ValidationUtil.validate(realOriginalFileName, ValidationUtil.IMAGE_PATTERN);

                    if (isFile || isImageFile) {
                        String originalFileName = fileUploadUtil.makeFileName(false, "", "bllt", true, realOriginalFileName);
                        String thumbnailFileName = fileUploadUtil.makeFileName(false, "", "bllt", false, "");

                        int fileIndex = originalFileName.lastIndexOf(".");
                        String fileExtension = originalFileName.substring(fileIndex+1).toLowerCase();

                        if (mpf.getSize() > 0) {
                            if (isFile) {
                                fileUploadUtil.uploadOriginalFile(mpf, realPath , thumbnailFileName);
                            } else if (isImageFile) {
                                UploadFileInfo originalUploadFileInfo = fileUploadUtil.uploadOriginalFile(mpf, realPath , originalFileName);

                                fileList.add(originalUploadFileInfo);
                                logger.info("\noriginalUploadFileInfo : {} ", originalUploadFileInfo.toString().replaceAll("\\]", "\n]").replaceAll("\\[", "\n[\n").replaceAll(",", ",\n"));

                                File thumbnailFile = fileUploadUtil.multipartToFile(mpf);

                                String originalRealPath = realPath + "/" + originalFileName;

                                BufferedImage image = ImageIO.read(new FileInputStream(originalRealPath));
                                int originalWidth = image.getWidth();
                                int originalHeight = image.getHeight();

                                if (originalWidth > fileImageBlltImageFixedWidth) {
                                    double compareWidth = image.getWidth();
                                    double rate = fileImageBlltImageFixedWidth / compareWidth;

                                    originalWidth = (int) Math.round(originalWidth * rate) ;
                                    originalHeight = (int) Math.round(originalHeight * rate);
                                }

                                fileUploadUtil.uploadThumbnailImageFile(thumbnailFile, realPath, thumbnailFileName, originalWidth, originalHeight, fileExtension);

                                boolean isTempFileDeleted =  thumbnailFile.delete();

                                logger.debug("thumbnailFile AbsolutePath: " + thumbnailFile.getAbsolutePath());
                                logger.debug("thumbnailFile CanonicalPath: " + thumbnailFile.getCanonicalPath());
                                logger.debug("isTempDeleted is True? : " + BooleanUtils.isTrue(isTempFileDeleted));
                            }

                            originalFileNameList.add(originalFileName);
                            thumbnailFileNameList.add(thumbnailFileName);
                        }
                    }
                }

                communityService.insertNoticeInfo(noticeItem, originalFileNameList, thumbnailFileNameList, houscplxCd);

                // 공지게시일 경우 Push 전송 API 호출
                Integer blltNo = null;
                blltNo = noticeItem.getBlltNo();
                if (blltNo != null && noticeItem.getTlrncYn().equals("Y")) {
                    if(houscplxCdList.equals("")){
                        houscplxCdList = "*";
                    }
                    sendPush(blltNo, houscplxCdList);
                }

            } catch (Exception e) {
                logger.error("addNoticeAction Error: " + e.getCause());
            }
        } else {
            communityService.insertNotice(noticeItem, houscplxCd);

            // 공지게시일 경우 Push 전송 API 호출
            Integer blltNo = null;
            blltNo = noticeItem.getBlltNo();
            if (blltNo != null && noticeItem.getTlrncYn().equals("Y")) {
                if(houscplxCdList.equals("")){
                    houscplxCdList = "*";
                }
                sendPush(blltNo, houscplxCdList);
            }

        }

        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");

        if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
            model.addAttribute("noticeList", communityService.getNoticeListForAdmin(noticeItem));
        } else if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            noticeItem.setHouscplxCd(houscplxCd);
            model.addAttribute("noticeList", communityService.getNoticeList(noticeItem));
        }

        return resultUrl;
    }

    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String noticeEdit(NoticeItem noticeItem, HousingCplx housingCplx, Model model, HttpServletRequest request) {
        NoticeItem serviceNoticeDetail = noticeService.getServiceNoticeDetail(noticeItem);
        List<NoticeFile> serviceNoticeFileList = noticeService.getServiceNoticeDetailFile(noticeItem);
        List<NoticeItem> serviceHouscplxList = noticeService.getServiceHouscplxList(noticeItem);

        model.addAttribute("noticeDetail", serviceNoticeDetail);
        model.addAttribute("noticeFileList", serviceNoticeFileList);
        model.addAttribute("houscplxList", serviceHouscplxList);

        return thisUrl + "/edit";
    }

    /**
     * 단지 커뮤니티 관리 > 단지 공지사항 관리
     * 단지 관리자용 단지 공지사항 상태를 수정 처리 (공지게시)
     * @param noticeItem
     * @param model
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "editNoticePublishAction", method = RequestMethod.POST)
    public String editNoticePublishAction(NoticeItem noticeItem, Model model, BindingResult result, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String resultUrl = "redirect:/cm/system/notice/list";
        String resultMsg = "";

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String editerId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        noticeItem.setHouscplxCd(houscplxCd);
        noticeItem.setEditerId(editerId);

        String paramTlrncYn = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getTlrncYn()));
        String tlrncYn = StringUtils.defaultIfEmpty(paramTlrncYn, "");

        if (StringUtils.isEmpty(tlrncYn)) {
            result.addError(new FieldError("tlrncYn", "tlrncYn", MessageUtil.getMessage("notEmpty.tlrncYn.tlrncYn")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        communityService.updateNoticeTlrncYn(noticeItem);

        String houscplxCdList = request.getParameter("houscplxCdList");

        // 공지게시일 경우 Push 전송 API 호출
        Integer blltNo = null;
        blltNo = noticeItem.getBlltNo();
        if (blltNo != null && noticeItem.getTlrncYn().equals("Y")) {
            if(houscplxCdList.equals("")){
                houscplxCdList = "*";
            }
            sendPush(blltNo, houscplxCdList);
        }

        communityService.deleteUserNotiCfRlt(noticeItem.getBlltNo());

        return resultUrl;
    }


    @RequestMapping(value = "editNoticeAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String editNoticeAction(NoticeItem noticeItem, Model model, BindingResult result, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String mainPopupYn = request.getParameter("mainPopupYn");
        noticeItem.setMainPopupYn(mainPopupYn);

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        noticeItem.setHouscplxCd(houscplxCd);

        String resultUrl = "redirect:/cm/system/notice/view";
        String resultMsg = "";

        String paramNotiTitle = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getTitle()));
        String paramIsAttachFileChange = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isAttachFileChange")));
        String paramIsAttachFileRemove = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isAttachFileRemove")));

        String notiTitle = StringUtils.defaultIfEmpty(paramNotiTitle, "");
        String notiCont = xssUtil.replaceSpecialCharacter(StringUtils.defaultIfEmpty(noticeItem.getCont(), ""));
        String isAttachFileChange = StringUtils.defaultIfEmpty(paramIsAttachFileChange, "");
        String isAttachFileRemove = StringUtils.defaultIfEmpty(paramIsAttachFileRemove, "");
        noticeItem.setCrerId(adminId);
        noticeItem.setEditerId(adminId);

        if (StringUtils.isEmpty(notiTitle)) {
            result.addError(new FieldError("title", "title", MessageUtil.getMessage("notEmpty.notiTitle.notiTitle")));
        }

        if (StringUtils.isEmpty(notiCont)) {
            result.addError(new FieldError("cont", "cont", MessageUtil.getMessage("notEmpty.notiCont.notiCont")));
        }

        String htmlNotiCont = StringEscapeUtils.unescapeHtml4(noticeItem.getCont()).replace("& lt;", "<").replace("& gt;", ">");
        noticeItem.setCont(htmlNotiCont);

        String danjiArray = request.getParameter("danjiArray");
        danjiArray = StringUtils.defaultIfEmpty(danjiArray, "");

        if (StringUtils.isNotEmpty(danjiArray)) {
            String[] danjiList = danjiArray.split(",");
            List houscplxList = new ArrayList();

            for (int i = 0; i < danjiList.length; i++) {
                String targetHouscplxCd = danjiList[i];

                NoticeItem tmp = new NoticeItem();
                tmp.setHouscplxCd(targetHouscplxCd);
                tmp.setBlltNo(noticeItem.getBlltNo());
                tmp.setCrerId(session.getUserId());

                houscplxList.add(tmp);
            }
            noticeItem.setHouscplxList(houscplxList);
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        if (isAttachFileChange.equals("Y")) {
            try {
                List<UploadFileInfo> fileList = new ArrayList<UploadFileInfo>();

                FileUploadUtil fileUploadUtil = new FileUploadUtil();

                fileRootPath = xssUtil.replaceAll(StringUtils.defaultString(fileRootPath));
                fileContentsSystemUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsSystemUploadPath));

                String realPath = fileRootPath + fileContentsSystemUploadPath;

                List<String> originalFileNameList = new ArrayList<>();
                List<String> thumbnailFileNameList = new ArrayList<>();

                MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
                MultipartFile mpf = mpRequest.getFile("inputGroupFile01");

                if (mpf.getSize() > 0) {
                    String realOriginalFileName = xssUtil.replaceTag(StringUtils.defaultString(mpf.getOriginalFilename()));

                    boolean isFile = ValidationUtil.validate(realOriginalFileName, ValidationUtil.FILE_PATTERN);
                    boolean isImageFile = ValidationUtil.validate(realOriginalFileName, ValidationUtil.IMAGE_PATTERN);

                    if (isFile || isImageFile) {
                        String originalFileName = fileUploadUtil.makeFileName(false, "", "bllt", true, realOriginalFileName);
                        String thumbnailFileName = fileUploadUtil.makeFileName(false, "", "bllt", false, "");

                        int fileIndex = originalFileName.lastIndexOf(".");
                        String fileExtension = originalFileName.substring(fileIndex+1).toLowerCase();

                        if (isFile) {
                            fileUploadUtil.uploadOriginalFile(mpf, realPath , thumbnailFileName);
                        } else if (isImageFile) {
                            UploadFileInfo originalUploadFileInfo = fileUploadUtil.uploadOriginalFile(mpf, realPath , originalFileName);

                            fileList.add(originalUploadFileInfo);
                            logger.info("\noriginalUploadFileInfo : {} ", originalUploadFileInfo.toString().replaceAll("\\]", "\n]").replaceAll("\\[", "\n[\n").replaceAll(",", ",\n"));

                            File thumbnailFile = fileUploadUtil.multipartToFile(mpf);

                            String originalRealPath = realPath + "/" + originalFileName;

                            BufferedImage image = ImageIO.read(new FileInputStream(originalRealPath));
                            int originalWidth = image.getWidth();
                            int originalHeight = image.getHeight();

                            if (originalWidth > fileImageBlltImageFixedWidth) {
                                double compareWidth = image.getWidth();
                                double rate = fileImageBlltImageFixedWidth / compareWidth;

                                originalWidth = (int) Math.round(originalWidth * rate) ;
                                originalHeight = (int) Math.round(originalHeight * rate);
                            }

                            fileUploadUtil.uploadThumbnailImageFile(thumbnailFile, realPath, thumbnailFileName, originalWidth, originalHeight, fileExtension);

                            boolean isTempFileDeleted =  thumbnailFile.delete();

                            logger.debug("thumbnailFile AbsolutePath: " + thumbnailFile.getAbsolutePath());
                            logger.debug("thumbnailFile CanonicalPath: " + thumbnailFile.getCanonicalPath());
                            logger.debug("isTempDeleted is True? : " + BooleanUtils.isTrue(isTempFileDeleted));
                        }

                        originalFileNameList.add(originalFileName);
                        thumbnailFileNameList.add(thumbnailFileName);
                    }
                }

                noticeService.updateNoticeInfo(noticeItem, originalFileNameList, thumbnailFileNameList, isAttachFileRemove);
                communityService.deleteUserNotiCfRlt(noticeItem.getBlltNo());
            } catch (Exception e) {
                logger.error("editNoticeAction Error: " + e.getCause());
            }
        } else if (isAttachFileChange.equals("N")){
            if (!noticeService.updateNotice(noticeItem)) {
                result.addError(new FieldError("notiTitle", "notiTitle", MessageUtil.getMessage("msg.common.error")));
            } else {
                communityService.deleteUserNotiCfRlt(noticeItem.getBlltNo());
            }
        }

        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("blltNo", noticeItem.getBlltNo());

        return resultUrl;
    }

    @RequestMapping(value = "deleteNoticeAction", method = RequestMethod.POST)
    public String deleteNoticeAction(NoticeItem noticeItem, Model model, BindingResult result, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String adminId = StringUtils.defaultIfEmpty(session.getUserId(), "");
        noticeItem.setEditerId(adminId);

        String resultUrl = "redirect:/cm/system/notice/list";
        String resultMsg = "";

        Integer blltNo = noticeItem.getBlltNo();

        logger.debug(">>>> blltNo : " + blltNo);

        if (blltNo == null) {
            result.addError(new FieldError("blltNo", "blltNo", MessageUtil.getMessage("notEmpty.blltNo.blltNo")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        communityService.deleteNoticeInfo(noticeItem, adminId);
        communityService.deleteUserNotiCfRlt(blltNo);

        return resultUrl;
    }

    public void sendPush(int blltNo, String houscplxCdList){
        String pushTargetNo = String.valueOf(blltNo);
        pushTargetNo = xssUtil.replaceAll(StringUtils.defaultString(pushTargetNo));

        String tpCd = "N";
        List<String> houscplxList = new ArrayList<>();
        houscplxList.add(houscplxCdList);

        apiService.sendPush(pushTargetNo, tpCd, houscplxList);
    }

}