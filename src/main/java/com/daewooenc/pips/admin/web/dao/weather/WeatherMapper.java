package com.daewooenc.pips.admin.web.dao.weather;

import com.daewooenc.pips.admin.web.domain.dto.weather.SpecialWeatherLocation;
import com.daewooenc.pips.admin.web.domain.dto.weather.Weather;
import com.daewooenc.pips.admin.web.domain.dto.weather.WeatherLocation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * 날씨 정보 구하는 mapper.
 *
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface WeatherMapper {

	/**
	 * 단지 정보에서 날씨를 구해야할 위치 정보를 취득
	 *
	 * @return the WeatherLocation
	 *
	 * 설명 : 날씨를 구해야할 위치 정보
	 */
	List<WeatherLocation> getWeatherLocationList();

	/**
	 * 기상 특보를 구해야할 지역 코드 정보를 취득
	 *
	 * @return the SpecialWeatherLocation
	 *
	 * 설명 : 날씨를 구해야할 위치 정보
	 */
	List<SpecialWeatherLocation> getSpecialWeatherLocationList();

	/**
	 * DB에 저장된 현재 날씨를 취득
	 *
	 * @return the WeatherDao
	 *
	 * 설명 : 날씨를 구해야할 위치 정보
	 */
	Weather getCurrentWeather(@Param("wrMeasXCoorNo") String location_x, @Param("wrMeasYCoorNo") String location_y, @Param("wrTpCd") String weatherType);

	/**
	 * 날씨 테이블 초기화(truncate)
	 */
	void setWeatherDataInit();
	/**
	 * 날씨 데이터 삭제
	 */
	int deleteWeather(@Param("wrMeasXCoorNo") String location_x, @Param("wrMeasYCoorNo") String location_y, @Param("wrTpCd") String weatherType);

	int deleteSpecialWeather(@Param("areaCd") String areaCd);

	void truncateSpecialWeather();

	int deleteSpecialWeatherRelease();

	int insertWeatherData(List<Weather> weatherList);

	int insertSpecialWeatherData(List<Weather> weatherList);

	int updateCurrentSkyStatus(Weather weather);

	int updateForecast(List<Weather> weatherList);

	int deleteFineDuty(@Param("addrSiDoNm") String addrSiDoNm);

	int insertFineDutyData(List<Weather> weatherList);
}
