package com.daewooenc.pips.admin.web.service.weather;

import com.daewooenc.pips.admin.web.common.WebConsts;
import com.daewooenc.pips.admin.web.dao.externalsvcinfo.ExternalSvcInfoMapper;
import com.daewooenc.pips.admin.web.dao.weather.WeatherMapper;
import com.daewooenc.pips.admin.web.domain.dto.externalsvcinfo.ExternalServiceInfo;
import com.daewooenc.pips.admin.web.domain.dto.weather.SpecialWeatherLocation;
import com.daewooenc.pips.admin.web.domain.dto.weather.Weather;
import com.daewooenc.pips.admin.web.domain.dto.weather.WeatherLocation;
import com.daewooenc.pips.admin.web.domain.vo.finedust.FineDutyItemsVo;
import com.daewooenc.pips.admin.web.domain.vo.finedust.FineDutyVo;
import com.daewooenc.pips.admin.web.domain.vo.weather.WeatherItemVo;
import com.daewooenc.pips.admin.web.domain.vo.weather.WeatherVo;
import com.daewooenc.pips.admin.web.util.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 기상 Service.
 *
 */
@Service("weatherService")
public class WeatherService   {
//public class WeatherService  extends QuartzJobBean {

	/** 로그 출력. */
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** WeatherMapper Autowired. */
	@Autowired
	private WeatherMapper weatherMapper;
	@Autowired
	private ExternalSvcInfoMapper externalSvcInfoMapper;
//
//	@Override
//	protected void executeInternal(JobExecutionContext arg0)
//			throws JobExecutionException {
//		System.out.println("================Job B is runing======");
//
//	}

	/**
	 * 날씨 정보를 구해야 하는 위치 정보 목록.
	 *
	 * @return List<WeatherLocation>
	 */
	public List<WeatherLocation> getWeatherLocation() {

		List<WeatherLocation> weatherLocation = weatherMapper.getWeatherLocationList();

		return weatherLocation;
	}

	/**
	 * 미세먼지
	 */
	public boolean createFineDutyInfo() throws UnsupportedEncodingException {
        logger.debug("+++++++++++++createFineDutyInfo start+++++++++++++");

	    ExternalServiceInfo serviceFineDutyInfo = externalSvcInfoMapper.getExternalServiceInfo(WebConsts.EXTERNAL_SERVICE_GROUP_CODE, WebConsts.FINE_DUTY_CODE);

        for(int i = 0; i < WebConsts.FINEDUTY_TARGET.length; i++) {
            String sidoName = URLEncoder.encode(WebConsts.FINEDUTY_TARGET[i], "utf-8");
            String sidoNameKR = WebConsts.FINEDUTY_TARGET[i];

            weatherFineDutyDataInfo(serviceFineDutyInfo, sidoName, sidoNameKR);


        }

        return true;
	}

