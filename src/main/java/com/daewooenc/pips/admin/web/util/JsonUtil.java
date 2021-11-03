package com.daewooenc.pips.admin.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class JsonUtil {
    /** 로그 출력. */
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * <PRE>
     * Description : object를 json형태로 변환
     * <PRE>
     *
     * @param o : o
     * @return : om.writeValueAsString(o)
     */
    public static String toJson(Object o){

        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        try {
            return om.writeValueAsString(o);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex.fillInStackTrace());
        }

        return "";
    }
    /**
     * <PRE>
     * Description : object를 json형태로 변환
     * <PRE>
     *
     * @param o : o
     * @return : om.writeValueAsString(o)
     */
    public static String toJsonNotZero(Object o){

        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);

        try {
            return om.writeValueAsString(o);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex.fillInStackTrace());
        }

        return "";
    }
    /**
     * <PRE>
     * Description : object를 json형태로 변환, maxin 적용
     * <PRE>
     *
     * @param o : o
     * @param o : e
     * @return : om.writeValueAsString(o)
     */
    public static String toJson(Object o, Class target, Class mixinSource){

        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        om.addMixIn(target, mixinSource);

        try {
            return om.writeValueAsString(o);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex.fillInStackTrace());
        }

        return "";
    }
    /**
     * <PRE>
     * Description : String 을 object형태로 변환
     * <PRE>
     *
     * @param json : o
     * @param cls : cls
     * @return : om.readValue(json, cls)
     */
    public static <T> T toObject(String json, Class<T> cls) throws Exception {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om.readValue(json, cls);
    }

    /**
     * <PRE>
     * Description : String 을 object list 형태로 변환
     * <PRE>
     *
     * @param json : json
     * @param cls : cls
     * @return : om.readValue(json, cls)
     */
    public static <T> List<T> toObjectList(String json, Class<T> cls) throws Exception {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om.readValue(json, om.getTypeFactory().constructCollectionType(List.class, cls) );
    }


    /**
     * <PRE>
     * Description : json을 model형태로 변환
     * <PRE>
     *
     * @param source : source
     * @param clazz : clazz
     * @return : toJson(model)
     */
    public static <Model> String jsonToModel(String source, Class<Model> clazz) throws Exception {

        JSONArray jArray = new JSONArray(source);

        Model model= JsonUtil.toObject(jArray.getJSONObject(0).toString(), clazz);

        return toJson(model);

    }

    /**
     * <PRE>
     * Description : json array를 model형태로 변환
     * <PRE>
     *
     * @param source : source
     * @param clazz : clazz
     * @return : sb.toString()
     */
    public static <Model> String jsonArrayToModel(String source, Class<Model> clazz) throws Exception {

        JSONArray jArray = new JSONArray(source);
        StringBuilder sb = new StringBuilder();

        sb.append("[ ");

        for (int i = 0; i < jArray.length(); i++) {
            Model model = JsonUtil.toObject(jArray.getJSONObject(i).toString(), clazz);
            sb.append(toJson(model));
            if (i != jArray.length() - 1) sb.append(", ");
        }

        sb.append(" ]");

        return sb.toString();

    }
}