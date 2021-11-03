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
 *       2019-09-25      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-25
 **/
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class HomenetServiceFunctionSendVo {
    public HomenetServiceFunctionSendVo(String adminId, String hmnetId, String houscplxCd, String target) {
        this.adminId = adminId;
        this.hmnetId = hmnetId;
        this.houscplxCd = houscplxCd;
        this.target = target;
    }

    @JsonProperty("admin_id") @JsonSerialize(using = ToStringSerializer.class)
    private String adminId;

    @JsonProperty("hmnet_id") @JsonSerialize(using = ToStringSerializer.class)
    private String hmnetId;

    @JsonProperty("target") @JsonSerialize(using = ToStringSerializer.class)
    private String target;

    @JsonProperty("houscplx_cd") @JsonSerialize(using = ToStringSerializer.class)
    private String houscplxCd;

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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }




}