	public Weather weatherFineDutyDataInfo(ExternalServiceInfo serviceFineDutyInfo, String sidoName, String sidoNameKR) {
        logger.debug("+++++++++++++weatherFineDutyDataInfo start+++++++++++++");
        logger.debug("미세먼지 정보");
		Weather weatherDto = null;
		try {
            String strUrl = serviceFineDutyInfo.getUrlCont();

			strUrl += "?sidoName="+sidoName;
			strUrl += "&searchCondition=HOUR";
			strUrl += "&pageNo=1";
			strUrl += "&numOfRows=100";
			strUrl += "&ServiceKey="+serviceFineDutyInfo.getSvcKeyCd();
//			strUrl += "&ServiceKey="+"JeFtiYwkRHZWlVBDKUSlYsYHBtoIL6GRUflweiE%2BrRm88lqYSp%2BIC4cEChXCyMaADFezWGlz64Mo9mIsSRLTcw%3D%3D";
			strUrl += "&returnType=json";
			// strUrl += "&instt_nm=UTF-8로 인코딩된 value";
			logger.debug("strUrl="+strUrl);
			logger.info("INFO_FineDutyStrUrl="+strUrl);

			URL url = new URL(strUrl);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			} else {
				logger.debug("error");
				return null;
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			String weatherData = "";

			String current;
			while ((current = in.readLine()) != null) {
                weatherData += current + "\n";
			}
            logger.debug("weatherData:"+weatherData);

			if(isJSON(weatherData)) {
				ObjectMapper mapper = new ObjectMapper();
				// String형 json을 Objecf로 변경
				FineDutyVo fineDutyVo = mapper.readValue(weatherData, FineDutyVo.class);

				// 미세먼지 정보 저장
				logger.debug("fineDutyVo.getWeatherResponse().getWeatherHeader().getResultCode()="+fineDutyVo.getResponse().getHeader().getResultCode());
				logger.info("INFO_fineDutyVo.getWeatherResponse().getWeatherHeader().getResultCode()="+fineDutyVo.getResponse().getHeader().getResultCode());

				weatherDto = convertFineDutyWeather(fineDutyVo, sidoNameKR);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return weatherDto;
	}

	// 현재 날씨 정보
	public boolean createCurrentWeatherInfo() {

		logger.debug("+++++++++++++createCurrentWeatherInfo start+++++++++++++");
		Calendar cal = Calendar.getInstance();
		String currentBaseTime = "";
		String currentBaseDate = "";
		String spaceBaseDate = "";
		String spaceBaseTime = "";

		currentBaseTime = DateUtil.getBaseTime(cal, WebConsts.nCurrent);

		currentBaseDate = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, cal.getTime());

		cal = Calendar.getInstance();
		spaceBaseTime = DateUtil.getBaseTime(cal, WebConsts.nSpace);

		spaceBaseDate = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, cal.getTime());

		List<WeatherLocation> weatherLocation = weatherMapper.getWeatherLocationList();
		// 동네 예보에 대한 등록 정보 조회(service Key 및 URL)
		ExternalServiceInfo serviceSpaceInfo = externalSvcInfoMapper.getExternalServiceInfo(WebConsts.EXTERNAL_SERVICE_GROUP_CODE, WebConsts.WEATER_SPACE_CODE);

		//  현재 날씨에 대한 등록 정보 조회(service Key 및 URL)
		ExternalServiceInfo serviceCurrentInfo = externalSvcInfoMapper.getExternalServiceInfo(WebConsts.EXTERNAL_SERVICE_GROUP_CODE, WebConsts.WEATER_CURRENT_CODE);

		// 공공데이터 날씨 정보 취득
		for(WeatherLocation data : weatherLocation) {
			Weather weatherSpace = null;
			if ("0200".equals(spaceBaseTime) || "0500".equals(spaceBaseTime) || "0800".equals(spaceBaseTime) || "1100".equals(spaceBaseTime) || "1400".equals(spaceBaseTime) || "1700".equals(spaceBaseTime) || "2000".equals(spaceBaseTime) || "2300".equals(spaceBaseTime)) {
				// 동내 날씨 예보 조회
				weatherSpace = weatherForecastSpaceInfo(data.getWrMeasXCoorNo(), data.getWrMeasYCoorNo(), spaceBaseDate, spaceBaseTime, serviceSpaceInfo);
				if (weatherSpace == null) {
					weatherSpace = new Weather();
				}
			} else {
				weatherSpace = new Weather();
			}

			Weather weatherCurrentDB = null;

			// DB에 저장된 현재 날씨 구함
			if(!"0200".equals(spaceBaseTime)) {
				weatherCurrentDB = weatherMapper.getCurrentWeather(data.getWrMeasXCoorNo()+"", data.getWrMeasYCoorNo()+"", WebConsts.WEATHER_TYPE_CURRENT);
			}

			if (weatherCurrentDB != null) {
				// 최저 온도는 basetime이 0200만 존재, 최고 온도는 basetime이 0200, 0500, 0800, 1100에 존재
				if ("0500".equals(spaceBaseTime) || "0800".equals(spaceBaseTime) || "1100".equals(spaceBaseTime)) {
					// 0200의 최저 온도를 설정
					weatherSpace.setLwstTempQty(weatherCurrentDB.getLwstTempQty());
					weatherSpace.setSkyStsCd(weatherCurrentDB.getSkyStsCd());
				} else {
					// DB에 저장되어 있는  최저 온도, 최고 온도를 설정
					weatherSpace.setSkyStsCd(weatherCurrentDB.getSkyStsCd());
					weatherSpace.setLwstTempQty(weatherCurrentDB.getLwstTempQty());
					weatherSpace.setHgstTempQty(weatherCurrentDB.getHgstTempQty());
				}
			} else {

			}

			// 초단기 실황 조회
			Weather weatherCurrent = weatherForecastGribInfo(data.getWrMeasXCoorNo(), data.getWrMeasYCoorNo(), currentBaseDate, currentBaseTime, weatherSpace, serviceCurrentInfo);

			if (weatherCurrent != null ) {
				logger.debug("###################################Current Data Delete###################################");
				// DB 날씨 데이터 삭제

				logger.info("CurrentWeather DB TIME CHECK START");

				weatherMapper.deleteWeather(data.getWrMeasXCoorNo() + "", data.getWrMeasYCoorNo() + "", WebConsts.WEATHER_TYPE_CURRENT);
				// DB 현재 날씨 등록
				List<Weather> weatherList = new ArrayList<Weather>(1);
				weatherList.add(weatherCurrent);
				weatherMapper.insertWeatherData(weatherList);

				logger.info("CurrentWeather DB TIME CHECK END");
			}
		}

		return true;
	}

	// 단기 예보
	public boolean createForecastWeatherInfo() {
		// 날씨 정보 테이블 초기화
		logger.debug("+++++++++++++createForecastWeatherInfo start+++++++++++++");
		Calendar cal = Calendar.getInstance();
		String forcastBaseTime = DateUtil.getBaseTime(cal, WebConsts.nForcast);
		String forcastBaseDate = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, cal.getTime());
		List<WeatherLocation> weatherLocation = weatherMapper.getWeatherLocationList();
		//  단기 예보
		ExternalServiceInfo serviceForecastInfo = externalSvcInfoMapper.getExternalServiceInfo(WebConsts.EXTERNAL_SERVICE_GROUP_CODE, WebConsts.WEATER_FORECAST_CODE);
		// 공공데이터 날씨 정보 취득
		for(WeatherLocation data : weatherLocation) {
			// 초단기 예보 조회
			List<Weather> weatherForcastDaoList = weatherForecastTimeDataInfo(data.getWrMeasXCoorNo(), data.getWrMeasYCoorNo(), forcastBaseDate, forcastBaseTime, serviceForecastInfo);
			if (weatherForcastDaoList != null && weatherForcastDaoList.size() > 0) {
				// 단기예보의 첫번째 데이터에서 하늘 상태를 구해 현재 날씨의 하늘 상태 업데이트,
				updateCurrentSkyStatus(weatherForcastDaoList.get(0));
//				if ("0230".equals(forcastBaseTime) || "0530".equals(forcastBaseTime) || "0830".equals(forcastBaseTime) || "1130".equals(forcastBaseTime) || "1430".equals(forcastBaseTime) || "1730".equals(forcastBaseTime) || "2030".equals(forcastBaseTime) || "2330".equals(forcastBaseTime)) {

					logger.info("ForecastWeather DB TIME CHECK START");

					// DB 날씨 데이터 삭제
					weatherMapper.deleteWeather(data.getWrMeasXCoorNo()+"", data.getWrMeasYCoorNo()+"", WebConsts.WEATHER_TYPE_FORECAST);
					// DB 날씨 정보 저장
					weatherMapper.insertWeatherData(weatherForcastDaoList);

					logger.info("ForecastWeather DB TIME CHECK END");

//				} else {
//					weatherMapper.updateForecast(weatherForcastDaoList);
//				}
			}
		}

