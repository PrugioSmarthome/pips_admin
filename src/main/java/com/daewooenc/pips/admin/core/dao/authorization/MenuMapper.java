package com.daewooenc.pips.admin.core.dao.authorization;

import com.daewooenc.pips.admin.core.domain.authorization.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 메뉴 mapper.
 *
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface MenuMapper {
    /**
     *시스템 관리자용 메뉴 총 개수 조회
     * @return
     */
    int selectMenuLastNo();

    /**
     * 시스템 관리자용 전체 메뉴 목록 조회
     * @return
     */
    List<Menu> selectMenuList();

    /**
     * 시스템 관리자용 메뉴 정보 목록 조회
     * @param menuNo
     * @return
     */
    List<Menu> selectMenuInfoList(@Param("menuNo") int menuNo);

    /**
     * 시스템 관리자용 메뉴 정보 등록
     * @param menu
     * @return
     */
    int insertMenu(Menu menu);

    /**
     * 메뉴정보 수정.
     *
     * @param menu 메뉴정보
     * @return int
     */
    int updateMenu(Menu menu);

    /**
     * 메뉴 리스트 정보 수정
     * @param menuMap
     * @return
     */
    int updateMenuItemOrderList(Map<String, Object> menuMap);

    /**
     * 시스템 관리자용 상, 하위메뉴 정보 삭제
     * @param menuNo
     * @return
     */
    int deleteMenuItemForUpMenu(@Param("menuNo") int menuNo);

    /**
     * 시스템 관리자용 하위메뉴 정보 삭제
     * @return
     */
    int deleteMenuItemForDownMenu(@Param("menuNo") int menuNo);

    /**
     * 기존코드: 메뉴 목록.
     *
     * @return List<Menu>
     */
    List<Menu> list();

    /**
     * 기존코드: 수정.
     *
     * @param menu 메뉴정보
     * @return int
     */
    int update(Menu menu);

    /**
     * up_menu_no 조회
     */
    String selectUpMenuNo(Menu menu);
}
