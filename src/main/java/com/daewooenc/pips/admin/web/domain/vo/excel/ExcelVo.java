package com.daewooenc.pips.admin.web.domain.vo.excel;

import java.util.Vector;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-08-14       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-14
 **/
public class ExcelVo {
    private String fileName;
    private String sheetName;
    private String title;
    private String[] tableHeader;
    private Vector<String[]> body;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Vector<String[]> getBody() {
        return body;
    }

    public void setBody(Vector<String[]> body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getTableHeader() {
        return tableHeader;
    }

    public void setTableHeader(String[] tableHeader) {
        this.tableHeader = tableHeader;
    }
}