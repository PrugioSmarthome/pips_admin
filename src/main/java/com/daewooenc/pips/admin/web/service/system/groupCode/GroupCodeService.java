package com.daewooenc.pips.admin.web.service.system.groupCode;

import com.daewooenc.pips.admin.web.dao.groupCode.GroupCodeMapper;
import com.daewooenc.pips.admin.web.dao.manufacturerByDevice.ManufacturerByDeviceMapper;
import com.daewooenc.pips.admin.web.domain.dto.groupCode.GroupCode;
import com.daewooenc.pips.admin.web.domain.dto.manufacturerByDevice.ManufacturerByDevice;
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
 *       2021-03-26      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2021-03-26
 **/
@Service
public class GroupCodeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupCodeMapper groupCodeMapper;

    /**
     * 시스템 관리자용 그룹코드 목록 조회
     * @return
     */
    public List<GroupCode> getGroupCodeList(GroupCode groupCode) {
        List<GroupCode> groupCodeList = groupCodeMapper.selectGroupCodeList(groupCode);

        return groupCodeList;
    }

    /**
     * 시스템 관리자용 그룹코드 이름 조회
     * @return
     */
    public List<GroupCode> getGroupCodeNameList(GroupCode groupCode) {
        List<GroupCode> groupCodeNameList = groupCodeMapper.selectGroupCodeNameList(groupCode);

        return groupCodeNameList;
    }

    /**
     * 시스템 관리자용 그룹코드 삭제
     * @return
     */
    public boolean deleteGroupCodeBas(GroupCode groupCode) {

        return groupCodeMapper.deleteGroupCodeBas(groupCode) > 0 ;
    }

    /**
     * 시스템 관리자용 그룹코드 삭제
     * @return
     */
    public boolean deleteGroupCodeDtl(GroupCode groupCode) {

        return groupCodeMapper.deleteGroupCodeDtl(groupCode) > 0 ;
    }

    /**
     * 시스템 관리자용 그룹코드 중복 체크
     * @return
     */
    public boolean checkGroupCode(GroupCode groupCode) {

        return groupCodeMapper.checkGroupCode(groupCode) > 0 ;
    }

    /**
     * 시스템 관리자용 그룹코드 등록
     * @return
     */
    public boolean insertGroupCode(GroupCode groupCode) {
        return groupCodeMapper.insertGroupCode(groupCode) > 0 ;
    }

    /**
     * 시스템 관리자용 그룹코드 수정 조회
     * @return
     */
    public GroupCode selectGroupCodeEdit(GroupCode groupCode) {
        GroupCode groupCodeEdit = groupCodeMapper.selectGroupCodeEdit(groupCode);

        return groupCodeEdit;
    }

    /**
     * 시스템 관리자용 그룹코드 등록
     * @return
     */
    public boolean updateGroupCode(GroupCode groupCode) {
        return groupCodeMapper.updateGroupCode(groupCode) > 0 ;
    }

}