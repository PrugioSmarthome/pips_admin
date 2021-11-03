package com.daewooenc.pips.admin.web.domain.dto.servermgmt;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-08-13       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-13
 **/
public class ServerMgmtCondition {
    private String svrTpCd;
    private String bizcoCd;
    private String stsCd;
    private String hmnetId;
    private String hmnetNm;

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

    public String getStsCd() {
        return stsCd;
    }

    public void setStsCd(String stsCd) {
        this.stsCd = stsCd;
    }

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
}