package com.daewooenc.pips.admin.web.domain.vo.homenet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-26      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-26
 **/

@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class HousingCplxNotiVo {
    public HousingCplxNotiVo() {

    }
    public HousingCplxNotiVo(String houscplxCd, String blltNo) {
        this.houscplxCd = houscplxCd;
        this.blltNo = blltNo;
    }

    @JsonProperty("houscplx_cd") @JsonSerialize(using = ToStringSerializer.class)
    private String houscplxCd;

    @JsonProperty("bllt_no") @JsonSerialize(using = ToStringSerializer.class)
    private String blltNo;

    @JsonProperty("admin_id") @JsonSerialize(using = ToStringSerializer.class)
    private String adminId;

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getBlltNo() {
        return blltNo;
    }

    public void setBlltNo(String blltNo) {
        this.blltNo = blltNo;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}