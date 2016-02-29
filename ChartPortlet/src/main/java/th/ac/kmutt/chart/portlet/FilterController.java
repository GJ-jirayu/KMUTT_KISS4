package th.ac.kmutt.chart.portlet;


import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import th.ac.kmutt.chart.form.FilterForm;
import th.ac.kmutt.chart.model.FilterInstanceM;
import th.ac.kmutt.chart.model.FilterM;
import th.ac.kmutt.chart.model.FilterValueM;
import th.ac.kmutt.chart.service.ChartService;

import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.print.attribute.HashAttributeSet;
import javax.xml.namespace.QName;

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

    @RequestMapping
    // default (action=list)
    public String showFilter(PortletRequest request, Model model) {
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
        	globalFilter= chartService.getGlobalFilter();
        }
        model.addAttribute("filterList",globalFilter);
        return "filter/showFilter";
    }

    @RequestMapping(params = "action=doSubmit") // action phase
    public void doSubmit(SessionStatus status,javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
                             @ModelAttribute("filterForm") FilterForm filterForm,
                             BindingResult result, Model model) {

       String command = "list";
       
       List<FilterM> gFilters = chartService.getGlobalFilter();
       logger.info("global filter size:"+gFilters.size());
       for(int i = 0 ; i<gFilters.size();i++){
    	   // map  name select in view showfilter.jsp  format  g_filter_+filterM.filterId
    	  
    	   logger.info("global target:"+ "g_filter_"+gFilters.get(i).getFilterId());
    	   String val = request.getParameter("g_filter_"+gFilters.get(i).getFilterId());
    	   gFilters.get(i).setSelectedValue(val);
    	   logger.info("global value:"+gFilters.get(i).getSelectedValue());
       	}
       // portlet to portlet  require configuration  portlet.xml
        QName qname = new QName("http://liferay.com/events","empinfo","x");
        th.ac.kmutt.chart.fusion.model.FilterFusionM filterFusionM=new th.ac.kmutt.chart.fusion.model.FilterFusionM();
        filterFusionM.setFilterMList(gFilters);
        response.setEvent(qname, filterFusionM);
        // status.setComplete();
        model.addAttribute("FilterMList", gFilters);
        response.setRenderParameter("action", command);
    }

}
