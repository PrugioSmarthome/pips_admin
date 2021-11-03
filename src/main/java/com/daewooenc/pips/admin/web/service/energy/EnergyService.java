package com.daewooenc.pips.admin.web.service.energy;

import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.common.WebConsts;
import com.daewooenc.pips.admin.web.dao.energy.EnergyMapper;
import com.daewooenc.pips.admin.web.dao.household.HouseholdMapper;
import com.daewooenc.pips.admin.web.dao.housingcplx.HousingCplxMapper;
import com.daewooenc.pips.admin.web.domain.dto.energy.EnergyAlarmTarget;
import com.daewooenc.pips.admin.web.domain.dto.energy.EnergyData;
import com.daewooenc.pips.admin.web.domain.dto.energy.EnergyPush;
import com.daewooenc.pips.admin.web.domain.dto.household.HouseholdUserItem;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import com.daewooenc.pips.admin.web.domain.vo.energy.EnergyCtrVo;
import com.daewooenc.pips.admin.web.domain.vo.energy.EnergyDataVo;
import com.daewooenc.pips.admin.web.domain.vo.energy.EnergyEventDataVo;
import com.daewooenc.pips.admin.web.domain.vo.energy.EnergyPushVo;
import com.daewooenc.pips.admin.web.util.DateUtil;
import com.daewooenc.pips.admin.web.util.HTTPClientUtil;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-08-26       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-26
 **/
@Service
public class EnergyService {

