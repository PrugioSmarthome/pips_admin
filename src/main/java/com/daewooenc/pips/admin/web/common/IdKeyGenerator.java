package com.daewooenc.pips.admin.web.common;

import com.daewooenc.pips.admin.web.service.system.platform.PlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-11-17      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-11-17
 **/
@Service
public class IdKeyGenerator {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlatformService platformService;

    /**
     * <PRE>
     * Description : DB를 확인하여 키가 존재하는지 확인
     * <PRE>
     *
     * @param nType : Type
     * @param Id : Id
     * @return : result
     */
    public boolean isExist(int nType, String Id){
        boolean result = false;

        // 발급 구분
        switch (nType) {
            //Platform
            case 0:
                int cnt = platformService.getSystemPlatformIdCheck(Id);
                if(cnt > 0){
                    result = true;
                }
                break;
        }

        return result;
    }

    /**
     * <PRE>
     * Description : id 조회
     * <PRE>
     *
     * @param nType : nType
     * @return : id
     */
    public String getID(int nType) {

        String id = "";
        // 첫번째 Random 값
        String firstRandomValue = "";
        // 두번째 Random 값
        String secondRandomValue = "";

        // Key 발급 종류
        String strType = "";
        // 첫번째 Random 값과 두번째 Random 값을 합친 Random값
        String strRandomValue = "";

        // 발급 구분
        switch (nType) {
            //Platform
            case 0:
                strType = "P";
                break;
        }

        while(true) {

            String strTempValue = "";

            // System.nanoTime()값을 초기값으로하는 난수발생
            Random firstRandom = new Random(System.nanoTime());
            // 0 ~ 999999999 사이의 random 값
            firstRandomValue = firstRandom.nextInt(999999999)+"";

            Random secondRandom = new Random();
            // 0 ~ 999999 사이의 random 값
            secondRandomValue = secondRandom.nextInt(999999)+"";

            strRandomValue = firstRandomValue+secondRandomValue+"";

            // random값을 11자리로 맞춤
            if (strRandomValue.length() < 11) {
                for(int i = 0; i < (11 - strRandomValue.length()); i++) {
                    strTempValue += "0";
                }
                strRandomValue = strTempValue + strRandomValue;
            } else if (strRandomValue.length() > 11) {
                strRandomValue = strRandomValue.substring(0, 11);
            }

            // id 값 취득(12자로 맞춤)
            id = strType + strRandomValue;

            // DB에서 ID가 있는지 조회
            // ID가 없으면 발급된 ID사용하며 있으면 새로 발급
            if (isExist(nType, id) == false) {
                break;
            }
        }
        return id;
    }

    /**
     * <PRE>
     * Description : key 발급
     * <PRE>
     *
     * @param : none
     * @return : id
     */
    public String getKey() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").substring(0, 16);
    }


}