package com.daewooenc.pips.admin.web.domain.dto.reservation;

public class ReservationData {
    private String hsholdId;
    private String pushType;
    private String crerId;
    private String resrvCtlId;
    private String pushCtl;
    private String resrvCtlReptYn;
    private String houscplxCd;
    private String cnt;
    private int page;


    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    public String getPushType() { return pushType; }

    public void setPushType(String pushType) { this.pushType = pushType; }

    public String getCrerId() { return crerId; }

    public void setCrerId(String crerId) { this.crerId = crerId; }

    public String getResrvCtlId() { return resrvCtlId; }

    public void setResrvCtlId(String resrvCtlId) { this.resrvCtlId = resrvCtlId; }

    public String getPushCtl() { return pushCtl; }

    public void setPushCtl(String pushCtl) { this.pushCtl = pushCtl; }

    public String getResrvCtlReptYn() { return resrvCtlReptYn; }

    public void setResrvCtlReptYn(String resrvCtlReptYn) { this.resrvCtlReptYn = resrvCtlReptYn; }

    public String getHouscplxCd() { return houscplxCd; }

    public void setHouscplxCd(String houscplxCd) { this.houscplxCd = houscplxCd; }

    public String getCnt() { return cnt; }

    public void setCnt(String cnt) { this.cnt = cnt; }

    public int getPage() { return page; }

    public void setPage(int page) { this.page = page; }

}