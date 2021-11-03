package com.daewooenc.pips.admin.web.domain.dto.groupCode;

import java.util.Date;

/**
 * T_COMM_CD_BAS
 * 그룹코드 관리 기본
 * 그룹코드 관리 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2021-03-26      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2021-03-26
 **/
public class GroupCode {
    private String commCdGrpNm;
    private String commCdGrpCd;
    private String commCdGrpCdDB;
    private String rem;
    private String crerId;
    private String editerId;

    public String getCommCdGrpNm() {
        return commCdGrpNm;
    }

    public void setCommCdGrpNm(String commCdGrpNm) {
        this.commCdGrpNm = commCdGrpNm;
    }

    public String getCommCdGrpCd() {
        return commCdGrpCd;
    }

    public void setCommCdGrpCd(String commCdGrpCd) {
        this.commCdGrpCd = commCdGrpCd;
    }

    public String getCommCdGrpCdDB() { return commCdGrpCdDB; }

    public void setCommCdGrpCdDB(String commCdGrpCdDB) { this.commCdGrpCdDB = commCdGrpCdDB; }

    public String getRem() { return rem; }

    public void setRem(String rem) { this.rem = rem; }

    public String getCrerId() { return crerId; }

    public void setCrerId(String crerId) { this.crerId = crerId; }

    public String getEditerId() { return editerId; }

    public void setEditerId(String editerId) { this.editerId = editerId; }


    @Override
    public String toString() {
        return "Device{" +
                ", commCdGrpNm=" + commCdGrpNm +
                ", commCdGrpCd=" + commCdGrpCd +
                ", commCdGrpCdDB=" + commCdGrpCdDB +
                ", rem='" + rem + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                '}';
    }
}