package com.daewooenc.pips.admin.web.dao.sns;

import com.daewooenc.pips.admin.web.domain.dto.sns.CommunicationAuthCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 날씨 정보 구하는 mapper.
 *
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface SnsMapper {

	/**
	 * SNS ID 갯수 구함
	 *
	 * @return SNS ID 갯수
	 *
	 * 설명 : SNS ID 갯수 구함
	 */
	int existSnsID(@Param("snsId") String snsId);
	int electExistSnsID(@Param("snsId") String snsId, @Param("houscplxCode") String houscplxCode);
	String houscplxCdSnsID(@Param("snsId") String snsId);
	int existNormalID(@Param("userId") String userId, @Param("pwd") String pwd);
	int electExistNormalID(@Param("userId") String userId, @Param("pwd") String pwd, @Param("houscplxCode") String houscplxCode);
	String houscplxCdNormalID(@Param("userId") String userId);
	int insertCommunicationAuthCode(CommunicationAuthCode communicationAuthCode);
	int insertOauthInfo(CommunicationAuthCode communicationAuthCode);
	CommunicationAuthCode selectOauthInfo(String sessionId);
	int deleteOauthInfo(String sessionId);
	int selectOauthLinkInfo(CommunicationAuthCode communicationAuthCode);
	int checkOauthInfo(String state);
}
