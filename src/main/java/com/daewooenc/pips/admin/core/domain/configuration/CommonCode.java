package com.daewooenc.pips.admin.core.domain.configuration;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 공통코드 관리.
 *
 */
@XStreamAlias("commonCode")
public class CommonCode {

	/** 그룹코드. */
	private String groupCode;

	/** 상세코드. */
	private String detailCode;

	/** 코드명. */
	private String codeName;

	/** 키명. */
	private String keyName;

	/** 출력순서. */
	private Integer displayOrder;

	/** 설명. */
	private String description;

	/** 이전 상세코드. */
	private String detailCodeOld;

	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getDetailCode() {
		return detailCode;
	}
	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDetailCodeOld() {
		return detailCodeOld;
	}
	public void setDetailCodeOld(String detailCodeOld) {
		this.detailCodeOld = detailCodeOld;
	}


}
