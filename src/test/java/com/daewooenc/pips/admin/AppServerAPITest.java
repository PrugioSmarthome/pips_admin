package com.daewooenc.pips.admin;

import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import com.daewooenc.pips.admin.web.util.geo.GeoPoint;
import com.daewooenc.pips.admin.web.util.geo.GeoTrans;
import com.daewooenc.pips.admin.web.util.HTTPClientUtil;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.*;

/**
 * 관리자 Web <-> 서비스 서버 API 간의 API 테스트를 위한 임시코드
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-12      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-12
 **/
public class AppServerAPITest {
    public static void main(String[] args) {
        //deviceRegi();
        //exitUser();
        //changeHouseholder();
        //deviceSync();
        //controlIsDataSend();
        //appPush();
        //reportPush();
        //hmnetUseyn();
        //houscplxNoti();
        //sendSMS();
        //searchAddress();
        //posConvert();

        //System.out.println(String.format("%06d", new SecureRandom().nextInt(1000000)));

        //System.out.println(String.format("%06d", 123));
    }

    private static void posConvert() {
//        GeoPoint in_pt = new GeoPoint(126.9403724, 37.5573651);
//        System.out.println("geo in : xGeo="  + in_pt.getX() + ", yGeo=" + in_pt.getY());
//        GeoPoint tm_pt = GeoTrans.convert(GeoTrans.GEO, GeoTrans.UTMK, in_pt);
//        System.out.println("tm : xTM=" + tm_pt.getX() + ", yTM=" + tm_pt.getY());
//        GeoPoint katec_pt = GeoTrans.convert(GeoTrans.TM, GeoTrans.KATEC, tm_pt);
//        System.out.println("katec : xKATEC=" + katec_pt.getX() + ", yKATEC=" + katec_pt.getY());
//        GeoPoint out_pt = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, katec_pt);
//        System.out.println("geo out : xGeo=" + out_pt.getX() + ", yGeo=" + out_pt.getY());
//        GeoPoint in2_pt = new GeoPoint(128., 38.);
//        System.out.println("geo distance between (127,38) and (128,38) =" + GeoTrans.getDistancebyGeo(in_pt, in2_pt) + "km");

        GeoPoint tmPt = new GeoPoint(951683.5016369051, 1951415.8732890524);
        GeoPoint tempT_pt = GeoTrans.convert(GeoTrans.UTMK, GeoTrans.GEO, tmPt);
        System.out.println("geo out : x=" + tempT_pt.getX() + ", y=" + tempT_pt.getY());
    }

    private static void deviceRegi() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", "test1234");
        map.put("hshold_id", "HS001.0001.1101");

        HTTPClientUtil httpClientUtil = new HTTPClientUtil();

        String requestUrl = "http://pips-service:8888/v1/admin/device/regi";
        String paramData = JsonUtil.toJson(map);

        System.out.println(paramData);

        HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, "QTAwMDAwMDAwMDAxOjViZTczYjA4ZjRiOTRjODk=");

        System.out.println(httpResult.getMessage());
        System.out.println(httpResult.getStatus());
    }

    private static void changeHouseholder() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("prev_user_id", "U0000000012");
        map.put("after_user_id", "aaa333");

        HTTPClientUtil httpClientUtil = new HTTPClientUtil();

        String requestUrl = "http://pips-service:8888/v1/admin/family/represent";
        String paramData = JsonUtil.toJson(map);

        System.out.println(paramData);

        HttpResult httpResult = httpClientUtil.sendDataForPut(requestUrl, paramData, "QTAwMDAwMDAwMDAxOjViZTczYjA4ZjRiOTRjODk=");

        System.out.println(httpResult.getMessage());
        System.out.println(httpResult.getStatus());
    }

    private static void exitUser() {
        String type = "MEMBER"; // OR RESIDENT
        List<String> userList = new ArrayList<>();
        String returnData = "";

        userList.add("puremouse14");
        userList.add("puremouse15");

        Map<String, Object> map = new HashMap<>();

        Map<String, String> bodyMap = new HashMap<>();

        for (int i=0; i<userList.size(); i++) {
            bodyMap.put("user_id", userList.get(i));
            bodyMap.put("cancel_tp_cd", type);
            String data = JsonUtil.toJson(bodyMap);

            if (i != 0) {
                returnData += "," + data;
            } else if (i == 0){
                returnData += data;
            }
        }

        String requestUrl = "http://pips-service:8888/v1/admin/user";
        String finalParamData = "[" + returnData + "]";

        System.out.println(finalParamData);

        HTTPClientUtil httpClientUtil = new HTTPClientUtil();

        HttpResult httpResult = httpClientUtil.sendDataForPut(requestUrl, finalParamData, "QTAwMDAwMDAwMDAxOjViZTczYjA4ZjRiOTRjODk=");

        System.out.println(httpResult.getMessage());
        System.out.println(httpResult.getStatus());
    }

    private static void deviceSync() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("admin_id", "admin");
        map.put("hmnet_id", "D41915493463");
        map.put("houscplx_cd", "000003");
        map.put("dong_no", "0205");
        map.put("hose_no", "1703");

        HTTPClientUtil httpClientUtil = new HTTPClientUtil();

        String requestUrl = "http://pips-service:8888/v1/admin/device/sync";
        String paramData = JsonUtil.toJson(map);

        System.out.println(paramData);

        HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, "QTAwMDAwMDAwMDAxOjViZTczYjA4ZjRiOTRjODk=");

        System.out.println(httpResult.getMessage());
        System.out.println(httpResult.getStatus());
    }

    private static void controlIsDataSend() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("admin_id", "admin");
        map.put("hmnet_id", "D41915493463");
        map.put("tgt_tp", "complex");
        map.put("houscplx_cd", "HS001");
        map.put("trnsm_yn", "Y");

        HTTPClientUtil httpClientUtil = new HTTPClientUtil();

        String requestUrl = "http://pips-service:8888/v1/admin/hmnet/conf/send";
        String paramData = JsonUtil.toJson(map);

        System.out.println(paramData);

        HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, "QTAwMDAwMDAwMDAxOjViZTczYjA4ZjRiOTRjODk=");

        System.out.println(httpResult.getMessage());
        System.out.println(httpResult.getStatus());
    }

    private static void appPush() {
        int qstNo = 1;
        String tpCd = "N";
        List<String> houscplxCdList = new ArrayList<>();
        String returnData = "";

        houscplxCdList.add("000002");
        houscplxCdList.add("000003");

        Map<String, String> houscplxCdMap = new HashMap<>();

        for (int i=0; i<houscplxCdList.size(); i++) {
            houscplxCdMap.put("houscplx_cd", houscplxCdList.get(i));
            String data = JsonUtil.toJson(houscplxCdMap);

            if (i != 0) {
                returnData += "," + data;
            } else if (i == 0){
                returnData += data;
            }
        }
        HTTPClientUtil httpClientUtil = new HTTPClientUtil();

        String requestUrl = "http://pips-service:8888/v1/admin/app/push";
        String houscplxList = "[" + returnData + "]";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("no", qstNo);
        map.put("tp_cd", tpCd);
        map.put("houscplx_list", houscplxList);

        String paramData = JsonUtil.toJson(map);

        HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, "QTAwMDAwMDAwMDAxOjViZTczYjA4ZjRiOTRjODk=");

        System.out.println(httpResult.getMessage());
        System.out.println(httpResult.getStatus());
    }

    private static void reportPush() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("bllt_no", "1");
        map.put("user_id", "aaa333");

        HTTPClientUtil httpClientUtil = new HTTPClientUtil();

        String requestUrl = "http://pips-service:8888/v1/admin/report/push";
        String paramData = JsonUtil.toJson(map);

        System.out.println(paramData);

        HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, "QTAwMDAwMDAwMDAxOjViZTczYjA4ZjRiOTRjODk=");

        System.out.println(httpResult.getMessage());
        System.out.println(httpResult.getStatus());
    }

    private static void hmnetUseyn() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("hmnet_id", "D41915493463");
        map.put("hmnet_yse_yn", "N");

        HTTPClientUtil httpClientUtil = new HTTPClientUtil();

        String requestUrl = "http://pips-service:8888/v1/admin/hmnet/useyn";
        String paramData = JsonUtil.toJson(map);

        System.out.println(paramData);

        HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, "QTAwMDAwMDAwMDAxOjViZTczYjA4ZjRiOTRjODk=");

        System.out.println(httpResult.getMessage());
        System.out.println(httpResult.getStatus());
    }

    private static void houscplxNoti() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("houscplx_cd", "000002");
        map.put("bllt_no", "1");

        HTTPClientUtil httpClientUtil = new HTTPClientUtil();

        String requestUrl = "http://pips-service:8888/v1/admin/houscplx/noti";
        String paramData = JsonUtil.toJson(map);

        System.out.println(paramData);

        HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, "QTAwMDAwMDAwMDAxOjViZTczYjA4ZjRiOTRjODk=");

        System.out.println(httpResult.getMessage());
        System.out.println(httpResult.getStatus());
    }

    private static void sendSMS() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnumber", "01067517535");
        map.put("content", "[대우건설 푸르지오 스마트홈 관리자 플랫폼] 본인확인 인증번호[987654]를 화면에 입력해주세요");

        HTTPClientUtil httpClientUtil = new HTTPClientUtil();

        //String requestUrl = "http://localhost:8888/v1/admin/sms";
        String requestUrl = "http://pips-service:8888/v1/admin/sms";
        String paramData = JsonUtil.toJson(map);

        System.out.println(paramData);

        HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, "QTAwMDAwMDAwMDAxOjViZTczYjA4ZjRiOTRjODk=");

        System.out.println(httpResult.getMessage());
        System.out.println(httpResult.getStatus());
    }

    private static void searchAddress() {
        String strUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        String keyword = "전북 익산시 부송동 100";

        HttpClient client = HttpClientBuilder.create().build();
        HttpResult httpResult = new HttpResult();
        BufferedReader rd = null;
        String line = "";
        try {
            String requestUrl = strUrl + "?query=" + URLEncoder.encode(keyword, "UTF-8");
            HttpGet httpGet = new HttpGet(requestUrl);
            //httpGet.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            httpGet.addHeader("Authorization", "KakaoAK d71f580141dd44f5e9fc48cf0aedb865"); // token 이용시

            HttpResponse response = client.execute(httpGet);

            httpResult.setStatus(response.getStatusLine().getStatusCode() + "");
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder sb = new StringBuilder();

            //Response 출력
            while ((line = rd.readLine()) != null) {
                System.out.println("Response Data = [" + line+"]");
                httpResult.setMessage(line);
                sb.append(line);
            }

            JSONObject resultJson = new JSONObject(sb.toString());

            System.out.println(resultJson.toString());
        } catch (Exception ex) {
            httpResult = new HttpResult("exception", ex.getMessage());
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }

    private static void sendSMSAPI() {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResult httpResult = new HttpResult();
        BufferedReader rd = null;
        String line = "";
        try {
            HttpPost httpPost = new HttpPost("https://webservice.daewooenc.com:7444/etls/v1/sms/send");
            httpPost.setHeader("content-type", "application/json+sua");
            httpPost.setHeader("dwenc-token", "55ee28d1f3cc88dceef01f97df7bebc5a63a365659c7deb3a5d5768c3bf77321d2080c9cdefd398368a5448b9e16a250f2df6d849447fa822918183611345"); // token 이용시

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("rnumber", "01067517535");
            paramMap.put("snumber", "15771000");
            paramMap.put("syscd", "pips");
            paramMap.put("content", "[대우건설 푸르지오 스마트홈 관리자 플랫폼] 본인확인 인증번호[123456]를 화면에 입력해주세요");

            Map<String, Object> wrapperMap = new HashMap<>();
            wrapperMap.put("_param", paramMap);

            String paramData = JsonUtil.toJson(wrapperMap);

            //케릭터셋 인코딩 처리
            httpPost.setEntity(new StringEntity(paramData, "UTF-8"));

            HttpResponse response = client.execute(httpPost);

            httpResult.setStatus(response.getStatusLine().getStatusCode() + "");
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            //Response 출력
            while ((line = rd.readLine()) != null) {
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
    }
}