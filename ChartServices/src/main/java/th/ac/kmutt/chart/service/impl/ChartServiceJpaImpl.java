package th.ac.kmutt.chart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import th.ac.kmutt.chart.domain.*;
import th.ac.kmutt.chart.model.*;
import th.ac.kmutt.chart.repository.ChartRepository;
import th.ac.kmutt.chart.service.ChartService;

import java.util.List;

@Service("chartServiceJpaImpl")
public class ChartServiceJpaImpl implements ChartService {


    @Autowired
    @Qualifier("chartRepository")
    private ChartRepository chartRepository;

    public List aew()throws DataAccessException{
        List<ChartEntity> list = chartRepository.aew();
        return list;
    }
    public List listChart() throws DataAccessException {
        List<ChartEntity> list = chartRepository.listChart();
        return list;
    }

    public List listCopyrightServiceEntity(CopyrightServiceM param) throws DataAccessException {
        return chartRepository.listCopyrightServiceEntity(param);
    }

    public List listJournalPapersServiceEntity(JournalPapersServiceM param) throws DataAccessException {
        return chartRepository.listJournalPapersServiceEntity(param);
    }

    public List listFundingResourceServiceEntity(FundingResourceServiceM param) throws DataAccessException {
        return chartRepository.listFundingResourceServiceEntity(param);
    }

    public Integer saveCopyrightServiceEntity(CopyrightServiceM model) throws DataAccessException {
        CopyrightServiceEntity entity=new CopyrightServiceEntity();
        CopyrightServiceEntityPK id=new CopyrightServiceEntityPK();
        id.setType(model.getType());
        id.setYear(model.getYear());
        id.setMonth(model.getMonth());
        entity.setId(id);
        entity.setMonthDesc(model.getMonthDesc());
        entity.setValue(model.getValue());
        return chartRepository.saveCopyrightServiceEntity(entity);
    }

    public Integer saveChartEntity(ChartEntity transientInstance) throws DataAccessException {
        return chartRepository.saveChartEntity(transientInstance);
    }

    public Integer updateChartEntity(ChartEntity transientInstance) throws DataAccessException {
        return chartRepository.updateChartEntity(transientInstance);
    }

    public Integer deleteChartEntity(ChartEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteChartEntity(persistentInstance);
    }

    public ChartEntity findChartEntityById(Integer chartId) throws DataAccessException {
        return chartRepository.findChartEntityById(chartId);
    }

    @Override
    public List listChartEntity(ChartM param) throws DataAccessException {
        return chartRepository.listChartEntity(param);
    }

    public Integer saveChartFeatureEntity(ChartFeatureEntity transientInstance) throws DataAccessException {
        return chartRepository.saveChartFeatureEntity(transientInstance);
    }

    public Integer updateChartFeatureEntity(ChartFeatureEntity transientInstance) throws DataAccessException {
        return chartRepository.updateChartFeatureEntity(transientInstance);
    }

    public Integer deleteChartFeatureEntity(ChartFeatureEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteChartFeatureEntity(persistentInstance);
    }

    public ChartFeatureEntity findChartFeatureEntityById(Integer chartId) throws DataAccessException {
        return chartRepository.findChartFeatureEntityById(chartId);
    }

    public Integer saveChartFeatureInstanceEntity(ChartFeatureInstanceEntity transientInstance) throws DataAccessException {
        return chartRepository.saveChartFeatureInstanceEntity(transientInstance);
    }

    public Integer updateChartFeatureInstanceEntity(ChartFeatureInstanceEntity transientInstance) throws DataAccessException {
        return chartRepository.updateChartFeatureInstanceEntity(transientInstance);
    }

    public Integer deleteChartFeatureInstanceEntity(ChartFeatureInstanceEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteChartFeatureInstanceEntity(persistentInstance);
    }

    public ChartFeatureInstanceEntity findChartFeatureInstanceEntityById(String instanceId) throws DataAccessException {
        return chartRepository.findChartFeatureInstanceEntityById(instanceId);
    }

    public Integer saveChartFeatureMappingEntity(ChartFeatureMappingEntity transientInstance) throws DataAccessException {
        return chartRepository.saveChartFeatureMappingEntity(transientInstance);
    }

    public Integer updateChartFeatureMappingEntity(ChartFeatureMappingEntity transientInstance) throws DataAccessException {
        return chartRepository.updateChartFeatureMappingEntity(transientInstance);
    }

    public Integer deleteChartFeatureMappingEntity(ChartFeatureMappingEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteChartFeatureMappingEntity(persistentInstance);
    }

    public ChartFeatureMappingEntity findChartFeatureMappingEntityById(ChartFeatureMappingEntityPK id) throws DataAccessException {
        return chartRepository.findChartFeatureMappingEntityById(id);
    }

    public Integer saveChartFilterInstanceEntity(ChartFilterInstanceEntity transientInstance) throws DataAccessException {
        return chartRepository.saveChartFilterInstanceEntity(transientInstance);
    }

