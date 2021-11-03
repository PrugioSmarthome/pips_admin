package com.daewooenc.pips.admin.web.service.household;

import com.daewooenc.pips.admin.web.dao.household.HouseholdMapper;
import com.daewooenc.pips.admin.web.domain.dto.device.HouseholdDevice;
import com.daewooenc.pips.admin.web.domain.dto.device.HouseholdDeviceDetail;
import com.daewooenc.pips.admin.web.domain.dto.household.Household;
import com.daewooenc.pips.admin.web.domain.dto.household.HouseholdUserItem;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.domain.dto.user.PipsUser;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 세대관리 관련 Service
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
public class HouseholdService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HouseholdMapper householdMapper;

    @Autowired
    private XSSUtil xssUtil;

    private @Value("${pips.encrypt.key}") String pipsEncryptKey;

    /**
     * 시스템 및 단지 관리자용 검색영역에서 동, 호 선택시 기본정보를 조회
     * @param pipsUser
     * @return
     */
    public List<PipsUser> getHouseholdMetaList(PipsUser pipsUser) {
        List<PipsUser> householdList = householdMapper.selectHouseholdMetaList(pipsUser);

        return householdList;
    }

    /**
     * 시스템 및 단지 관리자용 세대장치 목록을 조회
     * @param householdDevice
     * @return
     */
    public List<HouseholdDevice> getHouseholdDeviceList(HouseholdDevice householdDevice) {
        List<HouseholdDevice> householdDeviceList = householdMapper.selectHouseholdDeviceList(householdDevice);

        return householdDeviceList;
    }

    /**
     * 멀티 단지 관리자용 세대장치 목록을 조회
     * @param householdDevice
     * @return
     */
    public List<HouseholdDevice> getMultiHouseholdDeviceList(HouseholdDevice householdDevice) {
        List<HouseholdDevice> householdDeviceList = householdMapper.selectMultiHouseholdDeviceList(householdDevice);

        return householdDeviceList;
    }

    /**
     * 단지 관리자용 입주민탈퇴 가능 여부 체크
     * @param hsholdId
     * @return
     */
    public int getHouseholdSecessionCheck(String hsholdId) {
        int secessionCnt = householdMapper.selectHouseholdSecessionCheck(hsholdId);

        return secessionCnt;
    }

    /**
     * 시스템 및 단지 관리자용 홈넷 아이디 조회
     * @param houscplxCd
     * @return
     */
    public String getHouseholdHmnetId(String houscplxCd) {
        String hmnetId = householdMapper.selectHouseholdHmnetId(houscplxCd);

        return hmnetId;
    }

    /**
     * 시스템 관리자용 세대장치 상세목록을 조회
     * @param householdDeviceDetail
     * @return
     */
    public List<HouseholdDeviceDetail> getHouseholdDeviceDetail(HouseholdDeviceDetail householdDeviceDetail) {
        List<HouseholdDeviceDetail> householdDeviceDetailInfo = householdMapper.selectHouseholdDeviceDetail(householdDeviceDetail);

        return householdDeviceDetailInfo;
    }



    /**
     * (시스템 관리자) 회원 관리 대메뉴 아래 회원정보 관리의 회원정보 목록 조회를 위해 householdMapper로 select 쿼리 요청
     * @param pipsUser
     * @return
     */
    public List<PipsUser> getUserList(PipsUser pipsUser) {
        List<PipsUser> pipsUserList = householdMapper.selectUserList(pipsUser);

        return pipsUserList;
    }

    public List<PipsUser> getHouseholdUserList(PipsUser pipsUser) {
        List<PipsUser> householdUserList = householdMapper.selectHouseholdUserList(pipsUser);

        return householdUserList;
    }

    public List<PipsUser> getMultiHouseholdUserList(PipsUser pipsUser) {
        List<PipsUser> householdUserList = householdMapper.selectMultiHouseholdUserList(pipsUser);

        return householdUserList;
    }

    public PipsUser getHouseholdUserDetailForNormalUser(PipsUser pipsUser) {
        String userId = pipsUser.getUserId();
        pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

        PipsUser householdUserDetail = householdMapper.selectHouseholdUserDetailForNormalUser(userId, pipsEncryptKey);

        return householdUserDetail;
    }

    public PipsUser getHouseholdUserDetail(PipsUser pipsUser) {
        String userId = pipsUser.getUserId();
        pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

        PipsUser householdUserDetail = householdMapper.selectHouseholdUserDetail(userId, pipsEncryptKey);

        return householdUserDetail;
    }

    public List<PipsUser> getHouseholdUserDetailFamilyList(PipsUser pipsUser) {
        List<PipsUser> householdUserDetailFamilyList = householdMapper.selectHouseholdUserDetailFamilyList(pipsUser);

        return householdUserDetailFamilyList;
    }

    public List<PipsUser> getRepresentativeHistory(PipsUser pipsUser) {
        List<PipsUser> representativeHistoryList = householdMapper.selectRepresentativeHistory(pipsUser);

        return representativeHistoryList;
    }

    public List<PipsUser> getHouseholdUserListForUpdateRep(PipsUser pipsUser) {
        List<PipsUser> householdUserListForUpdateRep = householdMapper.selectHouseholdUserListForUpdateRep(pipsUser);

        return householdUserListForUpdateRep;
    }

    /**
     * (시스템 관리자) 회원 관리 대메뉴 아래 세대구성 신청정보의 세대주요청 신청목록 조회를 위해 householdMapper로 select 쿼리 요청
     * @param pipsUser
     * @return
     */
    public List<PipsUser> getHouseholderListForSysAdmin(PipsUser pipsUser) {
        List<PipsUser> pipsHouseholdUserList = householdMapper.selectHouseholderListForSysAdmin(pipsUser);

        return pipsHouseholdUserList;
    }

    public List<PipsUser> getHouseholderMultiList(PipsUser pipsUser) {
        List<PipsUser> pipsHouseholdUserList = householdMapper.selectHouseholderMultiList(pipsUser);

        return pipsHouseholdUserList;
    }

    public List<PipsUser> getHouseholderList(PipsUser pipsUser) {
        List<PipsUser> householderList = householdMapper.selectHouseholderList(pipsUser);

        return householderList;
    }

    public boolean insertHouseholderApprHst(PipsUser pipsUser) {
        return householdMapper.insertHouseholderApprHst(pipsUser) > 0;
    }

    public boolean updateUserForSysAdmin(HouseholdUserItem householdUserItem) {
        return householdMapper.updateUserForSysAdmin(householdUserItem) > 0;
    }

    public boolean updateUser(HouseholdUserItem householdUserItem) {
        return householdMapper.updateUser(householdUserItem) > 0;
    }


    public List<PipsUser> getHouseholdList(PipsUser pipsUser) {
        List<PipsUser> householdList = householdMapper.selectHouseholdList(pipsUser);

        return householdList;
    }

    /**
     * 가족구성원 갯수를 가져오는 쿼리요청
     * @return
     */
    public int getUserCount(PipsUser pipsUser) {
        int userCount = householdMapper.selectUserCount(pipsUser);

        return userCount;
    }
    public List<HouseholdUserItem> getHouseHoldApproveList(String housingCplxCd) {

        return householdMapper.selectHouseHoldApproveList(housingCplxCd);
    }
}