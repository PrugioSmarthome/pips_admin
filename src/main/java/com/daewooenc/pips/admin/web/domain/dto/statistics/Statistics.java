package com.daewooenc.pips.admin.web.domain.dto.statistics;

public class Statistics {

    private String houscplxNm;
    private int holdCnt;
    private int holdUserCnt;
    private int userCnt;

    public String getHouscplxNm() { return houscplxNm; }

    public void setHouscplxNm(String houscplxNm) { this.houscplxNm = houscplxNm; }

    public int getHoldCnt() { return holdCnt; }

    public void setHoldCnt(int holdCnt) { this.holdCnt = holdCnt; }

    public int getHoldUserCnt() { return holdUserCnt; }

    public void setHoldUserCnt(int holdUserCnt) { this.holdUserCnt = holdUserCnt; }

    public int getUserCnt() { return userCnt; }

    public void setUserCnt(int userCnt) { this.userCnt = userCnt; }
}
