package com.daewooenc.pips.admin.web.service.system.standardCode;

import com.daewooenc.pips.admin.web.dao.standardCode.StandardCodeMapper;
import com.daewooenc.pips.admin.web.domain.dto.standardCode.StandardCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2021-04-07      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2021-04-07
 **/
@Service
public class StandardCodeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StandardCodeMapper standardCodeMapper;

    /**
     * 시스템 관리자용 공통코드 목록 조회
     * @return
     */
    public List<StandardCode> getStandardCodeList(StandardCode StandardCode) {
        List<StandardCode> standardCodeList = standardCodeMapper.selectStandardCodeList(StandardCode);

        return standardCodeList;
    }

    /**
     * 시스템 관리자용 공통코드 목록 공통코드 이름 조회
     * @return
     */
    public List<StandardCode> getGroupStandardCodeNameList() {
        List<StandardCode> groupStandardCodeNameList = standardCodeMapper.selectGroupStandardCodeNameList();

        return groupStandardCodeNameList;
    }

    /**
     * 시스템 관리자용 공통코드 목록 공통코드 이름 조회
     * @return
     */
    public List<StandardCode> getStandardCodeNameList(StandardCode StandardCode) {
        List<StandardCode> standardCodeNameList = standardCodeMapper.selectStandardCodeNameList(StandardCode);

        return standardCodeNameList;
    }

    /**
     * 시스템 관리자용 공통코드 삭제
     * @return
     */
    public boolean deleteStandardCode(StandardCode StandardCode) {

        return standardCodeMapper.deleteStandardCode(StandardCode) > 0;
    }

    /**
     * 시스템 관리자용 공통코드 중복 체크
     * @return
     */
    public boolean checkStandardCode(StandardCode StandardCode) {

        return standardCodeMapper.checkStandardCode(StandardCode) > 0;
    }

    /**
     * 시스템 관리자용 공통코드 등록
     * @return
     */
    public boolean insertStandardCode(StandardCode StandardCode) {

        return standardCodeMapper.insertStandardCode(StandardCode) > 0;
    }

    /**
     * 시스템 관리자용 공통코드 수정 조회
     * @return
     */
    public StandardCode getStandardCodeEdit(StandardCode StandardCode) {
        StandardCode standardCodeEdit = standardCodeMapper.selectStandardCodeEdit(StandardCode);

        return standardCodeEdit;
    }

    /**
     * 시스템 관리자용 공통코드 수정
     * @return
     */
    public boolean updateStandardCode(StandardCode StandardCode) {

        return standardCodeMapper.updateStandardCode(StandardCode) > 0;
    }

}