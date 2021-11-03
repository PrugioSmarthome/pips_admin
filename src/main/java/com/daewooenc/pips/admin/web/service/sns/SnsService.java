package com.daewooenc.pips.admin.web.service.sns;

import com.daewooenc.pips.admin.web.dao.servicelink.ServiceLinkMapper;
import com.daewooenc.pips.admin.web.dao.sns.SnsMapper;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLink;
import com.daewooenc.pips.admin.web.domain.dto.sns.CommunicationAuthCode;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

/**
 * 연계 웹/앱 관리 관련 Service
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-23      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-23
 **/
@Service
public class SnsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private @Value("${pips.encrypt.key}") String pipsEncryptKey;

    @Autowired
    private SnsMapper snsMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private XSSUtil xssUtil;

    /**
     * SNS ID 조회
     * @param snsID
     * @return
     */
    public boolean existSnsID(String snsID) {
        return snsMapper.existSnsID(snsID) > 0;
    }
    public boolean electExistSnsID(String snsID, String houscplxCode) {
        return snsMapper.electExistSnsID(snsID, houscplxCode) > 0;
    }
    public String houscplxCdSnsID(String snsID) {
        String houscplxCd = snsMapper.houscplxCdSnsID(snsID);
        return houscplxCd;
    }
    public boolean existNormalID(String snsID, String pwd) {
        return snsMapper.existNormalID(snsID, pwd) > 0;
    }
    public boolean electExistNormalID(String snsID, String pwd, String houscplxCode) {
        return snsMapper.electExistNormalID(snsID, pwd, houscplxCode) > 0;
    }
    public String houscplxCdNormalID(String snsID) {
        String houscplxCd = snsMapper.houscplxCdNormalID(snsID);
        return houscplxCd;
    }
    public boolean insertCommunicationAuthCode(CommunicationAuthCode communicationAuthCode) {
        logger.debug("communicationAuthCode.getoAuthType()="+communicationAuthCode.getoAuthType());
        return snsMapper.insertCommunicationAuthCode(communicationAuthCode) > 0;
    }
    public boolean insertOauthInfo(CommunicationAuthCode communicationAuthCode) {
        return snsMapper.insertOauthInfo(communicationAuthCode) > 0;
    }
    public CommunicationAuthCode selectOauthInfo(String sessionId){
        CommunicationAuthCode oauthInfo = snsMapper.selectOauthInfo(sessionId);
        return oauthInfo;
    }
    public boolean deleteOauthInfo(String sessionId){
        return snsMapper.deleteOauthInfo(sessionId) > 0;
    }
    public boolean selectOauthLinkInfo(CommunicationAuthCode communicationAuthCode) {
        return !(snsMapper.selectOauthLinkInfo(communicationAuthCode) > 0);
    }
    public boolean checkOauthInfo(String state) {
        return !(snsMapper.checkOauthInfo(state) > 0);
    }

}