package com.daewooenc.pips.admin.web.service.reservation;

import com.daewooenc.pips.admin.web.dao.reservation.ReservationMapper;
import com.daewooenc.pips.admin.web.domain.dto.reservation.ReservationData;
import com.daewooenc.pips.admin.web.service.api.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationServiceThread implements Runnable{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ReservationMapper reservationMapper;

    private ApiService apiService;

    ReservationData reservationData = new ReservationData();

    List<ReservationData> reservationDataList;

    public ReservationServiceThread(List<ReservationData> reservationDataList) {
        this.reservationDataList = reservationDataList;
    }

    public void run() {

        reservationMapper = (ReservationMapper)BeanUtils.getBean(ReservationMapper.class);
        apiService = (ApiService)BeanUtils.getBean(ApiService.class);

        if(!reservationDataList.isEmpty()) {
            Map<String, Object> reservationMap;
            Map<String, Object> reservationCtrlMap = new HashMap<>();
            Map<String, Object> reservationPushMap = new HashMap<>();
            List<Map<String, Object>> selectCtrlList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> selectPushList = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < reservationDataList.size(); i++) {
                reservationCtrlMap.put("crerId", reservationDataList.get(i).getCrerId());
                reservationPushMap.put("crerId", reservationDataList.get(i).getCrerId());
                if ("push1".equals(reservationDataList.get(i).getPushType())) {
                    reservationMap = new HashMap<String, Object>();
                    reservationMap.put("resrvCtlId", reservationDataList.get(i).getResrvCtlId());
                    selectCtrlList.add(reservationMap);
                } else if ("push2".equals(reservationDataList.get(i).getPushType())) {
                    reservationMap = new HashMap<String, Object>();
                    reservationMap.put("resrvCtlId", reservationDataList.get(i).getResrvCtlId());
                    selectPushList.add(reservationMap);
                }
            }

            if(!selectCtrlList.isEmpty()){
                List<Map<String, Object>> insertCtrlList = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> deleteCtrlList = new ArrayList<Map<String, Object>>();

                reservationCtrlMap.put("selectCtrlList", selectCtrlList);
                List<ReservationData> reservationCtrlList = reservationMapper.selectReservationCtrlList(reservationCtrlMap);

                for(int j = 0; j < reservationCtrlList.size(); j++){
                    if("0".equals(reservationCtrlList.get(j).getCnt())){
                        String hsholdId = reservationCtrlList.get(j).getHsholdId();
                        String resrvCtlId = reservationCtrlList.get(j).getResrvCtlId();

                        apiService.sendControlHsholdId(hsholdId, resrvCtlId);

                        reservationMap = new HashMap<String, Object>();

                        reservationMap.put("crerId", reservationCtrlList.get(j).getCrerId());
                        reservationMap.put("resrvCtlId", reservationCtrlList.get(j).getResrvCtlId());
                        reservationMap.put("pushCtl", "C");

                        insertCtrlList.add(reservationMap);
                    }
                }

                if(!insertCtrlList.isEmpty()) {
                    Map<String, Object> insertCtrlMap = new HashMap<String, Object>();

                    insertCtrlMap.put("insertList", insertCtrlList);
                    reservationMapper.insertReservation(insertCtrlMap);
                }

                for(int j = 0; j < reservationCtrlList.size(); j++){
                    if("0".equals(reservationCtrlList.get(j).getCnt()) && "Y".equals(reservationCtrlList.get(j).getResrvCtlReptYn())) {

                        reservationMap = new HashMap<String, Object>();

                        reservationMap.put("resrvCtlId", reservationCtrlList.get(j).getResrvCtlId());

                        deleteCtrlList.add(reservationMap);
                    }
                }

                if(!deleteCtrlList.isEmpty()) {
                    Map<String, Object> deleteCtrlMap = new HashMap<String, Object>();

                    deleteCtrlMap.put("deleteList", deleteCtrlList);
                    reservationMapper.deleteReservation(deleteCtrlMap);
                }
            }

            if(!selectPushList.isEmpty()){
                List<Map<String, Object>> insertPushList = new ArrayList<Map<String, Object>>();

                reservationPushMap.put("selectPushList", selectPushList);
                List<ReservationData> reservationPushList = reservationMapper.selectReservationPushList(reservationPushMap);

                for(int j = 0; j < reservationPushList.size(); j++){
                    if("0".equals(reservationPushList.get(j).getCnt())){
                        String hsholdId = reservationPushList.get(j).getHsholdId();
                        String resrvCtlId = reservationPushList.get(j).getResrvCtlId();

                        apiService.sendPushHsholdId(hsholdId, resrvCtlId);

                        reservationMap = new HashMap<String, Object>();

                        reservationMap.put("crerId", reservationPushList.get(j).getCrerId());
                        reservationMap.put("resrvCtlId", reservationPushList.get(j).getResrvCtlId());
                        reservationMap.put("pushCtl", "P");

                        insertPushList.add(reservationMap);
                    }
                }

                if(!insertPushList.isEmpty()) {
                    Map<String, Object> insertPushMap = new HashMap<String, Object>();

                    insertPushMap.put("insertList", insertPushList);
                    reservationMapper.insertReservation(insertPushMap);
                }
            }
        }
    }
}
