package com.daewooenc.pips.admin.web.service.servicelink;

import com.daewooenc.pips.admin.web.dao.servicelink.ServiceLinkMapper;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLink;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLinkDetail;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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
 * 연계 웹/앱 관리 관련 Service
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-23      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-23
 **/
@Service
public class ServiceLinkService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private @Value("${pips.encrypt.key}") String pipsEncryptKey;

    @Autowired
    private ServiceLinkMapper serviceLinkMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private XSSUtil xssUtil;

    /**
     * 시스템 및 단지 관리자용 연계 웹/앱 리스트 조회를 위한 기본정보 조회
     * @param housingCplx
     * @return
     */
    public List<ServiceLink> getEtcServiceLinkMetaInfo(HousingCplx housingCplx) {
        List<ServiceLink> etcServiceLinkMetaInfoList = serviceLinkMapper.selectServiceLinkMetaInfo(housingCplx);

        return etcServiceLinkMetaInfoList;
    }

    /**
     * 시스템 및 단지 관리자용 연계 웹/앱 정보 상세 조회 (연계 웹/앱)
     * @param housingCplx
     * @return
     */
    public List<ServiceLinkDetail> getEtcServiceLink(HousingCplx housingCplx) {
        List<ServiceLinkDetail> etcServiceLinkList = serviceLinkMapper.selectServiceLink(housingCplx);

        return etcServiceLinkList;
    }

    /**
     * 시스템 용 연계 웹/앱 정보 상세 조회 (연계 웹/앱)
     * @param serviceLinkDetail
     * @return
     */
    public List<ServiceLinkDetail> getServiceDetailLinkList(ServiceLinkDetail serviceLinkDetail) {
        List<ServiceLinkDetail> etcServiceLinkList = serviceLinkMapper.selectServiceDetailLinkList(serviceLinkDetail);

        return etcServiceLinkList;
    }

    /**
     * 시스템 및 단지 관리자용 단지 관리 (기타) or 연계 웹/앱 관리 view를 위한 JSONArray
     * @param housingCplx
     */
    public JSONArray getLnkSvcInfoList(HousingCplx housingCplx) {
        List<ServiceLink> serviceLinkMetaList = getEtcServiceLinkMetaInfo(housingCplx);
        JSONArray lnkSvcInfoList = new JSONArray();

        for (int i=0; i<serviceLinkMetaList.size(); i++) {
            String metaType = serviceLinkMetaList.get(i).getLnkSvcGrpTpCd();
            String metaSvcNm = serviceLinkMetaList.get(i).getLnkSvcNm();

            housingCplx.setLnkSvcNm(metaSvcNm);
            List<ServiceLinkDetail> serviceLinkList = getEtcServiceLink(housingCplx);

            JSONObject urlInfoObject = new JSONObject();
            JSONObject schemaInfoObject = new JSONObject();
            JSONObject deepLinkInfoObject = new JSONObject();
            JSONObject appIdInfoObject = new JSONObject();

            for (int j=0; j<serviceLinkList.size(); j++) {
                String lnkSvcNm = serviceLinkList.get(j).getLnkSvcNm();
                String lnkAttrTpCd = serviceLinkList.get(j).getLnkAttrTpCd();
                String lnkTpCd = serviceLinkList.get(j).getLnkTpCd();
                String lnkAttrCont = StringUtils.defaultIfEmpty(serviceLinkList.get(j).getLnkAttrCont(), "");

                if (metaSvcNm.equals(lnkSvcNm)) {
                    if (lnkAttrTpCd.equals("URL")) {
                        urlInfoObject.put(lnkTpCd, lnkAttrCont);
                    } else if (lnkAttrTpCd.equals("SCHEMA")) {
                        schemaInfoObject.put(lnkTpCd, lnkAttrCont);
                    } else if (lnkAttrTpCd.equals("DEEP_LINK")) {
                        deepLinkInfoObject.put(lnkTpCd, lnkAttrCont);
                    } else if (lnkAttrTpCd.equals("APP_ID")) {
                        appIdInfoObject.put(lnkTpCd, lnkAttrCont);
                    }
                }

                if (j == (serviceLinkList.size() - 1)){
                    JSONObject lnkSvc = new JSONObject();
                    String fileNm = StringUtils.defaultIfEmpty(serviceLinkList.get(j).getOrgnlFileNm(), "");
                    String fileUrlCont = StringUtils.defaultIfEmpty(serviceLinkList.get(j).getFileUrlCont(), "");

                    lnkSvc.put("type", metaType);
                    lnkSvc.put("lnk_svc_nm", metaSvcNm);
                    lnkSvc.put("url", urlInfoObject);

                    String[] type = metaType.split("_");

                    if (type[1].equals("APP")) {
                        lnkSvc.put("schema", schemaInfoObject);
                        lnkSvc.put("deep_link", deepLinkInfoObject);
                        lnkSvc.put("app_id", appIdInfoObject);
                    }

                    lnkSvc.put("file_nm", fileNm);
                    lnkSvc.put("file_url", fileUrlCont);
                    lnkSvcInfoList.put(lnkSvc);
                }
            }
        }

        return lnkSvcInfoList;
    }

    /**
     * 연계 웹/앱 관리 view를 위한 JSONArray
     * @param serviceLinkDetail
     */
    public List<ServiceLinkDetail> getLnkSvcDetailList(ServiceLinkDetail serviceLinkDetail) {
        List<ServiceLinkDetail> serviceLinkList = getServiceDetailLinkList(serviceLinkDetail);

        return serviceLinkList;
    }

    /**
     * 시스템 관리자용 연계 웹/앱 목록 조회 (연계 웹/앱)
     * @param serviceLink
     * @return
     */
    public List<ServiceLink> getServiceLinkList(ServiceLink serviceLink) {
        List<ServiceLink> serviceLinkList = serviceLinkMapper.selectServiceLinkList(serviceLink);

        return serviceLinkList;
    }

    /**
     * 시스템 관리자용 연계 웹/앱 기본 상세정보 조회 (연계 웹/앱)
     * @param serviceLinkDetail
     * @return
     */
    public ServiceLinkDetail getServiceLinkDetail(ServiceLinkDetail serviceLinkDetail) {
        pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));
        serviceLinkDetail.setEncKey(pipsEncryptKey);
        ServiceLinkDetail serviceLinkDetails = serviceLinkMapper.selectServiceLinkDetail(serviceLinkDetail);

        return serviceLinkDetails;
    }

    /**
     * 시스템 관리자용 연계 웹/앱 정보 등록
     * @param serviceLinkDetailInfo
     * @param serviceLinkListArray
     */
    public void insertServiceLinkInfo(ServiceLinkDetail serviceLinkDetailInfo, JSONArray serviceLinkListArray, String danjiInfo) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));
            serviceLinkDetailInfo.setEncKey(pipsEncryptKey);
            if (serviceLinkMapper.insertServiceLinkInfo(serviceLinkDetailInfo) > 0) {
                Map<String, Object> serviceLinkDetailMap = new HashMap<String, Object>();

                int length = serviceLinkListArray.length();

                if (length > 0) {
                    List<ServiceLinkDetail> serviceLinkDetailList = new ArrayList<ServiceLinkDetail>();

                    for(int i=0; i<length; i++) {
                        ServiceLinkDetail serviceLinkDetail = new ServiceLinkDetail();

                        String lnkTpCd = xssUtil.replaceAll(StringUtils.defaultString(serviceLinkListArray.getJSONObject(i).getString("lnkTpCd")));
                        String lnkAttrTpCd = xssUtil.replaceAll(StringUtils.defaultString(serviceLinkListArray.getJSONObject(i).getString("lnkAttrTpCd")));
                        //String lnkAttrCont = xssUtil.replaceAll(StringUtils.defaultString(serviceLinkListArray.getJSONObject(i).getString("lnkAttrCont")));
                        String lnkAttrCont = StringUtils.defaultString(serviceLinkListArray.getJSONObject(i).getString("lnkAttrCont"));
                        String crerId = xssUtil.replaceAll(StringUtils.defaultString(serviceLinkDetailInfo.getCrerId()));

                        lnkTpCd = StringUtils.defaultIfEmpty(lnkTpCd, "");
                        lnkAttrTpCd = StringUtils.defaultIfEmpty(lnkAttrTpCd, "");
                        lnkAttrCont = StringUtils.defaultIfEmpty(lnkAttrCont, "");
                        crerId = StringUtils.defaultIfEmpty(crerId, "");

                        serviceLinkDetail.setLnkTpCd(lnkTpCd);
                        serviceLinkDetail.setLnkAttrTpCd(lnkAttrTpCd);
                        serviceLinkDetail.setLnkAttrCont(lnkAttrCont);
                        serviceLinkDetail.setCrerId(crerId);

                        serviceLinkDetailList.add(serviceLinkDetail);
                    }
                    serviceLinkDetailMap.put("list", serviceLinkDetailList);
                    serviceLinkMapper.insertServiceLinkDetailInfo(serviceLinkDetailMap);
                }



                String fileUrlCont = StringUtils.defaultString(serviceLinkDetailInfo.getFileUrlCont());
                String fileNm = StringUtils.defaultString(serviceLinkDetailInfo.getFileNm());

                if (StringUtils.isNotEmpty(fileUrlCont) && StringUtils.isNotEmpty(fileNm)) {
                    serviceLinkDetailInfo.setFileUrlCont(serviceLinkDetailInfo.getFileUrlCont() + "&file_nm=" + serviceLinkDetailInfo.getFileNm());
                }

                serviceLinkMapper.updateServiceFileUrl(serviceLinkDetailInfo);

                String[] danjiInfoList = danjiInfo.split(",");

                int serviceLinkKeyInfo = getServiceLinkKeyInfo();

                Map<String, Object> serviceLinkDanjiInfoMap = new HashMap<>();

                serviceLinkDanjiInfoMap.put("crerId", xssUtil.replaceAll(StringUtils.defaultString(serviceLinkDetailInfo.getCrerId())));
                serviceLinkDanjiInfoMap.put("lnk_svc_id", serviceLinkKeyInfo);

                for(int i=0;i<danjiInfoList.length;i++){
                    serviceLinkDanjiInfoMap.put("houscplx_cd", danjiInfoList[i]);
                    serviceLinkMapper.insertServiceLinkDanjiInfo(serviceLinkDanjiInfoMap);
                }



            }
        } catch (Exception e) {
            logger.debug("insertServiceLinkInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 시스템 관리자용 등록된 배너 갯수 조회
     * @return
     */
    public int getServiceLinkKeyInfo() {
        int serviceLinkKeyInfo = serviceLinkMapper.selectServiceLinkKeyInfo();

        return serviceLinkKeyInfo;
    }

    /**
     * 시스템 관리자용 연계 웹/앱 정보 수정
     * @param serviceLinkDetailInfo
     * @param serviceLinkListArray
     */
    public void updateServiceLinkInfo(ServiceLinkDetail serviceLinkDetailInfo, JSONArray serviceLinkListArray, String danjiInfo) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            logger.debug("serviceLinkDetailInfo - 1 : " + serviceLinkDetailInfo.getLnkSvcId() + ", " + serviceLinkDetailInfo.getLnkSvcNm());
            pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));
            serviceLinkDetailInfo.setEncKey(pipsEncryptKey);
            if (serviceLinkMapper.updateServiceLinkInfo(serviceLinkDetailInfo) > 0) {
                serviceLinkMapper.deleteServiceLinkDetailInfo(serviceLinkDetailInfo.getLnkSvcId(), serviceLinkDetailInfo.getCrerId());

                Map<String, Object> serviceLinkDetailMap = new HashMap<String, Object>();

                int length = serviceLinkListArray.length();

                if (length > 0) {
                    List<ServiceLinkDetail> serviceLinkDetailList = new ArrayList<ServiceLinkDetail>();

                    for(int i=0; i<length; i++) {
                        ServiceLinkDetail serviceLinkDetail = new ServiceLinkDetail();

                        String lnkTpCd = xssUtil.replaceAll(StringUtils.defaultString(serviceLinkListArray.getJSONObject(i).getString("lnkTpCd")));
                        String lnkAttrTpCd = xssUtil.replaceAll(StringUtils.defaultString(serviceLinkListArray.getJSONObject(i).getString("lnkAttrTpCd")));
                        //String lnkAttrCont = xssUtil.replaceAll(StringUtils.defaultString(serviceLinkListArray.getJSONObject(i).getString("lnkAttrCont")));
                        String lnkAttrCont = StringUtils.defaultString(serviceLinkListArray.getJSONObject(i).getString("lnkAttrCont"));

                        lnkTpCd = StringUtils.defaultIfEmpty(lnkTpCd, "");
                        lnkAttrTpCd = StringUtils.defaultIfEmpty(lnkAttrTpCd, "");
                        lnkAttrCont = StringUtils.defaultIfEmpty(lnkAttrCont, "");

                        serviceLinkDetail.setLnkSvcId(serviceLinkDetailInfo.getLnkSvcId());
                        serviceLinkDetail.setLnkTpCd(lnkTpCd);
                        serviceLinkDetail.setLnkAttrTpCd(lnkAttrTpCd);
                        serviceLinkDetail.setLnkAttrCont(lnkAttrCont);
                        serviceLinkDetail.setCrerId(serviceLinkDetailInfo.getCrerId());
                        serviceLinkDetail.setEditerId(serviceLinkDetailInfo.getCrerId());

                        serviceLinkDetailList.add(serviceLinkDetail);
                    }
                    serviceLinkDetailMap.put("list", serviceLinkDetailList);
                }

                if (serviceLinkDetailMap.size() > 0) {
                    serviceLinkMapper.insertServiceLinkDetailInfoForDel(serviceLinkDetailMap);

                    String[] danjiInfoList = danjiInfo.split(",");

                    if(!danjiInfoList.equals("")) {
                        int serviceLinkKeyInfo = serviceLinkDetailInfo.getLnkSvcId();

                        Map<String, Object> serviceLinkDanjiInfoMap = new HashMap<>();

                        serviceLinkDanjiInfoMap.put("crerId", xssUtil.replaceAll(StringUtils.defaultString(serviceLinkDetailInfo.getCrerId())));
                        serviceLinkDanjiInfoMap.put("lnk_svc_id", serviceLinkKeyInfo);

                        serviceLinkMapper.deleteServiceLinkDanjiInfo(serviceLinkKeyInfo);

                        for (int i = 0; i < danjiInfoList.length; i++) {
                            serviceLinkDanjiInfoMap.put("houscplx_cd", danjiInfoList[i]);
                            serviceLinkMapper.insertServiceLinkDanjiInfo(serviceLinkDanjiInfoMap);
                        }
                    }
                }else{
                    String[] danjiInfoList = danjiInfo.split(",");
                    if(!danjiInfoList.equals("")) {
                        int serviceLinkKeyInfo = serviceLinkDetailInfo.getLnkSvcId();

                        Map<String, Object> serviceLinkDanjiInfoMap = new HashMap<>();

                        serviceLinkDanjiInfoMap.put("crerId", xssUtil.replaceAll(StringUtils.defaultString(serviceLinkDetailInfo.getCrerId())));
                        serviceLinkDanjiInfoMap.put("lnk_svc_id", serviceLinkKeyInfo);

                        serviceLinkMapper.deleteServiceLinkDanjiInfo(serviceLinkKeyInfo);

                        for (int i = 0; i < danjiInfoList.length; i++) {
                            serviceLinkDanjiInfoMap.put("houscplx_cd", danjiInfoList[i]);
                            serviceLinkMapper.insertServiceLinkDanjiInfo(serviceLinkDanjiInfoMap);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("updateServiceLinkInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 시스템 관리자용 연계 웹/앱 정보 삭제
     * @param lnkSvdId
     */
    public void deleteServiceLinkInfo(int lnkSvdId, String adminId) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            serviceLinkMapper.deleteServiceLinkInfo(lnkSvdId, adminId);
            serviceLinkMapper.deleteServiceLinkDetailInfo(lnkSvdId, adminId);
        } catch (Exception e) {
            logger.debug("deleteServiceLinkInfo Exception: " + e.getCause());
            transactionManager.rollback(transactionStatus);
        }

        transactionManager.commit(transactionStatus);
    }

    /**
     * 시스템 관리자용 연계 웹/앱 정렬순서 중복 체크
     * @param lnkOrdNo
     */
    public boolean checkServiceLinkOrd(int lnkOrdNo) {
        return serviceLinkMapper.checkServiceLinkOrd(lnkOrdNo) > 0;
    }
}