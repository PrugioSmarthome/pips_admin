package com.daewooenc.pips.admin.web.domain.vo.servermgmt;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

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
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonPropertyOrder({"hmnetId", "hmnetNm", "hmnetKeyCd", "svrTpCd", "bizcoCd", "serlNo", "urlCont", "stsCd", "keepAliveCycleCont", "datTrnsmCycleCont", "ctlExprtnCycleCont",
        "useYn", "delYn", "crerId", "editerId", "crDt", "editDt"})
public class ServerMgmtVo {
    @JsonProperty("hmnetId") @JsonSerialize(using = ToStringSerializer.class)
    private String hmnetId;

    @JsonProperty("hmnetNm") @JsonSerialize(using = ToStringSerializer.class)
    private String hmnetNm;

    @JsonProperty("hmnetKeyCd") @JsonSerialize(using = ToStringSerializer.class)
    private String hmnetKeyCd;

    @JsonProperty("svrTpCd") @JsonSerialize(using = ToStringSerializer.class)
    private String svrTpCd;

    @JsonProperty("serlNo") @JsonSerialize(using = ToStringSerializer.class)
    private String serlNo;

    @JsonProperty("bizcoCd") @JsonSerialize(using = ToStringSerializer.class)
    private String bizcoCd;

    @JsonProperty("urlCont") @JsonSerialize(using = ToStringSerializer.class)
    private String urlCont;

    @JsonProperty("stsCd") @JsonSerialize(using = ToStringSerializer.class)
    private String stsCd;

    @JsonProperty("keepAliveCycleCont") @JsonSerialize(using = ToStringSerializer.class)
    private String keepAliveCycleCont;

    @JsonProperty("datTrnsmCycleCont") @JsonSerialize(using = ToStringSerializer.class)
    private String datTrnsmCycleCont;

    @JsonProperty("ctlExprtnCycleCont") @JsonSerialize(using = ToStringSerializer.class)
    private String ctlExprtnCycleCont;

    @JsonProperty("delYn") @JsonSerialize(using = ToStringSerializer.class)
    private String delYn;

    @JsonProperty("crerId") @JsonSerialize(using = ToStringSerializer.class)
    private String crerId;

    @JsonProperty("editerId") @JsonSerialize(using = ToStringSerializer.class)
    private String editerId;

    @JsonProperty("crDt") @JsonSerialize(using = ToStringSerializer.class)
    private String crDt;

    @JsonProperty("editDt") @JsonSerialize(using = ToStringSerializer.class)
    private String editDt;

    @JsonProperty("useYn") @JsonSerialize(using = ToStringSerializer.class)
    private String useYn;

    public String getHmnetId() {
        return hmnetId;
    }

    public void setHmnetId(String hmnetId) {
        this.hmnetId = hmnetId;
    }

    public String getHmnetNm() {
        return hmnetNm;
    }

    public void setHmnetNm(String hmnetNm) {
        this.hmnetNm = hmnetNm;
    }

    public String getHmnetKeyCd() {
        return hmnetKeyCd;
    }

    public void setHmnetKeyCd(String hmnetKeyCd) {
        this.hmnetKeyCd = hmnetKeyCd;
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

    public String getUrlCont() {
        return urlCont;
    }

    public void setUrlCont(String urlCont) {
        this.urlCont = urlCont;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
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

    public String getCrDt() {
        return crDt;
    }

    public void setCrDt(String crDt) {
        this.crDt = crDt;
    }

    public String getEditDt() {
        return editDt;
    }

    public void setEditDt(String editDt) {
        this.editDt = editDt;
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

    public String getDatTrnsmCycleCont() {
        return datTrnsmCycleCont;
    }

    public void setDatTrnsmCycleCont(String datTrnsmCycleCont) {
        this.datTrnsmCycleCont = datTrnsmCycleCont;
    }

    public String getCtlExprtnCycleCont() {
        return ctlExprtnCycleCont;
    }

    public void setCtlExprtnCycleCont(String ctlExprtnCycleCont) {
        this.ctlExprtnCycleCont = ctlExprtnCycleCont;
    }
}