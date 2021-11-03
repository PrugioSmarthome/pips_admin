package com.daewooenc.pips.admin.web.domain.dto.weather;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 사용자 Domain.
 *
 */
@XStreamAlias("weatherLocation")
public class WeatherLocation {

	/** 격자 좌표 : X. */
	private int wrMeasXCoorNo;

	/** 격자 좌표 : Y. */
	private int wrMeasYCoorNo;

	public int getWrMeasXCoorNo() {
		return wrMeasXCoorNo;
	}

	public void setWrMeasXCoorNo(int wrMeasXCoorNo) {
		this.wrMeasXCoorNo = wrMeasXCoorNo;
	}

	public int getWrMeasYCoorNo() {
		return wrMeasYCoorNo;
	}

	public void setWrMeasYCoorNo(int wrMeasYCoorNo) {
		this.wrMeasYCoorNo = wrMeasYCoorNo;
	}

	@Override
	public String toString() {
		return "WeatherLocation{" +
				"wrMeasXCoorNo=" + wrMeasXCoorNo +
				", wrMeasYCoorNo=" + wrMeasYCoorNo +
				'}';
	}

}
