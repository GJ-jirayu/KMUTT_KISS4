package th.ac.kmutt.chart.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Formatter.BigDecimalLayoutForm;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import th.ac.kmutt.chart.constant.ServiceConstant;
import th.ac.kmutt.chart.domain.ChartEntity;
import th.ac.kmutt.chart.domain.ChartFeatureEntity;
import th.ac.kmutt.chart.domain.ChartFeatureInstanceEntity;
import th.ac.kmutt.chart.domain.ChartFeatureMappingEntity;
import th.ac.kmutt.chart.domain.ChartFeatureMappingEntityPK;
import th.ac.kmutt.chart.domain.ChartFilterInstanceEntity;
import th.ac.kmutt.chart.domain.ChartInstanceEntity;
import th.ac.kmutt.chart.domain.CommentEntity;
import th.ac.kmutt.chart.domain.CopyrightServiceEntity;
import th.ac.kmutt.chart.domain.CopyrightServiceEntityPK;
import th.ac.kmutt.chart.domain.FeatureEntity;
import th.ac.kmutt.chart.domain.FilterEntity;
import th.ac.kmutt.chart.domain.FilterInstanceEntity;
import th.ac.kmutt.chart.domain.FundingResourceServiceEntity;
import th.ac.kmutt.chart.domain.FundingResourceServiceEntityPK;
import th.ac.kmutt.chart.dwh.domain.InboundOutboundStudent;
import th.ac.kmutt.chart.domain.JournalPapersServiceEntity;
import th.ac.kmutt.chart.domain.JournalPapersServiceEntityPK;
import th.ac.kmutt.chart.domain.ServiceChartMappingEntity;
import th.ac.kmutt.chart.domain.ServiceChartMappingEntityPK;
import th.ac.kmutt.chart.domain.ServiceEntity;
import th.ac.kmutt.chart.domain.ServiceFilterMappingEntity;
import th.ac.kmutt.chart.domain.ServiceFilterMappingEntityPK;
import th.ac.kmutt.chart.fusion.model.FilterFusionM;
import th.ac.kmutt.chart.model.CopyrightServiceM;
import th.ac.kmutt.chart.model.FundingResourceServiceM;
import th.ac.kmutt.chart.model.InBoundOutBoundServiceM;
import th.ac.kmutt.chart.model.JournalPapersServiceM;

@Repository("chartRepository")
@Transactional
public class ChartRepository {
    private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
    @Autowired
    @PersistenceContext(unitName = "HibernatePersistenceUnit")
    private EntityManager entityManager;

    @Autowired
    @PersistenceContext(unitName = "HibernatePersistenceLiferayUnit")
    private EntityManager portalEntityManager;
    
    @Autowired
    @PersistenceContext(unitName = "HibernatePersistenceUnitDwh")
    private EntityManager entityManagerDwh;
    
    public List listServiceEntity(th.ac.kmutt.chart.model.ServiceM param) throws DataAccessException {
        StringBuffer sb = new StringBuffer(" select p from ServiceEntity p  where 1=1 ");
        String type = param.getType();

        if (type != null ) {
            sb.append(" and p.type='" + type + "' ");
        }
        Query query = entityManager.createQuery(sb.toString(), ServiceEntity.class);
        return query.getResultList();
    }
    public List listFilterEntity(th.ac.kmutt.chart.model.FilterM param)throws DataAccessException{
        StringBuffer sb = new StringBuffer(" select p from FilterEntity p  where 1=1 ");
        String type= param.getType();

        if (type != null ) {
            sb.append(" and p.type='" + type + "' ");
        }

        Query query = entityManager.createQuery(sb.toString(), FilterEntity.class);
        return query.getResultList();
    }
    public List listFilterInstanceEntity(th.ac.kmutt.chart.model.FilterInstanceM param)throws DataAccessException{
        StringBuffer sb = new StringBuffer(" select p from FilterInstanceEntity p  where 1=1 ");
        String instanceId= param.getInstanceId();

        if (instanceId != null ) {
            sb.append(" and p.id.instanceId='" + instanceId + "' ");
        }

        Query query = entityManager.createQuery(sb.toString(), FilterInstanceEntity.class);
        return query.getResultList();
    }

    public List listServiceFilterMappingEntity(th.ac.kmutt.chart.model.ServiceFilterMappingM param)throws DataAccessException{
        StringBuffer sb = new StringBuffer(" select p from ServiceFilterMappingEntity p  where 1=1 ");
        Integer serviceId = param.getServiceId();
        Integer filterId = param.getFilterId();

        if (serviceId != null ) {
            sb.append(" and p.id.serviceId=" + serviceId + " ");
        }
        if (filterId != null ) {
            sb.append(" and p.id.filterId=" + filterId + " ");
        }
        Query query = entityManager.createQuery(sb.toString(), ServiceFilterMappingEntity.class);
        return query.getResultList();
    }
    public List<ChartEntity> listChart() {
        // TODO Auto-generated method stub
        Query query=null;
        StringBuffer sb = new StringBuffer(" select p from  ChartEntity p  ");
         query = entityManager.createQuery(sb.toString(), ChartEntity.class);
        query.setFirstResult(0);
        query.setMaxResults(10);
        return query.getResultList();
    }
    public List<ChartEntity> aew() {
        // TODO Auto-generated method stub
        Query query=null;
        StringBuffer sb = new StringBuffer(" select p from  xxx p  ");
        query = entityManager.createQuery(sb.toString(), ChartEntity.class);
        query.setFirstResult(0);
        query.setMaxResults(10);
        return query.getResultList();
    }
    public List listCopyrightServiceEntity(CopyrightServiceM param) throws DataAccessException {
        StringBuffer sb = new StringBuffer(" where 1=1 ");
        FilterFusionM filterFusionM =param.getFilterFusionM();
        logger.info("param.getFilterFusionM()-->" +filterFusionM );
        if(filterFusionM!=null && filterFusionM.getFilters()!=null){
            String[] filters=filterFusionM.getFilters();
            for (int i=0;i<filters.length;i++){
                String[] filter_array=filters[i].split("_");
                if(filter_array!=null && filter_array.length>=2){
                    if(!filter_array[0].equals("-1")) {
                        logger.info(" index [" + i + "]" + filters[i]);
                        if (filter_array[0].equals("1")) { // year
                            sb.append(" and p.id.year<=" + filter_array[1] + "  ");
                        }
                    }
                }
            }
        }

        Integer type=param.getType();
        Integer year=param.getYear();
        Integer month=param.getMonth();
        if (type != null ) {
            sb.append(" and p.id.type=" + type.intValue() + "  ");
        }
        if (year != null ) {
            sb.append(" and p.id.year=" + year.intValue() + "  ");
        }
        if (month != null ) {
            sb.append(" and p.id.month=" + month.intValue() + "  ");
        }
        logger.info(entityManager+",query->"+sb.toString());
        Query query =null;
        if(param.getInstanceId()!=null && param.getInstanceId().trim().length()>0){
            StringBuffer sb_filter=new StringBuffer(" select p from ChartFilterInstanceEntity p where p.id.instanceId=:instanceId ");
            query =entityManager.createQuery(sb_filter.toString());
            query.setParameter("instanceId",param.getInstanceId());
            List<ChartFilterInstanceEntity> chartFilterInstanceEntities= query.getResultList();
            if(chartFilterInstanceEntities!=null && chartFilterInstanceEntities.size()>0){
                for (int i=0;i<chartFilterInstanceEntities.size();i++){
                    ChartFilterInstanceEntity chartFilterInstanceEntity=chartFilterInstanceEntities.get(i);
                    if(chartFilterInstanceEntity.getId().getFilterId()==2){ // เน�เธซเธฅเน�เธ�เธ—เธตเน�เน�เธ”เน�เธฃเธฑเธ�เธ�เธฒเธฃเน€เธ�เธขเน�เธ�เธฃเน�
                        sb.append(" and p.id.type=" + chartFilterInstanceEntity.getValue() + "  ");
                    }else  if(chartFilterInstanceEntity.getId().getFilterId()==3){ // เน�เธซเธฅเน�เธ�เน€เธ�เธดเธ�เธ—เธธเธ�
                        sb.append(" and p.id.type=" + chartFilterInstanceEntity.getValue() + "  ");
                    }


                }
            }
            logger.info(" chartFilterInstanceEntities size"+chartFilterInstanceEntities.size());
        }

        query = entityManager.createQuery(" select p from CopyrightServiceEntity p "
                + sb.toString()+" order by p.id.year ", CopyrightServiceEntity.class);
        return query.getResultList();
    }

