package com.daewooenc.pips.admin.web.service.servermgmt;

import com.daewooenc.pips.admin.web.dao.servermgmt.ServerMgmtMapper;
import com.daewooenc.pips.admin.web.domain.dto.servermgmt.ServerMgmtCondition;
import com.daewooenc.pips.admin.web.domain.dto.servermgmt.ServerMgmt;
import com.daewooenc.pips.admin.web.domain.vo.servermgmt.ServerConfCtrVo;
import com.daewooenc.pips.admin.web.domain.vo.servermgmt.ServerDataSendCtrVo;
import com.daewooenc.pips.admin.web.domain.vo.servermgmt.ServerMgmtVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-08-07       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-07
 **/
@Service
public class ServerMgmtService {
    @Autowired
    private ServerMgmtMapper serverMgmtMapper;
    public boolean insert(ServerMgmtVo serverMgmtVo) {
        return serverMgmtMapper.insertServerMgmt(serverMgmtVo) > 0;
    }
    public boolean update(ServerMgmtVo serverMgmtVo) {
        return serverMgmtMapper.updateServerMgmt(serverMgmtVo) > 0;
    }
    public boolean confControlUpdate(ServerConfCtrVo serverCtrVo) {
        return serverMgmtMapper.confControlUpdateServerMgmt(serverCtrVo) > 0;
    }
    public boolean dataSendControlUpdateServerMgmt(ServerDataSendCtrVo serverDataSendCtrVo) {
        return serverMgmtMapper.dataSendControlUpdateServerMgmt(serverDataSendCtrVo) > 0;
    }
    public boolean statusUpdateServerMgmt(ServerMgmtVo serverMgmtVo) {
        return serverMgmtMapper.statusUpdateServerMgmt(serverMgmtVo) > 0;
    }

    public List<ServerMgmt> listServerMgmt(ServerMgmtCondition serverMgmtCondition) {
        return serverMgmtMapper.selectServerMgmtList(serverMgmtCondition);
    }
    public ServerMgmt getServerMgmt(String homenetId) {
        return serverMgmtMapper.getServerMgmt(homenetId);
    }
}