		// 날씨 정보 Insert
		return true;
	}

	// 기상 특보
	public boolean createSpecialWeatherInfo() {
		logger.debug("+++++++++++++createSpecialWeatherInfo start+++++++++++++");

        Calendar cal = Calendar.getInstance();
        String today = "";
        String yesterday = "";

        today = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, cal.getTime());

        cal.add(Calendar.DATE, -1);
        yesterday = DateUtil.getDate(WebConsts.strDateFormat_yyyyMMdd, cal.getTime());

		// 지역 코드 정보
		List<SpecialWeatherLocation> specialWeatherLocation = weatherMapper.getSpecialWeatherLocationList();

        Map<String,Object> locationMap = new HashMap<String,Object>();

        for(int i=0; i<specialWeatherLocation.size(); i++){
            locationMap.put("areaCd"+i, specialWeatherLocation.get(i).getAreaCd());
        }

		// 기상 특보에 대한 등록 정보 조회(service Key 및 URL)
		ExternalServiceInfo serviceSpecialInfo = externalSvcInfoMapper.getExternalServiceInfo(WebConsts.EXTERNAL_SERVICE_GROUP_CODE, WebConsts.WEATER_SPECIAL_CODE);

        // 기상 특보 조회 및 DB 저장
		weatherSpecialDataInfo(locationMap, serviceSpecialInfo, today, yesterday);
		// 발표 해제된 데이터 삭제
		//weatherMapper.deleteSpecialWeatherRelease();

		return true;
	}



	// 동네 날씨 예보
	public Weather weatherForecastSpaceInfo(int location_x, int location_y, String baseDate, String baseTime, ExternalServiceInfo serviceSpaceInfo) {
		logger.debug("+++++++++++++weatherForecastSpaceInfo start+++++++++++++");
		logger.debug("동내 예보 날씨 정보 ");
		Weather weatherDto = null;
		try {
			// 동
//			String strUrl = WeatherForecastSpaceURL;
			String strUrl = serviceSpaceInfo.getUrlCont();
			strUrl += "?serviceKey="+serviceSpaceInfo.getSvcKeyCd();
//			strUrl += "?serviceKey="+"JeFtiYwkRHZWlVBDKUSlYsYHBtoIL6GRUflweiE%2BrRm88lqYSp%2BIC4cEChXCyMaADFezWGlz64Mo9mIsSRLTcw%3D%3D";
			strUrl += "&dataType=JSON";
			strUrl += "&base_date="+baseDate;
			strUrl += "&base_time="+baseTime;
			strUrl += "&numOfRows=225";
			strUrl += "&nx="+location_x;
			strUrl += "&ny="+location_y;

			logger.info("ForecastSpace URL TIME CHECK START");

			URL url = new URL(strUrl);
			logger.debug("strUrl="+strUrl);
			logger.info("INFO_ForecastSpaceStrUrl="+strUrl);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			} else {
				logger.error("URL error");
				return null;
			}

			logger.info("ForecastSpace URL TIME CHECK END");

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			String urlString = "";

			String current;
			while ((current = in.readLine()) != null) {

				urlString += current + "\n";
			}

			if(isJSON(urlString)) {
				ObjectMapper mapper = new ObjectMapper();
				WeatherVo weatherVo = mapper.readValue(urlString, WeatherVo.class);

				logger.debug("weather.getWeatherResponse().getWeatherHeader().getResultCode()="+weatherVo.getResponse().getHeader().getResultCode());
				logger.info("INFO_ForecastSpaceWeather.getWeatherResponse().getWeatherHeader().getResultCode()="+weatherVo.getResponse().getHeader().getResultCode());

				weatherDto = convertSpaceWeather(weatherVo);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return weatherDto;
	}

	// API Data가 JSON 포맷인지 확인
	public boolean isJSON(String value) {
		try {
			new JSONObject(value);
		} catch(JSONException e) {
			logger.info("weather data json format error:\n" + value);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 초단기 날씨 실황 정보
	public Weather weatherForecastGribInfo(int location_x, int location_y, String baseDate, String baseTime, Weather weatherSpaceDao, ExternalServiceInfo serviceCurrentInfo) {
		logger.debug("+++++++++++++weatherForecastGribInfo start+++++++++++++");
		logger.debug("초단기 날씨 실황 정보 ");
		Weather weatherDto = null;
		try {
			// 동
//			String strUrl = publicData_WeatherForecastGribURL;
			String strUrl = serviceCurrentInfo.getUrlCont();
			strUrl += "?serviceKey="+serviceCurrentInfo.getSvcKeyCd();
//			strUrl += "?serviceKey="+"JeFtiYwkRHZWlVBDKUSlYsYHBtoIL6GRUflweiE%2BrRm88lqYSp%2BIC4cEChXCyMaADFezWGlz64Mo9mIsSRLTcw%3D%3D";
			strUrl += "&dataType=JSON";
			strUrl += "&base_date="+baseDate;
			strUrl += "&base_time="+baseTime;
			strUrl += "&nx="+location_x;
			strUrl += "&ny="+location_y;

			logger.info("ForecastGrip URL TIME CHECK START");

			URL url = new URL(strUrl);
			logger.debug("strUrl="+strUrl);
			logger.info("INFO_ForecastGribStrUrl="+strUrl);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			} else {
				logger.debug("error");
				return null;
			}

			logger.info("ForecastGrip URL TIME CHECK END");

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			String urlString = "";

			String current;
			while ((current = in.readLine()) != null) {

				urlString += current + "\n";
			}

			if(isJSON(urlString)) {
				ObjectMapper mapper = new ObjectMapper();
				WeatherVo weatherVo = mapper.readValue(urlString, WeatherVo.class);

				logger.debug("weather.getWeatherResponse().getWeatherHeader().getResultCode()="+weatherVo.getResponse().getHeader().getResultCode());
				logger.info("INFO_ForecastGribWeather.getWeatherResponse().getWeatherHeader().getResultCode()="+weatherVo.getResponse().getHeader().getResultCode());

				// 현재 날씨
				weatherDto = convertCurrentWeather(weatherVo);
			}

			if (weatherDto != null) {
				float temperature = Float.parseFloat(weatherDto.getCurTempQty());
				logger.debug("+++++++++++++++++++++++++temperature=" + temperature);
				// +900이상, –900 이하 값은 관측장비가 없는 해양 지역이거나 관측장비의 결측 등으로 자료가 없음을 의미
				if (temperature > 900 || temperature < -900) {
					return null;
				}
				/* 동네 예보데이터에서 최고 온도, 최저온도, 강수확률 데이터를 구함 */
				if (weatherSpaceDao != null) {
					// 최고 온도
					weatherDto.setHgstTempQty(weatherSpaceDao.getHgstTempQty());
					// 최저 온도
					weatherDto.setLwstTempQty(weatherSpaceDao.getLwstTempQty());
					// 강수확률
					weatherDto.setRainProbab(weatherSpaceDao.getRainProbab());

					// 하늘 상태(DB에 저장된 데이터를 사용 : 하늘 상태는 단기 예보에서 생성함
					weatherDto.setSkyStsCd(weatherSpaceDao.getSkyStsCd());

					// 예보 일자
					weatherDto.setFcastYmd(weatherDto.getPrsntYmd());
					// 예보 시간
					weatherDto.setFcastTime(weatherDto.getPrsntTime());

				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return weatherDto;
	}
	// 초단기 날씨 예보 정보
	public List<Weather> weatherForecastTimeDataInfo(int location_x, int location_y, String baseDate, String baseTime, ExternalServiceInfo serviceForecastInfo) {
		logger.debug("+++++++++++++weatherForecastTimeDataInfo start+++++++++++++");
		logger.debug("초단기 날씨 예보 정보");
		List<Weather> weatherList = null;
		try {
//			int cnt = getCnt(baseTime);
			Vector vtFcsTime = getFcstTime(baseTime);
			// 동
			String strUrl = serviceForecastInfo.getUrlCont();
//			String strUrl = publicData_WeatherForecastTimeDataURL;
			// strUrl += "?serviceKey=UTF-8로 인코딩된 인증키";
			strUrl += "?serviceKey="+serviceForecastInfo.getSvcKeyCd();
//			strUrl += "?serviceKey="+"JeFtiYwkRHZWlVBDKUSlYsYHBtoIL6GRUflweiE%2BrRm88lqYSp%2BIC4cEChXCyMaADFezWGlz64Mo9mIsSRLTcw%3D%3D";
			strUrl += "&dataType=JSON";
			strUrl += "&base_date="+baseDate;
			strUrl += "&base_time="+baseTime;
			strUrl += "&numOfRows="+60;
			strUrl += "&nx="+location_x;
			strUrl += "&ny="+location_y;

			logger.info("ForecastTime URL TIME CHECK START");

			logger.debug("strUrl="+strUrl);
			logger.info("INFO_ForecastTimeStrUrl="+strUrl);
			// strUrl += "&instt_nm=UTF-8로 인코딩된 value";
			URL url = new URL(strUrl);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			} else {
				logger.debug("error");
				return null;
			}

			logger.info("ForecastTime URL TIME CHECK END");

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			String weatherData = "";

			String current;
			while ((current = in.readLine()) != null) {
				weatherData += current + "\n";
			}
			logger.debug("weatherData:"+weatherData);

			if(isJSON(weatherData)) {
				ObjectMapper mapper = new ObjectMapper();
				// String형 json을 Objecf로 변경
				WeatherVo weatherVo = mapper.readValue(weatherData, WeatherVo.class);

				logger.info("INFO_ForecastTimeWeather.getWeatherResponse().getWeatherHeader().getResultCode()="+weatherVo.getResponse().getHeader().getResultCode());

				// 초단기 날씨 정보 저장
				weatherList = convertForecastWeather(weatherVo, vtFcsTime);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return weatherList;
	}
	// 기상 특보 정보
	public Weather weatherSpecialDataInfo(Map<String,Object> locationMap, ExternalServiceInfo serviceSpecialInfo, String today, String yesterday) {
		logger.debug("+++++++++++++weatherSpecialDataInfo start+++++++++++++");
		logger.debug("기상 특보 정보");
		Weather weatherDto = null;
		try {
			String strUrl = serviceSpecialInfo.getUrlCont();
			// strUrl += "?serviceKey=UTF-8로 인코딩된 인증키";
			strUrl += "?serviceKey="+serviceSpecialInfo.getSvcKeyCd();
			strUrl += "&dataType=JSON";
			strUrl += "&numOfRows=1000";
			strUrl += "&pageNo=1";
			//strUrl += "&areaCode="+areaCode;
			//strUrl += "&fromTmFc="+yesterday;
			strUrl += "&fromTmFc="+today;
			strUrl += "&toTmFc="+today;

			logger.debug("strUrl="+strUrl);
			logger.info("INFO_SpecialDataStrUrl="+strUrl);
			// strUrl += "&instt_nm=UTF-8로 인코딩된 value";
			URL url = new URL(strUrl);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			} else {
				logger.debug("error");
				return null;
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			String weatherData = "";

			String current;
			while ((current = in.readLine()) != null) {
				weatherData += current + "\n";
			}
			logger.debug("weatherData:"+weatherData);

			if(isJSON(weatherData)) {
				ObjectMapper mapper = new ObjectMapper();
				// String형 json을 Objecf로 변경
				WeatherVo weatherVo = mapper.readValue(weatherData, WeatherVo.class);

				logger.debug("weather.getWeatherResponse().getWeatherHeader().getResultCode()="+weatherVo.getResponse().getHeader().getResultCode());
				logger.info("INFO_SpecialDataWeather.getWeatherResponse().getWeatherHeader().getResultCode()="+weatherVo.getResponse().getHeader().getResultCode());

				// 기상 특보 정보 저장
				weatherDto = convertSpecialWeather(weatherVo, locationMap);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return weatherDto;
	}

	private Vector<String> getFcstTime(String baseTime) {
		Vector<String> vtFcstTime = new Vector<>();
		if ("0030".equals(baseTime)) {
			vtFcstTime.add("0100");
			vtFcstTime.add("0200");
			vtFcstTime.add("0300");
			vtFcstTime.add("0400");
		} else if ("0130".equals(baseTime)) {
			vtFcstTime.add("0200");
			vtFcstTime.add("0300");
			vtFcstTime.add("0400");
			vtFcstTime.add("0500");
		} else if ("0230".equals(baseTime)) {
			vtFcstTime.add("0300");
			vtFcstTime.add("0400");
			vtFcstTime.add("0500");
			vtFcstTime.add("0600");
		} else if ("0330".equals(baseTime)) {
			vtFcstTime.add("0400");
			vtFcstTime.add("0500");
			vtFcstTime.add("0600");
			vtFcstTime.add("0700");
		} else if ("0430".equals(baseTime)) {
			vtFcstTime.add("0500");
			vtFcstTime.add("0600");
			vtFcstTime.add("0700");
			vtFcstTime.add("0800");
		} else if ("0530".equals(baseTime)) {
			vtFcstTime.add("0600");
			vtFcstTime.add("0700");
			vtFcstTime.add("0800");
			vtFcstTime.add("0900");
		} else if ("0630".equals(baseTime)) {
			vtFcstTime.add("0700");
			vtFcstTime.add("0800");
			vtFcstTime.add("0900");
			vtFcstTime.add("1000");
		} else if ("0730".equals(baseTime)) {
			vtFcstTime.add("0800");
			vtFcstTime.add("0900");
			vtFcstTime.add("1000");
			vtFcstTime.add("1100");
		} else if ("0830".equals(baseTime)) {
			vtFcstTime.add("0900");
			vtFcstTime.add("1000");
			vtFcstTime.add("1100");
			vtFcstTime.add("1200");
		} else if ("0930".equals(baseTime)) {
			vtFcstTime.add("1000");
			vtFcstTime.add("1100");
			vtFcstTime.add("1200");
			vtFcstTime.add("1300");
		} else if ("1030".equals(baseTime)) {
			vtFcstTime.add("1100");
			vtFcstTime.add("1200");
			vtFcstTime.add("1300");
			vtFcstTime.add("1400");
		} else if ("1130".equals(baseTime)) {
			vtFcstTime.add("1200");
			vtFcstTime.add("1300");
			vtFcstTime.add("1400");
			vtFcstTime.add("1500");
		} else if ("1230".equals(baseTime)) {
			vtFcstTime.add("1300");
			vtFcstTime.add("1400");
			vtFcstTime.add("1500");
			vtFcstTime.add("1600");
		} else if ("1330".equals(baseTime)) {
			vtFcstTime.add("1400");
			vtFcstTime.add("1500");
			vtFcstTime.add("1600");
			vtFcstTime.add("1700");
		} else if ("1430".equals(baseTime)) {
			vtFcstTime.add("1500");
			vtFcstTime.add("1600");
			vtFcstTime.add("1700");
			vtFcstTime.add("1800");
		} else if ("1530".equals(baseTime)) {
			vtFcstTime.add("1600");
			vtFcstTime.add("1700");
			vtFcstTime.add("1800");
			vtFcstTime.add("1900");
		} else if ("1630".equals(baseTime)) {
			vtFcstTime.add("1700");
			vtFcstTime.add("1800");
			vtFcstTime.add("1900");
			vtFcstTime.add("2000");
		} else if ("1730".equals(baseTime)) {
			vtFcstTime.add("1800");
			vtFcstTime.add("1900");
			vtFcstTime.add("2000");
			vtFcstTime.add("2100");
		} else if ("1830".equals(baseTime)) {
			vtFcstTime.add("1900");
			vtFcstTime.add("2000");
			vtFcstTime.add("2100");
			vtFcstTime.add("2200");
		} else if ("1930".equals(baseTime)) {
			vtFcstTime.add("2000");
			vtFcstTime.add("2100");
			vtFcstTime.add("2200");
			vtFcstTime.add("2300");
		} else if ("2030".equals(baseTime)) {
			vtFcstTime.add("2100");
			vtFcstTime.add("2200");
			vtFcstTime.add("2300");
			vtFcstTime.add("0000");
		} else if ("2130".equals(baseTime)) {
			vtFcstTime.add("2200");
			vtFcstTime.add("2300");
			vtFcstTime.add("0000");
			vtFcstTime.add("0100");
		} else if ("2230".equals(baseTime)) {
			vtFcstTime.add("2300");
			vtFcstTime.add("0000");
			vtFcstTime.add("0100");
			vtFcstTime.add("0200");
		} else if ("2330".equals(baseTime)) {
			vtFcstTime.add("0000");
			vtFcstTime.add("0100");
			vtFcstTime.add("0200");
			vtFcstTime.add("0300");
		}
		return vtFcstTime;
	}
	/**
	 * json 포맷의 현재 기상 데이터를 DB 포맷의 기상 데이터로 변환
	 * @param weather
	 */
	public Weather convertCurrentWeather(WeatherVo weather) {
		logger.debug("+++++++++++++convertCurrentWeather start+++++++++++++");
		Weather weatherDto = null;
		if (WebConsts.WEATHER_RESULT.equals(weather.getResponse().getHeader().getResultCode())) {
			weatherDto = new Weather();
			List<WeatherItemVo> item = weather.getResponse().getBody().getItems().getItem();
			// 기본 정보 설정
			if (item.size() > 0) {
				weatherDto.setPrsntYmd(item.get(0).getBaseDate());
				weatherDto.setPrsntTime(item.get(0).getBaseTime());
				weatherDto.setWrMeasXCoorNo(item.get(0).getNx());
				weatherDto.setWrMeasYCoorNo(item.get(0).getNy());
				weatherDto.setWrTpCd(WebConsts.WEATHER_TYPE_CURRENT);
				// 세부 항목 설정
				for (WeatherItemVo weatherData : item) {
					if (WebConsts.WEATHER_T1H.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getObsrValue())) {
							weatherDto.setCurTempQty("0"); // 기온
						} else {
							weatherDto.setCurTempQty(weatherData.getObsrValue() +""); // 기온
						}

					} else if (WebConsts.WEATHER_REH.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getObsrValue())) {
							weatherDto.setHmdtQty("0"); // 습도
						} else {
							weatherDto.setHmdtQty(weatherData.getObsrValue()+""); // 습도
						}

					} else if (WebConsts.WEATHER_PTY.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getObsrValue())) {
							weatherDto.setRainTpCd("0"); // 강수 형태
						} else {
							weatherDto.setRainTpCd(weatherData.getObsrValue() + ""); // 강수 형태
						}

					} else if (WebConsts.WEATHER_VEC.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getObsrValue())) {
							weatherDto.setWidrtCd("0"); // 풍향
						} else {
							weatherDto.setWidrtCd(weatherData.getObsrValue() + ""); // 풍향
						}

					} else if (WebConsts.WEATHER_WSD.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getObsrValue())) {
							weatherDto.setWindQty("0"); // 풍속
						} else {
							weatherDto.setWindQty(weatherData.getObsrValue()+""); // 풍속
						}
					}
				}
			}
		}
		return weatherDto;
	}

	/**
	 * json 포맷의 현재 기상 데이터를 DB 포맷의 기상 데이터로 변환
	 * @param weather
	 */
	public Weather convertSpaceWeather(WeatherVo weather) {
		logger.debug("+++++++++++++convertSpaceWeather start+++++++++++++");
		Weather weatherDto = new Weather();
		Vector<String> vtWeatherTime = new Vector<String>();
		String strKey = "";
		boolean bSkyStatus = false;
		boolean bTMN = false;
		boolean bTMX = false;
 		if (WebConsts.WEATHER_RESULT.equals(weather.getResponse().getHeader().getResultCode())) {
			List<WeatherItemVo> item = weather.getResponse().getBody().getItems().getItem();
			// 기본 정보 설정
			if (item.size() > 0) {
				weatherDto.setPrsntYmd(item.get(0).getBaseDate());
				weatherDto.setPrsntTime(item.get(0).getBaseTime());
				weatherDto.setFcastYmd(item.get(0).getFcstDate());
				weatherDto.setFcastTime(item.get(0).getFcstTime());
				weatherDto.setWrMeasXCoorNo(item.get(0).getNx());
				weatherDto.setWrMeasYCoorNo(item.get(0).getNy());
				weatherDto.setWrTpCd(WebConsts.WEATHER_TYPE_CURRENT);

				// 세부 항목 설정
				for (WeatherItemVo weatherData : item) {

					// base time이 하나만 유지하도록 함
					strKey = weatherData.getFcstDate() + weatherData.getFcstTime();
					if (!vtWeatherTime.contains(strKey)) {
						vtWeatherTime.add(strKey);
					}

					if (WebConsts.WEATHER_POP.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getFcstValue())) {
							weatherDto.setRainProbab(0 + "");// 강수 확률
						} else {
							weatherDto.setRainProbab(weatherData.getFcstValue() + ""); // 강수 확률
						}

					} else if (WebConsts.WEATHER_PTY.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getFcstValue())) {
							weatherDto.setRainTpCd(0 + "");// 강수 형태
						} else {
							weatherDto.setRainTpCd(weatherData.getFcstValue() + ""); // 강수 형태
						}
					} else if (WebConsts.WEATHER_REH.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getFcstValue())) {
							weatherDto.setHmdtQty("0");// 습도
						} else {
							weatherDto.setHmdtQty(weatherData.getFcstValue()); // 습도
						}

					} else if (WebConsts.WEATHER_SKY.equals(weatherData.getCategory()) && !bSkyStatus) {
						if ("-".equals(weatherData.getFcstValue())) {
							weatherDto.setSkyStsCd("0");// 하늘상태
						} else {
							weatherDto.setSkyStsCd(weatherData.getFcstValue()); // 하늘상태
						}

						bSkyStatus = true;
					} else if (WebConsts.WEATHER_TMN.equals(weatherData.getCategory()) && !bTMN) {
						if ("-".equals(weatherData.getFcstValue())) {
							weatherDto.setLwstTempQty("0");// 최저온도
						} else {
							double lowTemp = Double.parseDouble(weatherData.getFcstValue());
							weatherDto.setLwstTempQty(Math.round(lowTemp)+""); // 최저온도
						}
						bTMN = true;
					} else if (WebConsts.WEATHER_TMX.equals(weatherData.getCategory()) && !bTMX) {
						if ("-".equals(weatherData.getFcstValue())) {
							weatherDto.setHgstTempQty("0");// 최고온도
						} else {
							double highTemp = Double.parseDouble(weatherData.getFcstValue());
							weatherDto.setHgstTempQty(Math.round(highTemp)+""); // 최고온도
						}
						bTMX = true;
					} else if (WebConsts.WEATHER_VEC.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getFcstValue())) {
							weatherDto.setWidrtCd("0");// 풍향
						} else {
							weatherDto.setWidrtCd(weatherData.getFcstValue() + ""); // 풍향
						}

					} else if (WebConsts.WEATHER_WSD.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getFcstValue())) {
							weatherDto.setWindQty("0");// 풍속
						} else {
							weatherDto.setWindQty(weatherData.getFcstValue()); // 풍속
						}
					}
					if (bTMN && bTMX && bSkyStatus) {
						break;
					}
				}
			}
		}
		return weatherDto;
	}
	public void updateCurrentSkyStatus(Weather weather) {
		logger.debug("+++++++++++++updateCurrentSkyStatus start+++++++++++++");
			Weather currentSkyWeather = new Weather();

			currentSkyWeather.setWrTpCd(WebConsts.WEATHER_TYPE_CURRENT);
			currentSkyWeather.setWrMeasXCoorNo(weather.getWrMeasXCoorNo());
			currentSkyWeather.setWrMeasYCoorNo(weather.getWrMeasYCoorNo());
			currentSkyWeather.setSkyStsCd(weather.getSkyStsCd());
			weatherMapper.updateCurrentSkyStatus(currentSkyWeather);
			logger.debug("SKY Status Update");
	}

	/**
	 * json 포맷의 단기 예보 기상 데이터를 DB 포맷의 기상 데이터로 변환
	 * @param weatherForecast
	 */
	public List<Weather> convertForecastWeather(WeatherVo weatherForecast, Vector<String> vtFcsTime) {
		logger.debug("+++++++++++++convertForecastWeather start+++++++++++++");
		List<Weather> weatherList = null;
		Map<String, Weather> weatherMap =  new HashMap<String, Weather>();
		Weather weather = null;
		for(String factTimeKey : vtFcsTime) {
			weather = new Weather();
			weatherMap.put(factTimeKey, weather);
		}
		if (WebConsts.WEATHER_RESULT.equals(weatherForecast.getResponse().getHeader().getResultCode())) {
			List<WeatherItemVo> item = weatherForecast.getResponse().getBody().getItems().getItem();
			// 세부 항목 설정
			for (WeatherItemVo weatherData : item) {
				weather = weatherMap.get(weatherData.getFcstTime());

				if (weather != null) {
					// 기본 정보 설정
					weather.setPrsntYmd(weatherData.getBaseDate());
					weather.setPrsntTime(weatherData.getBaseTime());
					weather.setFcastYmd(weatherData.getFcstDate());
					weather.setFcastTime(weatherData.getFcstTime());
					weather.setWrMeasXCoorNo(weatherData.getNx());
					weather.setWrMeasYCoorNo(weatherData.getNy());
					weather.setWrTpCd(WebConsts.WEATHER_TYPE_FORECAST);


					if (WebConsts.WEATHER_T1H.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getFcstValue())) {
							weather.setCurTempQty("0");// 기온
						} else {
							weather.setCurTempQty(weatherData.getFcstValue()); // 기온
						}
					} else if (WebConsts.WEATHER_REH.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getFcstValue())) {
							weather.setHmdtQty("0");// 습도
						} else {
							weather.setHmdtQty(weatherData.getFcstValue()); // 습도
						}

					} else if (WebConsts.WEATHER_PTY.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getFcstValue())) {
							weather.setRainTpCd(0 + "");// 강수 형태
						} else {
							weather.setRainTpCd(weatherData.getFcstValue() + ""); // 강수 형태
						}

					} else if (WebConsts.WEATHER_VEC.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getFcstValue())) {
							weather.setWidrtCd(0 + "");// 풍향
						} else {
							weather.setWidrtCd(weatherData.getFcstValue() + ""); // 풍향
						}

					} else if (WebConsts.WEATHER_WSD.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getFcstValue())) {
							weather.setWindQty("0");// 풍속
						} else {
							weather.setWindQty(weatherData.getFcstValue()); // 풍속
						}
					} else if (WebConsts.WEATHER_SKY.equals(weatherData.getCategory())) {
						if ("-".equals(weatherData.getFcstValue())) {
							weather.setSkyStsCd("0");// 풍속
						} else {
							weather.setSkyStsCd(weatherData.getFcstValue()); // 풍속
						}
					}
				}
			}
			weatherList = new ArrayList<>(vtFcsTime.size());
			for(int i  = 0; i < vtFcsTime.size(); i++) {
				Weather weatherTemp = weatherMap.get(vtFcsTime.get(i));

				float temperature = Float.parseFloat(weatherTemp.getCurTempQty());
				// +900이상, ?900 이하 값은 관측장비가 없는 해양 지역이거나 관측장비의 결측 등으로 자료가 없음을 의미
				logger.debug("+++++++++++++++++++++++++temperature="+temperature +" vtFcsTime="+vtFcsTime);
				if (temperature > -900 && temperature < 900) {
					weatherList.add(weatherMap.get(vtFcsTime.get(i)));
				} else {
				}
			}
		}
		logger.debug("=================weatherList.size()="+weatherList.size() +"vtFcsTime="+vtFcsTime);
		return weatherList;
	}

	/**
	 * json 포맷의 현재 기상 데이터를 DB 포맷의 기상 데이터로 변환
	 * @param weather
	 */
	public Weather convertSpecialWeather(WeatherVo weather, Map<String,Object> locationMap) {
		logger.debug("+++++++++++++convertSpecialWeather start+++++++++++++");
		Weather weatherDto = null;
		if (WebConsts.WEATHER_RESULT.equals(weather.getResponse().getHeader().getResultCode())) {
			List<WeatherItemVo> item = weather.getResponse().getBody().getItems().getItem();

			// DB 기상 특보 데이터 삭제
			weatherMapper.truncateSpecialWeather();

			for(int i=0; i<item.size(); i++ ){
				// 기본 정보 설정
				if(locationMap.containsValue((item.get(i).getAreaCode()))){
					// DB 기상 특보 데이터 삭제
					//weatherMapper.deleteSpecialWeather(item.get(i).getAreaCode());
					if(item.get(i).getCommand().equals("1")  || item.get(i).getCommand().equals("3")
					 ||item.get(i).getCommand().equals("6")  || item.get(i).getCommand().equals("7")) {
					 // 1: 발표, 3: 연장, 6: 정정, 7: 변경발표

						weatherDto = new Weather();
						weatherDto.setTmFc(item.get(i).getTmFc());
						weatherDto.setAreaCd(item.get(i).getAreaCode());
						weatherDto.setAreaNm(item.get(i).getAreaName());
						weatherDto.setWeatherWarnToCd(item.get(i).getWarnVar());
						weatherDto.setWeatherWarnStress(item.get(i).getWarnStress());
						weatherDto.setCommand(item.get(i).getCommand());
						weatherDto.setWeatherWarnTime(item.get(i).getStartTime());

						List<Weather> weatherList = new ArrayList<Weather>(1);
						weatherList.add(weatherDto);
						// DB 현재 기상 특보 등록
						weatherMapper.insertSpecialWeatherData(weatherList);
					}
				}
			}

		}
		return weatherDto;
	}

    /**
     * json 포맷의 현재 기상 데이터를 DB 포맷의 기상 데이터로 변환
     * @param weather
     */
    public Weather convertFineDutyWeather(FineDutyVo weather, String sidoNameKR) {
        logger.debug("+++++++++++++convertFineDutyWeather start+++++++++++++");
        Weather weatherDto = null;
        if (WebConsts.WEATHER_RESULT.equals(weather.getResponse().getHeader().getResultCode())) {
            List<FineDutyItemsVo> item = weather.getResponse().getBody().getItems();

            // DB 미세먼지 데이터 삭제
			weatherMapper.deleteFineDuty(sidoNameKR);

            for(int i=0; i<item.size(); i++ ){
                // 기본 정보 설정
            	weatherDto = new Weather();
                weatherDto.setAddrSiGunGuNm(item.get(i).getCityName());
                weatherDto.setAddrSiDoNm(item.get(i).getSidoName());
                weatherDto.setMeasDt(item.get(i).getDataTime());
                weatherDto.setSo2ConceQty(item.get(i).getSo2Value());
                weatherDto.setCoConceQty(item.get(i).getCoValue());
                weatherDto.setO3ConceQty(item.get(i).getO3Value());
                weatherDto.setNo2ConceQty(item.get(i).getNo2Value());
                weatherDto.setPm10ConceQty(item.get(i).getPm10Value());
                weatherDto.setPm25ConceQty(item.get(i).getPm25Value());

                List<Weather> weatherList = new ArrayList<Weather>(1);
                weatherList.add(weatherDto);
                // DB 현재 미세먼지 등록
				weatherMapper.insertFineDutyData(weatherList);
            }

        }
        return weatherDto;
    }

}