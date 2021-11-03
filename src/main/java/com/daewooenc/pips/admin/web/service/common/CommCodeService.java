package com.daewooenc.pips.admin.web.service.common;

import com.daewooenc.pips.admin.web.dao.common.CommCodeMapper;
import com.daewooenc.pips.admin.web.domain.dto.common.CommCodeDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 대우건설 스마트홈 푸르지오 플랫폼용 공통코드 관련 Service
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
public class CommCodeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommCodeMapper commCodeMapper;

    /**
     * 대우건설 스마트홈 푸르지오 플랫폼용 그룹공통코드를 통해 세부공통코드 목록조회
     * @link: https://daewooenc.atlassian.net/wiki/spaces/PIPS/pages/16417001/-
     * @param commCodeDetail
     * @return
     */
    public List<CommCodeDetail> getCommCodeList(CommCodeDetail commCodeDetail) {
        List<CommCodeDetail> commCodeDetails = commCodeMapper.selectCommCodeList(commCodeDetail);

        return commCodeDetails;
    }
}