    public List listJournalPapersServiceEntity(JournalPapersServiceM param) throws DataAccessException {
        StringBuffer sb = new StringBuffer(" where 1=1 ");
        FilterFusionM filterFusionM =param.getFilterFusionM();
        logger.info("param.getFilterFusionM()-->" +filterFusionM );
        if(filterFusionM!=null && filterFusionM.getFilters()!=null){
            String[] filters=filterFusionM.getFilters();
            for (int i=0;i<filters.length;i++){
                String[] filter_array=filters[i].split("_");
                if(filter_array!=null && filter_array.length>=2){
                    logger.info(" index ["+i+"]"+filters[i]);
                    if(!filter_array[0].equals("-1")) {
                        if (filter_array[0].equals("1")) { // year
                            sb.append(" and p.id.year<=" + filter_array[1] + "  ");
                        }
                    }
                }
            }
        }

        Integer type=param.getType();
        Integer year=param.getYear();
        if (type != null ) {
            sb.append(" and p.id.type=" + type.intValue() + "  ");
        }
        if (year != null ) {
            sb.append(" and p.id.year=" + year.intValue() + "  ");
        }
        Query query =null;
        System.out.println(sb.toString());
        if(param.getInstanceId()!=null && param.getInstanceId().trim().length()>0){
            StringBuffer sb_filter=new StringBuffer(" select p from ChartFilterInstanceEntity p where p.id.instanceId=:instanceId ");
             query =entityManager.createQuery(sb_filter.toString());
            query.setParameter("instanceId",param.getInstanceId());
            List<ChartFilterInstanceEntity> chartFilterInstanceEntities= query.getResultList();
            if(chartFilterInstanceEntities!=null && chartFilterInstanceEntities.size()>0){
                for (int i=0;i<chartFilterInstanceEntities.size();i++){
                    ChartFilterInstanceEntity chartFilterInstanceEntity=chartFilterInstanceEntities.get(i);
                    if(chartFilterInstanceEntity.getId().getFilterId()==2){ // เน�เธซเธฅเน�เธ�เธ—เธตเน�เน�เธ”เน�เธฃเธฑเธ�เธ�เธฒเธฃเน€เธ�เธขเน�เธ�เธฃเน�
                        sb.append(" and p.id.type=" + chartFilterInstanceEntity.getValue() + "  ");
                    }else  if(chartFilterInstanceEntity.getId().getFilterId()==3){ // เน�เธซเธฅเน�เธ�เน€เธ�เธดเธ�เธ—เธธเธ�
                        sb.append(" and p.id.type=" + chartFilterInstanceEntity.getValue() + "  ");
                    }


                }
            }
            logger.info(" chartFilterInstanceEntities size"+chartFilterInstanceEntities.size());
        }

         query = entityManager.createQuery(" select p from JournalPapersServiceEntity p "
                + sb.toString()+" order by p.id.year ", JournalPapersServiceEntity.class);

        return query.getResultList();
    }

    public List listFundingResourceServiceEntity(FundingResourceServiceM param) throws DataAccessException {
        StringBuffer sb = new StringBuffer(" where 1=1 ");
        FilterFusionM filterFusionM =param.getFilterFusionM();
        logger.info("param.getFilterFusionM()-->" +filterFusionM );
        if(filterFusionM!=null && filterFusionM.getFilters()!=null){
            String[] filters=filterFusionM.getFilters();
            for (int i=0;i<filters.length;i++){
                String[] filter_array=filters[i].split("_");
                if(filter_array!=null && filter_array.length>=2){
                    if(!filter_array[0].equals("-1")) {
                        logger.info(" index [" + i + "]" + filters[i]);
                        if (filter_array[0].equals("1")) { // year
                            sb.append(" and p.id.year<=" + filter_array[1] + "  ");
                        }
                    }
                }
            }
        }

        Integer type=param.getType();
        Integer year=param.getYear();
        if (type != null ) {
            sb.append(" and p.id.type=" + type.intValue() + "  ");
        }
        if (year != null ) {
            sb.append(" and p.id.year=" + year.intValue() + "  ");
        }
        Query query=null;
        if(param.getInstanceId()!=null && param.getInstanceId().trim().length()>0){
            StringBuffer sb_filter=new StringBuffer(" select p from ChartFilterInstanceEntity p where p.id.instanceId=:instanceId ");
            query =entityManager.createQuery(sb_filter.toString());
            query.setParameter("instanceId",param.getInstanceId());
            List<ChartFilterInstanceEntity> chartFilterInstanceEntities= query.getResultList();
            if(chartFilterInstanceEntities!=null && chartFilterInstanceEntities.size()>0){
                for (int i=0;i<chartFilterInstanceEntities.size();i++){
                    ChartFilterInstanceEntity chartFilterInstanceEntity=chartFilterInstanceEntities.get(i);
                    if(chartFilterInstanceEntity.getId().getFilterId()==2){ // เน�เธซเธฅเน�เธ�เธ—เธตเน�เน�เธ”เน�เธฃเธฑเธ�เธ�เธฒเธฃเน€เธ�เธขเน�เธ�เธฃเน�
                        sb.append(" and p.id.type=" + chartFilterInstanceEntity.getValue() + "  ");
                    }else  if(chartFilterInstanceEntity.getId().getFilterId()==3){ // เน�เธซเธฅเน�เธ�เน€เธ�เธดเธ�เธ—เธธเธ�
                        sb.append(" and p.id.type=" + chartFilterInstanceEntity.getValue() + "  ");
                    }


                }
            }
            logger.info(" chartFilterInstanceEntities size"+chartFilterInstanceEntities.size());
        }

         query = entityManager.createQuery(" select p from FundingResourceServiceEntity p "
                + sb.toString()+" order by p.id.year ", FundingResourceServiceEntity.class);

        return query.getResultList();
    }
    /*
    public Integer saveCopyrightServiceEntity(CopyrightServiceEntity transientInstance)
            throws DataAccessException {
        // TODO Auto-generated method stub
        entityManager.persist(transientInstance);
        return transientInstance.getType();
    }
*/

