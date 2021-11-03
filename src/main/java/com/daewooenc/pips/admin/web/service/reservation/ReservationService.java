package com.daewooenc.pips.admin.web.service.reservation;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.dao.reservation.ReservationMapper;
import com.daewooenc.pips.admin.web.domain.dto.reservation.ReservationData;
import com.daewooenc.pips.admin.web.service.api.ApiService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("reservationService")
public class ReservationService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ReservationMapper reservationMapper;

    @Autowired
    private ApiService apiService;

    public void reservationControlRequest() {

        ArrayList<Thread> threads = new ArrayList<Thread>();

        List<String> houscplxCdList = reservationMapper.selectReservationHouscplx();

        for(int i=0; i<houscplxCdList.size(); i++) {

            ReservationData reservationData = new ReservationData();

            String houscplxCd = houscplxCdList.get(i);

            reservationData.setHouscplxCd(houscplxCdList.get(i));

            int houscplxCdListCnt = reservationMapper.selectReservationListCnt(houscplxCd);
            int pageCnt = (int)Math.ceil(houscplxCdListCnt*1.0 / 100);
            int page = 0;

            for(int j=0; j<pageCnt; j++) {
                reservationData.setPage(page);

                List<ReservationData> reservationDataList = reservationMapper.selectReservationList(reservationData);


                Thread t = new Thread(new ReservationServiceThread(reservationDataList));
                t.start();
                threads.add(t);

                page = page + 100;
            }
        }

        for(int i=0; i<threads.size(); i++) {
            Thread t = threads.get(i);
            try {
                t.join();
            }catch(Exception e) {
            }
        }
        System.out.println("Thread_main_end.");
    }

}
