package com.daewooenc.pips.admin.web.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * 시스템 상수 정의 클래스
 *
 */
public abstract class WebConsts {
	/**
	 * 날씨 상수
	 */
	/** 날씨 조회 결과 코드 */
	public static final String WEATHER_RESULT = "00";
	/** 날씨 유형 코드 */
	// 현재 날씨
	public static final String WEATHER_TYPE_CURRENT = "CURRENT_WEATHER";
	// 예보 날씨
	public static final String WEATHER_TYPE_FORECAST = "FORECAST_WEATHER";
	/** 날씨 코드 */
	// 현재 기온
	public static final String WEATHER_T1H = "T1H";
	// 1시간 강수량
	public static final String WEATHER_RN1 = "RN1";
	// 동서바람성분
	public static final String WEATHER_UUU= "UUU";
	// 남북바람성분
	public static final String WEATHER_VVV = "VVV";
	// 습도
	public static final String WEATHER_REH = "REH";
	// 강수형태
	public static final String WEATHER_PTY = "PTY";
	// 풍향
	public static final String WEATHER_VEC = "VEC";
	// 풍속
	public static final String WEATHER_WSD = "WSD";
	// 강수확률
	public static final String WEATHER_POP = "POP";
	// 하늘상태
	public static final String WEATHER_SKY = "SKY";
	// 최저기온
	public static final String WEATHER_TMN = "TMN";
	// 최고기온
	public static final String WEATHER_TMX = "TMX";


	public static String strDateFormat_yyyyMMdd = "yyyyMMdd";
	public static String strDateFormat_yyyy = "yyyy";
	public static String strDateFormat_MM = "MM";
	public static String strDateFormat_dd = "dd";
	public static String strDateFormat_yyyyMMddHHmmss_1 = "yyyyMMddHHmmss";
	public static String strDateFormat_yyyyMMddHHmmss_2 = "yyyy-MM-dd HH:mm:ss";
	public static String strDateFormat_HHmm = "HHmm";
	public static String strDateFormat_HH = "HH";
	public static String strDateFormat_mm = "mm";

	public static int nCurrent = 1;
	public static int nForcast = 2;
	public static int nSpace = 3;

	public static String[] ExcelHeaderSystemMgmtCompany = {"구분", "대공종", "업체명", "공사내역", "직급", "성명", "사무실", "FAX", "H.P", "비고"};

	public static String[] ExcelHeaderHomenet = {"순번","Homenet ID", "Homenet Key", "Server 유형", "업체", "Serial Number", "Domain",
			"서버 상태", "Kepp Alive 주기", "데이터 전송 주기", "제어 Timeout", "사용유무", "삭제유무", "생성자", "수정자", "생성일시", "수정일시"};

	public static String[] ExcelKeyOrderHomenet = {"rownum", "hmnetId", "hmnetKeyCd", "svrTpCd", "bizcoCd", "serlNo", "urlCont", "stsCd", "keepAliveCycleCont",
			"datTrnsmCycleCont", "ctlExprtnCycleCont", "useYn", "delYn", "crerId", "editerId", "crDt", "editDt"};

	public static String[] ExcelHeaderHouseholdDevice = {"단지 이름","동", "호", "조명", "가스밸브", "에어컨", "난방",
			"환기", "콘센트", "전동커튼"};

	public static String[] ExcelKeyOrderHouseholdDevice = {"houscplx_nm", "dong_no", "hose_no", "lights", "gaslock", "aircon", "heating", "ventilator", "smart_consent",
			"curtain"};


	// 에너지 유형 코드 : 전기
	public static String ENRG_TP_CD_ELCT = "ELCT";
	// 에너지 유형 코드 : 온수
	public static String ENRG_TP_CD_HOTWTR = "HOTWTR";
	// 에너지 유형 코드 : 난방
	public static String ENRG_TP_CD_HEAT = "HEAT";
	// 에너지 유형 코드 : 가스
	public static String ENRG_TP_CD_GAS = "GAS";
	// 에너지 유형 코드 : 수도
	public static String ENRG_TP_CD_WTRSPL = "WTRSPL";
	// 홈넷 업체 코드
	public static String BIZCO_CD = "BIZCO_CD";
	// 서버 유형 코드
	public static String SVR_TP_CD = "SVR_TP_CD";

