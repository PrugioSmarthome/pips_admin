package com.daewooenc.pips.admin.web.domain.vo.device;

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
 *       2019-09-25      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-25
 **/
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DeviceSyncVo {
    public DeviceSyncVo() {

    }
    public DeviceSyncVo(String adminId, String hmnetId, String houscplxCd, String dongNo, String hoseNo) {
        this.adminId = adminId;
        this.hmnetId = hmnetId;
        this.houscplxCd = houscplxCd;
        this.dongNo = dongNo;
        this.hoseNo = hoseNo;
    }

    @JsonProperty("admin_id") @JsonSerialize(using = ToStringSerializer.class)
    private String adminId;

    @JsonProperty("hmnet_id") @JsonSerialize(using = ToStringSerializer.class)
    private String hmnetId;

    @JsonProperty("houscplx_cd") @JsonSerialize(using = ToStringSerializer.class)
    private String houscplxCd;

    @JsonProperty("dong_no") @JsonSerialize(using = ToStringSerializer.class)
    private String dongNo;

    @JsonProperty("hose_no") @JsonSerialize(using = ToStringSerializer.class)
    private String hoseNo;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getHmnetId() {
        return hmnetId;
    }

    public void setHmnetId(String hmnetId) {
        this.hmnetId = hmnetId;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
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
}