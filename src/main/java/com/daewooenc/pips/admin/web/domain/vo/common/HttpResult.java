package com.daewooenc.pips.admin.web.domain.vo.common;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-08-09       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-09
 **/
public class HttpResult {
    private String status;
    private String message;

    public HttpResult() {

    }
    public HttpResult(String status, String message) {

        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}