package com.daewooenc.pips.admin.web.domain.dto.household;

import java.util.Date;

/**
 * T_HSHOLD_NTC_HST
 * 세대 알림 이력
 * 세대별 알림 이력 정보 관리 테이블 맵핑 DAO
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
public class HouseholdNotiHist {
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String delYn;
    private String ntcMsgId;
    private String ntcTpCd;
    private String cont;
    private String hsholdId;

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

    public String getNtcMsgId() {
        return ntcMsgId;
    }

    public void setNtcMsgId(String ntcMsgId) {
        this.ntcMsgId = ntcMsgId;
    }

    public String getNtcTpCd() {
        return ntcTpCd;
    }

    public void setNtcTpCd(String ntcTpCd) {
        this.ntcTpCd = ntcTpCd;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    @Override
    public String toString() {
        return "HouseholdNotiHist{" +
                "crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", ntcMsgId='" + ntcMsgId + '\'' +
                ", ntcTpCd='" + ntcTpCd + '\'' +
                ", cont='" + cont + '\'' +
                ", hsholdId='" + hsholdId + '\'' +
                '}';
    }
}
