package th.ac.kmutt.chart.portlet;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

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
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import th.ac.kmutt.chart.form.ChartSettingForm;
import th.ac.kmutt.chart.model.*;
import th.ac.kmutt.chart.service.ChartService;

import javax.portlet.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


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

   /* @Autowired
    @Qualifier("mscolumn2d")
    private MultiSeriesColumn2D mscolumn2d;*/
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
    @RequestMapping("VIEW")
    //@RenderMapping(params="render=show")
    public String iniPage(PortletRequest request, Model model) {
    	return "chart/showChart";
    }
    @RequestMapping("VIEW")
    @RenderMapping(params="render=show")
    public String displayChart(PortletRequest request, Model model) {
        ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);
        String instanceId=themeDisplay.getPortletDisplay().getInstanceId();
        // get chartInstance prop
        ChartInstanceM chartInstanceM=chartService.findChartInstanceById(instanceId);
        String chartType="";
        String chartHeight="300";
        String comment="";
        String dataSourceType="2";
        String subFromFilter="0";
        String titleFromFilter="0";
        String showFilter="0";
        String linkTo="";

        if(chartInstanceM!=null){
            chartType=chartInstanceM.getChartType();
            chartHeight=chartInstanceM.getChartHeight();
            if(chartInstanceM.getComment()!=null && chartInstanceM.getComment().getComment()!=null)
                comment=chartInstanceM.getComment().getComment();
            dataSourceType=chartInstanceM.getDataSourceType();
            subFromFilter=chartInstanceM.getSubFromFilter();
            titleFromFilter=chartInstanceM.getTitleFromFilter();
            showFilter=chartInstanceM.getShowFilter();
            linkTo=chartInstanceM.getLinkTo();
        }
        
        // chart Setting
        ChartSettingForm chartSettingForm = null;
        if (!model.containsAttribute("chartSettingForm")) {
            chartSettingForm = new ChartSettingForm();
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
        
        
        if(chartInstanceM!=null){
        //retrive global filter 
        List<FilterM> globalFilter = new ArrayList<FilterM>();
        if (model.containsAttribute("globalFilter")) {
            FilterInstanceM filterIns = (FilterInstanceM) model.asMap().get("globalFilter");
            globalFilter = filterIns.getFilterList();
            chartSettingForm.setGlobalFilterString(encrptGlobalFilterString(globalFilter));//  store global filter
        }else{
        	globalFilter = chartService.getGlobalFilter(); // default all  global filter
        }
        // retrive internal filter 
        List<FilterM> internalFilter = new ArrayList<FilterM>();
        if (model.containsAttribute("submitFilter")) {
        	internalFilter = (ArrayList<FilterM>)model.asMap().get("submitFilter");
        }else{
            FilterInstanceM filterInstance = new FilterInstanceM();
            filterInstance.setInstanceId(instanceId);
            filterInstance = chartService.getFilterInstance(filterInstance);
            internalFilter = filterInstance.getFilterList();
        }
        
        //get chart object
        List<FilterM> allFilter = new ArrayList<FilterM>();
        allFilter.addAll(globalFilter);
        allFilter.addAll(internalFilter);
        FusionChartM fusionChart = new FusionChartM();
        fusionChart.setInstanceId(instanceId);
        fusionChart.setFilters(allFilter);  // 
        fusionChart = chartService.getFusionChart(fusionChart);
 
        // setting chart & title
        String ChartJsonString = "";
        if(chartInstanceM!=null){
	        try{
		        JSONObject ChartJson = new JSONObject(fusionChart.getChartJson());
	        	JSONObject chart = (JSONObject) ChartJson.get("chart");
		        if(chartInstanceM.getChartTitle()!=null && ! chartInstanceM.getChartTitle().trim().equals("")){
		        	String newTitle =  generateChartTitle(chartInstanceM.getChartTitle(),fusionChart.getFilters(),chartSettingForm.getTitleFromFilter());      
		        	chart.put("caption", newTitle);
		        }
		        if(chartInstanceM.getChartSubTitle()!=null && !chartInstanceM.getChartSubTitle().trim().equals("")){
		        	String newSub = generateChartSubTitle(chartInstanceM.getChartSubTitle(),fusionChart.getFilters(),chartSettingForm.getSubFromFilter());
		        	chart.put("subCaption", newSub);
		        }
		        ChartJsonString = ChartJson.toString();    
	        }catch(Exception ex){
	        	  logger.info("WARNING! Exception Instance title generator : ["+chartInstanceM.getInstanceId()+"] \r\n reason => "+ex.getMessage()); 
	        	  ChartJsonString = fusionChart.getChartJson();
	        }
        }
        // set chart object 
        chartSettingForm.setJsonStr( ChartJsonString );
        
        // add model into view
        model.addAttribute("filters",internalFilter);
        }
        model.addAttribute("chartSettingForm", chartSettingForm);
        return "chart/showChart";
    }
    @RequestMapping(params = "action=doSubmit") // action phase
    public void doSubmit(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
                         @ModelAttribute("chartSettingForm") ChartSettingForm chartSettingForm,
                         BindingResult result, Model model) {
    	String instanceId = chartSettingForm.getChartInstance();
    	//retrive global Filter from view
    	String globalString = chartSettingForm.getGlobalFilterString();
    	if(globalString!=null){
	    	if(!globalString.equals("")){
	    		FilterInstanceM globalFilter = new FilterInstanceM();
	    		globalFilter.setFilterList(decodeGlobalFilterString(globalString));
	    		model.addAttribute("globalFilter", globalFilter);
    		}
    	}
    	//retrive Internal Filter  from view
        FilterInstanceM filterInstance = new FilterInstanceM();
        filterInstance.setInstanceId(instanceId);
        List<FilterM> submitFilterList = new ArrayList<FilterM>(); 
        List<FilterInstanceM> filters = chartService.getFilterInstanceWithItem(filterInstance);
        for(FilterInstanceM filter : filters){
        	FilterM sumbitFilter = filter.getFilterM();
    		String val = request.getParameter("filter_"+instanceId+"_"+filter.getFilterId());
    		if(val!=null){
    			sumbitFilter.setSelectedValue(val);
    		}// end exist value
    		submitFilterList.add(sumbitFilter);
    	}
        model.addAttribute("submitFilter", submitFilterList);
        response.setRenderParameter("render", "show");
    }
    @EventMapping(value ="{http://liferay.com/events}empinfo")
    //@javax.portlet.ProcessEvent(qname = "{http://liferay.com}empinfo")
    public void receiveEvent(EventRequest request, EventResponse response, ModelMap map)
    {
        Event event = request.getEvent();
        FilterInstanceM globalFilter = (FilterInstanceM) event.getValue();
        map.put("empinfo", globalFilter);
        map.addAttribute("globalFilter", globalFilter);
      //  response.setRenderParameter("action", "list");
        response.setRenderParameter("render", "show");
    }
    public String generateChartTitle(String titleString , List<FilterM> filters,String flag){
    	 String newTitle = titleString;
    	 if(flag!=null ){
    		 if( flag.equals("1")){
		    	 for( FilterM filter : filters ){
		    		 CharSequence  check = "_"+filter.getColumnName()+"_";
		    		 if(    newTitle.contains(check)    ){
		    			 newTitle = newTitle.replaceAll(check.toString(),filter.getSelectedValue());
		    		 }
		    	 }
    		 }
    	 }//end check null
    	  return newTitle;
    }
    public String generateChartSubTitle(String subTitleString , List<FilterM> filters,String flag ){
    	String newSub = subTitleString;
    	if(flag!=null ){
    		if(flag.equals("1")){
	    		for( FilterM filter : filters ){
		    		 CharSequence  check = "_"+filter.getColumnName()+"_";
		    		 if(    newSub.contains(check)    ){
		    			 newSub = newSub.replaceAll(check.toString(),filter.getSelectedValue());
		    		 }
		    	}
    		}//end check enable
    	 }// end check null
    	return newSub;
    }
    public String encrptGlobalFilterString(List<FilterM> globalFilter){
    	String filterString = "";
    	String nextLimit = ":next:";
    	String valLimit = ":val:";
    	for(FilterM gm : globalFilter){
    		filterString  = filterString+nextLimit+gm.getColumnName()+valLimit+gm.getSelectedValue();
    	}
    	//example  paramYear:val:2560:next:paramType:val:1
    	return filterString;
    }
    public List<FilterM> decodeGlobalFilterString(String filerString){
    	List<FilterM> globalFilter = new ArrayList<FilterM>();
    	String nextLimit = ":next:";
    	String valLimit = ":val:";
    	String[] gs = filerString.split(nextLimit);
    	for( String g : gs){
    		try{
	    		String[] gkv = g.split(valLimit);  // [0] = key , [1] = value
	    		FilterM gm = new FilterM();
	    		gm.setColumnName(gkv[0]);
	    		gm.setSelectedValue(gkv[1]);
	    		globalFilter.add(gm);
    		}catch(Exception ex){
    		}
    	} // end loop
    	return globalFilter;
    }
}
