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
public class HousingCplxNotiResultVo {

    public HousingCplxNotiResultVo() {

    }
    @JsonProperty("id") @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    @JsonProperty("detail_command_status") @JsonSerialize(using = ToStringSerializer.class)
    private String detailCommandStatus;


    @JsonProperty("addition_message") @JsonSerialize(using = ToStringSerializer.class)
    private String additionMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetailCommandStatus() {
        return detailCommandStatus;
    }

    public void setDetailCommandStatus(String detailCommandStatus) {
        this.detailCommandStatus = detailCommandStatus;
    }

    public String getAdditionMessage() {
        return additionMessage;
    }

    public void setAdditionMessage(String additionMessage) {
        this.additionMessage = additionMessage;
    }
}