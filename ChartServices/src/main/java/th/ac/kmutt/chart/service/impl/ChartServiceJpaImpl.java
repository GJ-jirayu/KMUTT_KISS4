package th.ac.kmutt.chart.service.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import th.ac.kmutt.chart.builder.*;
import th.ac.kmutt.chart.domain.*;
import th.ac.kmutt.chart.model.*;
import th.ac.kmutt.chart.repository.ChartRepository;
import th.ac.kmutt.chart.service.ChartService;

import java.lang.reflect.Method;
import java.util.ArrayList;
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
    
    public FilterEntity getFilterValueList(Integer filterId) throws DataAccessException {
        return chartRepository.getFilterValueList(filterId);
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
    
    
    /*INBOUND_OUTBOUND_STUDENT

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
    }*/
    
    public List<FilterM> getGlobalFilter(){
    	return chartRepository.fetchGlobalFilter();
    }
    @SuppressWarnings("unchecked")
	public FusionChartM buildChartObject(FusionChartM source){
    	ChartInstanceEntity chartInsEnt = chartRepository.findChartInstanceEntityById(source.getInstanceId());
    	if(chartInsEnt!=null){
	    	source.setChartType(chartInsEnt.getChartType());
	    	source.setServiceId(chartInsEnt.getServiceId());
	    	try {
	    		//find filter
		    	FilterInstanceM fim = chartRepository.fetchFilterInstance(chartInsEnt.getInstanceId());
		    	List<FilterM> allFilters = new ArrayList<FilterM>(fim.getFilterList());
		    	System.out.println("filter"+fim.getFilterList().size()+":"+fim.getFilterList().get(0).getColumnName());
		    	if(source.getFilters()==null | source.getFilters().size()==0  ){ // portlet only send global filters 
		    		List<FilterM> globalFilters = chartRepository.fetchGlobalFilter();
		    		allFilters.addAll(globalFilters);
		    	}else{
		    		List<FilterM> globalFilters = source.getFilters();  
		    		allFilters.addAll(globalFilters);
		    	}
		    	source.setFilters(allFilters);  // set Filters for generate Title in  portlet app side
		    	// retrive data in chart
		    	List<Object[]> results = new ArrayList<Object[]>();
		    	if(chartInsEnt.getDataSourceType().equals("1")){
		    		//retrive datasource 
		    		ServiceEntity serviceEnt = chartRepository.findServiceEntityById(chartInsEnt.getServiceId());
		    		ServiceM datasource = new ServiceM();
		    		BeanUtils.copyProperties(serviceEnt, datasource);
			    	//fetch  result
		    		results = chartRepository.fetchChartResultSet(datasource, allFilters);
		    	}else if(chartInsEnt.getDataSourceType().equals("2")){
		    		results = convertDataJsonToList(chartInsEnt.getDataAdhoc());
		    	}
		    	String chartJson = "";
		    	if(chartInsEnt.getChartType().toLowerCase().equals("bar2d")){
		    		Bar bar = new Bar();
		    		bar.setTemplate(chartInsEnt.getChartJson());
		    		bar.setData(results);
		    		chartJson = bar.build();
		    	}else if(chartInsEnt.getChartType().toLowerCase().equals("angulargauge")){
		    		RealTimeAngular rta = new RealTimeAngular();
		    		rta.setTemplate(chartInsEnt.getChartJson());
		    		rta.setData(results);
		    		chartJson = rta.build();
		    	} else if(chartInsEnt.getChartType().toLowerCase().equals("mscolumn2d")) {
		    		Mscolumn2d col2d = new Mscolumn2d();
		    		col2d.setTemplate(chartInsEnt.getChartJson());
		    		col2d.setData(results);
		    		chartJson = col2d.build();
		    		System.out.println("line:"+chartJson);
		    	} else if(chartInsEnt.getChartType().toLowerCase().equals("table")) {
		    		Table table = new Table();
		    		//table.setTemplate(chartInsEnt.getChartJson());
		    		//table.setData(results);
		    		chartJson = table.build();	
		    	}
		    	// add json
		    	source.setChartJson(chartJson);
		    	System.out.println(chartJson);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}//check exist
    	return source;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List convertDataJsonToList(String jsonString){
    	List  dataList = new ArrayList<Object[]>();
    	try {
    		JSONObject json = new JSONObject(jsonString);
    		JSONArray rows = json.getJSONArray("data");
			 for (int i = 0; i < rows.length(); i++) {
				 JSONObject row = rows.getJSONObject(i);
				 JSONArray rw = row.getJSONArray("row");
				 if(rw.length()>1){
					 dataList.add(new Object[]{  rw.toString() });
				 }else{
					 dataList.add(rw.getString(0));
				 }
			}
			
		} catch (JSONException e) {
			dataList = new ArrayList<Object[]>();
		}
    	return dataList;
    }
    public List<FilterInstanceM> getFilterInstanceWithItem(String instanceId){
    	List<FilterInstanceM> fins = new ArrayList<FilterInstanceM>();
     	fins = chartRepository.fetchFilterInstanceWithItem(instanceId);
    	return fins;
    }
    public List<FilterM> getFilterOfService(Integer serviceId){
    	return 	chartRepository.fetchFilterOfService(serviceId);
    }
    public List<ChartFilterInstanceM> getChartFilterInstance(ChartFilterInstanceM chartFilterInstance){
    	return chartRepository.fetchChartFilterInstance(chartFilterInstance);
    }
    @Override 
    public FilterInstanceM getFilterInstance(FilterInstanceM fim){
    	return chartRepository.fetchFilterInstance(fim.getInstanceId());
    }
	@Override
	public FilterInstanceM saveFilterInstance(FilterInstanceM fim) {
		return chartRepository.saveFilterInstance(fim);
	}
	public Integer deleteFilterInstance(String instanceId){
		return chartRepository.deleteFilterInstance(instanceId);
	}
	@Override
	public Integer updateFilterInstance(FilterInstanceM fim) {
		return chartRepository.updateFilterInstance(fim);
	}
}
