package com.daewooenc.pips.admin.web.domain.vo.finedust;

import java.util.List;

public class FineDutyBodyVo {
    List<FineDutyItemsVo> items;

    String dataType;
    int numOfRows;
    int pageNo;
    int totalCount;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<FineDutyItemsVo> getItems() { return items; }

    public void setItems(List<FineDutyItemsVo> items) {
        this.items = items;
    }

}
