package com.daewooenc.pips.admin.web.util.excel;

import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizcoExcel;
import com.daewooenc.pips.admin.web.domain.dto.household.Household;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-09-20       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-09-20
 **/
public class ImportExcelUtil {


    /**
     *
     * @param file import할 file
     */
    public static List<Household> importHouseholdExcel(File file) {
        FileInputStream fis = null;
        XSSFWorkbook workbook = null;

        List<Household> householdList = new ArrayList<>();
        try {

            // Create Workbook instance holding reference to .xlsx file
            workbook = new XSSFWorkbook(file);

            // Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int rowCnt = 0;
            int cellCnt = 0;
            String cellValue = "";
            while (rowIterator.hasNext()) {
                cellCnt = 0;
                cellValue = "";

                Household household = new Household();
                Row row = rowIterator.next();
                if (rowCnt != 0) {
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        if (cell.getCellType() == cell.getCellType().STRING) {
                            cellValue = cell.getStringCellValue();
                        } else {
                            cellValue = Math.round(cell.getNumericCellValue()) + "";
                        }
                        if (cellCnt == 0) {
                            if (cellValue == null || "".equals(cellValue)) {
                                break;
                            }
                            String temp = "";
                            //  동의 길이가 4미만일 경우 앞에 0을 붙임
                            if (cellValue.length() < 4) {
                                for(int i = 0;i < 4 - cellValue.length(); i++) {
                                    temp += "0";
                                }
                                cellValue = temp + cellValue;
                            }
                            household.setDongNo(cellValue);

                        } else if  (cellCnt == 1) {
                            String temp = "";
                            //  호의 길이가 4미만일 경우 앞에 0을 붙임
                            if (cellValue.length() < 4) {
                                for(int i = 0;i < 4 - cellValue.length(); i++) {
                                    temp += "0";
                                }
                                cellValue = temp + cellValue;
                            }
                            household.setHoseNo(cellValue);
                        } else if  (cellCnt == 2) {
                            household.setDimQty(cellValue);
                            // 에너지 평형 구하기
                            int value = Integer.parseInt(cellValue);
                            int mok = value/5;
                            int remainder = value%5;
                            int energyPvalue = 0;
                            if (remainder >= 0 && remainder <= 2) {
                                energyPvalue = mok * 5;
                            } else {
                                energyPvalue = mok * 5 + 5;
                            }
                            household.setEnrgDimQty(energyPvalue+"");

                        } else if  (cellCnt == 3) {
                            household.setPtypeNm(cellValue);
                        }
                        cellCnt++;
                    }
                    if (cellValue != null && !"".equals(cellValue)) {
                        householdList.add(household);
                    }
                }
                rowCnt++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }

        return householdList;
    }

    public static List<FacilityBizcoExcel>  importFacilityBizcoExcel(File file) {
        FileInputStream fis = null;
        XSSFWorkbook workbook = null;

        List<FacilityBizcoExcel> facilityBizcoListExcel = new ArrayList<>();
        try {

            // Create Workbook instance holding reference to .xlsx file
            workbook = new XSSFWorkbook(file);

            // Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int rowCnt = 0;
            int cellCnt = 0;
            String cellValue = "";
            while (rowIterator.hasNext()) {
                cellCnt = 0;
                cellValue = "";

                FacilityBizcoExcel facilityBizcoExcel = new FacilityBizcoExcel();
                Row row = rowIterator.next();
                if (rowCnt != 0) {

                    // For each row, iterate through all the columns
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        if (cell.getCellType() == cell.getCellType().STRING) {
                            cellValue = cell.getStringCellValue();
                        } else {
                            cellValue = Math.round(cell.getNumericCellValue()) + "";
                        }
                        if (cellCnt == 0) {
                            if (cellValue == null || "".equals(cellValue)) {
                                break;
                            }
                            facilityBizcoExcel.setFacltBizcoTpNm(cellValue);
                        } else if  (cellCnt == 1) {
                            facilityBizcoExcel.setTwbsNm(cellValue);
                        } else if  (cellCnt == 2) {
                            facilityBizcoExcel.setBizcoNm(cellValue);
                        } else if  (cellCnt == 3) {
                            facilityBizcoExcel.setConCont(cellValue);
                        } else if  (cellCnt == 4) {
                            facilityBizcoExcel.setPerchrgNm(cellValue);
                        } else if  (cellCnt == 5) {
                            facilityBizcoExcel.setOffcPhoneNo(cellValue);
                        } else if  (cellCnt == 6) {
                            facilityBizcoExcel.setFaxNo(cellValue);
                        } else if  (cellCnt == 7) {
                            facilityBizcoExcel.setMphoneNo(cellValue);
                        }

                        cellCnt++;

                    }
                    if (cellValue != null && !"".equals(cellValue)) {
                        facilityBizcoListExcel.add(facilityBizcoExcel);
                    }
                }
                rowCnt++;
            }
            fis = new FileInputStream(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }

        return facilityBizcoListExcel;
    }
}