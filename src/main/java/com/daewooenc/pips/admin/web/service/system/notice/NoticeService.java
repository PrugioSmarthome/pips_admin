package com.daewooenc.pips.admin.web.service.system.notice;

import com.daewooenc.pips.admin.web.dao.community.CommunityMapper;
import com.daewooenc.pips.admin.web.domain.dto.community.NoticeFile;
import com.daewooenc.pips.admin.web.domain.dto.community.NoticeItem;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
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
@Service
public class NoticeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private @Value("${file.rootPath}") String fileRootPath;
    private @Value("${file.contents.systemUploadPath}") String fileContentsSystemUploadPath;
    private @Value("${pips.serviceServer.bllt.file.url}") String pipsServiceServerBlltFileUrl;

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private XSSUtil xssUtil;

    /**
     * 시스템 관리자용 서비스 공지사항 목록 조회
     * @param noticeItem
     * @return
     */
    public List<NoticeItem> getServiceNoticeList(NoticeItem noticeItem) {
        return communityMapper.selectServiceNoticeList(noticeItem);
    }

    /**
     * 시스템 관리자용 서비스 공지사항 상세내용 조회
     * @param noticeItem
     * @return
     */
    public NoticeItem getServiceNoticeDetail(NoticeItem noticeItem) {
        return communityMapper.selectServiceNoticeDetail(noticeItem);
    }

    /**
     * 시스템 관리자용 서비스 공지사항 상세내용의 첨부파일 목록을 조회
     * @param noticeItem
     * @return
     */
    public List<NoticeFile> getServiceNoticeDetailFile(NoticeItem noticeItem) {
        List<NoticeFile> noticeFileList = communityMapper.selectNoticeDetailFile(noticeItem);

        return noticeFileList;
    }

    public List<NoticeItem> getServiceHouscplxList(NoticeItem noticeItem) {
        return communityMapper.selectServiceHouscplxList(noticeItem);
    }

    /**
     * 서비스 공지사항을 수정
     * @param noticeItem
     * @return
     */
    public boolean updateNotice(NoticeItem noticeItem) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        int result = 0;

        try {
            noticeItem.setHmnetNotiCont("");
            communityMapper.deleteNoticeRelationList(noticeItem);
            if (noticeItem.getHouscplxList() != null) {
                communityMapper.insertNoticeRelationList(noticeItem);
            }

            result = communityMapper.updateNotice(noticeItem);
        } catch (Exception e) {
            logger.debug("updateNotice Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);

        return result > 0;
    }

    /**
     * 서비스 공지사항 정보를 수정
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
                fileContentsSystemUploadPath = xssUtil.replaceAll(StringUtils.defaultString(fileContentsSystemUploadPath));
                pipsServiceServerBlltFileUrl = xssUtil.replaceAll(StringUtils.defaultString(pipsServiceServerBlltFileUrl));

                String realPath = fileRootPath + fileContentsSystemUploadPath;
                String fileUrl = pipsServiceServerBlltFileUrl + "&file_nm=" + thumbnailFileNameList.get(0);

                noticeFile.setFilePathCont(realPath);
                noticeFile.setFileUrl(fileUrl);

                communityMapper.updateNoticeFile(noticeFile);
            }

            communityMapper.deleteNoticeRelationList(noticeItem);
            if (noticeItem.getHouscplxList() != null) {
                communityMapper.insertNoticeRelationList(noticeItem);
            }

        } catch (Exception e) {
            logger.debug("updateNoticeInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    public List<NoticeItem> getHouscplxBasAll() {
        List<NoticeItem> houscplxBas = communityMapper.selectHouscplxBasAll();
        return houscplxBas;
    }

}