    //CHART
    public Integer saveChartEntity(ChartEntity transientInstance) throws DataAccessException{
        entityManager.persist(transientInstance);
        return transientInstance.getChartId();
    }
    public Integer updateChartEntity(ChartEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getChartId();
    }
    public Integer deleteChartEntity(ChartEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                String.format("delete from  ChartEntity where chartId=%d", persistentInstance.getChartId()))
                .executeUpdate();
        return deletedCount;
    }
    public ChartEntity findChartEntityById(Integer chartId) throws DataAccessException{
        return entityManager.find(ChartEntity.class, chartId);
    }

    public List listChartEntity(th.ac.kmutt.chart.model.ChartM param)throws DataAccessException{
        StringBuffer sb = new StringBuffer(" select p from ChartEntity p  where 1=1 ");

        String activeFlag = param.getActiveFlag();
        String chartType = param.getChartType();

        if (activeFlag != null ) {
            sb.append(" and p.activeFlag='" + activeFlag + "' ");
        }
        if (chartType != null ) {
            sb.append(" and p.chartType='" + chartType + "' ");
        }
        sb.append(" order by p.chartName ");
        Query query = entityManager.createQuery(sb.toString(), ChartEntity.class);
        return query.getResultList();
    }

    //CHART_FEATURE
    public Integer saveChartFeatureEntity(ChartFeatureEntity transientInstance) throws DataAccessException{
        entityManager.persist(transientInstance);
        return transientInstance.getChartId();
    }
    public Integer updateChartFeatureEntity(ChartFeatureEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getChartId();
    }
    public Integer deleteChartFeatureEntity(ChartFeatureEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().
                        append("delete from ChartFeatureEntity where researchGroupId=").append(persistentInstance.getChartId()).toString())
                .executeUpdate();
        return deletedCount;
    }
    public ChartFeatureEntity findChartFeatureEntityById(Integer chartId) throws DataAccessException{
        return entityManager.find(ChartFeatureEntity.class, chartId);
    }

    //CHART_FEATURE_INSTANCE
    public Integer saveChartFeatureInstanceEntity(ChartFeatureInstanceEntity transientInstance) throws DataAccessException{
        entityManager.persist(transientInstance);
        return transientInstance.getChartId();
    }
    public Integer updateChartFeatureInstanceEntity(ChartFeatureInstanceEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getChartId();
    }
    public Integer deleteChartFeatureInstanceEntity(ChartFeatureInstanceEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from ChartFeatureInstanceEntity where instanceId=").
                        append(persistentInstance.getInstanceId()).toString())
                .executeUpdate();
        return deletedCount;
    }
    public ChartFeatureInstanceEntity findChartFeatureInstanceEntityById(String instanceId) throws DataAccessException{
        return entityManager.find(ChartFeatureInstanceEntity.class, instanceId);
    }

    //CHART_FEATURE_MAPPIG
    public Integer saveChartFeatureMappingEntity(ChartFeatureMappingEntity transientInstance) throws DataAccessException{
        entityManager.persist(transientInstance);
        return transientInstance.getId().getChartId();
    }
    public Integer updateChartFeatureMappingEntity(ChartFeatureMappingEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getId().getChartId();
    }
    public Integer deleteChartFeatureMappingEntity(ChartFeatureMappingEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from ChartFeatureMappingEntity where id=:id").toString())
                .setParameter("id", persistentInstance.getId())
                .executeUpdate();
        return deletedCount;
    }
    public ChartFeatureMappingEntity findChartFeatureMappingEntityById(ChartFeatureMappingEntityPK id) throws DataAccessException{
        return entityManager.find(ChartFeatureMappingEntity.class, id);
    }

    //CHART_FILTER_INSTANCE
    public Integer saveChartFilterInstanceEntity(ChartFilterInstanceEntity transientInstance) throws DataAccessException {
        entityManager.persist(transientInstance);
        return transientInstance.getId().getFilterId();
    }
    public Integer updateChartFilterInstanceEntity(ChartFilterInstanceEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getId().getFilterId();
    }
    public Integer deleteChartFilterInstanceEntity(ChartFilterInstanceEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from ChartFilterInstanceEntity where id.instanceId=:instanceId ").toString())
                .setParameter("instanceId",persistentInstance.getId().getInstanceId())
                .executeUpdate();
        return deletedCount;

    }
    public ChartFilterInstanceEntity findChartFilterInstanceEntityById(String instanceId) throws DataAccessException{
        return entityManager.find(ChartFilterInstanceEntity.class, instanceId);
    }

    //CHART_INSTANCE
    public Integer saveChartInstanceEntity(ChartInstanceEntity transientInstance) throws DataAccessException{
        if(transientInstance.getCommentByInstanceId()!=null) {
            logger.info("transientInstance getCommentByInstanceId id->" + transientInstance.getCommentByInstanceId().getInstanceId()+"x");
            logger.info("transientInstance getCommentByInstanceId comment->" + transientInstance.getCommentByInstanceId().getComment()+"x");
        }
        entityManager.persist(transientInstance);
        if(transientInstance.getCommentByInstanceId()!=null)
            entityManager.persist(transientInstance.getCommentByInstanceId());
        return 1;
    }
    public Integer updateChartInstanceEntity(ChartInstanceEntity transientInstance) throws DataAccessException{
        logger.info("transientInstance getInstanceId->"+transientInstance.getInstanceId());

        logger.info("transientInstance getServiceId->"+transientInstance.getServiceByServiceId());
        if(transientInstance.getCommentByInstanceId()!=null && transientInstance.getCommentByInstanceId().getInstanceId()!=null
                && transientInstance.getCommentByInstanceId().getInstanceId().trim().length()>0)
        logger.info("transientInstance getCommentByInstanceId->"+transientInstance.getCommentByInstanceId().getComment());
        entityManager.merge(transientInstance);
        if(transientInstance.getCommentByInstanceId()!=null && transientInstance.getCommentByInstanceId().getInstanceId()!=null
                && transientInstance.getCommentByInstanceId().getInstanceId().trim().length()>0)
            entityManager.merge(transientInstance.getCommentByInstanceId());
        return 1;
    }
    public Integer deleteChartInstanceEntity(ChartInstanceEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from ChartInstanceEntity where instanceId=:instanceId").toString())
                .setParameter("instanceId",persistentInstance.getInstanceId())
                .executeUpdate();
        return deletedCount;
    }
    public ChartInstanceEntity findChartInstanceEntityById(String instanceId) throws DataAccessException{
        Query query=null;
        StringBuffer sb=new StringBuffer("select p from ChartInstanceEntity p where p.instanceId=:instanceId ");

        query = entityManager.createQuery(sb.toString(), ChartInstanceEntity.class);
        query.setParameter("instanceId",instanceId);
        ChartInstanceEntity result_return=null;
        List resultList=query.getResultList();
        if(resultList!=null && resultList.size()>0)
            result_return=(ChartInstanceEntity)resultList.get(0);
        return result_return;
      //  return entityManager.find(ChartInstanceEntity.class, instanceId);
    }
    public List listChartFilterInstanceEntity(th.ac.kmutt.chart.model.ChartFilterInstanceM param)throws DataAccessException {
        StringBuffer sb = new StringBuffer(" select p from ChartFilterInstanceEntity p  where 1=1 ");
        Integer serviceId = param.getServiceId();
        Integer filterId = param.getFilterId();
        String instanceId= param.getInstanceId();
        if (instanceId != null) {
            sb.append(" and p.id.instanceId='" + instanceId + "' ");
        }
        if (serviceId != null) {
            sb.append(" and p.serviceId=" + serviceId + " ");
        }
        if (filterId != null) {
            sb.append(" and p.id.filterId=" + filterId + " ");
        }
        Query query = entityManager.createQuery(sb.toString(), ChartFilterInstanceEntity.class);
        return query.getResultList();
    }

    //COMMENT
    public Integer saveCommentEntity(CommentEntity transientInstance) throws DataAccessException{
        entityManager.persist(transientInstance);
        return 1;
    }
    public Integer updateCommentEntity(CommentEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return 1;
    }
    public Integer deleteCommentEntity(CommentEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from CommentEntity where instanceId=:instanceId").toString())
                .setParameter("instanceId",persistentInstance.getInstanceId())
                .executeUpdate();
        return deletedCount;
    }
    public CommentEntity findCommentEntityById(String instanceId) throws DataAccessException{
        return entityManager.find(CommentEntity.class, instanceId);
    }

    //COPYRIGHT_SERVICE
    public Integer saveCopyrightServiceEntity(CopyrightServiceEntity transientInstance) throws DataAccessException{
        entityManager.persist(transientInstance);
        return transientInstance.getId().getType();
    }
    public Integer updateCopyrightServiceEntity(CopyrightServiceEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getId().getType();
    }
    public Integer deleteCopyrightServiceEntity(CopyrightServiceEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from CopyrightServiceEntity where id=:id").toString())
                .setParameter("id", persistentInstance.getId())
                .executeUpdate();
        return deletedCount;
    }
    public CopyrightServiceEntity findCopyrightServiceEntityById(CopyrightServiceEntityPK id) throws DataAccessException{
        return entityManager.find(CopyrightServiceEntity.class, id);
    }

    //FEATURE
    public Integer saveFeatureEntity(FeatureEntity transientInstance) throws DataAccessException {
        entityManager.persist(transientInstance);
        return transientInstance.getFeatureId();
    }
    public Integer updateFeatureEntity(FeatureEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getFeatureId();
    }
    public Integer deleteFeatureEntity(FeatureEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from FeatureEntity where featureId=:featureId").toString())
                .setParameter("featureId",persistentInstance.getFeatureId())
                .executeUpdate();
        return deletedCount;
    }
    public FeatureEntity findFeatureEntityById(Integer featureId) throws DataAccessException{
        return entityManager.find(FeatureEntity.class, featureId);
    }

    //FILTER
    public Integer saveFilterEntity(FilterEntity transientInstance) throws DataAccessException {
        entityManager.persist(transientInstance);
        return transientInstance.getFilterId();
    }
    public Integer updateFilterEntity(FilterEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getFilterId();
    }
    public Integer deleteFilterEntity(FilterEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from FilterEntity where filterId=:filterId").toString())
                .setParameter("filterId",persistentInstance.getFilterId())
                .executeUpdate();
        return deletedCount;
    }
    public FilterEntity findFilterEntityById(Integer filterId) throws DataAccessException{
        return entityManager.find(FilterEntity.class, filterId);
    }

    //FILTER_INSTANCE
    public Integer saveFilterInstanceEntity(FilterInstanceEntity transientInstance) throws DataAccessException {
        entityManager.persist(transientInstance);
        return transientInstance.getId().getFilterId();
    }
    public Integer updateFilterInstanceEntity(FilterInstanceEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getId().getFilterId();
    }
    public Integer deleteFilterInstanceEntity(FilterInstanceEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from FilterInstanceEntity where id.instanceId=:instanceId").toString())
                .setParameter("instanceId",persistentInstance.getId().getInstanceId())
                .executeUpdate();
        return deletedCount;
    }
    public FilterInstanceEntity findFilterInstanceEntityById(String instanceId) throws DataAccessException{
        return entityManager.find(FilterInstanceEntity.class, instanceId);
    }

    //FUNDING_RESOURCE_SERVICE
    public Integer saveFundingResourceServiceEntity(FundingResourceServiceEntity transientInstance) throws DataAccessException{
        entityManager.persist(transientInstance);
        return transientInstance.getId().getType();
    }
    public Integer updateFundingResourceServiceEntity(FundingResourceServiceEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getId().getType();
    }
    public Integer deleteFundingResourceServiceEntity(FundingResourceServiceEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from FundingResourceServiceEntity where id=:id").toString())
                .setParameter("id",persistentInstance.getId())
                .executeUpdate();
        return deletedCount;
    }
    public FundingResourceServiceEntity findFundingResourceServiceEntityById(FundingResourceServiceEntityPK id) throws DataAccessException{
        return entityManager.find(FundingResourceServiceEntity.class, id);
    }

    //JOURNAL_PAPERS_SERVICE
    public Integer saveJournalPapersServiceEntity(JournalPapersServiceEntity transientInstance) throws DataAccessException{
        entityManager.persist(transientInstance);
        return transientInstance.getId().getType();
    }
    public Integer updateJournalPapersServiceEntity(JournalPapersServiceEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getId().getType();
    }
    public Integer deleteJournalPapersServiceEntity(JournalPapersServiceEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from JournalPapersServiceEntity where id=:id").toString())
                .setParameter("id",persistentInstance.getId())
                .executeUpdate();
        return deletedCount;
    }
    public JournalPapersServiceEntity findJournalPapersServiceEntityById(JournalPapersServiceEntityPK id) throws DataAccessException{
        return entityManager.find(JournalPapersServiceEntity.class, id);
    }

    //SERVICE_CHART_MAPPING
    public Integer saveServiceChartMappingEntity(ServiceChartMappingEntity transientInstance) throws DataAccessException{
        entityManager.persist(transientInstance);
        return transientInstance.getId().getChartId();
    }
    public Integer updateServiceChartMappingEntity(ServiceChartMappingEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getId().getChartId();
    }
    public Integer deleteServiceChartMappingEntity(ServiceChartMappingEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from ServiceChartMappingEntity where id=:id").toString())
                .setParameter("id",persistentInstance.getId())
                .executeUpdate();
        return deletedCount;
    }
    public ServiceChartMappingEntity findServiceChartMappingEntityById(ServiceChartMappingEntityPK id) throws DataAccessException{
        return entityManager.find(ServiceChartMappingEntity.class, id);
    }

    //SERVICE
    public Integer saveServiceEntity(ServiceEntity transientInstance) throws DataAccessException {
        entityManager.persist(transientInstance);
        return transientInstance.getServiceId();
    }
    public Integer updateServiceEntity(ServiceEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getServiceId();
    }
    public Integer deleteServiceEntity(ServiceEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from ServiceEntity where serviceId=:serviceId").toString())
                .setParameter("serviceId",persistentInstance.getServiceId())
                .executeUpdate();
        return deletedCount;
    }
    public ServiceEntity findServiceEntityById(Integer serviceId) throws DataAccessException{
        return entityManager.find(ServiceEntity.class, serviceId);
    }

    //SERVICE
    public Integer saveServiceFilterMappingEntity(ServiceFilterMappingEntity transientInstance) throws DataAccessException {
        entityManager.persist(transientInstance);
        return transientInstance.getId().getFilterId();
    }
    public Integer updateServiceFilterMappingEntity(ServiceFilterMappingEntity transientInstance) throws DataAccessException{
        entityManager.merge(transientInstance);
        return transientInstance.getId().getFilterId();
    }
    public Integer deleteServiceFilterMappingEntity(ServiceFilterMappingEntity persistentInstance) throws DataAccessException{
        int deletedCount = 0;
        deletedCount = entityManager.createQuery(
                new StringBuilder().append("delete from ServiceFilterMappingEntity where id=:id").toString())
                .setParameter("id",persistentInstance.getId())
                .executeUpdate();
        return deletedCount;
    }
    public ServiceFilterMappingEntity findServiceFilterMappingEntityById(ServiceFilterMappingEntityPK id) throws DataAccessException{
        return entityManager.find(ServiceFilterMappingEntity.class, id);
    }

    
    /*INBOUND_OUTBOUND_STUDENT*/
	
