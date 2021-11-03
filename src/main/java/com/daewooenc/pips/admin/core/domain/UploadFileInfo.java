package com.daewooenc.pips.admin.core.domain;

/**
 * 파일 정보 저장 Class.
 *
 */
public class UploadFileInfo {

	/**
	 * 파일명.
	 */
	private String fileName;
	
	/**
	 * 파일 확장자.
	 */
	private String fileExt;
	
	/**
	 * 파일 종류.
	 */
	private String fileType;
	
	/**
	 * 파일 크기.
	 */
	private String fileSize; // 파일 크기
	
	/**
	 * 파일 저장명 정보.
	 */
	private String filePathName;
	
	/**
	 * 파일 저장 위치.
	 */
	private String realPath;

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFilePathName() {
		return filePathName;
	}
	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}
	public String getRealPath() {
		return realPath;
	}
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	@Override
	public String toString() {
		return "UploadFileInfo [fileName=" + fileName + ", fileExt="
				+ fileExt + ", fileType=" + fileType + ", fileSize="
				+ fileSize + ", file_path_name=" + filePathName
				+ ", realPath=" + realPath + "]";
	}
}
