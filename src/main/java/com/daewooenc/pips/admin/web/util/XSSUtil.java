package com.daewooenc.pips.admin.web.util;

import org.springframework.stereotype.Component;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-11-25      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-11-25
 **/
@Component
public class XSSUtil {
    public String replaceAll(String param) {
        param = replaceSpecialCharacter(param);
//        param = replaceSpecialCharacterOrg(param);
        return replaceTag(param);
    }
    public String replaceAllOrg(String param) {
        param = replaceSpecialCharacterOrg(param);
        return replaceTag(param);
    }

    public String replaceLineBreakParam(String param) {
        return param.replaceAll("\r", "").replaceAll("\n", "");
    }

    public String replaceSpecialCharacter(String param) {
        return param.replaceAll("%", "#x69;")
                .replaceAll("&", "amp;")
                .replaceAll("<", "lt;")
                .replaceAll(">", "gt;")
                .replaceAll("\\(", "#40;")
                .replaceAll("\\)", "#41;")
                .replaceAll("'", "#x27;");
    }
    public String replaceSpecialCharacterOrg(String param) {

        return param.replaceAll("amp;", "&")
                .replaceAll("lt;", "<")
                .replaceAll("gt;", ">")
                .replaceAll("#40;", "\\(")
                .replaceAll("#41;", "\\)")
                .replaceAll("#x27;", "'")
                .replaceAll("#x69;", "%")
                ;
    }

    public String replaceTag(String param) {
        return param.replaceAll("eval\\((.*)\\)", "")
                .replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"")
                .replaceAll("(?i)<script.*?>.*?<script.*?>", "")
                .replaceAll("(?i)<script.*?>.*?</script.*?>", "")
                .replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "")
                .replaceAll("(?i)<style.*?>.*?<style.*?>", "")
                .replaceAll("(?i)<style.*?>.*?</style.*?>", "")
                .replaceAll("(?i)<.*?stylesheet:.*?>.*?</.*?>", "")
                .replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "")
                .replaceAll("<(\"[^\"]*\"|'[^']*'|[^'\">])*>", "");
    }
}