package com.daewooenc.pips.admin.core.domain.authorization;

import java.io.Serializable;

/**
 * 선택 메뉴 Domain.
 */
public class SelectedMenu implements Serializable{
	/** Serializable serialVersionUID. */
	private static final long serialVersionUID = 2269785928007434720L;

	/** 메뉴 번호. */
	private int paramMenuNo;

	/** 선택 메뉴 번호. */
	private int selectMenuNo;

	/** 메뉴클래스. top 메뉴에서 이미지 표시를 위해 필요함. */
	private String menuClass;

	/** 최상위 메뉴. */
	private int topMenuNo;


	public int getParamMenuNo() {
		return paramMenuNo;
	}
	public void setParamMenuNo(int paramMenuNo) {
		this.paramMenuNo = paramMenuNo;
	}
	public int getSelectMenuNo() {
		return selectMenuNo;
	}
	public void setSelectMenuNo(int selectMenuNo) {
		this.selectMenuNo = selectMenuNo;
	}
	public String getMenuClass() {
		return menuClass;
	}
	public void setMenuClass(String menuClass) {
		this.menuClass = menuClass;
	}
	public int getTopMenuNo() {
		return topMenuNo;
	}
	public void setTopMenuNo(int topMenuNo) {
		this.topMenuNo = topMenuNo;
	}
	@Override
	public String toString() {
		return "SelectedMenu [paramMenuNo=" + paramMenuNo + ", selectMenuNo="
				+ selectMenuNo + ", menuClass=" + menuClass + ", topMenuNo="
				+ topMenuNo + "]";
	}

}
