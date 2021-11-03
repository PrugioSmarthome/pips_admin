package com.daewooenc.pips.admin.web.util;

import com.daewooenc.pips.admin.web.common.WebConsts;
import org.apache.commons.math3.analysis.function.Add;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

    // 오늘 날짜
    public static String getDate(String strFormat, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
        String strResult = simpleDateFormat.format(date);
        return strResult;
    }

    public static Date convertStringForDate(String strDate, String strFormat) throws  ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
        Date date = null;
        date = simpleDateFormat.parse(strDate);

        return date;
    }
    public static String getBaseTime(Calendar cal, int nType) {

        int currentMin = Integer.parseInt(DateUtil.getDate("mm", cal.getTime()));
        Date date = null;
        String strBaseTime = "";
        // 현재 날씨
        if (nType == WebConsts.nCurrent) {
            if (currentMin < 40) {
                cal.add(Calendar.HOUR_OF_DAY, -1);
                date = cal.getTime();
                strBaseTime = DateUtil.getDate("HH", date) + "00";
            } else {
                date = cal.getTime();
                strBaseTime = DateUtil.getDate("HH", date) + "00";
            }
            // 단기 예보
        } else if (nType == WebConsts.nForcast) {
            if (currentMin < 45) {
                cal.add(Calendar.HOUR_OF_DAY, -1);
                date = cal.getTime();
                strBaseTime = DateUtil.getDate("HH", date) + "30";
            } else {
                date = cal.getTime();
                strBaseTime = DateUtil.getDate("HH", date) + "30";
            }
            //동네 예보
        } else if (nType == WebConsts.nSpace) {
            int currentTime = Integer.parseInt(DateUtil.getDate("HH", cal.getTime()));

            if (currentTime >= 2 && currentTime <= 4) {
                strBaseTime = "0200";
            } else if (currentTime >= 5 && currentTime <= 7) {
                strBaseTime = "0500";
            } else if (currentTime >= 8 && currentTime <= 10) {
                strBaseTime = "0800";
            } else if (currentTime >= 11 && currentTime <= 13) {
                strBaseTime = "1100";
            } else if (currentTime >= 14 && currentTime <= 16) {
                strBaseTime = "1400";
            } else if (currentTime >= 17 && currentTime <= 19) {
                strBaseTime = "1700";
            } else if (currentTime >= 20 && currentTime <= 22) {
                strBaseTime = "2000";
            } else {
                strBaseTime = "2300";
            }
        }
        return strBaseTime;
    }

    public static Map<String, String> makeSumSearchDate(int meterDay) {
        Map<String, String> result = new HashMap<>();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        Calendar currentCal = Calendar.getInstance();
//                startCal.set(Calendar.DAY_OF_MONTH, 31);
//                endCal.set(Calendar.DAY_OF_MONTH, 31);
//                currentCal.set(Calendar.DAY_OF_MONTH, 31);
        endCal.add(Calendar.DAY_OF_MONTH, -1);
        int currentDay = currentCal.get(Calendar.DAY_OF_MONTH);

        if (currentDay == meterDay) {
//            startCal.set(Calendar.DAY_OF_MONTH, meterDay);
            startCal.add(Calendar.MONTH, -1);
        } else {
            if(currentDay < meterDay) {

                startCal.add(Calendar.MONTH, -1);
            } else {
                startCal.add(Calendar.DAY_OF_MONTH, -1);
            }
        }
        int startMaxDay = startCal.getActualMaximum(Calendar.DATE);

        if (startMaxDay < meterDay) {
            startCal.set(Calendar.DAY_OF_MONTH, (startMaxDay - 1));
        } else {
            startCal.set(Calendar.DAY_OF_MONTH, (meterDay));
        }

        String startYear = DateUtil.getDate("YYYY", startCal.getTime());
        String startMonth = DateUtil.getDate("MM", startCal.getTime());
        String startDay = DateUtil.getDate("dd", startCal.getTime());

        String endYear = DateUtil.getDate("YYYY", endCal.getTime());
        String endMonth = DateUtil.getDate("MM", endCal.getTime());
        String endDay = DateUtil.getDate("dd", endCal.getTime());

        result.put("startYear", startYear);
        result.put("startMonth", startMonth);
        result.put("startDay", startDay);
        result.put("endYear", endYear);
        result.put("endMonth", endMonth);
        result.put("endDay", endDay);

        String startDate = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, startCal.getTime());
        String endDate = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, endCal.getTime());

        result.put("startDate", startDate);
        result.put("endDate", endDate);
        return result;
    }

    // 검침일 범위 계산(검침일, 에너지데이터 일자)
    public static Map<String, String> makeSumSearchDate2(String meterDay, int energyDay) {
        Map<String, String> result = new HashMap<>();

        Calendar currentCal = Calendar.getInstance();
        int today = Integer.parseInt(DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, currentCal.getTime()));

        String energyMeterDay = String.valueOf(energyDay).substring(0,6) + meterDay;

        // 미래 날짜면
        if(today < energyDay){
            System.out.println("미래날짜 error");
            return result;
        }

        try {
            String energyDayStr = String.valueOf(energyDay);
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(energyDayStr.substring(0, 4)), Integer.parseInt(energyDayStr.substring(4, 6)) - 1, Integer.parseInt(energyDayStr.substring(6, 8)));
            int maxDay = cal.getActualMaximum(cal.DAY_OF_MONTH);

            // 검침일이 에너지데이터 월 말일보다 크면
            if (Integer.parseInt(meterDay) > maxDay) {

                // 에너지데이터가 월 말일보다 작으면
                if (Integer.parseInt(energyDayStr.substring(6, 8)) < maxDay) {
                    String startDate = AddDate(energyDayStr, 0, -1, 0).substring(0, 6) + meterDay;
                    String endDate = energyDayStr.substring(0,6) + String.valueOf(maxDay -1);
                    result.put("startDate", startDate);
                    result.put("endDate", endDate);
                }
                // 에너지데이터가 월 말일이면
                else {
                    String startDate = energyDayStr.substring(0,6) + String.valueOf(maxDay);
                    String endDate = "";
                    if(energyDayStr.substring(4,6).equals("02")){
                        endDate = AddDate(energyMeterDay, 0, 1, -3);
                    } else {
                        endDate = AddDate(energyMeterDay, 0, 1, -1);
                    }
                    result.put("startDate", startDate);
                    result.put("endDate", endDate);
                }
            }
            // 검침일이 에너지데이터 월 말일이거나 그보다 작으면
            else {

                // 에너지데이터가 검침일 이전이면
                if (energyDay < Integer.parseInt(energyMeterDay)) {
                    String startDate = "";
                    if(energyDayStr.substring(4,6).equals("03")){
                        currentCal.set(Integer.parseInt(energyDayStr.substring(0,4)), 01, 01);
                        maxDay = currentCal.getActualMaximum(currentCal.DAY_OF_MONTH);
                        startDate = AddDate(energyDayStr, 0, -1, 0).substring(0, 6) + String.valueOf(maxDay);
                    } else {
                        startDate = AddDate(energyDayStr, 0, -1, 0).substring(0, 6) + meterDay;
                    }
                    String endDate = AddDate(energyMeterDay, 0, 0, -1);
                    result.put("startDate", startDate);
                    result.put("endDate", endDate);
                }
                // 에너지데이터가 검침일이거나 이후면
                else {
                    String startDate = energyMeterDay;
                    String endDate = AddDate(energyMeterDay, 0, 1, -1);
                    result.put("startDate", startDate);
                    result.put("endDate", endDate);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    // 날짜 연산
    public static String AddDate(String strDate, int year, int month, int day) {
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");

        Calendar cal = Calendar.getInstance();
        Date dt = null;
        try {
            dt = dtFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal.setTime(dt);
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DATE, day);

        return dtFormat.format(cal.getTime());
    }

    public static Map<String, String> makeAvgSearchDate(int meterDay) {
        Map<String, String> result = new HashMap<>();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.add(Calendar.MONTH, -1);

        int startMaxDay = startCal.getActualMaximum(Calendar.DATE);
        int endMaxDay = endCal.getActualMaximum(Calendar.DATE);

        if (startMaxDay < meterDay) {
            startCal.set(Calendar.DAY_OF_MONTH, (startMaxDay - 1));
        } else {
            startCal.set(Calendar.DAY_OF_MONTH, (meterDay));
        }

        if (endMaxDay < meterDay) {
            endCal.set(Calendar.DAY_OF_MONTH, (startMaxDay - 1));
        } else {
            endCal.set(Calendar.DAY_OF_MONTH, (meterDay - 1));
        }

        String startYear = DateUtil.getDate("YYYY", startCal.getTime());
        String startMonth = DateUtil.getDate("MM", startCal.getTime());
        String startDay = DateUtil.getDate("dd", startCal.getTime());

        String endYear = DateUtil.getDate("YYYY", endCal.getTime());
        String endMonth = DateUtil.getDate("MM", endCal.getTime());
        String endDay = DateUtil.getDate("dd", endCal.getTime());

        result.put("startYear", startYear);
        result.put("startMonth", startMonth);
        result.put("startDay", startDay);
        result.put("endYear", endYear);
        result.put("endMonth", endMonth);
        result.put("endDay", endDay);

        String startDate = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, startCal.getTime());
        String endDate = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, endCal.getTime());

        result.put("startDate", startDate);
        result.put("endDate", endDate);
        return result;
    }
    public static String getEnergyInsertTargetDate(int meterDa, String approveDate) {
        String result = "";
        try {
            Date date = convertStringForDate(approveDate, WebConsts.strDateFormat_yyyyMMdd);
            Calendar calApprove = Calendar.getInstance();
            Calendar resultCal = Calendar.getInstance();

            calApprove.setTime(date);
            int approveDay = calApprove.get(Calendar.DAY_OF_MONTH);
//        System.out.println("meterDa="+meterDa+" approveDay="+approveDay);
            if (meterDa > approveDay) {
                resultCal.set(Calendar.YEAR, calApprove.get(Calendar.YEAR));
                resultCal.set(Calendar.MONTH, calApprove.get(Calendar.MONTH));
                resultCal.set(Calendar.DAY_OF_MONTH, meterDa);
            } else {
                resultCal.set(Calendar.YEAR, calApprove.get(Calendar.YEAR));
                resultCal.set(Calendar.MONTH, calApprove.get(Calendar.MONTH));
                resultCal.add(Calendar.MONTH, 1);
                resultCal.set(Calendar.DAY_OF_MONTH, meterDa);
            }
            result = getDate("yyyyMMdd", resultCal.getTime());
        } catch (Exception ex) {

        }
        return result;

    }
    // base Date가 compare date보다 크면 false, base date와 compare date가 같거나 base Date가 작으면 true
    public static boolean campareDate(String inputBaseDate, String inputCampareDate) {
        boolean result = false;
        try {
            Date campareDate = convertStringForDate(inputCampareDate, WebConsts.strDateFormat_yyyyMMdd);
            Date baseDate = convertStringForDate(inputBaseDate, WebConsts.strDateFormat_yyyyMMdd);
            Calendar calCampareDate = Calendar.getInstance();
            Calendar calBaseDate = Calendar.getInstance();

            calCampareDate.setTime(campareDate);
            calBaseDate.setTime(baseDate);
            result = calCampareDate.compareTo(calBaseDate) >= 0;


        } catch (Exception ex) {

        }
        return result;

    }



}
