package th.ac.kmutt.chart.portlet;

import com.google.gson.Gson;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.EventMapping;

import th.ac.kmutt.chart.form.ChartSettingForm;
import th.ac.kmutt.chart.fusion.model.*;
import th.ac.kmutt.chart.model.*;
import th.ac.kmutt.chart.service.ChartService;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import th.co.imake.chart.services.MultiSeriesColumn2D;

/**
 * Created by imake on 07/09/2015.
 */

@Controller("chartCommonController")
@RequestMapping("VIEW")
@SessionAttributes({"chartSettingForm"})
public class ChartCommonPortlet {

    /*private static final Logger logger = LogManager
            .getLogger(ChartCommonPortlet.class);
            */
    private static final Logger logger = Logger
            .getLogger(ChartCommonPortlet.class);

    @Autowired
    @Qualifier("chartServiceWSImpl")
    private ChartService chartService;

    @Autowired
    @Qualifier("mscolumn2d")
    private MultiSeriesColumn2D mscolumn2d;
    @InitBinder
    public void initBinder(PortletRequestDataBinder binder, PortletPreferences preferences) {
        logger.debug("initBinder");
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

        final String[] ALLOWED_FIELDS = {"researchGroupM.researchGroupId", "researchGroupM.createdBy", "researchGroupM.createdDate",
                "researchGroupM.groupCode", "researchGroupM.permissions", "researchGroupM.updatedBy",
                "researchGroupM.updatedDate", "researchGroupM.groupTh", "researchGroupM.groupEng", "mode", "command",
                "keySearch", "pageNo", "paging.pageSize", "ids", "tab", "filter","chartType","jsonStr",
                "chartInstance","chartJson","dataAdhoc","advProp","chartHeight","comment","dataSourceType","aoe_internal","filterRole"
        ,"linkTo","subFromFilter","dataSource","aoe_internal","chartTitle","chartSubTitle","showFilter","titleFromFilter"};

        binder.setAllowedFields(ALLOWED_FIELDS);
    }
    @RequestMapping
    // default (action=list)
    public String displayChart(PortletRequest request, Model model) {
      
        ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);
        String instanceId=themeDisplay.getPortletDisplay().getInstanceId();
        // get chartInstance prop
        ChartInstanceM chartInstanceM=chartService.findChartInstanceById(instanceId);
        String jsonStr="";
        String chartType="";
        String chartHeight="300";
        String comment="";
        String dataSourceType="2";
        String subFromFilter="0";
        String titleFromFilter="0";
        String showFilter="0";
        String linkTo="";
        String chartTitle="";
        String chartSubTitle="";
        Integer serviceId=null;

        if(chartInstanceM!=null){
            chartType=chartInstanceM.getChartType();
            jsonStr=chartInstanceM.getChartJson();  // template
            chartHeight=chartInstanceM.getChartHeight();
            if(chartInstanceM.getComment()!=null && chartInstanceM.getComment().getComment()!=null)
                comment=chartInstanceM.getComment().getComment();
            dataSourceType=chartInstanceM.getDataSourceType();
            subFromFilter=chartInstanceM.getSubFromFilter();
            titleFromFilter=chartInstanceM.getTitleFromFilter();
            showFilter=chartInstanceM.getShowFilter();
            linkTo=chartInstanceM.getLinkTo();
            serviceId =chartInstanceM.getServiceId();
            chartTitle=chartInstanceM.getChartTitle();
            chartSubTitle=chartInstanceM.getChartSubTitle();
        }
        
        // chart Setting
        ChartSettingForm chartSettingForm = null;
        if (!model.containsAttribute("chartSettingForm")) {
            chartSettingForm = new ChartSettingForm();
            model.addAttribute("chartSettingForm", chartSettingForm);
        } else {
            chartSettingForm = (ChartSettingForm) model.asMap().get("chartSettingForm");
        }
        //setting form chart 
        chartSettingForm.setChartInstance(instanceId);
        chartSettingForm.setChartType(chartType);
        chartSettingForm.setChartHeight(chartHeight);
        chartSettingForm.setComment(comment);
        chartSettingForm.setDataSourceType(dataSourceType);
        chartSettingForm.setSubFromFilter(subFromFilter);
        chartSettingForm.setTitleFromFilter(titleFromFilter);
        chartSettingForm.setShowFilter(showFilter);
        chartSettingForm.setLinkTo(linkTo);
        
        //get global filter to pass into service Build Chart
        List<FilterM> globalFilter = new ArrayList<FilterM>();
        FilterFusionM filterFusionM = null;
        if (model.containsAttribute("filterFusionM")) {
            filterFusionM = (FilterFusionM) model.asMap().get("filterFusionM");
            globalFilter = filterFusionM.getFilterMList();
        }
      
        //get chart object
        FusionChartM fusionChart = new FusionChartM();
        fusionChart.setInstanceId(instanceId);
        fusionChart.setFilters(globalFilter);  // set only global filter 
        fusionChart = chartService.getFusionChart(fusionChart);
 
