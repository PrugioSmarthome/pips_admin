package com.daewooenc.pips.admin.core.domain.configuration;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 이력조회 조건.
 */
@XStreamAlias("historyCondition")
public class HistoryCondition {

	/** 시작일. */
	private String startDate;

	/** 종료일. */
	private String endDate;

	/** 시작시간. */
	private String startTime;

	/** 종료시간. */
	private String endTime;

	/** 시작 시. */
	private String startHour;
	/** 종료 시. */
	private String endHour;

	/** 시작 분. */
	private String startMin;

	/** 종료 분. */
	private String endMin;

	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public String getStartMin() {
		return startMin;
	}
	public void setStartMin(String startMin) {
		this.startMin = startMin;
	}
	public String getEndMin() {
		return endMin;
	}
	public void setEndMin(String endMin) {
		this.endMin = endMin;
	}
}
