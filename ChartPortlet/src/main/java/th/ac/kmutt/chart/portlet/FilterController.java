package th.ac.kmutt.chart.portlet;


import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import org.apache.log4j.Logger;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import th.ac.kmutt.chart.form.FilterForm;
import th.ac.kmutt.chart.model.FilterInstanceM;
import th.ac.kmutt.chart.model.FilterM;
import th.ac.kmutt.chart.model.FilterValueM;
import th.ac.kmutt.chart.service.ChartService;

import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import java.io.IOException;
import java.util.*;

/*import com.opencsv.CSVReader;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
*/
//import org.apache.log4j.Logger;
/*import th.ac.kmutt.research.constant.ServiceConstant;
import th.ac.kmutt.research.form.FilterForm;
import th.ac.kmutt.research.mapper.CustomObjectMapper;
import th.ac.kmutt.research.model.ResearchGroupM;
import th.ac.kmutt.research.service.ResearchService;
import th.ac.kmutt.research.utils.ImakeUtils;
import th.ac.kmutt.research.xstream.common.ImakeResultMessage;
*/


@Controller("filterController")
@RequestMapping("VIEW")
@SessionAttributes({"filterForm"})
public class FilterController {

    private static final Logger logger = Logger.getLogger(FilterController.class);


    @Autowired
    @Qualifier("chartServiceWSImpl")
    private ChartService chartService;
    @InitBinder
    public void initBinder(PortletRequestDataBinder binder, PortletPreferences preferences) {
        logger.debug("initBinder");
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        //String a[] = new String[]{"ntcfaq.nfaqName"};
        final String[] ALLOWED_FIELDS = {"researchGroupM.researchGroupId", "researchGroupM.createdBy", "researchGroupM.createdDate",
                "researchGroupM.groupCode", "researchGroupM.permissions", "researchGroupM.updatedBy",
                "researchGroupM.updatedDate", "researchGroupM.groupTh", "researchGroupM.groupEng", "mode",
                "command", "keySearch", "pageNo", "paging.pageSize", "ids", "tab", "filter","instanceId","filterGlobals","aoe_global"};

        binder.setAllowedFields(ALLOWED_FIELDS);
    }

    @RequestMapping("VIEW") 
    public String showFilter(PortletRequest request, Model model) {
    	ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);
        String instanceId=themeDisplay.getPortletDisplay().getInstanceId();
        
        FilterForm filterForm = null;
        if (!model.containsAttribute("filterForm")) {
            filterForm = new FilterForm();
            model.addAttribute("filterForm",
                    filterForm);
        } else {
            filterForm = (FilterForm) model.asMap().get("filterForm");
        }
        // retrive submit global filter value
        List<FilterM> globalFilter = new ArrayList<FilterM>();
        if (model.containsAttribute("FilterMList")) {
        	globalFilter = (ArrayList<FilterM>) model.asMap().get("FilterMList");
        }
        else{ // visit first  No submit action
        	FilterInstanceM fim = new FilterInstanceM();
        	fim.setInstanceId(instanceId);
        	List<FilterInstanceM>  fins = chartService.getFilterInstanceWithItem(fim);
        	if(fins!=null){
	        	for(FilterInstanceM fin : fins){
	        		globalFilter.add(fin.getFilterM());
	        	}
        	} // have fins
        }
        model.addAttribute("filterList",globalFilter);
        return "filter/showFilter";
    }

    @RequestMapping(params = "action=doSubmit") // action phase
    public void doSubmit(SessionStatus status,javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
                             @ModelAttribute("filterForm") FilterForm filterForm,
                             BindingResult result, Model model) {

       List<FilterM> gFilters = chartService.getGlobalFilter();
       List<FilterM> reRenFilters = new ArrayList<FilterM>();
       for(int i = 0 ; i<gFilters.size();i++){
    	   // map  name select in view showfilter.jsp  format  g_filter_+filterM.filterId
    	   String val = request.getParameter("g_filter_"+gFilters.get(i).getFilterId());
    	   gFilters.get(i).setSelectedValue(val);
    	   if(val!=null){
    		   reRenFilters.add(gFilters.get(i));
    	   }
       	}
       // portlet to portlet  require configuration  portlet.xml
        QName qname = new QName("http://liferay.com/events","empinfo","x");
       FilterInstanceM globalFilterIns = new FilterInstanceM();
        globalFilterIns.setFilterList(gFilters);
        response.setEvent(qname, globalFilterIns); // send event to all portlet 
        //status.setComplete();
        
        //re show 
        model.addAttribute("FilterMList", reRenFilters);
       // response.setRenderParameter("action", "list");
    }
    @ResourceMapping(value="cascadeGlobalFilter")
	@ResponseBody 
	public void cascadeGlobalFilter(ResourceRequest request,ResourceResponse response) throws IOException{
    	//for cascade parameter
    	JSONObject json = JSONFactoryUtil.createJSONObject();
    	JSONArray header = JSONFactoryUtil.createJSONArray();
    	JSONArray content = JSONFactoryUtil.createJSONArray();
       	
    	ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    	String instanceId=themeDisplay.getPortletDisplay().getInstanceId();
    	
    	HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
    	String causeFilterId = normalRequest.getParameter("filterId");
    	String factorString = normalRequest.getParameter("factor");
    	// format factorStrung  =  "filterId::filterValue||filterId::filterValue"
    	
    	FilterInstanceM fin = new FilterInstanceM();  
    	fin.setInstanceId(instanceId);
    	fin.setFilterList( decriptCascadeString(factorString,causeFilterId) );
    	List<FilterM> filters = chartService.cascadeFilterItems(fin); //
    	for(FilterM filter : filters){
    		if(!filter.getFilterId().equals(causeFilterId)){  // only change element to insert
	    		JSONObject fo = JSONFactoryUtil.createJSONObject();
	    		fo.put("id", filter.getFilterId());
	    		fo.put("value", filter.getSelectedValue());
	    		JSONArray foItem = JSONFactoryUtil.createJSONArray();
	    		for(FilterValueM fv : filter.getFilterValues()){
	    			JSONObject fov = JSONFactoryUtil.createJSONObject();
	    			fov.put("key",fv.getKeyMapping());
	    			fov.put("desc", fv.getValueMapping());
	    			foItem.put(fov);
	    		}
	    		fo.put("item", foItem);
	        	content.put(fo);
    		}
    	}
    	json.put("content", content);
		response.getWriter().write(json.toString());
    }
    private List<FilterM> decriptCascadeString(String cascadeString,String causeFilterId){
    	 List<FilterM> filters = new ArrayList<FilterM>();
    	 // example string =  "filterId::filterValue||filterId::filterValue"
    	String filterLimit = ":#:";
     	String seperate = ":&:";
     	
     	String[] gs = cascadeString.split(filterLimit);
     	for( String g : gs){
 	    		String[] gkv = g.split(seperate);  // [0] = filterId , [1] = value
 	    			FilterM gm = new FilterM();
	 	    		gm.setFilterId( Integer.parseInt( (String)gkv[0] ) );
	 	    		try{
	 	    			gm.setSelectedValue( (String)gkv[1] );
	 	    		}catch(Exception ex){
	 	    			gm.setSelectedValue(null);
	 	    		}
	 	    		gm.setActiveFlag("1");
	 	    		if( ((String)gkv[0].toString()).equals(causeFilterId)){
	 	    			gm.setActiveFlag("0"); // important to mark ,this is cause filter 
	 	    		}
	 	    		filters.add(gm);
     	} // end loop
    	 return filters;
    }
}
