package com.daewooenc.pips.admin.web.util.mongo;

import com.daewooenc.pips.admin.web.common.WebConsts;
import com.daewooenc.pips.admin.web.domain.dto.mongo.AppAccessHistory;
import com.daewooenc.pips.admin.web.domain.vo.mongodb.AppAccessHistoryConditionVo;
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
public class AppAccessHistoryMongo {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    @Resource(name="mongoTemplate")
    private MongoTemplate mongoTemplate;

    // 사용자 접속 이력 조회
    public List<AppAccessHistory> selectAppAccessHistory(AppAccessHistoryConditionVo appAccessHistCondition ) {
        List<AppAccessHistory> appAccessHistoryList = null;
        try {
            Query query = new Query();
            String sortKey = "";
            Sort.Direction order = null;
            // 시간 검색 조건
            if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getStartDate()) && !"".equalsIgnoreCase(appAccessHistCondition.getStartDate()) && appAccessHistCondition.getStartDate() != null &&
                    !"ALL".equalsIgnoreCase(appAccessHistCondition.getEndDate()) && !"".equalsIgnoreCase(appAccessHistCondition.getEndDate()) && appAccessHistCondition.getEndDate() != null) {
                query.addCriteria(new Criteria("cr_dt").gte(appAccessHistCondition.getStartDate()).lte(appAccessHistCondition.getEndDate()));
            } else {
                if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getStartDate()) && !"".equalsIgnoreCase(appAccessHistCondition.getStartDate()) && appAccessHistCondition.getStartDate() != null) {
                    query.addCriteria(new Criteria("cr_dt").gte(appAccessHistCondition.getStartDate()));
                }
                if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getEndDate()) && !"".equalsIgnoreCase(appAccessHistCondition.getEndDate()) && appAccessHistCondition.getEndDate() != null) {
                    query.addCriteria(new Criteria("cr_dt").lte(appAccessHistCondition.getEndDate()));
                }
            }
            // 사용자 ID 검색 조건
            if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getUserId()) && !"".equalsIgnoreCase(appAccessHistCondition.getUserId()) && appAccessHistCondition.getUserId() != null) {
                query.addCriteria(new Criteria("user_id").all(appAccessHistCondition.getUserId()));
            }
            // 사용자 Type 검색 조건
            if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getUserType()) && !"".equalsIgnoreCase(appAccessHistCondition.getUserType()) && appAccessHistCondition.getUserType() != null) {
                query.addCriteria(new Criteria("user_tp_cd").all(appAccessHistCondition.getUserType()));
            }
            // 단지 정보 검색 조건
            if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getComplexCode()) && !"".equalsIgnoreCase(appAccessHistCondition.getComplexCode()) && appAccessHistCondition.getComplexCode() != null) {
                query.addCriteria(new Criteria("houscplx_cd").all(appAccessHistCondition.getComplexCode()));
            }

            if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getDongNo()) && !"".equalsIgnoreCase(appAccessHistCondition.getDongNo()) && appAccessHistCondition.getDongNo() != null)
            {
                query.addCriteria(Criteria.where("dong_no").all(appAccessHistCondition.getDongNo()));
            }
            if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getHoseNo()) && !"".equalsIgnoreCase(appAccessHistCondition.getHoseNo()) && appAccessHistCondition.getHoseNo() != null)
            {
                query.addCriteria(Criteria.where("hose_no").all(appAccessHistCondition.getHoseNo()));
            }
            if (!"".equalsIgnoreCase(appAccessHistCondition.getSortKey()) && appAccessHistCondition.getSortKey() != null) {
                sortKey = appAccessHistCondition.getSortKey();
            }  else {
                sortKey = "cr_dt";
            }
            if (!"".equalsIgnoreCase(appAccessHistCondition.getOrder()) && appAccessHistCondition.getOrder() != null) {
                if ("ASC".equalsIgnoreCase(appAccessHistCondition.getOrder())) {
                    order = Sort.Direction.ASC;
                } else {
                    order = Sort.Direction.DESC;
                }
            } else {
                order = Sort.Direction.DESC;
            }

            query.with(new Sort(order, sortKey));

            if (appAccessHistCondition.getPage() > 0) {
                query.skip((appAccessHistCondition.getPage() - 1) * appAccessHistCondition.getPageForCnt());
            } else {
                query.skip(0);
            }
            query.limit(appAccessHistCondition.getPageForCnt());
            logger.debug("############################################query : "+query.toString());
            appAccessHistoryList = mongoTemplate.find(query, AppAccessHistory.class, WebConsts.USER_LOGIN_HIST);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return appAccessHistoryList;
    }
    // 사용자 접속 이력 count
    public long countAppAccessHistory(AppAccessHistoryConditionVo appAccessHistCondition ) {
        long count = 0;
        try {
            Query query = new Query();
            // 시간 검색 조건
            if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getStartDate()) && !"".equalsIgnoreCase(appAccessHistCondition.getStartDate()) && appAccessHistCondition.getStartDate() != null &&
                    !"ALL".equalsIgnoreCase(appAccessHistCondition.getEndDate()) && !"".equalsIgnoreCase(appAccessHistCondition.getEndDate()) && appAccessHistCondition.getEndDate() != null) {
                query.addCriteria(new Criteria("cr_dt").gte(appAccessHistCondition.getStartDate()).lte(appAccessHistCondition.getEndDate()));
            } else {
                if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getStartDate()) && !"".equalsIgnoreCase(appAccessHistCondition.getStartDate()) && appAccessHistCondition.getStartDate() != null) {
                    query.addCriteria(new Criteria("cr_dt").gte(appAccessHistCondition.getStartDate()));
                }
                if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getEndDate()) && !"".equalsIgnoreCase(appAccessHistCondition.getEndDate()) && appAccessHistCondition.getEndDate() != null) {
                    query.addCriteria(new Criteria("cr_dt").lte(appAccessHistCondition.getEndDate()));
                }
            }
            // 사용자 ID 검색 조건
            if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getUserId()) && !"".equalsIgnoreCase(appAccessHistCondition.getUserId()) && appAccessHistCondition.getUserId() != null) {
                query.addCriteria(new Criteria("user_id").all(appAccessHistCondition.getUserId()));
            }
            // 사용자 Type 검색 조건
            if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getUserType()) && !"".equalsIgnoreCase(appAccessHistCondition.getUserType()) && appAccessHistCondition.getUserType() != null) {
                query.addCriteria(new Criteria("user_tp_cd").all(appAccessHistCondition.getUserType()));
            }
            // 단지 정보 검색 조건
            if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getComplexCode()) && !"".equalsIgnoreCase(appAccessHistCondition.getComplexCode()) && appAccessHistCondition.getComplexCode() != null) {
                query.addCriteria(new Criteria("houscplx_cd").all(appAccessHistCondition.getComplexCode()));
            }

            if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getDongNo()) && !"".equalsIgnoreCase(appAccessHistCondition.getDongNo()) && appAccessHistCondition.getDongNo() != null)
            {
                query.addCriteria(Criteria.where("dong_no").all(appAccessHistCondition.getDongNo()));
            }

            if (!"ALL".equalsIgnoreCase(appAccessHistCondition.getHoseNo()) && !"".equalsIgnoreCase(appAccessHistCondition.getHoseNo()) && appAccessHistCondition.getHoseNo() != null)
            {
                query.addCriteria(Criteria.where("hose_no").all(appAccessHistCondition.getHoseNo()));
            }

            logger.debug("############################################query : "+query.toString());
            count = mongoTemplate.count(query,WebConsts.USER_LOGIN_HIST);
            logger.debug("############################################count result : "+count);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }
}