package com.daewooenc.pips.admin.web.domain.vo.energy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.List;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-08-27       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-27
 **/
public class EnergyEventDataVo {
    @JsonProperty("type") @JsonSerialize(using = ToStringSerializer.class)
    private String type;
    @JsonProperty("date") @JsonSerialize(using = ToStringSerializer.class)
    private String date;
    @JsonProperty("total_count") @JsonSerialize(using = ToStringSerializer.class)
    private String totalCount;
    @JsonProperty("page_count") @JsonSerialize(using = ToStringSerializer.class)
    private String pageCount;
    private List<EnergyDataVo> energy_data_info;

    public EnergyEventDataVo() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public List<EnergyDataVo> getEnergy_data_info() {
        return energy_data_info;
    }

    public void setEnergy_data_info(List<EnergyDataVo> energy_data_info) {
        this.energy_data_info = energy_data_info;
    }
}