package com.daewooenc.pips.admin.web.dao.household;

import com.daewooenc.pips.admin.web.domain.dto.device.HouseholdDeviceDetail;
import com.daewooenc.pips.admin.web.domain.dto.device.HouseholdDevice;
import com.daewooenc.pips.admin.web.domain.dto.household.HouseholdUserItem;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.domain.dto.user.PipsUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 세대관리 관련 HouseholdMapper.xml 맵핑 Interface
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
public interface HouseholdMapper {
    /**
     * 공통: 세대(동, 호) 기본정보 조회 (검색영역)
     * @param pipsUser
     * @return
     */
    List<PipsUser> selectHouseholdMetaList(PipsUser pipsUser);

    /**
     * 공통: 세대장치 목록 조회
     * @param householdDevice
     * @return
     */
    List<HouseholdDevice> selectHouseholdDeviceList(HouseholdDevice householdDevice);

    /**
     * 멀티 단지 관리자: 세대장치 목록 조회
     * @param householdDevice
     * @return
     */
    List<HouseholdDevice> selectMultiHouseholdDeviceList(HouseholdDevice householdDevice);

    /**
     * 공통: 세대장치 목록 개수 조회(동기화)
     * @param householdDevice
     * @return
     */
    int selectHouseholdDeviceListCount(HouseholdDevice householdDevice);

    /**
     * 공통: 세대장치 홈넷 아이디 조회
     * @param houscplxCd
     * @return
     */
    String selectHouseholdHmnetId(String houscplxCd);

    /**
     * 시스템 관리자: 세대장치 상세목록 조회
     * @param householdDeviceDetail
     * @return
     */
    List<HouseholdDeviceDetail> selectHouseholdDeviceDetail(HouseholdDeviceDetail householdDeviceDetail);

    /**
     * 가족구성원 갯수를 가져오는 쿼리요청
     * @return
     */
    int selectUserCount(PipsUser pipsUser);



    List<PipsUser> selectHouseholdList(PipsUser pipsUser);

    List<PipsUser> selectUserList(PipsUser pipsUser);

    List<PipsUser> selectHouseholdUserList(PipsUser pipsUser);

    List<PipsUser> selectMultiHouseholdUserList(PipsUser pipsUser);

    PipsUser selectHouseholdUserDetailForNormalUser(@Param("userId") String userId, @Param("encKey") String encKey);

    PipsUser selectHouseholdUserDetail(@Param("userId") String userId, @Param("encKey") String encKey);

    List<PipsUser> selectHouseholdUserDetailFamilyList(PipsUser pipsUser);

    List<PipsUser> selectRepresentativeHistory(PipsUser pipsUser);

    List<PipsUser> selectHouseholdUserListForUpdateRep(PipsUser pipsUser);

    List<PipsUser> selectHouseholderListForSysAdmin(PipsUser pipsUser);

    List<PipsUser> selectHouseholderMultiList(PipsUser pipsUser);

    List<PipsUser> selectHouseholderList(PipsUser pipsUser);

    int insertHouseholderApprHst(PipsUser pipsUser);

    int updateUserForSysAdmin(HouseholdUserItem householdUserItem);

    int selectHouseholdSecessionCheck(String hsholdId);

    int updateUser(HouseholdUserItem householdUserItem);

    int updateHouseholderForApproval();

    int updateHouseholderForReject();

    List<HouseholdUserItem> selectHouseHoldApproveList(@Param("housingCplxCd")  String housingCplxCd);
}
