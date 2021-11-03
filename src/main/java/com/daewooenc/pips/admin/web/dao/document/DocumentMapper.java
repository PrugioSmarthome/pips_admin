package com.daewooenc.pips.admin.web.dao.document;

import com.daewooenc.pips.admin.web.domain.dto.document.Document;
import com.daewooenc.pips.admin.web.domain.dto.externalsvcinfo.ExternalServiceInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 문서관리 관련 DocumentMapper.xml 맵핑 Interface
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
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface DocumentMapper {

    /**
     * 시스템 관리자: 문서관리 목록조회
     * @param document
     * @return
     */
    List<Document> selectDocumentList(Document document);

    /**
     * 시스템 관리자: 문서관리 상세조회
     * @param document
     * @return
     */
    Document selectDocumentDetail(Document document);

    /**
     * 시스템 관리자: 문서 등록
     * @param document
     * @return
     */
    int insertDocument(Document document);

    /**
     * 시스템 관리자: 문서 수정 (파일정보 포함)
     * @param document
     * @return
     */
    int updateDocumentFile(Document document);

    /**
     * 시스템 관리자: 문서 수정
     * @param document
     * @return
     */
    int updateDocumentInfo(Document document);

    /**
     * 시스템 관리자: 문서 상태 변경
     * @param document
     * @return
     */
    int updateDocumentStatus(Document document);

    /**
     * 시스템 관리자: 문서 삭제
     * @param document
     * @return
     */
    int deleteDocument(Document document);
}
