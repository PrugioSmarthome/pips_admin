package com.daewooenc.pips.admin.web.domain.dto.facility;

import java.util.Date;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-09-20       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-09-20
 **/
public class FacilityBizcoExcel {

    // 시설물 업체 유형 명
    private String facltBizcoTpNm;
    // 대공종 명
    private String twbsNm;
    // 업체 명
    private String bizcoNm;
    // 공사 내용
    private String conCont;
    // 담당자 명
    private String perchrgNm;
    // 사무실 전화 번호
    private String offcPhoneNo;
    // 팩스 번호
    private String faxNo;
    // 휴대폰 번호
    private String mphoneNo;





    public String getFacltBizcoTpNm() {
        return facltBizcoTpNm;
    }

    public void setFacltBizcoTpNm(String facltBizcoTpNm) {
        this.facltBizcoTpNm = facltBizcoTpNm;
    }

    public String getTwbsNm() {
        return twbsNm;
    }

    public void setTwbsNm(String twbsNm) {
        this.twbsNm = twbsNm;
    }

    public String getBizcoNm() {
        return bizcoNm;
    }

    public void setBizcoNm(String bizcoNm) {
        this.bizcoNm = bizcoNm;
    }

    public String getOffcPhoneNo() {
        return offcPhoneNo;
    }

    public void setOffcPhoneNo(String offcPhoneNo) {
        this.offcPhoneNo = offcPhoneNo;
    }

    public String getMphoneNo() {
        return mphoneNo;
    }

    public void setMphoneNo(String mphoneNo) {
        this.mphoneNo = mphoneNo;
    }

    public String getConCont() {
        return conCont;
    }

    public void setConCont(String conCont) {
        this.conCont = conCont;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getPerchrgNm() {
        return perchrgNm;
    }

    public void setPerchrgNm(String perchrgNm) {
        this.perchrgNm = perchrgNm;
    }
}