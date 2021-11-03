package com.daewooenc.pips.admin.web.domain.vo.weather;

public class WeatherBodyVo {

    WeatherItemsVo items;
    // 신규 API 추가
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

    public WeatherItemsVo getItems() {
        return items;
    }

    public void setItems(WeatherItemsVo items) {
        this.items = items;
    }
}
