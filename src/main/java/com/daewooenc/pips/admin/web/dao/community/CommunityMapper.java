package com.daewooenc.pips.admin.web.dao.community;

import com.daewooenc.pips.admin.web.domain.dto.community.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 공지사항, 설문관련 및 생활불편신고 관련 CommunityMapper.xml 맵핑 Interface
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-28      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-28
 **/
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface CommunityMapper {
    /**
     * 시스템 관리자: 단지 공지사항 목록조회
     * @param noticeItem
     * @return
     */
    List<NoticeItem> selectNoticeListForAdmin(NoticeItem noticeItem);

    /**
     * 멀티 단지 관리자: 단지 공지사항 목록조회
     * @param noticeItem
     * @return
     */
    List<NoticeItem> selectMultiNoticeListForAdmin(NoticeItem noticeItem);

    /**
     * 시스템 관리자: 단지 공지사항 상세조회
     * @param noticeItem
     * @return
     */
    NoticeItem selectNoticeDetailForAdmin(NoticeItem noticeItem);

    /**
     * 시스템 관리자: 단지 공지사항 상세 첨부파일조회
     * @param noticeItem
     * @return
     */
    List<NoticeFile> selectNoticeDetailFile(NoticeItem noticeItem);

    /**
     * 단지 관리자: 단지 공지사항 목록조회
     * @param noticeItem
     * @return
     */
    List<NoticeItem> selectNoticeList(NoticeItem noticeItem);

    /**
     * 단지 관리자: 단지 공지사항 상세조회
     * @param noticeItem
     * @return
     */
    NoticeItem selectNoticeDetail(NoticeItem noticeItem);

    /**
     * 시스템 관리자: 서비스 공지사항 목록조회
     * @param noticeItem
     * @return
     */
    List<NoticeItem> selectServiceNoticeList(NoticeItem noticeItem);

    /**
     * 시스템 관리자: 서비스 공지사항 상세
     * @param noticeItem
     * @return
     */
    NoticeItem selectServiceNoticeDetail(NoticeItem noticeItem);

    /**
     * 시스템 관리자: 서비스 공지사항 단지 목록조회
     * @param noticeItem
     * @return
     */
    List<NoticeItem> selectServiceHouscplxList(NoticeItem noticeItem);

    /**
     * 시스템 관리자: 설문조사 목록조회
     * @param question
     * @return
     */
    List<Question> selectQuestionListForAdmin(Question question);

    /**
     * 시스템 관리자: 설문조사 상세조회
     * @param questionDetail
     * @return
     */
    Question selectQuestionDetailForAdmin(QuestionDetail questionDetail);

    /**
     * 공통: 설문조사 상세항목조회
     * @param questionDetail
     * @return
     */
    List<QuestionDetail> selectQuestionItemList(QuestionDetail questionDetail);

    /**
     * 공통: 설문조사 상세 기타내용 조회
     * @param questionCompletion
     * @return
     */
    List<QuestionCompletion> selectQuestionEtcAnswerList(QuestionCompletion questionCompletion);

    /**
     * 시스템 관리자: 세대별 투표내역 조회
     * @param questionCompletion
     * @return
     */
    List<QuestionCompletion> selectQuestionCompletionList(QuestionCompletion questionCompletion);

    /**
     * 단지 관리자: 설문조사 목록조회
     * @param question
     * @return
     */
    List<Question> selectQuestionList(Question question);

    /**
     * 멀티 단지 관리자: 설문조사 목록조회
     * @param question
     * @return
     */
    List<Question> selectMultiQuestionList(Question question);

    /**
     * 단지 관리자: 설문조사 상세조회
     * @param questionDetail
     * @return
     */
    Question selectQuestionDetail(QuestionDetail questionDetail);

    /**
     * 배치 Job: 설문마감일이 끝난 설문조사 목록조회
     * @return
     */
    List<Question> selectQuestionEndList();

    /**
     * 시스템 관리자: 생활불편신고 목록조회
     * @param noticeItem
     * @return
     */
    List<NoticeItem> selectReportListForAdmin(NoticeItem noticeItem);

    /**
     * 멀티 단지 관리자: 생활불편신고 목록조회
     * @param noticeItem
     * @return
     */
    List<NoticeItem> selectMultiReportListForAdmin(NoticeItem noticeItem);

    /**
     * 시스템 관리자: 생활불편신고 상세조회
     * @param blltNo
     * @param houscplxCd
     * @param encKey
     * @return
     */
    NoticeItem selectReportDetailForAdmin(@Param("blltNo") int blltNo, @Param("houscplxCd") String houscplxCd, @Param("encKey") String encKey, @Param("hsholdId") String hsholdId);

    /**
     * 공통: 생활불편신고 신고자 파일목록조회
     * @param noticeItem
     * @return
     */
    List<NoticeFile> selectReportDetailFile(NoticeItem noticeItem);

    /**
     * 공통: 생활불편신고 처리자 파일목록조회
     * @param noticeItem
     * @return
     */
    List<NoticeFile> selectReportDetailAnswerFile(NoticeItem noticeItem);

    /**
     * 단지 관리자: 생활불편신고 목록조회
     * @param noticeItem
     * @return
     */
    List<NoticeItem> selectReportList(NoticeItem noticeItem);

    /**
     * 단지 관리자: 생활불편신고 상세조회
     * @param blltNo
     * @param houscplxCd
     * @param encKey
     * @return
     */
    NoticeItem selectReportDetail(@Param("blltNo") int blltNo, @Param("houscplxCd") String houscplxCd, @Param("encKey") String encKey, @Param("hsholdId") String hsholdId);

    /**
     * 단지 관리자: 단지 공지사항 등록
     * @param noticeItem
     * @return
     */
    int insertNotice(NoticeItem noticeItem);

    /**
     * 단지 관리자: 공지게시 전 공지시항과 단지매핑정보 등록
     * @param noticeItem
     * @return
     */
    int insertNoticeRelation(NoticeItem noticeItem);

    /**
     * 시스템 관리자: 공지게시 전 서비스 공지시항과 단지매핑정보 등록
     * @param noticeItem
     * @return
     */
    int insertNoticeRelationList(NoticeItem noticeItem);

    int deleteNoticeRelationList(NoticeItem noticeItem);

    /**
     * 단지 관리자: 단지 공지사항 파일 업로드
     * @param noticeFile
     * @return
     */
    int insertNoticeFile(NoticeFile noticeFile);

    /**
     * 단지 관리자: 설문조사 등록
     * @param question
     * @return
     */
    int insertQuestion(Question question);

    /**
     * 단지 관리자: 설문조사 세부항목 등록
     * @param qstItmAnsrContMap
     * @return
     */
    int insertQuestionDetail(Map<String, Object> qstItmAnsrContMap);

    /**
     * 단지 관리자: 설문조사 세부항목 기존삭제 후 등록
     * @param qstItmAnsrContMap
     * @return
     */
    int insertQuestionDetailForNewItem(Map<String, Object> qstItmAnsrContMap);

    /**
     * 단지 관리자: 설문게시 전 설문과 단지매핑정보 등록
     * @param question
     * @return
     */
    int insertQuestionRelation(Question question);

    /**
     * 단지 관리자: 생활불편신고 답변 등록
     * @param noticeItem
     * @return
     */
    int insertReportAnswer(NoticeItem noticeItem);

    /**
     * 단지 관리자: 공지사항 수정
     * @param noticeItem
     * @return
     */
    int updateNotice(NoticeItem noticeItem);

    /**
     * 단지 관리자: 단지 공지사항 수정파일 업로드
     * @param noticeFile
     * @return
     */
    int updateNoticeFile(NoticeFile noticeFile);

    /**
     * 단지 관리자: 공지사항 삭제
     * @param noticeItem
     * @return
     */
    int deleteNotice(NoticeItem noticeItem);

    /**
     * 단지 관리자: 단지 공지사항 업로드 파일 삭제
     * @param noticeFile
     * @return
     */
    int deleteNoticeFile(NoticeFile noticeFile);

    /**
     * 단지 관리자: 공지사항 상태 변경
     * @param noticeItem
     * @return
     */
    int updateNoticeTlrncYn(NoticeItem noticeItem);

    /**
     * 단지 관리자: 설문조사 수정
     * @param question
     * @return
     */
    int updateQuestion(Question question);

    /**
     * 단지 관리자: 설문조사 세부항목 수정
     * @param qstItmAnsrContMap
     * @return
     */
    int updateQuestionDetail(Map<String, Object> qstItmAnsrContMap);

    /**
     * 단지 관리자: 설문조사 상태 변경
     * @param question
     * @return
     */
    int updateQuestionStsCd(Question question);

    /**
     * 배치 Job: 기한이 지난 설문조사 상태 변경
     * @param qstEndList
     * @return
     */
    int updateEndQuestionStsCd(Map<String, Object> qstEndList);

    /**
     * 단지 관리자: 생활불편신고 상태 변경
     * @param noticeItem
     * @return
     */
    int updateReportBlltStsCd(NoticeItem noticeItem);

    /**
     * 단지 관리자: 설문조사 삭제
     * @param question
     * @return
     */
    int deleteQuestion(Question question);

    /**
     * 단지 관리자: 단지 설문조사 항목 삭제
     * @param qstNo
     * @return
     */
    int deleteQuestionDetail(@Param("qstNo") int qstNo, @Param("adminId") String adminId);

    /**
     * 단지 관리자: 공지사항 미사용 첨부파일 삭제
     * @param noticeFile
     * @return
     */
    int deleteUnusedNoticeFile(NoticeFile noticeFile);

    /**
     * 수정/삭제/공지상태 변경 시 사용자 공지사항 확인여부 삭제
     * @param blltNo
     * @return
     */
    int deleteUserNotiCfRlt(int blltNo);

    /**
     * 시스템 관리자: 단지 리스트 조회
     * @param
     * @return
     */
    List<NoticeItem> selectHouscplxBasAll();
}
