package com.daewooenc.pips.admin.web.dao.reservation;

import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.domain.dto.reservation.ReservationData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 예약 제어 데이터 처리 mapper.
 *
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface ReservationMapper {
	/**
	 * 예약 제어 데이터 조회
	 * */
	List<ReservationData> selectReservationList(ReservationData reservationData);
	/**
	 * 예약 제어 단지코드 조회
	 * */
	List<String> selectReservationHouscplx();
	/**
	 * 예약 제어 데이터 개수 조회
	 * */
	int selectReservationListCnt(String houscplxCd);
	/**
	 * 예약 제어 데이터 조회 Ctrl
	 * */
	List<ReservationData> selectReservationCtrlList(Map<String, Object> reservationDataMap);
	/**
	 * 예약 제어 데이터 조회 Push
	 * */
	List<ReservationData> selectReservationPushList(Map<String, Object> reservationDataMap);
	/**
	 * 예약 제어 히스토리 등록
	 * */
	int insertReservation(Map<String, Object> paramMap);
	/**
	 * 예약 제어 히스토리 삭제
	 * */
	int deleteReservation(Map<String, Object> paramMap);

}
