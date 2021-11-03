package com.daewooenc.pips.admin.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-08-20       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-20
 **/
public class ImageUtil {
    /**
     * 로그 출력.
     */
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean resizeImageRate(String inputFilePath, String outputFilePath) {
        OutputStream os = null;
        BufferedImage sourceImage = null;
        int extIndex = inputFilePath.lastIndexOf(".");
        double fixWidth = 300;

        String fileExt = inputFilePath.substring(extIndex+1);
        if (inputFilePath == null || "".equals(inputFilePath)) {
            logger.error("Input File Path is Null");
            return false;
        }
        if (outputFilePath == null || "".equals(outputFilePath)) {
            logger.error("Output File Path is Null");
            return false;
        }
        try {
            sourceImage= ImageIO.read(new File(inputFilePath));

            int srcWidth = sourceImage.getWidth();
            int srcHeight = sourceImage.getHeight();

            if (srcWidth > fixWidth) {

                double rate = fixWidth / srcWidth;

                int width = (int)Math.round(sourceImage.getWidth() * rate) ;

                int height = (int)Math.round(srcHeight * rate);

                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

                Image scaledImage = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

                img.createGraphics().drawImage(scaledImage, 0, 0, null);

                BufferedImage img2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

                img2 = img.getSubimage(0, 0, width, height);

                os = new FileOutputStream(outputFilePath);

                ImageIO.write(img2, fileExt, os);
            } else {
                sourceImage= ImageIO.read(new File(inputFilePath));
                os = new FileOutputStream(outputFilePath);
                ImageIO.write(sourceImage, fileExt, os);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch(IOException ie) {
                ie.printStackTrace();
                return false;
            }
        }
        return true;
    }
}