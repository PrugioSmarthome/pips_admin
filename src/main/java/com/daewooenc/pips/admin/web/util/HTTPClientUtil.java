package com.daewooenc.pips.admin.web.util;

import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-08-07       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-07
 **/
public class HTTPClientUtil {
    /**
     * 로그 출력.
     */
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpResult sendDataForGet(String strUrl, String strData, String serverAuth) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResult httpResult = new HttpResult();
        BufferedReader rd = null;
        String line = "";

        try {
            String requestUrl = strUrl + "?query=" + URLEncoder.encode(strData, "UTF-8");
            HttpGet httpGet = new HttpGet(requestUrl);
            httpGet.addHeader("Authorization", "KakaoAK " + serverAuth); // token 이용시

            HttpResponse response = client.execute(httpGet);

            httpResult.setStatus(response.getStatusLine().getStatusCode() + "");
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder sb = new StringBuilder();

            //Response 출력
            while ((line = rd.readLine()) != null) {
                System.out.println("Response Data = [" + line+"]");
                httpResult.setMessage(line);
            }
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

        return httpResult;
    }

    public HttpResult getSnsProfile(String strUrl, String token, String authType) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResult httpResult = new HttpResult();
        BufferedReader rd = null;
        String line = "";

        StringBuffer sb = new StringBuffer();
        try {
            HttpGet httpGet = new HttpGet(strUrl);
            if ("Bearer".equals(authType)) {
                httpGet.addHeader("Authorization", "Bearer "+token); // token 이용시
            } else if ("basic".equals(authType)) {
                httpGet.addHeader("Authorization", "basic "+token); // token 이용시
            } else {

            }

            HttpResponse response = client.execute(httpGet);

            httpResult.setStatus(response.getStatusLine().getStatusCode() + "");
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            //Response 출력
            while ((line = rd.readLine()) != null) {
                System.out.println("Response Data = [" + line+"]");
                sb.append(line);
            }
            httpResult.setMessage(sb.toString());
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

        return httpResult;
    }

    public HttpResult sendDataForGet(String strUrl) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResult httpResult = new HttpResult();
        BufferedReader rd = null;
        String line = "";

        try {
            String requestUrl = strUrl;
            HttpGet httpGet = new HttpGet(requestUrl);

            HttpResponse response = client.execute(httpGet);

            httpResult.setStatus(response.getStatusLine().getStatusCode() + "");
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder sb = new StringBuilder();

            //Response 출력
            while ((line = rd.readLine()) != null) {
                System.out.println("Response Data = [" + line+"]");
                httpResult.setMessage(line);
            }
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

        return httpResult;
    }

    public HttpResult sendData(String strUrl, String strData, String serverAuth) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResult httpResult = new HttpResult();
        BufferedReader rd = null;
        String line = "";
        try {
            logger.debug("strUrl====================="+strUrl);
            HttpPost httpPost = new HttpPost(strUrl);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpPost.addHeader("Authorization", " Basic " + serverAuth); // token 이용시

            //케릭터셋 인코딩 처리
            httpPost.setEntity(new StringEntity(strData, "UTF-8"));
            httpPost.addHeader("Connection", "close");

            logger.debug("Send Data : [" + strData+"]");
            HttpResponse response = client.execute(httpPost);

            logger.debug("Send Data execution");
            httpResult.setStatus(response.getStatusLine().getStatusCode() + "");
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            //Response 출력
            while ((line = rd.readLine()) != null) {
                logger.debug("Response Data = [" + line+"]");
                httpResult.setMessage(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        return httpResult;
    }
    public HttpResult snsPostData(String strUrl, String kakaoClientId, String redirect_uri, String snsAuthCode, String clientSecret) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResult httpResult = new HttpResult();
        BufferedReader rd = null;
        String line = "";
        StringBuffer sb = new StringBuffer();
        try {

            logger.debug("redirect_uri="+redirect_uri);
            HttpPost httpPost = new HttpPost(strUrl);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("grant_type", "authorization_code"));
            nameValuePairs.add(new BasicNameValuePair("client_id", kakaoClientId));
            nameValuePairs.add(new BasicNameValuePair("redirect_uri", redirect_uri));
            nameValuePairs.add(new BasicNameValuePair("code", snsAuthCode));
            nameValuePairs.add(new BasicNameValuePair("client_secret", clientSecret));


            //케릭터셋 인코딩 처리
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpPost.addHeader("Connection", "close");

            HttpResponse response = client.execute(httpPost);

            logger.debug("Send Data execution");
            httpResult.setStatus(response.getStatusLine().getStatusCode() + "");
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            //Response 출력
            while ((line = rd.readLine()) != null) {
                logger.debug("Response Data = [" + line+"]");
                sb.append(line);
            }
            httpResult.setMessage(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
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
        return httpResult;
    }

    public HttpResult sendDataForPut(String strUrl, String strData, String serverAuth) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResult httpResult = new HttpResult();
        BufferedReader rd = null;
        String line = "";
        try {
            HttpPut httpPut = new HttpPut(strUrl);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpPut.addHeader("Authorization", " Basic " + serverAuth); // token 이용시

            //케릭터셋 인코딩 처리
            httpPut.setEntity(new StringEntity(strData, "UTF-8"));
            httpPut.addHeader("Connection", "close");

            HttpResponse response = client.execute(httpPut);
            logger.debug("Send Data : [%s]", strData);

            httpResult.setStatus(response.getStatusLine().getStatusCode() + "");
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            //Response 출력
            while ((line = rd.readLine()) != null) {
                logger.debug("Response Data [%s]",line);
                httpResult.setMessage(line);
            }
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
        return httpResult;
    }

    public HttpResult sendDataForDelete(String strUrl, String strData, String serverAuth) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResult httpResult = new HttpResult();
        BufferedReader rd = null;
        String line = "";
        try {
            HttpDelete httpDelete = new HttpDelete(strUrl);
            httpDelete.setHeader("Accept", "application/json");
            httpDelete.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpDelete.addHeader("Authorization", " Basic " + serverAuth); // token 이용시

            //케릭터셋 인코딩 처리
            httpDelete.addHeader("Connection", "close");

            HttpResponse response = client.execute(httpDelete);
            logger.debug("Send Data : [%s]", strData);

            httpResult.setStatus(response.getStatusLine().getStatusCode() + "");
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            //Response 출력
            while ((line = rd.readLine()) != null) {
                logger.debug("Response Data [%s]",line);
                httpResult.setMessage(line);
            }
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
        return httpResult;
    }
}