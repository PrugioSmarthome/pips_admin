package com.daewooenc.pips.admin.core.config;

import com.daewooenc.pips.admin.core.dao.authorization.MenuMapper;
import com.daewooenc.pips.admin.core.domain.authorization.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;

/**
 * Spring의 ApplicationContext 가 load 될 때,
 * 전체 메뉴 리스트에 대한 정보를 불러오기 위한 Listener.
 */
@Component
public class MenuListener implements ApplicationListener<ContextRefreshedEvent> {

	/** the logger.	 */
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/** ServletContext Autowired. */
	@Autowired
	private ServletContext sc;

	/** MenuMapper Autowired. */
	@Autowired
	private MenuMapper menuMapper;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			HashMap<Integer, String> menuMap = new HashMap<Integer, String>();
			List<Menu> menus = menuMapper.list();
			for (Menu m : menus) {

				int menuNo = m.getMenuNo();
				String menuName = m.getMenuName();

				logger.debug("menuNo :{}, menuName:{}", menuNo, menuName);

				menuMap.put(menuNo, menuName);
			}

			sc.setAttribute("MenuMap", menuMap);
		} catch (Exception e) {
			logger.error("{}", e);
		}
	}
}
