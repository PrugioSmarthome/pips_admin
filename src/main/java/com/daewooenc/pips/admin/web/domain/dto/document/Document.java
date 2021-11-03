package com.daewooenc.pips.admin.web.domain.dto.document;

import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;

import java.util.Date;
import java.util.List;

/**
 * T_LNK_ATCH_FILE_BAS
 * 문서관리 기본
 * 문서관리 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-12-12      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-12-12
 **/
public class Document extends HousingCplx {
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String useYn;
    private String delYn;
    private int lnkAtchFileId;
    private String lnkAtchFileGrpTpCd;
    private String lnkAtchFileTpCd;
    private String fileNm;
    private String orgnlFileNm;
    private String filePathCont;
    private String fileUrl;

    private String startCrDt;
    private String endCrDt;
    private String lnkAtchFileGrpTpCdNm;
    private String lnkAtchFileTpCdNm;

    private String isNewFile;

    private List houscplxList;

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

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public int getLnkAtchFileId() {
        return lnkAtchFileId;
    }

    public void setLnkAtchFileId(int lnkAtchFileId) {
        this.lnkAtchFileId = lnkAtchFileId;
    }

    public String getLnkAtchFileGrpTpCd() {
        return lnkAtchFileGrpTpCd;
    }

    public void setLnkAtchFileGrpTpCd(String lnkAtchFileGrpTpCd) {
        this.lnkAtchFileGrpTpCd = lnkAtchFileGrpTpCd;
    }

    public String getLnkAtchFileTpCd() {
        return lnkAtchFileTpCd;
    }

    public void setLnkAtchFileTpCd(String lnkAtchFileTpCd) {
        this.lnkAtchFileTpCd = lnkAtchFileTpCd;
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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getLnkAtchFileGrpTpCdNm() {
        return lnkAtchFileGrpTpCdNm;
    }

    public void setLnkAtchFileGrpTpCdNm(String lnkAtchFileGrpTpCdNm) {
        this.lnkAtchFileGrpTpCdNm = lnkAtchFileGrpTpCdNm;
    }

    public String getLnkAtchFileTpCdNm() {
        return lnkAtchFileTpCdNm;
    }

    public void setLnkAtchFileTpCdNm(String lnkAtchFileTpCdNm) {
        this.lnkAtchFileTpCdNm = lnkAtchFileTpCdNm;
    }

    public String getStartCrDt() {
        return startCrDt;
    }

    public void setStartCrDt(String startCrDt) {
        this.startCrDt = startCrDt;
    }

    public String getEndCrDt() {
        return endCrDt;
    }

    public void setEndCrDt(String endCrDt) {
        this.endCrDt = endCrDt;
    }

    public String getIsNewFile() {
        return isNewFile;
    }

    public void setIsNewFile(String isNewFile) {
        this.isNewFile = isNewFile;
    }

    public List getHouscplxList() {
        return houscplxList;
    }

    public void setHouscplxList(List houscplxList) {
        this.houscplxList = houscplxList;
    }

    @Override
    public String toString() {
        return "Document{" +
                "crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", useYn='" + useYn + '\'' +
                ", delYn='" + delYn + '\'' +
                ", lnkAtchFileId=" + lnkAtchFileId +
                ", lnkAtchFileGrpTpCd='" + lnkAtchFileGrpTpCd + '\'' +
                ", lnkAtchFileTpCd='" + lnkAtchFileTpCd + '\'' +
                ", fileNm='" + fileNm + '\'' +
                ", orgnlFileNm='" + orgnlFileNm + '\'' +
                ", filePathCont='" + filePathCont + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", startCrDt='" + startCrDt + '\'' +
                ", endCrDt='" + endCrDt + '\'' +
                ", lnkAtchFileGrpTpCdNm='" + lnkAtchFileGrpTpCdNm + '\'' +
                ", lnkAtchFileTpCdNm='" + lnkAtchFileTpCdNm + '\'' +
                ", isNewFile='" + isNewFile + '\'' +
                '}';
    }
}