public List InternationalCompareAllStudent(InBoundOutBoundServiceM param) throws DataAccessException {
	Query query = entityManagerDwh.createNativeQuery(		

			"SELECT ROUND(DECIMAL(DECIMAL(INB.NOOFSTUDENT)/AL.NO_OF_STUDENT *100,10,2),2) AS NoOf \n"
				+"FROM ( SELECT ACADEMIC_YEAR, SUM(FACT_INBOUND_OUTBOUND_STUDENT.NO_OF_STUDENT) AS NOOFSTUDENT\n"
						 +"FROM FACT_INBOUND_OUTBOUND_STUDENT\n"
						 +"INNER JOIN DIM_FIELD ON  FACT_INBOUND_OUTBOUND_STUDENT.FIELD_KEY = DIM_FIELD.FIELD_KEY\n"
						+"WHERE IN_OUT_TYPE_KEY = '2'\n"
						+"AND NATIONALITY <> 'Thai'\n"
						+"AND ACADEMIC_YEAR = :paramYear \n"
						+"AND( DIM_FIELD.FACULTY_CODE = :paramFaculty OR 'ALL' = :paramFaculty)\n"
						+"AND( DIM_FIELD.DEPARTMENT_CODE = :paramDepartment OR 'ALL' = :paramDepartment) \n"
						+"GROUP BY ACADEMIC_YEAR\n"
						+")INB\n"
				+"INNER JOIN (SELECT DS.ACADEMIC_YEAR,\n"
								+"SUM(NO_OF_STUDENT) AS NO_OF_STUDENT\n"
								+"FROM FACT_ALL_STUDENT FAS\n"
								+"INNER JOIN DIM_SEMESTER DS ON  FAS.SEMESTER_KEY = DS.SEMESTER_KEY\n"
								+"INNER JOIN DIM_FIELD DF ON FAS.FIELD_KEY =DF.FIELD_KEY\n"
								+"WHERE DS.ACADEMIC_YEAR = :paramYear \n"
								+"AND (DF.FACULTY_CODE = :paramFaculty OR 'ALL' =:paramFaculty)\n"
								+"AND (DF.DEPARTMENT_CODE = :paramDepartment OR 'ALL' = :paramDepartment)\n"
								+"AND LEFT(DS.SEMESTER,1) = '1'\n"
								+"GROUP BY ACADEMIC_YEAR )AL on INB.ACADEMIC_YEAR = AL.ACADEMIC_YEAR\n"


			);
	query.setParameter("paramYear", param.getAcademicYear());
	query.setParameter("paramFaculty", param.getFacultyCode());
	query.setParameter("paramDepartment",param.getDepartmentCode());
	List<BigDecimal> results = query.getResultList();
	 
	List<InboundOutboundStudent> inboundOutboundStudents = new ArrayList<InboundOutboundStudent>();
	for (BigDecimal result : results) {	
		InboundOutboundStudent inboundOutboundStudent = new InboundOutboundStudent();
		inboundOutboundStudent.setNoOf((BigDecimal) result);

		
		inboundOutboundStudents.add(inboundOutboundStudent);
		
	}
	 		 
	 return inboundOutboundStudents;
}


