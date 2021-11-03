package com.daewooenc.pips.admin.web.dao.groupCode;

import com.daewooenc.pips.admin.web.domain.dto.groupCode.GroupCode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 대우건설 스마트홈 푸르지오 그룹코드 관련 GroupCodeMapper.xml 맵핑 Interface
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2021-03-26    :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2021-03-26
 * **/
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface GroupCodeMapper {
    /**
     * 시스템 관리자용 그룹코드 관리 목록 조회
     * @return
     */
    List<GroupCode> selectGroupCodeList(GroupCode groupCode);
    /**
     * 시스템 관리자용 그룹코드 이름 조회
     * @return
     */
    List<GroupCode> selectGroupCodeNameList(GroupCode groupCode);
    /**
     * 시스템 관리자용 그룹코드 삭제
     * @return
     */
    int deleteGroupCodeBas(GroupCode groupCode);
    /**
     * 시스템 관리자용 그룹코드 삭제
     * @return
     */
    int deleteGroupCodeDtl(GroupCode groupCode);
    /**
     * 시스템 관리자용 그룹코드 중복 체크
     * @return
     */
    int checkGroupCode(GroupCode groupCode);
    /**
     * 시스템 관리자용 그룹코드 등록
     * @return
     */
    int insertGroupCode(GroupCode groupCode);
    /**
     * 시스템 관리자용 그룹코드 수정 조회
     * @return
     */
    GroupCode selectGroupCodeEdit(GroupCode groupCode);
    /**
     * 시스템 관리자용 그룹코드 수정
     * @return
     */
    int updateGroupCode(GroupCode groupCode);
}
