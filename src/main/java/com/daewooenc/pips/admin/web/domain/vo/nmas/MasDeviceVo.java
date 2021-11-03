package com.daewooenc.pips.admin.web.domain.vo.nmas;

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
 *    2019-08-12       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-12
 **/
public class MasDeviceVo {
    @JsonProperty("device_id") @JsonSerialize(using = ToStringSerializer.class)
    private String deviceId = "";

    @JsonProperty("device_key") @JsonSerialize(using = ToStringSerializer.class)
    private String deviceKey = "";

    @JsonProperty("protocol_type") @JsonSerialize(using = ToStringSerializer.class)
    private String protocolType = "";

    @JsonProperty("domain") @JsonSerialize(using = ToStringSerializer.class)
    private String domain = "";

    @JsonProperty("device_mf_id") @JsonSerialize(using = ToStringSerializer.class)
    private String deviceMfId = "";

    @JsonProperty("device_model_id") @JsonSerialize(using = ToStringSerializer.class)
    private String deviceModelId = "";

    @JsonProperty("device_sn") @JsonSerialize(using = ToStringSerializer.class)
    private String deviceSn = "";

    @JsonProperty("content_push") @JsonSerialize(using = ToStringSerializer.class)
    private String contentPush = "Y";

    @JsonProperty("content_save") @JsonSerialize(using = ToStringSerializer.class)
    private String contentSave = "N";

    @JsonProperty("command_push") @JsonSerialize(using = ToStringSerializer.class)
    private String commandPush = "Y";

    @JsonProperty("command_save") @JsonSerialize(using = ToStringSerializer.class)
    private String commandSave = "N";

    @JsonProperty("node_mgmt_push") @JsonSerialize(using = ToStringSerializer.class)
    private String nodeMgmtPush = "Y";

    public MasDeviceVo () {

    }
    public MasDeviceVo(String protocolType, String domain, String deviceMfId, String deviceModelId, String deviceSn) {
        this.protocolType = protocolType;
        this.domain = domain;
        this.deviceMfId = deviceMfId;
        this.deviceModelId = deviceModelId;
        this.deviceSn = deviceSn;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDeviceMfId() {
        return deviceMfId;
    }

    public void setDeviceMfId(String deviceMfId) {
        this.deviceMfId = deviceMfId;
    }

    public String getDeviceModelId() {
        return deviceModelId;
    }

    public void setDeviceModelId(String deviceModelId) {
        this.deviceModelId = deviceModelId;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getContentPush() {
        return contentPush;
    }

    public void setContentPush(String contentPush) {
        this.contentPush = contentPush;
    }

    public String getContentSave() {
        return contentSave;
    }

    public void setContentSave(String contentSave) {
        this.contentSave = contentSave;
    }

    public String getCommandPush() {
        return commandPush;
    }

    public void setCommandPush(String commandPush) {
        this.commandPush = commandPush;
    }

    public String getCommandSave() {
        return commandSave;
    }

    public void setCommandSave(String commandSave) {
        this.commandSave = commandSave;
    }

    public String getNodeMgmtPush() {
        return nodeMgmtPush;
    }

    public void setNodeMgmtPush(String nodeMgmtPush) {
        this.nodeMgmtPush = nodeMgmtPush;
    }
}