package com.daewooenc.pips.admin.web.service.mongo;


import com.daewooenc.pips.admin.web.common.WebConsts;
import com.daewooenc.pips.admin.web.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2021-08-31       :       dokim        :                  <br/>
 *
 * </pre>
 * @since : 2021-08-31
 **/
@Service
public class MongoService {

    /**
     * 로그 출력.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private @Value("${mongo.collection.name}") String collectionName;
    private @Value("${mongo.drop.day}") int dropDay;

    @Autowired
    private MongoTemplate mongodb;

    public void mongoControl() {

        // 날짜 계산
        Calendar cal = Calendar.getInstance();
        String today = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, cal.getTime());
        String dropDate = DateUtil.AddDate(today, 0, 0, dropDay);

        // 전체 컬렉션
        Set<String> allCollections = mongodb.getCollectionNames();
        Set<String> filteredCollections = new HashSet<>();

        // 컬렉션 필터링
        for(String collections : allCollections) {
            if(collections.endsWith(collectionName)) {
                filteredCollections.add(collections);
            }
        }

        for(String collections : filteredCollections) {
            String[] collectionDate = collections.split("\\.");

            // 기준 날짜거나 이전이면 컬렉션 drop
            if(DateUtil.campareDate(collectionDate[1], dropDate)) {
                logger.info("MongoDB dropCollectionName: " + collections);
                if(collections.equals("push.20211014.push.send.hist")){
                    mongodb.dropCollection(collections);
                }
            }
        }

    }
}
