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
public class ServerStatusVo {
    public ServerStatusVo() {

    }


    @JsonProperty("type") @JsonSerialize(using = ToStringSerializer.class)
    private String type;


    @JsonProperty("status") @JsonSerialize(using = ToStringSerializer.class)
    private String status;

    @JsonProperty("creation_time") @JsonSerialize(using = ToStringSerializer.class)
    private String creationTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}