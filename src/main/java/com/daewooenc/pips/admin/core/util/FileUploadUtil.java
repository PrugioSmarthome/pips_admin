package com.daewooenc.pips.admin.core.util;

import com.daewooenc.pips.admin.core.domain.UploadFileInfo;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 파일 업로드 처리 유틸.
 */
@Component
public class FileUploadUtil {
	
	/**
	 * 로그출력.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

	private static String fileRootPath;

	@Value("${file.rootPath}")
	public void setFileRootPath(String path) {	this.fileRootPath = path; }

	/**
	 * 
	 * 파일 업로드 처리. 
	 *
	 * @param formFile MultipartFile
	 * @param realPath String
	 * @return UploadFileInfo
	 */
	public UploadFileInfo uploadFormFile(final MultipartFile formFile, final String realPath) {
		UUID uuid = UUID.randomUUID();
		String tempFileName = uuid.toString();

		try(
			InputStream stream = formFile.getInputStream();
			OutputStream bos = new FileOutputStream(realPath + "/" + tempFileName)
        ) {
			int bytesRead = 0;
			final byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			logger.info("The file has been written to [{}]", tempFileName);
		} catch (FileNotFoundException e) {
			logger.error("{}", e);
		} catch (IOException e) {
			logger.error("{}", e);
		}
		
		UploadFileInfo f = new UploadFileInfo();
		String ofn = formFile.getOriginalFilename();
		
		f.setFileName(ofn);
		f.setFileSize(String.valueOf(formFile.getSize()));
		f.setFileType(formFile.getContentType());
		f.setFileExt(ofn.indexOf('.') > -1 ? ofn.substring(ofn.lastIndexOf('.')+1):"");
		f.setFilePathName(tempFileName);
		f.setRealPath(realPath);

		return f;
	}

	/**
	 *
	 * Original ImageFile 업로드 처리. (대우건설 스마트홈 플랫폼 전용)
	 *
	 * @param formFile MultipartFile
	 * @param realPath String
	 * @return UploadFileInfo
	 */
	public UploadFileInfo uploadOriginalFile(final MultipartFile formFile, final String realPath, final String fileName) {
		try	(
			InputStream stream = formFile.getInputStream();
			OutputStream bos = new FileOutputStream(realPath + "/" + fileName)
        ) {
			int bytesRead = 0;
			final byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			logger.info("The original file has been written to [{}]", fileName);
		} catch (FileNotFoundException e) {
			logger.error("{}", e);
		} catch (IOException e) {
			logger.error("{}", e);
		}

		UploadFileInfo f = new UploadFileInfo();
		String ofn = formFile.getOriginalFilename();

		f.setFileName(ofn);
		f.setFileSize(String.valueOf(formFile.getSize()));
		f.setFileType(formFile.getContentType());
		f.setFileExt(ofn.indexOf('.') > -1 ? ofn.substring(ofn.lastIndexOf('.')+1):"");
		f.setFilePathName(fileName);
		f.setRealPath(realPath);

		return f;
	}

    /**
     *
	 * Thumbnail ImageFile 업로드 처리. (대우건설 스마트홈 플랫폼 전용)
     *
     * @param formFile File
     * @param realPath String
     * @return UploadFileInfo
     */
	public void uploadThumbnailImageFile(final File formFile, final String realPath, final String fileName,
							   int width, int height, String fileExt) {
		try	{
			BufferedImage originalImage = ImageIO.read(formFile);

			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			BufferedImage resizeImage = resizeImageWithHint(originalImage, type, width, height);

			ImageIO.write(resizeImage, fileExt, new File(realPath + "/" + fileName));

			logger.info("The thumbnail file has been written to [{}]", fileName);
		} catch (FileNotFoundException e) {
			logger.error("{}", e);
		} catch (IOException e) {
			logger.error("{}", e);
		}
	}

	public String makeFileName(boolean hasHouscplxCd, String houscplxCd, String prefix,
							   boolean isOriginal, String originalFileName) {
		String fileName = "";
        String tailFileName = "";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();

		String currentDate = simpleDateFormat.format(date);

		if (BooleanUtils.isTrue(isOriginal)) {
            tailFileName = originalFileName;
        } else {
            UUID uuid = UUID.randomUUID();
            tailFileName = uuid.toString();
        }

		if (BooleanUtils.isTrue(hasHouscplxCd)) {
			fileName = houscplxCd + "_" + prefix + "_" + currentDate + "_" + tailFileName;
		} else {
			fileName = prefix + "_" + currentDate + "_" + tailFileName;
		}

		return fileName;
	}

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(fileRootPath + "/" + multipart.getOriginalFilename());
		multipart.transferTo(convFile);
        return convFile;
    }

	private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type,
													 int width, int height) {

		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}
}