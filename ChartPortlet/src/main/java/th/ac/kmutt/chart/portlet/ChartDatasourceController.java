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

import th.ac.kmutt.chart.form.ChartDatasourceForm;
import th.ac.kmutt.chart.form.FilterForm;
import th.ac.kmutt.chart.model.FilterInstanceM;
import th.ac.kmutt.chart.model.FilterM;
import th.ac.kmutt.chart.model.FilterValueM;
import th.ac.kmutt.chart.model.ServiceM;
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
import java.security.Provider.Service;
import java.util.*;

@Controller("chartDatasourceController")
@RequestMapping("VIEW")
@SessionAttributes({"chartDatasourceForm"})
public class ChartDatasourceController {

    private static final Logger logger = Logger.getLogger(ChartDatasourceController.class);


    @Autowired
    @Qualifier("chartServiceWSImpl")
    private ChartService chartService;
    @InitBinder
    public void initBinder(PortletRequestDataBinder binder, PortletPreferences preferences) {
        logger.debug("initBinder");
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        //String a[] = new String[]{"ntcfaq.nfaqName"};
        final String[] ALLOWED_FIELDS = {""};
        binder.setAllowedFields(ALLOWED_FIELDS);
    }

    @RequestMapping("VIEW") 
    public String iniPage(PortletRequest request, Model model) {
    	ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);
        
        ChartDatasourceForm chartds = null;
        if (!model.containsAttribute("chartDatasourceForm")) {
        	chartds = new ChartDatasourceForm();
        } else {
        	chartds = (ChartDatasourceForm) model.asMap().get("chartDatasourceForm");
        }
        
        List<ServiceM> sm = chartService.listChartDatasource();
        chartds.setDatasources(sm);
        
        model.addAttribute("chartDatasourceForm",chartds);
        return "chart/chartDatasource";
    }

    @RequestMapping(params = "action=doSubmit") // action phase
    public void doSave(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
                             @ModelAttribute("chartDatasourceForm") ChartDatasourceForm chartds,
                             BindingResult result, Model model) {
    	 try{
    	 ServiceM s = new ServiceM();
    	 s.setServiceId(chartds.getDatasourceId());
    	 s.setServiceName(chartds.getDatasourceName());
    	 s.setSqlString(chartds.getSqlString());
    	 s.setFilterList(chartds.getFilterList());
    	 Integer resultCode  = chartService.saveChartDatasource(s);
    	 if(resultCode==0) throw new Exception("error");
    	 chartds.setMessage("success");
    	 }catch(Exception e){
    		 chartds.setMessage("error");  
    	 }
    	 model.addAttribute("chartDatasourceForm", chartds);
    }
    @ResourceMapping(value="detailChartDatasource")
	@ResponseBody 
	public void detailChartDatasource(ResourceRequest request,ResourceResponse response) throws IOException{
    	JSONObject json = JSONFactoryUtil.createJSONObject();
    	JSONObject head = JSONFactoryUtil.createJSONObject();
    	JSONObject content = JSONFactoryUtil.createJSONObject();
        
    	HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
 
		try{
	    	ServiceM s = new ServiceM();
	    	s.setServiceId(  Integer.parseInt( normalRequest.getParameter( "sid" ) ) );
	    	
	    	s = chartService.detailChartDatasource(s);
	    	
	    	content.put("sid", s.getServiceId());
	    	content.put("sname", s.getServiceName());
	    	content.put("sql", s.getSqlString());
	    	
	    	JSONArray fsonList = JSONFactoryUtil.createJSONArray();
	    	
	    	for( FilterM f :  s.getFilterList() ){
	    		JSONObject fson = JSONFactoryUtil.createJSONObject();
		        fson.put("sid", f.getFilterId() );
		        fson.put("sname", f.getFilterName() );
		        fson.put("param",f.getColumnName());
		        fsonList.put(fson);
	    	}
	    	content.put("filter", fsonList);
	    	head.put("success","1");
	    	head.put("msg", "success");
		}catch(Exception e){
			head.put("success","0");
			head.put("msg", e.getStackTrace().toString() );
		}
    	json.put("content", content);
    	json.put("header",head);
		response.getWriter().write(json.toString());
    }
   
    @ResourceMapping(value="addChartDatasourceFilter")
	@ResponseBody 
	public void addDsFilter(ResourceRequest request,ResourceResponse response) throws IOException{
    	JSONObject json = JSONFactoryUtil.createJSONObject();
    	JSONObject header = JSONFactoryUtil.createJSONObject();       
    	JSONObject content = JSONFactoryUtil.createJSONObject();       
    	HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		
		try{
			// transform  request filter =>  list filterM
			String filterReq = normalRequest.getParameter("fids"); //filter id list
			String[] filters = filterReq.split(",");
			List<FilterM> filterList = new ArrayList<FilterM>();
			for(String filter : filters){
				FilterM f = new FilterM();
				f.setFilterId( Integer.parseInt( filter ));
				filterList.add(f);
			}
			//do
			ServiceM s = new ServiceM();
			s.setServiceId( Integer.parseInt( normalRequest.getParameter("sid") ) );
	    	Integer resultCode = chartService.saveDatasourceXFilter(s);
			if(resultCode==0)
				throw new Exception();
    		header.put("success", "1");
    		header.put("msg", "success");
		}catch(Exception e){
			header.put("success", "0");
			header.put("msg",e.getStackTrace().toString());
		}
		json.put("content", content);
    	json.put("header",header);
		response.getWriter().write(json.toString());
    }
    @ResourceMapping(value="delChartDatasourceFilter")
	@ResponseBody 
	public void deleteDsFilter(ResourceRequest request,ResourceResponse response) throws IOException{
    	JSONObject json = JSONFactoryUtil.createJSONObject();
    	JSONObject header = JSONFactoryUtil.createJSONObject();       
    	JSONObject content = JSONFactoryUtil.createJSONObject();       
    	HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		
		try{
			ServiceM s = new ServiceM();
			s.setServiceId( Integer.parseInt( normalRequest.getParameter("sid") ) );
			List<FilterM> filterList = new ArrayList<FilterM>();
			FilterM f = new FilterM();
			f.setFilterId(  Integer.parseInt( normalRequest.getParameter("fid") ));
			filterList.add(f);
			s.setFilterList(filterList);
	    	Integer resultCode = chartService.deleteDatasourceXFilter(s);
			if(resultCode==0)
				throw new Exception("error");
    		header.put("success", "1");
    		header.put("msg", "success");
		}catch(Exception e){
			header.put("success", "0");
			header.put("msg",e.getStackTrace().toString());
		}
		json.put("content", content);
    	json.put("header",header);
		response.getWriter().write(json.toString());
    }
}
