package com.daewooenc.pips.admin.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class EncUtil {

    private static Logger logger = LoggerFactory.getLogger(EncUtil.class);
    private static String encKey = "8KTJnkBq73vTx0PleUdD7NUqMo+gRXwm9RBYMGA=";

    public static void initEncKey(String value) {
        encKey = value;
    }

    public static String encryptValue() {
        return "to_base64(aes_encrypt(?, sha2('" + encKey + "', 512)))";
        //return "aes_encrypt(?, sha2('" + encKey + "', 512))";
    }

    public static String encryptValue(String value) {
        return "to_base64(aes_encrypt('" + value + "', sha2('" + encKey + "', 512)))";
        //return "aes_encrypt('" + value + "', sha2('" + encKey + "', 512))";
    }

    public static String decryptValue(String value) {
        return "cast(aes_decrypt(from_base64(" + value + "), sha2('" + encKey + "', 512)) as char(128))";
        //return "cast(aes_decrypt(" + value + ", sha2('" + encKey + "', 512)) as char(128)) ";
    }

    public static String hashValue(String strMsg) {
        StringBuilder sb = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(strMsg.getBytes());

            for (byte b : md.digest()) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception e) {
            logger.error("Error : ", e);
        }

        return sb.toString();
    }

}
