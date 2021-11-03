package com.daewooenc.pips.admin.web.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-10-07      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-07
 **/
public class CommonUtil {

    public String getBrowserHeader(HttpServletRequest request) {
        String header =request.getHeader("User-Agent");

        if (header.contains("MSIE")) {
            return "MSIE";
        } else if(header.contains("Chrome")) {
            return "Chrome";
        } else if(header.contains("Opera")) {
            return "Opera";
        }

        return "Firefox";
    }

    public String getDownloadFileName(String header, String fileName) throws UnsupportedEncodingException {
        String convertedFileName = "";

        if (header.contains("MSIE")) {
            convertedFileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
        } else if (header.contains("Firefox")) {
            convertedFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else if (header.contains("Opera")) {
            convertedFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else if (header.contains("Chrome")) {
            convertedFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }

        return convertedFileName;
    }
}