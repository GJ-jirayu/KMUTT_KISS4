package th.ac.kmutt.chart.portlet;

/**
 * Created by imake on 13/09/2015.
 */

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import th.ac.kmutt.chart.form.ChartSettingForm;
import th.ac.kmutt.chart.model.*;
import th.ac.kmutt.chart.service.ChartService;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("chartCommonSettingController")
@RequestMapping("EDIT")
@SessionAttributes({"chartSettingForm"})
public class ChartCommonSettingController {

    private static final Logger logger = Logger
            .getLogger(ChartCommonSettingController.class);

    @Autowired
    @Qualifier("chartServiceWSImpl")
    private ChartService chartService;
    // 'เลือกตามปี'
    private static final String[] YEAR_FILTER_KEY={"2550","2551","2552","2553","2554","2555","2556","2557","2558"};
    private static final String[] YEAR_FILTER_VALUE={"2550","2551","2552","2553","2554","2555","2556","2557","2558"};

    // ''เลือกตามเดือน''
    private static final String[] MONTH_FILTER_KEY={"1","2","3","4","5","6","7","8","9","10","11","12"};
    private static final String[] MONTH_FILTER_VALUE={"ม.ค.","ก.พ.","มี.ค.","เม.ย.","พ.ค.","มิ.ย.","ก.ค.","ส.ค.","ก.ย.","ต.ค.","พ.ย.","ธ.ค."};

    //'แหล่งที่ได้รับการเผยแพร่'
    private static final String[] PUBLISH_RESOURCE_FILTER_KEY={"1","2","3","4"};
    private static final String[] PUBLISH_RESOURCE_FILTER_VALUE={"วารสารนานาชาติ","ประชุมนานาชาติ","วารสารในประเทศ","ประชุมระดับประเทศ"};

    //'แหล่งเงินทุน'
    private static final String[] FUNDING_RESOURCE_FILTER_KEY={"1","2","3"};
    private static final String[] FUNDING_RESOURCE_FILTER_VALUE={"เงินรายได้ มจธ.","รัฐ ว.1","แหล่งทุนภายนอก"};
    @InitBinder
    public void initBinder(PortletRequestDataBinder binder, PortletPreferences preferences) {
        logger.debug("initBinder");
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        //String a[] = new String[]{"ntcfaq.nfaqName"};
        final String[] ALLOWED_FIELDS = {"researchGroupM.researchGroupId", "researchGroupM.createdBy", "researchGroupM.createdDate",
                "researchGroupM.groupCode", "researchGroupM.permissions", "researchGroupM.updatedBy",
                "researchGroupM.updatedDate", "researchGroupM.groupTh", "researchGroupM.groupEng", "mode", "command",
                "keySearch", "pageNo", "paging.pageSize", "ids", "tab", "chartType","jsonStr","chartInstance",
                "chartHeight","chartJson","dataAdhoc","advProp","comment","dataSourceType","aoe_internal","filterRole"
        ,"linkTo","subFromFilter","dataSource","aoe_internal","chartTitle","chartSubTitle","showFilter","titleFromFilter"};

        binder.setAllowedFields(ALLOWED_FIELDS);
    }