        // setting chart & title
        String ChartJsonString = "";
        try{
	        JSONObject ChartJson = new JSONObject(fusionChart.getChartJson());
	        String newTitle =  generateChartTitle(chartInstanceM.getChartTitle(),fusionChart.getFilters(),chartSettingForm.getTitleFromFilter());
	        logger.info("test:"+chartInstanceM.getChartSubTitle());
	        String newSub = generateChartSubTitle(chartInstanceM.getChartSubTitle(),fusionChart.getFilters(),chartSettingForm.getSubFromFilter());
	        JSONObject chart = (JSONObject) ChartJson.get("chart");
	        chart.put("caption", newTitle);
	        chart.put("subCaption", newSub);
	        ChartJsonString = ChartJson.toString();    
        }catch(Exception ex){
        	  ChartJsonString = fusionChart.getChartJson();
        }
        
        // set chart object 
        chartSettingForm.setJsonStr( ChartJsonString );
        
        //get filter to display param select
        FilterInstanceM filterInstance = new FilterInstanceM();
        filterInstance.setInstanceId(instanceId);
        List<FilterInstanceM> AllfilterInstance = chartService.getAllFilterInstance(filterInstance);
      
        // add model into view
        model.addAttribute("filters",AllfilterInstance);
        //model.addAttribute("serviceFilterMappingMList",serviceFilterMappingMList);
        model.addAttribute("chartSettingForm", chartSettingForm);
        return "chart/showChart";
    }
    @RequestMapping(params = "action=doSubmit") // action phase
    public void doSubmit(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
                         @ModelAttribute("chartSettingForm") ChartSettingForm chartSettingForm,
                         BindingResult result, Model model) {
    	//String[] filters=request.getParameterValues("filters_"+chartSettingForm.getChartInstance());
    	// format filter select id  in view [filter_instanceId_filterid] *important*
    	String instanceId = chartSettingForm.getChartInstance();
    	//get Instance
        ChartInstanceM inst = chartService.findChartInstanceById(instanceId);
    	//get filter
        FilterInstanceM filterInstance = new FilterInstanceM();
        filterInstance.setInstanceId(instanceId);
        List<FilterInstanceM> filters = chartService.getAllFilterInstance(filterInstance);
    	for(FilterInstanceM filter : filters){
    		String val = request.getParameter("filter_"+instanceId+"_"+filter.getFilterId());
    	//	HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
    //		HttpServletRequest normalRequest =PortalUtil.getOriginalServletRequest(httpReq);
    //		String val = normalRequest.getParameter("filter_"+instanceId+"_"+filter.getFilterId());
    		logger.info("submit value:"+"filter_"+instanceId+"_"+filter.getFilterId()+":"+val);
    		if(val!=null){
    			ChartFilterInstanceM chartFilterInstanceM = new ChartFilterInstanceM();
    	        chartFilterInstanceM.setInstanceId(instanceId);
    	        chartFilterInstanceM.setServiceId(inst.getServiceId());
    	        chartFilterInstanceM.setFilterId(filter.getFilterId());
    	        chartFilterInstanceM.setValue(val);
    	        chartService.updateChartFilterInstance(chartFilterInstanceM);
    		}// end exist value
    		else{
    			logger.info("not exist"+filter.getFilterId());
    		}
    	}
        response.setRenderParameter("action", "list");
    }
    @EventMapping(value ="{http://liferay.com/events}empinfo")
    //@javax.portlet.ProcessEvent(qname = "{http://liferay.com}empinfo")
    public void receiveEvent(EventRequest request, EventResponse response, ModelMap map)
    {
        Event event = request.getEvent();
        FilterFusionM filterFusionM = (FilterFusionM) event.getValue();
        map.put("empinfo", filterFusionM);
        map.addAttribute("filterFusionM", filterFusionM);
        response.setRenderParameter("action", "list");
    }
    public String generateChartTitle(String titleString , List<FilterM> filters,String flag){
    	 String newTitle = titleString;
    	 if(flag!=null & flag.equals("1")){
	    	 for( FilterM filter : filters ){
	    		 CharSequence  check = "_"+filter.getColumnName()+"_";
	    		 if(    newTitle.contains(check)    ){
	    			 newTitle = newTitle.replaceAll(check.toString(),filter.getSelectedValue());
	    		 }
	    	 }
    	 }
    	  return newTitle;
    }
    public String generateChartSubTitle(String subTitleString , List<FilterM> filters,String flag ){
    	String newSub = subTitleString;
    	if(flag!=null & flag.equals("1")){
    		 for( FilterM filter : filters ){
	    		 CharSequence  check = "_"+filter.getColumnName()+"_";
	    		 if(    newSub.contains(check)    ){
	    			 newSub = newSub.replaceAll(check.toString(),filter.getSelectedValue());
	    		 }
	    	 }
    	 }
    	return newSub;
    }
}
