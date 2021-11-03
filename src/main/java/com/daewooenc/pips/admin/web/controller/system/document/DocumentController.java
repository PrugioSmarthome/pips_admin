package com.daewooenc.pips.admin.web.controller.system.document;

import com.daewooenc.pips.admin.core.domain.UploadFileInfo;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.FileUploadUtil;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.domain.dto.community.NoticeItem;
import com.daewooenc.pips.admin.web.domain.dto.document.Document;
import com.daewooenc.pips.admin.web.domain.dto.externalsvcinfo.ExternalServiceInfo;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLink;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLinkDetail;
import com.daewooenc.pips.admin.web.service.document.DocumentService;
import com.daewooenc.pips.admin.web.service.system.banner.BannerService;
import com.daewooenc.pips.admin.web.util.ValidationUtil;
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
 * 시스템 관리의 문서 관리 관련 Controller
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
@RequestMapping("/cm/system/document")
public class DocumentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/document";

    private @Value("${file.rootPath}") String fileRootPath;
    private @Value("${file.contents.systemUploadPath}") String fileContentsSystemUploadPath;
    private @Value("${pips.serviceServer.doc.file.url}") String pipsServiceServerDocFileUrl;

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private DocumentService documentService;

    /**
     * 시스템 관리 > 문서 관리 > 문서 관리 목록
     * 시스템 관리자용 문서 관리 목록을 조회
     * @param document
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String documentList(Document document, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("documentList", documentService.getDocumentList(document));

        String startCrDt = xssUtil.replaceAll(StringUtils.defaultString(document.getStartCrDt()));
        String endCrDt = xssUtil.replaceAll(StringUtils.defaultString(document.getEndCrDt()));
        String lnkAtchFileGrpTpCd = xssUtil.replaceAll(StringUtils.defaultString(document.getLnkAtchFileGrpTpCd()));
        String lnkAtchFileTpCd = xssUtil.replaceAll(StringUtils.defaultString(document.getLnkAtchFileTpCd()));
        String houscplxCd = xssUtil.replaceAll(StringUtils.defaultString(document.getHouscplxCd()));

        model.addAttribute("startCrDt", StringUtils.defaultIfEmpty(startCrDt, ""));
        model.addAttribute("endCrDt", StringUtils.defaultIfEmpty(endCrDt, ""));
        model.addAttribute("lnkAtchFileGrpTpCd", StringUtils.defaultIfEmpty(lnkAtchFileGrpTpCd, ""));
        model.addAttribute("lnkAtchFileTpCd", StringUtils.defaultIfEmpty(lnkAtchFileTpCd, ""));
        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(houscplxCd, ""));

        return thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 문서 관리 > 문서 관리 상세
     * 시스템 관리자용 문서관리 상세정보 조회
     * @param document
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "view", method = {RequestMethod.GET, RequestMethod.POST})
    public String view(Document document, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        Integer lnkAtchFileId = null;
        lnkAtchFileId = document.getLnkAtchFileId();

        if (lnkAtchFileId == null) {
            String requestLnkAtchFileId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("lnkAtchFileId")));

            requestLnkAtchFileId = StringUtils.defaultIfEmpty(requestLnkAtchFileId, "");

            lnkAtchFileId = Integer.parseInt(requestLnkAtchFileId);
            document.setLnkAtchFileId(lnkAtchFileId);
        }

        model.addAttribute("documentDetail", documentService.getDocumentDetail(document));

        return thisUrl + "/view";
    }

    /**
     * 시스템 관리 > 문서 관리 > 문서 관리 등록
     * 시스템 관리자용 문서관리 정보 등록
     * @param document
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public String add(Document document, Model model, HttpServletRequest request) {

        return thisUrl + "/add";
    }

    /**
     * 시스템 관리 > 문서 관리 > 문서 등록을 처리
     * 시스템 관리자용 문서 등록을 처리
     * @param document
     * @param model
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "addDocumentAction", method = RequestMethod.POST)
    public String addDocumentAction(Document document, Model model, HttpServletRequest request, BindingResult result) {
        String resultUrl = thisUrl;
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String lnkAtchFileGrpTpCd = xssUtil.replaceAll(StringUtils.defaultString(document.getLnkAtchFileGrpTpCd()));
        String lnkAtchFileTpCd = xssUtil.replaceAll(StringUtils.defaultString(document.getLnkAtchFileTpCd()));
        String useYn = xssUtil.replaceAll(StringUtils.defaultString(document.getUseYn()));

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        lnkAtchFileGrpTpCd = StringUtils.defaultIfEmpty(lnkAtchFileGrpTpCd, "");
        lnkAtchFileTpCd = StringUtils.defaultIfEmpty(lnkAtchFileTpCd, "");
        useYn = StringUtils.defaultIfEmpty(useYn, "");

        houscplxCd = StringUtils.defaultIfEmpty(houscplxCd, "");

        if(StringUtils.isEmpty(lnkAtchFileGrpTpCd)) {
            result.addError(new FieldError("lnkAtchFileGrpTpCd", "lnkAtchFileGrpTpCd", MessageUtil.getMessage("notEmpty.lnkAtchFileGrpTpCd.lnkAtchFileGrpTpCd")));
        }

        if(StringUtils.isEmpty(lnkAtchFileTpCd)) {
            result.addError(new FieldError("lnkAtchFileTpCd", "lnkAtchFileTpCd", MessageUtil.getMessage("notEmpty.lnkAtchFileTpCd.lnkAtchFileTpCd")));
        }

        if(StringUtils.isEmpty(useYn)) {
            result.addError(new FieldError("useYn", "useYn", MessageUtil.getMessage("notEmpty.useYn.useYn")));
        }

        String householdList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("householdList")));
        householdList = StringUtils.defaultIfEmpty(householdList, "");

        if (StringUtils.isNotEmpty(householdList)) {
            JSONArray householdJsonArray = new JSONArray(householdList);

            List houscplxList = new ArrayList();
            for (int i = 0; i < householdJsonArray.length(); i++) {
                String householdInfo = householdJsonArray.getString(i);
                String targetHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(householdInfo.split("_")[0]));

                houscplxCd = targetHouscplxCd;
                document.setHouscplxCd(targetHouscplxCd);

            }
            document.setHouscplxList(houscplxList);
        }



        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("addDocumentAction", null);
            model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.error"));

            resultUrl = resultUrl + "/add";
        }

        try {
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

                boolean isFile = ValidationUtil.validate(realOriginalFileName, ValidationUtil.DOC_PATTERN);

                if (isFile) {
                    String originalFileName = fileUploadUtil.makeFileName(false, "", "file", true, realOriginalFileName);
                    String thumbnailFileName = fileUploadUtil.makeFileName(false, "", "file", false, "");

                    fileUploadUtil.uploadOriginalFile(mpf, realPath , thumbnailFileName);

                    originalFileNameList.add(originalFileName);
                    thumbnailFileNameList.add(thumbnailFileName);
                }
            }

            String fileUrl =  xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerDocFileUrl)) + "&file_nm=" + thumbnailFileNameList.get(0);

            document.setOrgnlFileNm(originalFileNameList.get(0));
            document.setFileNm(thumbnailFileNameList.get(0));
            document.setFilePathCont(realPath);
            document.setFileUrl(fileUrl);
            document.setUseYn(useYn);
            document.setCrerId(adminId);
            document.setEditerId(adminId);

//            document.setHouscplxCd(houscplxCd);

//            documentService.insertDocument(document);
            documentService.insertDocument(document, houscplxCd);
        } catch (Exception e) {
            logger.error("addDocumentAction Error: " + e.getCause());
        }

        return "redirect:/" + resultUrl + "/list";
    }

    /**
     * 시스템 관리 > 문서 관리 > 문서 관리 수정
     * 시스템 관리자용 문서관리 정보 수정
     * @param document
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String edit(Document document, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        Integer lnkAtchFileId = null;
        lnkAtchFileId = document.getLnkAtchFileId();

        if (lnkAtchFileId == null) {
            String requestLnkAtchFileId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("lnkAtchFileId")));

            requestLnkAtchFileId = StringUtils.defaultIfEmpty(requestLnkAtchFileId, "");

            lnkAtchFileId = Integer.parseInt(requestLnkAtchFileId);
            document.setLnkAtchFileId(lnkAtchFileId);
        }

        model.addAttribute("documentDetail", documentService.getDocumentDetail(document));

        return thisUrl + "/edit";
    }

    /**
     * 시스템 관리 > 문서 관리 > 문서 수정을 처리
     * 시스템 관리자용 문서 수정을 처리
     * @param document
     * @param model
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "editDocumentAction", method = RequestMethod.POST)
    public String editDocumentAction(Document document, Model model, HttpServletRequest request, BindingResult result) {
        String resultUrl = thisUrl;
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        document.setCrerId(adminId);

        String lnkAtchFileGrpTpCd = xssUtil.replaceAll(StringUtils.defaultString(document.getLnkAtchFileGrpTpCd()));
        String lnkAtchFileTpCd = xssUtil.replaceAll(StringUtils.defaultString(document.getLnkAtchFileTpCd()));
        String useYn = xssUtil.replaceAll(StringUtils.defaultString(document.getUseYn()));

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        lnkAtchFileGrpTpCd = StringUtils.defaultIfEmpty(lnkAtchFileGrpTpCd, "");
        lnkAtchFileTpCd = StringUtils.defaultIfEmpty(lnkAtchFileTpCd, "");
        useYn = StringUtils.defaultIfEmpty(useYn, "");

        document.setHouscplxCd(houscplxCd);



        if(StringUtils.isEmpty(lnkAtchFileGrpTpCd)) {
            result.addError(new FieldError("lnkAtchFileGrpTpCd", "lnkAtchFileGrpTpCd", MessageUtil.getMessage("notEmpty.lnkAtchFileGrpTpCd.lnkAtchFileGrpTpCd")));
        }

        if(StringUtils.isEmpty(lnkAtchFileTpCd)) {
            result.addError(new FieldError("lnkAtchFileTpCd", "lnkAtchFileTpCd", MessageUtil.getMessage("notEmpty.lnkAtchFileTpCd.lnkAtchFileTpCd")));
        }

        if(StringUtils.isEmpty(useYn)) {
            result.addError(new FieldError("useYn", "useYn", MessageUtil.getMessage("notEmpty.useYn.useYn")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());

            model.addAttribute("editDocumentAction", null);
            model.addAttribute("resultMsg", MessageUtil.getMessage("msg.common.error"));

            return resultUrl + "/edit";
        }

        String householdList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("householdList")));
        householdList = StringUtils.defaultIfEmpty(householdList, "");

        if (StringUtils.isNotEmpty(householdList)) {
            JSONArray householdJsonArray = new JSONArray(householdList);

            List houscplxList = new ArrayList();
            for (int i = 0; i < householdJsonArray.length(); i++) {
                String householdInfo = householdJsonArray.getString(i);
                String targetHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(householdInfo.split("_")[0]));

                houscplxCd = targetHouscplxCd;
                document.setHouscplxCd(targetHouscplxCd);
            }
            document.setHouscplxList(houscplxList);
        }

        String isNewFile = xssUtil.replaceSpecialCharacter(StringUtils.defaultString(request.getParameter("isNewFile")));
        isNewFile = StringUtils.defaultIfEmpty(isNewFile, "N");

        document.setUseYn(useYn);
        document.setEditerId(adminId);
        document.setIsNewFile(isNewFile);
        document.setHouscplxCd(houscplxCd);

        if (isNewFile.equals("Y")) {
            try {
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

                    boolean isFile = ValidationUtil.validate(realOriginalFileName, ValidationUtil.DOC_PATTERN);

                    if (isFile) {
                        String originalFileName = fileUploadUtil.makeFileName(false, "", "file", true, realOriginalFileName);
                        String thumbnailFileName = fileUploadUtil.makeFileName(false, "", "file", false, "");

                        fileUploadUtil.uploadOriginalFile(mpf, realPath , thumbnailFileName);

                        originalFileNameList.add(originalFileName);
                        thumbnailFileNameList.add(thumbnailFileName);
                    }
                }

                String fileUrl =  xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerDocFileUrl)) + "&file_nm=" + thumbnailFileNameList.get(0);

                document.setOrgnlFileNm(originalFileNameList.get(0));
                document.setFileNm(thumbnailFileNameList.get(0));
                document.setFilePathCont(realPath);
                document.setFileUrl(fileUrl);

                documentService.updateDocument(document, houscplxCd);
            } catch (Exception e) {
                logger.error("editDocumentAction Error: " + e.getCause());
            }
        } else if (isNewFile.equals("N")) {
            documentService.updateDocument(document, houscplxCd);
        }

        model.addAttribute("lnkAtchFileId", document.getLnkAtchFileId());

        return "redirect:/cm/system/document/view";
    }

    /**
     * 시스템 관리 > 문서 관리 > 문서 삭제 처리
     * @param model
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "deleteDocumentAction", method = RequestMethod.POST)
    public String deleteDocumentAction(Document document, Model model, HttpServletRequest request, BindingResult result) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        document.setEditerId(adminId);

        documentService.deleteDocument(document);

        return "redirect:/" + thisUrl + "/list";
    }
}