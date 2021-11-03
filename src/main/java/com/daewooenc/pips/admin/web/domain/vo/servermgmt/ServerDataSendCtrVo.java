package com.daewooenc.pips.admin.web.domain.vo.servermgmt;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
 *    2019-08-07       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-07
 **/
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ServerDataSendCtrVo {

    @JsonProperty("hmnet_id") @JsonSerialize(using = ToStringSerializer.class)
    private String hmnetId;

    @JsonProperty("tgt_tp") @JsonSerialize(using = ToStringSerializer.class)
    private String tgtTp;

    @JsonProperty("houscplx_cd") @JsonSerialize(using = ToStringSerializer.class)
    private String houscplxCd;

    @JsonProperty("trnsm_yn") @JsonSerialize(using = ToStringSerializer.class)
    private String trnsmYn;

    @JsonProperty("admin_id") @JsonSerialize(using = ToStringSerializer.class)
    private String adminId;

    public ServerDataSendCtrVo() {

    }
    public ServerDataSendCtrVo(String hmnetId, String tgtTp, String houscplxCd, String trnsmYn, String admin_id) {
        this.hmnetId = hmnetId;
        this.tgtTp = tgtTp;
        this.houscplxCd = houscplxCd;
        this.trnsmYn = trnsmYn;
        this.adminId = admin_id;
    }


    public String getHmnetId() {
        return hmnetId;
    }

    public void setHmnetId(String hmnetId) {
        this.hmnetId = hmnetId;
    }

    public String getTgtTp() {
        return tgtTp;
    }

    public void setTgtTp(String tgtTp) {
        this.tgtTp = tgtTp;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getTrnsmYn() {
        return trnsmYn;
    }

    public void setTrnsmYn(String trnsmYn) {
        this.trnsmYn = trnsmYn;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String userId) {
        this.adminId = adminId;
    }
}