    public Integer updateChartFilterInstanceEntity(ChartFilterInstanceEntity transientInstance) throws DataAccessException {
        return chartRepository.updateChartFilterInstanceEntity(transientInstance);
    }

    public Integer deleteChartFilterInstanceEntity(ChartFilterInstanceEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteChartFilterInstanceEntity(persistentInstance);
    }

    public ChartFilterInstanceEntity findChartFilterInstanceEntityById(String instanceId) throws DataAccessException {
        return chartRepository.findChartFilterInstanceEntityById(instanceId);
    }

    public Integer saveChartInstanceEntity(ChartInstanceEntity transientInstance) throws DataAccessException {
        return chartRepository.saveChartInstanceEntity(transientInstance);
    }

    public Integer updateChartInstanceEntity(ChartInstanceEntity transientInstance) throws DataAccessException {
        return chartRepository.updateChartInstanceEntity(transientInstance);
    }

    public Integer deleteChartInstanceEntity(ChartInstanceEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteChartInstanceEntity(persistentInstance);
    }

    public ChartInstanceEntity findChartInstanceEntityById(String instanceId) throws DataAccessException {
        return chartRepository.findChartInstanceEntityById(instanceId);
    }

    @Override
    public List listChartFilterInstanceEntity(ChartFilterInstanceM param) throws DataAccessException {
        return chartRepository.listChartFilterInstanceEntity(param);
    }

    public Integer saveCommentEntity(CommentEntity transientInstance) throws DataAccessException {
        return chartRepository.saveCommentEntity(transientInstance);
    }

    public Integer updateCommentEntity(CommentEntity transientInstance) throws DataAccessException {
        return chartRepository.updateCommentEntity(transientInstance);
    }

    public Integer deleteCommentEntity(CommentEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteCommentEntity(persistentInstance);
    }

    public CommentEntity findCommentEntityById(String instanceId) throws DataAccessException {
        return chartRepository.findCommentEntityById(instanceId);
    }

    public Integer saveCopyrightServiceEntity(CopyrightServiceEntity transientInstance) throws DataAccessException {
        return chartRepository.saveCopyrightServiceEntity(transientInstance);
    }

    public Integer updateCopyrightServiceEntity(CopyrightServiceEntity transientInstance) throws DataAccessException {
        return chartRepository.updateCopyrightServiceEntity(transientInstance);
    }

    public Integer deleteCopyrightServiceEntity(CopyrightServiceEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteCopyrightServiceEntity(persistentInstance);
    }

    public CopyrightServiceEntity findCopyrightServiceEntityById(CopyrightServiceEntityPK id) throws DataAccessException {
        return chartRepository.findCopyrightServiceEntityById(id);
    }

    public Integer saveFeatureEntity(FeatureEntity transientInstance) throws DataAccessException {
        return chartRepository.saveFeatureEntity(transientInstance);
    }

    public Integer updateFeatureEntity(FeatureEntity transientInstance) throws DataAccessException {
        return chartRepository.updateFeatureEntity(transientInstance);
    }

    public Integer deleteFeatureEntity(FeatureEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteFeatureEntity(persistentInstance);
    }

    public FeatureEntity findFeatureEntityById(Integer featureId) throws DataAccessException {
        return chartRepository.findFeatureEntityById(featureId);
    }

    public Integer saveFilterEntity(FilterEntity transientInstance) throws DataAccessException {
        return chartRepository.saveFilterEntity(transientInstance);
    }

    public Integer updateFilterEntity(FilterEntity transientInstance) throws DataAccessException {
        return chartRepository.updateFilterEntity(transientInstance);
    }

    public Integer deleteFilterEntity(FilterEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteFilterEntity(persistentInstance);
    }

    public FilterEntity findFilterEntityById(Integer filterId) throws DataAccessException {
        return chartRepository.findFilterEntityById(filterId);
    }

    public Integer saveFilterInstanceEntity(FilterInstanceEntity transientInstance) throws DataAccessException {
        return chartRepository.saveFilterInstanceEntity(transientInstance);
    }

    public Integer updateFilterInstanceEntity(FilterInstanceEntity transientInstance) throws DataAccessException {
        return chartRepository.updateFilterInstanceEntity(transientInstance);
    }

    public Integer deleteFilterInstanceEntity(FilterInstanceEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteFilterInstanceEntity(persistentInstance);
    }

    public FilterInstanceEntity findFilterInstanceEntityById(String instanceId) throws DataAccessException {
        return chartRepository.findFilterInstanceEntityById(instanceId);
    }

    @Override
    public List listFilterInstanceEntity(FilterInstanceM param) throws DataAccessException {
        return chartRepository.listFilterInstanceEntity(param);
    }

    public Integer saveFundingResourceServiceEntity(FundingResourceServiceEntity transientInstance) throws DataAccessException {
        return chartRepository.saveFundingResourceServiceEntity(transientInstance);
    }

    public Integer updateFundingResourceServiceEntity(FundingResourceServiceEntity transientInstance) throws DataAccessException {
        return chartRepository.updateFundingResourceServiceEntity(transientInstance);
    }

