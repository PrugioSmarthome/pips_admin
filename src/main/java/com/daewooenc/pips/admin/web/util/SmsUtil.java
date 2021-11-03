package com.daewooenc.pips.admin.web.util;

import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
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
public class SmsUtil {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpResult sendSms(String smsUrl, String smsToken, String smsData) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResult httpResult = new HttpResult();
        BufferedReader rd = null;
        String line = "";
        try {
            logger.debug("${pips.smsServer.token}: " + smsToken);

            HttpPost httpPost = new HttpPost(smsUrl);
            httpPost.setHeader("content-type", "application/json+sua");
            httpPost.setHeader("dwenc-token", smsToken); // token 이용시

            //케릭터셋 인코딩 처리
            httpPost.setEntity(new StringEntity(smsData, "UTF-8"));

            logger.debug("Send SMS Data : [" + smsData+"]");
            HttpResponse response = client.execute(httpPost);

            logger.debug("Send SMS Data execution");
            httpResult.setStatus(response.getStatusLine().getStatusCode() + "");
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            //Response 출력
            while ((line = rd.readLine()) != null) {
                logger.debug("Response SMS Data = [" + line+"]");
                httpResult.setMessage(line);
            }
        } catch (Exception ex) {
            httpResult = new HttpResult("sendSms exception", ex.getMessage());
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }
        return httpResult;
    }
}