public List EmpInternationalCompareAllEmp(InBoundOutBoundServiceM param) throws DataAccessException {
	Query query = entityManagerDwh.createNativeQuery(		

			"SELECT ROUND(DECIMAL(DECIMAL(HR.NOOFEMPIN)/INB.NOOFEMP *100,10,1),1) AS NoOf\n"
			+"FROM ( SELECT DD.ACADAMIC_YEAR, count(HR_FACT_EMPLOYEE.emp_key) AS NOOFEMP\n"
								+"FROM HR_FACT_EMPLOYEE \n"
								+"INNER JOIN DIM_DATE DD ON  HR_FACT_EMPLOYEE.MONTH_KEY = DD.DATE_KEY\n"
								+"INNER JOIN HR_DIM_DEPARTMENT ON HR_FACT_EMPLOYEE.DEPARTMENT_KEY = HR_DIM_DEPARTMENT.DEPARTMENT_KEY\n"
								+"WHERE  HR_FACT_EMPLOYEE.MONTH_KEY = (SELECT Max (HFE.MONTH_KEY)\n"
																		+"FROM HR_FACT_EMPLOYEE HFE \n"
																		+"INNER JOIN DIM_DATE DD ON  HFE.MONTH_KEY = DD.DATE_KEY\n"
																		+"WHERE DD.CALENDAR_YEAR = :paramYear\n"  
																		+")\n"
                                +"AND (HR_DIM_DEPARTMENT.ORGENIZATION_CODE = :paramFaculty OR 'ALL' = :paramFaculty)\n"
                                +"AND (HR_DIM_DEPARTMENT.DEPARTMENT_CODE = :paramDepartment OR 'ALL' = :paramDepartment)\n"
                                +"group by DD.ACADAMIC_YEAR )INB\n"
				+"INNER JOIN (SELECT DD.ACADAMIC_YEAR, count(HR_FACT_EMPLOYEE.emp_key) AS NOOFEMPIN\n"
								+"FROM HR_FACT_EMPLOYEE \n"
								+"INNER JOIN DIM_DATE DD ON  HR_FACT_EMPLOYEE.MONTH_KEY = DD.DATE_KEY\n"
								+"INNER JOIN HR_DIM_DEPARTMENT ON HR_FACT_EMPLOYEE.DEPARTMENT_KEY = HR_DIM_DEPARTMENT.DEPARTMENT_KEY\n"
								+"WHERE  HR_FACT_EMPLOYEE.MONTH_KEY = (SELECT Max (HFE.MONTH_KEY)\n"
																		+"FROM HR_FACT_EMPLOYEE HFE \n"
																		+"INNER JOIN DIM_DATE DD ON  HFE.MONTH_KEY = DD.DATE_KEY\n"
																		+"WHERE DD.CALENDAR_YEAR = :paramYear\n" 
																		+")\n"
								+"AND HR_FACT_EMPLOYEE.NATIONALITY_KEY <> 100\n"
                                +"AND (HR_DIM_DEPARTMENT.ORGENIZATION_CODE = :paramFaculty OR 'ALL' = :paramFaculty)\n"
                                +"AND (HR_DIM_DEPARTMENT.DEPARTMENT_CODE = :paramDepartment OR 'ALL' = :paramDepartment)\n"
								+"group by DD.ACADAMIC_YEAR )HR on INB.ACADAMIC_YEAR = HR.ACADAMIC_YEAR\n"
			);
	query.setParameter("paramYear", param.getAcademicYear());
	query.setParameter("paramFaculty", param.getFacultyCode());
	query.setParameter("paramDepartment",param.getDepartmentCode());
	List<BigDecimal> results = query.getResultList();
	 
	List<InboundOutboundStudent> inboundOutboundStudents = new ArrayList<InboundOutboundStudent>();
	for (BigDecimal result : results) {	
		InboundOutboundStudent inboundOutboundStudent = new InboundOutboundStudent();
		inboundOutboundStudent.setNoOf((BigDecimal) result);
		
		inboundOutboundStudents.add(inboundOutboundStudent);
		
	}
	 		 
	 return inboundOutboundStudents;
}


