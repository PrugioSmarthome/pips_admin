package com.daewooenc.pips.admin.web.controller.community;

import com.daewooenc.pips.admin.core.domain.UploadFileInfo;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.FileUploadUtil;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.domain.dto.community.*;
import com.daewooenc.pips.admin.web.service.api.ApiService;
import com.daewooenc.pips.admin.web.service.community.CommunityService;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.ValidationUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * 공지사항, 설문조사 및 생활불편신고 관련 Controller
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
@RequestMapping("/cm/community")
public class CommunityController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/community";

    @Autowired
    private CommunityService communityService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private XSSUtil xssUtil;

    private @Value("${file.rootPath}") String fileRootPath;
    private @Value("${file.contents.complexUploadPath}") String fileContentsComplexUploadPath;
    private @Value("${file.image.bllt.fixedWidth}") int fileImageBlltImageFixedWidth;
    private @Value("${pips.serviceServer.bllt.file.url}") String pipsServiceServerBlltFileUrl;

    /**
     * 단지 커뮤니티 관리 > 단지 공지사항 관리 > 단지 공지사항 목록
     * 시스템 및 단지 관리자용 단지 공지사항 목록을 조회
     * @param noticeItem
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "notice/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String noticeList(NoticeItem noticeItem, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String paramDelYn = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("delYn")));

        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");
        String delYn = StringUtils.defaultIfEmpty(paramDelYn, "N");
        noticeItem.setDelYn(delYn);

        if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
            model.addAttribute("noticeList", communityService.getNoticeListForAdmin(noticeItem));
        } else if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));

            String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, "");
            noticeItem.setHouscplxCd(houscplxCd);
            model.addAttribute("noticeList", communityService.getNoticeList(noticeItem));
        }else if(UserType.MULTI_COMPLEX.getGroupName().equals(groupName)){
            noticeItem.setUserId(session.getUserId());
            model.addAttribute("noticeList", communityService.getMultiNoticeListForAdmin(noticeItem));
        }

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getHouscplxCd()));
        String paramTitle = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getTitle()));
        String paramNoticeDelYn = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getDelYn()));
        String paramStartCrDt = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getStartCrDt()));
        String paramEndCrDt = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getEndCrDt()));
        String paramHouscplxNm = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getHouscplxNm()));

        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, "");
        String title = StringUtils.defaultIfEmpty(paramTitle, "");
        String noticeDelYn = StringUtils.defaultIfEmpty(paramNoticeDelYn,"N");
        String startCrDt = StringUtils.defaultIfEmpty(paramStartCrDt, "");
        String endCrDt = StringUtils.defaultIfEmpty(paramEndCrDt, "");
        String houscplxNm = StringUtils.defaultIfEmpty(paramHouscplxNm, "");

        model.addAttribute("userId", StringUtils.defaultIfEmpty(session.getUserId(), ""));
        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("title", title);
        model.addAttribute("delYn", noticeDelYn);
        model.addAttribute("startCrDt", startCrDt);
        model.addAttribute("endCrDt", endCrDt);
        model.addAttribute("houscplxNm", houscplxNm);

        return thisUrl + "/notice/list";
    }

    /**
     * 단지 커뮤니티 관리 > 단지 공지사항 관리 > 단지 공지사항 목록 > 단지 공지사항 상세
     * 시스템 및 단지 관리자용 단지 공지사항 상세내용을 조회
     * @param noticeItem
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "notice/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String noticeView(NoticeItem noticeItem, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String paramBlltNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("blltNo")));

        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);
        String blltNo = StringUtils.defaultIfEmpty(paramBlltNo, "");

        if (StringUtils.isNotEmpty(blltNo)) {
            Integer parseBlltNo = Integer.parseInt(blltNo);

            noticeItem.setBlltNo(parseBlltNo);
            noticeItem.setHouscplxCd(houscplxCd);
        }

        if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
            model.addAttribute("noticeDetail", communityService.getNoticeDetailForAdmin(noticeItem));
        } else if (UserType.COMPLEX.getGroupName().equals(groupName) || UserType.MULTI_COMPLEX.getGroupName().equals(groupName)) {
            model.addAttribute("noticeDetail", communityService.getNoticeDetail(noticeItem));
        }

        model.addAttribute("noticeFileList", communityService.getNoticeDetailFile(noticeItem));

        return thisUrl + "/notice/view";
    }

    /**
     * 단지 커뮤니티 관리 > 단지 공지사항 관리 > 단지 공지사항 등록
     * 단지 관리자용 단지 공지사항 등록
     * @param noticeItem
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "notice/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String noticeAdd(NoticeItem noticeItem, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        model.addAttribute("userId", session.getUserId());

        return thisUrl + "/notice/add";
    }

    /**
     * 단지 커뮤니티 관리 > 단지 공지사항 관리 > 단지 공지사항 등록 처리
     * 단지 관리자용 단지 공지사항 등록을 처리
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
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));

        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);
        noticeItem.setBlltTpDtlCd("HOUSCPLX");
        noticeItem.setSvcNotiTpCd("PART");
        noticeItem.setBlltTpCd("NOTICE");

        String resultUrl = "redirect:/cm/community/notice/list";
        String resultMsg = "";

        String paramNotiTitle = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getTitle()));
        String paramNotiCont = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getCont()));

        String notiTitle = StringUtils.defaultIfEmpty(paramNotiTitle, "");
        String notiCont = StringUtils.defaultIfEmpty(paramNotiCont, "");
        String hmnetNotiCont = StringUtils.defaultIfEmpty(noticeItem.getHmnetNotiCont(), "");

        String paramIsAttachFile = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isAttachFile")));
        String isAttachFile = StringUtils.defaultIfEmpty(paramIsAttachFile, "");
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));

        if (StringUtils.isEmpty(notiTitle)) {
            result.addError(new FieldError("title", "title", MessageUtil.getMessage("notEmpty.notiTitle.notiTitle")));
        }

        if (StringUtils.isEmpty(notiCont) && StringUtils.isEmpty(hmnetNotiCont)) {
            result.addError(new FieldError("cont", "cont", MessageUtil.getMessage("notEmpty.notiCont.notiCont")));
        }

        String htmlNotiCont = StringEscapeUtils.unescapeHtml4(noticeItem.getCont()).replace("& lt;", "<").replace("& gt;", ">");
        String userNm = paramSessionUserId;

        noticeItem.setTitle(notiTitle);
        noticeItem.setCont(htmlNotiCont);
        noticeItem.setCrerId(userNm);
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
                fileContentsComplexUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsComplexUploadPath));

                String realPath = fileRootPath + fileContentsComplexUploadPath;

                List<String> originalFileNameList = new ArrayList<>();
                List<String> thumbnailFileNameList = new ArrayList<>();

                MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
                MultipartFile mpf = mpRequest.getFile("inputGroupFile01");

                if (mpf.getSize() > 0) {
                    String realOriginalFileName = xssUtil.replaceTag(StringUtils.defaultString(mpf.getOriginalFilename()));

                    boolean isFile = ValidationUtil.validate(realOriginalFileName, ValidationUtil.FILE_PATTERN);
                    boolean isImageFile = ValidationUtil.validate(realOriginalFileName, ValidationUtil.IMAGE_PATTERN);

                    if (isFile || isImageFile) {
                        String originalFileName = fileUploadUtil.makeFileName(true, houscplxCd, "bllt", true, realOriginalFileName);
                        String thumbnailFileName = fileUploadUtil.makeFileName(true, houscplxCd, "bllt", false, "");

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
                String danjiInfo = request.getParameter("danjiArray");
                if(danjiInfo != null && !"".equals(danjiInfo)) {
                    houscplxCd = danjiInfo;
                }

                communityService.insertNoticeInfo(noticeItem, originalFileNameList, thumbnailFileNameList, houscplxCd);

                // 공지게시일 경우 Push 전송 API 호출
                Integer blltNo = null;
                blltNo = noticeItem.getBlltNo();
                if (blltNo != null && noticeItem.getTlrncYn().equals("Y")) {
                    sendPush(blltNo, houscplxCd);
                }

            } catch (Exception e) {
                logger.error("addNoticeAction Error: " + e.getCause());
            }
        } else {
            String danjiInfo = request.getParameter("danjiArray");
            if(danjiInfo != null && !"".equals(danjiInfo)) {
                houscplxCd = danjiInfo;
            }

            communityService.insertNotice(noticeItem, houscplxCd);

            // 공지게시일 경우 Push 전송 API 호출
            Integer blltNo = null;
            blltNo = noticeItem.getBlltNo();
            if (blltNo != null && noticeItem.getTlrncYn().equals("Y")) {
                sendPush(blltNo, houscplxCd);
            }

        }

        if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
            model.addAttribute("noticeList", communityService.getNoticeListForAdmin(noticeItem));
        } else if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            noticeItem.setHouscplxCd(houscplxCd);
            model.addAttribute("noticeList", communityService.getNoticeList(noticeItem));
        }else if(UserType.MULTI_COMPLEX.getGroupName().equals(groupName)){
            noticeItem.setUserId(session.getUserId());
            model.addAttribute("noticeList", communityService.getMultiNoticeListForAdmin(noticeItem));
        }

        return resultUrl;
    }

    /**
     * 단지 커뮤니티 관리 > 단지 공지사항 관리 > 단지 공지사항 수정
     * 단지 관리자용 단지 공지사항 수정
     * @param noticeItem
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "notice/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String noticeEdit(NoticeItem noticeItem, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));

        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);
        noticeItem.setHouscplxCd(houscplxCd);

        model.addAttribute("noticeDetail", communityService.getNoticeDetail(noticeItem));
        model.addAttribute("noticeFileList", communityService.getNoticeDetailFile(noticeItem));

        return thisUrl + "/notice/edit";
    }

    /**
     * 단지 커뮤니티 관리 > 단지 공지사항 관리 > 단지 공지사항 수정 처리
     * 단지 관리자용 단지 공지사항 수정을 처리
     * @param noticeItem
     * @param model
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "editNoticeAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String editNoticeAction(NoticeItem noticeItem, Model model, BindingResult result, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String groupName = StringUtils.defaultIfEmpty(session.getUserGroupName(), "");

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        session.setHouscplxCd(reqParamHouscplxCd);

        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);
        noticeItem.setHouscplxCd(houscplxCd);

        String resultUrl = "redirect:/cm/community/notice/view";
        String resultMsg = "";

        String paramNotiTitle = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getTitle()));
        String paramNotiCont = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getCont()));
        String paramIsAttachFileChange = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isAttachFileChange")));
        String paramIsAttachFileRemove = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isAttachFileRemove")));

        String notiTitle = StringUtils.defaultIfEmpty(paramNotiTitle, "");
        String notiCont = StringUtils.defaultIfEmpty(paramNotiCont, "");
        String hmnetNotiCont = StringUtils.defaultIfEmpty(noticeItem.getHmnetNotiCont(), "");
        String isAttachFileChange = StringUtils.defaultIfEmpty(paramIsAttachFileChange, "");
        String isAttachFileRemove = StringUtils.defaultIfEmpty(paramIsAttachFileRemove, "");

        if (StringUtils.isEmpty(notiTitle)) {
            result.addError(new FieldError("title", "title", MessageUtil.getMessage("notEmpty.notiTitle.notiTitle")));
        }

        if (StringUtils.isEmpty(notiCont) && StringUtils.isEmpty(hmnetNotiCont)) {
            result.addError(new FieldError("cont", "cont", MessageUtil.getMessage("notEmpty.notiCont.notiCont")));
        }

        String htmlNotiCont = StringEscapeUtils.unescapeHtml4(noticeItem.getCont()).replace("& lt;", "<").replace("& gt;", ">");

        noticeItem.setTitle(notiTitle);
        noticeItem.setCont(htmlNotiCont);
        noticeItem.setEditerId(sessionUserId);

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
                fileContentsComplexUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsComplexUploadPath));

                String realPath = fileRootPath + fileContentsComplexUploadPath;

                List<String> originalFileNameList = new ArrayList<>();
                List<String> thumbnailFileNameList = new ArrayList<>();

                MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
                MultipartFile mpf = mpRequest.getFile("inputGroupFile01");

                if (mpf.getSize() > 0) {
                    String realOriginalFileName = xssUtil.replaceTag(StringUtils.defaultString(mpf.getOriginalFilename()));

                    boolean isFile = ValidationUtil.validate(realOriginalFileName, ValidationUtil.FILE_PATTERN);
                    boolean isImageFile = ValidationUtil.validate(realOriginalFileName, ValidationUtil.IMAGE_PATTERN);

                    if (isFile || isImageFile) {
                        String originalFileName = fileUploadUtil.makeFileName(true, houscplxCd, "bllt", true, realOriginalFileName);
                        String thumbnailFileName = fileUploadUtil.makeFileName(true, houscplxCd, "bllt", false, "");

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

                communityService.updateNoticeInfo(noticeItem, originalFileNameList, thumbnailFileNameList, isAttachFileRemove);
                communityService.deleteUserNotiCfRlt(noticeItem.getBlltNo());
            } catch (Exception e) {
                logger.error("editNoticeAction Error: " + e.getCause());
            }
        } else if (isAttachFileChange.equals("N")){
            if (!communityService.updateNotice(noticeItem)) {
                result.addError(new FieldError("notiTitle", "notiTitle", MessageUtil.getMessage("msg.common.error")));
            } else {
                communityService.deleteUserNotiCfRlt(noticeItem.getBlltNo());
            }
        }

        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("blltNo", noticeItem.getBlltNo());

        return resultUrl;
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
        String resultUrl = "redirect:/cm/community/notice/list";
        String resultMsg = "";
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        if(UserType.MULTI_COMPLEX.getGroupName().equals(groupName)){
            paramHouscplxCd = request.getParameter("multiHouscplxCd");
        }
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String editerId = paramSessionUserId;
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

        // 공지게시일 경우 Push 전송 API 호출
        Integer blltNo = null;
        blltNo = noticeItem.getBlltNo();
        if (blltNo != null && noticeItem.getTlrncYn().equals("Y")) {
            sendPush(blltNo, houscplxCd);
        }

        communityService.deleteUserNotiCfRlt(noticeItem.getBlltNo());

        return resultUrl;
    }

    /**
     * 단지 커뮤니티 관리 > 단지 공지사항 관리
     * 단지 관리자용 게시되지 않은 공지사항을 삭제 처리
     * @param noticeItem
     * @param model
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteNoticeAction", method = RequestMethod.POST)
    public String deleteNoticeAction(NoticeItem noticeItem, Model model, BindingResult result, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        noticeItem.setEditerId(session.getUserId());

        String resultUrl = "redirect:/cm/community/notice/list";
        String resultMsg = "";

        Integer blltNo = null;
        blltNo = noticeItem.getBlltNo();

        if (blltNo == null) {
            result.addError(new FieldError("blltNo", "blltNo", MessageUtil.getMessage("notEmpty.blltNo.blltNo")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        communityService.deleteNoticeInfo(noticeItem, paramSessionUserId);
        communityService.deleteUserNotiCfRlt(blltNo);

        return resultUrl;
    }

    /**
     * 단지 커뮤니티 관리 > 설문조사 관리 > 설문조사 목록
     * 시스템 및 단지 관리자용 설문조사 목록을 조회
     * @param question
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "question/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String questionList(Question question, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        String groupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String paramDelYn = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("delYn")));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);
        String delYn = StringUtils.defaultIfEmpty(paramDelYn, "N");
        question.setDelYn(delYn);

        if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
            model.addAttribute("questionList", communityService.getQuestionListForAdmin(question));
        } else if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            question.setHouscplxCd(houscplxCd);
            model.addAttribute("questionList", communityService.getQuestionList(question));
        } else if (UserType.MULTI_COMPLEX.getGroupName().equals(groupName)){
            question.setUserId(session.getUserId());
            model.addAttribute("questionList", communityService.getMultiQuestionList(question));
        }

        String paramSearchHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(question.getHouscplxCd()));
        String qstTitle = xssUtil.replaceAll(StringUtils.defaultString(question.getQstTitle()));
        String qstDelYn = xssUtil.replaceAll(StringUtils.defaultString(question.getDelYn()));
        String qstStartCrDt = xssUtil.replaceAll(StringUtils.defaultString(question.getStartCrDt()));
        String qstEndCrDt = xssUtil.replaceAll(StringUtils.defaultString(question.getEndCrDt()));
        String houscplxNm = xssUtil.replaceAll(StringUtils.defaultString(question.getHouscplxNm()));

        model.addAttribute("userId", session.getUserId());
        model.addAttribute("houscplxCd", paramSearchHouscplxCd);
        model.addAttribute("qstTitle", qstTitle);
        model.addAttribute("delYn", StringUtils.defaultIfEmpty(qstDelYn,"N"));
        model.addAttribute("startCrDt", qstStartCrDt);
        model.addAttribute("endCrDt", qstEndCrDt);
        model.addAttribute("houscplxNm", houscplxNm);

        return thisUrl + "/question/list";
    }

    /**
     * 단지 커뮤니티 관리 > 설문조사 관리 > 설문조사 목록 > 설문조사 상세
     * 시스템 및 단지 관리자용 설문조사 상세내용을 조회
     * @param questionDetail
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "question/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String questionView(QuestionDetail questionDetail, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        String groupId = StringUtils.defaultIfEmpty(session.getUserGroupId(), "");
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);
        String paramQstNo = xssUtil.replaceSpecialCharacter(StringUtils.defaultString(request.getParameter("qstNo")));
        String qstNo = StringUtils.defaultIfEmpty(paramQstNo,"");

        questionDetail.setHouscplxCd(houscplxCd);
        questionDetail.setQstNo(Integer.parseInt(qstNo));
        questionDetail.setHouscplxCd(houscplxCd);

        if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
            model.addAttribute("questionDetail", communityService.getQuestionDetailForAdmin(questionDetail));
        } else if (UserType.COMPLEX.getGroupName().equals(groupName) || UserType.MULTI_COMPLEX.getGroupName().equals(groupName)) {
            model.addAttribute("questionDetail", communityService.getQuestionDetail(questionDetail));
        }

        QuestionCompletion questionCompletion = new QuestionCompletion();

        questionCompletion.setQstNo(Integer.parseInt(qstNo));
        questionCompletion.setHouscplxCd(houscplxCd);

        model.addAttribute("questionItemList", communityService.getQuestionItemList(questionDetail));
        model.addAttribute("questionEtcAnswerList", communityService.getQuestionEtcAnswerList(questionCompletion));

        return thisUrl + "/question/view";
    }

    /**
     * 단지 커뮤니티 관리 > 설문조사 관리 > 설문조사 등록
     * 단지 관리자용 설문조사 등록
     * @param question
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "question/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String questionAdd(Question question, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        model.addAttribute("userId", session.getUserId());

        return thisUrl + "/question/add";
    }

    /**
     * 단지 커뮤니티 관리 > 설문조사 관리 > 설문조사 등록을 처리
     * 단지 관리자용 설문조사 등록을 처리
     * @param question
     * @param questionDetail
     * @param model
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "addQuestionAction", method = RequestMethod.POST)
    public String addQuestionAction(Question question, QuestionDetail questionDetail, Model model,
                                    BindingResult result, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));

        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        if(UserType.MULTI_COMPLEX.getGroupName().equals(groupName)){
            houscplxCd = request.getParameter("danjiArray");
        }

        String resultUrl = "redirect:/cm/community/question/list";
        String resultMsg = "";

        String paramQstTitle = xssUtil.replaceAll(StringUtils.defaultString(question.getQstTitle()));
        String paramQstStDt = xssUtil.replaceAll(StringUtils.defaultString(question.getQstStDt()));
        String paramQstEdDt = xssUtil.replaceAll(StringUtils.defaultString(question.getQstEdDt()));
        String paramQstTpCd = xssUtil.replaceAll(StringUtils.defaultString(question.getQstTpCd()));
        String paramQstItmQuestCont = xssUtil.replaceAll(StringUtils.defaultString(questionDetail.getQstItmQuestCont()));

        String qstTitle = StringUtils.defaultIfEmpty(paramQstTitle, "");
        String qstStDt = StringUtils.defaultIfEmpty(paramQstStDt, "");
        String qstEdDt = StringUtils.defaultIfEmpty(paramQstEdDt, "");
        String qstTpCd = StringUtils.defaultIfEmpty(paramQstTpCd, "");
        String qstItmQuestCont = StringUtils.defaultIfEmpty(paramQstItmQuestCont, "");

        if (StringUtils.isEmpty(qstTitle)) {
            result.addError(new FieldError("qstTitle", "qstTitle", MessageUtil.getMessage("notEmpty.qstTitle.qstTitle")));
        }

        if (StringUtils.isEmpty(qstStDt)) {
            result.addError(new FieldError("qstStDt", "qstStDt", MessageUtil.getMessage("notEmpty.qstStDt.qstStDt")));
        }

        if (StringUtils.isEmpty(qstEdDt)) {
            result.addError(new FieldError("qstEdDt", "qstEdDt", MessageUtil.getMessage("notEmpty.qstEdDt.qstEdDt")));
        }

        if (StringUtils.isEmpty(qstTpCd)) {
            result.addError(new FieldError("qstTpCd", "qstTpCd", MessageUtil.getMessage("notEmpty.qstTpCd.qstTpCd")));
        }

        if (StringUtils.isEmpty(qstItmQuestCont)) {
            result.addError(new FieldError("qstItmQuestCont", "qstItmQuestCont", MessageUtil.getMessage("notEmpty.qstItmQuestCont.qstItmQuestCont")));
        }

        JSONArray qstItmAnsrContJsonArray = new JSONArray(request.getParameter("qstItmAnsrContList"));

        if (request.getParameter("qstItmAnsrContList") == null) {
            result.addError(new FieldError("qstItmAnsrContList", "qstItmAnsrContList", MessageUtil.getMessage("notEmpty.qstItmAnsrContList.qstItmAnsrContList")));
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("addQuestionAction", null);
            model.addAttribute("resultMsg", resultMsg);
        }
        question.setHouscplxCd(houscplxCd);
        question.setCrerId(sessionUserId);
        communityService.insertQuestionInfo(question, questionDetail, qstItmAnsrContJsonArray);

        if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
            model.addAttribute("questionList", communityService.getQuestionListForAdmin(question));
        } else if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            model.addAttribute("questionList", communityService.getQuestionList(question));
        }

        return resultUrl;
    }

    /**
     * 단지 커뮤니티 관리 > 설문조사 관리 > 설문조사 수정
     * 단지 관리자용 설문조사 수정
     * @param questionDetail
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "question/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String questionEdit(QuestionDetail questionDetail, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));

        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        questionDetail.setHouscplxCd(houscplxCd);

        if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
            model.addAttribute("questionDetail", communityService.getQuestionDetailForAdmin(questionDetail));
        } else if (UserType.COMPLEX.getGroupName().equals(groupName) || UserType.MULTI_COMPLEX.getGroupName().equals(groupName)) {
            model.addAttribute("questionDetail", communityService.getQuestionDetail(questionDetail));
        }

        QuestionCompletion questionCompletion = new QuestionCompletion();

        Integer qstNo = null;
        qstNo = questionDetail.getQstNo();

        questionCompletion.setQstNo(qstNo);
        questionCompletion.setHouscplxCd(houscplxCd);

        model.addAttribute("questionItemList", communityService.getQuestionItemList(questionDetail));
        model.addAttribute("questionEtcAnswerList", communityService.getQuestionEtcAnswerList(questionCompletion));

        return thisUrl + "/question/edit";
    }

    /**
     * 단지 커뮤니티 관리 > 설문조사 관리 > 설문조사 수정을 처리
     * 단지 관리자용 설문조사 수정을 처리
     * @param questionDetail
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "editQuestionAction", method = RequestMethod.POST)
    public String editQuestionAction(Question question, QuestionDetail questionDetail, Model model,
                                     BindingResult result, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String groupName = StringUtils.defaultIfEmpty(session.getUserGroupName(), "");

        String resultUrl = "redirect:/cm/community/question/view";
        String resultMsg = "";

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        session.setHouscplxCd(reqParamHouscplxCd);
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramQstTitle = xssUtil.replaceAll(StringUtils.defaultString(question.getQstTitle()));
        String paramQstStDt = xssUtil.replaceAll(StringUtils.defaultString(question.getQstStDt()));
        String paramQstEdDt = xssUtil.replaceAll(StringUtils.defaultString(question.getQstEdDt()));
        String paramQstTpCd = xssUtil.replaceAll(StringUtils.defaultString(question.getQstTpCd()));
        String paramQstItmQuestCont = xssUtil.replaceAll(StringUtils.defaultString(questionDetail.getQstItmQuestCont()));
        String paramIsQuestionDetailChange = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isQuestionDetailChange")));

        String qstTitle = StringUtils.defaultIfEmpty(paramQstTitle, "");
        String qstStDt = StringUtils.defaultIfEmpty(paramQstStDt, "");
        String qstEdDt = StringUtils.defaultIfEmpty(paramQstEdDt, "");
        String qstTpCd = StringUtils.defaultIfEmpty(paramQstTpCd, "");
        String qstItmQuestCont = StringUtils.defaultIfEmpty(paramQstItmQuestCont, "");
        String isQuestionDetailChange = StringUtils.defaultIfEmpty(paramIsQuestionDetailChange, "");
        question.setEditerId(paramSessionUserId);
        question.setCrerId(paramSessionUserId);

        if (StringUtils.isEmpty(qstTitle)) {
            result.addError(new FieldError("qstTitle", "qstTitle", MessageUtil.getMessage("notEmpty.qstTitle.qstTitle")));
        }

        if (StringUtils.isEmpty(qstStDt)) {
            result.addError(new FieldError("qstStDt", "qstStDt", MessageUtil.getMessage("notEmpty.qstStDt.qstStDt")));
        }

        if (StringUtils.isEmpty(qstEdDt)) {
            result.addError(new FieldError("qstEdDt", "qstEdDt", MessageUtil.getMessage("notEmpty.qstEdDt.qstEdDt")));
        }

        if (StringUtils.isEmpty(qstTpCd)) {
            result.addError(new FieldError("qstTpCd", "qstTpCd", MessageUtil.getMessage("notEmpty.qstTpCd.qstTpCd")));
        }

        if (StringUtils.isEmpty(qstItmQuestCont)) {
            result.addError(new FieldError("qstItmQuestCont", "qstItmQuestCont", MessageUtil.getMessage("notEmpty.qstItmQuestCont.qstItmQuestCont")));
        }

        if (isQuestionDetailChange.equals("Y")) {
            JSONArray newQstItmAnsrContJsonArray = new JSONArray(request.getParameter("newQstItmAnsrContJsonArray"));

            if (request.getParameter("newQstItmAnsrContJsonArray") == null) {
                result.addError(new FieldError("newQstItmAnsrContJsonArray", "newQstItmAnsrContJsonArray", MessageUtil.getMessage("notEmpty.newQstItmAnsrContJsonArray.newQstItmAnsrContJsonArray")));
                logger.debug("result.hasErrors()=>{}", result.getFieldError());

                model.addAttribute("editQuestionAction", null);
                model.addAttribute("resultMsg", resultMsg);
            }

            communityService.updateQuestionInfoForNewItem(question, questionDetail, newQstItmAnsrContJsonArray, adminId);
        } else {
            JSONArray qstItmAnsrContJsonArray = new JSONArray(request.getParameter("qstItmAnsrContList"));

            if (request.getParameter("qstItmAnsrContList") == null) {
                result.addError(new FieldError("qstItmAnsrContList", "qstItmAnsrContList", MessageUtil.getMessage("notEmpty.qstItmAnsrContList.qstItmAnsrContList")));
                logger.debug("result.hasErrors()=>{}", result.getFieldError());

                model.addAttribute("editQuestionAction", null);
                model.addAttribute("resultMsg", resultMsg);
            }

            communityService.updateQuestionInfo(question, questionDetail, qstItmAnsrContJsonArray);
        }

        model.addAttribute("qstNo", question.getQstNo());
        model.addAttribute("houscplxCd", houscplxCd);

        return resultUrl;
    }

    /**
     * 단지 커뮤니티 관리 > 설문조사 관리
     * 단지 관리자용 설문조사 상태를 변경(설문게시 or 강제설문종료)
     * @param question
     * @param model
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "editQuestionStsCdAction", method = RequestMethod.POST)
    public String editQuestionStsCdAction(Question question, Model model, BindingResult result, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String resultUrl = "redirect:/cm/community/question/list";
        String resultMsg = "";

        String paramQstStsCd = xssUtil.replaceAll(StringUtils.defaultString(question.getQstStsCd()));
        String qstStsCd = StringUtils.defaultIfEmpty(paramQstStsCd, "");

        if (StringUtils.isEmpty(qstStsCd)) {
            result.addError(new FieldError("qstStsCd", "qstStsCd", MessageUtil.getMessage("notEmpty.qstStsCd.qstStsCd")));
        }

        if (!qstStsCd.equals("2") && !qstStsCd.equals("4")) {
            result.addError(new FieldError("qstStsCd", "qstStsCd", MessageUtil.getMessage("notPublish.qstStsCd.qstStsCd")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        communityService.updateQuestionStsCd(question);

        return resultUrl;
    }

    /**
     * 단지 커뮤니티 관리 > 단지 설문조사 관리
     * 단지 관리자용 게시되지 않은 설문조사를 삭제 처리
     * @param question
     * @param model
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteQuestionAction", method = RequestMethod.POST)
    public String deleteQuestionAction(Question question, Model model, BindingResult result, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String resultUrl = "redirect:/cm/community/question/list";
        String resultMsg = "";

        Integer qstNo = null;
        qstNo = question.getQstNo();

        if (qstNo == null) {
            result.addError(new FieldError("qstNo", "qstNo", MessageUtil.getMessage("notEmpty.qstNo.qstNo")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        communityService.deleteQuestionInfo(question, adminId);

        return resultUrl;
    }

    /**
     * 단지 커뮤니티 관리 > 설문조사 관리 > 설문조사 목록 > 설문조사 상세 진행후 > 세대별 투표내역
     * 시스템 관리자용 설문조사 상세내용에 대한 세대별 투표내역을 조회
     * Ajax 방식
     * @param questionCompletion
     * @param request
     * @return
     */
    @RequestMapping(value = "question/completion/modal", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String questionCompletionModal(QuestionCompletion questionCompletion, HttpServletRequest request) {
        String result = "";

        Integer qstNo = null;
        qstNo = questionCompletion.getQstNo();

        if (qstNo != null) {
            List<QuestionCompletion> questionCompletionList = communityService.getQuestionCompletionList(questionCompletion);
            String questionCompletionJsonArray = JsonUtil.toJsonNotZero(questionCompletionList);

            result = questionCompletionJsonArray;
        } else if (qstNo == null) {
            JSONObject jsonResult = new JSONObject();
            JSONArray params = new JSONArray();
            JSONObject param = new JSONObject();

            param.put("id", "qstNo");
            param.put("msg", MessageUtil.getMessage("notEmpty.qstNo.qstNo"));
            params.put(param);

            jsonResult.put("status", false);
            jsonResult.put("params", params);

            return jsonResult.toString();
        }

        return result;
    }

    /**
     * 단지 커뮤니티 관리 > 생활불편신고 관리 > 생활불편신고 목록
     * 시스템 및 단지 관리자용 생활불편신고 목록을 조회
     * @param noticeItem
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "report/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String reportList(NoticeItem noticeItem, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");

        String paramDelYn = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("delYn")));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));

        String delYn = StringUtils.defaultIfEmpty(paramDelYn, "N");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        noticeItem.setDelYn(delYn);
        noticeItem.setHouscplxCd(houscplxCd);

        if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
            model.addAttribute("reportList", communityService.getReportListForAdmin(noticeItem));
        } else if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            model.addAttribute("reportList", communityService.getReportList(noticeItem));
        } else if (UserType.MULTI_COMPLEX.getGroupName().equals(groupName)) {
            noticeItem.setUserId(session.getUserId());
            model.addAttribute("reportList", communityService.getMultiReportListForAdmin(noticeItem));
        }

        logger.debug("title!!!!!!! " + noticeItem.getTitle());

        String noticeHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getHouscplxCd()));
        String title = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getTitle()));
        String notiDelYn = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getDelYn()));
        String startCrDt = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getStartCrDt()));
        String EndCrDt = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getEndCrDt()));
        String houscplxNm = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getHouscplxNm()));

        model.addAttribute("userId", session.getUserId());
        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(noticeHouscplxCd, ""));
        model.addAttribute("qstTitle", StringUtils.defaultIfEmpty(title, ""));
        model.addAttribute("delYn", StringUtils.defaultIfEmpty(notiDelYn,"N"));
        model.addAttribute("startCrDt", StringUtils.defaultIfEmpty(startCrDt, ""));
        model.addAttribute("endCrDt", StringUtils.defaultIfEmpty(EndCrDt, ""));
        model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(houscplxNm, ""));
        logger.info("****************************  생활 불편 신고 조회  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+groupName+", remoteIP : "+request.getRemoteHost()+", action : Report Life Search]");
        logger.info("***********************************************************************");
        return thisUrl + "/report/list";
    }

    /**
     * 단지 커뮤니티 관리 > 생활불편신고 관리 > 생활불편신고 목록 > 생활불편신고 상세
     * 시스템 및 단지 관리자용 생활불편신고 상세내용을 조회
     * @param noticeItem
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "report/view", method = {RequestMethod.GET, RequestMethod.POST})
    public String reportView(NoticeItem noticeItem, Model model, HttpServletRequest request, @RequestParam(value="hsholdIdRedirect", required=false) String hsholdIdRedirect, @RequestParam(value="blltNoRedirect", required=false) String blltNoRedirect) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));

        String paramBlltNo = xssUtil.replaceSpecialCharacter(StringUtils.defaultIfEmpty(request.getParameter("blltNo"), ""));

        if("".equals(paramBlltNo)){
            paramBlltNo = blltNoRedirect;
        }

        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);
        String blltNo = StringUtils.defaultIfEmpty(paramBlltNo, "");
        noticeItem.setHouscplxCd(houscplxCd);

        if (StringUtils.isNotEmpty(blltNo)) {
            Integer parseBlltNo = Integer.parseInt(blltNo);

            noticeItem.setBlltNo(parseBlltNo);
            noticeItem.setHouscplxCd(houscplxCd);
        }

        if(!"".equals(request.getParameter("hsholdId")) && request.getParameter("hsholdId") != null){
            noticeItem.setHsholdId(request.getParameter("hsholdId"));
            model.addAttribute("hsholdId", request.getParameter("hsholdId"));
        }

        if(!"".equals(hsholdIdRedirect) && hsholdIdRedirect != null){
            noticeItem.setHsholdId(hsholdIdRedirect);
        }


        if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
            model.addAttribute("reportDetail", communityService.getReportDetailForAdmin(noticeItem));
        } else if (UserType.COMPLEX.getGroupName().equals(groupName) || UserType.MULTI_COMPLEX.getGroupName().equals(groupName)) {
            model.addAttribute("reportDetail", communityService.getReportDetail(noticeItem));
        }

        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("blltNo", blltNo);
        model.addAttribute("reportDetailFile", communityService.getReportDetailFile(noticeItem));
        model.addAttribute("reportDetailAnswerFile", communityService.getReportDetailAnswerFile(noticeItem));
        logger.info("****************************  생활 불편 신고 상세 조회  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+groupName+"]");
        logger.info("***********************************************************************");
        return thisUrl + "/report/view";
    }

    @RequestMapping(value = "editReportCommentAction", method = RequestMethod.POST)
    public String editReportCommentAction(NoticeItem noticeItem, Model model, HttpServletRequest request, RedirectAttributes redirect) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String groupName = StringUtils.defaultIfEmpty(session.getUserGroupName(), "");

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramReportUserId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("reportUserId")));
        String paramIsProcessing = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isProcessing")));
        String paramComment = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("comment")));
        String paramIsAttachFile = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isAttachFile")));

        String reportUserId = StringUtils.defaultIfEmpty(paramReportUserId, "");
        String isProcessing = StringUtils.defaultIfEmpty(paramIsProcessing, "");
        String comment = StringUtils.defaultIfEmpty(paramComment, "");
        String isAttachFile = StringUtils.defaultIfEmpty(paramIsAttachFile, "");
        int blltNo = noticeItem.getBlltNo();
        String resultUrl = "redirect:/cm/community/report/view";

        noticeItem.setCrerId(adminId);
        noticeItem.setEditerId(adminId);

        if (isProcessing.equals("Y") && StringUtils.isEmpty(comment)) {
            communityService.updateReportBlltStsCd(noticeItem);
        } else if (isProcessing.equals("Y") && StringUtils.isNotEmpty(comment)) {
            if (isAttachFile.equals("Y")) {
                try {
                    List<UploadFileInfo> fileList = new ArrayList<UploadFileInfo>();

                    FileUploadUtil fileUploadUtil = new FileUploadUtil();

                    fileRootPath = xssUtil.replaceAll(StringUtils.defaultString(fileRootPath));
                    fileContentsComplexUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsComplexUploadPath));

                    String realPath = fileRootPath + fileContentsComplexUploadPath;

                    List<String> originalFileNameList = new ArrayList<>();
                    List<String> thumbnailFileNameList = new ArrayList<>();

                    MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
                    MultipartFile mpf = mpRequest.getFile("inputGroupFile01");

                    if (mpf.getSize() > 0) {
                        String realOriginalFileName = xssUtil.replaceTag(StringUtils.defaultString(mpf.getOriginalFilename()));

                        boolean isFile = ValidationUtil.validate(realOriginalFileName, ValidationUtil.FILE_PATTERN);
                        boolean isImageFile = ValidationUtil.validate(realOriginalFileName, ValidationUtil.IMAGE_PATTERN);

                        if (isFile || isImageFile) {
                            String originalFileName = fileUploadUtil.makeFileName(true, houscplxCd, "bllt", true, realOriginalFileName);
                            String thumbnailFileName = fileUploadUtil.makeFileName(true, houscplxCd, "bllt", false, "");

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

                    noticeItem.setCont(comment);
                    noticeItem.setBlltGrpNo(noticeItem.getBlltGrpNo());
                    communityService.insertReportAnswerInfoFile(noticeItem, originalFileNameList, thumbnailFileNameList, reportUserId);
                } catch (Exception e) {
                    logger.error("addNoticeAction Error: " + e.getCause());
                }
            } else {
                noticeItem.setCont(comment);
                noticeItem.setBlltGrpNo(noticeItem.getBlltGrpNo());

                communityService.insertReportAnswerInfo(noticeItem, reportUserId);
            }
        }

        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("blltNo", blltNo);
        logger.info("****************************  생활 불편 신고 수정 ****************************");
        logger.info("User Info [ID : "+adminId+", Group : "+groupName+", remoteIP : "+request.getRemoteHost()+", action : Report Life Modify]");
        logger.info("***********************************************************************");
        redirect.addAttribute("hsholdIdRedirect", request.getParameter("hsholdIdRedirect"));
        redirect.addAttribute("blltNoRedirect", request.getParameter("blltNo"));
        return resultUrl;
    }

    public void sendPush(int blltNo, String houscplxCd){
        String pushTargetNo = String.valueOf(blltNo);
        pushTargetNo = xssUtil.replaceAll(StringUtils.defaultString(pushTargetNo));

        String tpCd = "N";
        List<String> houscplxList = new ArrayList<>();
        houscplxList.add(houscplxCd);

        apiService.sendPush(pushTargetNo, tpCd, houscplxList);
    }

}