	// 에너지 Push Level
	public static int ENERGY_PUSH_LEVEL_1 = 1;
	public static int ENERGY_PUSH_LEVEL_2 = 2;

	/** 서비스 연동 그룹 코드. */
	public static final String EXTERNAL_SERVICE_GROUP_CODE = "PUBLIC_DATA";

	/** 현재 날씨 코드. */
	public static final String WEATER_CURRENT_CODE = "CURRENT_WEATHER";
	/** 단기 예보*/
	public static final String WEATER_FORECAST_CODE = "FORECAST_WEATHER";
	/** 동네 날씨 예보*/
	public static final String WEATER_SPACE_CODE = "SPACE_WEATHER";
	/** 기상 특보 */
	public static final String WEATER_SPECIAL_CODE = "SPECIAL_WEATHER";
	/** 미세먼지*/
	public static final String FINE_DUTY_CODE = "FINE_DUTY";

	public static String[] FINEDUTY_TARGET = {"서울", "부산", "대구", "인천", "광주", "대전", "울산", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "세종"};

	public static String HTTP_METHOD_POST = "POST";
	public static String HTTP_METHOD_PUT = "PUT";
	public static String HTTP_METHOD_GET = "GET";
	public static String HTTP_METHOD_DELETE = "DELETE";

	// 제어 결과 Json attribute
	public static String CTL_TP_GRP_CD = "ctl_tp_grp_cd" ;
	public static String CTL_STS_CD = "ctl_sts_cd" ;
	public static String CTL_TP_CD = "ctl_tp_cd" ;
	public static String USER_ID = "user_id" ;
	public static String CTL_DEM_ORGNL_CONT = "ctl_dem_orgnl_cont" ;
	public static String CTL_OUTCOM_CONT = "ctl_outcom_cont" ;
	public static String ADD_CONT = "add_cont" ;

	// 제어 결과 Json value
	// 시스템 제어 유형 코드
	public static String SYS_CTL_TP_CD = "SYS_CTL_TP_CD";
	// 제어 상태 : SUCCESS
	public static String SUCCESS = "SUCCESS";
	// 제어 상태 : FAIL
	public static String D_NACK = "D_NACK";
	public static String TIME_OUT = "TIME_OUT";
	public static String PART_FAIL = "PART_FAIL";
	public static String FAIL = "FAIL";


	// 제어 대상 : 단지
	public static String TARGET_TYPE_DEVICE = "device";
	// 제어 대상 : DEVICE
	public static String TARGET_TYPE_COMPLEX = "complex";


	// 이벤트 데이터 Json attribute
	// 데이터 유형 코드
	public static String DAT_TP_CD = "dat_tp_cd" ;

	// 이벤트 데이터 내용
	public static String CONT = "cont" ;
	// 이벤트 데이터 종류
	public static String TYPE = "type" ;

	// 이벤트 데이터 Json value
	// 에너지 데이터
	public static String ENERGY_DATA = "ENERGY_DATA" ;
	// Device 상태 데이터
	public static String DEVICE_STATUS = "DEVICE_STATUS" ;


	//mongodb doc
	// 외부 API 사용 이력
	public static String SVC_API_HIST = "svc.api.hist";
	// 사용자 로그인 이력
	public static String USER_LOGIN_HIST = "user.login.hist";

	// 홈넷 설정 제어
	public static String DEVICE_CONF = "DEVICE_CONF";
	// 세대 등록 제어
	public static String NODE_REGI = "NODE_REGI";
	// 세대 사용자 수정 제어
	public static String USER_MODIFY = "USER_MODIFY";
	// 사용자 삭제
	public static String USER_DEL = "USER_DEL";
	// 장치 정보 조회 제어
	public static String ATTACHED_DEVICE_INFO = "ATTACHED_DEVICE_INFO";
	// 세대내 장치 상태 조회 제어
	public static String ATTACHED_DEVICE_STATUS = "ATTACHED_DEVICE_STATUS";
	// 세대 데이터 전송 설정 제어
	public static String DATA_TRANSFER_CONF = "DATA_TRANSFER_CONF";

