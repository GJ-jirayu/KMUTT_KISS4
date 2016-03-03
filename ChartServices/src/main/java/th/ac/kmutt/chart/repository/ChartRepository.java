package th.ac.kmutt.chart.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Formatter.BigDecimalLayoutForm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.liferay.mail.model.Filter;

import th.ac.kmutt.chart.constant.ServiceConstant;
import th.ac.kmutt.chart.domain.ChartEntity;
import th.ac.kmutt.chart.domain.ChartFeatureEntity;
import th.ac.kmutt.chart.domain.ChartFeatureInstanceEntity;
import th.ac.kmutt.chart.domain.ChartFeatureMappingEntity;
import th.ac.kmutt.chart.domain.ChartFeatureMappingEntityPK;
import th.ac.kmutt.chart.domain.ChartFilterInstanceEntity;
import th.ac.kmutt.chart.domain.ChartInstanceEntity;
import th.ac.kmutt.chart.domain.CommentEntity;
import th.ac.kmutt.chart.domain.FeatureEntity;
import th.ac.kmutt.chart.domain.FilterEntity;
import th.ac.kmutt.chart.domain.FilterInstanceEntity;
import th.ac.kmutt.chart.dwh.domain.InboundOutboundStudent;
import th.ac.kmutt.chart.domain.ServiceChartMappingEntity;
import th.ac.kmutt.chart.domain.ServiceChartMappingEntityPK;
import th.ac.kmutt.chart.domain.ServiceEntity;
import th.ac.kmutt.chart.domain.ServiceFilterMappingEntity;
import th.ac.kmutt.chart.domain.ServiceFilterMappingEntityPK;
import th.ac.kmutt.chart.fusion.model.FilterFusionM;
import th.ac.kmutt.chart.model.CopyrightServiceM;
import th.ac.kmutt.chart.model.FilterInstanceM;
import th.ac.kmutt.chart.model.FilterM;
import th.ac.kmutt.chart.model.FilterValueM;
import th.ac.kmutt.chart.model.FundingResourceServiceM;
import th.ac.kmutt.chart.model.ServiceM;

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
        StringBuffer sb = new StringBuffer(" select p from  ChartEntity p ");
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
        if(transientInstance.getCommentByInstanceId()!=null && transientInstance.getCommentByInstanceId().getInstanceId()!=null
                && transientInstance.getCommentByInstanceId().getInstanceId().trim().length()>0)
       { 	entityManager.merge(transientInstance);
       }
        /*
        if(transientInstance.getCommentByInstanceId()!=null && transientInstance.getCommentByInstanceId().getInstanceId()!=null
                && transientInstance.getCommentByInstanceId().getInstanceId().trim().length()>0)
            entityManager.merge(transientInstance.getCommentByInstanceId());*/
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
    public FilterEntity getFilterValueList(Integer filterId) throws DataAccessException{
    	FilterEntity ret = new FilterEntity();
    	try{
    		ret  = entityManager.find(FilterEntity.class, filterId);
   // 		ret  = entityManager.find(FilterValueEntity.class, filterId);
    	}catch(Exception ex){
    		ret = new FilterEntity();
    	}
    	return ret;
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

    
    /*INBOUND_OUTBOUND_STUDENT
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
	*/
	
	@SuppressWarnings("unchecked")
	public List<Object[]> fetchChartResultSet(ServiceM datasource,List<FilterM> filters){
		List<Object[]>  results  = new ArrayList<Object[]>();

			String sql = datasource.getSqlString();
			Query query = entityManagerDwh.createNativeQuery(sql);
			for( FilterM filter : filters ){
				if(  sql.contains(":"+filter.getColumnName())  ){ // check param syntax in sqlQuery
					query.setParameter(filter.getColumnName(),filter.getSelectedValue());
				}
			}
			results = query.getResultList();

		return results;
	}
	@SuppressWarnings("unchecked")
	public List<FilterM> fetchInstanceFilters(String instanceId){
		 List<FilterM> filters = new ArrayList<FilterM>();  
		 // [filter_name,column_name,value]
		String sql = "SELECT f.FILTER_NAME,f.COLUMN_NAME, coalesce( cfi.VALUE , f.SUBSTITUTE_DEFAULT) as filter_value "
				+ "	FROM CHART_FILTER_INSTANCE cfi inner join  FILTER f on cfi.FILTER_ID = f.FILTER_ID "
				+ "   WHERE cfi.instance_id = '"+instanceId+"'";
		try{
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> resultSet = query.getResultList();
		for( Object[] result :  resultSet){
			FilterM f = new FilterM();
			f.setFilterName( (String)result[0]);
			f.setColumnName((String)result[1]);
			f.setSelectedValue( (String)result[2] );
			filters.add(f);
		}
		}catch(Exception ex){
			filters = new ArrayList<FilterM>();  
		}
		return filters;
	}
	public List<FilterInstanceM> fetchAllFilterInstance(String instanceId){
		//  no try exception
		List<FilterInstanceM> fins = new ArrayList<FilterInstanceM>();
		String sql = "SELECT f.filter_id,f.filter_name,f.sql_query,cfi.VALUE "
				+ "	FROM CHART_FILTER_INSTANCE cfi inner join FILTER f on cfi.FILTER_ID  = f.FILTER_ID "
				+ " where INSTANCE_ID =  '"+instanceId+"'";
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> resultSet = query.getResultList();
		for( Object[] result :  resultSet){
			FilterInstanceM fin = new FilterInstanceM();
			fin.setInstanceId(instanceId);
			fin.setFilterId( (Integer)result[0]);
			FilterM f = new FilterM();
			f.setFilterId( (Integer)result[0] );
			f.setFilterName( (String) result[1]);
			f.setSqlQuery((String) result[2]);
			f.setSelectedValue((String)result[3]);
			fin.setFilterM(f);	//set fin Filter
			
			List<FilterValueM> fvs = new ArrayList<FilterValueM>();
			if(  f.getSqlQuery()!=null   ){
				Query qf =   entityManager.createNativeQuery( f.getSqlQuery());
				List<Object[]> resultFilter =  qf.getResultList();
				for( Object[] rf : resultFilter ){
					fvs.add( buildFilterItems(rf) );
				}
			}else{
				fvs.add(buildFilterItems(new Object[]{}));
			}
			fin.setItems(fvs);  // set fin FilterValue
			fins.add(fin);
		}
		return fins;
	}
	public FilterValueM buildFilterItems(Object[] rf){
		FilterValueM fv = new FilterValueM();
		if(rf.length==1){
			//Can't be because entityManager.createNativeQuery  dose not reture List<Object[]>  for  1 column resultset
			//fv.setKeyMapping((String)rf[0]);
			//fv.setValueMapping((String)rf[0]);
		}else if(rf.length==2){
			fv.setKeyMapping((String)rf[0]);
			fv.setValueMapping((String)rf[1]);
		}else{
		}
		return fv;
	}
	public  List<FilterM> fetchGlobalFilter(){
		List<FilterM> filters = new ArrayList<FilterM>();
		String sql = "select filter_id,filter_name,column_name,SUBSTITUTE_DEFAULT,SQL_QUERY from FILTER where type = 'global'";
		Query q = entityManager.createNativeQuery(sql);
		List<Object[]> fd = q.getResultList();
		for(Object[] f : fd){
			FilterM fm = new FilterM();
			fm.setFilterId( (Integer) f[0]  );
			fm.setFilterName( (String) f[1] );
			fm.setColumnName((String) f[2]);
			fm.setSelectedValue((String) f[3]);
			fm.setSqlQuery((String) f[4] );
			
			//filterValue
			List<FilterValueM> fvs = new ArrayList<FilterValueM>();
			if(  fm.getSqlQuery()!=null   ){
				Query qf =   entityManager.createNativeQuery( fm.getSqlQuery());
				List<Object[]> resultFilter =  qf.getResultList();
				for( Object[] rf : resultFilter ){
					fvs.add( buildFilterItems(rf) );
				}
			}else{
				fvs.add(buildFilterItems(new Object[]{}));
			}
			fm.setFilterValues(fvs);
			//into list
			filters.add(fm);
		}
		return filters;
	}
	public List<FilterM> fetchFilterOfService(Integer serviceId){
		List<FilterM> filters = new ArrayList<FilterM>();
		String sql = "select sfm from SERVICE_FILTE where SERVICE_ID = ";
		Query q =  entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> sfm = q.getResultList();
		//
		return filters;
	}
}
