package com.daewooenc.pips.admin.web.domain.vo.energy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.stereotype.Component;

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
@Component
public class EnergyCtrVo {
    @JsonProperty("hmnet_id") @JsonSerialize(using = ToStringSerializer.class)
    private String hmnetId;


    @JsonProperty("houscplx_cd") @JsonSerialize(using = ToStringSerializer.class)
    private String houscplxCd;

    @JsonProperty("trnsm_hshold_cnt")
    private int trnsmHsholdCnt;

    @JsonProperty("admin_id") @JsonSerialize(using = ToStringSerializer.class)
    private String userId;

    public EnergyCtrVo() {

    }
    public EnergyCtrVo(String hmnetId, String houscplxCd, int trnsmHsholdCnt, String userId) {
        this.hmnetId = hmnetId;
        this.houscplxCd = houscplxCd;
        this.trnsmHsholdCnt = trnsmHsholdCnt;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTrnsmHsholdCnt() {
        return trnsmHsholdCnt;
    }

    public void setTrnsmHsholdCnt(int trnsmHsholdCnt) {
        this.trnsmHsholdCnt = trnsmHsholdCnt;
    }
}