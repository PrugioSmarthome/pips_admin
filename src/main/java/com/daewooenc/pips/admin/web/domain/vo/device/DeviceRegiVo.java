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
public class DeviceRegiVo {
    public DeviceRegiVo() {

    }
    public DeviceRegiVo(String adminId, String userId, String hsholdId) {
        this.adminId = adminId;
        this.userId = userId;
        this.hsholdId = hsholdId;
    }

    @JsonProperty("admin_id") @JsonSerialize(using = ToStringSerializer.class)
    private String adminId;

    @JsonProperty("user_id") @JsonSerialize(using = ToStringSerializer.class)
    private String userId;

    @JsonProperty("hshold_id") @JsonSerialize(using = ToStringSerializer.class)
    private String hsholdId;

    @JsonProperty("appr_sts_cd") @JsonSerialize(using = ToStringSerializer.class)
    private String apprStsCd;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    public String getApprStsCd() {
        return apprStsCd;
    }

    public void setApprStsCd(String apprStsCd) {
        this.apprStsCd = apprStsCd;
    }
}