package com.daewooenc.pips.admin.web.domain.vo.servermgmt;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
public class ServerConfCtrVo {
    public ServerConfCtrVo() {

    }

    public ServerConfCtrVo(String hmnetId, String keepAliveCycleCont, String datTrnsmCycleCont, String ctlExprtnCycleCont, String admin_id) {
        this.hmnetId = hmnetId;
        this.keepAliveCycleCont = keepAliveCycleCont;
        this.datTrnsmCycleCont = datTrnsmCycleCont;
        this.ctlExprtnCycleCont = ctlExprtnCycleCont;
        this.adminId = admin_id;
    }

    @JsonProperty("hmnet_id") @JsonSerialize(using = ToStringSerializer.class)
    private String hmnetId;

    @JsonProperty("keep_alive_cycle") @JsonSerialize(using = ToStringSerializer.class)
    private String keepAliveCycleCont;

    @JsonProperty("dat_trnsm_cycle") @JsonSerialize(using = ToStringSerializer.class)
    private String datTrnsmCycleCont;

    @JsonProperty("ctl_exprtn_cycle") @JsonSerialize(using = ToStringSerializer.class)
    private String ctlExprtnCycleCont;
    @JsonProperty("admin_id") @JsonSerialize(using = ToStringSerializer.class)
    private String adminId;

    public String getHmnetId() {
        return hmnetId;
    }

    public void setHmnetId(String hmnetId) {
        this.hmnetId = hmnetId;
    }

    public String getKeepAliveCycleCont() {
        return keepAliveCycleCont;
    }

    public void setKeepAliveCycleCont(String keepAliveCycleCont) {
        this.keepAliveCycleCont = keepAliveCycleCont;
    }

    public String getDatTrnsmCycleCont() {
        return datTrnsmCycleCont;
    }

    public void setDatTrnsmCycleCont(String datTrnsmCycleCont) {
        this.datTrnsmCycleCont = datTrnsmCycleCont;
    }

    public String getCtlExprtnCycleCont() {
        return ctlExprtnCycleCont;
    }

    public void setCtlExprtnCycleCont(String ctlExprtnCycleCont) {
        this.ctlExprtnCycleCont = ctlExprtnCycleCont;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String userId) {
        this.adminId = adminId;
    }
}