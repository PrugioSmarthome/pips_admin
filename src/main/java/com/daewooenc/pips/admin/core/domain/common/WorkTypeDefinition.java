package com.daewooenc.pips.admin.core.domain.common;

/**
 * 작업 타입 정의.
 */
public interface WorkTypeDefinition {

	/** 검색. */
    int WORK_TYPE_SEARCH  = 0;

    /** 입력. */
    int WORK_TYPE_INSERT  = 1;

    /** 삭제. */
    int WORK_TYPE_DELETE  = 2;

    /** 변경. */
    int WORK_TYPE_UPDATE  = 3;

    /** 페이지 이동. */
    int WORK_TYPE_MOVE_PAGE   = 4;

	/**
	 * 작업 타입 이름.
	 */
    String[] WORK_TYPE_NAME = new String[]{
    	"Display",
        "Insert",
        "Delete",
        "Update",
        "Menu Select",
    };
}