    @RequestMapping
    // default (action=list)
    public String showSetting(PortletRequest request, Model model) {
        ChartSettingForm chartSettingForm = null;
        if (!model.containsAttribute("chartSettingForm")) {
            chartSettingForm = new ChartSettingForm();
            model.addAttribute("chartSettingForm",
                    chartSettingForm);
        } else {
            chartSettingForm = (ChartSettingForm) model.asMap().get("chartSettingForm");
        }
        //get liferay interface
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
       // logger.info("themeDisplay->"+themeDisplay);

        String instanceId=themeDisplay.getPortletDisplay().getInstanceId(); 
        ChartInstanceM chartInstanceM=chartService.findChartInstanceById(instanceId);
        List<ChartFilterInstanceM>  chartFilterInstance = new ArrayList<ChartFilterInstanceM>();
        if(chartInstanceM!=null ){
            chartSettingForm.setChartInstance(instanceId);
            chartSettingForm.setAdvProp(chartInstanceM.getAdvProp());
            chartSettingForm.setDataAdhoc(chartInstanceM.getDataAdhoc());
            chartSettingForm.setChartType(chartInstanceM.getChartType());
            chartSettingForm.setChartJson(chartInstanceM.getChartJson());
            chartSettingForm.setJsonStr(chartInstanceM.getChartJson());
            chartSettingForm.setDataSourceType(chartInstanceM.getDataSourceType());
            chartSettingForm.setLinkTo(chartInstanceM.getLinkTo());
            chartSettingForm.setFilterRole(chartInstanceM.getFilterRole());
            chartSettingForm.setSubFromFilter(chartInstanceM.getSubFromFilter());
            chartSettingForm.setTitleFromFilter(chartInstanceM.getTitleFromFilter());
            chartSettingForm.setDataSource(chartInstanceM.getServiceId()+"");
            chartSettingForm.setChartTitle(chartInstanceM.getChartTitle());
            chartSettingForm.setChartSubTitle(chartInstanceM.getChartSubTitle());
            chartSettingForm.setShowFilter(chartInstanceM.getShowFilter());
            // chart filter instance
            ChartFilterInstanceM cfi = new ChartFilterInstanceM();
            cfi.setInstanceId(instanceId);
            cfi.setServiceId(chartInstanceM.getServiceId());
            chartFilterInstance = chartService.getChartFilterInstance(cfi);
        }
        model.addAttribute("chartFilterInstance",chartFilterInstance);
        model.addAttribute("chartSettingForm", chartSettingForm);
        
        // list service & chart
        ServiceM  serviceM=new ServiceM();
        serviceM.setType("chart");
        @SuppressWarnings("unchecked")
		List<ServiceM> listServices=  chartService.listService(serviceM);
        model.addAttribute("serviceList",listServices);      
        ChartM chartM=new ChartM();
        chartM.setActiveFlag("1");
        List<ChartM> chartList= chartService.listChart(chartM);
        model.addAttribute("chartList", chartList);
        
        //model.addAttribute("serviceFilterMappingMList",serviceFilterMappingMList);
        return "chart/settingChart";
    }
    @RequestMapping(params = "action=doSubmit") // action phase
    public void doSubmit(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
                             @ModelAttribute("chartSettingForm") ChartSettingForm chartSettingForm,
                             BindingResult result, Model model) {
        //logger.info("into do submit instance =>"+chartSettingForm.getChartInstance()+" ,new json "+chartSettingForm.getJsonStr());
        boolean isNew=false;
        ChartInstanceM chartInstanceM=chartService.findChartInstanceById(chartSettingForm.getChartInstance());
        if(chartInstanceM==null) { // check new instance or not
            chartInstanceM = new ChartInstanceM();
            isNew=true;
        }
        chartInstanceM.setInstanceId(chartSettingForm.getChartInstance());
        chartInstanceM.setChartType(chartSettingForm.getChartType());
        chartInstanceM.setDataSourceType(chartSettingForm.getDataSourceType());
        if(chartSettingForm.getDataSourceType().equals("1")){ // choose use datasource
            chartInstanceM.setServiceId(Integer.valueOf(chartSettingForm.getDataSource()));
        }
        chartInstanceM.setChartJson(chartSettingForm.getChartJson());
        chartInstanceM.setAdvProp(chartSettingForm.getAdvProp());
        chartInstanceM.setDataAdhoc(chartSettingForm.getDataAdhoc());
        chartInstanceM.setChartHeight(chartSettingForm.getChartHeight());
        chartInstanceM.setLinkTo(chartSettingForm.getLinkTo());
        chartInstanceM.setFilterRole(chartSettingForm.getFilterRole());
        chartInstanceM.setSubFromFilter(chartSettingForm.getSubFromFilter());
        chartInstanceM.setTitleFromFilter(chartSettingForm.getTitleFromFilter());
        chartInstanceM.setShowFilter(chartSettingForm.getShowFilter());
        chartInstanceM.setChartTitle(chartSettingForm.getChartTitle());
        chartInstanceM.setChartSubTitle(chartSettingForm.getChartSubTitle());
        if(chartSettingForm.getComment()!=null){
            CommentM commentM=new CommentM();
            commentM.setInstanceId(chartInstanceM.getInstanceId());
            commentM.setComment(chartSettingForm.getComment());
            chartInstanceM.setComment(commentM);
        }
        //  insertNew or update ChartInstance
        if(isNew)
            chartService.saveChartInstance(chartInstanceM);
        else
            chartService.updateChartInstance(chartInstanceM);

        // save FilterInstance
        ServiceFilterMappingM sfm = new ServiceFilterMappingM();
        sfm.setServiceId(Integer.valueOf(chartSettingForm.getDataSource()));
        @SuppressWarnings("unchecked")
		List<ServiceFilterMappingM> sfmList = chartService.listServiceFilterMapping(sfm);
        List<FilterM> filterList = new ArrayList<FilterM>();
        if(sfmList!=null){
	        for( ServiceFilterMappingM sfmItem : sfmList){
	        	FilterM readFilter = sfmItem.getFilterM();
	        	String filterActiveFlag = request.getParameter("filter_active_"+readFilter.getFilterId());
	        	logger.info("filterM=>"+readFilter.getFilterId()+":"+filterActiveFlag);
	        	String filterSelectValue  = request.getParameter("filter_selection_"+readFilter.getFilterId());
	        	logger.info("filterM=>"+readFilter.getFilterId()+":"+filterSelectValue);
	        	if(filterActiveFlag!=null ){ // insert checked only
	        		if(filterActiveFlag.equals("1")){
	        			readFilter.setActiveFlag(filterActiveFlag);
	    	        	if(filterSelectValue!=null ){ // insert checked only
	    	        			readFilter.setSelectedValue(filterSelectValue);
	    	        	}	// end have value
	        			filterList.add(readFilter);
	        		}// end  active = 1
	        	} // end have active
	        } // end serviceMappingList
        }
        FilterInstanceM fim = new FilterInstanceM();
        fim.setInstanceId(chartSettingForm.getChartInstance());
        fim.setFilterList(filterList);
        chartService.saveFilterInstance(fim);
        // end save Filter Instance
        try {
            response.setPortletMode(PortletMode.VIEW);
            response.setRenderParameter("action", "list");
        } catch (PortletModeException e) {
            e.printStackTrace();
        }
    }
    //
    @ResourceMapping(value="loadChartProp")
	@ResponseBody 
	public void loadChartProp(ResourceRequest request,ResourceResponse response) throws IOException{
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		String chartType = normalRequest.getParameter("chartType");
		String prop = normalRequest.getParameter("prop");
		com.liferay.portal.kernel.json.JSONObject json = JSONFactoryUtil.createJSONObject();
		com.liferay.portal.kernel.json.JSONObject header = JSONFactoryUtil.createJSONObject();
		header.put("prop", prop);
		
		ChartM param = new ChartM();
		param.setChartType(chartType);
		@SuppressWarnings("unchecked")
		List<ChartM> charts = chartService.listChart(param);
		if( charts!=null && charts.size()>0 ){
			ChartM chart = charts.get(0);
			if(prop.equals("chartJson")){
				json.put("content", chart.getChartJson());
			}
			header.put("success","1" );
		}else{

			header.put("success","0" );
		}
		json.put("header",header);
		response.getWriter().write(json.toString());
    }
    @ResourceMapping(value="loadServiceFilter")
   	@ResponseBody 
   	public void loadSericeFilter(ResourceRequest request,ResourceResponse response) throws IOException{
   		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
   		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
   		String serviceId = normalRequest.getParameter("serviceId");
   		com.liferay.portal.kernel.json.JSONObject json = JSONFactoryUtil.createJSONObject();
   		com.liferay.portal.kernel.json.JSONObject header = JSONFactoryUtil.createJSONObject();
   		com.liferay.portal.kernel.json.JSONArray data = JSONFactoryUtil.createJSONArray();
   		header.put("serviceId", serviceId);
   		
   		FilterM filter = new FilterM();
   		filter.setServiceId(Integer.valueOf(serviceId));
   		List<FilterM> filters = chartService.getFilterService(filter);
   		if( filters!=null && filters.size()>0 ){
   			header.put("success","1" );
   			for( FilterM readFilter : filters){
   				com.liferay.portal.kernel.json.JSONObject row = JSONFactoryUtil.createJSONObject();
   				row.put("id", readFilter.getFilterId() );
   				row.put("name", readFilter.getFilterName() );
   				row.put("param", readFilter.getColumnName() );
   				com.liferay.portal.kernel.json.JSONArray items = JSONFactoryUtil.createJSONArray();
   				if(readFilter.getFilterValues() !=null){
	   				for( FilterValueM fv : readFilter.getFilterValues() ){
	   					com.liferay.portal.kernel.json.JSONObject item = JSONFactoryUtil.createJSONObject();
	   					item.put("value", fv.getKeyMapping());
	   					item.put("display",fv.getValueMapping() );
	   					items.put(item);
	   				}
   				}
   				row.put("item", items);
   	   			data.put(row);
   			}
   		}else{
   			header.put("success","0" );
   		}
   		json.put("header",header);
   		json.put("data",data);
   		response.getWriter().write(json.toString());
       }
}
