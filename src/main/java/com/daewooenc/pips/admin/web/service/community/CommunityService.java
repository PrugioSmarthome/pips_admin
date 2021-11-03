package com.daewooenc.pips.admin.web.service.community;

import com.daewooenc.pips.admin.web.dao.community.CommunityMapper;
import com.daewooenc.pips.admin.web.domain.dto.community.*;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.service.api.ApiService;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 공지사항, 설문조사 및 생활불편신고 관련 Service
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-07-31      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-07-31
 **/
@Service
public class CommunityService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApiService apiService;

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private XSSUtil xssUtil;

    private @Value("${file.rootPath}") String fileRootPath;
    private @Value("${file.contents.complexUploadPath}") String fileContentsComplexUploadPath;
    private @Value("${file.contents.systemUploadPath}") String fileContentsSystemUploadPath;
    private @Value("${pips.serviceServer.bllt.file.url}") String pipsServiceServerBlltFileUrl;

    private @Value("${pips.encrypt.key}") String pipsEncryptKey;

    /**
     * 시스템 관리자용 단지 공지사항 목록을 조회
     * @param noticeItem
     * @return
     */
    public List<NoticeItem> getNoticeListForAdmin(NoticeItem noticeItem) {
        List<NoticeItem> noticeItemList = communityMapper.selectNoticeListForAdmin(noticeItem);

        return noticeItemList;
    }

    /**
     * 멀티 단지 관리자용 단지 공지사항 목록을 조회
     * @param noticeItem
     * @return
     */
    public List<NoticeItem> getMultiNoticeListForAdmin(NoticeItem noticeItem) {
        List<NoticeItem> noticeItemList = communityMapper.selectMultiNoticeListForAdmin(noticeItem);

        return noticeItemList;
    }

    /**
     * 시스템 관리자용 단지 공지사항 상세내용을 조회
     * @param noticeItem
     * @return
     */
    public NoticeItem getNoticeDetailForAdmin(NoticeItem noticeItem) {
        NoticeItem noticeItemDetail = communityMapper.selectNoticeDetailForAdmin(noticeItem);

        return noticeItemDetail;
    }

    /**
     * 시스템 및 단지 관리자용 단지 공지사항 상세내용의 첨부파일 목록을 조회
     * @param noticeItem
     * @return
     */
    public List<NoticeFile> getNoticeDetailFile(NoticeItem noticeItem) {
        List<NoticeFile> noticeFileList = communityMapper.selectNoticeDetailFile(noticeItem);

        return noticeFileList;
    }

    /**
     * 단지 관리자용 단지 공지사항 목록을 조회
     * @param noticeItem
     * @return
     */
    public List<NoticeItem> getNoticeList(NoticeItem noticeItem) {
        List<NoticeItem> noticeItemList = communityMapper.selectNoticeList(noticeItem);

        return noticeItemList;
    }

    /**
     * 단지 관리자용 단지 공지사항 상세내용을 조회
     * @param noticeItem
     * @return
     */
    public NoticeItem getNoticeDetail(NoticeItem noticeItem) {
        NoticeItem noticeItemDetail = communityMapper.selectNoticeDetail(noticeItem);

        return noticeItemDetail;
    }

    /**
     * 시스템 관리자용 설문조사 목록을 조회
     * @param question
     * @return
     */
    public List<Question> getQuestionListForAdmin(Question question) {
        List<Question> questionList = communityMapper.selectQuestionListForAdmin(question);

        return questionList;
    }

    /**
     * 시스템 관리자용 설문조사 상세내용을 조회
     * @param questionDetail
     * @return
     */
    public Question getQuestionDetailForAdmin(QuestionDetail questionDetail) {
        Question questionDetailInfo = communityMapper.selectQuestionDetailForAdmin(questionDetail);

        return questionDetailInfo;
    }

    /**
     * 시스템 및 단지 관리자용 상세내용 항목을 조회
     * @param questionDetail
     * @return
     */
    public List<QuestionDetail> getQuestionItemList(QuestionDetail questionDetail) {
        List<QuestionDetail> questionItemList = communityMapper.selectQuestionItemList(questionDetail);

        return questionItemList;
    }

    /**
     * 시스템 및 단지 관리자용 상세항목의 기타내용을 조회
     * @param questionCompletion
     * @return
     */
    public List<QuestionCompletion> getQuestionEtcAnswerList(QuestionCompletion questionCompletion) {
        List<QuestionCompletion> questionEtcAnswerList = communityMapper.selectQuestionEtcAnswerList(questionCompletion);

        return questionEtcAnswerList;
    }

    /**
     * 시스템 관리자용 세대별 투표내역을 조회
     * @param questionCompletion
     * @return
     */
    public List<QuestionCompletion> getQuestionCompletionList(QuestionCompletion questionCompletion) {
        List<QuestionCompletion> questionCompletionList = communityMapper.selectQuestionCompletionList(questionCompletion);

        return questionCompletionList;
    }

    /**
     * 단지 관리자용 설문목록을 조회
     * @param question
     * @return
     */
    public List<Question> getQuestionList(Question question) {
        List<Question> questionList = communityMapper.selectQuestionList(question);

        return questionList;
    }

    /**
     * 멀티 단지 관리자용 설문목록을 조회
     * @param question
     * @return
     */
    public List<Question> getMultiQuestionList(Question question) {
        List<Question> questionList = communityMapper.selectMultiQuestionList(question);

        return questionList;
    }

    /**
     * 단지 관리자용 설문 상세내용을 조회
     * @param questionDetail
     * @return
     */
    public Question getQuestionDetail(QuestionDetail questionDetail) {
        Question questionDetailInfo = communityMapper.selectQuestionDetail(questionDetail);

        return questionDetailInfo;
    }

    /**
     * 시스템 관리자용 생활불편신고 목록을 조회
     * @param noticeItem
     * @return
     */
    public List<NoticeItem> getReportListForAdmin(NoticeItem noticeItem) {
        List<NoticeItem> ReportList = communityMapper.selectReportListForAdmin(noticeItem);

        return ReportList;
    }

    /**
     * 멀티 단지 관리자용 생활불편신고 목록을 조회
     * @param noticeItem
     * @return
     */
    public List<NoticeItem> getMultiReportListForAdmin(NoticeItem noticeItem) {
        List<NoticeItem> ReportList = communityMapper.selectMultiReportListForAdmin(noticeItem);

        return ReportList;
    }

    /**
     * 시스템 관리자용 생활불편신고 상세내용을 조회
     * @param noticeItem
     * @return
     */
    public NoticeItem getReportDetailForAdmin(NoticeItem noticeItem) {
        int blltNo = noticeItem.getBlltNo();
        String houscplxCd = noticeItem.getHouscplxCd();
        String hsholdId = noticeItem.getHsholdId();
        pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

        NoticeItem noticeItemInfo = communityMapper.selectReportDetailForAdmin(blltNo, houscplxCd, pipsEncryptKey, hsholdId);

        return noticeItemInfo;
    }

    /**
     * 시스템 및 단지 관리자용 상세항목의 신고자 파일목록을 조회
     * @param noticeItem
     * @return
     */
    public List<NoticeFile> getReportDetailFile(NoticeItem noticeItem) {
        List<NoticeFile> ReportDetailFile = communityMapper.selectReportDetailFile(noticeItem);

        return ReportDetailFile;
    }

    /**
     * 시스템 및 단지 관리자용 상세항목의 처리자 파일목록을 조회
     * @param noticeItem
     * @return
     */
    public List<NoticeFile> getReportDetailAnswerFile(NoticeItem noticeItem) {
        List<NoticeFile> ReportDetailFile = communityMapper.selectReportDetailAnswerFile(noticeItem);

        return ReportDetailFile;
    }

    /**
     * 단지 관리자용 생활불편신고 목록을 조회
     * @param noticeItem
     * @return
     */
    public List<NoticeItem> getReportList(NoticeItem noticeItem) {
        List<NoticeItem> ReportList = communityMapper.selectReportList(noticeItem);

        return ReportList;
    }

    /**
     * 단지 관리자용 생활불편신고 상세내용을 조회
     * @param noticeItem
     * @return
     */
    public NoticeItem getReportDetail(NoticeItem noticeItem) {
        int blltNo = noticeItem.getBlltNo();
        String houscplxCd = noticeItem.getHouscplxCd();
        String hsholdId = noticeItem.getHsholdId();
        pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

        NoticeItem noticeItemInfo = communityMapper.selectReportDetail(blltNo, houscplxCd, pipsEncryptKey, hsholdId);

        return noticeItemInfo;
    }

    /**
     * 단지 관리자용 공지사항 정보를 수정
     * @param noticeItem
     * @param originalFileNameList
     * @param thumbnailFileNameList
     */
    public void updateNoticeInfo(NoticeItem noticeItem, List<String> originalFileNameList, List<String> thumbnailFileNameList,
                                 String isAttachFileRemove) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            noticeItem.setHmnetNotiCont("");
            communityMapper.updateNotice(noticeItem);

            NoticeFile noticeFile = new NoticeFile();
            noticeFile.setBlltNo(noticeItem.getBlltNo());
            noticeFile.setEditerId(noticeItem.getEditerId());

            if (isAttachFileRemove.equals("Y")) {
                communityMapper.deleteNoticeFile(noticeFile);
            } else if (isAttachFileRemove.equals("N")) {
                noticeFile.setDelYn("N");
                noticeFile.setBlltOrdNo(String.valueOf(originalFileNameList.size()));
                noticeFile.setFileNm(thumbnailFileNameList.get(0));
                noticeFile.setOrgnlFileNm(originalFileNameList.get(0));

                fileRootPath = xssUtil.replaceAll(StringUtils.defaultString(fileRootPath));
                fileContentsComplexUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsComplexUploadPath));
                pipsServiceServerBlltFileUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerBlltFileUrl));

                String realPath = fileRootPath + fileContentsComplexUploadPath;
                String fileUrl = pipsServiceServerBlltFileUrl + "&file_nm=" + thumbnailFileNameList.get(0);

                noticeFile.setFilePathCont(realPath);
                noticeFile.setFileUrl(fileUrl);

                communityMapper.updateNoticeFile(noticeFile);
            }
        } catch (Exception e) {
            logger.debug("updateNoticeInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 단지 관리자용 공지사항을 등록(첨부파일이 있는 경우)
     * @param noticeItem
     * @param originalFileNameList
     * @param thumbnailFileNameList
     */
    public void insertNoticeInfo(NoticeItem noticeItem, List<String> originalFileNameList, List<String> thumbnailFileNameList, String houscplxCd) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {

            if(houscplxCd.contains(",")) {
                String[] danjiInfoList = houscplxCd.split(",");
                for (int i = 0; i < danjiInfoList.length; i++) {

                    noticeItem.setHmnetNotiCont("");
                    communityMapper.insertNotice(noticeItem);

                    int blltNo = noticeItem.getBlltNo();
                    String adminId = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getCrerId()));

                    if (originalFileNameList.size() > 0) {
                        NoticeFile noticeFile = new NoticeFile();

                        noticeFile.setCrerId(adminId);
                        noticeFile.setEditerId(adminId);
                        noticeFile.setBlltNo(blltNo);
                        noticeFile.setBlltOrdNo(String.valueOf(originalFileNameList.size()));
                        noticeFile.setFileNm(thumbnailFileNameList.get(0));
                        noticeFile.setOrgnlFileNm(originalFileNameList.get(0));

                        fileRootPath = xssUtil.replaceAll(StringUtils.defaultString(fileRootPath));
                        String subPath = "";

                        if (noticeItem.getBlltTpCd().equals("NOTICE") && noticeItem.getBlltTpDtlCd().equals("SERVICE")) {
                            subPath = fileContentsSystemUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsSystemUploadPath));
                        } else {
                            subPath = fileContentsComplexUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsComplexUploadPath));
                        }

                        pipsServiceServerBlltFileUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerBlltFileUrl));

                        String realPath = fileRootPath + subPath;
                        String fileUrl = pipsServiceServerBlltFileUrl + "&file_nm=" + thumbnailFileNameList.get(0);

                        noticeFile.setFilePathCont(realPath);
                        noticeFile.setFileUrl(fileUrl);
                        communityMapper.insertNoticeFile(noticeFile);
                    }

                    noticeItem.setHouscplxCd(danjiInfoList[i]);
                    insertNoticeRelation(noticeItem);
                }
            }else {
                noticeItem.setHouscplxCd(houscplxCd);
                noticeItem.setHmnetNotiCont("");
                communityMapper.insertNotice(noticeItem);

                int blltNo = noticeItem.getBlltNo();
                String adminId = xssUtil.replaceAll(StringUtils.defaultString(noticeItem.getCrerId()));

                if (originalFileNameList.size() > 0) {
                    NoticeFile noticeFile = new NoticeFile();

                    noticeFile.setCrerId(adminId);
                    noticeFile.setEditerId(adminId);
                    noticeFile.setBlltNo(blltNo);
                    noticeFile.setBlltOrdNo(String.valueOf(originalFileNameList.size()));
                    noticeFile.setFileNm(thumbnailFileNameList.get(0));
                    noticeFile.setOrgnlFileNm(originalFileNameList.get(0));

                    fileRootPath = xssUtil.replaceAll(StringUtils.defaultString(fileRootPath));
                    String subPath = "";

                    if (noticeItem.getBlltTpCd().equals("NOTICE") && noticeItem.getBlltTpDtlCd().equals("SERVICE")) {
                        subPath = fileContentsSystemUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsSystemUploadPath));
                    } else {
                        subPath = fileContentsComplexUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsComplexUploadPath));
                    }

                    pipsServiceServerBlltFileUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerBlltFileUrl));

                    String realPath = fileRootPath + subPath;
                    String fileUrl = pipsServiceServerBlltFileUrl + "&file_nm=" + thumbnailFileNameList.get(0);

                    noticeFile.setFilePathCont(realPath);
                    noticeFile.setFileUrl(fileUrl);
                    communityMapper.insertNoticeFile(noticeFile);
                }
                insertNoticeRelation(noticeItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("insertNoticeInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);

    }

    /**
     * 단지 관리자용 공지사항을 등록
     * @param noticeItem
     * @return
     */
    public boolean insertNotice(NoticeItem noticeItem, String houscplxCd) {

        boolean result = true;
        noticeItem.setHmnetNotiCont("");

        if(!houscplxCd.contains(",")) {
            noticeItem.setHouscplxCd(houscplxCd);

            result = communityMapper.insertNotice(noticeItem) > 0;

            if(result) {
                insertNoticeRelation(noticeItem);
            }

        }else {
            String[] danjiInfoList = houscplxCd.split(",");

            for(int i=0; i<danjiInfoList.length; i++){
                result = communityMapper.insertNotice(noticeItem) > 0;

                if(result) {
                    noticeItem.setHouscplxCd(danjiInfoList[i]);
                    insertNoticeRelation(noticeItem);
                }
            }
        }

        return result;
    }

    /**
     * 단지 관리자용 공지게시 전 공지사항과 단지매핑정보 등록
     * @param noticeItem
     * @return
     */
    public boolean insertNoticeRelation(NoticeItem noticeItem) {
/*
        logger.debug(">>>> insertNoticeRelation : " + noticeItem.getHouscplxCd());
        if (StringUtils.isEmpty(noticeItem.getHouscplxCd())) {
            return true;
        }

*/
        if (noticeItem.getHouscplxList() != null) {
            return communityMapper.insertNoticeRelationList(noticeItem) > 0;
        } else {
            return communityMapper.insertNoticeRelation(noticeItem) > 0;
        }
    }

    /**
     * 단지 관리자용 공지사항을 수정
     * @param noticeItem
     * @return
     */
    public boolean updateNotice(NoticeItem noticeItem) {
        noticeItem.setHmnetNotiCont("");
        return communityMapper.updateNotice(noticeItem) > 0;
    }

    /**
     * 단지 관리자용 공지 상태를 변경
     * @param noticeItem
     * @return
     */
    public boolean updateNoticeTlrncYn(NoticeItem noticeItem) {
        boolean result = communityMapper.updateNoticeTlrncYn(noticeItem) > 0;

        return result;
    }

    /**
     * 단지 관리자용 공지사항 정보를 삭제
     * @param noticeItem
     */
    public void deleteNoticeInfo(NoticeItem noticeItem, String adminId) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            Integer blltNo = noticeItem.getBlltNo();

            communityMapper.deleteNotice(noticeItem);

            NoticeFile noticeFile = new NoticeFile();
            noticeFile.setBlltNo(noticeItem.getBlltNo());
            noticeFile.setEditerId(noticeItem.getEditerId());

            communityMapper.deleteNoticeFile(noticeFile);
        } catch (Exception e) {
            logger.debug("deleteNoticeInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 단지 관리자용 설문조사 정보를 등록
     * @param question
     * @param questionDetail
     * @param qstItmAnsrContJsonArray
     */
    public void insertQuestionInfo(Question question, QuestionDetail questionDetail, JSONArray qstItmAnsrContJsonArray) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {

            if(question.getHouscplxCd().contains(",")) {
                String danjiArray = question.getHouscplxCd();
                String[] danjiList = danjiArray.split(",");
                for (int i = 0; i < danjiList.length; i++) {
                    communityMapper.insertQuestion(question);

                    int qstNo = question.getQstNo();

                    Map<String, Object> qstItmAnsrContMap = new HashMap<String, Object>();
                    int length = qstItmAnsrContJsonArray.length();

                    if (length > 0) {
                        List<QuestionDetail> list = new ArrayList<QuestionDetail>();

                        for (int j = 0; j < length; j++) {
                            QuestionDetail questionDetailInfo = new QuestionDetail();
                            String qstItmAnsrCont = (String) qstItmAnsrContJsonArray.getJSONObject(j).get("qstItmAnsrCont");

                            questionDetailInfo.setQstNo(qstNo);
                            questionDetailInfo.setQstItmQuestCont(questionDetail.getQstItmQuestCont());
                            questionDetailInfo.setQstItmAnsrCont(qstItmAnsrCont);
                            questionDetailInfo.setQstItmAnsrCnt(0);
                            questionDetailInfo.setCrerId(question.getCrerId());

                            list.add(questionDetailInfo);
                        }
                        qstItmAnsrContMap.put("list", list);
                    }
                    communityMapper.insertQuestionDetail(qstItmAnsrContMap);
                    question.setHouscplxCd(danjiList[i]);
                    insertQuestionRelation(question);
                }
            }else {
                communityMapper.insertQuestion(question);

                int qstNo = question.getQstNo();

                Map<String, Object> qstItmAnsrContMap = new HashMap<String, Object>();
                int length = qstItmAnsrContJsonArray.length();

                if (length > 0) {
                    List<QuestionDetail> list = new ArrayList<QuestionDetail>();

                    for (int i = 0; i < length; i++) {
                        QuestionDetail questionDetailInfo = new QuestionDetail();
                        String qstItmAnsrCont = (String) qstItmAnsrContJsonArray.getJSONObject(i).get("qstItmAnsrCont");

                        questionDetailInfo.setQstNo(qstNo);
                        questionDetailInfo.setQstItmQuestCont(questionDetail.getQstItmQuestCont());
                        questionDetailInfo.setQstItmAnsrCont(qstItmAnsrCont);
                        questionDetailInfo.setQstItmAnsrCnt(0);
                        questionDetailInfo.setCrerId(question.getCrerId());

                        list.add(questionDetailInfo);
                    }
                    qstItmAnsrContMap.put("list", list);
                }
                communityMapper.insertQuestionDetail(qstItmAnsrContMap);
                insertQuestionRelation(question);
            }
        } catch (Exception e) {
            logger.debug("insertQuestionInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);

        String qstStsCd = StringUtils.defaultIfEmpty(question.getQstStsCd(), "");

        // 설문게시일 경우 Push 전송 API 호출
        if (qstStsCd.equals("2")) {
            String pushTargetNo = String.valueOf(question.getQstNo());
            pushTargetNo = xssUtil.replaceAll(StringUtils.defaultString(pushTargetNo));

            String tpCd = "Q";
            List<String> houscplxList = new ArrayList<>();
            houscplxList.add(question.getHouscplxCd());

            apiService.sendPush(pushTargetNo, tpCd, houscplxList);
        }
    }

    /**
     * 단지 관리자용 설문게시 전 설문조사와 단지매핑정보 등록
     * @param question
     * @return
     */
    public boolean insertQuestionRelation(Question question) {
        return communityMapper.insertQuestionRelation(question) > 0;
    }

    /**
     * 단지 관리자용 설문조사 기본정보 및 설문세부항목 수정
     * @param question
     * @param questionDetail
     * @param qstItmAnsrContJsonArray
     */
    public void updateQuestionInfo(Question question, QuestionDetail questionDetail, JSONArray qstItmAnsrContJsonArray) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            communityMapper.updateQuestion(question);

            Map<String, Object> qstItmAnsrContMap = new HashMap<String, Object>();
            int length = qstItmAnsrContJsonArray.length();

            if (length > 0) {
                List<QuestionDetail> list = new ArrayList<QuestionDetail>();

                for(int i=0; i<length; i++) {
                    QuestionDetail questionDetailInfo = new QuestionDetail();
                    String qstItmAnsrCont = (String) qstItmAnsrContJsonArray.getJSONObject(i).get("qstItmAnsrCont");
                    String qstItmNo = (String) qstItmAnsrContJsonArray.getJSONObject(i).get("qstItmNo");


                    questionDetailInfo.setQstItmQuestCont(questionDetail.getQstItmQuestCont());
                    questionDetailInfo.setQstItmAnsrCont(qstItmAnsrCont);
                    questionDetailInfo.setEditerId(question.getEditerId());
                    questionDetailInfo.setQstNo(question.getQstNo());
                    questionDetailInfo.setQstItmNo(Integer.parseInt(qstItmNo));

                    list.add(questionDetailInfo);
                }
                qstItmAnsrContMap.put("list", list);
            }

            communityMapper.updateQuestionDetail(qstItmAnsrContMap);
        } catch (Exception e) {
            logger.debug("updateQuestionInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 단지 관리자용 설문조사 기본정보 및 새로운 설문세부항목 등록
     * @param question
     * @param questionDetail
     * @param newQstItmAnsrContJsonArray
     */
    public void updateQuestionInfoForNewItem(Question question, QuestionDetail questionDetail, JSONArray newQstItmAnsrContJsonArray, String adminId) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            communityMapper.updateQuestion(question);

            Integer qstNo = question.getQstNo();

            Map<String, Object> newQstItmAnsrContMap = new HashMap<String, Object>();
            int length = newQstItmAnsrContJsonArray.length();

            if (length > 0) {
                List<QuestionDetail> list = new ArrayList<QuestionDetail>();

                for(int i=0; i<length; i++) {
                    QuestionDetail questionDetailInfo = new QuestionDetail();
                    String qstItmAnsrCont = (String) newQstItmAnsrContJsonArray.getJSONObject(i).get("qstItmAnsrCont");
                    String crerId = (String) newQstItmAnsrContJsonArray.getJSONObject(i).get("editerId");

                    questionDetailInfo.setQstItmQuestCont(questionDetail.getQstItmQuestCont());
                    questionDetailInfo.setQstItmAnsrCont(qstItmAnsrCont);
                    questionDetailInfo.setCrerId(crerId);
                    questionDetailInfo.setQstNo(question.getQstNo());

                    list.add(questionDetailInfo);
                }
                newQstItmAnsrContMap.put("list", list);
            }

            communityMapper.deleteQuestionDetail(qstNo, adminId);
            communityMapper.insertQuestionDetailForNewItem(newQstItmAnsrContMap);
        } catch (Exception e) {
            logger.debug("updateQuestionInfoForNewItem Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 배치용 기한이 지난 설문조사 상태 변경 (Job)
     * @return
     */
    public boolean updateQuestionEndStsCd() {
        List<Question> endQuestionList = communityMapper.selectQuestionEndList();
        Map<String, Object> endQuestionMap = new HashMap<String, Object>();

        if (endQuestionList.size() > 0) {
            for(int i=0; i<endQuestionList.size(); i++) {
                Question questionInfo = new Question();
                questionInfo.setQstNo(endQuestionList.get(i).getQstNo());

                endQuestionList.add(questionInfo);
            }
            endQuestionMap.put("list", endQuestionList);
        }

        return communityMapper.updateEndQuestionStsCd(endQuestionMap) > 0;
    }

    /**
     * 단지 관리자용 설문조사 상태를 변경
     * @param question
     * @return
     */
    public boolean updateQuestionStsCd(Question question) {
        boolean result = communityMapper.updateQuestionStsCd(question) > 0;

        if (BooleanUtils.isTrue(result)) {
            String qstStsCd = StringUtils.defaultIfEmpty(question.getQstStsCd(), "");

            // 설문게시일 경우 Push 전송 API 호출
            if (qstStsCd.equals("2")) {
                String pushTargetNo = String.valueOf(question.getQstNo());
                pushTargetNo = xssUtil.replaceAll(StringUtils.defaultString(pushTargetNo));

                String tpCd = "Q";
                List<String> houscplxList = new ArrayList<>();
                houscplxList.add(question.getHouscplxCd());

                apiService.sendPush(pushTargetNo, tpCd, houscplxList);
            }
        }

        return result;
    }

    /**
     * 단지 관리자용 설문조사 정보를 삭제
     * @param question
     */
    public void deleteQuestionInfo(Question question, String adminId) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            Integer qstNo = question.getQstNo();

            communityMapper.deleteQuestion(question);
            communityMapper.deleteQuestionDetail(qstNo, adminId);
        } catch (Exception e) {
            logger.debug("deleteQuestionInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 단지 관리자용 생활불편신고 정보를 등록 (답변 및 첨부파일)
     * @param noticeItem
     * @param originalFileNameList
     * @param thumbnailFileNameList
     */
    public void insertReportAnswerInfoFile(NoticeItem noticeItem, List<String> originalFileNameList, List<String> thumbnailFileNameList, String reportUserId) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        Integer blltGrpNo = null;

        try {
            noticeItem.setHmnetNotiCont("");
            communityMapper.insertReportAnswer(noticeItem);

            blltGrpNo = noticeItem.getBlltGrpNo();

            if (originalFileNameList.size() > 0) {
                NoticeFile noticeFile = new NoticeFile();

                noticeFile.setBlltNo(noticeItem.getBlltNo());
                noticeFile.setBlltOrdNo(String.valueOf(originalFileNameList.size()));
                noticeFile.setFileNm(thumbnailFileNameList.get(0));
                noticeFile.setOrgnlFileNm(originalFileNameList.get(0));
                noticeFile.setCrerId(noticeItem.getEditerId());

                fileRootPath = xssUtil.replaceAll(StringUtils.defaultString(fileRootPath));
                fileContentsComplexUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsComplexUploadPath));
                pipsServiceServerBlltFileUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerBlltFileUrl));

                String realPath = fileRootPath + fileContentsComplexUploadPath;
                String fileUrl = pipsServiceServerBlltFileUrl + "&file_nm=" + thumbnailFileNameList.get(0);

                noticeFile.setFilePathCont(realPath);
                noticeFile.setFileUrl(fileUrl);
                communityMapper.insertNoticeFile(noticeFile);

                noticeItem.setBlltNo(blltGrpNo);
                noticeItem.setBlltStsCd("COMPLETE");
                noticeItem.setEditerId(noticeItem.getCrerId());
                communityMapper.updateReportBlltStsCd(noticeItem);
            }
        } catch (Exception e) {
            logger.debug("insertReportInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);

        String pushTargetNo = String.valueOf(noticeItem.getBlltNo());
        pushTargetNo = xssUtil.replaceAll(StringUtils.defaultString(pushTargetNo));

        apiService.sendReportPush(pushTargetNo, reportUserId);
    }

    /**
     * 단지 관리자용 생활불편신고 정보를 등록 (답변)
     * @param noticeItem
     * @return
     */
    public void insertReportAnswerInfo(NoticeItem noticeItem, String reportUserId) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        Integer blltGrpNo = null;

        try {
            noticeItem.setHmnetNotiCont("");
            communityMapper.insertReportAnswer(noticeItem);

            blltGrpNo = noticeItem.getBlltGrpNo();

            noticeItem.setBlltNo(blltGrpNo);
            noticeItem.setBlltStsCd("COMPLETE");
            noticeItem.setEditerId(noticeItem.getCrerId());
            communityMapper.updateReportBlltStsCd(noticeItem);
        } catch (Exception e) {
            logger.debug("insertReportAnswerInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);

        String pushTargetNo = String.valueOf(blltGrpNo);
        pushTargetNo = xssUtil.replaceAll(StringUtils.defaultString(pushTargetNo));

        apiService.sendReportPush(pushTargetNo, reportUserId);
    }

    /**
     * 단지 관리자용 생활불편신고 상태를 변경
     * @param noticeItem
     * @return
     */
    public boolean updateReportBlltStsCd(NoticeItem noticeItem) {
        return communityMapper.updateReportBlltStsCd(noticeItem) > 0;
    }

    /**
     * 단지 관리자용 공지사항 미사용 파일을 삭제
     * @param noticeFile
     * @return
     */
    public boolean deleteUnusedNoticeFile(NoticeFile noticeFile) {
        return communityMapper.deleteUnusedNoticeFile(noticeFile) > 0;
    }

    /**
     * 수정/삭제/공지상태 변경 시 사용자 공지사항 확인여부 삭제
     * @param blltNo
     * @return
     */
    public boolean deleteUserNotiCfRlt(int blltNo) {
        return communityMapper.deleteUserNotiCfRlt(blltNo) > 0;
    }
}