	// 홈넷 데이터 전송 여부 설정 제어
	public static String DATA_SEND	 = "DATA_SEND";
	// 홈넷 데이터 전송 여부 설정 제어
	public static String NOTICE_INFO	 = "NOTICE_INFO";


//	// 에너지 Push 메시지
//	// 전기
//	public static String ENERGY_PUSH_MSG_ELCT_LEVEL1	 = "설정된 전기 사용 목표량의 90%에 도달하였습니다. ";
//	public static String ENERGY_PUSH_MSG_ELCT_LEVEL2	 = "설정된 전기 사용 목표량이 초과되었습니다.";
//	// 온수
//	public static String ENERGY_PUSH_MSG_HOTWTR_LEVEL1	 = "설정된 온수 사용 목표량의 90%에 도달하였습니다. ";
//	public static String ENERGY_PUSH_MSG_HOTWTR_LEVEL2	 = "설정된 온수 사용 목표량이 초과되었습니다.";
//	// 난방
//	public static String ENERGY_PUSH_MSG_HEAT_LEVEL1	 = "설정된 난방 사용 목표량의 90%에 도달하였습니다. ";
//	public static String ENERGY_PUSH_MSG_HEAT_LEVEL2	 = "설정된 난방 사용 목표량이 초과되었습니다.";
//	// 가스
//	public static String ENERGY_PUSH_MSG_GAS_LEVEL1	 = "설정된 가스 사용 목표량의 90%에 도달하였습니다. ";
//	public static String ENERGY_PUSH_MSG_GAS_LEVEL2	 = "설정된 가스 사용 목표량이 초과되었습니다.";
//	// 수도
//	public static String ENERGY_PUSH_MSG_WTRSPL_LEVEL1	 = "설정된 수도 사용 목표량의 90%에 도달하였습니다. ";
//	public static String ENERGY_PUSH_MSG_WTRSP_LLEVEL2	 = "설정된 수도 사용 목표량이 초과되었습니다.";



	/** 사용자 생성 이메일 발신자. */
	public static final String ADDUSER_MAIL_FROM = "sender@ntels.com";
	/** 사용자 생성 이메일. */
	public static final String ADDUSER_MAIL_SUBJECT = "Smart Signage  신규 계정 발급이 완료되었습니다.";
	/** 어드민 사용자 레벨. */
	public static final String ADMIN_USER_LEVEL = "00";
	/** 사용자 그룹코드. */
	public static final String ADMIN_USER_TYPE = "USR001";

	/** */
	public static final String DEVICE_PROTOCOL = "rest";
	/**
	 * 세션항목.
	 */
	public abstract static class SessionAttr {
		/** 세션 사용자 정보. */
		public static final String USER = "session_user";

		/** 세션 국가 정보. */
		public static final String COUNTRY = "sessionCountry";		

		/** 세션 언어 정보. */
		public static final String LANG = "sessionLanguage";		
	}
	
	/**
	 * 공통_그룹_코드.
	 */
	public abstract static class COMMON_GROUP_CODE {
		/** 국가그룹코드. */
		public static final String COUNTRY_GROUP_CODE 	= "000100";
		
		/** 언어그룹코드. */
		public static final String LANGUAGE_GROUP_CODE 	= "000200";
	}


	/**
	 * 
	 * 언어코드.
	 *
	 */
	public abstract static class LanguageCode {
		
		/** 영어. */
		public static final String ENGLISH 		= "en";
		
		/** 인도네시아어. */
		public static final String INDONESIAN 	= "id";
		
		/** 한국어. */
		public static final String KOREAN 		= "ko";
		
		/** 일본어. */
		public static final String JAPAN 		= "ja";
	}

	/**
	 * 
	 * TODO: 언어 코드 맵 : 서비스 국가 확장시에 확장되는 언어코드를 추가해줘야한다.
	 * 
	 * 1. 구성관리 >> 공통코드 국가코드 추가
	 * 2. 구성관리 >> 공통코드 언어코드 추가
	 * 3. 구성관리 >> 국가별 언어 >> 언어 추가
	 * 4. 아래 소스 코드 추가
	 */
	public static final Map<String, String> LANGUAGE_CODE_MAP;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put(LanguageCode.ENGLISH, "en");
		map.put(LanguageCode.INDONESIAN, "id");
		map.put(LanguageCode.KOREAN, "ko");
		map.put(LanguageCode.JAPAN, "ja");
		LANGUAGE_CODE_MAP = Collections.unmodifiableMap(map);
	}
}
