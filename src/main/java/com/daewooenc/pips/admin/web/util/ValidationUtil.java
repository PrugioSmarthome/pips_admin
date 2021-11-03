package com.daewooenc.pips.admin.web.util;

import org.apache.commons.io.FilenameUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-11-05      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-11-05
 **/
public class ValidationUtil {
    public static final String DOC_PATTERN = "(.*/)*.+\\.(pdf|PDF)$";
    public static final String IMAGE_PATTERN = "(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$";
    public static final String FILE_PATTERN = "(.*/)*.+\\.(hwp|txt|doc|docx|xls|xlsx|ppt|pptx|pdf|HWP|TXT|DOC|DOCX|XLS|XLSX|PPT|PPTX|PDF)$";

    /**
     * Validate image with regular expression
     * @return true valid image, false invalid image
     */
    public static boolean validate(String fileName, String filePattern){
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(filePattern);
        matcher = pattern.matcher(fileName);
        return matcher.matches();
    }
}