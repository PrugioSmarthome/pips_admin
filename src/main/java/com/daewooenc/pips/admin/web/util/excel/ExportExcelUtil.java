package com.daewooenc.pips.admin.web.util.excel;

import com.daewooenc.pips.admin.web.common.WebConsts;
import com.daewooenc.pips.admin.web.domain.vo.excel.ExcelVo;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-08-14       :       yckim        :                  <br/>
 *    2019-10-10       :       dokim        :      cell merge 수정 및 기타 param 추가 <br/>
 *
 * </pre>
 * @since : 2019-08-14
 **/
public class ExportExcelUtil {
    /**
     *
     * @param object List형 object
     * @param fileName export할 파일 이름
     * @param sheetName sheet 이름
     * @param tableHeader 테이블 헤더
     * @param keyOder object의 key 리스트(Object를 JsonObject으로 변환하여 값을 찾을 때 사용)
     * @return
     */
    public ExcelVo makeExportData(Object object, String fileName, String sheetName, String title, String[] tableHeader, String[] keyOder) {
        ExcelVo excelVo = new ExcelVo();
        excelVo.setFileName(fileName);
        excelVo.setSheetName(sheetName);
        excelVo.setTitle(title);
        excelVo.setTableHeader(tableHeader);

        List<JSONObject> list = null;
        if (object instanceof List) {
            list = (List<JSONObject>)object;
        }

        Vector<String[]> vtBody = new Vector();
        for(JSONObject dataObject : list) {
            Iterator<String> it = dataObject.keys();

            String[] strbody = new String[keyOder.length];
            int i = 0;
            for (String key : keyOder) {
                strbody[i++] = dataObject.get(key) + "";
            }
            vtBody.add(strbody);
        }
        excelVo.setBody(vtBody);
        return excelVo;
    }

    /**
     * HTTP Response로 Return
     * @param excelVo
     * @param response
     * @return
     */
    public boolean exportExcel(ExcelVo excelVo, HttpServletResponse response) {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        FileOutputStream out = null;
        XSSFSheet sheet = null;
        ServletOutputStream outputStream = null;
        String sheetName = "";

        //Create a blank sheet
        if (excelVo.getSheetName() != null && !"".equalsIgnoreCase(excelVo.getSheetName())) {
            sheet = workbook.createSheet(excelVo.getSheetName());
        } else {
            sheetName = com.daewooenc.pips.admin.web.util.DateUtil.getDate(WebConsts.strDateFormat_yyyyMMddHHmmss_1, Calendar.getInstance().getTime());
            sheet = workbook.createSheet(sheetName);
        }
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        Map<String, CellStyle> styles= createStyles(workbook);

        int rownum = 0;
        int cellnum = 0;

        Cell tileCell = null;

        Row tileRow = sheet.createRow(rownum++);
        for(int i = 0; i < excelVo.getTableHeader().length; i++) {
            tileCell = tileRow.createCell(cellnum++);
            tileCell.setCellStyle(styles.get("title"));
            if (i == 0) {
                tileCell.setCellValue(excelVo.getTitle());
            }
        }

        // blank row 생성
        sheet.createRow(rownum++);

        // table header row
        Row tableHeaderRow = sheet.createRow(rownum++);
        // title
        cellnum = 0;

        Cell headerCell = null;

        // table header
        for(int i = 0; i < excelVo.getTableHeader().length; i++) {
            headerCell = tableHeaderRow.createCell(cellnum++);
            headerCell.setCellStyle(styles.get("header"));
            headerCell.setCellValue(excelVo.getTableHeader()[i]);
        }

        Cell bodyCell = null;
        // table body
        for(int i = 0; i < excelVo.getBody().size(); i++) {
            Row bodyRow = sheet.createRow(rownum++);
            String[] bodyContent = excelVo.getBody().get(i);
            cellnum = 0;
            for (int j = 0; j < bodyContent.length; j++) {
                bodyCell = bodyRow.createCell(cellnum++);
                bodyCell.setCellStyle(styles.get("cell"));
                bodyCell.setCellValue(bodyContent[j]);
            }

            sheet.setColumnWidth(i, 256 * 20);
        }

        if (excelVo.getTableHeader().length > 1) {
            // Cell Merge
            String ref = CellAddress.A1.toString() + ":" + tileCell.getAddress().toString();

            CellRangeAddress cellRangeAddress = CellRangeAddress.valueOf(ref);
            sheet.addMergedRegionUnsafe(cellRangeAddress);
        }

        for (int i = 0; i < excelVo.getTableHeader().length; i++) {
            sheet.setColumnWidth(i, 256 * 20);
        }

        try {
            response.setContentType("application/download;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + excelVo.getFileName() + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            outputStream = response.getOutputStream();
            // Write to the output stream
            workbook.write(outputStream);
            // Flush the stream
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ie) {

            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ie) {

            }
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException ie) {

            }
        }
        return true;
    }


    private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)18);
//        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setFont(titleFont);
        styles.put("title", style);

        Font headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short)12);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setFont(headerFont);
        style.setWrapText(true);

        styles.put("header", style);

        Font bodyFont = wb.createFont();
        bodyFont.setFontHeightInPoints((short)10);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setFont(bodyFont);
        style.setWrapText(true);

        styles.put("cell", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula_2", style);

        return styles;
    }


}
