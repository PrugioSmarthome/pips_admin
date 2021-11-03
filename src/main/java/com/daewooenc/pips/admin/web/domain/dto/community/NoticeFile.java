package com.daewooenc.pips.admin.web.domain.dto.community;

import java.util.Date;

/**
 * T_NOTI_ATCH_FILE_DTL
 * 공지 첨부 파일 상세
 * 단지  및 서비스 공지 사항의 첨부 파일 관리 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-07-30      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-07-30
 **/
public class NoticeFile {
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String delYn;
    private int blltNo;
    private String fileNm;
    private String orgnlFileNm;
    private String filePathCont;
    private String blltOrdNo;
    private String fileUrl;
    private String isRemoveFile;

    public Date getCrDt() {
        return crDt;
    }

    public void setCrDt(Date crDt) {
        this.crDt = crDt;
    }

    public Date getEditDt() {
        return editDt;
    }

    public void setEditDt(Date editDt) {
        this.editDt = editDt;
    }

    public String getCrerId() {
        return crerId;
    }

    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    public String getEditerId() {
        return editerId;
    }

    public void setEditerId(String editerId) {
        this.editerId = editerId;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public int getBlltNo() {
        return blltNo;
    }

    public void setBlltNo(int blltNo) {
        this.blltNo = blltNo;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getOrgnlFileNm() {
        return orgnlFileNm;
    }

    public void setOrgnlFileNm(String orgnlFileNm) {
        this.orgnlFileNm = orgnlFileNm;
    }

    public String getFilePathCont() {
        return filePathCont;
    }

    public void setFilePathCont(String filePathCont) {
        this.filePathCont = filePathCont;
    }

    public String getBlltOrdNo() {
        return blltOrdNo;
    }

    public void setBlltOrdNo(String blltOrdNo) {
        this.blltOrdNo = blltOrdNo;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getIsRemoveFile() {
        return isRemoveFile;
    }

    public void setIsRemoveFile(String isRemoveFile) {
        this.isRemoveFile = isRemoveFile;
    }

    @Override
    public String toString() {
        return "NoticeFile{" +
                "crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", blltNo=" + blltNo +
                ", fileNm='" + fileNm + '\'' +
                ", orgnlFileNm='" + orgnlFileNm + '\'' +
                ", filePathCont='" + filePathCont + '\'' +
                ", blltOrdNo='" + blltOrdNo + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", isRemoveFile='" + isRemoveFile + '\'' +
                '}';
    }
}
