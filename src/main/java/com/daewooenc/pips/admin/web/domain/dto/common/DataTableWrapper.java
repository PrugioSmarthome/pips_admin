package com.daewooenc.pips.admin.web.domain.dto.common;

import java.util.List;

/**
 * @author : najinuki
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-10-31       :       najinuki        :                <br/>
 *
 * </pre>
 * @since : 2019-10-31
 **/
public class DataTableWrapper {

    private int recordsTotal;
    private int recordsFiltered;
    private List data;
    private int draw;

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    @Override
    public String toString() {
        return "DataTableWrapper{" +
                "recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", data=" + data +
                ", draw=" + draw +
                '}';
    }
}