public List InternationalCompareAllStudentProgramInter(InBoundOutBoundServiceM param) throws DataAccessException {
	Query query = entityManagerDwh.createNativeQuery(		

			"SELECT ROUND(DECIMAL(DECIMAL(INB.NO_OF_INTER)/AL.NO_OF_ALL *100,10,2),2) AS NoOf \n"
				+"FROM ( SELECT ACADEMIC_YEAR, SUM(FACT_INBOUND_OUTBOUND_STUDENT.NO_OF_STUDENT) AS NO_OF_INTER\n"
						+" FROM FACT_INBOUND_OUTBOUND_STUDENT\n"
						 +"INNER JOIN DIM_FIELD ON  FACT_INBOUND_OUTBOUND_STUDENT.FIELD_KEY = DIM_FIELD.FIELD_KEY\n"
                         +"WHERE IN_OUT_TYPE_KEY = '2'\n"
						 +"AND NATIONALITY <> 'Thai'\n"
						 +"AND ACADEMIC_YEAR = :paramYear \n"
						 +"AND( DIM_FIELD.FACULTY_CODE = :paramFaculty  OR 'ALL' = :paramFaculty )\n"
						 +"AND( DIM_FIELD.DEPARTMENT_CODE = :paramDepartment OR 'ALL' = :paramDepartment) \n"
						 +"GROUP BY ACADEMIC_YEAR\n"
						 +")INB\n"
				+"INNER JOIN (SELECT DS.ACADEMIC_YEAR,\n"
								+"SUM(FAS.NO_OF_STUDENT) AS NO_OF_ALL\n"
								+"FROM FACT_ALL_STUDENT FAS\n"
								+"INNER JOIN DIM_SEMESTER DS ON  FAS.SEMESTER_KEY = DS.SEMESTER_KEY\n"
								+"INNER JOIN DIM_FIELD DF ON FAS.FIELD_KEY =DF.FIELD_KEY\n"
                                +"INNER JOIN DIM_PROGRAM DP ON FAS.PROGRAM_KEY = DP.PROGRAM_KEY\n"
								+"WHERE DS.ACADEMIC_YEAR = :paramYear \n"
								+"AND (DF.FACULTY_CODE = :paramFaculty  OR 'ALL' = :paramFaculty )\n"
								+"AND (DF.DEPARTMENT_CODE = :paramDepartment  OR 'ALL' = :paramDepartment)\n"
								+"AND LEFT(DS.SEMESTER,1) = '1'\n"
                                +"AND DP.ARRANGE_TYPE = 'หลักสูตรนานาชาติ'\n"
								+"GROUP BY ACADEMIC_YEAR\n"
								+")AL on INB.ACADEMIC_YEAR = AL.ACADEMIC_YEAR\n"
								) ;
	query.setParameter("paramYear", param.getAcademicYear());
	query.setParameter("paramFaculty", param.getFacultyCode());
	query.setParameter("paramDepartment",param.getDepartmentCode());
	List<BigDecimal> results = query.getResultList();
	 
	List<InboundOutboundStudent> inboundOutboundStudents = new ArrayList<InboundOutboundStudent>();
	for (BigDecimal result : results) {	
		InboundOutboundStudent inboundOutboundStudent = new InboundOutboundStudent();
		inboundOutboundStudent.setNoOf((BigDecimal) result);
		
		inboundOutboundStudents.add(inboundOutboundStudent);
		
	}
	 		 
	 return inboundOutboundStudents;
}


