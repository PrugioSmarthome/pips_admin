package com.daewooenc.pips.admin.web.domain.dto.weather;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 사용자 Domain.
 *
 */
@XStreamAlias("specialWeatherLocation")
public class SpecialWeatherLocation {

	/** 지역명 */
	private String areaNm;

	/** 코드명 */
	private String areaCd;

	public String getAreaNm() {
		return areaNm;
	}

	public void setAreaNm(String areaNm) {
		this.areaNm = areaNm;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	@Override
	public String toString() {
		return areaCd;
	}

}