    public Integer deleteFundingResourceServiceEntity(FundingResourceServiceEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteFundingResourceServiceEntity(persistentInstance);
    }

    public FundingResourceServiceEntity findFundingResourceServiceEntityById(FundingResourceServiceEntityPK id) throws DataAccessException {
        return chartRepository.findFundingResourceServiceEntityById(id);
    }

    public Integer saveJournalPapersServiceEntity(JournalPapersServiceEntity transientInstance) throws DataAccessException {
        return chartRepository.saveJournalPapersServiceEntity(transientInstance);
    }

    public Integer updateJournalPapersServiceEntity(JournalPapersServiceEntity transientInstance) throws DataAccessException {
        return chartRepository.updateJournalPapersServiceEntity(transientInstance);
    }

    public Integer deleteJournalPapersServiceEntity(JournalPapersServiceEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteJournalPapersServiceEntity(persistentInstance);
    }

    public JournalPapersServiceEntity findJournalPapersServiceEntityById(JournalPapersServiceEntityPK id) throws DataAccessException {
        return chartRepository.findJournalPapersServiceEntityById(id);
    }

    public Integer saveServiceChartMappingEntity(ServiceChartMappingEntity transientInstance) throws DataAccessException {
        return chartRepository.saveServiceChartMappingEntity(transientInstance);
    }

    public Integer updateServiceChartMappingEntity(ServiceChartMappingEntity transientInstance) throws DataAccessException {
        return chartRepository.updateServiceChartMappingEntity(transientInstance);
    }

    public Integer deleteServiceChartMappingEntity(ServiceChartMappingEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteServiceChartMappingEntity(persistentInstance);
    }

    public ServiceChartMappingEntity findServiceChartMappingEntityById(ServiceChartMappingEntityPK id) throws DataAccessException {
        return chartRepository.findServiceChartMappingEntityById(id);
    }

    public Integer saveServiceEntity(ServiceEntity transientInstance) throws DataAccessException {
        return chartRepository.saveServiceEntity(transientInstance);
    }

    public Integer updateServiceEntity(ServiceEntity transientInstance) throws DataAccessException {
        return chartRepository.updateServiceEntity(transientInstance);
    }

    public Integer deleteServiceEntity(ServiceEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteServiceEntity(persistentInstance);
    }

    public ServiceEntity findServiceEntityById(Integer serviceId) throws DataAccessException {
        return chartRepository.findServiceEntityById(serviceId);
    }

    @Override
    public List listServiceEntity(ServiceM param) throws DataAccessException {
        return chartRepository.listServiceEntity(param);
    }

    public Integer saveServiceFilterMappingEntity(ServiceFilterMappingEntity transientInstance) throws DataAccessException {
        return chartRepository.saveServiceFilterMappingEntity(transientInstance);
    }

    public Integer updateServiceFilterMappingEntity(ServiceFilterMappingEntity transientInstance) throws DataAccessException {
        return chartRepository.updateServiceFilterMappingEntity(transientInstance);
    }

    public Integer deleteServiceFilterMappingEntity(ServiceFilterMappingEntity persistentInstance) throws DataAccessException {
        return chartRepository.deleteServiceFilterMappingEntity(persistentInstance);
    }

    public ServiceFilterMappingEntity findServiceFilterMappingEntityById(ServiceFilterMappingEntityPK id) throws DataAccessException {
        return chartRepository.findServiceFilterMappingEntityById(id);
    }

    @Override
    public List listServiceFilterMappingEntity(ServiceFilterMappingM param) throws DataAccessException {
        return chartRepository.listServiceFilterMappingEntity(param);
    }

    @Override
    public List listFilterEntity(FilterM param) throws DataAccessException {
        return chartRepository.listFilterEntity(param);
    }
    
    
    /*INBOUND_OUTBOUND_STUDENT*/

    
    public List InternationalCompareAllStudent(InBoundOutBoundServiceM persistentInstance){
    	return chartRepository.InternationalCompareAllStudent(persistentInstance);
    }
    
    
    public List EmpInternationalCompareAllEmp(InBoundOutBoundServiceM persistentInstance){
    	return chartRepository.EmpInternationalCompareAllEmp(persistentInstance);
    }
    
    public List ProgramInternationalCompareAllProgram(InBoundOutBoundServiceM persistentInstance){
    	return chartRepository.ProgramInternationalCompareAllProgram(persistentInstance);
    }
    
    public List InternationalCompareAllStudentProgramInter(InBoundOutBoundServiceM persistentInstance){
    	return chartRepository.InternationalCompareAllStudentProgramInter(persistentInstance);
    }
    
    public List InternationalCompareAllStudentByFaculty(InBoundOutBoundServiceM persistentInstance){
    	return chartRepository.InternationalCompareAllStudentByFaculty(persistentInstance);
    }
    
    public List InternationalCompareAllEmpByFaculty(InBoundOutBoundServiceM persistentInstance){
    	return chartRepository.InternationalCompareAllEmpByFaculty(persistentInstance);
    }
}
