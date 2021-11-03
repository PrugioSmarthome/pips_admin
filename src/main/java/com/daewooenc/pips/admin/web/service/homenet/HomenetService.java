package com.daewooenc.pips.admin.web.service.homenet;

import com.daewooenc.pips.admin.web.dao.homenet.HomenetMapper;
import com.daewooenc.pips.admin.web.domain.dto.homenet.HomenetSvr;
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
 *       2019-10-10      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-10
 **/
@Service
public class HomenetService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HomenetMapper homenetMapper;

    /**
     * 시스템 및 단지 관리자용 단지등록 및 수정영역에서 홈넷서버 목록조회
     * @return
     */
    public List<HomenetSvr> getHomenetMetaList(HomenetSvr homenetSvr) {
        List<HomenetSvr> homenetMetaList = homenetMapper.selectHomenetMetaList(homenetSvr);

        return homenetMetaList;
    }

    /**
     * 시스템 및 단지 관리자용 사용되고 있는 홈넷 체크
     * @param homenetSvr
     * @return
     */
    public HomenetSvr checkUsedHomenet(HomenetSvr homenetSvr) {
        HomenetSvr homenetSvrInfo = homenetMapper.checkUsedHomenet(homenetSvr);

        return homenetSvrInfo;
    }

    public String getHomenetId(String houscplxCd){
        String HomenetId = homenetMapper.getHomenetId(houscplxCd);

        return HomenetId;
    }

    public String getHomenetNm(String hmnetId){
        String hmnetNm = homenetMapper.getHomenetNm(hmnetId);

        return hmnetNm;
    }
}