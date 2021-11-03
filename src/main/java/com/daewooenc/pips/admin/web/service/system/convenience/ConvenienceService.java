package com.daewooenc.pips.admin.web.service.system.convenience;

import com.daewooenc.pips.admin.web.dao.convenience.ConvenienceMapper;
import com.daewooenc.pips.admin.web.domain.dto.convenience.Convenience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-11-17      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-11-17
 **/
@Service
public class ConvenienceService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConvenienceMapper conveniencemapper;

    /**
     * 시스템 관리자용 편의 시설 목록 조회
     * @return
     */
    public List<Convenience> getSystemConvenienceList(Convenience convenience) {
        List<Convenience> convenienceList = conveniencemapper.selectSystemConvenienceList(convenience);

        return convenienceList;
    }

    /**
     * 시스템 관리자용 편의 시설 수정 조화
     * @return
     */
    public Convenience getSystemConvenienceEdit(Convenience convenience) {
        Convenience convenienceEdit = conveniencemapper.selectSystemConvenienceEdit(convenience);

        return convenienceEdit;
    }

    /**
     * 시스템 관리자용 편의 시설 수정
     * @return
     */
    public boolean insertConvenience(Convenience convenience) {
        return conveniencemapper.insertConvenience(convenience) > 0;
    }

    /**
     * 시스템 관리자용 편의 시설 삭제
     * @return
     */
    public boolean deleteConvenience(Convenience Convenience) {
        return conveniencemapper.deleteConvenience(Convenience) > 0;
    }

}