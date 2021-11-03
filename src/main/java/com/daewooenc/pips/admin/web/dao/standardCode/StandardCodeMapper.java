package com.daewooenc.pips.admin.web.dao.standardCode;

import com.daewooenc.pips.admin.web.domain.dto.standardCode.StandardCode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 대우건설 스마트홈 푸르지오 공통코드 관련 StandardCodeMapper.xml 맵핑 Interface
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2021-04-07    :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2021-04-07
 * **/
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface StandardCodeMapper {
    /**
     * 시스템 관리자용 공통코드 관리 목록 조회
     * @return
     */
    List<StandardCode> selectStandardCodeList(StandardCode standardCode);
    /**
     * 시스템 관리자용 공통코드 관리 그룹코드,공통코드 이름 조회
     * @return
     */
    List<StandardCode> selectGroupStandardCodeNameList();
    /**
     * 시스템 관리자용 공통코드 관리 공통코드 이름 조회
     * @return
     */
    List<StandardCode> selectStandardCodeNameList(StandardCode standardCode);
    /**
     * 시스템 관리자용 공통코드 삭제
     * @return
     */
    int deleteStandardCode(StandardCode standardCode);
    /**
     * 시스템 관리자용 공통코드 중복 체크
     * @return
     */
    int checkStandardCode(StandardCode standardCode);
    /**
     * 시스템 관리자용 공통코드 등록
     * @return
     */
    int insertStandardCode(StandardCode standardCode);
    /**
     * 시스템 관리자용 공통코드 수정 조회
     * @return
     */
    StandardCode selectStandardCodeEdit(StandardCode standardCode);
    /**
     * 시스템 관리자용 공통코드 수정
     * @return
     */
    int updateStandardCode(StandardCode standardCode);
}