    /**
     * 로그 출력.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HousingCplxMapper housingCplxMapper;
    @Autowired
    HouseholdMapper householdMapper;
    @Autowired
    EnergyMapper energyMapper;
    @Autowired
    XSSUtil xssUtil;

    private @Value("${pips.serviceServer.url}") String serviceServerURL;
    private @Value("${pips.serviceServer.auth}") String serviceServerAuth;
    private @Value("${pips.serviceServer.path.energy}") String energyPath;
    private @Value("${pips.serviceServer.path.energy.push}") String energyPush;

    public void energyControlRequest() {
        HousingCplx housingCplx = new HousingCplx();
        housingCplx.setDelYn("N");
        List<HousingCplx> housingCplxList= housingCplxMapper.selectAllHousingCplxList(housingCplx);
        for(HousingCplx housingCplxDB : housingCplxList) {
            EnergyCtrVo energyCtrVo = new EnergyCtrVo(housingCplxDB.getHmnetId(), housingCplxDB.getHouscplxCd(), 100, "Batch");
            String jsonData = JsonUtil.toJson(energyCtrVo);
            // 홈넷 설정 제어 URL 20
            serviceServerURL = xssUtil.replaceAll(StringUtils.defaultString(serviceServerURL));
            energyPath = xssUtil.replaceAll(StringUtils.defaultString(energyPath));
            serviceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(serviceServerAuth));

            String strSvcSvrURL = serviceServerURL + energyPath;

            HTTPClientUtil httpClientUtil = new HTTPClientUtil();
            //  Service Server 제어 요청
            HttpResult httpResult = httpClientUtil.sendData(strSvcSvrURL, jsonData, serviceServerAuth);
            if ("201".equals(httpResult.getStatus())) {
                logger.info("Energy Data Request Success. ComplexCode : " + housingCplxDB.getHouscplxCd()+" Homenet ID : "+housingCplxDB.getHmnetId());
            } else {
                logger.info("Energy Data Request Fail. ComplexCode : " + housingCplxDB.getHouscplxCd()+" Homenet ID : "+housingCplxDB.getHmnetId());
            }
        }
    }

    /*
    에너지 이벤트 데이터를 DB에 저장
     */
    public void energyDataProcess(EnergyEventDataVo energyEventDataVo) {
        logger.debug("에너지 이벤트 데이터 처리");
        EnergyData energyData = null;
        EnergyData newEnergyData = new EnergyData();

        String strEnergyEventDate = energyEventDataVo.getDate();

        HashMap<String, String> hsHoldApproveMap = new HashMap<>();


        Date energyDate = null;
        List<EnergyData> energyDataList = null;
        String node_id = null;
        String houscplx_cd = null;
        int meterDay = 1;

        try {
            energyDate = DateUtil.convertStringForDate(strEnergyEventDate, WebConsts.strDateFormat_yyyyMMdd);
            String energyYear = DateUtil.getDate(WebConsts.strDateFormat_yyyy, energyDate);
            String energyMonth = DateUtil.getDate(WebConsts.strDateFormat_MM, energyDate);
            String energyDay = DateUtil.getDate(WebConsts.strDateFormat_dd, energyDate);

            energyDataList = new ArrayList<>(energyEventDataVo.getEnergy_data_info().size());

            // 전송된 에너지 데이터에서 단지 코드 확인
            if (energyEventDataVo.getEnergy_data_info().size() > 0) {
                node_id = energyEventDataVo.getEnergy_data_info().get(0).getNodeId();
                houscplx_cd = node_id.split("\\.")[0];
            }

            HousingCplx housingCplx = new HousingCplx();
            housingCplx.setDelYn("N");
            housingCplx.setHouscplxCd(houscplx_cd);
            // 단지 검침일 확인
            List<HousingCplx> housingCplxList = housingCplxMapper.selectAllHousingCplxList(housingCplx);

            /*
            검칠일을 1일로 고정함에 따른 삭제
            for(HousingCplx housingCplxDB : housingCplxList) {
                meterDay = Integer.parseInt(housingCplxDB.getEnrgMeasYmd());
            }*/

            // 세대 승인 날짜 조회
            List<HouseholdUserItem> houseHoldApproveList = householdMapper.selectHouseHoldApproveList(houscplx_cd+"%");
            for(HouseholdUserItem householdUserItem : houseHoldApproveList) {
                String approveDate = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, householdUserItem.getApprDt());
                hsHoldApproveMap.put(householdUserItem.getHsholdId(), approveDate);
            }

            for (int i = 0; i < energyEventDataVo.getEnergy_data_info().size(); i++) {
                EnergyDataVo energyDataVo = energyEventDataVo.getEnergy_data_info().get(i);
                node_id = energyDataVo.getNodeId();
                logger.debug("hsHoldApproveMap=============="+hsHoldApproveMap+" node_id="+node_id);
                String approveDate = hsHoldApproveMap.get(node_id);

                logger.debug("approveDate=============="+approveDate);
//                String insertTargetDate = DateUtil.getEnergyInsertTargetDate(meterDay, approveDate);

//                logger.debug("insertTargetDate=============="+insertTargetDate);
                logger.debug("strEnergyEventDate=============="+strEnergyEventDate);
//                logger.debug("DateUtil.campareDate(insertTargetDate, strEnergyEventDate)=============="+DateUtil.campareDate(insertTargetDate, strEnergyEventDate));
                // 에너지 발생 date가  insertTargetDate보다 크면 DB에 insert
//                if (DateUtil.campareDate(insertTargetDate, strEnergyEventDate)) {
                boolean bResult = DateUtil.campareDate(approveDate, strEnergyEventDate);
                if (bResult) {
                    node_id = energyDataVo.getNodeId();

                    // 000011(시흥센트럴푸르지오) 단지이며, 1일이 아닐 경우
                    if ("000011".equals(houscplx_cd) && !"01".equals(energyDay)){
                        newEnergyData.setHsholdId(node_id);
                        newEnergyData.setHouscplxCd(houscplx_cd);
                        newEnergyData.setYr(energyYear);
                        newEnergyData.setMm(energyMonth);

                        // 기존 DB의 누적 전기 사용량
                        double elctUseQtyDb = Double.parseDouble(energyMapper.selectElctUseQty(newEnergyData));
                        // 수신받은 누적 전기 사용량
                        double elctUseQtyHmnet = Double.parseDouble(energyDataVo.getElectricity());
                        // 1일 전기 사용량 = 수신받은 누적 전기 사용량 - 기존 DB의 누적 전기 사용량
                        double elctUseQtyVal = elctUseQtyHmnet - elctUseQtyDb;

                        // 소수점 3자리수
                        String elctUseQty = String.format("%.3f", elctUseQtyVal);

                        if("0".equals(energyDataVo.getElectricity())){
                            elctUseQty = "0";
                        }

                        energyDataVo.setElectricity(elctUseQty);
                    }

                    // 평형 정보는 데이터 등록하는 쿼리에서 취득하여 입력
                    energyData = new EnergyData(houscplx_cd, node_id, "", energyYear, energyMonth, energyDay, strEnergyEventDate, energyDataVo.getGas(), energyDataVo.getElectricity(), energyDataVo.getWater(), energyDataVo.getHotwater(), energyDataVo.getHeating(), "Batch", "");
                    energyDataList.add(energyData);
                }
            }

            if (energyDataList.size() > 0) {
                energyMapper.insertEnergyData(energyDataList);
            }

            // 단지 에너지 데이터가 모두 전송이 되었을 경우 누적 사용량을 구함. 또는 검침일에 해당 할 경우 평형별 평균 사용량을 구함
            if (energyEventDataVo.getPageCount().equals(energyEventDataVo.getTotalCount())) {
                // 현재 날짜와 검치일과 일치 시 평균 사용량 or (말일 == 현재날짜 and 말일 <= 검침일
                Calendar cal = Calendar.getInstance();
                int currentMaxDay = cal.getActualMaximum(Calendar.DATE);
                int currentDay = cal.get(Calendar.DAY_OF_MONTH);

                for(HousingCplx housingCplxDB : housingCplxList) {
                    // 에너지 데이터에 대한 누적할 조건
                    Map<String, String> sumConditionMap = DateUtil.makeSumSearchDate(meterDay);
                    //Map<String, String> map = DateUtil.makeSumSearchDate2(meterDay, 20201230);
                    Map<String, String> avgConditionMap = DateUtil.makeAvgSearchDate(meterDay);
                    sumConditionMap.put("housingCplxCd", housingCplxDB.getHouscplxCd());
                    avgConditionMap.put("housingCplxCd", housingCplxDB.getHouscplxCd());
                    // 누적 사용량
                    try {
                        energyMapper.insertSumEnergyData(sumConditionMap);
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    // 평형별 평균 사용량 : 현재 날짜와 검치일과 일치 시 평균 사용량 or (말일 == 현재날짜 and 말일 <= 검침일
                    if ((currentDay == meterDay) || (currentMaxDay == currentDay && currentMaxDay < meterDay)) {
                        try {
                            energyMapper.insertAvgEnergyData(avgConditionMap);
                        }catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }catch (ParseException pe) {
            logger.error("Energy Date Parsing Fail : " + strEnergyEventDate);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void energyDataUseAlarmProcess() {
        logger.debug("에너지 Push 데이터 처리");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String targetDate = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, cal.getTime());
        // 임계치를 초과한 세대별 에너지 종류별 데이터 구함
        List<EnergyAlarmTarget> energyAlarmSendDto= energyMapper.selectEnergyAlarmSendList(targetDate);
        List<EnergyPush> energyPushList = new ArrayList<>();
        List<EnergyPushVo> energyPushVoList = new ArrayList<>();
        EnergyPushVo energyPushVo = null;
        EnergyPush energyPush = null;


        for(EnergyAlarmTarget energyAlarmSend : energyAlarmSendDto) {
            logger.debug("energyAlarmSend.getElctSend1()="+energyAlarmSend.getElctSend1());
            logger.debug("energyAlarmSend.getElctSend2()="+energyAlarmSend.getElctSend2());
            logger.debug("energyAlarmSend.getElctRate()="+energyAlarmSend.getElctRate());
            // 전기 사용률 90%이상일 경우
            if (energyAlarmSend.getElctRate() >= 90) {
                // 90%이상 사용 시 사용자에게 Push
                if ("0".equals(energyAlarmSend.getElctSend1())) {
                    energyPush = new EnergyPush();
                    energyPush.setEnrgTpCd(WebConsts.ENRG_TP_CD_ELCT);
                    energyPush.setHouscplxCd(energyAlarmSend.getHouscplxCd());
                    energyPush.setHsholdId(energyAlarmSend.getHsholdId());
                    energyPush.setMm(energyAlarmSend.getMm());
                    energyPush.setYr(energyAlarmSend.getYr());
                    energyPush.setStep1TrnsmYn("Y");
                    energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_ELCT,  WebConsts.ENERGY_PUSH_LEVEL_1);
                    energyPushVoList.add(energyPushVo);
                    // 100%이상 사용 시 사용자에게 Push
                    if (energyAlarmSend.getElctRate() >= 100) {
                        // push 미발송 상태
                        if ("0".equals(energyAlarmSend.getElctSend2())) {
                            // 발송으로 변경
                            energyPush.setStep2TrnsmYn("Y");
                            energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_ELCT,  WebConsts.ENERGY_PUSH_LEVEL_2);
                            energyPushVoList.add(energyPushVo);
                            // push 발송 상태
                        } else {
                            energyPush.setStep2TrnsmYn("Y");
                        }
                    } else {
                        // push 미발송 상태
                        energyPush.setStep2TrnsmYn("N");
                    }
                    energyPushList.add(energyPush);
                    // 90%이상 사용은 Push, 100%이상 사용 확인
                } else {
                    if (energyAlarmSend.getElctRate() >= 100 && "0".equals(energyAlarmSend.getElctSend2())) {
                        energyPush = new EnergyPush();
                        energyPush.setEnrgTpCd(WebConsts.ENRG_TP_CD_ELCT);
                        energyPush.setHouscplxCd(energyAlarmSend.getHouscplxCd());
                        energyPush.setHsholdId(energyAlarmSend.getHsholdId());
                        energyPush.setMm(energyAlarmSend.getMm());
                        energyPush.setYr(energyAlarmSend.getYr());
                        energyPush.setStep1TrnsmYn("Y");
                        energyPush.setStep2TrnsmYn("Y");

                        energyPushList.add(energyPush);

                        energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_ELCT,  WebConsts.ENERGY_PUSH_LEVEL_2);
                        energyPushVoList.add(energyPushVo);

                    }
                }
            } // if (energyAlarmSend.getElctRate() >= 90) {
            // 가스 사용률 90%이상일 경우
            if (energyAlarmSend.getGasRate() >= 90) {
                // 90%이상 사용 시 사용자에게 Push
                if ("0".equals(energyAlarmSend.getGasSend1())) {
                    energyPush = new EnergyPush();
                    energyPush.setEnrgTpCd(WebConsts.ENRG_TP_CD_GAS);
                    energyPush.setHouscplxCd(energyAlarmSend.getHouscplxCd());
                    energyPush.setHsholdId(energyAlarmSend.getHsholdId());
                    energyPush.setMm(energyAlarmSend.getMm());
                    energyPush.setYr(energyAlarmSend.getYr());
                    energyPush.setStep1TrnsmYn("Y");
                    energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_GAS,  WebConsts.ENERGY_PUSH_LEVEL_1);
                    energyPushVoList.add(energyPushVo);
                    // 100%이상 사용 시 사용자에게 Push
                    if (energyAlarmSend.getGasRate() >= 100) {
                        // push 미발송 상태
                        if ("0".equals(energyAlarmSend.getGasRend2())) {
                            // push 발송 상태로 변경
                            energyPush.setStep2TrnsmYn("Y");
                            energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_GAS,  WebConsts.ENERGY_PUSH_LEVEL_2);
                            energyPushVoList.add(energyPushVo);
                            // push 발송 상태
                        } else {
                            energyPush.setStep2TrnsmYn("Y");
                        }
                        // push 미발송 상태
                    } else {
                        energyPush.setStep2TrnsmYn("N");
                    }
                    energyPushList.add(energyPush);
                    // 90%이상 사용은 Push, 100%이상 사용 확인
                } else {
                    if (energyAlarmSend.getGasRate() >= 100 && "0".equals(energyAlarmSend.getGasRend2())) {
                        energyPush = new EnergyPush();
                        energyPush.setEnrgTpCd(WebConsts.ENRG_TP_CD_GAS);
                        energyPush.setHouscplxCd(energyAlarmSend.getHouscplxCd());
                        energyPush.setHsholdId(energyAlarmSend.getHsholdId());
                        energyPush.setMm(energyAlarmSend.getMm());
                        energyPush.setYr(energyAlarmSend.getYr());
                        energyPush.setStep1TrnsmYn("Y");
                        energyPush.setStep2TrnsmYn("Y");

                        energyPushList.add(energyPush);

                        energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_GAS,  WebConsts.ENERGY_PUSH_LEVEL_2);
                        energyPushVoList.add(energyPushVo);
                    }
                }
            } // if (energyAlarmSend.getGasRate() >= 90) {
            // 가스 사용률 90%이상일 경우
            if (energyAlarmSend.getHeatRate() >= 90) {
                // 90%이상 사용 시 사용자에게 Push
                if ("0".equals(energyAlarmSend.getHeatSend1())) {
                    energyPush = new EnergyPush();
                    energyPush.setEnrgTpCd(WebConsts.ENRG_TP_CD_HEAT);
                    energyPush.setHouscplxCd(energyAlarmSend.getHouscplxCd());
                    energyPush.setHsholdId(energyAlarmSend.getHsholdId());
                    energyPush.setMm(energyAlarmSend.getMm());
                    energyPush.setYr(energyAlarmSend.getYr());
                    energyPush.setStep1TrnsmYn("Y");
                    energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_HEAT,  WebConsts.ENERGY_PUSH_LEVEL_1);
                    energyPushVoList.add(energyPushVo);
                    // 100%이상 사용 시 사용자에게 Push
                    if (energyAlarmSend.getHeatRate() >= 100) {
                        // push 미발송 상태
                        if ("0".equals(energyAlarmSend.getHeatSend2())) {
                            // push 발송 상태로 변경
                            energyPush.setStep2TrnsmYn("Y");
                            energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_HEAT,  WebConsts.ENERGY_PUSH_LEVEL_2);
                            energyPushVoList.add(energyPushVo);
                            // push 발송 상태
                        } else {
                            energyPush.setStep2TrnsmYn("Y");
                        }
                        // push 미발송 상태
                    } else {
                        energyPush.setStep2TrnsmYn("N");
                    }
                    energyPushList.add(energyPush);
                    // 90%이상 사용은 Push, 100%이상 사용 확인
                } else {
                    if (energyAlarmSend.getHeatRate() >= 100 && "0".equals(energyAlarmSend.getHeatSend2())) {
                        energyPush = new EnergyPush();
                        energyPush.setEnrgTpCd(WebConsts.ENRG_TP_CD_HEAT);
                        energyPush.setHouscplxCd(energyAlarmSend.getHouscplxCd());
                        energyPush.setHsholdId(energyAlarmSend.getHsholdId());
                        energyPush.setMm(energyAlarmSend.getMm());
                        energyPush.setYr(energyAlarmSend.getYr());
                        energyPush.setStep1TrnsmYn("Y");
                        energyPush.setStep2TrnsmYn("Y");

                        energyPushList.add(energyPush);

                        energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_HEAT,  WebConsts.ENERGY_PUSH_LEVEL_2);
                        energyPushVoList.add(energyPushVo);
                    }
                }
            } // if (energyAlarmSend.getHeatRate() >= 90) {

            // 온수 사용률 90%이상일 경우
            if (energyAlarmSend.getHotwtrRate() >= 90) {
                // 90%이상 사용 시 사용자에게 Push
                if ("0".equals(energyAlarmSend.getHotwtrSend1())) {
                    energyPush = new EnergyPush();
                    energyPush.setEnrgTpCd(WebConsts.ENRG_TP_CD_HOTWTR);
                    energyPush.setHouscplxCd(energyAlarmSend.getHouscplxCd());
                    energyPush.setHsholdId(energyAlarmSend.getHsholdId());
                    energyPush.setMm(energyAlarmSend.getMm());
                    energyPush.setYr(energyAlarmSend.getYr());
                    energyPush.setStep1TrnsmYn("Y");
                    energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_HOTWTR,  WebConsts.ENERGY_PUSH_LEVEL_1);
                    energyPushVoList.add(energyPushVo);
                    // 100%이상 사용 시 사용자에게 Push
                    if (energyAlarmSend.getHotwtrRate() >= 100) {
                        // push 미발송 상태
                        if ("0".equals(energyAlarmSend.getHotwtrSend2())) {
                            // push 발송 상태로 변경
                            energyPush.setStep2TrnsmYn("Y");
                            energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_HOTWTR,  WebConsts.ENERGY_PUSH_LEVEL_2);
                            energyPushVoList.add(energyPushVo);
                            // push 발송 상태
                        } else {
                            energyPush.setStep2TrnsmYn("Y");
                        }
                        // push 미발송 상태
                    } else {
                        energyPush.setStep2TrnsmYn("N");
                    }
                    energyPushList.add(energyPush);
                    // 90%이상 사용은 Push, 100%이상 사용 확인
                } else {
                    if (energyAlarmSend.getHotwtrRate() >= 100 && "0".equals(energyAlarmSend.getHotwtrSend2())) {
                        energyPush = new EnergyPush();
                        energyPush.setEnrgTpCd(WebConsts.ENRG_TP_CD_HOTWTR);
                        energyPush.setHouscplxCd(energyAlarmSend.getHouscplxCd());
                        energyPush.setHsholdId(energyAlarmSend.getHsholdId());
                        energyPush.setMm(energyAlarmSend.getMm());
                        energyPush.setYr(energyAlarmSend.getYr());
                        energyPush.setStep1TrnsmYn("Y");
                        energyPush.setStep2TrnsmYn("Y");

                        energyPushList.add(energyPush);

                        energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_HOTWTR,  WebConsts.ENERGY_PUSH_LEVEL_2);
                        energyPushVoList.add(energyPushVo);
                    }
                }
            } // if (energyAlarmSend.getHotwtrRate() >= 90) {

            // 수도 사용률 90%이상일 경우
            if (energyAlarmSend.getWtrsplRate() >= 90) {
                // 90%이상 사용 시 사용자에게 Push
                if ("0".equals(energyAlarmSend.getWtrsplSend1())) {
                    energyPush = new EnergyPush();
                    energyPush.setEnrgTpCd(WebConsts.ENRG_TP_CD_WTRSPL);
                    energyPush.setHouscplxCd(energyAlarmSend.getHouscplxCd());
                    energyPush.setHsholdId(energyAlarmSend.getHsholdId());
                    energyPush.setMm(energyAlarmSend.getMm());
                    energyPush.setYr(energyAlarmSend.getYr());
                    energyPush.setStep1TrnsmYn("Y");
                    energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_WTRSPL,  WebConsts.ENERGY_PUSH_LEVEL_1);
                    energyPushVoList.add(energyPushVo);
                    // 100%이상 사용 시 사용자에게 Push
                    if (energyAlarmSend.getWtrsplRate() >= 100) {
                        // push 미발송 상태
                        if ("0".equals(energyAlarmSend.getWtrsplSend2())) {
                            // push 발송 상태로 변경
                            energyPush.setStep2TrnsmYn("Y");
                            energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_HOTWTR,  WebConsts.ENERGY_PUSH_LEVEL_2);
                            energyPushVoList.add(energyPushVo);
                            // Push 발송 상태
                        } else {
                            // 이미 보냈음
                            energyPush.setStep2TrnsmYn("Y");
                        }
                        // push 미발송 상태
                    } else {
                        energyPush.setStep2TrnsmYn("N");
                    }
                    energyPushList.add(energyPush);
                    // 90%이상 사용은 Push, 100%이상 사용 확인
                } else {
                    if (energyAlarmSend.getWtrsplRate() >= 100 && "0".equals(energyAlarmSend.getWtrsplSend2())) {
                        energyPush = new EnergyPush();
                        energyPush.setEnrgTpCd(WebConsts.ENRG_TP_CD_WTRSPL);
                        energyPush.setHouscplxCd(energyAlarmSend.getHouscplxCd());
                        energyPush.setHsholdId(energyAlarmSend.getHsholdId());
                        energyPush.setMm(energyAlarmSend.getMm());
                        energyPush.setYr(energyAlarmSend.getYr());
                        energyPush.setStep1TrnsmYn("Y");
                        energyPush.setStep2TrnsmYn("Y");

                        energyPushList.add(energyPush);

                        energyPushVo = new EnergyPushVo(energyAlarmSend.getHsholdId(), WebConsts.ENRG_TP_CD_HOTWTR,  WebConsts.ENERGY_PUSH_LEVEL_2);
                        energyPushVoList.add(energyPushVo);
                    }
                }
            } // if (energyAlarmSend.getWtrsplRate() >= 90) {
        } // for(EnergyAlarmTargetDto energyAlarmSend : energyAlarmSendDto) {
        if (energyPushList.size() > 0) {

            for(EnergyPush energyPushData : energyPushList) {
                energyMapper.insertEnergyPushData(energyPushData);
            }
            if (energyPushVoList.size() > 0) {
                serviceServerURL = xssUtil.replaceAll(StringUtils.defaultString(serviceServerURL));
                energyPath = xssUtil.replaceAll(StringUtils.defaultString(energyPath));
                serviceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(serviceServerAuth));

                String sendData = JsonUtil.toJson(energyPushVoList);
                String sendUrl = serviceServerURL + this.energyPush;
                HTTPClientUtil httpClientUtil = new HTTPClientUtil();
                httpClientUtil.sendData(sendUrl, sendData, serviceServerAuth);
            }
        }
    }
}