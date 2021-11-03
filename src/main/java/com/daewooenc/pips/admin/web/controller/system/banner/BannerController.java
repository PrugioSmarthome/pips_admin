package com.daewooenc.pips.admin.web.controller.system.banner;

import com.daewooenc.pips.admin.core.domain.UploadFileInfo;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.domain.configuration.CommonCode;
import com.daewooenc.pips.admin.core.util.FileUploadUtil;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.domain.dto.banner.Banner;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplxPtype;
import com.daewooenc.pips.admin.web.service.system.banner.BannerService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import com.mchange.lang.IntegerUtils;
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
import org.springframework.web.bind.annotation.RequestBody;
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
import java.io.IOException;
import java.util.*;

/**
 * 시스템 관리의 배너 관리 관련 Controller
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
@RequestMapping("/cm/system/banner")
public class BannerController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/banner";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private BannerService bannerService;

    private @Value("${file.rootPath}") String fileRootPath;
    private @Value("${file.contents.systemUploadPath}") String fileContentsSystemUploadPath;
    private @Value("${file.image.banr.fixedWidth}") int fileImageBannerFixedWidth;
    private @Value("${pips.serviceServer.banr.image.url}") String pipsServiceServerBannerImageUrl;

    /**
     * 시스템 관리 > 배너 관리 > 배너 목록
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String bannerList(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("bannerList", bannerService.getSystemBannerList());

        return thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 배너 관리 > 배너 이미지 정보 등록
     * 시스템 관리자용 배너 이미지 정보 등록을 처리
     * @param banner
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "addSystemBannerImageInfoAction", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String addSystemBannerImageInfoAction(Banner banner, Model model, HttpServletRequest request) throws IOException {
        JSONObject result = new JSONObject();

        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        List<UploadFileInfo> fileList = new ArrayList<UploadFileInfo>();

        FileUploadUtil fileUploadUtil = new FileUploadUtil();

        fileRootPath = xssUtil.replaceAll(StringUtils.defaultString(fileRootPath));
        fileContentsSystemUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsSystemUploadPath));

        String realPath = fileRootPath + fileContentsSystemUploadPath;

        MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;

        List<String> originalFileNameList = new ArrayList<>();
        List<String> thumbnailFileNameList = new ArrayList<>();

        int fileCount = mpRequest.getMultiFileMap().get("file[]").size();

        for (int i=0; i<fileCount; i++) {
            MultipartFile mpf = mpRequest.getMultiFileMap().get("file[]").get(i);
            String fileNameInfo = mpRequest.getParameterMap().get("_fileNames")[0];
            String[] fileNameInfoArray = fileNameInfo.split("/");
            String realOriginalFileName = xssUtil.replaceTag(StringUtils.defaultString(fileNameInfoArray[i]));
            String originalFileName = fileUploadUtil.makeFileName(false, "", "banr", true, realOriginalFileName);

            if (mpf.getSize() > 0) {
                UploadFileInfo originalUploadFileInfo = fileUploadUtil.uploadOriginalFile(mpf, realPath , originalFileName);

                fileList.add(originalUploadFileInfo);
                logger.info("\noriginalUploadFileInfo : {} ", originalUploadFileInfo.toString().replaceAll("\\]", "\n]").replaceAll("\\[", "\n[\n").replaceAll(",", ",\n"));

                File thumbnailFile = fileUploadUtil.multipartToFile(mpf);
                String thumbnailFileName = fileUploadUtil.makeFileName(false, "", "banr", false, "");

                String originalRealPath = realPath + "/" + originalFileName;

                BufferedImage image = ImageIO.read(new FileInputStream(originalRealPath));
                int originalWidth = image.getWidth();
                int originalHeight = image.getHeight();

                if (originalWidth > fileImageBannerFixedWidth) {
                    double compareWidth = image.getWidth();
                    double rate = fileImageBannerFixedWidth / compareWidth;

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

        Map<String, Object> bannerInfoMap = new HashMap<>();

        int fileListSize = originalFileNameList.size();

        if (fileListSize > 0) {
            List<Banner> list = new ArrayList<Banner>();

            int prevImgCnt = bannerService.getBannerCnt();

            for(int i=0; i<fileListSize; i++) {
                int ordNo = (i + prevImgCnt) + 1;

                String fileUrlCont = "";
                String blltOrdNo = mpRequest.getParameter("_blltOrdNo");

                pipsServiceServerBannerImageUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerBannerImageUrl));
                fileUrlCont = pipsServiceServerBannerImageUrl + "&file_nm=" + thumbnailFileNameList.get(i);

                Banner bannerInfo = new Banner();
                //bannerInfo.setBlltOrdNo(ordNo);
                bannerInfo.setFilePathCont(realPath);
                bannerInfo.setCrerId(adminId);

                String fileNameInfo = mpRequest.getParameterMap().get("_fileNames")[0];
                String[] fileNameInfoArray = fileNameInfo.split("/");
                String[] linkUrlInfoArray = null;

                if (mpRequest.getParameterMap().get("_linkUrlNames")[0] != null) {
                    String linkUrlInfo = mpRequest.getParameterMap().get("_linkUrlNames")[0];
                    linkUrlInfoArray = linkUrlInfo.split(",");
                }

                String linkUrl = "";

                if (linkUrlInfoArray.length > 0) {
                    linkUrl = xssUtil.replaceAll(StringUtils.defaultString(linkUrlInfoArray[i]));
                    linkUrl = StringUtils.defaultIfEmpty(linkUrl, "");
                }

                bannerInfo.setLinkUrlCont(linkUrl);
                bannerInfo.setFileNm(thumbnailFileNameList.get(i));
                bannerInfo.setOrgnlFileNm(originalFileNameList.get(i));
                bannerInfo.setFileUrlCont(fileUrlCont);
                bannerInfo.setBlltOrdNo(blltOrdNo);

                list.add(bannerInfo);
            }
            bannerInfoMap.put("list", list);
        }

        bannerService.insertSystemBannerInfo(bannerInfoMap);

        String danjiInfo = mpRequest.getParameterMap().get("_danjiArray")[0];
        String[] danjiInfoList = danjiInfo.split(",");

        List bannerKeyList =  bannerService.getSystemBannerKeyInfo(fileCount);
        List<Map<String, Object>> bannerKeyListMap = bannerKeyList;
        Map<String, Object> bannerDanjiInfoMap = new HashMap<>();

        bannerDanjiInfoMap.put("crerId", adminId);

        for(int i=0; i<bannerKeyList.size(); i++) {
            bannerDanjiInfoMap.put("lnk_svc_id",( (Banner) bannerKeyListMap.get(i)).getBlltNo());
            for (int j = 0; j < danjiInfoList.length; j++) {
                bannerDanjiInfoMap.put("houscplx_cd", danjiInfoList[j]);
                bannerService.insertSystemBannerDanjiInfo(bannerDanjiInfoMap);
            }
        }

        result.put("uploaded", "OK");

        return result.toString();
    }

    /**
     * 시스템 관리 > 배너 관리 > 배너 이미지 정보 수정
     * 시스템 관리자용 배너 이미지 정보 수정
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String bannerEdit(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        List<Banner> bannerList = bannerService.getSystemBannerEditList();

        for(int i=0; i<bannerList.size(); i++){
            int blltNo = bannerList.get(i).getBlltNo();

            int nonResident = bannerService.getSystemBannerNonResident(blltNo);

            bannerList.get(i).setNonResident(nonResident);
        }

        model.addAttribute("bannerList", bannerList);

        return thisUrl + "/edit";
    }

    /**
     * 시스템 관리 > 배너 관리 > 배너 이미지 정보 수정을 처리
     * 시스템 관리자용 배너 이미지 정보 수정을 처리
     * @param banner
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "editBannerImageInfoAction", method = RequestMethod.POST)
    public String editBannerImageInfoAction(Banner banner, Model model, HttpServletRequest request) throws IOException {
        SessionUser session = SessionUtil.getSessionUser(request);
        String resultMsg = "";
        String resultUrl = "redirect:/" + thisUrl + "/edit";

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramRequestType = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("requestType")));
        String paramPrevFileNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("prevFileNm")));
        String requestType = StringUtils.defaultIfEmpty(paramRequestType, "");
        String prevFileNm = StringUtils.defaultIfEmpty(paramPrevFileNm, "");

        if (requestType.equals("CHANGE") && StringUtils.isNotEmpty(prevFileNm)) {
            String paramIsFileChange = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isFileChange")));
            String isFileChange = StringUtils.defaultIfEmpty(paramIsFileChange, "N");

            if (isFileChange.equals("Y")) {
                List<UploadFileInfo> fileList = new ArrayList<UploadFileInfo>();

                fileRootPath = xssUtil.replaceAll(StringUtils.defaultString(fileRootPath));
                fileContentsSystemUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsSystemUploadPath));

                String realPath = fileRootPath + fileContentsSystemUploadPath;

                FileUploadUtil fileUploadUtil = new FileUploadUtil();

                MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
                Iterator<String> fileNames = mpRequest.getFileNames();

                List<String> originalFileNameList = new ArrayList<>();
                List<String> thumbnailFileNameList = new ArrayList<>();

                while (fileNames.hasNext()) {
                    MultipartFile mpf = mpRequest.getFile(fileNames.next());
                    String realOriginalFileName = xssUtil.replaceTag(StringUtils.defaultString(mpf.getOriginalFilename()));
                    String originalFileName = fileUploadUtil.makeFileName(false, "", "banr", true, realOriginalFileName);

                    if (mpf.getSize() > 0) {
                        UploadFileInfo originalUploadFileInfo = fileUploadUtil.uploadOriginalFile(mpf, realPath , originalFileName);

                        fileList.add(originalUploadFileInfo);
                        logger.info("\noriginalUploadFileInfo : {} ", originalUploadFileInfo.toString().replaceAll("\\]", "\n]").replaceAll("\\[", "\n[\n").replaceAll(",", ",\n"));

                        File thumbnailFile = fileUploadUtil.multipartToFile(mpf);
                        String thumbnailFileName = fileUploadUtil.makeFileName(false, "", "banr", false, "");

                        String originalRealPath = realPath + "/" + originalFileName;

                        BufferedImage image = ImageIO.read(new FileInputStream(originalRealPath));
                        int originalWidth = image.getWidth();
                        int originalHeight = image.getHeight();

                        if (originalWidth > fileImageBannerFixedWidth) {
                            double compareWidth = image.getWidth();
                            double rate = fileImageBannerFixedWidth / compareWidth;

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

                Map<String, Object> editBannerImageInfoMap = new HashMap<>();

                int fileListSize = originalFileNameList.size();

                if (fileListSize > 0) {
                    List<Banner> list = new ArrayList<Banner>();

                    for(int i=0; i<fileListSize; i++) {
                        String fileUrlCont = "";

                        fileUrlCont = pipsServiceServerBannerImageUrl + "&file_nm=" + thumbnailFileNameList.get(i);

                        String paramLinkUrl = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("linkUrlCont")));
                        String paramBlltNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("blltNo")));
                        String paramBlltOrdNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("editBlltOrdNo")));

                        String linkUrl = StringUtils.defaultIfEmpty(paramLinkUrl, "");
                        String blltNo = StringUtils.defaultIfEmpty(paramBlltNo, "");
                        String blltOrdNo = StringUtils.defaultIfEmpty(paramBlltOrdNo, "");
                        Integer parseBlltNo = null;
                        parseBlltNo = Integer.parseInt(blltNo);

                        Banner bannerInfo = new Banner();
                        bannerInfo.setFilePathCont(realPath);
                        bannerInfo.setFileNm(thumbnailFileNameList.get(i));
                        bannerInfo.setOrgnlFileNm(originalFileNameList.get(i));
                        bannerInfo.setFileUrlCont(fileUrlCont);
                        bannerInfo.setLinkUrlCont(linkUrl);
                        bannerInfo.setBlltNo(parseBlltNo);
                        bannerInfo.setEditerId(adminId);
                        bannerInfo.setBlltOrdNo(blltOrdNo);

                        list.add(bannerInfo);

                    }
                    editBannerImageInfoMap.put("list", list);

                }
                bannerService.updateSystemBannerImageInfo(editBannerImageInfoMap);
            } else if (isFileChange.equals("N")) {
                String paramLinkUrl = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("linkUrlCont")));
                String paramBlltNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("blltNo")));
                String paramBlltOrdNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("editBlltOrdNo")));

                String linkUrl = StringUtils.defaultIfEmpty(paramLinkUrl, "");
                String blltNo = StringUtils.defaultIfEmpty(paramBlltNo, "");
                String blltOrdNo = StringUtils.defaultIfEmpty(paramBlltOrdNo, "");
                Integer parseBlltNo = null;
                parseBlltNo = Integer.parseInt(blltNo);

                Banner bannerInfo = new Banner();

                bannerInfo.setEditerId(adminId);
                bannerInfo.setLinkUrlCont(linkUrl);
                bannerInfo.setBlltNo(parseBlltNo);
                bannerInfo.setBlltOrdNo(blltOrdNo);

                bannerService.updateSystemBannerDataInfo(bannerInfo);
            }
        } else if (requestType.equals("DELETE")) {
            String removeFileList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("removeFileList")));
            removeFileList = StringUtils.defaultIfEmpty(removeFileList, "");

            JSONArray removeFileJsonArray = new JSONArray(removeFileList);
            Map<String, Object> removeFileMap = new HashMap<String, Object>();
            String bannerKey = "";
            int length = removeFileJsonArray.length();

            if (length > 0) {
                List<Banner> list = new ArrayList<Banner>();
                String bannerKeyInfo = "";

                for(int i=0; i<length; i++) {
                    Banner bannerInfo = new Banner();
                    String paramFileNm = xssUtil.replaceAll(StringUtils.defaultString(removeFileJsonArray.getJSONObject(i).getString("fileNm")));
                    String paramBlltNo = xssUtil.replaceAll(StringUtils.defaultString(removeFileJsonArray.getJSONObject(i).getString("blltNo")));
                    String fileNm = StringUtils.defaultIfEmpty(paramFileNm, "");
                    String blltNo = StringUtils.defaultIfEmpty(paramBlltNo, "");

                    Integer parseBlltNo = null;
                    parseBlltNo = Integer.parseInt(blltNo);

                    bannerInfo.setEditerId(adminId);
                    bannerInfo.setFileNm(fileNm);
                    bannerInfo.setBlltNo(parseBlltNo);

                    list.add(bannerInfo);
                    bannerKeyInfo = blltNo;
                }
                removeFileMap.put("list", list);
                bannerKey = bannerKeyInfo;
            }

            bannerService.updateSystemBannerImageStatus(removeFileMap);
            bannerService.deleteSystemBannerDanjiInfo(bannerKey);
        }

        String danjiInfo = request.getParameter("danjiArray");
        if(danjiInfo != null && !"".equals(danjiInfo)) {
            String[] danjiInfoList = danjiInfo.split(",");

            if (!"".equals(danjiInfo)) {
                String systemBannerKeyInfo = request.getParameter("blltNo");

                Map<String, Object> bannerDanjiInfoMap = new HashMap<>();

                bannerDanjiInfoMap.put("crerId", adminId);
                bannerDanjiInfoMap.put("lnk_svc_id", systemBannerKeyInfo);

                bannerService.deleteSystemBannerDanjiInfo(systemBannerKeyInfo);

                for (int i = 0; i < danjiInfoList.length; i++) {
                    bannerDanjiInfoMap.put("houscplx_cd", danjiInfoList[i]);
                    bannerService.insertSystemBannerDanjiInfo(bannerDanjiInfoMap);
                }
            }
        }

        String noDanjiList = request.getParameter("noDanjiList");
        if("Y".equals(noDanjiList)){
            String systemBannerKeyInfo = request.getParameter("blltNo");
            bannerService.deleteSystemBannerDanjiInfo(systemBannerKeyInfo);

            Map<String, Object> bannerDanjiInfoMap = new HashMap<>();
            bannerDanjiInfoMap.put("crerId", adminId);
            bannerDanjiInfoMap.put("lnk_svc_id", systemBannerKeyInfo);
            bannerService.insertSystemBannerNoResident(bannerDanjiInfoMap);
        }

        String nonResidentVal = request.getParameter("nonResidentVal");
        if("Y".equals(nonResidentVal)){
            String lnkSvcId = request.getParameter("blltNo");
            bannerService.deleteSystemBannerNoResident(lnkSvcId);

            Map<String, Object> bannerDanjiInfoMap = new HashMap<>();
            bannerDanjiInfoMap.put("crerId", adminId);
            bannerDanjiInfoMap.put("lnk_svc_id", lnkSvcId);
            bannerService.insertSystemBannerNoResident(bannerDanjiInfoMap);
        }else if("N".equals(nonResidentVal)){
            String lnkSvcId = request.getParameter("blltNo");
            bannerService.deleteSystemBannerNoResident(lnkSvcId);
        }

        return resultUrl;
    }

    /**
     * 시스템 관리 > 배너 관리 > 배너 이미지 순서 정보 수정
     * 시스템 관리자용 배너 이미지 순서 정보 수정
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "editBannerImageOrderInfoAction", method = RequestMethod.POST)
    public String editBannerImageOrderInfoAction(Model model, HttpServletRequest request) throws IOException {
        SessionUser session = SessionUtil.getSessionUser(request);

        String adminId =  StringUtils.defaultIfEmpty(session.getUserId(), "");
        String resultUrl = "redirect:/" + thisUrl + "/edit";

        String orderFileList = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("orderFileList")));
        orderFileList = StringUtils.defaultIfEmpty(orderFileList, "");

        JSONArray orderFileJsonArray = new JSONArray(orderFileList);
        Map<String, Object> orderFileMap = new HashMap<String, Object>();
        int orderFileLength = orderFileJsonArray.length();

        if (orderFileLength > 0) {
            List<Banner> list = new ArrayList<Banner>();

            for(int i=0; i<orderFileLength; i++) {
                Banner orderBannerInfo = new Banner();

                String paramFileNm = xssUtil.replaceAll(StringUtils.defaultString(orderFileJsonArray.getJSONObject(i).getString("fileNm")));
                String paramBlltNo = xssUtil.replaceAll(StringUtils.defaultString(orderFileJsonArray.getJSONObject(i).getString("blltNo")));
                String paramOrdNo = xssUtil.replaceAll(StringUtils.defaultString(orderFileJsonArray.getJSONObject(i).getString("ordNo")));
                paramFileNm = StringUtils.defaultIfEmpty(paramFileNm, "");
                paramBlltNo = StringUtils.defaultIfEmpty(paramBlltNo, "");
                paramOrdNo = StringUtils.defaultIfEmpty(paramOrdNo, "");

                String fileNm = StringUtils.defaultIfEmpty(paramFileNm, "");
                String blltNo = StringUtils.defaultIfEmpty(paramBlltNo, "");
                String ordNo = StringUtils.defaultIfEmpty(paramOrdNo, "");

                Integer parseBlltNo = null;
                parseBlltNo = Integer.parseInt(blltNo);

                Integer parseOrdNo = null;
                parseOrdNo = Integer.parseInt(ordNo);

                orderBannerInfo.setEditerId(adminId);
                orderBannerInfo.setFileNm(fileNm);
                orderBannerInfo.setBlltNo(parseBlltNo);
                //orderBannerInfo.setBlltOrdNo(parseOrdNo);

                list.add(orderBannerInfo);
            }
            orderFileMap.put("list", list);
        }

        bannerService.updateSystemBannerImageOrder(orderFileMap);

        return resultUrl;
    }

    /**
     * 시스템 관리 > 배너 관리 > 배너 등록 > 정렬순서 중복 체크
     * 시스템 관리자용 배너 정렬순서 중복 체크
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "checkBannerBlltOrdNo", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkBannerBlltOrdNo(Model model, HttpServletRequest request) throws IOException {
        String blltOrdNo = request.getParameter("blltOrdNo");

        boolean check = bannerService.checkBannerBlltOrdNo(blltOrdNo);

        return check;
    }

}