package com.daewooenc.pips.admin.web.domain.vo.sns;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class SnsResult {
    @JsonProperty("access_token") @JsonSerialize(using = ToStringSerializer.class)
    private String accessToken = "";

    @JsonProperty("refresh_token") @JsonSerialize(using = ToStringSerializer.class)
    private String refreshToken = "";

    @JsonProperty("token_type") @JsonSerialize(using = ToStringSerializer.class)
    private String tokenType = "";

    @JsonProperty("expires_in") @JsonSerialize(using = ToStringSerializer.class)
    private String expiresIn = "";

    @JsonProperty("error") @JsonSerialize(using = ToStringSerializer.class)
    private String error = "";

    @JsonProperty("error_description") @JsonSerialize(using = ToStringSerializer.class)
    private String errorDescription = "";

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }


}
