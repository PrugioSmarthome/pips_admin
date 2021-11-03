package com.daewooenc.pips.admin.web.service.facility;

import com.daewooenc.pips.admin.web.dao.facility.FacilityMapper;
import com.daewooenc.pips.admin.web.domain.dto.community.NoticeFile;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizco;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizcoCaddr;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizcoCaddrRelation;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizcodHousingCplxRelation;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 시설업체관리 관련 Service
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-18      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-18
 **/
@Service
public class FacilityService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FacilityMapper facilityMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private XSSUtil xssUtil;

    private @Value("${pips.encrypt.key}") String pipsEncryptKey;

    /**
     * 시설업체 엑셀다운로드 목록 조회
     * @param facilityBizco
     * @return
     */
    public List<FacilityBizco> getFacilityInfoExcelList(FacilityBizco facilityBizco) {
        List<FacilityBizco> facilityBizcoExcelList = facilityMapper.selectFacilityInfoExcelList(facilityBizco);

        return facilityBizcoExcelList;
    }

    /**
     * 시설업체 기본정보 목록 조회
     * @param facilityBizco
     * @return
     */
    public List<FacilityBizco> getFacilityInfoList(FacilityBizco facilityBizco) {
        List<FacilityBizco> facilityBizcoList = facilityMapper.selectFacilityInfoList(facilityBizco);

        return facilityBizcoList;
    }

    /**
     * 해당 시설업체 연락처 정보 목록 조회
     * @param facilityBizco
     * @return
     */
    public List<FacilityBizcoCaddr> getFacilityInfoCaddrList(FacilityBizco facilityBizco) {
        List<FacilityBizcoCaddr> facilityBizcoCaddrList = facilityMapper.selectFacilityInfoCaddrList(facilityBizco);

        return facilityBizcoCaddrList;
    }

    /**
     * 시스템 및 단지 관리자용 시설업체 정보 등록
     * @param facilityBizco
     * @param addressList
     */
    public void insertFacilityInfo(FacilityBizco facilityBizco, List<FacilityBizcoCaddr> addressList, String houscplxCd) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            Map<String, Object> facilityBizcoCaddrRelationMap = new HashMap<String, Object>();
            List<FacilityBizcoCaddrRelation> facilityBizcoCaddrRelationList = new ArrayList<>();

            facilityMapper.insertFacilityInfo(facilityBizco);

            int facltBizcoId = facilityBizco.getFacltBizcoId();
            String adminId = facilityBizco.getCrerId();

            for (int i=0; i<addressList.size(); i++) {
                FacilityBizcoCaddr facilityBizcoCaddr = addressList.get(i);
                pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));
                facilityBizcoCaddr.setEncKey(pipsEncryptKey);
                facilityMapper.insertFacilityCaddrInfo(facilityBizcoCaddr);

                int facltBizcoCaddrId = facilityBizcoCaddr.getFacltBizcoCaddrId();

                FacilityBizcoCaddrRelation facilityBizcoCaddrRelation = new FacilityBizcoCaddrRelation();
                facilityBizcoCaddrRelation.setFacltBizcoId(facltBizcoId);
                facilityBizcoCaddrRelation.setFacltBizcoCaddrId(facltBizcoCaddrId);
                facilityBizcoCaddrRelation.setCrerId(adminId);

                facilityBizcoCaddrRelationList.add(facilityBizcoCaddrRelation);
            }

            facilityBizcoCaddrRelationMap.put("list", facilityBizcoCaddrRelationList);

            facilityMapper.insertFacilityCaddrInfoRelation(facilityBizcoCaddrRelationMap);

            FacilityBizcodHousingCplxRelation facilityBizcodHousingCplxRelation = new FacilityBizcodHousingCplxRelation();
            facilityBizcodHousingCplxRelation.setFacltBizcoId(facltBizcoId);
            facilityBizcodHousingCplxRelation.setHouscplxCd(houscplxCd);
            facilityBizcodHousingCplxRelation.setCrerId(adminId);

            facilityMapper.insertFacilityInfoRelation(facilityBizcodHousingCplxRelation);

        } catch (Exception e) {
            logger.debug("insertFacilityInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 시스템 및 단지 관리자용 시설업체 연락처 정보 등록
     * @param facilityBizco
     * @param addressList
     */
    public void insertFacilityInfoCaddrInfo(FacilityBizco facilityBizco, List<FacilityBizcoCaddr> addressList) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            int facltBizcoId = facilityBizco.getFacltBizcoId();
            String adminId = facilityBizco.getEditerId();

            for (int i=0; i<addressList.size(); i++) {
                FacilityBizcoCaddr facilityBizcoCaddr = addressList.get(i);
                pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));
                facilityBizcoCaddr.setEncKey(pipsEncryptKey);

                Integer prevFacltBizcoCaddrId = facilityBizcoCaddr.getFacltBizcoCaddrId();

                if (prevFacltBizcoCaddrId != 0) {
                    facilityMapper.updateFacilityCaddrInfo(facilityBizcoCaddr);
                } else if (prevFacltBizcoCaddrId == 0) {
                    facilityMapper.insertFacilityCaddrInfo(facilityBizcoCaddr);

                    int facltBizcoCaddrId = facilityBizcoCaddr.getFacltBizcoCaddrId();

                    FacilityBizcoCaddrRelation facilityBizcoCaddrRelation = new FacilityBizcoCaddrRelation();
                    facilityBizcoCaddrRelation.setFacltBizcoId(facltBizcoId);
                    facilityBizcoCaddrRelation.setFacltBizcoCaddrId(facltBizcoCaddrId);
                    facilityBizcoCaddrRelation.setCrerId(adminId);

                    facilityMapper.insertFacilityCaddrInfoRelationOne(facilityBizcoCaddrRelation);
                }
            }
        } catch (Exception e) {
            logger.debug("insertFacilityInfoCaddrInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 시설업체 기본정보 수정
     * @param facilityBizco
     * @return
     */
    public boolean updateFacilityInfo(FacilityBizco facilityBizco) {
        return facilityMapper.updateFacilityInfo(facilityBizco) > 0;
    }

    /**
     * 시설업체 기본정보 삭제 (기본정보 및 연락처 정보 삭제 수정 처리, 단지 or 시설 및 연락처 관계정보)
     * @param facilityBizco
     */
    public void deleteFacilityInfo(FacilityBizco facilityBizco) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            List<FacilityBizcoCaddr> facilityBizcoCaddrInfoList = facilityMapper.selectFacilityInfoCaddrIdList(facilityBizco);

            facilityMapper.deleteFacilityInfo(facilityBizco);

            if (facilityBizcoCaddrInfoList.size() > 0) {
                Map<String, Object> facilityBizcoCaddrMap = new HashMap<String, Object>();
                List<FacilityBizcoCaddr> facilityBizcoCaddrList = new ArrayList<>();

                for (int i=0; i<facilityBizcoCaddrInfoList.size(); i++) {
                    FacilityBizcoCaddr facilityBizcoCaddr = facilityBizcoCaddrInfoList.get(i);
                    facilityBizcoCaddr.setEditerId(facilityBizco.getEditerId());
                    facilityBizcoCaddr.setFacltBizcoCaddrId(facilityBizcoCaddr.getFacltBizcoCaddrId());

                    facilityBizcoCaddrList.add(facilityBizcoCaddr);
                }

                facilityBizcoCaddrMap.put("list", facilityBizcoCaddrList);

                facilityMapper.deleteFacilityInfoCaddrList(facilityBizcoCaddrMap);
            }

            facilityMapper.deleteFacilityInfoHousingCplxRelation(facilityBizco);
            facilityMapper.deleteFacilityInfoCaddrRelation(facilityBizco);
        } catch (Exception e) {
            logger.debug("deleteFacilityInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 해당단지의 시설업체 기본정보 및 연락처 삭제 (기본정보 및 연락처 정보 삭제 수정 처리, 단지 or 시설 및 연락처 관계정보)
     * @param facilityBizco
     */
    public void deleteFacilityInfoAll(FacilityBizco facilityBizco) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            List<FacilityBizco> facilityBizcoIdList = facilityMapper.selectFacilityBizcoIdList(facilityBizco);

            if (facilityBizcoIdList.size() > 0) {
                Map<String, Object> facilityBizcoMap = new HashMap<String, Object>();
                List<FacilityBizco> facilityBizcoList = new ArrayList<>();

                for (int i=0; i<facilityBizcoIdList.size(); i++) {
                    FacilityBizco facilityBizcoInfo = facilityBizcoIdList.get(i);
                    facilityBizcoInfo.setEditerId(facilityBizco.getEditerId());
                    facilityBizcoInfo.setFacltBizcoId(facilityBizcoInfo.getFacltBizcoId());

                    facilityBizcoList.add(facilityBizcoInfo);
                }

                facilityBizcoMap.put("list", facilityBizcoList);

                facilityMapper.deleteFacilityInfoList(facilityBizcoMap);
            }

            List<FacilityBizcoCaddr> facilityBizcoCaddrIdList = facilityMapper.selectFacilityBizcoCaddrIdList(facilityBizco);
            Map<String, Object> facilityBizcoCaddrMap = new HashMap<String, Object>();

            if (facilityBizcoCaddrIdList.size() > 0) {
                List<FacilityBizcoCaddr> facilityBizcoCaddrList = new ArrayList<>();

                for (int i=0; i<facilityBizcoCaddrIdList.size(); i++) {
                    FacilityBizcoCaddr facilityBizcoCaddr = facilityBizcoCaddrIdList.get(i);
                    facilityBizcoCaddr.setEditerId(facilityBizco.getEditerId());
                    facilityBizcoCaddr.setFacltBizcoCaddrId(facilityBizcoCaddr.getFacltBizcoCaddrId());

                    facilityBizcoCaddrList.add(facilityBizcoCaddr);
                }

                facilityBizcoCaddrMap.put("list", facilityBizcoCaddrList);

                facilityMapper.deleteFacilityInfoCaddrList(facilityBizcoCaddrMap);
            }

            facilityMapper.deleteFacilityInfoHousingCplxRelationAll(facilityBizco);

            if (facilityBizcoCaddrMap.size() > 0) {
                facilityMapper.deleteFacilityInfoCaddrRelationAll(facilityBizcoCaddrMap);
            }
        } catch (Exception e) {
            logger.debug("deleteFacilityInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 해당단지의 해당 시설업체 연락처 삭제
     * @param facltBizcoCaddrId
     * @param falctBizcoId
     * @param adminId
     */
    public void deleteFacilityInfoCaddrInfoOne(int facltBizcoCaddrId, int falctBizcoId, String adminId) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            FacilityBizcoCaddr facilityBizcoCaddr = new FacilityBizcoCaddr();
            facilityBizcoCaddr.setFacltBizcoCaddrId(facltBizcoCaddrId);
            facilityBizcoCaddr.setEditerId(adminId);

            facilityMapper.deleteFacilityInfoCaddr(facilityBizcoCaddr);

            FacilityBizcoCaddrRelation facilityBizcoCaddrRelation = new FacilityBizcoCaddrRelation();
            facilityBizcoCaddrRelation.setFacltBizcoId(falctBizcoId);
            facilityBizcoCaddrRelation.setFacltBizcoCaddrId(facltBizcoCaddrId);
            facilityMapper.deleteFacilityInfoCaddrRelationOne(facilityBizcoCaddrRelation);
        } catch (Exception e) {
            logger.debug("deleteFacilityInfoCaddrInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 해당단지의 해당 시설업체 연락처 전체 삭제
     * @param facilityBizco
     */
    public void deleteFacilityInfoCaddrInfoAll(FacilityBizco facilityBizco) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            List<FacilityBizcoCaddr> facilityBizcoCaddrInfoList = facilityMapper.selectFacilityInfoCaddrIdList(facilityBizco);

            Map<String, Object> facilityBizcoCaddrMap = new HashMap<String, Object>();
            List<FacilityBizcoCaddr> facilityBizcoCaddrList = new ArrayList<>();

            for (int i=0; i<facilityBizcoCaddrInfoList.size(); i++) {
                FacilityBizcoCaddr facilityBizcoCaddr = facilityBizcoCaddrInfoList.get(i);
                facilityBizcoCaddr.setEditerId(facilityBizco.getEditerId());
                facilityBizcoCaddr.setFacltBizcoCaddrId(facilityBizcoCaddr.getFacltBizcoCaddrId());

                facilityBizcoCaddrList.add(facilityBizcoCaddr);
            }

            facilityBizcoCaddrMap.put("list", facilityBizcoCaddrList);

            facilityMapper.deleteFacilityInfoCaddrList(facilityBizcoCaddrMap);
            facilityMapper.deleteFacilityInfoCaddrRelation(facilityBizco);
        } catch (Exception e) {
            logger.debug("deleteFacilityInfoCaddrInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }
}