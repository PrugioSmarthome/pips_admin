package com.daewooenc.pips.admin.web.domain.dto.mongo;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-09-04       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-09-04
 **/

public class ExternalApiUseHistoryItem {

    private String startCrDt;
    private String endCrDt;
    private String userId;
    private String userTpCd;
    private String houscplxCd;
    private String houscplxNm;
    private String svcTpCd;
    private String svcTpDtlCd;
    private int draw;
    private int start;
    private int length;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserTpCd() {
        return userTpCd;
    }

    public void setUserTpCd(String userTpCd) {
        this.userTpCd = userTpCd;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getHouscplxNm() {
        return houscplxNm;
    }

    public void setHouscplxNm(String houscplxNm) {
        this.houscplxNm = houscplxNm;
    }

    public String getSvcTpCd() {
        return svcTpCd;
    }

    public void setSvcTpCd(String svcTpCd) {
        this.svcTpCd = svcTpCd;
    }

    public String getSvcTpDtlCd() {
        return svcTpDtlCd;
    }

    public void setSvcTpDtlCd(String svcTpDtlCd) {
        this.svcTpDtlCd = svcTpDtlCd;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "ExternalApiUseHistoryItem{" +
                "startCrDt='" + startCrDt + '\'' +
                ", endCrDt='" + endCrDt + '\'' +
                ", userId='" + userId + '\'' +
                ", userTpCd='" + userTpCd + '\'' +
                ", houscplxCd='" + houscplxCd + '\'' +
                ", houscplxNm='" + houscplxNm + '\'' +
                ", svcTpCd='" + svcTpCd + '\'' +
                ", svcTpDtlCd='" + svcTpDtlCd + '\'' +
                ", draw=" + draw +
                ", start=" + start +
                ", length=" + length +
                '}';
    }
}