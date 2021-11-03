package com.daewooenc.pips.admin.web.domain.vo.energy;

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
 *    2019-08-27       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-27
 **/
public class EnergyDataVo {
    @JsonProperty("node_id") @JsonSerialize(using = ToStringSerializer.class)
    private String nodeId;
    @JsonProperty("gas") @JsonSerialize(using = ToStringSerializer.class)
    private String gas;
    @JsonProperty("electricity") @JsonSerialize(using = ToStringSerializer.class)
    private String electricity;
    @JsonProperty("water") @JsonSerialize(using = ToStringSerializer.class)
    private String water;
    @JsonProperty("hotwater") @JsonSerialize(using = ToStringSerializer.class)
    private String hotwater;
    @JsonProperty("heating") @JsonSerialize(using = ToStringSerializer.class)
    private String heating;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getHotwater() {
        return hotwater;
    }

    public void setHotwater(String hotwater) {
        this.hotwater = hotwater;
    }

    public String getHeating() {
        return heating;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }
}