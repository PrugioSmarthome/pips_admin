package com.daewooenc.pips.admin.web.domain.dto.platform;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-11-16      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-11-16
 **/
public class Platform {
    private String platformTpNm;
    private String platformNm;
    private String platformId;
    private String platformAuthKey;
    private String platformCompany;
    private String platformUrl;
    private String platformNotiUrl;
    private String crerId;
    private String editerId;

    public String getPlatformTpNm() {
        return platformTpNm;
    }

    public void setPlatformTpNm(String platformTpNm) {
        this.platformTpNm = platformTpNm;
    }

    public String getPlatformNm() {
        return platformNm;
    }

    public void setPlatformNm(String platformNm) {
        this.platformNm = platformNm;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformAuthKey() {
        return platformAuthKey;
    }

    public void setPlatformAuthKey(String platformAuthKey) {
        this.platformAuthKey = platformAuthKey;
    }

    public String getPlatformCompany() {
        return platformCompany;
    }

    public void setPlatformCompany(String platformCompany) {
        this.platformCompany = platformCompany;
    }

    public String getPlatformUrl() {
        return platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

    public String getPlatformNotiUrl() {
        return platformNotiUrl;
    }

    public void setPlatformNotiUrl(String platformNotiUrl) {
        this.platformNotiUrl = platformNotiUrl;
    }

    public String getCrerId() {
        return crerId;
    }

    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    public String getEditerId() {
        return editerId;
    }

    public void setEditerId(String editerId) {
        this.editerId = editerId;
    }

}