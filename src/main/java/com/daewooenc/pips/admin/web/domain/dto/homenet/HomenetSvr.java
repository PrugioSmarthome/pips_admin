package com.daewooenc.pips.admin.web.domain.dto.homenet;

import java.util.Date;

/**
 * T_HMNET_SVR_BAS
 * 홈넷 서버 기본
 * 홈넷 서버 정보 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-20      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-20
 **/
public class HomenetSvr {
    private String hmnetId;
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String delYn;
    private String hmnetKeyCd;
    private String urlCont;
    private String svrTpCd;
    private String bizcoCd;
    private String useYn;
    private String serlNo;
    private String stsCd;
    private String keepAliveCycleCont;
    private String ctlExprtnCycleCont;
    private String datTrnsmCycleCont;
    private String hmnetNm;

    private int usedCnt;

    public String getHmnetId() {
        return hmnetId;
    }

    public void setHmnetId(String hmnetId) {
        this.hmnetId = hmnetId;
    }

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

    public String getHmnetKeyCd() {
        return hmnetKeyCd;
    }

    public void setHmnetKeyCd(String hmnetKeyCd) {
        this.hmnetKeyCd = hmnetKeyCd;
    }

    public String getUrlCont() {
        return urlCont;
    }

    public void setUrlCont(String urlCont) {
        this.urlCont = urlCont;
    }

    public String getSvrTpCd() {
        return svrTpCd;
    }

    public void setSvrTpCd(String svrTpCd) {
        this.svrTpCd = svrTpCd;
    }

    public String getBizcoCd() {
        return bizcoCd;
    }

    public void setBizcoCd(String bizcoCd) {
        this.bizcoCd = bizcoCd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getSerlNo() {
        return serlNo;
    }

    public void setSerlNo(String serlNo) {
        this.serlNo = serlNo;
    }

    public String getStsCd() {
        return stsCd;
    }

    public void setStsCd(String stsCd) {
        this.stsCd = stsCd;
    }

    public String getKeepAliveCycleCont() {
        return keepAliveCycleCont;
    }

    public void setKeepAliveCycleCont(String keepAliveCycleCont) {
        this.keepAliveCycleCont = keepAliveCycleCont;
    }

    public String getCtlExprtnCycleCont() {
        return ctlExprtnCycleCont;
    }

    public void setCtlExprtnCycleCont(String ctlExprtnCycleCont) {
        this.ctlExprtnCycleCont = ctlExprtnCycleCont;
    }

    public String getDatTrnsmCycleCont() {
        return datTrnsmCycleCont;
    }

    public void setDatTrnsmCycleCont(String datTrnsmCycleCont) {
        this.datTrnsmCycleCont = datTrnsmCycleCont;
    }

    public int getUsedCnt() {
        return usedCnt;
    }

    public void setUsedCnt(int usedCnt) {
        this.usedCnt = usedCnt;
    }

    public String getHmnetNm() {
        return hmnetNm;
    }

    public void setHmnetNm(String hmnetNm) {
        this.hmnetNm = hmnetNm;
    }
}