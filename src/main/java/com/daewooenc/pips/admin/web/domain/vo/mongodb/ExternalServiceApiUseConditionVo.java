package com.daewooenc.pips.admin.web.domain.vo.mongodb;

import com.fasterxml.jackson.annotation.JsonProperty;
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
 *    2019-09-09       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-09-09
 **/
public class ExternalServiceApiUseConditionVo {

    @JsonProperty("startDate") @JsonSerialize(using = ToStringSerializer.class)
    private String startDate;

    @JsonProperty("endDate") @JsonSerialize(using = ToStringSerializer.class)
    private String endDate;

    @JsonProperty("userId") @JsonSerialize(using = ToStringSerializer.class)
    private String userId;

    @JsonProperty("userType") @JsonSerialize(using = ToStringSerializer.class)
    private String userType;

    @JsonProperty("complexCode") @JsonSerialize(using = ToStringSerializer.class)
    private String complexCode;

    @JsonProperty("svcTpDtlCd") @JsonSerialize(using = ToStringSerializer.class)
    private String svcTpDtlCd;

    @JsonProperty("svc_tp_cd") @JsonSerialize(using = ToStringSerializer.class)
    private String svcTpCd;

    @JsonProperty("dongNo") @JsonSerialize(using = ToStringSerializer.class)
    private String dongNo;

    @JsonProperty("hoseNo") @JsonSerialize(using = ToStringSerializer.class)
    private String hoseNo;

    @JsonProperty("sortKey") @JsonSerialize(using = ToStringSerializer.class)
    private String sortKey;

    @JsonProperty("order") @JsonSerialize(using = ToStringSerializer.class)
    private String order;

    @JsonProperty("page") @JsonSerialize(using = ToStringSerializer.class)
    private int page = 1;

    @JsonProperty("pageForCnt") @JsonSerialize(using = ToStringSerializer.class)
    private int pageForCnt = 10;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getComplexCode() {
        return complexCode;
    }

    public void setComplexCode(String complexCode) {
        this.complexCode = complexCode;
    }

    public String getSvcTpDtlCd() {
        return svcTpDtlCd;
    }

    public void setSvcTpDtlCd(String svcTpDtlCd) {
        this.svcTpDtlCd = svcTpDtlCd;
    }

    public String getSvcTpCd() {
        return svcTpCd;
    }

    public void setSvcTpCd(String svcTpCd) {
        this.svcTpCd = svcTpCd;
    }

    public String getDongNo() {
        return dongNo;
    }

    public void setDongNo(String dongNo) {
        this.dongNo = dongNo;
    }

    public String getHoseNo() {
        return hoseNo;
    }

    public void setHoseNo(String hoseNo) {
        this.hoseNo = hoseNo;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageForCnt() {
        return pageForCnt;
    }

    public void setPageForCnt(int pageForCnt) {
        this.pageForCnt = pageForCnt;
    }
}