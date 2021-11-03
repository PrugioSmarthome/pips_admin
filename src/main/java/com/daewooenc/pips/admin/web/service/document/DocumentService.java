package com.daewooenc.pips.admin.web.service.document;

import com.daewooenc.pips.admin.web.dao.document.DocumentMapper;
import com.daewooenc.pips.admin.web.dao.externalsvcinfo.ExternalSvcInfoMapper;
import com.daewooenc.pips.admin.web.domain.dto.community.NoticeFile;
import com.daewooenc.pips.admin.web.domain.dto.community.NoticeItem;
import com.daewooenc.pips.admin.web.domain.dto.document.Document;
import com.daewooenc.pips.admin.web.domain.dto.externalsvcinfo.ExternalServiceInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * 문서관리 관련 Service
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-12-12      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-12-12
 **/
@Service
public class DocumentService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private DocumentMapper documentMapper;

    /**
     * 시스템 관리자용 문서관리 목록을 조회
     * @param document
     * @return
     */
    public List<Document> getDocumentList(Document document) {
        List<Document> documentList = documentMapper.selectDocumentList(document);

        return documentList;
    }

    /**
     * 시스템 관리자용 문서관리 상세내용을 조회
     * @param document
     * @return
     */
    public Document getDocumentDetail(Document document) {
        Document documentDetail = documentMapper.selectDocumentDetail(document);

        return documentDetail;
    }

    /**
     * 시스템 관리자용 문서정보 등록
     * @param document
     */
    public void insertDocument(Document document, String houscplxCd) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {

            /* 로직 제거(잘 못 만듦) 20201130
            if (document.getUseYn().equals("Y")) {
                Document prevDocument = new Document();
                prevDocument.setEditerId(document.getCrerId());
                prevDocument.setLnkAtchFileGrpTpCd(document.getLnkAtchFileGrpTpCd());
                prevDocument.setLnkAtchFileTpCd(document.getLnkAtchFileTpCd());
                prevDocument.setLnkAtchFileId(document.getLnkAtchFileId());
                prevDocument.setUseYn("N");
                logger.debug("insertDocument>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+houscplxCd);

                prevDocument.setHouscplxCd(document.getHouscplxCd());


                documentMapper.updateDocumentStatus(prevDocument);
            }
            */
            documentMapper.insertDocument(document);
        } catch (Exception e) {
            logger.debug("insertDocument Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 시스템 관리자용 문서정보 수정
     * @param document
     */
    public void updateDocument(Document document, String houscplxCd) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            if (document.getUseYn().equals("Y")) {
                Document prevDocument = new Document();
                prevDocument.setEditerId(document.getCrerId());
                prevDocument.setLnkAtchFileGrpTpCd(document.getLnkAtchFileGrpTpCd());
                prevDocument.setLnkAtchFileTpCd(document.getLnkAtchFileTpCd());
                prevDocument.setLnkAtchFileId(document.getLnkAtchFileId());
                prevDocument.setUseYn("N");
logger.debug("updateDocument>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+houscplxCd);
                prevDocument.setHouscplxCd(document.getHouscplxCd());

                documentMapper.updateDocumentStatus(prevDocument);
            }

            String isNewFile = document.getIsNewFile();

            if (isNewFile.equals("Y")) {
                documentMapper.updateDocumentFile(document);
            } else if (isNewFile.equals("N")) {
                documentMapper.updateDocumentInfo(document);
            }
        } catch (Exception e) {
            logger.debug("updateDocument Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    public boolean deleteDocument(Document document) {
        return documentMapper.deleteDocument(document) > 0;
    }
}