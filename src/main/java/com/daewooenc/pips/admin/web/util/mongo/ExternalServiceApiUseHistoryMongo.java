package com.daewooenc.pips.admin.web.util.mongo;

import com.daewooenc.pips.admin.web.common.WebConsts;
import com.daewooenc.pips.admin.web.domain.dto.mongo.ExternalApiUseHistory;
import com.daewooenc.pips.admin.web.domain.vo.mongodb.ExternalServiceApiUseConditionVo;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-09-03       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-09-03
 **/
@Component
public class ExternalServiceApiUseHistoryMongo {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    @Resource(name="mongoTemplate")
    private MongoTemplate mongoTemplate;

    // 외부 서비스 API 사용 이력 조회
    public List<ExternalApiUseHistory> selectExternalServiceApiUseHistory(ExternalServiceApiUseConditionVo externalServiceApiUseConditionVo) {
        List<ExternalApiUseHistory> externalApiUseHistoryList = null;
        try {
            Query query = new Query();
            String hshold_id = null;
            String sortKey = "";
            Sort.Direction order = null;
            // 시간 검색 조건
            // 시간 검색 조건
            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getStartDate()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getStartDate()) && externalServiceApiUseConditionVo.getStartDate() != null &&
                    !"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getEndDate()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getEndDate()) && externalServiceApiUseConditionVo.getEndDate() != null) {
                query.addCriteria(new Criteria("cr_dt").gte(externalServiceApiUseConditionVo.getStartDate()).lte(externalServiceApiUseConditionVo.getEndDate()));
            } else {
                if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getStartDate()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getStartDate()) && externalServiceApiUseConditionVo.getStartDate() != null) {
                    query.addCriteria(new Criteria("cr_dt").gte(externalServiceApiUseConditionVo.getStartDate()));
                }
                if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getEndDate()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getEndDate()) && externalServiceApiUseConditionVo.getEndDate() != null) {
                    query.addCriteria(new Criteria("cr_dt").lte(externalServiceApiUseConditionVo.getEndDate()));
                }
            }
            // 사용자 ID 검색 조건
            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getUserId()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getUserId()) && externalServiceApiUseConditionVo.getUserId() != null) {
                query.addCriteria(new Criteria("user_id").all(externalServiceApiUseConditionVo.getUserId()));
            }
            // 사용자 Type 검색 조건
            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getUserType()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getUserType()) && externalServiceApiUseConditionVo.getUserType() != null) {
                query.addCriteria(new Criteria("user_tp_cd").all(externalServiceApiUseConditionVo.getUserType()));
            }
            // 단지 정보 검색 조건
            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getComplexCode()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getComplexCode()) && externalServiceApiUseConditionVo.getComplexCode() != null) {
                query.addCriteria(new Criteria("houscplx_cd").all(externalServiceApiUseConditionVo.getComplexCode()));
            }

            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getDongNo()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getDongNo()) && externalServiceApiUseConditionVo.getDongNo() != null)
            {
                query.addCriteria(Criteria.where("dong_no").all(externalServiceApiUseConditionVo.getDongNo()));
            }

            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getHoseNo()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getHoseNo()) && externalServiceApiUseConditionVo.getHoseNo() != null)
            {
                query.addCriteria(Criteria.where("hose_no").all(externalServiceApiUseConditionVo.getHoseNo()));
            }


            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getSvcTpCd()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getSvcTpCd()) && externalServiceApiUseConditionVo.getSvcTpCd() != null)
            {
                query.addCriteria(Criteria.where("svc_tp_cd").all(externalServiceApiUseConditionVo.getSvcTpCd()));
            }

            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getSvcTpDtlCd()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getSvcTpDtlCd()) && externalServiceApiUseConditionVo.getSvcTpDtlCd() != null)
            {
                query.addCriteria(Criteria.where("svc_tp_dtl_cd").all(externalServiceApiUseConditionVo.getSvcTpDtlCd()));
            }

            if (!"".equalsIgnoreCase(externalServiceApiUseConditionVo.getSortKey()) && externalServiceApiUseConditionVo.getSortKey() != null) {
                sortKey = externalServiceApiUseConditionVo.getSortKey();
            }  else {
                sortKey = "cr_dt";
            }
            if (!"".equalsIgnoreCase(externalServiceApiUseConditionVo.getOrder()) && externalServiceApiUseConditionVo.getOrder() != null) {
                if ("ASC".equalsIgnoreCase(externalServiceApiUseConditionVo.getOrder())) {
                    order = Sort.Direction.ASC;
                } else {
                    order = Sort.Direction.DESC;
                }
            } else {
                order = Sort.Direction.DESC;
            }

            logger.debug("queryfkdkd : " + query.toString());

            query.with(new Sort(order, sortKey));

            if (externalServiceApiUseConditionVo.getPage() > 0) {
                query.skip((externalServiceApiUseConditionVo.getPage() - 1) * 1);
            } else {
                query.skip(0);
            }
            query.limit(externalServiceApiUseConditionVo.getPageForCnt());
            logger.debug("############################################query : "+query.toString());
            externalApiUseHistoryList = mongoTemplate.find(query, ExternalApiUseHistory.class,WebConsts.SVC_API_HIST);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return externalApiUseHistoryList;
    }
    // 외부 서비스 API 사용 이력 count
    public long countExternalServiceApiUseHistory(ExternalServiceApiUseConditionVo externalServiceApiUseConditionVo) {
        long count = 0;
        try {
            Query query = new Query();
            String hshold_id = null;
            // 시간 검색 조건
            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getStartDate()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getStartDate()) && externalServiceApiUseConditionVo.getStartDate() != null &&
                    !"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getEndDate()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getEndDate()) && externalServiceApiUseConditionVo.getEndDate() != null) {
                query.addCriteria(new Criteria("cr_dt").gte(externalServiceApiUseConditionVo.getStartDate()).lte(externalServiceApiUseConditionVo.getEndDate()));
            } else {
                if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getStartDate()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getStartDate()) && externalServiceApiUseConditionVo.getStartDate() != null) {
                    query.addCriteria(new Criteria("cr_dt").gte(externalServiceApiUseConditionVo.getStartDate()));
                }
                if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getEndDate()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getEndDate()) && externalServiceApiUseConditionVo.getEndDate() != null) {
                    query.addCriteria(new Criteria("cr_dt").lte(externalServiceApiUseConditionVo.getEndDate()));
                }
            }
            // 사용자 ID 검색 조건
            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getUserId()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getUserId()) && externalServiceApiUseConditionVo.getUserId() != null) {
                query.addCriteria(new Criteria("user_id").all(externalServiceApiUseConditionVo.getUserId()));
            }
            // 사용자 Type 검색 조건
            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getUserType()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getUserType()) && externalServiceApiUseConditionVo.getUserType() != null) {
                query.addCriteria(new Criteria("user_tp_cd").all(externalServiceApiUseConditionVo.getUserType()));
            }
            // 단지 정보 검색 조건
            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getComplexCode()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getComplexCode()) && externalServiceApiUseConditionVo.getComplexCode() != null) {
                query.addCriteria(new Criteria("houscplx_cd").all(externalServiceApiUseConditionVo.getComplexCode()));
            }

            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getDongNo()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getDongNo()) && externalServiceApiUseConditionVo.getDongNo() != null)
            {
                query.addCriteria(Criteria.where("dong_no").all(externalServiceApiUseConditionVo.getDongNo()));
            }

            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getHoseNo()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getHoseNo()) && externalServiceApiUseConditionVo.getHoseNo() != null)
            {
                query.addCriteria(Criteria.where("hose_no").all(externalServiceApiUseConditionVo.getHoseNo()));
            }


            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getSvcTpCd()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getSvcTpCd()) && externalServiceApiUseConditionVo.getSvcTpCd() != null)
            {
                query.addCriteria(Criteria.where("svc_tp_cd").all(externalServiceApiUseConditionVo.getSvcTpCd()));
            }

            if (!"ALL".equalsIgnoreCase(externalServiceApiUseConditionVo.getSvcTpDtlCd()) && !"".equalsIgnoreCase(externalServiceApiUseConditionVo.getSvcTpDtlCd()) && externalServiceApiUseConditionVo.getSvcTpDtlCd() != null)
            {
                query.addCriteria(Criteria.where("svc_tp_dtl_cd").all(externalServiceApiUseConditionVo.getSvcTpDtlCd()));
            }

            logger.debug("############################################query : "+query.toString());

            count = mongoTemplate.count(query,WebConsts.SVC_API_HIST);

            logger.debug("############################################count result : "+count);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }
}