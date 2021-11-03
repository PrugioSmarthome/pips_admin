package com.daewooenc.pips.admin.web.domain.dto.standardCode;

/**
 * T_COMM_CD_DTL
 * 공통코드 관리 기본
 * 공통코드 관리 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2021-04-07      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2021-04-07
 **/
public class StandardCode {
    private String commCdGrpNm;
    private String commCdGrpCd;
    private String commCdGrpCdDB;
    private String commCdNm;
    private String commCd;
    private String commCdDB;
    private String rem;
    private String crerId;
    private String editerId;

    public String getCommCdGrpNm() {
        return commCdGrpNm;
    }

    public void setCommCdGrpNm(String commCdGrpNm) { this.commCdGrpNm = commCdGrpNm; }

    public String getCommCdGrpCd() { return commCdGrpCd; }

    public void setCommCdGrpCd(String commCdGrpCd) { this.commCdGrpCd = commCdGrpCd; }

    public String getCommCdGrpCdDB() { return commCdGrpCdDB; }

    public void setCommCdGrpCdDB(String commCdGrpCdDB) { this.commCdGrpCdDB = commCdGrpCdDB; }

    public String getCommCdNm() { return commCdNm; }

    public void setCommCdNm(String commCdNm) { this.commCdNm = commCdNm; }

    public String getCommCd() { return commCd; }

    public void setCommCd(String commCd) { this.commCd = commCd; }

    public String getCommCdDB() { return commCdDB; }

    public void setCommCdDB(String commCdDB) { this.commCdDB = commCdDB; }

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
                ", commCdNm=" + commCdNm +
                ", commCd=" + commCd +
                ", rem='" + rem + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                '}';
    }
}