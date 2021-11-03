package com.daewooenc.pips.admin.web.dao.servermgmt;

import com.daewooenc.pips.admin.web.domain.dto.servermgmt.ServerMgmtCondition;
import com.daewooenc.pips.admin.web.domain.dto.servermgmt.ServerMgmt;
import com.daewooenc.pips.admin.web.domain.vo.servermgmt.ServerConfCtrVo;
import com.daewooenc.pips.admin.web.domain.vo.servermgmt.ServerDataSendCtrVo;
import com.daewooenc.pips.admin.web.domain.vo.servermgmt.ServerMgmtVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Resource(name = "sqlDataSourceAdmin")
public interface ServerMgmtMapper {
    int insertServerMgmt(ServerMgmtVo serverMgmtVo);
    int updateServerMgmt(ServerMgmtVo serverMgmtVo);
    int confControlUpdateServerMgmt(ServerConfCtrVo serverCtrVo);
    int dataSendControlUpdateServerMgmt(ServerDataSendCtrVo serverDataSendCtrVo);
    int statusUpdateServerMgmt(ServerMgmtVo serverMgmtVo);
    List<ServerMgmt> selectServerMgmtList(ServerMgmtCondition serverMgmtCondition);
    ServerMgmt getServerMgmt(@Param("homenetId") String homenetId );
}
