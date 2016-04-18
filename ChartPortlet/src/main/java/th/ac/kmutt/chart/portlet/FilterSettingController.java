package th.ac.kmutt.chart.portlet;

/**
 * Created by imake on 07/09/2015.
 */

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
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;

import th.ac.kmutt.chart.form.ChartSettingForm;
import th.ac.kmutt.chart.form.FilterForm;
import th.ac.kmutt.chart.model.ChartInstanceM;
import th.ac.kmutt.chart.model.CommentM;
import th.ac.kmutt.chart.model.FilterInstanceM;
import th.ac.kmutt.chart.model.FilterM;
import th.ac.kmutt.chart.service.ChartService;

import javax.portlet.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller("filterSettingController")
@RequestMapping("EDIT")
@SessionAttributes({"filterForm"})
public class FilterSettingController {

    private static final Logger logger = Logger
            .getLogger(FilterSettingController.class);
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
    public String showSettingFilter(PortletRequest request,PortletResponse response, Model model) {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        String instanceId=themeDisplay.getPortletDisplay().getInstanceId();
        
        List<FilterM> filterList= chartService.getGlobalFilter();
    //    model.addAttribute("filterList",filterList);
        FilterForm  filterForm = null;
        if (!model.containsAttribute("filterForm")) {
            filterForm = new FilterForm();
            model.addAttribute("filterForm",
                    filterForm);
        } else {
            filterForm = (FilterForm) model.asMap().get("filterForm");
        }
        FilterInstanceM filterInstanceM=new FilterInstanceM();
        filterInstanceM.setInstanceId(instanceId);

        List<String> mark = new ArrayList<String>();
        List<FilterInstanceM> filterInstanceList= chartService.listFilterInstance(filterInstanceM);
        if(filterInstanceList!=null){
	        for(FilterInstanceM filterInstance : filterInstanceList){
	        	mark.add( filterInstance.getFilterM().getFilterId().toString());
	        }
        }
        String[] filterGlobals = mark.toArray(new String[0]);
        filterForm.setInstanceId(instanceId);
        filterForm.setFilterGlobals(filterGlobals);
        filterForm.setFilterList(filterList);
        model.addAttribute("filterForm", filterForm);
        return "filter/editFilter";
    }
    @RequestMapping(params = "action=doSubmit") // action phase
    public void doSubmit(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
                             @ModelAttribute("filterForm") FilterForm filterForm,
                             BindingResult result, Model model) {
     
        String[] ids=filterForm.getFilterGlobals();
        List<String> filterIds = Arrays.asList(ids);
        List<FilterM> filterList = new ArrayList<FilterM>();
        
        for(String filterId : filterIds){
        	FilterM filter = new FilterM();
        	filter.setFilterId(Integer.valueOf(filterId));
        	filterList.add(filter);
        }
        
        FilterInstanceM fim= new FilterInstanceM();
        fim.setInstanceId(filterForm.getInstanceId());
        fim.setFilterList(filterList);
        chartService.saveFilterInstance(fim);

        try {
            response.setPortletMode(PortletMode.VIEW);
            response.setRenderParameter("action", "list");
        } catch (PortletModeException e) {
            e.printStackTrace();
        }
        // logger.info("chartType -->"+chartSettingForm.getChartType());
    }
}