public List ProgramInternationalCompareAllProgram(InBoundOutBoundServiceM param) throws DataAccessException {
	Query query = entityManagerDwh.createNativeQuery(		

			"SELECT ROUND(DECIMAL(DECIMAL(INB.NO_OF_INTER)/AL.NO_OF_ALL *100,10,2),2) AS NoOf\n"
					+"FROM (SELECT DS.ACADEMIC_YEAR,\n"
							+"COUNT(dISTINCT DP.PROGRAM_CODE) AS NO_OF_INTER\n"
							+"FROM FACT_ALL_STUDENT FAS\n"
							+"INNER JOIN DIM_SEMESTER DS ON  FAS.SEMESTER_KEY = DS.SEMESTER_KEY\n"
							+"INNER JOIN DIM_FIELD DF ON FAS.FIELD_KEY =DF.FIELD_KEY\n"
							+"INNER JOIN DIM_PROGRAM DP ON FAS.PROGRAM_KEY = DP.PROGRAM_KEY\n"
							+"WHERE DS.ACADEMIC_YEAR = :paramYear \n"
							+"AND (DF.FACULTY_CODE = :paramFaculty OR 'ALL' = :paramFaculty)\n"
							+"AND (DF.DEPARTMENT_CODE = :paramDepartment OR 'ALL' = :paramDepartment)\n"
							+"AND LEFT(DS.SEMESTER,1) = '1'\n"
							+"AND DP.ARRANGE_TYPE = 'หลักสูตรนานาชาติ'\n"
							+"GROUP BY ACADEMIC_YEAR \n"
							+")INB\n"
					+"INNER JOIN (SELECT DS.ACADEMIC_YEAR,\n"
								+"COUNT(dISTINCT DP.PROGRAM_CODE) AS NO_OF_ALL\n"
								+"FROM FACT_ALL_STUDENT FAS\n"
								+"INNER JOIN DIM_SEMESTER DS ON  FAS.SEMESTER_KEY = DS.SEMESTER_KEY\n"
								+"INNER JOIN DIM_FIELD DF ON FAS.FIELD_KEY =DF.FIELD_KEY\n"
								+"INNER JOIN DIM_PROGRAM DP ON FAS.PROGRAM_KEY = DP.PROGRAM_KEY\n"
								+"WHERE DS.ACADEMIC_YEAR = :paramYear \n"
								+"AND (DF.FACULTY_CODE = :paramFaculty OR 'ALL' = :paramFaculty)\n"
								+"AND (DF.DEPARTMENT_CODE = :paramDepartment OR 'ALL' = :paramDepartment)\n"
								+"AND LEFT(DS.SEMESTER,1) = '1'\n"
								+"GROUP BY ACADEMIC_YEAR )AL on INB.ACADEMIC_YEAR = AL.ACADEMIC_YEAR\n"
			);
	query.setParameter("paramYear", param.getAcademicYear());
	query.setParameter("paramFaculty", param.getFacultyCode());
	query.setParameter("paramDepartment",param.getDepartmentCode());
	List<BigDecimal> results = query.getResultList();
	 
	List<InboundOutboundStudent> inboundOutboundStudents = new ArrayList<InboundOutboundStudent>();
	for (BigDecimal result : results) {	
		InboundOutboundStudent inboundOutboundStudent = new InboundOutboundStudent();
		inboundOutboundStudent.setNoOf((BigDecimal) result);
		
		inboundOutboundStudents.add(inboundOutboundStudent);
		
	}
	 		 
	 return inboundOutboundStudents;
}

public List InternationalCompareAllStudentByFaculty(InBoundOutBoundServiceM param) throws DataAccessException {
	Query query = entityManagerDwh.createNativeQuery(		

			"SELECT INB.FACULTY_NAME_INITIAL,INB.NO_OF_INTER,AL.NO_OF_ALL\n"
			+"FROM ( SELECT ACADEMIC_YEAR,\n"
                         +"DIM_FACULTY.FACULTY_NAME_INITIAL, \n"
                         +"SUM(FACT_INBOUND_OUTBOUND_STUDENT.NO_OF_STUDENT) AS NO_OF_INTER\n"
                         +"FROM FACT_INBOUND_OUTBOUND_STUDENT\n"
                         +"INNER JOIN DIM_FACULTY ON  FACT_INBOUND_OUTBOUND_STUDENT.FACULTY_KEY = DIM_FACULTY.FACULTY_KEY\n"
                         +"INNER JOIN DIM_FIELD ON  FACT_INBOUND_OUTBOUND_STUDENT.FIELD_KEY = DIM_FIELD.FIELD_KEY\n"
                         +"WHERE IN_OUT_TYPE_KEY = '2'\n"
                         +"AND NATIONALITY <> 'Thai'\n"
                         +"AND ACADEMIC_YEAR = :paramYear \n"
                         +"AND( DIM_FIELD.FACULTY_CODE = :paramFaculty  OR 'ALL' = :paramFaculty)\n"
                         +"AND( DIM_FIELD.DEPARTMENT_CODE = :paramDepartment  OR 'ALL' = :paramDepartment) \n"
                         +"GROUP BY ACADEMIC_YEAR, DIM_FACULTY.FACULTY_NAME_INITIAL \n"
                         +")INB\n"
          +"INNER JOIN (SELECT DS.ACADEMIC_YEAR,DC.FACULTY_NAME_INITIAL,\n"
          				+"SUM(FAS.NO_OF_STUDENT) AS NO_OF_ALL\n"
          				+"FROM FACT_ALL_STUDENT FAS\n"
          				+"INNER JOIN DIM_SEMESTER DS ON  FAS.SEMESTER_KEY = DS.SEMESTER_KEY\n"
          				+"INNER JOIN DIM_FIELD DF ON FAS.FIELD_KEY =DF.FIELD_KEY\n"
          				+"INNER JOIN DIM_FACULTY DC ON DF.FACULTY_CODE = DC.FACULTY_CODE\n"
          				+"WHERE DS.ACADEMIC_YEAR = :paramYear\n" 
          				+"AND (DF.FACULTY_CODE = :paramFaculty  OR 'ALL' = :paramFaculty)\n"
          				+"AND (DF.DEPARTMENT_CODE = :paramDepartment  OR 'ALL' = :paramDepartment)\n"
          				+"AND LEFT(DS.SEMESTER,1) = '1'  \n"
          				+"GROUP BY DS.ACADEMIC_YEAR,DC.FACULTY_NAME_INITIAL\n"
          				+")AL on INB.ACADEMIC_YEAR = AL.ACADEMIC_YEAR AND AL.FACULTY_NAME_INITIAL = INB.FACULTY_NAME_INITIAL\n"
					) ;
	query.setParameter("paramYear", param.getAcademicYear());
	query.setParameter("paramFaculty", param.getFacultyCode());
	query.setParameter("paramDepartment",param.getDepartmentCode());
	List<Object[]> results = query.getResultList();
	 
	List<InBoundOutBoundServiceM> inboundOutboundStudents = new ArrayList<InBoundOutBoundServiceM>();
	for (Object[] result : results) {	
		InBoundOutBoundServiceM inBoundOutBoundServiceM = new InBoundOutBoundServiceM();
		inBoundOutBoundServiceM.setShortFaculty((String) result[0]);
		inBoundOutBoundServiceM.setnoOfInter((Integer) result[1]);
		inBoundOutBoundServiceM.setnoOfAll((Integer) result[2]);
		
		inBoundOutBoundServiceM.setPaging(null);
		inboundOutboundStudents.add(inBoundOutBoundServiceM);
		
	}
	 return inboundOutboundStudents;
}


