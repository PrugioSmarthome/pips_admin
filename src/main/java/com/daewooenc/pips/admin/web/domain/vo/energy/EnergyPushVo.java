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
 *    2019-09-03       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-09-03
 **/
@Component
public class EnergyPushVo {
    @JsonProperty("hshold_id") @JsonSerialize(using = ToStringSerializer.class)
    private String hsholdId;
    @JsonProperty("enrg_tp_cd") @JsonSerialize(using = ToStringSerializer.class)
    private String enrgTpCd;
    @JsonProperty("level")
    private int level;

    public EnergyPushVo() {

    }
    public EnergyPushVo(String hsholdId, String enrgTpCd, int level) {
        this.hsholdId = hsholdId;
        this.enrgTpCd = enrgTpCd;
        this.level = level;
    }

    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    public String getEnrgTpCd() {
        return enrgTpCd;
    }

    public void setEnrgTpCd(String enrgTpCd) {
        this.enrgTpCd = enrgTpCd;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}