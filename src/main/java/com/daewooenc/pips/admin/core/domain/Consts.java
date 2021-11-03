package com.daewooenc.pips.admin.core.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * 시스템 상수 정의 클래스
 *
 */
public abstract class Consts {
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

		public static final String USER_MENU = "sessionUserMenuList";

		public static final String UP_MENU_NO = "upMenuNo";
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