public List InternationalCompareAllEmpByFaculty(InBoundOutBoundServiceM param) throws DataAccessException {
	Query query = entityManagerDwh.createNativeQuery(		

			"SELECT INB.FACULTY_NAME_INITIAL as ShortFaculty,INB.noOfInter,AL.noOfAll \n"
				+"FROM ( SELECT DD.ACADAMIC_YEAR, \n"
				+"CASE \n"
				+" WHEN DIM_FACULTY.FACULTY_NAME_INITIAL IS NULL \n"
				+"THEN 'KMUTT'\n"
				+" ELSE DIM_FACULTY.FACULTY_NAME_INITIAL END FACULTY_NAME_INITIAL ,\n"
				+" count(HR_FACT_EMPLOYEE.emp_key) AS noOfInter\n"
				+"FROM HR_FACT_EMPLOYEE \n"
				+"INNER JOIN DIM_DATE DD ON  HR_FACT_EMPLOYEE.MONTH_KEY = DD.DATE_KEY\n"
				+"INNER JOIN HR_DIM_DEPARTMENT ON HR_FACT_EMPLOYEE.DEPARTMENT_KEY = HR_DIM_DEPARTMENT.DEPARTMENT_KEY\n"
								+"LEFT JOIN DIM_FACULTY ON  HR_DIM_DEPARTMENT.ORGENIZATION_CODE = DIM_FACULTY.FACULTY_CODE\n"
								+"WHERE  HR_FACT_EMPLOYEE.MONTH_KEY = (SELECT Max (HFE.MONTH_KEY)\n"
																		+"FROM HR_FACT_EMPLOYEE HFE \n"
																		+"INNER JOIN DIM_DATE DD ON  HFE.MONTH_KEY = DD.DATE_KEY\n"
																		+"WHERE DD.CALENDAR_YEAR = :paramYear \n"
																		+")\n"
								+"AND (HR_DIM_DEPARTMENT.ORGENIZATION_CODE =  :paramFaculty OR 'ALL' =  :paramFaculty)\n"
								+"AND (HR_DIM_DEPARTMENT.DEPARTMENT_CODE =  :paramDepartment OR 'ALL' =  :paramDepartment )\n"
								+"AND  HR_FACT_EMPLOYEE.NATIONALITY_KEY <> 100\n"
								+"group by DD.ACADAMIC_YEAR,DIM_FACULTY.FACULTY_NAME_INITIAL )INB\n"
				+"INNER JOIN (SELECT DD.ACADAMIC_YEAR, \n"
								+"CASE \n"
								+"WHEN DIM_FACULTY.FACULTY_NAME_INITIAL IS NULL\n" 
								+"THEN 'KMUTT'\n"
								+"ELSE DIM_FACULTY.FACULTY_NAME_INITIAL END AS FACULTY_NAME_INITIAL ,\n"
								+"count(HR_FACT_EMPLOYEE.emp_key) AS noOfAll\n"
								+"FROM HR_FACT_EMPLOYEE \n"
								+"INNER JOIN DIM_DATE DD ON  HR_FACT_EMPLOYEE.MONTH_KEY = DD.DATE_KEY\n"
								+"INNER JOIN HR_DIM_DEPARTMENT ON HR_FACT_EMPLOYEE.DEPARTMENT_KEY = HR_DIM_DEPARTMENT.DEPARTMENT_KEY\n"
								+"LEFT JOIN DIM_FACULTY ON  HR_DIM_DEPARTMENT.ORGENIZATION_CODE = DIM_FACULTY.FACULTY_CODE\n"
								+"WHERE  HR_FACT_EMPLOYEE.MONTH_KEY = (SELECT Max (HFE.MONTH_KEY)\n"
																		+"FROM HR_FACT_EMPLOYEE HFE \n"
																		+"INNER JOIN DIM_DATE DD ON  HFE.MONTH_KEY = DD.DATE_KEY\n"
																		+"WHERE DD.CALENDAR_YEAR = :paramYear \n"
																		+")\n"
								+"AND (HR_DIM_DEPARTMENT.ORGENIZATION_CODE =  :paramFaculty OR 'ALL' =  :paramFaculty )\n"
								+"AND (HR_DIM_DEPARTMENT.DEPARTMENT_CODE =  :paramDepartment OR 'ALL' =  :paramDepartment )\n"
								+"group by DD.ACADAMIC_YEAR,DIM_FACULTY.FACULTY_NAME_INITIAL )AL on INB.ACADAMIC_YEAR = AL.ACADAMIC_YEAR  AND  INB.FACULTY_NAME_INITIAL = AL.FACULTY_NAME_INITIAL \n"
					) ;
	query.setParameter("paramYear", param.getAcademicYear());
	query.setParameter("paramFaculty", param.getFacultyCode());
	query.setParameter("paramDepartment",param.getDepartmentCode());
	List<Object[]> results = query.getResultList();
	 
	List<InBoundOutBoundServiceM> inboundOutboundStudents = new ArrayList<InBoundOutBoundServiceM>();
	for (Object[] result : results) {	
		InBoundOutBoundServiceM inBoundOutBoundServiceM = new InBoundOutBoundServiceM();
		inBoundOutBoundServiceM.setShortFaculty((String) result[0]);
		inBoundOutBoundServiceM.setnoOfInter((Integer) result[1]);
		inBoundOutBoundServiceM.setnoOfAll((Integer) result[2]);
		
		inBoundOutBoundServiceM.setPaging(null);
		inboundOutboundStudents.add(inBoundOutBoundServiceM);
		
	}
	 return inboundOutboundStudents;
}

}
