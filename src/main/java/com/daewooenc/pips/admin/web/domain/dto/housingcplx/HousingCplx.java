package com.daewooenc.pips.admin.web.domain.dto.housingcplx;

import com.daewooenc.pips.admin.web.domain.dto.household.Household;

import java.util.Date;

/**
 * T_HOUSCPLX_BAS
 * 단지 기본
 * 단지 정보 테이블 맵핑 DAO
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
public class HousingCplx {
    private String houscplxCd;
    private Date crDt;
    private Date editDt;
    private int qryRangeCont;
    private String roadnmBasAddrCont;
    private String latudCont;
    private String lotudCont;
    private String houscplxNm;
    private String screenHouscplxNm;
    private String crerId;
    private String editerId;
    private String delYn;
    private String hmnetId;
    private String postNo;

    private String areaBasAddrCont;
    private String landDimQty;
    private String archDimQty;
    private String busiApprYmd;
    private String busiConendYmd;
    private int wholeHsholdCnt;
    private int wholeDongCnt;
    private String heatTpCd;
    private int lwstUngrFlrcnt;
    private int hgstAbgrFlrcnt;
    private String addrSiGunGuNm;
    private String addrSiDoNm;
    private String addrEupMyeonDongNm;
    private String enrgMeasYmd;
    private int wrMeasXCoorNo;
    private int wrMeasYCoorNo;
    private int wholeWlLcnt;
    private String excwkBizcoNm;
    private String hsholdWlLcnt;

    private String heatTpNm;
    private String bizcoCd;
    private String bizcoNm;
    private String hmnetNm;
    private String svrTpCd;
    private String lnkSvcNm;
    private String lnkSvcId;
    private String userId;
    private int cnt;

    private String startCrDt;
    private String endCrDt;

    private String banrLnkCls;

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
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

    public int getQryRangeCont() {
        return qryRangeCont;
    }

    public void setQryRangeCont(int qryRangeCont) {
        this.qryRangeCont = qryRangeCont;
    }

    public String getRoadnmBasAddrCont() {
        return roadnmBasAddrCont;
    }

    public void setRoadnmBasAddrCont(String roadnmBasAddrCont) {
        this.roadnmBasAddrCont = roadnmBasAddrCont;
    }

    public String getLatudCont() {
        return latudCont;
    }

    public void setLatudCont(String latudCont) {
        this.latudCont = latudCont;
    }

    public String getLotudCont() {
        return lotudCont;
    }

    public void setLotudCont(String lotudCont) {
        this.lotudCont = lotudCont;
    }

    public String getHouscplxNm() {
        return houscplxNm;
    }

    public void setHouscplxNm(String houscplxNm) {
        this.houscplxNm = houscplxNm;
    }

    public String getScreenHouscplxNm() { return screenHouscplxNm; }

    public void setScreenHouscplxNm(String screenHouscplxNm) { this.screenHouscplxNm = screenHouscplxNm; }

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

    public String getHmnetId() {
        return hmnetId;
    }

    public void setHmnetId(String hmnetId) {
        this.hmnetId = hmnetId;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getAreaBasAddrCont() {
        return areaBasAddrCont;
    }

    public void setAreaBasAddrCont(String areaBasAddrCont) {
        this.areaBasAddrCont = areaBasAddrCont;
    }

    public String getLandDimQty() {
        return landDimQty;
    }

    public void setLandDimQty(String landDimQty) {
        this.landDimQty = landDimQty;
    }

    public String getArchDimQty() {
        return archDimQty;
    }

    public void setArchDimQty(String archDimQty) {
        this.archDimQty = archDimQty;
    }

    public String getBusiApprYmd() {
        return busiApprYmd;
    }

    public void setBusiApprYmd(String busiApprYmd) {
        this.busiApprYmd = busiApprYmd;
    }

    public String getBusiConendYmd() {
        return busiConendYmd;
    }

    public void setBusiConendYmd(String busiConendYmd) {
        this.busiConendYmd = busiConendYmd;
    }

    public int getWholeHsholdCnt() {
        return wholeHsholdCnt;
    }

    public void setWholeHsholdCnt(int wholeHsholdCnt) {
        this.wholeHsholdCnt = wholeHsholdCnt;
    }

    public int getWholeDongCnt() {
        return wholeDongCnt;
    }

    public void setWholeDongCnt(int wholeDongCnt) {
        this.wholeDongCnt = wholeDongCnt;
    }

    public String getHeatTpCd() {
        return heatTpCd;
    }

    public void setHeatTpCd(String heatTpCd) {
        this.heatTpCd = heatTpCd;
    }

    public int getLwstUngrFlrcnt() {
        return lwstUngrFlrcnt;
    }

    public void setLwstUngrFlrcnt(int lwstUngrFlrcnt) {
        this.lwstUngrFlrcnt = lwstUngrFlrcnt;
    }

    public int getHgstAbgrFlrcnt() {
        return hgstAbgrFlrcnt;
    }

    public void setHgstAbgrFlrcnt(int hgstAbgrFlrcnt) {
        this.hgstAbgrFlrcnt = hgstAbgrFlrcnt;
    }

    public String getAddrSiGunGuNm() {
        return addrSiGunGuNm;
    }

    public void setAddrSiGunGuNm(String addrSiGunGuNm) {
        this.addrSiGunGuNm = addrSiGunGuNm;
    }

    public String getAddrSiDoNm() {
        return addrSiDoNm;
    }

    public void setAddrSiDoNm(String addrSiDoNm) {
        this.addrSiDoNm = addrSiDoNm;
    }

    public String getAddrEupMyeonDongNm() {
        return addrEupMyeonDongNm;
    }

    public void setAddrEupMyeonDongNm(String addrEupMyeonDongNm) {
        this.addrEupMyeonDongNm = addrEupMyeonDongNm;
    }

    public String getEnrgMeasYmd() {
        return enrgMeasYmd;
    }

    public void setEnrgMeasYmd(String enrgMeasYmd) {
        this.enrgMeasYmd = enrgMeasYmd;
    }

    public int getWrMeasXCoorNo() {
        return wrMeasXCoorNo;
    }

    public void setWrMeasXCoorNo(int wrMeasXCoorNo) {
        this.wrMeasXCoorNo = wrMeasXCoorNo;
    }

    public int getWrMeasYCoorNo() {
        return wrMeasYCoorNo;
    }

    public void setWrMeasYCoorNo(int wrMeasYCoorNo) {
        this.wrMeasYCoorNo = wrMeasYCoorNo;
    }

    public int getWholeWlLcnt() {
        return wholeWlLcnt;
    }

    public void setWholeWlLcnt(int wholeWlLcnt) {
        this.wholeWlLcnt = wholeWlLcnt;
    }

    public String getExcwkBizcoNm() {
        return excwkBizcoNm;
    }

    public void setExcwkBizcoNm(String excwkBizcoNm) {
        this.excwkBizcoNm = excwkBizcoNm;
    }

    public String getHsholdWlLcnt() {
        return hsholdWlLcnt;
    }

    public void setHsholdWlLcnt(String hsholdWlLcnt) {
        this.hsholdWlLcnt = hsholdWlLcnt;
    }

    public String getHeatTpNm() {
        return heatTpNm;
    }

    public void setHeatTpNm(String heatTpNm) {
        this.heatTpNm = heatTpNm;
    }

    public String getBizcoCd() {
        return bizcoCd;
    }

    public void setBizcoCd(String bizcoCd) {
        this.bizcoCd = bizcoCd;
    }

    public String getBizcoNm() {
        return bizcoNm;
    }

    public void setBizcoNm(String bizcoNm) {
        this.bizcoNm = bizcoNm;
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

    public String getHmnetNm() {
        return hmnetNm;
    }

    public void setHmnetNm(String hmnetNm) {
        this.hmnetNm = hmnetNm;
    }

    public String getSvrTpCd() {
        return svrTpCd;
    }

    public void setSvrTpCd(String svrTpCd) {
        this.svrTpCd = svrTpCd;
    }

    public String getLnkSvcNm() {
        return lnkSvcNm;
    }

    public void setLnkSvcNm(String lnkSvcNm) {
        this.lnkSvcNm = lnkSvcNm;
    }

    public String getLnkSvcId() { return lnkSvcId; }

    public void setLnkSvcId(String lnkSvcId) { this.lnkSvcId = lnkSvcId; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getbanrLnkCls() { return banrLnkCls; }

    public void setbanrLnkCls(String banrLnkCls) { this.banrLnkCls = banrLnkCls; }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "HousingCplx{" +
                "houscplxCd='" + houscplxCd + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", qryRangeCont=" + qryRangeCont +
                ", roadnmBasAddrCont='" + roadnmBasAddrCont + '\'' +
                ", latudCont='" + latudCont + '\'' +
                ", lotudCont='" + lotudCont + '\'' +
                ", houscplxNm='" + houscplxNm + '\'' +
                ", screenHouscplxNm='" + screenHouscplxNm + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", hmnetId='" + hmnetId + '\'' +
                ", postNo='" + postNo + '\'' +
                ", areaBasAddrCont='" + areaBasAddrCont + '\'' +
                ", landDimQty='" + landDimQty + '\'' +
                ", archDimQty='" + archDimQty + '\'' +
                ", busiApprYmd='" + busiApprYmd + '\'' +
                ", busiConendYmd='" + busiConendYmd + '\'' +
                ", wholeHsholdCnt=" + wholeHsholdCnt +
                ", wholeDongCnt=" + wholeDongCnt +
                ", heatTpCd='" + heatTpCd + '\'' +
                ", lwstUngrFlrcnt=" + lwstUngrFlrcnt +
                ", hgstAbgrFlrcnt=" + hgstAbgrFlrcnt +
                ", addrSiGunGuNm='" + addrSiGunGuNm + '\'' +
                ", addrSiDoNm='" + addrSiDoNm + '\'' +
                ", addrEupMyeonDongNm='" + addrEupMyeonDongNm + '\'' +
                ", enrgMeasYmd='" + enrgMeasYmd + '\'' +
                ", wrMeasXCoorNo=" + wrMeasXCoorNo +
                ", wrMeasYCoorNo=" + wrMeasYCoorNo +
                ", wholeWlLcnt=" + wholeWlLcnt +
                ", excwkBizcoNm='" + excwkBizcoNm + '\'' +
                ", hsholdWlLcnt='" + hsholdWlLcnt + '\'' +
                ", heatTpNm='" + heatTpNm + '\'' +
                ", bizcoCd='" + bizcoCd + '\'' +
                ", bizcoNm='" + bizcoNm + '\'' +
                ", hmnetNm='" + hmnetNm + '\'' +
                ", svrTpCd='" + svrTpCd + '\'' +
                ", lnkSvcNm='" + lnkSvcNm + '\'' +
                ", lnkSvcId='" + lnkSvcId + '\'' +
                ", cnt='" + cnt + '\'' +
                ", startCrDt='" + startCrDt + '\'' +
                ", endCrDt='" + endCrDt + '\'' +
                '}';
    }
}
