package com.daewooenc.pips.admin.web.dao.energy;

import com.daewooenc.pips.admin.web.domain.dto.energy.EnergyAlarmTarget;
import com.daewooenc.pips.admin.web.domain.dto.energy.EnergyData;
import com.daewooenc.pips.admin.web.domain.dto.energy.EnergyPush;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 에너지 데이터 처리 mapper.
 *
 */
@Component
@Resource(name = "sqlDataSourceAdmin")
public interface EnergyMapper {
	/**
	 * 에너지 데이터 저장
	 */
	int insertEnergyData(List<EnergyData> energyData);
	int insertSumEnergyData(Map<String, String> condtion);
	int insertAvgEnergyData(Map<String, String> condtion);
	List<EnergyAlarmTarget> selectEnergyAlarmSendList(String enrgCumUseYmd);
	int insertEnergyPushData(EnergyPush energyPush);
	String selectElctUseQty(EnergyData energyData);
}
